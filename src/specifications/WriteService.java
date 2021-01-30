/* ******************************************************
 * Project alpha - Composants logiciels 2015.
 * Copyright (C) 2015 <Binh-Minh.Bui-Xuan@ens-lyon.org>.
 * GPL version>=3 <http://www.gnu.org/licenses/>.
 * $Id: specifications/WriteService.java 2015-03-11 buixuan.
 * ******************************************************/
package specifications;

import data.Ball;
import data.Destructive;
import data.Palette;
import data.Player;
import tools.Position;
import tools.Sound;

import java.util.ArrayList;
import javafx.util.Pair;

public interface WriteService {
    public void setStepNumber(int n);
    public void setMainBallPosition(Position p);
    public void setMainBallPlayer(Player p);
    public void setRayon(double r);
    public void setMainBall(Ball ball);
    public void setSpeed(double speed);
    public void setDirection(Pair<Integer,Integer> direction); // Va servir a rien
    public void setBlue(Palette blue);
    public void setBluePosition(Position p);
    public void setRed(Palette red);
    public void setRedPosition(Position p);
    public  void setdestBallsPos(Position pos, Player pla);
    public void setBdestBall(Destructive bdestBall);
    public void setRdestBall(Destructive bdestBall);
}
