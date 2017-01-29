package com.munsch.feedreader;

import com.munsch.feedreader.http.ReaderService;
import com.munsch.feedreader.http.WebServiceSingleton;
import com.munsch.feedreader.models.RespAuth;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ConnectController implements Initializable {

    @FXML
    private Button loginButton;

    @FXML
    private TextField usernameInput;

    @FXML
    private TextField passwordInput;

    Stage stage;
    Parent root;

    @FXML
    private void handleButtonAction(ActionEvent event) throws IOException {
        if (event.getSource() == loginButton) {
            System.out.println(usernameInput.getText());
            Call<RespAuth> call = WebServiceSingleton.getInstance().getService()
                    .login(new ReaderService.LoginInformation(usernameInput.getText(), passwordInput.getText()));
            call.enqueue(new Callback<RespAuth>() {
                @Override
                public void onResponse(Call<RespAuth> call, Response<RespAuth> response) {
                    System.out.println("Code: " + response.code());
                    if (response.code() == 200) {
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    stage = (Stage) loginButton.getScene().getWindow();
                                    root = FXMLLoader.load(getClass().getResource("/fxml/MainView.fxml"));
                                    Scene scene = new Scene(root);
                                    stage.setScene(scene);
                                    stage.show();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    } else {
                        System.out.println("WTF");
                    }
                }

                @Override
                public void onFailure(Call<RespAuth> call, Throwable t) {
                    t.printStackTrace();
                }

            });

        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
}
