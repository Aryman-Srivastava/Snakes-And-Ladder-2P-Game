package sample;

import javafx.animation.*;
import javafx.application.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.scene.text.*;
import javafx.stage.*;
import javafx.util.*;
import java.util.*;

import static javafx.scene.paint.Color.BLUE;

public class Controller{

    private int DiceVal;
    private final Label gameResult = new Label();

    private boolean player1Turn = true;
    private boolean player2Turn = true;

    private boolean gameStart;

    private final Group tileGroup = new Group();

    private final DieImages images = new DieImages();
    private final Die die = new Die(images.getImages());

    private final Image img2 = new Image("images.jpeg");
    private final ImageView bgImage2 = new ImageView();

    private final Image pl1l = new Image("p1l.png");
    private final Image pl1r = new Image("p1r.png");
    private final Image pl2l = new Image("p2l.png");
    private final Image pl2r = new Image("p2r.png");
    private final ImageView left = new ImageView();
    private final ImageView right = new ImageView();

    private final Image play1 = new Image("pl1.png");
    private final Image play2 = new Image("pl2.png");
    private final ImageView pl1 = new ImageView();

    private final ImageView pl2 = new ImageView();

    private final Image arr_img = new Image("arrow.png");
    private final ImageView arrow = new ImageView(arr_img);

    private final Stage primaryStage;

    private final Player p1 = new Player(pl1,35,648,1);
    private final Player p2 = new Player(pl2,24,664,2);

    Controller(Stage ps){
        this.primaryStage = ps;
    }

    protected void setGameStart() {
        this.gameStart = true;
    }

    protected Parent createContent(){
        Pane root  = new Pane();
        int tile_size = 64;
        root.setPrefSize((10 * tile_size), (10 * tile_size) + 180);
        root.getChildren().addAll(tileGroup);

        for(int i = 0; i< 10; i++) {
            for (int j = 0; j < 10; j++) {
                Tile tile = new Tile(tile_size, tile_size);
                tile.setTranslateX(j * tile_size);
                tile.setTranslateY(i * tile_size);
                tileGroup.getChildren().add(tile);
            }
        }

        pl1.setImage(play1);
        pl1.setFitHeight(45);
        pl1.setFitWidth(45);
        pl1.setPreserveRatio(true);

        pl2.setImage(play2);
        pl2.setFitHeight(45);
        pl2.setFitWidth(45);
        pl2.setPreserveRatio(true);

        left.setImage(pl1l);
        left.setFitWidth(100);
        left.setFitHeight(50);
        left.setX(185);
        left.setY(710);

        right.setImage(pl2r);
        right.setFitWidth(100);
        right.setFitHeight(50);
        right.setX(355);
        right.setY(710);

        arrow.setFitWidth(45);
        arrow.setFitHeight(45);
        arrow.setX(297);
        arrow.setY(655);
        TranslateArrow(arrow);

        Button button = new Button();
        button.setPrefSize(70,70);
        button.setMinSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
        button.setTranslateX(285);
        button.setTranslateY(700);
        die.setDieFace(6);
        button.setGraphic(die.getDieFace());
        button.setStyle( "-fx-background-color:rgb(35,111,189); -fx-border-color: rgb(35,111,189); -fx-border-radius: 5" );
        button.setContentDisplay(ContentDisplay.CENTER);
        button.setOnAction(actionEvent -> {
            if(gameStart){
                getDice(button);
            }
        });

        if (gameStart) {
            p1.setXPosition(35);
            p1.setYPosition(648);

            p1.Move();

            p2.setXPosition(24);
            p2.setYPosition(664);

            p2.Move();

            player1Turn = true;
        }



        Image img = new Image("snakes&ladder.jpg");
        ImageView bgImage = new ImageView();
        bgImage.setImage(img);
        bgImage.setFitHeight(640);
        bgImage.setFitWidth(640);
        bgImage.setSmooth(true);

        bgImage2.setImage(img2);
        bgImage2.setFitHeight(210);
        bgImage2.setFitWidth(640);
        bgImage2.setX(0);
        bgImage2.setY(640);

        tileGroup.getChildren().addAll(bgImage,bgImage2,p1.getPlayer(),p2.getPlayer(),button,arrow,left,right);
        return root;
    }

