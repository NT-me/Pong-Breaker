package data;

import javafx.util.Pair;
import tools.Position;

public class Ball extends MobileObject {

    public Ball(Position position, boolean displayed, String sprite, double speed, Pair<Integer, Integer> direction) {
        super(position, displayed, sprite, speed, direction);
    }
}
