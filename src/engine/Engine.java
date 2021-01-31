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
import tools.PBUtils;
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

    data.getRcreaBall().setDirection(new Position(-10,0));
    data.getBcreaBall().setDirection(new Position(10,0));

    data.getRdestBall().setDirection(new Position(-10,0));
    data.getBdestBall().setDirection(new Position(10,0));
  }

  @Override
  public void start(){
    engineClock.schedule(new TimerTask(){
      public void run() {


        updateCommandPalette();
        updatePositionPalette();

        updateCreaBallState();
        updateDestBallState();

        data.getMainBall().updateSpeedBall();
        data.getMainBall().updatePositionBall();

        data.getRcreaBall().updatePositionCreaBall();
        data.getBcreaBall().updatePositionCreaBall();
        data.getRdestBall().updatePositionDestBall();
        data.getBdestBall().updatePositionDestBall();

        collisionsPalette();
        collisionsWallsAndGoal();
        collisionBricks();

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

  private void collisionBricks(){
    ArrayList<Brick> bricksList = (ArrayList<Brick>) data.getBricks().clone();
    for (Brick bri : data.getBricks()){
      if (Collisions.collisionBallBrick(data.getMainBall(), bri)){
        data.setMainBallDirection(new Position(-data.getMainBallDirection().x,-data.getMainBallDirection().y));
      }
      if (Collisions.collisionBallBrick(data.getBdestBall(), bri)){
        bri.setPv(bri.getPv()-1);
        if (bri.getPv() <= 0){
          bricksList.remove(bri);
          data.setMatrice(bri.getMatrixPos().getKey(), bri.getMatrixPos().getValue(), 0);
        }
        data.setdestBallsPos(new Position(-200, -200), Player.BLUE);
      }
      if (Collisions.collisionBallBrick(data.getRdestBall(), bri)){
        bri.setPv(bri.getPv()-1);
        if (bri.getPv() <= 0){
          bricksList.remove(bri);
          data.setMatrice(bri.getMatrixPos().getKey(), bri.getMatrixPos().getValue(), 0);
        }
        data.setdestBallsPos(new Position(-200, -200), Player.RED);
      }
    }
    data.setBricks(bricksList);
  }

  private void collisionsWallsAndGoal(){
    if (Collisions.collisionWallBall(data.getMainBall(), data.getNorth(), data.getSouth())){
      data.setMainBallDirection(new Position(data.getMainBallDirection().x,-data.getMainBallDirection().y));
    }
    if (Collisions.collisionGoalMainBall(data.getMainBall(), data.getWest(), data.getEast())){
      if (data.getMainBall().getPlayer() == Player.BLUE && data.getMainBallPosition().x > HardCodedParameters.defaultWidth/2){
        data.setScoreB(data.getScoreB() + 1);
      }
      else if (data.getMainBall().getPlayer() == Player.RED && data.getMainBallPosition().x < HardCodedParameters.defaultWidth/2){
        data.setScoreR(data.getScoreR() + 1);
      }
      data.setMainBallPlayer(Player.NONE);
      int chooseSide = new Random().nextInt(2);
      if (chooseSide == 0){
        data.setMainBallDirection(new Position(HardCodedParameters.paletteHeight-0.1,0));
      }
      else if (chooseSide == 1){
        data.setMainBallDirection(new Position(-HardCodedParameters.paletteHeight-0.1,0));
      }
      data.setMainBallPosition(new Position(HardCodedParameters.defaultWidth/2,HardCodedParameters.defaultHeight/2));
    }
  }

  private void collisionsPalette(){
    Palette[] tabPalette = {data.getBlue(), data.getRed()};
    Destructive[] tabdestBall = {data.getRdestBall(), data.getBdestBall()};

    for (Palette pals : tabPalette){
      pals.updateSpeedPalette();
      if (Collisions.collisionPaletteBalls(pals, data.getMainBall())){
        if (pals.getPlayer() == Player.BLUE){
          data.setMainBallDirection(new Position(data.getRedDirection().x+10,
                  data.getRedDirection().y));
        }
        else if (pals.getPlayer() == Player.RED){
          data.setMainBallDirection(new Position(data.getRedDirection().x-10,
                  data.getRedDirection().y));
        }

        if (data.getSpeed() <= 1.3){
          data.setSpeed(data.getSpeed()*1.08);
        }
        data.setBlueDirection(new Position(0, 0));
        data.setMainBallPlayer(pals.getPlayer());
      }


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
        if(Collisions.collisionPaletteBalls(pals, dests)){
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
  }

  private void updateDestBallState(){
    Position dir0 = new Position(0,0);
    if(RdestBallLaucnh){
      Position centR = new Position(data.getRed().getPosition().x,
              data.getRed().getPosition().y+(data.getRed().getWidth())/2);
      data.getRdestBall().setPosition(centR);
    }

    if(BdestBallLaunch){
      Position centR = new Position(data.getBlue().getPosition().x+data.getBlue().getHeight(),
              data.getBlue().getPosition().y+(data.getBlue().getWidth())/2);
      data.getBdestBall().setPosition(centR);
    }
  }

  private void updateCreaBallState(){
    if(RcreaBallLaucnh){
      Position centR = new Position(data.getRed().getPosition().x, data.getRed().getPosition().y+(data.getRed().getWidth())/2);
      data.setRcreaPosition(centR);
    }

    if(RcreaBallToBrick){
      createBrick(data.getRcreaBall());
      data.setRcreaPosition(new Position(-200,-200));
    }

    if(BcreaBallLaunch){
      Position centR = new Position(data.getBlue().getPosition().x+data.getBlue().getHeight(), data.getBlue().getPosition().y+(data.getBlue().getWidth())/2);
      data.setBcreaPosition(centR);
    }

    if(BcreaBallToBrick){
      createBrick(data.getBcreaBall());
      data.setBcreaPosition(new Position(-200,-200));
    }
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

  public void createBrick(Ball b) {
    int x = (int) (b.getPosition().x - 320) / 80;// position exploitable sur les x
    int y = (int) (b.getPosition().y / 90);//position exploitable sur les y
    Pair<Integer, Integer> matrixPos = new Pair<Integer, Integer>(x,y);

  if(b.getPosition().x > HardCodedParameters.defaultWidth / 4 && b.getPosition().x < 3*HardCodedParameters.defaultWidth / 4) {
      if (b.getPosition().x < HardCodedParameters.defaultWidth / 2
              && b.getPlayer() == Player.BLUE
              && data.getMatrice()[x][y] != 1) {
        data.setMatrice(x, y, 1);
        Brick brick = new Brick(Player.BLUE, true, matrixPos);
        brick.setPosition(new Position(320 + (x * 80), y * 90));
        data.getBricks().add(brick);
      }
      if (b.getPosition().x > HardCodedParameters.defaultWidth / 2
              && b.getPlayer() == Player.RED
              && data.getMatrice()[x][y] != 1) {
        data.setMatrice(x, y, 1);
        Brick brick = new Brick(Player.RED, true, matrixPos);
        brick.setPosition(new Position(320 + (x * 80), y * 90));
        data.getBricks().add(brick);
      }
    }
  }
}
