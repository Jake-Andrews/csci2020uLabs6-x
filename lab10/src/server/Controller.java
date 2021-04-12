package server;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

import java.io.IOException;
import java.lang.reflect.AccessibleObject;

public class Controller {

    private BlackjackServer server;

    @FXML private Button exit;
    @FXML private TextArea ta;

    public void initialize() {
        server = new BlackjackServer();
        server.start();
    }

    @FXML
    public void exit(ActionEvent e) throws IOException {
        server.serverStop();
        System.exit(0);
    }

    public void add(String userName, String message) {
        ta.appendText(userName + ": " + message + "\n");
    }
}
