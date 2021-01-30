package data;

import javafx.scene.shape.Rectangle;
import javafx.util.Pair;

public class Brick extends ImmobileObject{
    Player color;
    boolean breakable;
    private Rectangle avatar;
    int pv;
    Pair<Integer, Integer> matrixPos;

    public Brick(Player color, boolean breakable) {
        super();
        this.color = color;
        this.breakable = breakable;
        this.avatar = null;
        this.pv = 3;
    }

    public Brick(Player color, boolean breakable, Pair<Integer, Integer> matrixPos) {
        super();
        this.color = color;
        this.breakable = breakable;
        this.avatar = null;
        this.pv = 1;
        this.matrixPos = matrixPos;
    }

    public Pair<Integer, Integer> getMatrixPos() {
        return matrixPos;
    }

    public void setMatrixPos(Pair<Integer, Integer> matrixPos) {
        this.matrixPos = matrixPos;
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