    private <T extends Player> void playerMove(T p1){
        if(DiceVal == 1 && p1.getPlayerPos() == 0 ){
            p1.setPlayerPos(1);
            p1.setXPosition(30);
            p1.setYPosition(580);
            p1.Move();
        }
        else if(p1.getPlayerPos() > 0){
            movePlayer(p1);
        }
    }

    private void getDice(Button button){
        button.setDisable(true);
        Random Dice = new Random();
        new Thread(() -> {
            try {
                for (int i = 0; i < 15; i++) {
                    Thread.sleep(50);
                    die.setDieFace(Dice.nextInt(6)+1);
                    button.setGraphic(die.getDieFace());
                }
                DiceVal = Dice.nextInt(6)+1;
                die.setDieFace(DiceVal);
                if(player1Turn) {
                    left.setImage(pl1l);
                    right.setImage(pl1r);
                    playerMove(p1);
                    player1Turn = false;
                    player2Turn = true;
                }
                else if(player2Turn) {
                    left.setImage(pl2l);
                    right.setImage(pl2r);
                    playerMove(p2);
                    player2Turn = false;
                    player1Turn = true;
                }
                button.setDisable(false);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    private void movePlayer(Player p){
        if (p.getPlayerPos()+DiceVal==100) {
            p.setXPosition(35);
            p.setYPosition(14);
            Platform.runLater(new runnable2(p,this));

        }
        for(int i = 0; i < DiceVal; i++) {
            if(p.getPlayerPos()>94){
                if(DiceVal<=100-p.getPlayerPos()){
                    p.setXPosition(p.getXPosition()-(64));
                    p.setPlayerPos(p.getPlayerPos() + 1);
                }
                else{
                    break;
                }
                p.setYPosition(p.getYPosition());
            }
            else {
                if (p.getPosCir() % 2 == 1 && p.getYPosition() > 0) {
                    p.setXPosition(p.getXPosition() + 64);
                } else if (p.getPosCir() % 2 == 0 && p.getYPosition() > 0) {
                    p.setXPosition(p.getXPosition() - 64);
                }
                if (p.getXPosition() > 640) {
                    p.setYPosition(p.getYPosition() - 64);
                    p.setXPosition(p.getXPosition() - 64);
                    p.setPosCir(p.getPosCir() + 1);
                } else if (p.getXPosition() < 0) {
                    p.setYPosition(p.getYPosition() - 64);
                    p.setXPosition(p.getXPosition() + 64);
                    p.setPosCir(p.getPosCir() + 1);
                }
                p.setPlayerPos(p.getPlayerPos() + 1);
            }
            try{
                TranslatePlayer(p.getXPosition(), p.getYPosition(), p.getPlayer());
                Thread.sleep(500);
            }
            catch(Exception e){e.printStackTrace();}
        }

        moveNewPlayer(p);
    }

    private void moveNewPlayer(Player p){
        if(p.getPlayerPos() == 2){
            p.setXPosition(p.getXPosition()+64);
            p.setYPosition(p.getYPosition()-192);
            p.setPosCir(p.getPosCir() + 3);
            p.setPlayerPos(38);
        }
        if(p.getPlayerPos() == 7){
            p.setXPosition(p.getXPosition());
            p.setYPosition(p.getYPosition()-64);
            p.setPosCir(p.getPosCir() + 1);
            p.setPlayerPos(14);
        }
        if(p.getPlayerPos() == 8){
            p.setXPosition(p.getXPosition()+130);
            p.setYPosition(p.getYPosition()-192);
            p.setPosCir(p.getPosCir() + 3);
            p.setPlayerPos(31);
        }
        if(p.getPlayerPos() == 15){
            p.setXPosition(p.getXPosition());
            p.setYPosition(p.getYPosition()-64);
            p.setPosCir(p.getPosCir() + 1);
            p.setPlayerPos(26);
        }
        if(p.getPlayerPos() == 28){
            p.setXPosition(p.getXPosition()-255);
            p.setYPosition(p.getYPosition()-384);
            p.setPosCir(p.getPosCir() + 6);
            p.setPlayerPos(84);
        }
        if(p.getPlayerPos() == 21){
            p.setXPosition(p.getXPosition()+64);
            p.setYPosition(p.getYPosition()-128);
            p.setPosCir(p.getPosCir() + 2);
            p.setPlayerPos(42);
        }
        if(p.getPlayerPos() == 36){
            p.setXPosition(p.getXPosition()-64); p.setYPosition(p.getYPosition()-64);
            p.setPosCir(p.getPosCir() + 1);
            p.setPlayerPos(44);
        }
        if(p.getPlayerPos() == 51){
            p.setXPosition(p.getXPosition()-195);
            p.setYPosition(p.getYPosition()-64);
            p.setPosCir(p.getPosCir() + 1);
            p.setPlayerPos(67);
        }
        if(p.getPlayerPos() == 78){
            p.setXPosition(p.getXPosition());
            p.setYPosition(p.getYPosition()-128);
            p.setPosCir(p.getPosCir() + 2);
            p.setPlayerPos(98);
        }
        if(p.getPlayerPos() == 71){
            p.setXPosition(p.getXPosition()); p.setYPosition(p.getYPosition()-128);
            p.setPosCir(p.getPosCir() + 2);
            p.setPlayerPos(91);
        }
        if(p.getPlayerPos() == 87){
            p.setXPosition(p.getXPosition()); p.setYPosition(p.getYPosition()-64);
            p.setPosCir(p.getPosCir() + 1);
            p.setPlayerPos(94);
        }

//        snakes
        if(p.getPlayerPos() == 16){
            p.setXPosition(p.getXPosition()+64); p.setYPosition(p.getYPosition()+64);
            p.setPosCir(p.getPosCir() - 1);
            p.setPlayerPos(6);
        }
        if(p.getPlayerPos() == 46){
            p.setXPosition(p.getXPosition()-64); p.setYPosition(p.getYPosition()+128);
            p.setPosCir(p.getPosCir() - 2);
            p.setPlayerPos(25);
        }
        if(p.getPlayerPos() == 49){
            p.setXPosition(p.getXPosition()+64); p.setYPosition(p.getYPosition()+192);
            p.setPosCir(p.getPosCir() - 3);
            p.setPlayerPos(11);
        }
        if(p.getPlayerPos() == 62){
            p.setXPosition(p.getXPosition()); p.setYPosition(p.getYPosition()+320);
            p.setPosCir(p.getPosCir() - 5);
            p.setPlayerPos(19);
        }
        if(p.getPlayerPos() == 64){
            p.setXPosition(p.getXPosition()-192); p.setYPosition(p.getYPosition()+64);
            p.setPosCir(p.getPosCir() - 1);
            p.setPlayerPos(60);
        }
        if(p.getPlayerPos() == 74){
            p.setXPosition(p.getXPosition()+64); p.setYPosition(p.getYPosition()+128);
            p.setPosCir(p.getPosCir() - 2);
            p.setPlayerPos(53);
        }
        if(p.getPlayerPos() == 92){
            p.setXPosition(p.getXPosition()-64); p.setYPosition(p.getYPosition()+64);
            p.setPosCir(p.getPosCir() - 1);
            p.setPlayerPos(88);
        }
        if(p.getPlayerPos() == 95){
            p.setXPosition(p.getXPosition()); p.setYPosition(p.getYPosition()+128);
            p.setPosCir(p.getPosCir() - 2);
            p.setPlayerPos(75);
        }
        if(p.getPlayerPos() == 99){
            p.setXPosition(p.getXPosition()-64); p.setYPosition(p.getYPosition()+128);
            p.setPosCir(p.getPosCir() - 2);
            p.setPlayerPos(80);
        }
        if(p.getPlayerPos() == 100){
            p.setXPosition(35); p.setYPosition(14);
            p.setPosCir(10);
            p.setPlayerPos(100);
        }
        TranslatePlayer(p.getXPosition(), p.getYPosition(), p.getPlayer());
        player1Turn = false;
        player2Turn = true;
    }

    private void TranslatePlayer(int x, int y, ImageView b){
        TranslateTransition animate = new TranslateTransition(Duration.millis(300),b);
        animate.setToX(x);
        animate.setToY(y);
        animate.setAutoReverse(false);
        animate.play();
    }

    private void TranslateArrow(ImageView arrow){
        TranslateTransition animate = new TranslateTransition(Duration.millis(800),arrow);
        animate.setToY(20);
        animate.setAutoReverse(true);
        animate.setCycleCount(Animation.INDEFINITE);
        animate.play();

    }

    protected void GameResult(int i){
        StackPane root = new StackPane();
        gameResult.setText("Player " + i + " WINS!!");
        gameResult.setTextFill(BLUE);
        Font font = Font.font("Agency FB", FontWeight.BOLD,25);
        gameResult.setFont(font);
        root.getChildren().add(gameResult);
        gameStart = false;
        Scene s = new Scene(root,400,200);
        Stage stage = new Stage();
        stage.setScene(s);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
    }

}

class Player{
    private final ImageView player;
    private int playerPos;
    private int XPosition;
    private int YPosition;
    private int posCir;
    Player(ImageView c, int XPosition, int YPosition, int i){
        this.player = c;
        this.player.setSmooth(true);
        this.player.setId("Player" + i);
        this.player.getStyleClass().add("style.css");
        this.player.setTranslateX(XPosition);
        this.player.setTranslateY(YPosition);
        this.XPosition = XPosition;
        this.YPosition = YPosition;
        this.playerPos = 0;
        this.posCir = 1;
    }

    protected ImageView getPlayer() {
        return player;
    }
    protected void setPosCir(int posCir) {
        this.posCir = posCir;
    }
    protected int getPosCir() {
        return posCir;
    }
    protected void setPlayerPos(int playerPos) {
        this.playerPos = playerPos;
    }
    protected void setXPosition(int XPosition) {
        this.XPosition = XPosition;
    }
    protected void setYPosition(int YPosition) {
        this.YPosition = YPosition;
    }
    protected void Move(){
        this.player.setTranslateY(this.YPosition);
        this.player.setTranslateX(this.XPosition);
    }
    protected int getXPosition() {
        return XPosition;
    }
    protected int getYPosition() {
        return YPosition;
    }
    protected int getPlayerPos() {
        return playerPos;
    }
}

class DieImages{
    private final Image die1 = new Image("FACE1.png");
    private final Image die2 = new Image("FACE2.png");
    private final Image die3 = new Image("FACE3.png");
    private final Image die4 = new Image("FACE4.png");
    private final Image die5 = new Image("FACE5.png");
    private final Image die6 = new Image("FACE6.png");

    private final Image[] images = new Image[6];

    public DieImages()
    {
        images[0] = die1;
        images[1] = die2;
        images[2] = die3;
        images[3] = die4;
        images[4] = die5;
        images[5] = die6;
    }

    public Image[] getImages()
    {
        return images;
    }
}

class Die{

    private final ImageView dieFace;
    private final Image[] images;

    public Die(Image[] images)
    {
        this.images = images;
        dieFace = new ImageView(this.images[5]);
        dieFace.setFitHeight(60);
        dieFace.setFitWidth(60);
    }

    public ImageView getDieFace()
    {
        return dieFace;
    }

    public void setDieFace(int dieFaceValue)
    {
        dieFace.setImage(this.images[dieFaceValue - 1]);
    }
}

class runnable2 implements Runnable{
    private final Player p;
    Controller c;
    runnable2(Player p, Controller c){
        this.p = p;
        this.c = c;
    }
    @Override
    public void run() {
        if (p.getPlayer().getId().equals("Player1")) {
            c.GameResult(1);
        } else if(p.getPlayer().getId().equals("Player2")) {
            c.GameResult(2);
        }
    }
}
