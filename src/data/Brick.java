package data;

import javafx.scene.shape.Rectangle;

public class Brick extends ImmobileObject{
    private Player color;
    private int PV;
    private boolean breakable;
    private Rectangle avatar;

    public Brick(Player color, boolean breakable) {
        super();
        this.color = color;
        this.breakable = breakable;
        this.PV = 3;
        this.avatar = null;
    }

    public Rectangle getAvatar() {
        return avatar;
    }

    public void setAvatar(Rectangle avatar) {
        this.avatar = avatar;
    }

    public Player getColor() {
        return color;
    }

    public void setColor(Player color) {
        this.color = color;
    }

    public boolean isBreakable() {
        return breakable;
    }

    public void setBreakable(boolean breakable) {
        this.breakable = breakable;
    }

    public int getPV() {
        return PV;
    }

    public void setPV(int PV) {
        this.PV = PV;
    }
}
