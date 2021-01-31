/* ******************************************************
 * Project alpha - Composants logiciels 2015.
 * Copyright (C) 2015 <Binh-Minh.Bui-Xuan@ens-lyon.org>.
 * GPL version>=3 <http://www.gnu.org/licenses/>.
 * $Id: specifications/WriteService.java 2015-03-11 buixuan.
 * ******************************************************/
package specifications;

import data.Brick;
import data.Create;
import data.Palette;
import data.Ball;
import data.Wall;
import data.Goal;

import data.Destructive;
import data.Player;

import tools.Position;
import tools.Sound;

import java.util.ArrayList;
import javafx.util.Pair;

public interface WriteService {
    void setStepNumber(int n);
    void setMainBallPosition(Position p);
    void setMainBallPlayer(Player p);
    void setRayon(double r);
    void setMainBall(Ball ball);
    void setSpeed(double speed);
    void setMainBallDirection(Position direction);
    void setBlueDirection(Position direction);
    void setRedDirection(Position direction);
    void setBlue(Palette blue);
    void setBluePosition(Position p);
    void setRed(Palette red);
    void setRedPosition(Position p);

    void setMatrice(int x, int y, int val) ;
    void setBricks(ArrayList<Brick> bricks);
    void setBcreaBall(Create bcreaBall);

    void setRcreaBall(Create bcreaBall);
    void setBcreaPosition(Position p);
    void setRcreaPosition(Position p);
    void setdestBallsPos(Position pos, Player pla);
    void setBdestBall(Destructive bdestBall);
    void setRdestBall(Destructive bdestBall);
    void setScoreR(int scoreR);
    void setScoreB(int scoreB);
}
