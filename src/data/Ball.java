package data;

import javafx.util.Pair;
import tools.Position;

public class Ball extends MobileObject {

    private double rayon;
    private Player player;

    public Ball(Position position, double speed, Pair<Integer,Integer> direction, double rayon, Player player) {
        super(position, speed, direction);
        this.rayon = rayon;
        this.player = player;
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


    public void Collision(){

    }
}
