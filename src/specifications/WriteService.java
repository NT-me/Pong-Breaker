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

public interface WriteService {
    public abstract void setStepNumber(int n);
    public abstract void setPosition(Position p);
    public abstract void setRayon(double r);
    public abstract void setMainBall(Ball ball);
}
