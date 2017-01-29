package fr.socialhive.rssninja;

/*5
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import fr.socialhive.rssninja.http.ReaderService;
import fr.socialhive.rssninja.http.WebServiceSingleton;
import fr.socialhive.rssninja.models.JSendResp;
import fr.socialhive.rssninja.models.RSSFeed;
import fr.socialhive.rssninja.models.RespAuth;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
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
    private WebView mainWebView;
    @FXML
    private ProgressIndicator spinner;

    @FXML
    private void logout(ActionEvent event) throws IOException {
        Call<JSendResp> call = WebServiceSingleton.getInstance().getService()
                .logout();
        call.enqueue(new Callback<JSendResp>() {
            @Override
            public void onResponse(Call<JSendResp> call, Response<JSendResp> response) {
                System.out.println("Code: " + response.code());
                if (response.code() == 200) {
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                Stage stage = (Stage) logoutButton.getScene().getWindow();
                                Parent root = FXMLLoader.load(getClass().getResource("/fxml/Connect.fxml"));
                                Scene scene = new Scene(root);
                                stage.setScene(scene);
                                stage.show();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                } else {
                    System.out.println("WTF");
                }
            }

            @Override
            public void onFailure(Call<JSendResp> call, Throwable t) {
                t.printStackTrace();
            }

        });
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

    private void displayInWebView(String url) {
        spinner.setVisible(true);
        WebEngine engine = mainWebView.getEngine();
        engine.load(url);
    }

    @FXML
    private void deleteFeed(ActionEvent event) throws IOException {
        Long id = new Long(0); // TODO: get id at onClick munsch
        Call<List<RSSFeed>> call = WebServiceSingleton.getInstance().getService()
                .removeFeed(id);
        call.enqueue(new Callback<List<RSSFeed>>() {

            @Override
            public void onResponse(Call<List<RSSFeed>> call, Response<List<RSSFeed>> response) {
                System.out.println("Code: " + response.code());
                if (response.code() == 200) {
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            // TODO: Update list by removing the feed
                        }
                    });
                } else {
                    System.out.println("WTF");
                }
            }

            @Override
            public void onFailure(Call<List<RSSFeed>> call, Throwable t) {
                t.printStackTrace();
            }

        });
    }
    
        @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
}
