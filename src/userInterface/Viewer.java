/* ******************************************************
 * Project alpha - Composants logiciels 2015.
 * Copyright (C) 2015 <Binh-Minh.Bui-Xuan@ens-lyon.org>.
 * GPL version>=3 <http://www.gnu.org/licenses/>.
 * $Id: userInterface/Viewer.java 2015-03-11 buixuan.
 * ******************************************************/
package userInterface;

import data.Player;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import specifications.ReadService;
import specifications.RequireReadService;
import specifications.ViewerService;
import tools.HardCodedParameters;

import java.awt.*;
import java.util.ArrayList;

public class Viewer<rectangle> implements ViewerService, RequireReadService{
  private static final double defaultMainWidth=HardCodedParameters.defaultWidth,
                              defaultMainHeight=HardCodedParameters.defaultHeight;

  private static double xShrink;
  private static double yShrink;

  private Circle mainBallAvatar, creaBallAvatar;
  private double direction;
  private ReadService data;
  private Factory factory = new Factory();
  private ImageView paletteView;
  private Image paletteSpriteSheet;
  private ArrayList<Rectangle2D> heroesAvatarViewports;
  private Rectangle paletteBlue, paletteRed;




  public Viewer(){}
  
  @Override
  public void bindReadService(ReadService service){
    data=service;
  }

  @Override
  public void init(){

  }

  @Override
  public Parent getPanel(){
    direction = Math.random();

    creaBallAvatar = factory.createAvatarBall(data.getCreaBall());
    mainBallAvatar = factory.createAvatarBall(data.getMainBall());

    paletteBlue = factory.createAvatarPalette(data.getBlue());
    paletteRed = factory.createAvatarPalette(data.getRed());

    Rectangle map = new Rectangle(defaultMainWidth, defaultMainHeight);
    ArrayList<Shape> field = factory.createField();

    Group panel = new Group();
    panel.getChildren().add(map);
    panel.getChildren().addAll(field);
    panel.getChildren().add(factory.createBrick(new Point(500,400),Player.BLUE));
    panel.getChildren().addAll(mainBallAvatar,paletteRed,paletteBlue,creaBallAvatar);
    return panel;
  }

  @Override
  public void setMainWindowWidth(double width){
    xShrink=width/defaultMainWidth;
  }

  @Override
  public void setMainWindowHeight(double height){
    yShrink=height/defaultMainHeight;
  }
}
