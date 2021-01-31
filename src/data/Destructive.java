package data;

import javafx.util.Pair;
import tools.Position;

public class Destructive extends Ball{

    public Destructive(Position position, double speed, Position direction, double rayon, Player player) {
        super(position, speed, direction, rayon, player);
    }

    public void updatePositionDestBall(){
        this.setPosition(new Position(this.getPosition().x+this.getDirection().x,
                        this.getPosition().y+this.getDirection().y));
    }

}
