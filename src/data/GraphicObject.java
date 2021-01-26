package data;

import tools.Position;

public class GraphicObject {
    private Position position;
    private boolean displayed;
    private String sprite;
    private String hitbox;

    public GraphicObject(Position position, boolean displayed, String sprite, String hitbox) {
        this.position = position;
        this.displayed = displayed;
        this.sprite = sprite;
        this.hitbox = hitbox;
    }

    public GraphicObject(Position position) {
        this.position = position;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public boolean isDisplayed() {
        return displayed;
    }

    public void setDisplayed(boolean displayed) {
        this.displayed = displayed;
    }

    public String getSprite() {
        return sprite;
    }

    public void setSprite(String sprite) {
        this.sprite = sprite;
    }

    public String getHitbox() {
        return hitbox;
    }

    public void setHitbox(String hitbox) {
        this.hitbox = hitbox;
    }
}
