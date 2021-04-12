package client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.w3c.dom.Text;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Controller {
    private BlackjackClient connection;

    @FXML private Button exit;
    @FXML private Button send;

    @FXML private TextField username;
    @FXML private TextField message;

    @FXML
    public void exit(ActionEvent event) {
        //Tell the server were done.
        System.out.println(connection.sendMessage("\\q"));
        connection.close();
        System.exit(0);
    }

    @FXML
    public void send(ActionEvent e) throws IOException {
        //Need the text inside the label
        String name = username.getText();
        String text = message.getText();
        System.out.println("Username: " + name + " ----- Message: " + text);
        System.out.println(connection.sendMessage("SEND," + name + "," + text));
    }

    @FXML
    public void initialize() throws FileNotFoundException {
        try {
            connection = BlackjackClient.connect("localhost", 8001);
            if (connection != null) {
                System.out.println("Connected");
            } else {
                System.err.println("No connection made.");
            }
        } catch (NumberFormatException e) {
            System.err.println(e.getMessage());
        }
    }
}