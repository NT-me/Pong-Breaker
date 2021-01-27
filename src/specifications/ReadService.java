/* ******************************************************
 * Project alpha - Composants logiciels 2015.
 * Copyright (C) 2015 <Binh-Minh.Bui-Xuan@ens-lyon.org>.
 * GPL version>=3 <http://www.gnu.org/licenses/>.
 * $Id: specifications/ReadService.java 2015-03-11 buixuan.
 * ******************************************************/
package specifications;

import data.Palette;
import data.Ball;
import tools.Position;
import tools.Sound;
import javafx.util.Pair;

import java.util.ArrayList;

public interface ReadService {
    public Position getPosition();
    public int getStepNumber();
    public Palette getBlue();
    public void setBlue(Palette blue);
    public void setBluePosition(Position p);
    public Position getBluePosition();
    public Palette getRed();
    public void setRed(Palette red);
    public void setRedPosition(Position p);
    public Position getRedPosition();
    public double getRayon();
    public Ball getMainBall();
    public double getSpeed();
    public Pair<Integer,Integer> getDirection();
    public void setDirection(Pair<Integer,Integer> direction);

}
