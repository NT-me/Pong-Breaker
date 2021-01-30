/* ******************************************************
 * Project alpha - Composants logiciels 2015.
 * Copyright (C) 2015 <Binh-Minh.Bui-Xuan@ens-lyon.org>.
 * GPL version>=3 <http://www.gnu.org/licenses/>.
 * $Id: userInterface/Viewer.java 2015-03-11 buixuan.
 * ******************************************************/
package userInterface;

import data.Brick;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;
import specifications.ReadService;
import specifications.RequireReadService;
import specifications.ViewerService;
import tools.HardCodedParameters;

import java.awt.*;
import java.util.ArrayList;

public class Viewer implements ViewerService, RequireReadService{
  private static final int spriteSlowDownRate=HardCodedParameters.spriteSlowDownRate;

  private static double xShrink;
  private static double yShrink;

  private double direction;
  private ReadService data;
  private Factory factory = new Factory();
  private ImageView paletteView;
  private Circle creaBallRed, creaBallBlue, destBallRed, destBallBlue;
  private ArrayList<Shape> field;
  private ArrayList<Brick> brickList;

  public Viewer(){
    this.field = new ArrayList<>();
    this.brickList = new ArrayList<>();

  }
  
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

    //Crée l'image de la balle classique
    data.getMainBall().setAvatar(factory.createBall(data.getMainBall()));

    destBallRed = new Circle(data.getRdestBall().getPosition().x,
            data.getRdestBall().getPosition().y,
            data.getRdestBall().getRayon(),
            Color.rgb(120,40,100));

    destBallBlue = new Circle(data.getBdestBall().getPosition().x,
            data.getBdestBall().getPosition().y,
            data.getBdestBall().getRayon(),
            Color.rgb(120,40,100));

    try{
      data.getRcreaBall().setAvatar(factory.createBall(data.getRcreaBall()));
      creaBallRed = data.getRcreaBall().getAvatar();
    }
    catch(NullPointerException E){
      creaBallRed = new Circle(-100,-100, 0, Color.rgb(0,0,0));
    }

    try{
      data.getBcreaBall().setAvatar(factory.createBall(data.getBcreaBall()));
      creaBallBlue = data.getBcreaBall().getAvatar();
    }
    catch(NullPointerException E){
      creaBallBlue = new Circle(-100,-100, 0, Color.rgb(0,0,0));
    }

    //Recuperation des images de palettes
    data.getBlue().setAvatar(factory.createPalette(data.getBlue()));
    data.getRed().setAvatar(factory.createPalette(data.getRed()));

    //Creation du terrain
    field = factory.createField();

    //Recuperation des briques existantes
    brickList = data.getBricks();

    Group panel = new Group();

    panel.getChildren().addAll(field);
    for (Brick B : brickList){
      panel.getChildren().add(factory.createBrick(new Point((int)B.getPosition().x,(int)B.getPosition().y),B.getColor()));
    }

    panel.getChildren().addAll(data.getMainBall().getAvatar(),
            data.getRed().getAvatar(),
            data.getBlue().getAvatar(),
            creaBallRed,
            creaBallBlue,
            destBallRed,
            destBallBlue);
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