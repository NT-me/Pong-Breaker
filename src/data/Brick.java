package data;

import javafx.scene.shape.Rectangle;

public class Brick extends ImmobileObject{
    Player color;
    boolean breakable;
    private Rectangle avatar;
    int pv;

    public Brick(Player color, boolean breakable) {
        super();
        this.color = color;
        this.breakable = breakable;
        this.avatar = null;
        this.pv = 3;
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

    public int getPv() {
        return pv;
    }

    public void setPv(int pv) {
        this.pv = pv;
    }
}
