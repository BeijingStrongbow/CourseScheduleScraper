using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace CourseScheduleScraper
{
    public class Section : IComparable
    {
        private string _letter;

        private int _number;

        private DateTime _startTime;

        private DateTime _endTime;

        private bool _isAppointment;

        private string _weekDays;

        private string _instructor;

        private string _type;

        private Course _course;

        //optional
        private DateTime _fromDate;

        //optional
        private DateTime _toDate;

        public Section(string letter, int number, string days, string instructor, Course course)
        {
            _letter = letter;
            _number = number;
            _isAppointment = true;
            _weekDays = days;
            _instructor = instructor;
            _course = course;
        }

        public Section(string letter, int number, string days, string instructor, DateTime fromDate, DateTime toDate, Course course)
        {
            _letter = letter;
            _number = number;
            _weekDays = days;
            _instructor = instructor;
            _fromDate = fromDate;
            _toDate = toDate;
            _isAppointment = true;
            _course = course;
        }

        public Section(string letter, int number, DateTime startTime, DateTime endTime, string days, string instructor, Course course)
        {
            _letter = letter;
            _number = number;
            _startTime = startTime;
            _endTime = endTime;
            _weekDays = days;
            _instructor = instructor;
            _isAppointment = false;
            _course = course;
        }

        public Section(string letter, int number, DateTime startTime, DateTime endTime, string days, string instructor, DateTime fromDate, DateTime toDate, Course course)
        {
            _letter = letter;
            _number = number;
            _startTime = startTime;
            _endTime = endTime;
            _weekDays = days;
            _instructor = instructor;
            _fromDate = fromDate;
            _toDate = toDate;
            _isAppointment = false;
            _course = course;
        }

        public string Letter
        {
            get
            {
                return _letter;
            }
        }

        public int Number
        {
            get
            {
                return _number;
            }
        }

        public DateTime StartTime
        {
            get
            {
                if (_startTime == null) throw new InvalidOperationException("This section has no start time!");
                return _startTime;
            }
        }

        public DateTime EndTime
        {
            get
            {
                if (_endTime == null) throw new InvalidOperationException("This section has no end time!");
                return _endTime;
            }
        }

        public string Days
        {
            get
            {
                return _weekDays;
            }
        }

        public string Instructor
        {
            get
            {
                return _instructor;
            }
        }

        public DateTime FromDate
        {
            get
            {
                if (_fromDate == null) throw new InvalidOperationException("You must initialize FromDate first!");
                return _fromDate;
            }
        }

        public DateTime ToDate
        {
            get
            {
                if (_toDate == null) throw new InvalidOperationException("You must initialize ToDate first!");
                return _toDate;
            }
        }

        public bool IsAppointment
        {
            get
            {
                return _isAppointment;
            }
        }

        public Course Course
        {
            get
            {
                return _course;
            }
            set
            {
                _course = value;
            }
        }

        public string Type
        {
            get
            {
                return _type;
            }
            set
            {
                _type = value;
            }
        }

        public bool Overlaps(Section s)
        {
            if ((_number == 10707 && s.Number == 14148) || (_number == 14148 && s.Number == 10707))
            {
                int sldkjs = 5;
            }
            if (s._isAppointment || _isAppointment)
            {
                return true;
            }

            for (int i = 0; i < _weekDays.Length; i++)
            {
                for (int j = 0; j < s.Days.Length; j++)
                {
                    if (_weekDays[i] == s.Days[j])
                    {
                        if(_endTime.CompareTo(s._startTime) < 0 || _startTime.CompareTo(s._endTime) > 0)
                        {
                            return false;
                        }
                        else if(_fromDate != default(DateTime) && s._fromDate != default(DateTime) &&
                            (_toDate.CompareTo(s._fromDate) < 0 || _fromDate.CompareTo(s._toDate) > 0))
                        {
                            return false;
                        }
                        else
                        {
                            return true;
                        }
                    }
                }
            }
            return false;
        }

        public int CompareTo(object obj)
        {
            return _startTime.CompareTo(((Section)obj)._startTime);
        }

        public string AsString
        {
            get
            {
                int startHour = _startTime.Hour;
                int startMinute = _startTime.Minute;
                int endHour = _endTime.Hour;
                int endMinute = _endTime.Minute;

                string time;

                string sStartMinute = Convert.ToString(startMinute);
                string sEndMinute = Convert.ToString(endMinute);

                if(startMinute < 10)
                {
                    sStartMinute = "0" + sStartMinute;
                }
                if(endMinute < 10)
                {
                    sEndMinute = "0" + sEndMinute;
                }

                if(startHour < 12 && endHour < 12)
                {
                    time = startHour + ":" + sStartMinute + " - " + endHour + ":" + sEndMinute + " a.m.";
                }
                else if(startHour < 12 && endHour >= 12)
                {
                    if(endHour >= 13)
                    {
                        endHour -= 12;
                    }
                    time = startHour + ":" + sStartMinute + " a.m. - " + endHour + ":" + sEndMinute + " p.m.";
                }
                else
                {
                    if(startHour >= 13)
                    {
                        startHour -= 12;
                    }
                    if(endHour >= 13)
                    {
                        endHour -= 12;
                    }
                    time = startHour + ":" + sStartMinute + " - " + endHour + ":" + sEndMinute + " p.m.";
                }

                return _course.Number + ": " + _course.Name + " (" + _number + ") " + _weekDays + " " + time + " " + _instructor;
            }
        }
    }
}
