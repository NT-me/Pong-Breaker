package data;

import tools.Position;

public class Goal extends Wall {

    private Player player;

    public Goal(Position start, Position end, boolean displayed, String sprite, Player player) {
        super(start, end, displayed, sprite);
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}
