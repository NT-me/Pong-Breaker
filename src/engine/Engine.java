/* ******************************************************
 * Project alpha - Composants logiciels 2015.
 * Copyright (C) 2015 <Binh-Minh.Bui-Xuan@ens-lyon.org>.
 * GPL version>=3 <http://www.gnu.org/licenses/>.
 * $Id: engine/Engine.java 2015-03-11 buixuan.
 * ******************************************************/
package engine;

import data.*;

import javafx.util.Pair;
import tools.HardCodedParameters;
import tools.User;
import tools.Position;

import specifications.EngineService;
import specifications.DataService;
import specifications.RequireDataService;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class Engine implements EngineService, RequireDataService{
  private Timer engineClock;
  private DataService data;
  private User.COMMAND command;
  private boolean moveLeft,moveRight,moveUp,moveDown;
  private boolean RmoveLeft,RmoveRight,RmoveUp,RmoveDown;
  private boolean RcreaBallLaucnh, BcreaBallLaunch;
  private boolean RcreaBallToBrick, BcreaBallToBrick;
  private double BcreaVX,BcreaVY;
  private double RcreaVX,RcreaVY;

  public Engine(){}

  @Override
  public void bindDataService(DataService service){
    data=service;
  }
  
  @Override
  public void init(){
    engineClock = new Timer();
    moveLeft = false;
    moveRight = false;
    moveUp = false;
    moveDown = false;
    command = User.COMMAND.NONE;

    int chooseSide = new Random().nextInt(2);
    if (chooseSide == 0){
      data.setMainBallDirection(new Position(HardCodedParameters.paletteHeight-0.1,0));
    }
    else if (chooseSide == 1){
      data.setMainBallDirection(new Position(-HardCodedParameters.paletteHeight-0.1,0));
    }

    BcreaVX = 10;
    BcreaVY = 0;

    RcreaVX = -10;
    RcreaVY = 0;
  }

  @Override
  public void start(){
    engineClock.schedule(new TimerTask(){
      public void run() {

        updateSpeedPalette();
        updateCommandPalette();
        updatePositionPalette();
        updateSpeedBall();
        updatePositionBall();
        updateCreaBallState();
        updatePositionCreaBall();

        if (collisionPaletteMainBall(data.getBlue())){
          data.setMainBallDirection(new Position(data.getBlueDirection().x+10,data.getBlueDirection().y));
          if (data.getSpeed() <= 1.3){
            data.setSpeed(data.getSpeed()*1.08);
          }
          data.setBlueDirection(new Position(0,0));
          data.getMainBall().setPlayer(Player.BLUE);
        }
        if (collisionPaletteMainBall(data.getRed())){
          data.setMainBallDirection(new Position(data.getRedDirection().x-10,data.getRedDirection().y));
          if (data.getSpeed() <= 1.3){
            data.setSpeed(data.getSpeed()*1.08);
          }
          data.setRedDirection(new Position(0,0));
          data.getMainBall().setPlayer(Player.RED);
        }
        if (collisionWallMainBall()){
          data.setMainBallDirection(new Position(data.getMainBallDirection().x,-data.getMainBallDirection().y));
        }
        if (collisionGoalMainBall()){
          System.out.println(data.getMainBall().getPlayer() + " a marqué");
          int chooseSide = new Random().nextInt(2);
          if (chooseSide == 0){
            data.setMainBallDirection(new Position(HardCodedParameters.paletteHeight-0.1,0));
          }
          else if (chooseSide == 1){
            data.setMainBallDirection(new Position(-HardCodedParameters.paletteHeight-0.1,0));
          }
          data.setMainBallPosition(new Position(HardCodedParameters.defaultWidth/2,HardCodedParameters.defaultHeight/2));
        }
        for (Brick bri : data.getBricks()){
          if (collisionBallBrick(data.getMainBall(), bri)){
            data.setMainBallDirection(new Position(-data.getMainBallDirection().x,-data.getMainBallDirection().y));
          }
        }
        data.setStepNumber(data.getStepNumber()+1);
      }
    },0,HardCodedParameters.enginePaceMillis);
  }

  @Override
  public void stop(){
    engineClock.cancel();
  }

  @Override
  public void setPlayerCommand(User.COMMAND c){
    if (c==User.COMMAND.LEFT) moveLeft=true;
    if (c==User.COMMAND.RIGHT) moveRight=true;
    if (c==User.COMMAND.UP) moveUp=true;
    if (c==User.COMMAND.DOWN) moveDown=true;

    if (c==User.COMMAND.RLEFT) RmoveLeft=true;
    if (c==User.COMMAND.RRIGHT) RmoveRight=true;
    if (c==User.COMMAND.RUP) RmoveUp=true;
    if (c==User.COMMAND.RDOWN) RmoveDown=true;

    if (c==User.COMMAND.BLAUNCHCREATE) BcreaBallLaunch =true;
    if (c==User.COMMAND.RLAUNCHCREATE) RcreaBallLaucnh =true;

    if (c==User.COMMAND.BCREATEBRICK) BcreaBallToBrick =true;
    if (c==User.COMMAND.RCREATEBRICK) RcreaBallToBrick =true;
  }

  @Override
  public void releasePlayerCommand(User.COMMAND c){
    if (c==User.COMMAND.LEFT) moveLeft=false;
    if (c==User.COMMAND.RIGHT) moveRight=false;
    if (c==User.COMMAND.UP) moveUp=false;
    if (c==User.COMMAND.DOWN) moveDown=false;

    if (c==User.COMMAND.RLEFT) RmoveLeft=false;
    if (c==User.COMMAND.RRIGHT) RmoveRight=false;
    if (c==User.COMMAND.RUP) RmoveUp=false;
    if (c==User.COMMAND.RDOWN) RmoveDown=false;

    if (c==User.COMMAND.BLAUNCHCREATE) BcreaBallLaunch =false;
    if (c==User.COMMAND.RLAUNCHCREATE) RcreaBallLaucnh =false;

    if (c==User.COMMAND.BCREATEBRICK) BcreaBallToBrick =false;
    if (c==User.COMMAND.RCREATEBRICK) RcreaBallToBrick =false;
  }

  private void updateCreaBallState(){
    Position dir0 = new Position(0,0);
    if(RcreaBallLaucnh){
      Position centR = new Position(data.getRed().getPosition().x+data.getRed().getHeight(), data.getRed().getPosition().y+(data.getRed().getWidth())/2);
      data.setRcreaBall(new Create(centR, 1, dir0, 5, Player.RED));
    }

    if(RcreaBallToBrick){
      createBrick(data.getRcreaBall());
      data.setRcreaPosition(new Position(-200,-200));
      System.out.println(data.getRcreaBall().getPosition());
    }

    if(BcreaBallLaunch){
      Position centR = new Position(data.getBlue().getPosition().x, (data.getBlue().getPosition().y+data.getBlue().getHeight())/2);
      data.setBcreaBall(new Create(centR, 1, dir0, 5, Player.BLUE));
    }

    if(BcreaBallToBrick){
      createBrick(data.getBcreaBall());
    }
  }

  private void updatePositionCreaBall(){
    try{
      data.setRcreaPosition(new Position(data.getRcreaBall().getPosition().x+RcreaVX,data.getRcreaBall().getPosition().y+RcreaVY));
    }
    catch (NullPointerException E){

    }

    try{
      data.setBcreaPosition(new Position(data.getBcreaBall().getPosition().x+BcreaVX,data.getBcreaBall().getPosition().y+BcreaVY));
    }
    catch (NullPointerException E){

    }
  }

  private void updateSpeedPalette(){
    data.setBlueDirection(new Position(data.getBlueDirection().x*HardCodedParameters.friction,data.getBlueDirection().y*HardCodedParameters.friction));
    data.setRedDirection(new Position(data.getRedDirection().x*HardCodedParameters.friction,data.getRedDirection().y*HardCodedParameters.friction));
  }

  private void updateCommandPalette(){
    if (moveLeft) data.setBlueDirection(new Position(data.getBlueDirection().x-HardCodedParameters.paletteStep,data.getBlueDirection().y));
    if (moveRight) data.setBlueDirection(new Position(data.getBlueDirection().x+HardCodedParameters.paletteStep,data.getBlueDirection().y));
    if (moveUp) data.setBlueDirection(new Position(data.getBlueDirection().x,data.getBlueDirection().y-HardCodedParameters.paletteStep));
    if (moveDown) data.setBlueDirection(new Position(data.getBlueDirection().x,data.getBlueDirection().y+HardCodedParameters.paletteStep));

    if (RmoveLeft) data.setRedDirection(new Position(data.getRedDirection().x-HardCodedParameters.paletteStep,data.getRedDirection().y));
    if (RmoveRight) data.setRedDirection(new Position(data.getRedDirection().x+HardCodedParameters.paletteStep,data.getRedDirection().y));
    if (RmoveUp) data.setRedDirection(new Position(data.getRedDirection().x,data.getRedDirection().y-HardCodedParameters.paletteStep));
    if (RmoveDown) data.setRedDirection(new Position(data.getRedDirection().x,data.getRedDirection().y+HardCodedParameters.paletteStep));
  }

  private void updatePositionPalette(){
    data.setBluePosition(new Position(data.getBluePosition().x + data.getBlueDirection().x, data.getBluePosition().y + data.getBlueDirection().y));
    data.setRedPosition(new Position(data.getRedPosition().x + data.getRedDirection().x, data.getRedPosition().y + data.getRedDirection().y));
}

  private void updateSpeedBall(){
    if (data.getSpeed() > 1){
      data.setSpeed(data.getSpeed()*0.99);
    }
    data.setMainBallDirection(new Position(data.getMainBallDirection().x*data.getSpeed(),data.getMainBallDirection().y*data.getSpeed()));
  }

  private void updatePositionBall(){

    if (data.getMainBallDirection().x >= HardCodedParameters.paletteHeight){
      data.setMainBallDirection(new Position(HardCodedParameters.paletteHeight-0.1,data.getMainBallDirection().y));
    }
    if (data.getMainBallDirection().x <= -HardCodedParameters.paletteHeight){
      data.setMainBallDirection(new Position(-(HardCodedParameters.paletteHeight-0.1),data.getMainBallDirection().y));
    }
    data.setMainBallPosition(new Position(data.getMainBallPosition().x+data.getMainBallDirection().x,data.getMainBallPosition().y+data.getMainBallDirection().y));

  }

  private boolean collisionPaletteMainBall(Palette pal){
    Ball mainBall = data.getMainBall();
    Position NO = pal.getPosition();
    Position NE = new Position(NO.x+pal.getHeight(), NO.y);
    Position SE = new Position(NO.x+pal.getHeight(),NO.y+ pal.getWidth());
    Position SO = new Position(NO.x,NO.y+ pal.getWidth());

    return pointInRectangle(NO, NE, SE, SO, mainBall.getPosition())
            || intersectCercle(mainBall.getPosition(), mainBall.getRayon(), NO, NE)
            || intersectCercle(mainBall.getPosition(), mainBall.getRayon(), NE, SE)
            || intersectCercle(mainBall.getPosition(), mainBall.getRayon(), SE, SO)
            || intersectCercle(mainBall.getPosition(), mainBall.getRayon(), SO, NO);
  }

  private double crossproduct(Position p, Position q, Position s, Position t){
    return (((q.x-p.x)*(t.y-s.y))-((q.y-p.y)*(t.x-s.x)));
  }

  public double dotProd(Position p, Position q, Position s, Position t){
    return ((q.x-p.x)*(t.x-s.x)+(q.y-p.y)*(t.y-s.y));
  }

  /**
   * Soit AB une droite et C quelconque
   */
  public static Position getPerpendicular(Position A, Position B, Position C){
    double vx = B.x-A.x; //x du vecteur AB
    double vy = B.y-A.y; //y du vecteur AB
    double ab2 = vx*vx + vy*vy; //norme au carré de AB
    double u = ((C.x - A.x) * vx + (C.y - A.y) * vy) / (ab2);
    return new Position ((int)Math.round(A.x + u * vx), (int)Math.round(A.y + u * vy)); //D appartient à AB
  }

  public boolean intersectCercle(Position centreCercle, double rayonCercle, Position A, Position B ){
    Position intersectPoint = getPerpendicular(A, B, centreCercle);
    if (0 <= dotProd(A, B, A, intersectPoint) && dotProd(A, B, A, intersectPoint) <= dotProd(A, B, A, B)){
      if (centreCercle.distance(intersectPoint) <= rayonCercle){
        return true;
      }
    }

    return false;
  }

  /**
   * Soit ABCD les points du rectangle et P le centre du cercle et rayon le rayon du cercle
   */
  private boolean pointInRectangle(Position A, Position B, Position C, Position D, Position P){
    // 0 ≤ AP·AB ≤ AB·AB and 0 ≤ AP·AD ≤ AD·AD
    double firstCP = dotProd(A, P, A, B);
    double firstComp = dotProd(A, B, A, B);

    double secondCP = dotProd(A, P, A, D);
    double secondComp = dotProd(A, D, A, D);

    return (0 <= firstCP && firstCP <= firstComp) && (0 <= secondCP && secondCP <= secondComp);

  }

  private boolean collisionBallBrick(Ball b, Brick bri){
    Position NO = bri.getPosition();
    Position NE = new Position(NO.x+90, NO.y);
    Position SE = new Position(NO.x+90,NO.y+ 80);
    Position SO = new Position(NO.x,NO.y+ 80);

    return pointInRectangle(NO, NE, SE, SO, b.getPosition())
            || intersectCercle(b.getPosition(), b.getRayon(), NO, NE)
            || intersectCercle(b.getPosition(), b.getRayon(), NE, SE)
            || intersectCercle(b.getPosition(), b.getRayon(), SE, SO)
            || intersectCercle(b.getPosition(), b.getRayon(), SO, NO)
            && b.getPlayer() != bri.getColor();
  }

  private boolean collisionWallMainBall(){
    Ball mainBall = data.getMainBall();
    Wall north = data.getNorth();
    Wall south = data.getSouth();
    double circleDistanceNorth = mainBall.getPosition().y-north.getPosition().y;
    double circleDistanceSouth = south.getPosition().y-mainBall.getPosition().y;

    if (circleDistanceNorth > (20 + mainBall.getRayon())) {
      if (circleDistanceSouth > (20 + mainBall.getRayon())){
        return false;
      }
      else
        return true;
    }
    else
      return true;
  }

  private boolean collisionGoalMainBall(){
    Ball mainBall = data.getMainBall();
    Goal goalBlue = data.getWest();
    Goal goalRed = data.getEast();
    double circleDistanceBlue = mainBall.getPosition().x-goalBlue.getPosition().x;
    double circleDistanceRed = goalRed.getPosition().x-mainBall.getPosition().x;

    if (circleDistanceBlue <= (0 + mainBall.getRayon()))
        return true;
    if (circleDistanceRed <= (0 + mainBall.getRayon()))
        return true;
    return false;
  }
    
  public void createBrick(Ball b) {
    int x = (int) (b.getPosition().x - 320) / 80;// position exploitable sur les x
    int y = (int) (b.getPosition().y / 90);//position exploitable sur les y

  if(b.getPosition().x > HardCodedParameters.defaultWidth / 4 && b.getPosition().x < 3*HardCodedParameters.defaultWidth / 4) {
      if (b.getPosition().x < HardCodedParameters.defaultWidth / 2
              && b.getPlayer() == Player.BLUE
              && data.getMatrice()[x][y] != 1) {
        data.setMatrice(x, y, 1);
        Brick brick = new Brick(Player.BLUE, true);
        brick.setPosition(new Position(320 + (x * 80), y * 90));
        data.getBricks().add(brick);
      }
      if (b.getPosition().x > HardCodedParameters.defaultWidth / 2
              && b.getPlayer() == Player.RED
              && data.getMatrice()[x][y] != 1) {
        data.setMatrice(x, y, 1);
        Brick brick = new Brick(Player.RED, true);
        brick.setPosition(new Position(320 + (x * 80), y * 90));
        System.out.println(brick.getPosition());
        data.getBricks().add(brick);
      }
    }
  }
}
