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
import data.Player;
import specifications.DataService;
import specifications.EngineService;
import specifications.RequireDataService;
import tools.HardCodedParameters;
import tools.Position;
import tools.User;

import java.util.Timer;
import java.util.TimerTask;

public class Engine implements EngineService, RequireDataService{
  private static final double friction=HardCodedParameters.friction;
  private Timer engineClock;
  private DataService data;
  private User.COMMAND command;
  private boolean moveLeft,moveRight,moveUp,moveDown;
  private boolean RmoveLeft,RmoveRight,RmoveUp,RmoveDown;
  private double blueVX,blueVY,redVX,redVY;
  private double ballVX,ballVY;

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
        if (collisionPaletteMainBall(data.getBlue())){
          ballVX = blueVX;
          ballVY = blueVY;
          blueVX = 0;
          blueVY = 0;

        }
        if (collisionPaletteMainBall(data.getRed())){
          ballVX = redVX;
          ballVY = redVY;
          redVX = 0;
          redVY = 0;
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
  }

  public void createBrickBlue(User.COMMAND create) {
    createBrick();
  }

  public void createBrickRed(User.COMMAND create) {
    createBrick();
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
    data.setBluePosition(new Position(data.getBluePosition().x+blueVX,data.getBluePosition().y+blueVY));
    data.setRedPosition(new Position(data.getRedPosition().x+redVX,data.getRedPosition().y+redVY));
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

    if (circleDistanceX > (pal.getWidth()/2 + mainBall.getRayon())) { return false; }
    if (circleDistanceX > (pal.getHeight()/2 + mainBall.getRayon())) { return false; }

    if (circleDistanceX <= (pal.getWidth()/2)) { return true; }
    if (circleDistanceY <= (pal.getHeight()/2)) { return true; }

    double cornerDistance_sq = (circleDistanceX - pal.getWidth()/2)*(circleDistanceX - pal.getWidth()/2) + (circleDistanceY - pal.getHeight()/2)*(circleDistanceY - pal.getHeight()/2);

    return (cornerDistance_sq <= (mainBall.getRayon())*(mainBall.getRayon()));
  }

  public void createBrickBlue(Ball b) {
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
