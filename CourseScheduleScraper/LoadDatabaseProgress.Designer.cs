namespace CourseScheduleScraper
{
    partial class LoadDatabaseProgress
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
            this.uxProgressBar = new System.Windows.Forms.ProgressBar();
            this.uxLoadingLabel = new System.Windows.Forms.Label();
            this.SuspendLayout();
            // 
            // uxProgressBar
            // 
            this.uxProgressBar.Location = new System.Drawing.Point(13, 51);
            this.uxProgressBar.Name = "uxProgressBar";
            this.uxProgressBar.Size = new System.Drawing.Size(435, 35);
            this.uxProgressBar.TabIndex = 0;
            // 
            // uxLoadingLabel
            // 
            this.uxLoadingLabel.AutoSize = true;
            this.uxLoadingLabel.Font = new System.Drawing.Font("Microsoft Sans Serif", 12F);
            this.uxLoadingLabel.Location = new System.Drawing.Point(13, 13);
            this.uxLoadingLabel.Name = "uxLoadingLabel";
            this.uxLoadingLabel.Size = new System.Drawing.Size(186, 25);
            this.uxLoadingLabel.TabIndex = 1;
            this.uxLoadingLabel.Text = "Loading Database...";
            // 
            // LoadDatabaseProgress
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(8F, 16F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(460, 110);
            this.Controls.Add(this.uxLoadingLabel);
            this.Controls.Add(this.uxProgressBar);
            this.Name = "LoadDatabaseProgress";
            this.Text = "LoadDatabaseProgress";
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.ProgressBar uxProgressBar;
        private System.Windows.Forms.Label uxLoadingLabel;
    }
}