import contracts.Screens;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("views/startScreen.fxml"));
        primaryStage.setTitle(Screens.START_SCREEN);
        primaryStage.setScene(new Scene(root, 532, 400));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
