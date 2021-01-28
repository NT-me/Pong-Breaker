package data;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Pair;
import tools.Position;

public class Ball extends MobileObject {

    private double rayon;
    private Player player;
    private Circle avatar;
    private double VX, VY;

    public Ball(Position position, double speed, Pair<Integer, Integer> direction, double rayon, Player player) {
        super(position, speed, direction);
        this.rayon = rayon;
        this.player = player;
        this.avatar = null;
        this.VX = 0;
        this.VY = 0;
    }

    public double getVX() {
        return VX;
    }

    public void setVX(double VX) {
        this.VX = VX;
    }

    public void setVY(double VY) {
        this.VY = VY;
    }

    public Circle getAvatar() {
        return avatar;
    }

    public void setAvatar(Circle avatar) {
        this.avatar = avatar;
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
}
