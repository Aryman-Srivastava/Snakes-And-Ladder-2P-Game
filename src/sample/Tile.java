package sample;

import javafx.scene.paint.*;
import javafx.scene.shape.*;

public class Tile extends Rectangle {
    public Tile(int x, int y){
        setWidth(x);
        setHeight(y);

        setStroke(Color.BLACK);
        setFill(Color.WHITE);

    }
}