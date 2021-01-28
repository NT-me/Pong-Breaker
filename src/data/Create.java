package data;

import javafx.scene.shape.Circle;
import javafx.util.Pair;
import tools.Position;

public class Create extends Ball{
    boolean active;

    public Create(Position position, double speed, Pair<Integer, Integer> direction, double rayon, Player player) {
        super(position, speed, direction, rayon, player);
        active = false;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
