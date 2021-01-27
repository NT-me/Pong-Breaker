/* ******************************************************
 * Project alpha - Composants logiciels 2015.
 * Copyright (C) 2015 <Binh-Minh.Bui-Xuan@ens-lyon.org>.
 * GPL version>=3 <http://www.gnu.org/licenses/>.
 * $Id: engine/Engine.java 2015-03-11 buixuan.
 * ******************************************************/
package engine;

import data.Ball;
import data.Brick;
import data.Palette;
import data.Wall;
import data.Goal;

import tools.HardCodedParameters;
import tools.User;
import tools.Position;
import tools.Sound;

import specifications.EngineService;
import data.Player;
import specifications.DataService;
import specifications.EngineService;
import specifications.RequireDataService;
import tools.HardCodedParameters;
import tools.Position;
import tools.User;

import javafx.util.Pair;

import java.util.Timer;
import java.util.TimerTask;

public class Engine implements EngineService, RequireDataService{
  private Timer engineClock;
  private DataService data;
  private User.COMMAND command;
  private boolean moveLeft,moveRight,moveUp,moveDown;
  private boolean RmoveLeft,RmoveRight,RmoveUp,RmoveDown;
  private boolean creaBallToBrick;
  private double blueVX,blueVY,redVX,redVY;
  private double ballVX,ballVY;
  private double creaVX,creaVY;

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

    ballVX = 0;
    ballVY = 0;

    creaVX = 0;
    creaVY = 0;
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
        if (collisionPaletteMainBall(data.getBlue())){
          ballVX = blueVX;
          ballVY = blueVY;
          blueVX = 0;
          blueVY = 0;
          data.getMainBall().setPlayer("j1");

        }
        if (collisionPaletteMainBall(data.getRed())){
          ballVX = redVX;
          ballVY = redVY;
          redVX = 0;
          redVY = 0;
          data.getMainBall().setPlayer("j2");
        }
        if (collisionWallMainBall()){
          data.setMainBallPosition(new Position(data.getMainBallPosition().x,data.getMainBallPosition().y-ballVY));
          //data.getMainBall().setDirection(new Pair<Integer,Integer>(data.getMainBall().getDirection().getKey(),data.getMainBall().getDirection().getValue()*0));
        }
        if (collisionGoalMainBall()){
          System.out.println(data.getMainBall().getPlayer() + " a marqué");
          data.setMainBallPosition(new Position(HardCodedParameters.defaultWidth/2,HardCodedParameters.defaultHeight/2));
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

    if (c==User.COMMAND.CREATE) creaBallToBrick=true;
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

    if (c==User.COMMAND.CREATE) creaBallToBrick=false;
  }

  private void updateCreaBallState(){
    createBrick(data.getCreaBall());
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
    if (data.getNorth().getPosition().y <= data.getBluePosition().y){
      if (data.getSouth().getPosition().y >= data.getBluePosition().y)
        data.setBluePosition(new Position(data.getBluePosition().x+blueVX,data.getBluePosition().y+blueVY));
      else
        data.setBluePosition(new Position(data.getBluePosition().x,data.getBluePosition().y-10));
    }
    else
      data.setBluePosition(new Position(data.getBluePosition().x,data.getBluePosition().y+10));


    if (data.getNorth().getPosition().y <= data.getRedPosition().y){
      if (data.getSouth().getPosition().y >= data.getRedPosition().y)
        data.setRedPosition(new Position(data.getRedPosition().x+redVX,data.getRedPosition().y+redVY));
      else
        data.setRedPosition(new Position(data.getRedPosition().x,data.getRedPosition().y-10));
    }
    else
      data.setRedPosition(new Position(data.getRedPosition().x,data.getRedPosition().y+10));


  }

  private void updateSpeedBall(){
    ballVX*=data.getSpeed();
    ballVY*=data.getSpeed();

  }

  private void updatePositionBall(){
    data.setMainBallPosition(new Position(data.getMainBallPosition().x+ballVX,data.getMainBallPosition().y+ballVY));
    //if (data.getHeroesPosition().x<0) data.setHeroesPosition(new Position(0,data.getHeroesPosition().y));
  }

  private boolean collisionPaletteMainBall(Palette pal){
    Ball mainBall = data.getMainBall();
    double circleDistanceX = Math.abs(mainBall.getPosition().x-pal.getPosition().x);
    double circleDistanceY = Math.abs(mainBall.getPosition().y-pal.getPosition().y);

    if (circleDistanceX > (pal.getHeight()/2 + mainBall.getRayon())) { return false; }
    if (circleDistanceY > (pal.getWidth()/2 + mainBall.getRayon())) { return false; }

    if (circleDistanceX <= (pal.getHeight()/2)) { return true; }
    if (circleDistanceY <= (pal.getWidth()/2)) { return true; }

    double cornerDistance_sq = (circleDistanceX - pal.getWidth()/2)*(circleDistanceX - pal.getWidth()/2) + (circleDistanceY - pal.getHeight()/2)*(circleDistanceY - pal.getHeight()/2);

    return (cornerDistance_sq <= (mainBall.getRayon())*(mainBall.getRayon()));
  }

  private boolean collisionWallMainBall(){
    Ball mainBall = data.getMainBall();
    Wall north = data.getNorth();
    Wall south = data.getSouth();
    double circleDistanceNorth = Math.abs(mainBall.getPosition().y-north.getPosition().y);
    double circleDistanceSouth = Math.abs(south.getPosition().y-mainBall.getPosition().y);

    if (circleDistanceNorth > (0 + mainBall.getRayon())) {
      if (circleDistanceSouth > (0 + mainBall.getRayon())){
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
    double circleDistanceBlue = Math.abs(mainBall.getPosition().x-goalBlue.getPosition().x);
    double circleDistanceRed = Math.abs(goalRed.getPosition().x-mainBall.getPosition().x);

    if (circleDistanceBlue > (0 + mainBall.getRayon())) {
      if (circleDistanceRed > (0 + mainBall.getRayon())){
        return false;
      }
      else
        return true;
    }
    else
      return true;
  }
    
  public void createBrick(Ball b) {
    int x = (int) (b.getPosition().x - 320) / 80;// position exploitable sur les x
    int y = (int) (b.getPosition().y / 90);//position exploitable sur les y

    if (b.getPosition().x < HardCodedParameters.defaultWidth / 2 && b.getPlayer() == Player.BLUE
            && data.getMatrice()[x][y] != 1) {
      data.setMatrice(x, y, 1);
      Brick brick = new Brick(Player.BLUE, true);
      brick.setPosition(new Position(320+(x*80),y*90));
      data.getBricks().add(brick);
    }
    if (b.getPosition().x > HardCodedParameters.defaultWidth / 2 && b.getPlayer() == Player.RED
            && data.getMatrice()[x][y] != 1) {
      data.setMatrice(x, y, 1);
      Brick brick = new Brick(Player.RED, true);
      brick.setPosition(new Position(640+(x*80),y*90));
      data.getBricks().add(brick);
    }

  }
}
