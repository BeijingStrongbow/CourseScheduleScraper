namespace CourseScheduleScraper
{
    partial class ScheduleViewer
    {
        /// <summary>
        /// Required designer variable.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        /// Clean up any resources being used.
        /// </summary>
        /// <param name="disposing">true if managed resources should be disposed; otherwise, false.</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Windows Form Designer generated code

        /// <summary>
        /// Required method for Designer support - do not modify
        /// the contents of this method with the code editor.
        /// </summary>
        private void InitializeComponent()
        {
            this.uxSchedulesBox = new System.Windows.Forms.ListBox();
            this.uxDetailsBox = new System.Windows.Forms.ListBox();
            this.uxSchedulesLabel = new System.Windows.Forms.Label();
            this.uxDetailsButton = new System.Windows.Forms.Button();
            this.uxDetailsLabel = new System.Windows.Forms.Label();
            this.uxNarrowResultsLabel = new System.Windows.Forms.Label();
            this.uxCourseNumberLabel = new System.Windows.Forms.Label();
            this.uxCourseNumberBox = new System.Windows.Forms.TextBox();
            this.uxStartTimeLabel = new System.Windows.Forms.Label();
            this.uxStartTimeBox = new System.Windows.Forms.TextBox();
            this.uxInstructorLabel = new System.Windows.Forms.Label();
            this.uxInstructorBox = new System.Windows.Forms.TextBox();
            this.uxExactlyAsInLabel = new System.Windows.Forms.Label();
            this.uxNarrowResultsButton = new System.Windows.Forms.Button();
            this.uxSectionTypeLabel = new System.Windows.Forms.Label();
            this.uxSectionTypeBox = new System.Windows.Forms.TextBox();
            this.label1 = new System.Windows.Forms.Label();
            this.uxSectionNumberLabel = new System.Windows.Forms.Label();
            this.uxSectionNumberBox = new System.Windows.Forms.TextBox();
            this.SuspendLayout();
            // 
            // uxSchedulesBox
            // 
            this.uxSchedulesBox.Font = new System.Drawing.Font("Microsoft Sans Serif", 12F);
            this.uxSchedulesBox.FormattingEnabled = true;
            this.uxSchedulesBox.ItemHeight = 25;
            this.uxSchedulesBox.Location = new System.Drawing.Point(13, 45);
            this.uxSchedulesBox.Name = "uxSchedulesBox";
            this.uxSchedulesBox.Size = new System.Drawing.Size(152, 429);
            this.uxSchedulesBox.TabIndex = 0;
            this.uxSchedulesBox.DoubleClick += new System.EventHandler(this.ShowDetails);
            // 
            // uxDetailsBox
            // 
            this.uxDetailsBox.DisplayMember = "AsString";
            this.uxDetailsBox.Font = new System.Drawing.Font("Microsoft Sans Serif", 12F);
            this.uxDetailsBox.FormattingEnabled = true;
            this.uxDetailsBox.HorizontalScrollbar = true;
            this.uxDetailsBox.ItemHeight = 25;
            this.uxDetailsBox.Location = new System.Drawing.Point(308, 45);
            this.uxDetailsBox.Name = "uxDetailsBox";
            this.uxDetailsBox.Size = new System.Drawing.Size(831, 429);
            this.uxDetailsBox.TabIndex = 1;
            // 
            // uxSchedulesLabel
            // 
            this.uxSchedulesLabel.AutoSize = true;
            this.uxSchedulesLabel.Font = new System.Drawing.Font("Microsoft Sans Serif", 12F);
            this.uxSchedulesLabel.Location = new System.Drawing.Point(13, 13);
            this.uxSchedulesLabel.Name = "uxSchedulesLabel";
            this.uxSchedulesLabel.Size = new System.Drawing.Size(105, 25);
            this.uxSchedulesLabel.TabIndex = 2;
            this.uxSchedulesLabel.Text = "Schedules";
            // 
            // uxDetailsButton
            // 
            this.uxDetailsButton.Font = new System.Drawing.Font("Microsoft Sans Serif", 12F);
            this.uxDetailsButton.Location = new System.Drawing.Point(169, 208);
            this.uxDetailsButton.Name = "uxDetailsButton";
            this.uxDetailsButton.Size = new System.Drawing.Size(133, 63);
            this.uxDetailsButton.TabIndex = 3;
            this.uxDetailsButton.Text = "View Schedule";
            this.uxDetailsButton.UseVisualStyleBackColor = true;
            this.uxDetailsButton.Click += new System.EventHandler(this.ShowDetails);
            // 
            // uxDetailsLabel
            // 
            this.uxDetailsLabel.AutoSize = true;
            this.uxDetailsLabel.Font = new System.Drawing.Font("Microsoft Sans Serif", 12F);
            this.uxDetailsLabel.Location = new System.Drawing.Point(303, 13);
            this.uxDetailsLabel.Name = "uxDetailsLabel";
            this.uxDetailsLabel.Size = new System.Drawing.Size(71, 25);
            this.uxDetailsLabel.TabIndex = 4;
            this.uxDetailsLabel.Text = "Details";
            // 
            // uxNarrowResultsLabel
            // 
            this.uxNarrowResultsLabel.AutoSize = true;
            this.uxNarrowResultsLabel.Font = new System.Drawing.Font("Microsoft Sans Serif", 12F);
            this.uxNarrowResultsLabel.Location = new System.Drawing.Point(12, 477);
            this.uxNarrowResultsLabel.Name = "uxNarrowResultsLabel";
            this.uxNarrowResultsLabel.Size = new System.Drawing.Size(143, 25);
            this.uxNarrowResultsLabel.TabIndex = 5;
            this.uxNarrowResultsLabel.Text = "Narrow Results";
            // 
            // uxCourseNumberLabel
            // 
            this.uxCourseNumberLabel.AutoSize = true;
            this.uxCourseNumberLabel.Font = new System.Drawing.Font("Microsoft Sans Serif", 12F);
            this.uxCourseNumberLabel.Location = new System.Drawing.Point(8, 518);
            this.uxCourseNumberLabel.Name = "uxCourseNumberLabel";
            this.uxCourseNumberLabel.Size = new System.Drawing.Size(150, 25);
            this.uxCourseNumberLabel.TabIndex = 6;
            this.uxCourseNumberLabel.Text = "Course Number";
            // 
            // uxCourseNumberBox
            // 
            this.uxCourseNumberBox.Font = new System.Drawing.Font("Microsoft Sans Serif", 12F);
            this.uxCourseNumberBox.Location = new System.Drawing.Point(13, 547);
            this.uxCourseNumberBox.Name = "uxCourseNumberBox";
            this.uxCourseNumberBox.Size = new System.Drawing.Size(197, 30);
            this.uxCourseNumberBox.TabIndex = 7;
            // 
            // uxStartTimeLabel
            // 
            this.uxStartTimeLabel.AutoSize = true;
            this.uxStartTimeLabel.Font = new System.Drawing.Font("Microsoft Sans Serif", 12F);
            this.uxStartTimeLabel.Location = new System.Drawing.Point(367, 518);
            this.uxStartTimeLabel.Name = "uxStartTimeLabel";
            this.uxStartTimeLabel.Size = new System.Drawing.Size(102, 25);
            this.uxStartTimeLabel.TabIndex = 8;
            this.uxStartTimeLabel.Text = "Start Time";
            // 
            // uxStartTimeBox
            // 
            this.uxStartTimeBox.Font = new System.Drawing.Font("Microsoft Sans Serif", 12F);
            this.uxStartTimeBox.Location = new System.Drawing.Point(372, 547);
            this.uxStartTimeBox.Name = "uxStartTimeBox";
            this.uxStartTimeBox.Size = new System.Drawing.Size(206, 30);
            this.uxStartTimeBox.TabIndex = 9;
            this.uxStartTimeBox.Text = "(e.g. 3:40 p.m.)";
            // 
            // uxInstructorLabel
            // 
            this.uxInstructorLabel.AutoSize = true;
            this.uxInstructorLabel.Font = new System.Drawing.Font("Microsoft Sans Serif", 12F);
            this.uxInstructorLabel.Location = new System.Drawing.Point(598, 518);
            this.uxInstructorLabel.Name = "uxInstructorLabel";
            this.uxInstructorLabel.Size = new System.Drawing.Size(92, 25);
            this.uxInstructorLabel.TabIndex = 10;
            this.uxInstructorLabel.Text = "Instructor";
            // 
            // uxInstructorBox
            // 
            this.uxInstructorBox.Font = new System.Drawing.Font("Microsoft Sans Serif", 12F);
            this.uxInstructorBox.Location = new System.Drawing.Point(603, 547);
            this.uxInstructorBox.Name = "uxInstructorBox";
            this.uxInstructorBox.Size = new System.Drawing.Size(312, 30);
            this.uxInstructorBox.TabIndex = 11;
            this.uxInstructorBox.Text = "(e.g. Higgins, Daniel A)";
            // 
            // uxExactlyAsInLabel
            // 
            this.uxExactlyAsInLabel.AutoSize = true;
            this.uxExactlyAsInLabel.Location = new System.Drawing.Point(716, 526);
            this.uxExactlyAsInLabel.Name = "uxExactlyAsInLabel";
            this.uxExactlyAsInLabel.Size = new System.Drawing.Size(183, 17);
            this.uxExactlyAsInLabel.TabIndex = 12;
            this.uxExactlyAsInLabel.Text = "Exactly as in course catalog";
            // 
            // uxNarrowResultsButton
            // 
            this.uxNarrowResultsButton.Font = new System.Drawing.Font("Microsoft Sans Serif", 12F);
            this.uxNarrowResultsButton.Location = new System.Drawing.Point(963, 540);
            this.uxNarrowResultsButton.Name = "uxNarrowResultsButton";
            this.uxNarrowResultsButton.Size = new System.Drawing.Size(138, 44);
            this.uxNarrowResultsButton.TabIndex = 13;
            this.uxNarrowResultsButton.Text = "Use This";
            this.uxNarrowResultsButton.UseVisualStyleBackColor = true;
            this.uxNarrowResultsButton.Click += new System.EventHandler(this.uxNarrowResultsButton_Click);
            // 
            // uxSectionTypeLabel
            // 
            this.uxSectionTypeLabel.AutoSize = true;
            this.uxSectionTypeLabel.Font = new System.Drawing.Font("Microsoft Sans Serif", 12F);
            this.uxSectionTypeLabel.Location = new System.Drawing.Point(12, 589);
            this.uxSectionTypeLabel.Name = "uxSectionTypeLabel";
            this.uxSectionTypeLabel.Size = new System.Drawing.Size(128, 25);
            this.uxSectionTypeLabel.TabIndex = 14;
            this.uxSectionTypeLabel.Text = "Section Type";
            // 
            // uxSectionTypeBox
            // 
            this.uxSectionTypeBox.Font = new System.Drawing.Font("Microsoft Sans Serif", 12F);
            this.uxSectionTypeBox.Location = new System.Drawing.Point(13, 617);
            this.uxSectionTypeBox.Name = "uxSectionTypeBox";
            this.uxSectionTypeBox.Size = new System.Drawing.Size(275, 30);
            this.uxSectionTypeBox.TabIndex = 15;
            // 
            // label1
            // 
            this.label1.AutoSize = true;
            this.label1.Location = new System.Drawing.Point(136, 597);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(152, 17);
            this.label1.TabIndex = 16;
            this.label1.Text = "Only if Lab, Rec, or Qz";
            // 
            // uxSectionNumberLabel
            // 
            this.uxSectionNumberLabel.AutoSize = true;
            this.uxSectionNumberLabel.Font = new System.Drawing.Font("Microsoft Sans Serif", 12F);
            this.uxSectionNumberLabel.Location = new System.Drawing.Point(367, 590);
            this.uxSectionNumberLabel.Name = "uxSectionNumberLabel";
            this.uxSectionNumberLabel.Size = new System.Drawing.Size(152, 25);
            this.uxSectionNumberLabel.TabIndex = 17;
            this.uxSectionNumberLabel.Text = "Section Number";
            // 
            // uxSectionNumberBox
            // 
            this.uxSectionNumberBox.Font = new System.Drawing.Font("Microsoft Sans Serif", 12F);
            this.uxSectionNumberBox.Location = new System.Drawing.Point(372, 617);
            this.uxSectionNumberBox.Name = "uxSectionNumberBox";
            this.uxSectionNumberBox.Size = new System.Drawing.Size(206, 30);
            this.uxSectionNumberBox.TabIndex = 18;
            // 
            // ScheduleViewer
            // 
            this.AcceptButton = this.uxNarrowResultsButton;
            this.AutoScaleDimensions = new System.Drawing.SizeF(8F, 16F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(1151, 662);
            this.Controls.Add(this.uxSectionNumberBox);
            this.Controls.Add(this.uxSectionNumberLabel);
            this.Controls.Add(this.label1);
            this.Controls.Add(this.uxSectionTypeBox);
            this.Controls.Add(this.uxSectionTypeLabel);
            this.Controls.Add(this.uxNarrowResultsButton);
            this.Controls.Add(this.uxExactlyAsInLabel);
            this.Controls.Add(this.uxInstructorBox);
            this.Controls.Add(this.uxInstructorLabel);
            this.Controls.Add(this.uxStartTimeBox);
            this.Controls.Add(this.uxStartTimeLabel);
            this.Controls.Add(this.uxCourseNumberBox);
            this.Controls.Add(this.uxCourseNumberLabel);
            this.Controls.Add(this.uxNarrowResultsLabel);
            this.Controls.Add(this.uxDetailsLabel);
            this.Controls.Add(this.uxDetailsButton);
            this.Controls.Add(this.uxSchedulesLabel);
            this.Controls.Add(this.uxDetailsBox);
            this.Controls.Add(this.uxSchedulesBox);
            this.Name = "ScheduleViewer";
            this.Text = "Schedule Viewer";
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.ListBox uxSchedulesBox;
        private System.Windows.Forms.ListBox uxDetailsBox;
        private System.Windows.Forms.Label uxSchedulesLabel;
        private System.Windows.Forms.Button uxDetailsButton;
        private System.Windows.Forms.Label uxDetailsLabel;
        private System.Windows.Forms.Label uxNarrowResultsLabel;
        private System.Windows.Forms.Label uxCourseNumberLabel;
        private System.Windows.Forms.TextBox uxCourseNumberBox;
        private System.Windows.Forms.Label uxStartTimeLabel;
        private System.Windows.Forms.TextBox uxStartTimeBox;
        private System.Windows.Forms.Label uxInstructorLabel;
        private System.Windows.Forms.TextBox uxInstructorBox;
        private System.Windows.Forms.Label uxExactlyAsInLabel;
        private System.Windows.Forms.Button uxNarrowResultsButton;
        private System.Windows.Forms.Label uxSectionTypeLabel;
        private System.Windows.Forms.TextBox uxSectionTypeBox;
        private System.Windows.Forms.Label label1;
        private System.Windows.Forms.Label uxSectionNumberLabel;
        private System.Windows.Forms.TextBox uxSectionNumberBox;
    }
}