/* ******************************************************
 * Project alpha - Composants logiciels 2015.
 * Copyright (C) 2015 <Binh-Minh.Bui-Xuan@ens-lyon.org>.
 * GPL version>=3 <http://www.gnu.org/licenses/>.
 * $Id: userInterface/Viewer.java 2015-03-11 buixuan.
 * ******************************************************/
package userInterface;

import data.Ball;
import data.Brick;
import data.Palette;
import data.Player;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.effect.Lighting;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
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
  private static final int spriteSlowDownRate=HardCodedParameters.spriteSlowDownRate;
  private static final double defaultMainWidth=HardCodedParameters.defaultWidth,
                              defaultMainHeight=HardCodedParameters.defaultHeight;

  private static double xShrink;
  private static double yShrink;

  private Circle mainBallAvatar;
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

    Ball mainBall = data.getMainBall();
    mainBallAvatar = new Circle(mainBall.getPosition().x,mainBall.getPosition().y, mainBall.getRayon(),Color.rgb(0,156,156));
    mainBallAvatar.setEffect(new Lighting());

    Circle creaBallRed = null;
    try{
      creaBallRed = factory.createCreaBall(data.getRcreaBall());
    }
    catch(NullPointerException E){
      creaBallRed = new Circle(-100,-100, 0, Color.rgb(0,0,0));
    }

    Palette blue = data.getBlue();
    paletteBlue = factory.createPalette(blue);
    paletteBlue.setFill(javafx.scene.paint.Color.BLUE);

    Palette red = data.getRed();
    paletteRed = factory.createPalette(red);
    paletteRed.setFill(javafx.scene.paint.Color.RED);

    Rectangle map = new Rectangle(defaultMainWidth, defaultMainHeight);
    ArrayList<Shape> field = factory.createField();

    ArrayList<Brick> brickList = data.getBricks();

    Group panel = new Group();
    panel.getChildren().add(map);
    panel.getChildren().addAll(field);


    for (Brick B : brickList){
      panel.getChildren().add(factory.createBrick(new Point((int)B.getPosition().x,(int)B.getPosition().y),B.getColor()));
    }

    panel.getChildren().addAll(mainBallAvatar,paletteRed,paletteBlue,creaBallRed);
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
