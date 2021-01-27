package data;

import data.Data;
import engine.Engine;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import specifications.DataService;
import specifications.EngineService;
import specifications.ViewerService;
import tools.HardCodedParameters;
import tools.Position;
import userInterface.Viewer;
import javafx.scene.shape.Line;

import java.awt.*;
import java.awt.*;

public class Wall extends ImmobileObject {

    private Position end;
    public Wall() {
        super();
    }

    public Wall(Position start, Position end, boolean displayed, String sprite) {
        super(start, displayed, sprite);
        this.end = end;
    }
}
