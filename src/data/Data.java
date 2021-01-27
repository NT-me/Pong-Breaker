/* ******************************************************
 * Project alpha - Composants logiciels 2015.
 * Copyright (C) 2015 <Binh-Minh.Bui-Xuan@ens-lyon.org>.
 * GPL version>=3 <http://www.gnu.org/licenses/>.
 * $Id: data/Data.java 2015-03-11 buixuan.
 * ******************************************************/
package data;

import javafx.util.Pair;
import specifications.DataService;
import tools.HardCodedParameters;
import tools.Position;

import java.util.ArrayList;

public class Data implements DataService{
  private Palette blue;
  private Palette red;
  private int[][] matrice;
  private Create creaBall;

  public Data(){  }
  private int stepNumber;
  private double rayon;
  private Position position;
  private Ball mainBall;
  private Pair<Integer,Integer> direction;
  private double speed;
  private ArrayList<Brick> bricks;

  @Override
  public int[][] getMatrice() {
    return matrice;
  }

  @Override
  public void setMatrice(int x, int y, int val) {
    this.matrice[x][y] = val;
  }

  @Override
  public ArrayList<Brick> getBricks() {
    return bricks;
  }

  @Override
  public void setBricks(ArrayList<Brick> bricks) {
    this.bricks = bricks;
  }

  @Override
  public void init(){
    matrice = new int[4][8];
    bricks = new ArrayList<>();
    direction = new Pair<Integer,Integer>(0,0);

    stepNumber = 0;
    double paletteWidth = 200;
    double paletteHeight = 10;
    Pair<Integer, Integer> dir0 = new Pair<Integer, Integer>(0,0);
    Position posBlue = new Position((double)paletteHeight*2,(double)(HardCodedParameters.defaultHeight/2)-(paletteWidth/2));
    this.blue = new Palette(posBlue, (double)0, dir0, paletteWidth, paletteHeight, Player.BLUE, 15, 10);

    Position posRed = new Position(HardCodedParameters.defaultWidth-(paletteHeight*4),(double)(HardCodedParameters.defaultHeight/2)-(paletteWidth/2));
    this.red = new Palette(posRed, (double)0, dir0, paletteWidth, paletteHeight, Player.RED, 15, 10);
    position = new Position(0,0);
    mainBall = new Ball(new Position(200,200), 1, direction, 10, Player.RED);
    creaBall = new Create(new Position(200,100), 1, direction, 15, Player.NONE);
  }

  @Override
  public Create getCreaBall() {
    return creaBall;
  }

  @Override
  public void setCreaBall(Create creaBall) {
    this.creaBall = creaBall;
  }

  @Override
  public Palette getBlue() {
    return blue;
  }

  @Override
  public void setBlue(Palette blue) {
    this.blue = blue;
  }

  @Override
  public Palette getRed() {
    return red;
  }

  @Override
  public void setRed(Palette red) {
    this.red = red;
  }

  @Override
  public int getStepNumber(){ return stepNumber; }

  @Override
  public double getRayon(){return mainBall.getRayon();}

  @Override
  public Position getMainBallPosition(){return mainBall.getPosition();}

  @Override
  public double getSpeed(){return mainBall.getSpeed();}

  @Override
  public Pair<Integer,Integer> getDirection(){return mainBall.getDirection();}

  @Override
  public void setStepNumber(int n){ stepNumber=n; }

  @Override
  public void setBluePosition(Position p){
    this.blue.setPosition(p);
  }

  @Override
  public Position getBluePosition(){
    return blue.getPosition();
  }

  @Override
  public void setRedPosition(Position p){
    this.red.setPosition(p);
  }

  @Override
  public Position getRedPosition(){
    return red.getPosition();
  }
  public void setRayon(double n){ mainBall.setRayon(n);}

  @Override
  public void setMainBallPosition(Position p){
    mainBall.setPosition(p);
  }

  @Override
  public void setSpeed(double speed){mainBall.setSpeed(speed);}

  @Override
  public void setDirection(Pair<Integer, Integer> direction){
    mainBall.setDirection(direction);
  }

  @Override
  public Ball getMainBall(){ return mainBall; }

  @Override
  public void setMainBall(Ball ball){ mainBall=new Ball(ball.getPosition(), 0, direction, 10, Player.RED); }
}
