import Backend.Compress.Compression;
import UI.Scenes.TableScene;
import com.jfoenix.controls.JFXDecorator;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class Test extends Application {
    public static Stage stage = new Stage();
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws Exception {

                try {
                   Parent root = FXMLLoader.load(getClass().getResource("FXML/MainScene.fxml"));
                    JFXDecorator decorator = new JFXDecorator(stage,root);
                    decorator.getStylesheets().add("st.css");
                    stage.setScene(new Scene(decorator));
                    stage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }


    }

        }
