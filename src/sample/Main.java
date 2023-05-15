package sample;
import javafx.application.Application;
import javafx.scene.*;
import javafx.scene.image.*;
import javafx.scene.media.*;
import javafx.stage.Stage;
import java.nio.file.*;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        music();
        Controller2 c = new Controller2(primaryStage);
        Scene scene = new Scene(c.createContent(),700,450);
        primaryStage.setTitle("Snakes And Ladders");
        primaryStage.setScene(scene);
        primaryStage.getIcons().add(new Image("icon.jfif"));
        primaryStage.setResizable(false);
        primaryStage.show();
    }
    MediaPlayer mp;
    private void music(){
        Media h = new Media(Paths.get("GameAudio.mp3").toUri().toString());
        mp = new MediaPlayer(h);
        mp.play();
    }
    public static void main(String[] args) {
        launch(args);
    }
}
