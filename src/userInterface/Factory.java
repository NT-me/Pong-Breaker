package userInterface;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.awt.*;

public class Factory {
    public Factory() {
    }

    public Rectangle createBrick(Point p){
        Rectangle brique = new Rectangle();
        brique.setY(p.getY());
        brique.setX(p.getX());
        brique.setWidth(20);
        brique.setHeight(70);
        brique.setFill(Color.WHITE);
        return brique;
    }
}
