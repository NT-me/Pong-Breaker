package data;

import javafx.scene.shape.Circle;
import javafx.util.Pair;
import tools.HardCodedParameters;
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

    public void updateSpeedBall(){
        if (getSpeed() > 1){
            setSpeed(getSpeed()*0.99);
        }
        else{
            setSpeed(1);
        }
        setDirection(new Position(getDirection().x*getSpeed(),
                getDirection().y*getSpeed()));
    }

    public void updatePositionBall(){

        if (getDirection().x >= HardCodedParameters.paletteHeight){
            setDirection(new Position(HardCodedParameters.paletteHeight-0.1,
                                         getDirection().y));
        }
        if (getDirection().x <= -HardCodedParameters.paletteHeight){
            setDirection(new Position(-(HardCodedParameters.paletteHeight-0.1),
                                        getDirection().y));
        }
        setPosition(new Position(getPosition().x+getDirection().x,
                                 getPosition().y+getDirection().y));

    }

}
