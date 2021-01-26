package data;

import javafx.util.Pair;
import tools.Position;
import java.util.List;

public class MobileObject extends GraphicObject {

    private double speed;
    private Pair<Integer, Integer> direction;
    public MobileObject(Position position, double speed, Pair<Integer, Integer> direction) {
        super(position);
        this.speed = speed;
        this.direction = direction;
    }

    public MobileObject(Position position, boolean displayed, String sprite, String hitbox, double speed, Pair<Integer, Integer> direction) {
        super(position, displayed, sprite, hitbox);
        this.speed = speed;
        this.direction = direction;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public Pair<Integer, Integer> getDirection() {
        return direction;
    }

    public void setDirection(Pair<Integer, Integer> direction) {
        this.direction = direction;
    }
}
