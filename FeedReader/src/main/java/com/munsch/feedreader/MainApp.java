package com.munsch.feedreader;

import com.guigarage.flatterfx.FlatterFX;
import com.munsch.feedreader.utils.PropsManager;
import java.io.IOException;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class MainApp extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/Connect.fxml"));
        
        Scene scene = new Scene(root);
//        scene.getStylesheets().add("/styles/Styles.css");
        
        stage.setTitle("RSSFeedReader");
        stage.setScene(scene);
        stage.show();
        FlatterFX.style();
        PropsManager.getInstance().setProperty("url", "http://socialhive.fr:4242/");
        try {
            System.out.println(PropsManager.getInstance().getProperty("url"));
        } catch (IOException ex) {
            System.out.println(ex.toString());
        }
    }

    /**
     * The main() method is ignored in correctly deployed JavaFX application.
     * main() serves only as fallback in case the application can not be
     * launched through deployment artifacts, e.g., in IDEs with limited FX
     * support. NetBeans ignores main().
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
