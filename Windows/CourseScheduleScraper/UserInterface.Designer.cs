namespace CourseScheduleScraper
{
    partial class UserInterface
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
            this.uxSelectedCoursesList = new System.Windows.Forms.ListBox();
            this.uxSearchResultsList = new System.Windows.Forms.ListBox();
            this.uxAddCourseLabel = new System.Windows.Forms.Label();
            this.uxSearchByLabel = new System.Windows.Forms.Label();
            this.uxCourseNumberLabel = new System.Windows.Forms.Label();
            this.uxCourseNumberBox = new System.Windows.Forms.TextBox();
            this.uxCourseNameLabel = new System.Windows.Forms.Label();
            this.uxCourseNameBox = new System.Windows.Forms.TextBox();
            this.uxSearchButton = new System.Windows.Forms.Button();
            this.uxCreateScheduleButton = new System.Windows.Forms.Button();
            this.uxClickCourseLabel = new System.Windows.Forms.Label();
            this.uxRemoveItemButon = new System.Windows.Forms.Button();
            this.SuspendLayout();
            // 
            // uxSelectedCoursesList
            // 
            this.uxSelectedCoursesList.DisplayMember = "AsString";
            this.uxSelectedCoursesList.Font = new System.Drawing.Font("Microsoft Sans Serif", 12F);
            this.uxSelectedCoursesList.FormattingEnabled = true;
            this.uxSelectedCoursesList.ItemHeight = 25;
            this.uxSelectedCoursesList.Location = new System.Drawing.Point(12, 262);
            this.uxSelectedCoursesList.Name = "uxSelectedCoursesList";
            this.uxSelectedCoursesList.Size = new System.Drawing.Size(389, 229);
            this.uxSelectedCoursesList.TabIndex = 0;
            // 
            // uxSearchResultsList
            // 
            this.uxSearchResultsList.DisplayMember = "AsString";
            this.uxSearchResultsList.Font = new System.Drawing.Font("Microsoft Sans Serif", 12F);
            this.uxSearchResultsList.FormattingEnabled = true;
            this.uxSearchResultsList.HorizontalScrollbar = true;
            this.uxSearchResultsList.ItemHeight = 25;
            this.uxSearchResultsList.Location = new System.Drawing.Point(417, 38);
            this.uxSearchResultsList.Name = "uxSearchResultsList";
            this.uxSearchResultsList.Size = new System.Drawing.Size(387, 429);
            this.uxSearchResultsList.TabIndex = 1;
            this.uxSearchResultsList.DoubleClick += new System.EventHandler(this.uxSearchResultsList_DoubleClick);
            // 
            // uxAddCourseLabel
            // 
            this.uxAddCourseLabel.AutoSize = true;
            this.uxAddCourseLabel.Font = new System.Drawing.Font("Microsoft Sans Serif", 12F);
            this.uxAddCourseLabel.Location = new System.Drawing.Point(12, 13);
            this.uxAddCourseLabel.Name = "uxAddCourseLabel";
            this.uxAddCourseLabel.Size = new System.Drawing.Size(117, 25);
            this.uxAddCourseLabel.TabIndex = 2;
            this.uxAddCourseLabel.Text = "Add Course";
            // 
            // uxSearchByLabel
            // 
            this.uxSearchByLabel.AutoSize = true;
            this.uxSearchByLabel.Font = new System.Drawing.Font("Microsoft Sans Serif", 12F);
            this.uxSearchByLabel.Location = new System.Drawing.Point(23, 50);
            this.uxSearchByLabel.Name = "uxSearchByLabel";
            this.uxSearchByLabel.Size = new System.Drawing.Size(101, 25);
            this.uxSearchByLabel.TabIndex = 4;
            this.uxSearchByLabel.Text = "Search by";
            // 
            // uxCourseNumberLabel
            // 
            this.uxCourseNumberLabel.AutoSize = true;
            this.uxCourseNumberLabel.Font = new System.Drawing.Font("Microsoft Sans Serif", 12F);
            this.uxCourseNumberLabel.Location = new System.Drawing.Point(30, 75);
            this.uxCourseNumberLabel.Name = "uxCourseNumberLabel";
            this.uxCourseNumberLabel.Size = new System.Drawing.Size(150, 25);
            this.uxCourseNumberLabel.TabIndex = 7;
            this.uxCourseNumberLabel.Text = "Course Number";
            // 
            // uxCourseNumberBox
            // 
            this.uxCourseNumberBox.Font = new System.Drawing.Font("Microsoft Sans Serif", 12F);
            this.uxCourseNumberBox.Location = new System.Drawing.Point(30, 103);
            this.uxCourseNumberBox.Name = "uxCourseNumberBox";
            this.uxCourseNumberBox.Size = new System.Drawing.Size(372, 30);
            this.uxCourseNumberBox.TabIndex = 8;
            this.uxCourseNumberBox.Text = "(e.g. CHM110)";
            // 
            // uxCourseNameLabel
            // 
            this.uxCourseNameLabel.AutoSize = true;
            this.uxCourseNameLabel.Font = new System.Drawing.Font("Microsoft Sans Serif", 12F);
            this.uxCourseNameLabel.Location = new System.Drawing.Point(30, 153);
            this.uxCourseNameLabel.Name = "uxCourseNameLabel";
            this.uxCourseNameLabel.Size = new System.Drawing.Size(133, 25);
            this.uxCourseNameLabel.TabIndex = 9;
            this.uxCourseNameLabel.Text = "Course Name";
            // 
            // uxCourseNameBox
            // 
            this.uxCourseNameBox.Font = new System.Drawing.Font("Microsoft Sans Serif", 12F);
            this.uxCourseNameBox.Location = new System.Drawing.Point(30, 181);
            this.uxCourseNameBox.Name = "uxCourseNameBox";
            this.uxCourseNameBox.Size = new System.Drawing.Size(372, 30);
            this.uxCourseNameBox.TabIndex = 10;
            this.uxCourseNameBox.Text = "(e.g. General Chemistry)";
            // 
            // uxSearchButton
            // 
            this.uxSearchButton.Font = new System.Drawing.Font("Microsoft Sans Serif", 12F);
            this.uxSearchButton.Location = new System.Drawing.Point(259, 217);
            this.uxSearchButton.Name = "uxSearchButton";
            this.uxSearchButton.Size = new System.Drawing.Size(143, 39);
            this.uxSearchButton.TabIndex = 11;
            this.uxSearchButton.Text = "Search";
            this.uxSearchButton.UseVisualStyleBackColor = true;
            this.uxSearchButton.Click += new System.EventHandler(this.uxSearchButton_Click);
            // 
            // uxCreateScheduleButton
            // 
            this.uxCreateScheduleButton.Font = new System.Drawing.Font("Microsoft Sans Serif", 12F);
            this.uxCreateScheduleButton.Location = new System.Drawing.Point(608, 473);
            this.uxCreateScheduleButton.Name = "uxCreateScheduleButton";
            this.uxCreateScheduleButton.Size = new System.Drawing.Size(196, 46);
            this.uxCreateScheduleButton.TabIndex = 12;
            this.uxCreateScheduleButton.Text = "Create Schedule";
            this.uxCreateScheduleButton.UseVisualStyleBackColor = true;
            this.uxCreateScheduleButton.Click += new System.EventHandler(this.uxCreateScheduleButton_Click);
            // 
            // uxClickCourseLabel
            // 
            this.uxClickCourseLabel.AutoSize = true;
            this.uxClickCourseLabel.Font = new System.Drawing.Font("Microsoft Sans Serif", 12F);
            this.uxClickCourseLabel.Location = new System.Drawing.Point(412, 9);
            this.uxClickCourseLabel.Name = "uxClickCourseLabel";
            this.uxClickCourseLabel.Size = new System.Drawing.Size(270, 25);
            this.uxClickCourseLabel.TabIndex = 13;
            this.uxClickCourseLabel.Text = "Double click a course to add it";
            // 
            // uxRemoveItemButon
            // 
            this.uxRemoveItemButon.Font = new System.Drawing.Font("Microsoft Sans Serif", 12F);
            this.uxRemoveItemButon.Location = new System.Drawing.Point(240, 493);
            this.uxRemoveItemButon.Name = "uxRemoveItemButon";
            this.uxRemoveItemButon.Size = new System.Drawing.Size(162, 35);
            this.uxRemoveItemButon.TabIndex = 14;
            this.uxRemoveItemButon.Text = "Remove Course";
            this.uxRemoveItemButon.UseVisualStyleBackColor = true;
            this.uxRemoveItemButon.Click += new System.EventHandler(this.uxRemoveItemButon_Click);
            // 
            // UserInterface
            // 
            this.AcceptButton = this.uxSearchButton;
            this.AutoScaleDimensions = new System.Drawing.SizeF(8F, 16F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(816, 531);
            this.Controls.Add(this.uxRemoveItemButon);
            this.Controls.Add(this.uxClickCourseLabel);
            this.Controls.Add(this.uxCreateScheduleButton);
            this.Controls.Add(this.uxSearchButton);
            this.Controls.Add(this.uxCourseNameBox);
            this.Controls.Add(this.uxCourseNameLabel);
            this.Controls.Add(this.uxCourseNumberBox);
            this.Controls.Add(this.uxCourseNumberLabel);
            this.Controls.Add(this.uxSearchByLabel);
            this.Controls.Add(this.uxAddCourseLabel);
            this.Controls.Add(this.uxSearchResultsList);
            this.Controls.Add(this.uxSelectedCoursesList);
            this.Name = "UserInterface";
            this.Text = "Scheduler";
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.ListBox uxSelectedCoursesList;
        private System.Windows.Forms.ListBox uxSearchResultsList;
        private System.Windows.Forms.Label uxAddCourseLabel;
        private System.Windows.Forms.Label uxSearchByLabel;
        private System.Windows.Forms.Label uxCourseNumberLabel;
        private System.Windows.Forms.TextBox uxCourseNumberBox;
        private System.Windows.Forms.Label uxCourseNameLabel;
        private System.Windows.Forms.TextBox uxCourseNameBox;
        private System.Windows.Forms.Button uxSearchButton;
        private System.Windows.Forms.Button uxCreateScheduleButton;
        private System.Windows.Forms.Label uxClickCourseLabel;
        private System.Windows.Forms.Button uxRemoveItemButon;
    }
}

