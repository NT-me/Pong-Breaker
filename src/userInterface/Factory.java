package userInterface;
import data.Palette;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import tools.HardCodedParameters;

import java.awt.*;
import java.util.ArrayList;

public class Factory {
    public Factory() {
    }

    public ArrayList<Object> createPlayGround(){
        ArrayList<Object> res = new ArrayList<Object>();

        Rectangle leftSurface = new Rectangle(
                -1,
                HardCodedParameters.defaultHeight/4,
                HardCodedParameters.defaultWidth/8,
                HardCodedParameters.defaultHeight/2);
        leftSurface.setStroke(Color.WHITE);
        Rectangle rightSurface = new Rectangle((HardCodedParameters.defaultWidth/8)*7,
                HardCodedParameters.defaultHeight/4,
                HardCodedParameters.defaultWidth/8,
                HardCodedParameters.defaultHeight/2);
        rightSurface.setStroke(Color.WHITE);
        Line middleLine = new Line(HardCodedParameters.defaultWidth/2,
                0,
                HardCodedParameters.defaultWidth/2,
                HardCodedParameters.defaultHeight);
        middleLine.setStroke(Color.WHITE);
        Circle middleCircle = new Circle(HardCodedParameters.defaultWidth/2,
                HardCodedParameters.defaultHeight/2
                ,HardCodedParameters.defaultHeight/4);
        middleCircle.setStroke(Color.WHITE);
        res.add(leftSurface);
        res.add(rightSurface);
        res.add(middleLine);
        res.add(middleCircle);

        return res;
    }

    public Rectangle createPalette(Palette pal){
        return new Rectangle(
                pal.getPosition().x,
                pal.getPosition().y,
                pal.getHeight(),
                pal.getWidth());
    }

    public Rectangle createBrick(Point p){
        Rectangle brique = new Rectangle();
        brique.setY(p.getY());
        brique.setX(p.getX());
        brique.setWidth(20);
        brique.setHeight(70);
        if(s.toString() == "RED"){
            brique.setFill(Color.RED);
        }else{
            brique.setFill(Color.BLUE);
        }
        return brique;
    }
}
