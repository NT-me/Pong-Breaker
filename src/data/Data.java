/* ******************************************************
 * Project alpha - Composants logiciels 2015.
 * Copyright (C) 2015 <Binh-Minh.Bui-Xuan@ens-lyon.org>.
 * GPL version>=3 <http://www.gnu.org/licenses/>.
 * $Id: data/Data.java 2015-03-11 buixuan.
 * ******************************************************/
package data;

import javafx.util.Pair;
import tools.HardCodedParameters;
import tools.Position;
import tools.Sound;

import specifications.DataService;

import java.util.ArrayList;

public class Data implements DataService{
  private Palette blue;
  private Palette red;

  public Data(){  }
  private int stepNumber;

  @Override
  public void init(){
    stepNumber = 0;
    double paletteWidth = 200;
    double paletteHeight = 10;
    Pair<Integer, Integer> dir0 = new Pair<Integer, Integer>(0,0);
    Position posBlue = new Position((double)paletteHeight*2,(double)(HardCodedParameters.defaultHeight/2)-(paletteWidth/2));
    this.blue = new Palette(posBlue, (double)0, dir0, paletteWidth, paletteHeight, "Blue", 15, 10);

    Position posRed = new Position(HardCodedParameters.defaultWidth-(paletteHeight*4),(double)(HardCodedParameters.defaultHeight/2)-(paletteWidth/2));
    this.red = new Palette(posRed, (double)0, dir0, paletteWidth, paletteHeight, "Red", 15, 10);
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
}
