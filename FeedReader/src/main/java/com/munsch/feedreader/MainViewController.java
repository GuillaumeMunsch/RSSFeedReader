package com.munsch.feedreader;

/*5
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.munsch.feedreader.http.ReaderService;
import com.munsch.feedreader.http.WebServiceSingleton;
import com.munsch.feedreader.models.RSSFeed;
import com.munsch.feedreader.models.RespAuth;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * FXML Controller class
 *
 * @author Munsch
 */
public class MainViewController implements Initializable {
    @FXML
    private Button logoutButton;

    @FXML
    private Button addButton;
    
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

    @FXML
    private void handleAddRSSButton(ActionEvent event) throws IOException {
        Stage stage;
        Parent root;
        if (event.getSource() == addButton) {
            stage = new Stage();
            root = FXMLLoader.load(getClass().getResource("/fxml/AddModal.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Add a feed");
            stage.setResizable(false);
            stage.initOwner(((Node)event.getSource()).getScene().getWindow());
            stage.show();
        }
    }

    
        @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
}
