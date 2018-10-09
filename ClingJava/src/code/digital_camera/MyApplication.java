package code.digital_camera;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import code.digital_camera.controllers.Controller;
import code.digital_camera.controllers.ControllerInterface;
import code.digital_camera.views.View;

public class MyApplication extends Application{
    View view;

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/resources/view.fxml"));
        Parent root = fxmlLoader.load();

        // controller instance to init model attribute
        view = fxmlLoader.<View>getController();
        ControllerInterface controller = new Controller(view);
        view.setController(controller);

        primaryStage.setTitle("Audio System");
        primaryStage.setScene(new Scene(root, 600, 300));
        primaryStage.setResizable(false);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
