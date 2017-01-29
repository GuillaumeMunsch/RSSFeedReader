package fr.socialhive.rssninja;

import fr.socialhive.rssninja.http.WebServiceSingleton;
import fr.socialhive.rssninja.models.RSSFeed;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AddModalController implements Initializable {

    @FXML
    private Button buttonAdd;
    @FXML
    private TextField inputLabel;
    @FXML
    private TextField inputURL;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    @FXML
    private void addRSSFeed(ActionEvent event) throws IOException {
        Call<RSSFeed> call = WebServiceSingleton.getInstance().getService()
                .addFeed(new RSSFeed(this.inputLabel.getText(), this.inputURL.getText()));
        call.enqueue(new Callback<RSSFeed>() {

            @Override
            public void onResponse(Call<RSSFeed> call, Response<RSSFeed> response) {
                System.out.println("Code: " + response.code());
                if (response.code() == 200) {
                    System.out.println("Added");
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            Stage stage = (Stage) buttonAdd.getScene().getWindow();
                            stage.close();
                        }
                    });
                } else {
                    System.out.println("WTF");
                }
            }

            @Override
            public void onFailure(Call<RSSFeed> call, Throwable t) {
                t.printStackTrace();
            }

        });
    }
}
