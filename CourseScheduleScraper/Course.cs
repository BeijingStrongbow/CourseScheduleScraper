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

        public Course(string number, string name)
        {
            _number = number;
            _name = name;
            _sections = new List<Section>();
        }

        public void AddSection(Section s)
        {
            _sections.Add(s);
        }
    }
}
