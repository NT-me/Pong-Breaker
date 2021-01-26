package data;

import javafx.util.Pair;
import tools.Position;

public class Palette extends MobileObject {
    private double width;
    private String player;
    private Integer PV;
    private Integer RespawnCoolDown;

    public Palette(Position position, boolean displayed, String sprite, double speed, Pair<Integer, Integer> direction, double width, String player, Integer PV, Integer respawnCoolDown) {
        super(position, displayed, sprite, speed, direction);
        this.width = width;
        this.player = player;
        this.PV = PV;
        RespawnCoolDown = respawnCoolDown;
    }

    public Palette(Position position, double speed, Pair<Integer, Integer> direction, double width, String player, Integer PV, Integer respawnCoolDown) {
        super(position, speed, direction);
        this.width = width;
        this.player = player;
        this.PV = PV;
        RespawnCoolDown = respawnCoolDown;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public String getPlayer() {
        return player;
    }

    public void setPlayer(String player) {
        this.player = player;
    }

    public Integer getPV() {
        return PV;
    }

    public void setPV(Integer PV) {
        this.PV = PV;
    }

    public Integer getRespawnCoolDown() {
        return RespawnCoolDown;
    }

    public void setRespawnCoolDown(Integer respawnCoolDown) {
        RespawnCoolDown = respawnCoolDown;
    }
}
