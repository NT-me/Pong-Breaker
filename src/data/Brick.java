package data;

public class Brick extends ImmobileObject{
    Player color;
    boolean breakable;

    public Brick(Player color, boolean breakable) {
        super();
        this.color = color;
        this.breakable = breakable;
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
}
