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

import java.util.ArrayList;

public class Data implements DataService{

  public Data(){}
  private int stepNumber;
  private Ball mainBall;

  @Override
  public void init(){

    stepNumber = 0;
    mainBall = new Ball(new Position(20,20), 0, 0, 10, "j1");
  }

  @Override
  public int getStepNumber(){ return stepNumber; }

  @Override
  public void setStepNumber(int n){ stepNumber=n; }

  @Override
  public Ball getMainBall(){ return mainBall; }

  @Override
  public void setMainBall(Ball ball){ mainBall=new Ball(ball); }
}
