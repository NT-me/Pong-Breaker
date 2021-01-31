package data;

import javafx.scene.shape.Circle;
import javafx.util.Pair;
import tools.Position;

public class Ball extends MobileObject {

    private double rayon;
    private Player player;
    private Circle avatar;

    public Ball(Position position, double speed, Position direction, double rayon, Player player) {
        super(position, speed, direction);
        this.rayon = rayon;
        this.player = player;
        this.avatar = null;
    }


    public double getRayon() {
        return rayon;
    }
    public void setRayon(double rayon) {
        this.rayon = rayon;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Circle getAvatar() {
        return avatar;
    }

    public void setAvatar(Circle avatar) {
        this.avatar = avatar;
    }


}
