namespace CourseScheduleScraper
{
    partial class InitializationDialog
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
            this.uxYearLabel = new System.Windows.Forms.Label();
            this.uxSemesterLabel = new System.Windows.Forms.Label();
            this.uxYearBox = new System.Windows.Forms.TextBox();
            this.uxSemesterBox = new System.Windows.Forms.TextBox();
            this.uxLoadButton = new System.Windows.Forms.Button();
            this.uxChooseLabel = new System.Windows.Forms.Label();
            this.uxReloadDatabaseCheckBox = new System.Windows.Forms.CheckBox();
            this.uxNoteLabel = new System.Windows.Forms.Label();
            this.SuspendLayout();
            // 
            // uxYearLabel
            // 
            this.uxYearLabel.AutoSize = true;
            this.uxYearLabel.Font = new System.Drawing.Font("Microsoft Sans Serif", 12F);
            this.uxYearLabel.Location = new System.Drawing.Point(10, 48);
            this.uxYearLabel.Name = "uxYearLabel";
            this.uxYearLabel.Size = new System.Drawing.Size(53, 25);
            this.uxYearLabel.TabIndex = 0;
            this.uxYearLabel.Text = "Year";
            // 
            // uxSemesterLabel
            // 
            this.uxSemesterLabel.AutoSize = true;
            this.uxSemesterLabel.Font = new System.Drawing.Font("Microsoft Sans Serif", 12F);
            this.uxSemesterLabel.Location = new System.Drawing.Point(12, 102);
            this.uxSemesterLabel.Name = "uxSemesterLabel";
            this.uxSemesterLabel.Size = new System.Drawing.Size(96, 25);
            this.uxSemesterLabel.TabIndex = 1;
            this.uxSemesterLabel.Text = "Semester";
            // 
            // uxYearBox
            // 
            this.uxYearBox.Font = new System.Drawing.Font("Microsoft Sans Serif", 12F);
            this.uxYearBox.Location = new System.Drawing.Point(114, 48);
            this.uxYearBox.Name = "uxYearBox";
            this.uxYearBox.Size = new System.Drawing.Size(346, 30);
            this.uxYearBox.TabIndex = 2;
            this.uxYearBox.Text = "(Enter all four digits)";
            // 
            // uxSemesterBox
            // 
            this.uxSemesterBox.Font = new System.Drawing.Font("Microsoft Sans Serif", 12F);
            this.uxSemesterBox.Location = new System.Drawing.Point(114, 99);
            this.uxSemesterBox.Name = "uxSemesterBox";
            this.uxSemesterBox.Size = new System.Drawing.Size(346, 30);
            this.uxSemesterBox.TabIndex = 3;
            this.uxSemesterBox.Text = "(Spring, Summer, Fall)";
            // 
            // uxLoadButton
            // 
            this.uxLoadButton.DialogResult = System.Windows.Forms.DialogResult.OK;
            this.uxLoadButton.Font = new System.Drawing.Font("Microsoft Sans Serif", 12F);
            this.uxLoadButton.Location = new System.Drawing.Point(301, 135);
            this.uxLoadButton.Name = "uxLoadButton";
            this.uxLoadButton.Size = new System.Drawing.Size(159, 43);
            this.uxLoadButton.TabIndex = 4;
            this.uxLoadButton.Text = "Load Schedule";
            this.uxLoadButton.UseVisualStyleBackColor = true;
            this.uxLoadButton.Click += new System.EventHandler(this.uxLoadButton_Click);
            // 
            // uxChooseLabel
            // 
            this.uxChooseLabel.AutoSize = true;
            this.uxChooseLabel.Font = new System.Drawing.Font("Microsoft Sans Serif", 12F);
            this.uxChooseLabel.Location = new System.Drawing.Point(10, 9);
            this.uxChooseLabel.Name = "uxChooseLabel";
            this.uxChooseLabel.Size = new System.Drawing.Size(244, 25);
            this.uxChooseLabel.TabIndex = 5;
            this.uxChooseLabel.Text = "Choose a schedule to load";
            // 
            // uxReloadDatabaseCheckBox
            // 
            this.uxReloadDatabaseCheckBox.AutoSize = true;
            this.uxReloadDatabaseCheckBox.Font = new System.Drawing.Font("Microsoft Sans Serif", 12F);
            this.uxReloadDatabaseCheckBox.Location = new System.Drawing.Point(15, 145);
            this.uxReloadDatabaseCheckBox.Name = "uxReloadDatabaseCheckBox";
            this.uxReloadDatabaseCheckBox.Size = new System.Drawing.Size(184, 29);
            this.uxReloadDatabaseCheckBox.TabIndex = 6;
            this.uxReloadDatabaseCheckBox.Text = "Reload Database";
            this.uxReloadDatabaseCheckBox.UseVisualStyleBackColor = true;
            // 
            // uxNoteLabel
            // 
            this.uxNoteLabel.AutoSize = true;
            this.uxNoteLabel.Location = new System.Drawing.Point(5, 181);
            this.uxNoteLabel.Name = "uxNoteLabel";
            this.uxNoteLabel.Size = new System.Drawing.Size(351, 17);
            this.uxNoteLabel.TabIndex = 7;
            this.uxNoteLabel.Text = "Note: Always loads if this semester hasn\'t been loaded";
            // 
            // InitializationDialog
            // 
            this.AcceptButton = this.uxLoadButton;
            this.AutoScaleDimensions = new System.Drawing.SizeF(8F, 16F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(466, 204);
            this.Controls.Add(this.uxNoteLabel);
            this.Controls.Add(this.uxReloadDatabaseCheckBox);
            this.Controls.Add(this.uxChooseLabel);
            this.Controls.Add(this.uxLoadButton);
            this.Controls.Add(this.uxSemesterBox);
            this.Controls.Add(this.uxYearBox);
            this.Controls.Add(this.uxSemesterLabel);
            this.Controls.Add(this.uxYearLabel);
            this.FormBorderStyle = System.Windows.Forms.FormBorderStyle.FixedDialog;
            this.Name = "InitializationDialog";
            this.Text = "Open Schedule";
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.Label uxYearLabel;
        private System.Windows.Forms.Label uxSemesterLabel;
        private System.Windows.Forms.TextBox uxYearBox;
        private System.Windows.Forms.TextBox uxSemesterBox;
        private System.Windows.Forms.Button uxLoadButton;
        private System.Windows.Forms.Label uxChooseLabel;
        private System.Windows.Forms.CheckBox uxReloadDatabaseCheckBox;
        private System.Windows.Forms.Label uxNoteLabel;
    }
}