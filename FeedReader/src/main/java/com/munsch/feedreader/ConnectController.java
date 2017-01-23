package com.munsch.feedreader;

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
import javafx.stage.Stage;

public class ConnectController implements Initializable {
    
    @FXML
    private Button connectBtn;
    
    @FXML
    private void handleButtonAction(ActionEvent event) throws IOException {
        Stage stage;
        Parent root;
       if (event.getSource() == connectBtn) {
           stage = (Stage) connectBtn.getScene().getWindow();
           root = FXMLLoader.load(getClass().getResource("/fxml/MainView.fxml"));
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
