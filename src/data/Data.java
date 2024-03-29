/* ******************************************************
 * Project alpha - Composants logiciels 2015.
 * Copyright (C) 2015 <Binh-Minh.Bui-Xuan@ens-lyon.org>.
 * GPL version>=3 <http://www.gnu.org/licenses/>.
 * $Id: data/Data.java 2015-03-11 buixuan.
 * ******************************************************/
package data;

import javafx.geometry.Pos;
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

  private Destructive BdestBall;
  private Destructive RdestBall;

  private int stepNumber;
  private Position position;
  private Ball mainBall;
  private Position direction;
  private double speed;
  private ArrayList<Brick> bricks;

  private int scoreB;
  private int scoreR;
  public Data(){

  }

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

    scoreB = 0;
    scoreR = 0;

    north = new Wall(NO, NE, true, "");
    south = new Wall(SO, SE, true, "");
    west = new Goal(NO, SO, true, "", Player.BLUE);
    east = new Goal(NE, SE, true, "", Player.RED);

    matrice = new int[8][8];
    matrice[3][4] = 1;matrice[3][3] = 1;matrice[4][4] = 1;matrice[4][3] = 1;
    bricks = new ArrayList<>();
    direction = new Position(0,0);

    stepNumber = 0;
    double paletteWidth = 200;
    double paletteHeight = HardCodedParameters.paletteHeight;
    Position dir0 = new Position(0,0);
    Position posBlue = new Position(paletteHeight *2,(double)(HardCodedParameters.defaultHeight/2)-(paletteWidth/2));
    this.blue = new Palette(posBlue, 0, dir0, paletteWidth, paletteHeight, Player.BLUE, HardCodedParameters.palettePV, -1);

    Position posRed = new Position(HardCodedParameters.defaultWidth-(paletteHeight*4),(double)(HardCodedParameters.defaultHeight/2)-(paletteWidth/2));
    this.red = new Palette(posRed, 0, dir0, paletteWidth, paletteHeight, Player.RED, HardCodedParameters.palettePV, -1);
    position = new Position(0,0);
    mainBall = new Ball(new Position(HardCodedParameters.defaultWidth/2,HardCodedParameters.defaultHeight/2), 1, direction, 10, Player.BLUE);

    BcreaBall = new Create(new Position(-200, -200), 1, dir0, 5, Player.BLUE);
    RcreaBall = new Create(new Position(-200, -200), 1, dir0, 5, Player.RED);

    BdestBall = new Destructive(new Position(-200, -200), 1, dir0, 5, Player.BLUE);
    RdestBall = new Destructive(new Position(-200, -200), 1, dir0, 5, Player.RED);
  }

  @Override
  public int getScoreB() {
    return scoreB;
  }

  @Override
  public void setScoreB(int scoreB) {
    this.scoreB = scoreB;
  }

  @Override
  public int getScoreR() {
    return scoreR;
  }

  @Override
  public void setScoreR(int scoreR) {
    this.scoreR = scoreR;
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
  public Position getMainBallDirection(){return mainBall.getDirection();}

  @Override
  public Position getBlueDirection(){return blue.getDirection();}

  @Override
  public Position getRedDirection(){return red.getDirection();}

  @Override
  public void setStepNumber(int n){ stepNumber=n; }

  @Override
  public void setBluePosition(Position p){

    if (getNorth().getPosition().y > getBluePosition().y)
      p.y = getBluePosition().y +10;
    if (getSouth().getPosition().y < getBluePosition().y + getBlueWidth())
      p.y = getBluePosition().y -10;
    if (getBluePosition().x > HardCodedParameters.defaultWidth / 6)
      p.x = getBluePosition().x -10;
    if (getBluePosition().x < getWest().getPosition().x)
      p.x = getBluePosition().x +10;
    this.blue.setPosition(p);
  }

  @Override
  public void setBcreaPosition(Position p){
        this.BcreaBall.setPosition(p);
  }

  @Override
  public void setRcreaPosition(Position p){
        this.RcreaBall.setPosition(p);
  }

  @Override
  public Position getBluePosition(){
    return blue.getPosition();
  }

  @Override
  public double getBlueWidth(){
    return blue.getWidth();
  }

  @Override
  public double getRedWidth(){
    return red.getWidth();
  }

  @Override
  public void setRedPosition(Position p){

    if (getNorth().getPosition().y > getRedPosition().y)
      p.y = getRedPosition().y +10;
    if (getSouth().getPosition().y < getRedPosition().y + getRedWidth())
      p.y = getRedPosition().y -10;
    if (getRedPosition().x < 5*HardCodedParameters.defaultWidth / 6)
      p.x = getRedPosition().x +10;
    if (getRedPosition().x > getEast().getPosition().x)
      p.x = getRedPosition().x -10;
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
  public void setMainBallDirection(Position direction){
    mainBall.setDirection(direction);
  }

  @Override
  public void setBlueDirection(Position direction){
    blue.setDirection(direction);
  }

  @Override
  public void setRedDirection(Position direction){
    red.setDirection(direction);
  }

  @Override
  public Ball getMainBall(){ return mainBall; }

  @Override
  public void setMainBall(Ball ball){ mainBall=new Ball(ball.getPosition(), 0, direction, 10, ball.getPlayer()); }

  @Override
  public void setMainBallPlayer(Player p){
    mainBall.setPlayer(p);
  }

  @Override
  public  void setdestBallsPos(Position pos, Player pla){
    if (pla == Player.RED){
      this.RdestBall.setPosition(pos);
    }
    else if (pla == Player.BLUE){
      this.BdestBall.setPosition(pos);
    }
  }

  @Override
  public Destructive getBdestBall() {
    return BdestBall;
  }

  @Override
  public void setBdestBall(Destructive bdestBall) {
    BdestBall = bdestBall;
  }

  @Override
  public Destructive getRdestBall() {
    return RdestBall;
  }

  @Override
  public void setRdestBall(Destructive rdestBall) {
    RdestBall = rdestBall;
  }
}
