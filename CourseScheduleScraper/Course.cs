using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace CourseScheduleScraper
{
    public class Course
    {
        private List<Section> _sections;

        //i.e. CIS300
        private string _number;

        //i.e. Chemistry 1
        private string _name;

        private Course _qz;

        private Course _rec;

        private Course _lab;

        public string Number
        {
            get
            {
                return _number;
            }
        }

        public string Name
        {
            get
            {
                return _name;
            }
        }

        public List<Section> Sections
        {
            get
            {
                return _sections;
            }
        }

        public int NumberOfSections
        {
            get
            {
                return _sections.Count;
            }
        }

        public Course Lab
        {
            get
            {
                return _lab;
            }
        }

        public Course Rec
        {
            get
            {
                return _rec;
            }
        }

        public Course Quiz
        {
            get
            {
                return _qz;
            }
        }

        public string AsString
        {
            get
            {
                return _number + ": " + _name;
            }
        }

        public Course()
        {
            _number = "";
            _name = "";
        }

        public Course(string number, string name)
        {
            _number = number;
            _name = name;
            _sections = new List<Section>();
        }

        public void AddSection(Section s, string type)
        {
            switch (type)
            {
                case "LAB":
                case "STD":
                    if(_lab == null)
                    {
                        _lab = new Course(_number, _name + " Lab");
                    }
                    type = "LAB";
                    s.Course = _lab;
                    _lab._sections.Add(s);
                    break;
                case "REC":
                    if(_rec == null)
                    {
                        _rec = new Course(_number, _name + " Recitation");
                    }
                    s.Course = _rec;
                    _rec._sections.Add(s);
                    break;
                case "QZ":
                    if(_qz == null)
                    {
                        _qz = new Course(_number, _name + " Quiz");
                    }
                    s.Course = _qz;
                    _qz._sections.Add(s);
                    break;
                default:
                    type = "LEC";
                    _sections.Add(s);
                    break;
            }
            s.Type = type;
        }

        public void FinishProcessing()
        {
            int labLength = 0;
            int qzLength = 0;
            int recLength = 0;

            if (_lab != null)
            {
                labLength = _lab.NumberOfSections;
            }
            if (_qz != null)
            {
                qzLength = _qz.NumberOfSections;
            }
            if (_rec != null)
            {
                recLength = _rec.NumberOfSections;
            }

            int mostSections = Math.Max(labLength, Math.Max(qzLength, recLength));
            if(mostSections == 0)
            {
                return;
            }
            else if (mostSections == labLength)
            {
                _sections = _lab._sections;
                foreach(Section s in _sections)
                {
                    s.Course = this;
                }
                _lab = null;
            }
            else if(mostSections == qzLength)
            {
                _sections = _qz._sections;
                foreach (Section s in _sections)
                {
                    s.Course = this;
                }
                _qz = null;
            }
            else
            {
                _sections = _rec._sections;
                foreach (Section s in _sections)
                {
                    s.Course = this;
                }
                _rec = null;
            }
        }
    }
}