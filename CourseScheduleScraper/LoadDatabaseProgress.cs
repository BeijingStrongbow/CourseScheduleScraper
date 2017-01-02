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
    public partial class LoadDatabaseProgress : Form
    {
        public int Progress
        {
            get
            {
                return uxProgressBar.Value;
            }
            set
            {
                uxProgressBar.Value = value;
            }
        }

        public LoadDatabaseProgress()
        {
            InitializeComponent();
        }
    }
}
