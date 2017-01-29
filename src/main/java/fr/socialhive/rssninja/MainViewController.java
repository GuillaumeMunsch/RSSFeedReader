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
import fr.socialhive.rssninja.models.Feed;
import fr.socialhive.rssninja.models.JSendResp;
import fr.socialhive.rssninja.models.RSSFeed;
import fr.socialhive.rssninja.models.RespAuth;
import fr.socialhive.rssninja.utils.Utils;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
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
    private ListView feedsListView;
    @FXML
    private ListView feedItemsListView;
    @FXML
    private WebView webView;
    @FXML
    private Button buttonAdd;
    @FXML
    private TextField inputLabel;
    @FXML
    private TextField inputURL;

    private List<RSSFeed> feedsList;
    private Feed feedItemsList;

    public List<RSSFeed> getFeedsList() {
        return feedsList;
    }

    public void setFeedsList(List<RSSFeed> feedsList) {
        this.feedsList = feedsList;
    }

    public ListView getFeedsListView() {
        return feedsListView;
    }

    public void setFeedsListView(ListView feedsListView) {
        this.feedsListView = feedsListView;
    }

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
            URL location = getClass().getResource("/fxml/AddModal.fxml");
            FXMLLoader loader = new FXMLLoader(location);
            loader.setLocation(location);
            root = loader.load();
            MainViewController c = loader.getController();
            c.setFeedsListView(feedsListView);
            c.setFeedsList(feedsList);
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Add a feed");
            stage.setResizable(false);
            stage.initOwner(((Node)event.getSource()).getScene().getWindow());
            stage.show();
        }
    }

    @FXML
    private void deleteFeed(int id) {
        Call<Void> call = WebServiceSingleton.getInstance().getService().removeFeed(id);
        call.enqueue(new Callback<Void>() {

            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                System.out.println("Code: " + response.code());
                if (response.code() == 200) {
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            feedsListView.getItems().remove(feedsListView.getSelectionModel().getSelectedIndex());
                            feedsList.remove(feedsListView.getSelectionModel().getSelectedIndex());
                        }
                    });
                } else {
                    System.out.println("WTF: " + response.toString());
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                t.printStackTrace();
            }

        });
    }

    private void fetchFeedItems(int id) {
        Call<Feed> call = WebServiceSingleton.getInstance().getService().getFeedItems(id);
        call.enqueue(new Callback<Feed>() {
            @Override
            public void onResponse(Call<Feed> call, final Response<Feed> response) {
                System.out.println("Code: " + response.code());
                if (response.code() == 200) {
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                feedItemsList = response.body();
                                ObservableList<String> items = FXCollections.observableArrayList((List<String>) (Object) Utils.transform(feedItemsList.getItems(), "title"));
                                feedItemsListView.setItems(items);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });
                } else {
                    feedItemsListView.getItems().clear();
                    System.out.println("Error");
                }
            }

            @Override
            public void onFailure(Call<Feed> call, Throwable t) {
                t.printStackTrace();
            }

        });
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        System.out.println(url.getFile());
        if (url.getFile().contains("AddModal.fxml")) {
            return;
        }
        Call<List<RSSFeed>> call = WebServiceSingleton.getInstance().getService().getFeeds();
        call.enqueue(new Callback<List<RSSFeed>>() {
            @Override
            public void onResponse(Call<List<RSSFeed>> call, final Response<List<RSSFeed>> response) {
                System.out.println("Code: " + response.code());
                if (response.code() == 200) {
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                feedsList = response.body();
                                ObservableList<String> items = FXCollections.observableArrayList((List<String>) (Object) Utils.transform(feedsList, "name"));
                                feedsListView.setItems(items);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });
                } else {
                    System.out.println("Error");
                }
            }

            @Override
            public void onFailure(Call<List<RSSFeed>> call, Throwable t) {
                t.printStackTrace();
            }

        });
        feedsListView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                fetchFeedItems(feedsList.get(feedsListView.getSelectionModel().getSelectedIndex()).getId().intValue());
                webView.getEngine().loadContent("");
            }
        });
        feedsListView.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if(event.getCode() == KeyCode.DELETE); {
                    System.out.println("Value: " + feedsList.get(feedsListView.getSelectionModel().getSelectedIndex()).getId().intValue());
                    deleteFeed(feedsList.get(feedsListView.getSelectionModel().getSelectedIndex()).getId().intValue());
                }
            }
        });
        feedItemsListView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                webView.getEngine().load(feedItemsList.getItems().get(feedItemsListView.getSelectionModel().getSelectedIndex()).getLink());
            }
        });
    }

    @FXML
    private void modalAddClick(ActionEvent event) throws IOException {
        System.out.println(feedsList);
        Call<RSSFeed> call = WebServiceSingleton.getInstance().getService()
                .addFeed(new RSSFeed(this.inputLabel.getText(), this.inputURL.getText()));
        call.enqueue(new Callback<RSSFeed>() {

            @Override
            public void onResponse(Call<RSSFeed> call, final Response<RSSFeed> response) {
                System.out.println("Code: " + response.code());
                System.out.println(feedsListView);
                if (response.code() == 200) {
                    System.out.println("Added");
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            System.out.println(feedsListView);
                            feedsListView.getItems().add(response.body().getName());
                            feedsList.add(response.body());
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
