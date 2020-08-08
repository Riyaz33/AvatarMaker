import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    static double WINDOW_START_HEIGHT = 800;
    static double WINDOW_START_WIDTH = 1200;

    static double WINDOW_MIN_HEIGHT = 400;
    static double WINDOW_MIN_WIDTH = 800;
    private Controller controller;

    @Override
    public void start(Stage stage) throws Exception{

        stage.setResizable(true);

        Model model = new Model();

        FXMLLoader loader = new FXMLLoader((getClass().getResource("window.fxml")));
        Parent root = loader.load();
        this.controller = loader.getController();
        this.controller.model = model;
        model.controller = this.controller;

        stage.setTitle("Avatar Maker");
        Scene rootScene = new Scene(root, WINDOW_START_WIDTH, WINDOW_START_HEIGHT);
        stage.setScene(rootScene);
        stage.setMinHeight(WINDOW_MIN_HEIGHT);
        stage.setMinWidth(WINDOW_MIN_WIDTH);
        stage.show();
        controller.initAvatar();
        controller.initHandlers();
        controller.controlsPane.getChildren().remove(1); //remove controls pane color picker
        controller.controlsPane.getChildren().remove(1); // remove spinner brows
        controller.controlsPane.getChildren().remove(1); // remove spinner eyes
        controller.controlsPane.getChildren().remove(1); // remove colorpicker 2
        controller.controlsPane.getChildren().remove(1); // remove colorpicker 3





    }


    public static void main(String[] args) {
        launch(args);

    }
}
