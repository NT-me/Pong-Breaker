package data;

import javafx.scene.shape.Rectangle;
import javafx.util.Pair;
import tools.Position;

public class Palette extends MobileObject {
    private double width;
    private double height;
    private Player player;
    private Integer PV;
    private Integer RespawnCoolDown;
    private Rectangle avatar;

    public Palette(Position position, boolean displayed, String sprite, double speed, Position direction, double width, double height, Player player, Integer PV, Integer respawnCoolDown) {
        super(position, displayed, sprite, speed, direction);
        this.width = width;
        this.player = player;
        this.PV = PV;
        this.height = height;
        RespawnCoolDown = respawnCoolDown;
        this.avatar = null;
    }
    public Palette(Position position, double speed, Position direction, double width, double height, Player player, Integer PV, Integer respawnCoolDown) {
        super(position, speed, direction);
        this.width = width;
        this.player = player;
        this.PV = PV;
        this.height = height;
        RespawnCoolDown = respawnCoolDown;
        this.avatar = null;
    }

    public Rectangle getAvatar() {
        return avatar;
    }

    public void setAvatar(Rectangle avatar) {
        this.avatar = avatar;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
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
