package sample;

import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.scene.text.*;
import javafx.scene.text.Font;
import javafx.stage.*;


import static javafx.scene.paint.Color.WHITE;

public class Controller2 {

    private final Button Start;
    private Scene scene;
    private final Stage primaryStage;

    Controller2(Stage primaryStage){
       Start = new Button("START");
       Start.setPrefSize(70,35);
       Start.setTranslateX(330);
       Start.setTranslateY(410);
       Start.setStyle("-fx-background-color:#4a9c33 ;-fx-border-color: #ffffff; -fx-border-radius: 3");
       Font font = Font.font("Agency FB", FontWeight.BOLD,17);
       Start.setTextFill(WHITE);
       Start.setFont(font);
       this.primaryStage = primaryStage;
    }

    protected Parent createContent(){
        Pane root = new Pane();
        Group s = new Group();
        root.setId("pane");
        ImageView bgImage = new ImageView("background_image.jpeg");
        bgImage.setFitHeight(500);
        bgImage.setFitWidth(820);
        bgImage.setX(-40);
        bgImage.setY(-35);
        s.getChildren().addAll(bgImage,Start);
        Start.setOnAction(actionEvent ->{
            Controller c2 = new Controller(primaryStage);
            scene = new Scene(c2.createContent());
            c2.setGameStart();
            primaryStage.setScene(scene);
        });

        root.getChildren().add(s);
        return root;
    }

}
