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

import tools.Position;
import tools.Sound;

import java.util.ArrayList;
import javafx.util.Pair;

public interface WriteService {
    public void setStepNumber(int n);
    public void setMainBallPosition(Position p);
    public void setRayon(double r);
    public void setMainBall(Ball ball);
    public void setSpeed(double speed);
    public void setMainBallDirection(Position direction);
    public void setBlueDirection(Position direction);
    public void setRedDirection(Position direction);
    public void setBlue(Palette blue);
    public void setBluePosition(Position p);
    public void setRed(Palette red);
    public void setRedPosition(Position p);

    public void setMatrice(int x,int y, int val) ;
    public void setBricks(ArrayList<Brick> bricks);
    public void setBcreaBall(Create bcreaBall);

    public void setRcreaBall(Create bcreaBall);
    public void setBcreaPosition(Position p);
    public void setRcreaPosition(Position p);
}
