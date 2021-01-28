package userInterface;
import data.Ball;
import data.Create;
import data.Palette;
import data.Player;
import javafx.scene.effect.Lighting;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import tools.HardCodedParameters;

import java.awt.*;
import java.util.ArrayList;

public class Factory {
    public Factory() {
    }

    public ArrayList<Shape> createField(){
        ArrayList<Shape> field = new ArrayList<>();
        Rectangle map = new Rectangle(HardCodedParameters.defaultWidth, HardCodedParameters.defaultHeight);
        field.add(map);
        Rectangle leftSurface = new Rectangle(-1, HardCodedParameters.defaultHeight/4,
                HardCodedParameters.defaultWidth/8,HardCodedParameters.defaultHeight/2);
        leftSurface.setStroke(Color.WHITE);
        field.add(leftSurface);
        Rectangle rightSurface = new Rectangle((HardCodedParameters.defaultWidth/8)*7,HardCodedParameters.defaultHeight/4,
                HardCodedParameters.defaultWidth/8,HardCodedParameters.defaultHeight/2);
        rightSurface.setStroke(Color.WHITE);
        field.add(rightSurface);
        Line middleLine = new Line(HardCodedParameters.defaultWidth/2,0,
                HardCodedParameters.defaultWidth/2,HardCodedParameters.defaultHeight);
        middleLine.setStroke(Color.WHITE);
        field.add(middleLine);
        Circle middleCircle = new Circle(HardCodedParameters.defaultWidth/2,HardCodedParameters.defaultHeight/2
                ,HardCodedParameters.defaultHeight/4);
        middleCircle.setStroke(Color.WHITE);
        field.add(middleCircle);
        return field;
    }

    public Rectangle createAvatarPalette(Palette pal){
        Rectangle palette = new Rectangle(
                pal.getPosition().x,
                pal.getPosition().y,
                pal.getHeight(),
                pal.getWidth());
        if(pal.getPlayer() == Player.BLUE){
            palette.setFill(javafx.scene.paint.Color.BLUE);
        }
        else{
            palette.setFill(javafx.scene.paint.Color.RED);
        }
        return palette;
    }

    public Rectangle createBrick(Point p, Player s){
        Rectangle brique = new Rectangle();
        brique.setY(p.getY());
        brique.setX(p.getX());
        brique.setWidth(80);
        brique.setHeight(120);
        if(s.toString() == "RED"){
            brique.setFill(Color.RED);
        }else{
            brique.setFill(Color.BLUE);
        }
        return brique;
    }

    public Circle createAvatarBall(Ball b){
        if(b instanceof Create){
            Create createBall = (Create) b;
            Circle createBallAvatar = new Circle(createBall.getPosition().x,createBall.getPosition().y,createBall.getRayon(),Color.DARKGRAY);
            createBallAvatar.setEffect(new Lighting());
            return createBallAvatar;
        }
        Circle mainBallAvatar = new Circle(b.getPosition().x, b.getPosition().y, b.getRayon(),Color.rgb(0,156,156));
        mainBallAvatar.setEffect(new Lighting());
        return mainBallAvatar;
    }
}
