package userInterface;
import data.Create;
import data.Palette;
import data.Player;
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

    public Rectangle createPalette(Palette pal){
        return new Rectangle(
                pal.getPosition().x,
                pal.getPosition().y,
                pal.getHeight(),
                pal.getWidth());
    }

    public Rectangle createBrick(Point p, Player s){
        Rectangle brique = new Rectangle();
        brique.setY(p.getY()+10);
        brique.setX(p.getX()+10);
        brique.setWidth(70);
        brique.setHeight(80);
        if(s.toString() == "RED"){
            brique.setFill(Color.RED);
        }else{
            brique.setFill(Color.BLUE);
        }
        return brique;
    }

    public Circle createCreaBall(Create cr){
        return new Circle(cr.getPosition().x,
                cr.getPosition().y,
                cr.getRayon(),
                Color.rgb(0,255,0));
    }
}
