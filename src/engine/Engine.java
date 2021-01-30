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

import java.util.ArrayList;
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
  private boolean RdestBallLaucnh, BdestBallLaunch;
  private boolean RcreaBallToBrick, BcreaBallToBrick;
  private double blueVX,blueVY,redVX,redVY;
  private double ballVX,ballVY;
  private double BcreaVX,BcreaVY;
  private double RcreaVX,RcreaVY;
  private double RdestVX,RdestVY;
  private double BdestVX,BdestVY;

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

    blueVX = 0;
    blueVY = 0;

    redVX = 0;
    redVY = 0;
    int chooseSide = new Random().nextInt(2);
    if (chooseSide == 0){
      ballVX = HardCodedParameters.paletteHeight-0.1;
    }
    else if (chooseSide == 1){
      ballVX = -(HardCodedParameters.paletteHeight-0.1);
    }
    ballVY = 0;

    BcreaVX = 10;
    BcreaVY = 0;

    RcreaVX = -10;
    RcreaVY = 0;

    RdestVX = -20;
    RdestVY = 0;

    BdestVX = 20;
    BdestVY = 0;
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
        updateDestBallState();
        updatePositionDestBall();

        Palette[] tabPalette = {data.getBlue(), data.getRed()};

        if (collisionPaletteBalls(data.getBlue(), data.getMainBall())){
          ballVX = blueVX+10;
          ballVY = blueVY;
          if (data.getSpeed() <= 1.3){
            data.setSpeed(data.getSpeed()*1.08);
          }
          blueVX = 0;
          blueVY = 0;
          data.setMainBallPlayer(Player.BLUE);
        }
        if (collisionPaletteBalls(data.getRed(), data.getMainBall())){
          ballVX = redVX-10;
          ballVY = redVY;
          if (data.getSpeed() <= 1.3){
            data.setSpeed(data.getSpeed()*1.08);
          }
          redVX = 0;
          redVY = 0;
          data.setMainBallPlayer(Player.RED);
        }

        Destructive[] tabdestBall = {data.getRdestBall(), data.getBdestBall()};
        for (Palette pals : tabPalette){
          if (pals.getRespawnCoolDown() != -1){
            if (pals.getRespawnCoolDown() == 0){
              if (pals.getPlayer() == Player.BLUE){
                pals.setPosition(new Position((double)pals.getHeight()*2,(double)(HardCodedParameters.defaultHeight/2)-(pals.getWidth()/2)));
              }
              if (pals.getPlayer() == Player.RED){
                pals.setPosition(new Position(HardCodedParameters.defaultWidth-(pals.getHeight()*4),(double)(HardCodedParameters.defaultHeight/2)-(pals.getWidth()/2)));
              }
            }
            pals.setRespawnCoolDown(pals.getRespawnCoolDown()-1);
          }
          for (Destructive dests : tabdestBall){
            if(collisionPaletteBalls(pals, dests)){
              if (pals.getPV() > 0){
                pals.setPV(pals.getPV()-1);
                if (pals.getPV() <= 0){
                  pals.setPV((HardCodedParameters.palettePV));
                  pals.setRespawnCoolDown(HardCodedParameters.paletteCooldown);
                  pals.setPosition(new Position(-200,-200));
                }
              }
              dests.setPosition(new Position(-200, -200));
            }
          }
        }
        if (collisionWallMainBall()){
            ballVY = -ballVY;
        }
        if (collisionGoalMainBall()){
          System.out.println(data.getMainBall().getPlayer() + " a marqué");
          ballVY = 0;
          data.setMainBallPlayer(Player.NONE);
          int chooseSide = new Random().nextInt(2);
          if (chooseSide == 0){
            ballVX = HardCodedParameters.paletteHeight-0.1;
          }
          else if (chooseSide == 1){
            ballVX = -(HardCodedParameters.paletteHeight-0.1);
          }
          data.setMainBallPosition(new Position(HardCodedParameters.defaultWidth/2,HardCodedParameters.defaultHeight/2));
        }

        ArrayList<Brick> bricksList = (ArrayList<Brick>) data.getBricks().clone();
        for (Brick bri : data.getBricks()){
          if (collisionBallBrick(data.getMainBall(), bri)){
            ballVX = -ballVX;
            ballVY = -ballVY;
          }
          if (collisionBallBrick(data.getBdestBall(), bri)){
            bri.setPv(bri.getPv()-1);
            if (bri.getPv() <= 0){
              bricksList.remove(bri);
            }
            data.setdestBallsPos(new Position(-200, -200), Player.BLUE);
          }
          if (collisionBallBrick(data.getRdestBall(), bri)){
            bri.setPv(bri.getPv()-1);
            if (bri.getPv() <= 0){
              bricksList.remove(bri);
            }
            data.setdestBallsPos(new Position(-200, -200), Player.RED);
          }
        }
        data.setBricks(bricksList);
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

    if (c==User.COMMAND.BLAUNCHDEST) BdestBallLaunch =true;
    if (c==User.COMMAND.RLAUNCHDEST) RdestBallLaucnh =true;
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

    if (c==User.COMMAND.BLAUNCHDEST) BdestBallLaunch =false;
    if (c==User.COMMAND.RLAUNCHDEST) RdestBallLaucnh =false;
  }

  private void updateDestBallState(){
    Pair<Integer, Integer> dir0 = new Pair<Integer, Integer>(0,0);
    if(RdestBallLaucnh){
      Position centR = new Position(data.getRed().getPosition().x,
              data.getRed().getPosition().y+(data.getRed().getWidth())/2);
      data.setRdestBall(new Destructive(centR, 1, dir0, 5, Player.RED));
    }

    if(BdestBallLaunch){
      Position centR = new Position(data.getBlue().getPosition().x+data.getBlue().getHeight(), data.getBlue().getPosition().y+(data.getBlue().getWidth())/2);
      data.setBdestBall(new Destructive(centR, 1, dir0, 5, Player.BLUE));
    }
  }

  private void updateCreaBallState(){
    Pair<Integer, Integer> dir0 = new Pair<Integer, Integer>(0,0);
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
      Position centR = new Position(data.getBlue().getPosition().x+data.getBlue().getHeight(), data.getBlue().getPosition().y+(data.getBlue().getWidth())/2);
      data.setBcreaBall(new Create(centR, 1, dir0, 5, Player.BLUE));
    }

    if(BcreaBallToBrick){
      createBrick(data.getBcreaBall());
      data.setBcreaPosition(new Position(-200,-200));
    }
  }

  private void updatePositionCreaBall(){
    data.setRcreaPosition(new Position(data.getRcreaBall().getPosition().x+RcreaVX,data.getRcreaBall().getPosition().y+RcreaVY));
    data.setBcreaPosition(new Position(data.getBcreaBall().getPosition().x+BcreaVX,data.getBcreaBall().getPosition().y+BcreaVY));
  }

  private void updatePositionDestBall(){
    data.setdestBallsPos(new Position(data.getRdestBall().getPosition().x+RdestVX,
                                      data.getRdestBall().getPosition().y+RdestVY),
            Player.RED);

    data.setdestBallsPos(new Position(data.getBdestBall().getPosition().x+BdestVX,
                                      data.getBdestBall().getPosition().y+BdestVY),
            Player.BLUE);
  }

  private void updateSpeedPalette(){
    blueVX*=HardCodedParameters.friction;
    blueVY*=HardCodedParameters.friction;

    redVX*=HardCodedParameters.friction;
    redVY*=HardCodedParameters.friction;
  }

  private void updateCommandPalette(){
    if (moveLeft) blueVX-=HardCodedParameters.paletteStep;
    if (moveRight) blueVX+=HardCodedParameters.paletteStep;
    if (moveUp) blueVY-=HardCodedParameters.paletteStep;
    if (moveDown) blueVY+=HardCodedParameters.paletteStep;

    if (RmoveLeft) redVX-=HardCodedParameters.paletteStep;
    if (RmoveRight) redVX+=HardCodedParameters.paletteStep;
    if (RmoveUp) redVY-=HardCodedParameters.paletteStep;
    if (RmoveDown) redVY+=HardCodedParameters.paletteStep;
  }

  private void updatePositionPalette(){
    data.setBluePosition(new Position(data.getBluePosition().x + blueVX, data.getBluePosition().y + blueVY));
    data.setRedPosition(new Position(data.getRedPosition().x + redVX, data.getRedPosition().y + redVY));
}

  private void updateSpeedBall(){
    if (data.getSpeed() > 1){
      data.setSpeed(data.getSpeed()*0.99);
    }
    else{
      data.setSpeed(1);
    }
    ballVX*=data.getSpeed();
    ballVY*=data.getSpeed();
  }

  private void updatePositionBall(){

    if (ballVX >= HardCodedParameters.paletteHeight){
      ballVX = HardCodedParameters.paletteHeight-0.1;
    }
    if (ballVX <= -HardCodedParameters.paletteHeight){
      ballVX = -(HardCodedParameters.paletteHeight-0.1);
    }
    data.setMainBallPosition(new Position(data.getMainBallPosition().x+ballVX,data.getMainBallPosition().y+ballVY));

  }

  private boolean collisionPaletteBalls(Palette pal, Ball ba){
    Position NO = pal.getPosition();
    Position NE = new Position(NO.x+pal.getHeight(), NO.y);
    Position SE = new Position(NO.x+pal.getHeight(),NO.y+ pal.getWidth());
    Position SO = new Position(NO.x,NO.y+ pal.getWidth());

    return pointInRectangle(NO, NE, SE, SO, ba.getPosition())
            || intersectCercle(ba.getPosition(), ba.getRayon(), NO, NE)
            || intersectCercle(ba.getPosition(), ba.getRayon(), NE, SE)
            || intersectCercle(ba.getPosition(), ba.getRayon(), SE, SO)
            || intersectCercle(ba.getPosition(), ba.getRayon(), SO, NO);
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

    return (pointInRectangle(NO, NE, SE, SO, b.getPosition())
            || intersectCercle(b.getPosition(), b.getRayon(), NO, NE)
            || intersectCercle(b.getPosition(), b.getRayon(), NE, SE)
            || intersectCercle(b.getPosition(), b.getRayon(), SE, SO)
            || intersectCercle(b.getPosition(), b.getRayon(), SO, NO)
            ) && b.getPlayer() != bri.getColor();
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
