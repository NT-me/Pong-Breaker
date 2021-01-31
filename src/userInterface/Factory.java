package userInterface;
import data.*;
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

    public Circle createBall(Ball b){
        Circle avatar =  new Circle(b.getPosition().x,b.getPosition().y, b.getRayon());

        if (b instanceof Create){
            avatar.setFill(Color.rgb(0, 255, 0));
        }
        else if (b instanceof Destructive){
            avatar.setFill(Color.rgb(120,40,100));
        }
        else if (b instanceof Ball){
            avatar.setFill(Color.rgb(0,156,156));
            avatar.setEffect(new Lighting());
        }
        return avatar;
    }


    public Rectangle createPalette(Palette pal){
        Rectangle palette = new Rectangle(
                pal.getPosition().x,
                pal.getPosition().y,
                pal.getHeight(),
                pal.getWidth());
        if(pal.getPlayer() == Player.RED){
            palette.setFill(new Color(1.0,0,0,((double)pal.getPV()/(double)HardCodedParameters.palettePV)));
        }
        if(pal.getPlayer() == Player.BLUE){
            palette.setFill(new Color(0,0,1.0,((double)pal.getPV()/(double)HardCodedParameters.palettePV)));
        }
        return palette;
    }

    public Rectangle createBrick(Brick bri){
        Rectangle brique = new Rectangle();
        brique.setY(bri.getPosition().y+10);
        brique.setX(bri.getPosition().x+10);
        brique.setWidth(70);
        brique.setHeight(80);
        if(bri.getColor().toString() == "RED"){
            brique.setFill(Color.RED);
        }else{
            brique.setFill(Color.BLUE);
        }
        return brique;
    }
}
