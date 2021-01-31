/* ******************************************************
 * Project alpha - Composants logiciels 2015.
 * Copyright (C) 2015 <Binh-Minh.Bui-Xuan@ens-lyon.org>.
 * GPL version>=3 <http://www.gnu.org/licenses/>.
 * $Id: specifications/ReadService.java 2015-03-11 buixuan.
 * ******************************************************/
package specifications;

import data.*;

import tools.Position;
import tools.Sound;
import javafx.util.Pair;

import java.util.ArrayList;

public interface ReadService {
    Position getMainBallPosition();
    int getStepNumber();
    Palette getBlue();
    Position getBluePosition();
    Palette getRed();
    Position getRedPosition();
    double getBlueWidth();
    double getRedWidth();
    double getRayon();
    Ball getMainBall();
    double getSpeed();
    Position getMainBallDirection();
    Position getBlueDirection();
    Position getRedDirection();
    int[][] getMatrice() ;
    ArrayList<Brick> getBricks() ;
    Create getBcreaBall();

    Wall getNorth();
    Wall getSouth();
    Goal getEast();
    Goal getWest();

    Create getRcreaBall();
    void setRcreaBall(Create bcreaBall);
    void setBcreaPosition(Position p);
    void setRcreaPosition(Position p);

    Destructive getBdestBall();
    Destructive getRdestBall();

    int getScoreB();
    int getScoreR();
}
