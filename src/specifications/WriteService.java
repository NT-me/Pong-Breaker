/* ******************************************************
 * Project alpha - Composants logiciels 2015.
 * Copyright (C) 2015 <Binh-Minh.Bui-Xuan@ens-lyon.org>.
 * GPL version>=3 <http://www.gnu.org/licenses/>.
 * $Id: specifications/WriteService.java 2015-03-11 buixuan.
 * ******************************************************/
package specifications;

import data.Ball;
import tools.Position;
import tools.Sound;

import java.util.ArrayList;
import javafx.util.Pair;

public interface WriteService {
    public void setStepNumber(int n);
    public void setPosition(Position p);
    public void setRayon(double r);
    public void setMainBall(Ball ball);
    public void setSpeed(double speed);
    public void setDirection(Pair<Position,Position> direction);
}
