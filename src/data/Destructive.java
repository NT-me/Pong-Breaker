package data;

import javafx.util.Pair;
import tools.Position;

public class Destructive extends Ball{

    public Destructive(Position position, double speed, Pair<Integer, Integer> direction, double rayon, Player player) {
        super(position, speed, direction, rayon, player);
    }
}