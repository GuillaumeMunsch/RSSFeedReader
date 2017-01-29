package com.munsch.feedreader;

/*5
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Munsch
 */
public class MainViewController implements Initializable {
    @FXML
    private Button logoutButton;
    
    @FXML
    private TextField usernameInput;
    
    @FXML
    private TextField passwordInput;
    
    @FXML
    private void handleButtonAction(ActionEvent event) throws IOException {
        Stage stage;
        Parent root;
       if (event.getSource() == logoutButton) {
           stage = (Stage) logoutButton.getScene().getWindow();
           root = FXMLLoader.load(getClass().getResource("/fxml/Connect.fxml"));
           Scene scene = new Scene(root);
           stage.setScene(scene);
           stage.show();
       }
    }
    
        @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
}
