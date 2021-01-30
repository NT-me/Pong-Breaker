package data;

import javafx.util.Pair;
import tools.Position;
import java.util.List;

public class MobileObject extends GraphicObject {

    private double speed;
    private Position direction;
    //private Integer direction;


    public MobileObject(Position position, boolean displayed, String sprite, double speed, Position direction) {
        super(position, displayed, sprite);

        this.speed = speed;
        this.direction = direction;
    }

    public MobileObject(Position position, double speed, Position direction) {
        super(position);

        this.speed = speed;
        this.direction = direction;
    }

    public double getSpeed() {
        return speed;
    }

    public Position getDirection() {
        return direction;

    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public void setDirection(Position direction) {
        this.direction = direction;
    }

}

