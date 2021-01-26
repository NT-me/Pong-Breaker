/* ******************************************************
 * Project alpha - Composants logiciels 2015.
 * Copyright (C) 2015 <Binh-Minh.Bui-Xuan@ens-lyon.org>.
 * GPL version>=3 <http://www.gnu.org/licenses/>.
 * $Id: userInterface/Viewer.java 2015-03-11 buixuan.
 * ******************************************************/
package userInterface;

import data.Palette;
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

import java.util.ArrayList;

public class Viewer<rectangle> implements ViewerService, RequireReadService{
  private static final int spriteSlowDownRate=HardCodedParameters.spriteSlowDownRate;
  private static final double defaultMainWidth=HardCodedParameters.defaultWidth,
                              defaultMainHeight=HardCodedParameters.defaultHeight;
  private static double xShrink;
  private static double yShrink;
  private ReadService data;
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
    paletteSpriteSheet = new Image("file:src/images/paletteBlue.png");
    paletteView = new ImageView(paletteSpriteSheet);
    heroesAvatarViewports = new ArrayList<Rectangle2D>();
    heroesAvatarViewports.add(new Rectangle2D(570,194,115,190));
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
    Group panel = new Group();
    panel.getChildren().addAll(map, paletteBlue, paletteRed);
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
