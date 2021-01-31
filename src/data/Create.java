package data;

import javafx.util.Pair;
import tools.Position;

public class Create extends Ball{
    boolean active;

    public Create(Position position, double speed, Position direction, double rayon, Player player) {
        super(position, speed, direction, rayon, player);
        active = false;
    }

    public void updatePositionCreaBall(){
        Position dirR = this.getDirection();
        this.setPosition(new Position(this.getPosition().x+dirR.x,this.getPosition().y+dirR.y));
    }

}
