package data;

import javafx.util.Pair;
import tools.Position;

public class Create extends Ball{
    boolean active;

    public Create(Position position, double speed, Pair<Integer, Integer> direction, double rayon, Player player) {
        super(position, speed, direction, rayon, player);
        active = false;
    }

}
