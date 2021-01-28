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
  private Create BcreaBall;
  private Create RcreaBall;

  public Data(){

  }
  private int stepNumber;
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

  private Position NO;
  private Position NE;
  private Position SO;
  private Position SE;

  private Wall north;
  private Wall south;
  private Goal east;
  private Goal west;

  @Override
  public void init(){
    NO = new Position(0,0);
    NE = new Position(HardCodedParameters.defaultWidth,0);
    SO = new Position(0,HardCodedParameters.defaultHeight);
    SE = new Position(HardCodedParameters.defaultWidth,HardCodedParameters.defaultHeight);

    north = new Wall(NO, NE, true, "");
    south = new Wall(SO, SE, true, "");
    west = new Goal(NO, SO, true, "", Player.BLUE);
    east = new Goal(NE, SE, true, "", Player.RED);

    matrice = new int[4][8];
    bricks = new ArrayList<>();
    direction = new Pair<Integer,Integer>(0,0);

    stepNumber = 0;
    double paletteWidth = 200;
    double paletteHeight = HardCodedParameters.paletteHeight;
    Pair<Integer, Integer> dir0 = new Pair<Integer, Integer>(0,0);
    Position posBlue = new Position((double)paletteHeight*2,(double)(HardCodedParameters.defaultHeight/2)-(paletteWidth/2));
    this.blue = new Palette(posBlue, (double)0, dir0, paletteWidth, paletteHeight, Player.BLUE, 15, 10);

    Position posRed = new Position(HardCodedParameters.defaultWidth-(paletteHeight*4),(double)(HardCodedParameters.defaultHeight/2)-(paletteWidth/2));
    this.red = new Palette(posRed, (double)0, dir0, paletteWidth, paletteHeight, Player.RED, 15, 10);
    position = new Position(0,0);
    mainBall = new Ball(new Position(HardCodedParameters.defaultWidth/2,HardCodedParameters.defaultHeight/2), 1, direction, 10, Player.BLUE);
  }

  @Override
  public Create getBcreaBall() {
    return BcreaBall;
  }

  @Override
  public void setBcreaBall(Create bcreaBall) {
    this.BcreaBall = bcreaBall;
  }

  @Override
  public Create getRcreaBall() {
    return RcreaBall;
  }

  @Override
  public void setRcreaBall(Create rcreaBall) {
    this.RcreaBall = rcreaBall;
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
  public Wall getNorth(){ return north; }

  @Override
  public Wall getSouth(){ return south; }

  @Override
  public Goal getEast(){ return east; }

  @Override
  public Goal getWest(){ return west; }

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
    if(p.x <= HardCodedParameters.defaultWidth/6)
      if(p.x >= west.getPosition().x)
        this.blue.setPosition(p);

  }

  @Override
  public Position getBluePosition(){
    return blue.getPosition();
  }

  @Override
  public void setRedPosition(Position p){
    if(p.x >= 5*HardCodedParameters.defaultWidth/6)
      if(p.x <= east.getPosition().x-5)
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
