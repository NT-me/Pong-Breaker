package data;

import javafx.util.Pair;
import tools.Position;

public class MobileObject extends GraphicObject {

    private double speed;
    private Pair<Integer, Integer> direction;
    //private Integer direction;


    public MobileObject(Position position, boolean displayed, String sprite, double speed, Pair<Integer, Integer> direction) {
        super(position, displayed, sprite);
        this.speed = speed;
        this.direction = direction;
    }

    public MobileObject(Position position, double speed, Pair<Integer, Integer> direction) {
        super(position);
        this.speed = speed;
        this.direction = direction;
    }

    public double getSpeed() {
        return speed;
    }

    public Pair<Integer, Integer> getDirection() {
        return direction;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public void setDirection(Pair<Integer, Integer> direction) {
        this.direction = direction;
    }

}