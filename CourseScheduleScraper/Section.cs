using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace CourseScheduleScraper
{
    public class Section
    {
        private string _letter;

        private int _number;

        private DateTime _startTime;

        private DateTime _endTime;

        private bool _isAppointment;

        private string _weekDays;

        private string _instructor;

        //optional
        private DateTime _fromDate;

        //optional
        private DateTime _toDate;

        public Section(string letter, int number, string days, string instructor)
        {
            _letter = letter;
            _number = number;
            _isAppointment = true;
            _weekDays = days;
            _instructor = instructor;
        }

        public Section(string letter, int number, string days, string instructor, DateTime fromDate, DateTime toDate)
        {
            _letter = letter;
            _number = number;
            _weekDays = days;
            _instructor = instructor;
            _fromDate = fromDate;
            _toDate = toDate;
            _isAppointment = true;
        }

        public Section(string letter, int number, DateTime startTime, DateTime endTime, string days, string instructor)
        {
            _letter = letter;
            _number = number;
            _startTime = startTime;
            _endTime = endTime;
            _weekDays = days;
            _instructor = instructor;
            _isAppointment = false;
        }

        public Section(string letter, int number, DateTime startTime, DateTime endTime, string days, string instructor, DateTime fromDate, DateTime toDate)
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
    }
}
