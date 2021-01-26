/* ******************************************************
 * Project alpha - Composants logiciels 2015.
 * Copyright (C) 2015 <Binh-Minh.Bui-Xuan@ens-lyon.org>.
 * GPL version>=3 <http://www.gnu.org/licenses/>.
 * $Id: userInterface/Viewer.java 2015-03-11 buixuan.
 * ******************************************************/
package userInterface;

import data.Wall;
import data.Ball;
import data.Brick;
import data.Player;
import data.Palette;
import javafx.scene.Node;
import javafx.scene.shape.Line;
import tools.HardCodedParameters;
import tools.Position;
import specifications.ViewerService;
import specifications.ReadService;
import specifications.RequireReadService;

import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.effect.Lighting;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.Font;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.geometry.Rectangle2D;

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
  private ArrayList<Object> playground;


  public Viewer(){}
  
  @Override
  public void bindReadService(ReadService service){
    data=service;
  }

  @Override
  public void init(){
    playground = factory.createPlayGround();
  }

  @Override
  public Parent getPanel(){
    direction = Math.random();

    Ball mainBall = data.getMainBall();
    mainBallAvatar = new Circle(mainBall.getPosition().x,mainBall.getPosition().y, mainBall.getRayon(),Color.rgb(0,156,156));
    mainBallAvatar.setEffect(new Lighting());


    Palette blue = data.getBlue();
    paletteBlue = factory.createPalette(blue);
    paletteBlue.setFill(javafx.scene.paint.Color.BLUE);

    Palette red = data.getRed();
    paletteRed = factory.createPalette(red);
    paletteRed.setFill(javafx.scene.paint.Color.RED);

    Group panel = new Group();

    for (int i = 0; i < playground.size();++i){
      panel.getChildren().add((Node) playground.get(i));
    }

    panel.getChildren().addAll(mainBallAvatar, paletteBlue, paletteRed,factory.createBrick(new Point(500,400)));
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
