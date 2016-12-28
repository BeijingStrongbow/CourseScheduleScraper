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

        public UserInterface()
        {
            _progress = new LoadDatabaseProgress();
            InitializationDialog init = new InitializationDialog();
            init.ShowDialog();
            _year = init.Year;
            _semester = init.Semester;
            _urls = new List<string>();
            InitializeComponent();
            if (init.ReloadDatabase && )
            {
                RunPython(_year, _semester);
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
            get_urls.StartInfo.WorkingDirectory = "C:\\Users\\Eric\\Documents\\CodeProjects\\CourseScheduleScraper\\__pycache__";

            get_urls.OutputDataReceived += new DataReceivedEventHandler(UrlDataRecieved);
            get_urls.StartInfo.RedirectStandardOutput = true;
            get_urls.Start();
            get_urls.BeginOutputReadLine();

            get_urls.WaitForExit();

            Process get_courses = new Process();
            get_courses.StartInfo.CreateNoWindow = true;
            get_courses.StartInfo.UseShellExecute = false;

            get_courses.StartInfo.FileName = "cmd";
            get_courses.StartInfo.WorkingDirectory = "C:\\Users\\Eric\\Documents\\CodeProjects\\CourseScheduleScraper\\__pycache__";

            double targetProgressIncrement = 100.0 / _urls.Count;
            double urlsParsed = 1;

            foreach(string url in _urls)
            {
                get_courses.StartInfo.Arguments = "/c python get_courses.cpython-35.pyc " + url + " " + _urls.Count + " " + Convert.ToString(urlsParsed != 1);
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

        public void LoadDatabase()
        {
            using (StreamReader input = new StreamReader("courseCont.txt"))
            {
                Course current;

                while (!input.EndOfStream)
                {
                    string line = input.ReadLine();
                    //if line contains section data
                    if(line[0] == '\t')
                    {

                    }
                    //if line contains course data
                    else
                    {
                        string[] lineData = line.Split(new char[]{',', '"'});
                        string number = "";
                        string name = "";
                        current = new Course(number, name);
                    }
                }
            }
        }
    }
}
