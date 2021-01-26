/* ******************************************************
 * Project alpha - Composants logiciels 2015.
 * Copyright (C) 2015 <Binh-Minh.Bui-Xuan@ens-lyon.org>.
 * GPL version>=3 <http://www.gnu.org/licenses/>.
 * $Id: data/Data.java 2015-03-11 buixuan.
 * ******************************************************/
package data;

import tools.HardCodedParameters;
import tools.Position;
import tools.Sound;

import specifications.DataService;

import javafx.util.Pair;
import java.util.ArrayList;

public class Data implements DataService{

  public Data(){}
  private int stepNumber;
  private double rayon;
  private Position position;
  private Ball mainBall;
  private Pair<Position,Position> direction;

  @Override
  public void init(){
    direction = new Pair<Position,Position>(new Position(200,200), new Position(210,210));
    stepNumber = 0;
    rayon = 0;
    position = new Position(0,0);
    mainBall = new Ball(new Position(200,200), 0, direction, 10, "j1");
  }

  @Override
  public int getStepNumber(){ return stepNumber; }

  @Override
  public double getRayon(){return rayon;}

  @Override
  public Position getPosition(){return position;}


  @Override
  public void setStepNumber(int n){ stepNumber=n; }

  @Override
  public void setRayon(double n){}

  @Override
  public void setPosition(Position p){}

  @Override
  public Ball getMainBall(){ return mainBall; }

  @Override
  public void setMainBall(Ball ball){ mainBall=new Ball(ball.getPosition(), 0, direction, 10, "j1"); }

}
