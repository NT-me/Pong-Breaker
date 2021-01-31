/* ******************************************************
 * Project alpha - Composants logiciels 2015.
 * Copyright (C) 2015 <Binh-Minh.Bui-Xuan@ens-lyon.org>.
 * GPL version>=3 <http://www.gnu.org/licenses/>.
 * $Id: userInterface/Viewer.java 2015-03-11 buixuan.
 * ******************************************************/
package userInterface;

import data.Ball;
import data.Brick;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import specifications.ReadService;
import specifications.RequireReadService;
import specifications.ViewerService;
import tools.HardCodedParameters;

import java.awt.*;
import java.util.ArrayList;

public class Viewer implements ViewerService, RequireReadService{
  private static double xShrink;
  private static double yShrink;

  private ReadService data;
  private final Factory factory = new Factory();
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
    Text scoreBlue = new Text();
    scoreBlue.setText("Blue Score : " + data.getScoreB());
    scoreBlue.setX(0);
    scoreBlue.setY(10);
    scoreBlue.setFill(Color.WHITE);
    Text scoreRed = new Text();
    scoreRed.setText("Red Score : " + data.getScoreR());
    scoreRed.setX(0);
    scoreRed.setY(25);
    scoreRed.setFill(Color.WHITE);

    //Set des images de palettes
    data.getBlue().setAvatar(factory.createPalette(data.getBlue()));
    data.getRed().setAvatar(factory.createPalette(data.getRed()));

    //Set des balles
    data.getRdestBall().setAvatar(factory.createBall(data.getRdestBall()));
    data.getBdestBall().setAvatar(factory.createBall(data.getBdestBall()));

    data.getRcreaBall().setAvatar(factory.createBall(data.getRcreaBall()));
    data.getBcreaBall().setAvatar(factory.createBall(data.getBcreaBall()));

    data.getMainBall().setAvatar(factory.createBall(data.getMainBall()));

    //Creation du terrain
    field = factory.createField();

    //Recuperation des briques existantes
    brickList = data.getBricks();

    Group panel = new Group();

    panel.getChildren().addAll(field);
    for (Brick B : brickList){
      panel.getChildren().add(factory.createBrick(B));
    }

    panel.getChildren().addAll(data.getMainBall().getAvatar(),
            data.getRed().getAvatar(),
            data.getBlue().getAvatar(),
            data.getBcreaBall().getAvatar(),
            data.getRcreaBall().getAvatar(),
            data.getBdestBall().getAvatar(),
            data.getRdestBall().getAvatar(),
            scoreBlue,
            scoreRed);
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