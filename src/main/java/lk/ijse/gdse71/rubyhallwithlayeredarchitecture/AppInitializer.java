package lk.ijse.gdse71.rubyhallwithlayeredarchitecture;

import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class AppInitializer extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/LoadingScreenView.fxml"))));
        stage.setTitle("Welcome");
        stage.show();

        Task<Scene> loadingTask = new Task<>() {
            @Override
            protected Scene call() throws Exception {
                FXMLLoader fxmlLoader = new FXMLLoader(AppInitializer.class.getResource("/view/Login.fxml"));
                return new Scene(fxmlLoader.load());
            }
        };

        loadingTask.setOnSucceeded(event -> {
            Scene value = loadingTask.getValue();

            stage.setTitle("Ruby Hall");
            stage.getIcons().add(new Image(getClass().getResourceAsStream("/images/app_icon.png")));

            stage.setScene(value);
        });

        loadingTask.setOnFailed(event -> {
            System.err.println("Failed to load the Login page.");
        });

        new Thread(loadingTask).start();
    }

    public static void main(String[] args) {
        launch();
    }
}