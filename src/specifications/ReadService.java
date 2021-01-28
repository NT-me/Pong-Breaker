/* ******************************************************
 * Project alpha - Composants logiciels 2015.
 * Copyright (C) 2015 <Binh-Minh.Bui-Xuan@ens-lyon.org>.
 * GPL version>=3 <http://www.gnu.org/licenses/>.
 * $Id: specifications/ReadService.java 2015-03-11 buixuan.
 * ******************************************************/
package specifications;

import data.Brick;
import data.Create;
import data.Palette;
import data.Ball;
import data.Wall;
import data.Goal;

import tools.Position;
import tools.Sound;
import javafx.util.Pair;

import java.util.ArrayList;

public interface ReadService {
    public Position getMainBallPosition();
    public Position getCreateBallPosition();
    public int getStepNumber();
    public Palette getBlue();
    public Position getBluePosition();
    public Palette getRed();
    public Position getRedPosition();
    public double getRayon();
    public Ball getMainBall();
    public double getSpeed();
    public Pair<Integer,Integer> getDirection();
    public int[][] getMatrice() ;
    public void setMatrice(int x,int y, int val) ;
    public ArrayList<Brick> getBricks() ;
    public void setBricks(ArrayList<Brick> bricks);
    public Create getCreateBall();
    public void setCreaBall(Create creaBall);

    public Wall getNorth();
    public Wall getSouth();
    public Goal getEast();
    public Goal getWest();
}
