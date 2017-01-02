using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Diagnostics;
using System.Drawing;
using System.IO;
using System.Linq;
using System.Text;
using System.Threading;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace CourseScheduleScraper
{
    public partial class UserInterface : Form
    {
        private int _year;

        private string _semester;

        private LoadDatabaseProgress _progress;

        private List<string> _urls;

        private Dictionary<string, Course> _courses;

        public UserInterface()
        {
            InitializeComponent();
            Hide();
            _courses = new Dictionary<string, Course>();
            _progress = new LoadDatabaseProgress();
            InitializationDialog init = new InitializationDialog();
            if(init.ShowDialog() == DialogResult.Cancel)
            {
                Load += UserInterface_Load;
            }
            else
            {
                _year = init.Year;
                _semester = init.Semester;
                _urls = new List<string>();
                if (init.ReloadDatabase || !SemesterLoaded(_year, _semester))
                {
                    RunPython(_year, _semester);
                }

                LoadDatabase(_year, _semester);
                Show();
            }
        }

        private void RunPython(int year, string semester)
        {
            _progress.Show();

            Process get_urls = new Process();
            get_urls.StartInfo.CreateNoWindow = true;
            get_urls.StartInfo.UseShellExecute = false;

            get_urls.StartInfo.FileName = "cmd";
            get_urls.StartInfo.Arguments = "/c python get_urls.cpython-35.pyc " + year + " " + semester;

            get_urls.OutputDataReceived += new DataReceivedEventHandler(UrlDataRecieved);
            get_urls.StartInfo.RedirectStandardOutput = true;
            get_urls.Start();
            get_urls.BeginOutputReadLine();

            get_urls.WaitForExit();

            Process get_courses = new Process();
            get_courses.StartInfo.CreateNoWindow = true;
            get_courses.StartInfo.UseShellExecute = false;

            get_courses.StartInfo.FileName = "cmd";

            double targetProgressIncrement = 100.0 / _urls.Count;
            double urlsParsed = 1;

            foreach(string url in _urls)
            {
                get_courses.StartInfo.Arguments = "/c python get_courses.cpython-35.pyc " + url + " " + _urls.Count + " " + Convert.ToString(urlsParsed != 1) + " " + year + " " + semester;
                get_courses.Start();
                get_courses.WaitForExit();

                _progress.Progress = Convert.ToInt32(Math.Floor(targetProgressIncrement * urlsParsed));

                urlsParsed++;
            }

            _progress.Dispose();
        }

        public void UrlDataRecieved(object sendingProcess, DataReceivedEventArgs outLine)
        {
            if (!String.IsNullOrEmpty(outLine.Data))
            {
                _urls.Add(outLine.Data);
            }
        }

        public void LoadDatabase(int year, string semester)
        {
            using (StreamReader input = new StreamReader("database\\" + year + semester + ".txt"))
            {
                Course current = new Course();

                while (!input.EndOfStream)
                {
                    string line = input.ReadLine();
                    //if line contains section data
                    if(line[0] == '\t')
                    {
                        string[] lineData = line.Split(new char[] { '|', '"'});

                        string days = lineData[10];
                        string[] data = days.Split(' ');
                        DateTime startDate = new DateTime();
                        DateTime endDate = new DateTime();
                        if (days.Length > 7 && days != "Appointment")
                        {
                            if (data.Length == 12)
                            {
                                if (data[6].Contains(","))
                                {
                                    startDate = new DateTime(Convert.ToInt32(data[7]), MonthStringToInt(data[5]), Convert.ToInt32(data[6].Substring(0, data[6].Length - 1)));
                                }
                                else
                                {
                                    startDate = new DateTime(Convert.ToInt32(data[7]), MonthStringToInt(data[5]), Convert.ToInt32(data[6]));
                                }
                                endDate = new DateTime(Convert.ToInt32(data[11].Substring(0, data[11].Length - 1)), MonthStringToInt(data[9]), Convert.ToInt32(data[10].Substring(0, data[10].Length - 1)));
                            }
                            else
                            {
                                if (data.Length == 12)
                                {
                                    if (data[6].Contains(","))
                                    {
                                        startDate = new DateTime(Convert.ToInt32(data[10]), MonthStringToInt(data[5]), Convert.ToInt32(data[6].Substring(0, data[6].Length - 1)));
                                    }
                                    else
                                    {
                                        startDate = new DateTime(Convert.ToInt32(data[10]), MonthStringToInt(data[5]), Convert.ToInt32(data[6]));
                                    }
                                    endDate = new DateTime(Convert.ToInt32(data[10].Substring(0, data[11].Length - 1)), MonthStringToInt(data[8]), Convert.ToInt32(data[9].Substring(0, data[9].Length - 1)));
                                }
                            }
                        }

                        if (lineData[13].Length > 15)
                        {
                            string start = lineData[13].Substring(0, lineData[13].IndexOf('-') - 1);
                            int startHour = Convert.ToInt32(start.Substring(0, start.IndexOf(':')));
                            int startMinute = Convert.ToInt32(start.Substring(start.IndexOf(':') + 1, 2));

                            string end = lineData[13].Substring(lineData[13].IndexOf('-') + 2);
                            int endHour = Convert.ToInt32(end.Substring(0, end.IndexOf(':')));
                            int endMinute = Convert.ToInt32(end.Substring(end.IndexOf(':') + 1, 2));

                            if(end.Contains("p") && !start.Contains("a") && startHour != 12)
                            {
                                startHour += 12;
                            }
                            if(end.Contains("p") && endHour != 12)
                            {
                                endHour += 12;
                            }

                            DateTime startTime = new DateTime(1, 1, 1, startHour, startMinute, 0);
                            DateTime endTime = new DateTime(1, 1, 1, endHour, endMinute, 0);

                            if(startDate != default(DateTime))
                            {
                                current.AddSection(new Section(lineData[1], Convert.ToInt32(lineData[7]), startTime, endTime, data[0], lineData[lineData.Length - 2], startDate, endDate, current), lineData[4]);
                            }
                            else
                            {
                                current.AddSection(new Section(lineData[1], Convert.ToInt32(lineData[7]), startTime, endTime, lineData[10], lineData[lineData.Length - 2], current), lineData[4]);
                            }
                        }
                        else
                        {
                            if(startDate != default(DateTime))
                            {
                                current.AddSection(new Section(lineData[1], Convert.ToInt32(lineData[7]), data[0], lineData[lineData.Length - 2], startDate, endDate, current), lineData[4]);
                            }
                            else
                            {
                                current.AddSection(new Section(lineData[1], Convert.ToInt32(lineData[7]), lineData[10], lineData[lineData.Length - 2], current), lineData[4]);
                            }
                        }
                    }
                    //if line contains course data
                    else
                    {
                        string[] lineData = line.Split(new char[]{'|', '"'});
                        string number = lineData[1];
                        string name = lineData[4];
                        if (!_courses.ContainsKey(number))
                        {
                            current.FinishProcessing();
                            current = new Course(number, name);
                            _courses.Add(number, current);
                        }
                    }
                }
            }
        }

        private bool SemesterLoaded(int year, string semester)
        {
            string[] loaded = Directory.GetFiles("database");

            foreach(string s in loaded)
            {
                string[] data = s.Split('\\');
                if(data[data.Length - 1] == year + semester + ".txt")
                {
                    return true;
                }
            }
            return false;
        }

        private void UserInterface_Load(object sender, EventArgs e)
        {
            Dispose();
            Application.Exit();
        }

        private void uxSearchButton_Click(object sender, EventArgs e)
        {
            string searchMethod = GetSearchMethod();
            bool found = false;
            uxSearchResultsList.Items.Clear();
            
            if(searchMethod == "Course Name")
            {
                string name = uxCourseNameBox.Text.Trim();
                foreach(Course c in _courses.Values)
                {
                    if (c.Name.ToUpper().Contains(name.ToUpper()))
                    {
                        uxSearchResultsList.Items.Add(c);
                        found = true;
                    }
                }
                if (!found)
                {
                    MessageBox.Show("No course called " + name + " was found");
                }
            }
            else if(searchMethod == "Course Number")
            {
                string number = uxCourseNumberBox.Text.Replace(" ", "");
                
                foreach(string n in _courses.Keys)
                {
                    if (n.ToUpper().Contains(number.ToUpper()))
                    {
                        Course c;
                        _courses.TryGetValue(n, out c);
                        uxSearchResultsList.Items.Add(c);
                        found = true;
                    }
                }
                if (!found)
                {
                    MessageBox.Show("No course numbered " + number + " was found");
                }
            }
            else
            {
                MessageBox.Show("Invalid search criteria");
            }
            uxCourseNumberBox.Clear();
            uxCourseNameBox.Clear();
        }

        private string GetSearchMethod()
        {
            if (uxCourseNameBox.Text.Length > 0 && uxCourseNameBox.Text[0] != '(' &&
                (uxCourseNumberBox.Text.Length == 0 || uxCourseNumberBox.Text[0] == '('))
            {
                return "Course Name";
            }
            else if (uxCourseNumberBox.Text.Length > 0 && uxCourseNumberBox.Text[0] != '(' &&
                (uxCourseNameBox.Text.Length == 0 || uxCourseNameBox.Text[0] == '('))
            {
                return "Course Number";
            }
            else
            {
                return "Invalid";
            }
        }

        private void uxSearchResultsList_DoubleClick(object sender, EventArgs e)
        {
            uxSelectedCoursesList.Items.Add(uxSearchResultsList.SelectedItem);
            uxSearchResultsList.Items.Remove(uxSearchResultsList.SelectedItem);
        }

        private void uxRemoveItemButon_Click(object sender, EventArgs e)
        {
            uxSelectedCoursesList.Items.Remove(uxSelectedCoursesList.SelectedItem);
        }

        private void uxCreateScheduleButton_Click(object sender, EventArgs e)
        {
            List<Course> schedule = uxSelectedCoursesList.Items.OfType<Course>().ToList();
            List<Course> temp = new List<Course>();
            foreach(Course c in schedule)
            {
                temp.Add(c);
                if(c.Lab != null)
                {
                    temp.Add(c.Lab);
                }
                if(c.Rec != null)
                {
                    temp.Add(c.Rec);
                }
                if(c.Quiz != null)
                {
                    temp.Add(c.Quiz);
                }
            }

            ScheduleViewer viewer = new ScheduleViewer();
            viewer.GenerateSchedules(temp);
        }

        private int MonthStringToInt(string month)
        {
            month = month.ToLower();
            switch (month)
            {
                case "january":
                    return 1;
                case "february":
                    return 2;
                case "march":
                    return 3;
                case "april":
                    return 4;
                case "may":
                    return 5;
                case "june":
                    return 6;
                case "july":
                    return 7;
                case "august":
                    return 8;
                case "september":
                    return 9;
                case "october":
                    return 10;
                case "november":
                    return 11;
                case "december":
                    return 12;
                default:
                    return -1;
            }
        }
    }
}
