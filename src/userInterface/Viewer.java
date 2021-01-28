/* ******************************************************
 * Project alpha - Composants logiciels 2015.
 * Copyright (C) 2015 <Binh-Minh.Bui-Xuan@ens-lyon.org>.
 * GPL version>=3 <http://www.gnu.org/licenses/>.
 * $Id: userInterface/Viewer.java 2015-03-11 buixuan.
 * ******************************************************/
package userInterface;

import data.Player;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.shape.Shape;
import specifications.ReadService;
import specifications.RequireReadService;
import specifications.ViewerService;
import tools.HardCodedParameters;

import java.awt.*;
import java.util.ArrayList;

public class Viewer implements ViewerService, RequireReadService{
  private static double xShrink;
  private static double yShrink;

  private double direction;
  private ReadService data;
  private Factory factory = new Factory();

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

    //Création des balles
    data.getCreateBall().setAvatar(factory.createAvatarBall(data.getCreateBall()));
    data.getMainBall().setAvatar(factory.createAvatarBall(data.getMainBall()));

    //Création de palettes
    data.getBlue().setAvatar(factory.createAvatarPalette(data.getBlue()));
    data.getRed().setAvatar(factory.createAvatarPalette(data.getRed()));

    //Création du terrain de jeu
    ArrayList<Shape> field = factory.createField();

    Group panel = new Group();
    panel.getChildren().addAll(field);
    panel.getChildren().add(factory.createBrick(new Point(500,400),Player.BLUE));
    panel.getChildren().addAll(data.getMainBall().getAvatar(),data.getRed().getAvatar(),
            data.getBlue().getAvatar(),data.getCreateBall().getAvatar());
    return panel;
  }

  @Override
  public void setMainWindowWidth(double width){
    xShrink=width/HardCodedParameters.defaultWidth;
  }

  @Override
  public void setMainWindowHeight(double height){
    yShrink=height/HardCodedParameters.defaultHeight;
  }
}
