package sample;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.xml.crypto.Data;
import java.io.File;
import java.io.IOException;


public class Controller {

    @FXML private TableView tableView;
    @FXML private MenuBar menuBar;

    @FXML private TableColumn<Object, Object> studentID;
    @FXML private TableColumn<Object, Object> assignmentMark;
    @FXML private TableColumn<Object, Object> midtermMark;
    @FXML private TableColumn<Object, Object> finalExamMark;
    @FXML private TableColumn<Object, Object> finalMark;
    @FXML private TableColumn<Object, Object> letterGrade;

    @FXML private MenuItem open;
    @FXML private MenuItem exit;

    @FXML private TextField SID;
    @FXML private TextField assignment;
    @FXML private TextField midterm;
    @FXML private TextField exam;
    //private TableView<StudentRecord> student;

    @FXML
    public void initialize() {
        studentID.setCellValueFactory(new PropertyValueFactory<>("studentID"));
        assignmentMark.setCellValueFactory(new PropertyValueFactory<>("assignmentMark"));
        midtermMark.setCellValueFactory(new PropertyValueFactory<>("midtermMark"));
        finalExamMark.setCellValueFactory(new PropertyValueFactory<>("finalExamMark"));
        finalMark.setCellValueFactory(new PropertyValueFactory<>("finalMark"));
        letterGrade.setCellValueFactory(new PropertyValueFactory<>("letterGrade"));
        tableView.setItems(DataSource.getAllMarks());
    }

    @FXML
    private void clearTable(ActionEvent event){
        //wipes the current tableview's data
        //by wiping the datasource's data and filename to null
        DataSource.wipe();
        initialize();
    }

    @FXML
    private void exit() {
        Platform.exit();
    }

    @FXML
    private void addRecord(ActionEvent event) {
        String studentID = SID.getText();
        float assignmentMark = Float.valueOf(assignment.getText());
        float midtermMark = Float.valueOf(midterm.getText());
        float examMark = Float.valueOf(exam.getText());
        StudentRecord student = new StudentRecord(studentID, assignmentMark, midtermMark, examMark);

        DataSource.addRecord(student);
        initialize();
    }

    @FXML
    private void saveAs(ActionEvent event) throws  IOException{
        FileChooser chooser = new FileChooser();
        chooser.setInitialDirectory(new File("."));
        //Grabbing the scene from the menuitem
        //It's an absolute path by default!
        File file = chooser.showOpenDialog(open.getParentPopup().getScene().getWindow());
        System.out.println(file.toString());

        //set the DataSource class's currentFileName
        DataSource.setCurrentFileName(file);
        DataSource.save();
    }

    @FXML
    private void save(ActionEvent event) throws IOException {
        DataSource.save();
    }

    @FXML
    private void open(ActionEvent event) {

        FileChooser chooser = new FileChooser();
        chooser.setInitialDirectory(new File("."));
        //Grabbing the scene from the menuitem
        //It's an absolute path by default!
        File file = chooser.showOpenDialog(open.getParentPopup().getScene().getWindow());
        System.out.println(file.toString());

        //set the DataSource class's currentFileName
        DataSource.setCurrentFileName(file);
        DataSource.load();
    }

    @FXML
    private void load(ActionEvent event){

    }

}