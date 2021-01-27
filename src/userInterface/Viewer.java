/* ******************************************************
 * Project alpha - Composants logiciels 2015.
 * Copyright (C) 2015 <Binh-Minh.Bui-Xuan@ens-lyon.org>.
 * GPL version>=3 <http://www.gnu.org/licenses/>.
 * $Id: userInterface/Viewer.java 2015-03-11 buixuan.
 * ******************************************************/
package userInterface;
import data.Brick;
import data.Player;
import data.Palette;
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


  public Viewer(){}
  
  @Override
  public void bindReadService(ReadService service){
    data=service;
  }

  @Override
  public void init(){

    direction = Math.random();

    mainBallAvatar = new Circle(20,Color.rgb(0,156,156));
    mainBallAvatar.setEffect(new Lighting());
    mainBallAvatar.setTranslateX(data.getMainBall().getPosition().x);
    mainBallAvatar.setTranslateY(data.getMainBall().getPosition().y);

  }

  @Override
  public Parent getPanel(){
    Palette blue = data.getBlue();
    paletteBlue = new Rectangle(blue.getPosition().x, blue.getPosition().y, blue.getHeight(), blue.getWidth());
    paletteBlue.setFill(javafx.scene.paint.Color.BLUE);

    Palette red = data.getRed();
    paletteRed = new Rectangle(red.getPosition().x, red.getPosition().y, red.getHeight(), red.getWidth());
    paletteRed.setFill(javafx.scene.paint.Color.RED);

    Rectangle map = new Rectangle(defaultMainWidth, defaultMainHeight);
    Rectangle leftSurface = new Rectangle(-1,HardCodedParameters.defaultHeight/4,
            HardCodedParameters.defaultWidth/8,HardCodedParameters.defaultHeight/2);
    leftSurface.setStroke(Color.WHITE);
    Rectangle rightSurface = new Rectangle((HardCodedParameters.defaultWidth/8)*7,HardCodedParameters.defaultHeight/4,
            HardCodedParameters.defaultWidth/8,HardCodedParameters.defaultHeight/2);
    rightSurface.setStroke(Color.WHITE);
    Line middleLine = new Line(HardCodedParameters.defaultWidth/2,0,
            HardCodedParameters.defaultWidth/2,HardCodedParameters.defaultHeight);
    middleLine.setStroke(Color.WHITE);
    Circle middleCircle = new Circle(HardCodedParameters.defaultWidth/2,HardCodedParameters.defaultHeight/2
            ,HardCodedParameters.defaultHeight/4);
    middleCircle.setStroke(Color.WHITE);
    Group panel = new Group();
    panel.getChildren().add(map);
    panel.getChildren().add(factory.createBrick(new Point(500,400)));

    panel.getChildren().addAll(map,middleCircle,middleLine,leftSurface,rightSurface, mainBallAvatar, paletteBlue, paletteRed);
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
