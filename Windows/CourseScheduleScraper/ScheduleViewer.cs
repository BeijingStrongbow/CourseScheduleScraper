using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using System.Linq;

namespace CourseScheduleScraper
{
    public partial class ScheduleViewer : Form
    {
        private List<List<Section>> _validSchedules;

        public ScheduleViewer()
        {
            _validSchedules = new List<List<Section>>();
            InitializeComponent();
        }
        
        public void GenerateSchedules(List<Course> selected)
        {
            IterateSections(selected, 0, new List<Section>());
            if(_validSchedules.Count <= 0)
            {
                MessageBox.Show("No valid schedules found");
                return;
            }

            for (int i = 1; i <= _validSchedules.Count; i++)
            {
                uxSchedulesBox.Items.Add(i);
            }
            Show();
        }

        private void IterateSections(List<Course> courses, int index, List<Section> schedule)
        {
            foreach (Section s in courses[index].Sections)
            {
                schedule.Add(s);
                bool valid = true;
                if(index == courses.Count - 1)
                {
                    for(int i = 0; i < schedule.Count; i++)
                    {
                        for(int j = i+1; j < schedule.Count; j++)
                        {
                            if (schedule[i].Overlaps(schedule[j]))
                            {
                                valid = false;
                                break;
                            }
                        }
                        if (!valid)
                        {
                            break;
                        }
                    }
                    if (valid)
                    {
                        List<Section> temp = new List<Section>();
                        foreach (Section v in schedule)
                        {
                            temp.Add(v);
                        }
                        _validSchedules.Add(temp);
                    }
                }
                else
                {
                    IterateSections(courses, index + 1, schedule);
                }
                schedule.Remove(s);
            }
        }
        
        private void ShowDetails(object sender, EventArgs e)
        {
            uxDetailsBox.Items.Clear();
            List<Section> schedule = _validSchedules[uxSchedulesBox.SelectedIndex];
            schedule.Sort();
            foreach(Section s in schedule)
            {
                uxDetailsBox.Items.Add(s);
            }
        }

        private void uxNarrowResultsButton_Click(object sender, EventArgs e)
        {
            string searchType = GetSearchCriteria();
            string courseNumber = uxCourseNumberBox.Text.Replace(" ", "");
            string sectionType = uxSectionTypeBox.Text.Trim();
            List<int> toRemove = new List<int>();

            if(sectionType != "")
            {
                if (sectionType.ToUpper().Contains("Q"))
                {
                    sectionType = "QZ";
                }
                else if (sectionType.ToUpper().Contains("R"))
                {
                    sectionType = "REC";
                }
                else if (sectionType.ToUpper().Contains("L") || sectionType.ToUpper().Contains("D"))
                {
                    sectionType = "LAB";
                }
                else
                {
                    MessageBox.Show("Invalid section type! Valid types are Lab, Std, Rec, and Quiz");
                    return;
                }
            }
            else
            {
                sectionType = "LEC";
            }

            if(searchType == "Start Time")
            {
                try
                {
                    string time = uxStartTimeBox.Text;
                    int hour = Convert.ToInt32(time.Substring(0, time.IndexOf(':')));
                    int minute = Convert.ToInt32(time.Substring(time.IndexOf(':') + 1, time.IndexOf(' ') - 2));
                    if (time.Contains("p"))
                    {
                        hour += 12;
                    }

                    DateTime startTime = new DateTime(1, 1, 1, hour, minute, 0);
                    for(int i = 0; i < _validSchedules.Count; i++)
                    {
                        foreach (Section s in _validSchedules[i])
                        {
                            if (s.Course.Number == courseNumber && s.Type == sectionType && !DateTime.Equals(s.StartTime, startTime))
                            {
                                toRemove.Add(i);
                            }
                        }
                    }
                }
                catch (IndexOutOfRangeException)
                {
                    MessageBox.Show("Time should be formatted as hh:mm/h:mm a.m./p.m. (e.g. 4:30 p.m.)");
                    return;
                }
                catch (ArgumentOutOfRangeException)
                {
                    MessageBox.Show("Time should be formatted as hh:mm / h:mm a.m./ p.m. (e.g. 4:30 p.m.)");
                    return;
                }
            }
            else if(searchType == "Instructor")
            {
                string instructor = uxInstructorBox.Text;

                for(int i = 0; i < _validSchedules.Count; i++)
                {
                    foreach(Section s in _validSchedules[i])
                    {
                        if(s.Course.Number == courseNumber && s.Type == sectionType && s.Instructor.ToLower() != instructor.ToLower())
                        {
                            toRemove.Add(i);
                        }
                    }
                }
            }
            else if(searchType == "Section Number")
            {
                try
                {
                    int number = Convert.ToInt32(uxSectionNumberBox.Text);

                    for (int i = 0; i < _validSchedules.Count; i++)
                    {
                        foreach (Section s in _validSchedules[i])
                        {
                            if (s.Course.Number == courseNumber && s.Type == sectionType && s.Number != number)
                            {
                                toRemove.Add(i);
                            }
                        }
                    }
                }
                catch (FormatException)
                {
                    MessageBox.Show("Invalid number");
                    return;
                }
            }
            else
            {
                MessageBox.Show("Only one box can have text in it");
                return;
            }


            if(toRemove.Count == _validSchedules.Count)
            {
                MessageBox.Show("No schedules found");
                return;
            }
            else
            {
                for(int i = toRemove.Count - 1; i >= 0; i--)
                {
                    _validSchedules.RemoveAt(toRemove[i]);
                    uxSchedulesBox.Items.RemoveAt(toRemove[i]);
                }
                uxSchedulesBox.Refresh();
            }
        }

        private string GetSearchCriteria()
        {
            if (uxStartTimeBox.Text.Length > 0 && uxStartTimeBox.Text[0] != '(' &&
                (uxInstructorBox.Text.Length == 0 || uxInstructorBox.Text[0] == '(') &&
                uxSectionNumberBox.Text == "")
            {
                return "Start Time";
            }
            else if (uxInstructorBox.Text.Length > 0 && uxInstructorBox.Text[0] != '(' &&
                (uxStartTimeBox.Text.Length == 0 || uxStartTimeBox.Text[0] == '(') &&
                uxSectionNumberBox.Text == "")
            {
                return "Instructor";
            }
            else if(uxSectionNumberBox.Text != "" &&
                (uxStartTimeBox.Text.Length == 0 || uxStartTimeBox.Text[0] == '(') &&
                (uxInstructorBox.Text.Length == 0 || uxInstructorBox.Text[0] == '('))
            {
                return "Section Number";
            }
            else
            {
                return "Invalid";
            }
        }
    }
}
