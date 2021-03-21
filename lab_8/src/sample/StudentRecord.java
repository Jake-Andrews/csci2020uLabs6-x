package sample;

public class StudentRecord {
    public String studentID;
    public float midtermMark;
    public float assignmentMark;
    public float finalExamMark;
    public float finalMark;
    public char letterGrade;

    public StudentRecord (String id, float assignment, float midterm, float exam) {
        studentID = id;
        midtermMark = midterm;
        assignmentMark = assignment;
        finalExamMark = exam;
        finalMark = (assignmentMark*0.20f + midtermMark*0.30f + finalExamMark*0.50f);
        decideLetterGrade();
    }

    public void decideLetterGrade() {
        if (this.finalMark >= 80f) {
            this.letterGrade = 'A';
        }
        else if (this.finalMark >= 70f) {
            this.letterGrade = 'B';
        }
        else if (this.finalMark >= 60f) {
            this.letterGrade = 'C';
        }
        else if (this.finalMark >= 50f) {
            this.letterGrade = 'D';
        }
        else {
            this.letterGrade = 'F';
        }
    }

    public String getStudentID() {
        return this.studentID;
    }
    public float getMidtermMark() {
        return this.midtermMark;
    }
    public float getAssignmentMark() {
        return this.assignmentMark;
    }
    public float getFinalExamMark() {
        return this.finalExamMark;
    }
    public float getFinalMark() {
        return this.finalMark;
    }
    public char getLetterGrade() {
        return this.letterGrade;
    }
}
