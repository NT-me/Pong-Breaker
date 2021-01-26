package data;

import javafx.util.Pair;
import tools.Position;
import java.util.List;

public class MobileObject extends GraphicObject {

    private double speed;
    private Pair<Position,Position> direction;
    public MobileObject(Position position, double speed, Pair<Position,Position> direction) {
        super(position);
        this.speed = speed;
        this.direction = direction;
    }

    public MobileObject(Position position, boolean displayed, String sprite, String hitbox, double speed, Pair<Position,Position> direction) {
        super(position, displayed, sprite, hitbox);
        this.speed = speed;
        this.direction = direction;
    }

    public MobileObject() {
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public Pair<Position,Position> getDirection() {
        return direction;
    }

    public void setDirection(Pair<Position,Position> direction) {
        this.direction = direction;
    }
}
