package data;

import javafx.util.Pair;
import tools.Position;
import java.util.List;

public class Ball extends MobileObject {

    private double rayon;
    private String player;

    public Ball(Position position, double speed, Pair<Position,Position> direction, double rayon, String player) {
        super(position, speed, direction);
        this.rayon = rayon;
        this.player = player;
    }

    public Ball() {
    }

    public double getRayon() {
        return rayon;
    }
    public void setRayon(double rayon) {
        this.rayon = rayon;
    }

    public String getPlayer() {
        return player;
    }

    public void setPlayer(String player) {
        this.player = player;
    }


    public void Collision(){

    }
}
