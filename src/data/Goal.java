package data;

import tools.Position;

public class Goal extends Wall {

    private String player;

    public Goal(Position start, Position end, boolean displayed, String sprite, String player) {
        super(start, end, displayed, sprite);
        this.player = player;
    }

    public String getPlayer() {
        return player;
    }

    public void setPlayer(String player) {
        this.player = player;
    }
}
