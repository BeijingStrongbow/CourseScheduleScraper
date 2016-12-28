using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace CourseScheduleScraper
{
    public partial class InitializationDialog : Form
    {
        private int _year;

        private string _semester;

        public int Year
        {
            get
            {
                return _year;
            }
        }

        public string Semester
        {
            get
            {
                return _semester;
            }
        }

        public bool ReloadDatabase
        {
            get
            {
                return uxReloadDatabaseCheckBox.Checked;
            }
        }

        public InitializationDialog()
        {
            InitializeComponent();
        }

        private void uxLoadButton_Click(object sender, EventArgs e)
        {
            int year = 0;
            if(uxYearBox.Text.Length == 4)
            {
                try
                {
                    year = Convert.ToInt32(uxYearBox.Text);
                }
                catch(FormatException ex)
                {
                    MessageBox.Show("The year must be a four-digit number!");
                    return;
                }
            }
            else
            {
                MessageBox.Show("The year must be a four-digit number!");
                return;
            }

            string semester = uxSemesterBox.Text.ToLower();
            if(semester != "spring" && semester != "summer" && semester != "fall")
            {
                MessageBox.Show("Semester can only be Spring, Summer, or Fall!");
                return;
            }

            _year = year;
            _semester = semester;
            Hide();
        }
    }
}
