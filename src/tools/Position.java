/* ******************************************************
 * Project alpha - Composants logiciels 2015.
 * Copyright (C) 2015 <Binh-Minh.Bui-Xuan@ens-lyon.org>.
 * GPL version>=3 <http://www.gnu.org/licenses/>.
 * $Id: tools/Position.java 2015-03-11 buixuan.
 * ******************************************************/
package tools;

public class Position {
  public double x,y;
  public Position(double x, double y){
    this.x=x;
    this.y=y;
  }

  public double distance(Position other) {
    double A = (other.x - this.x) * (other.x - this.x);
    double B = (other.y - this.y) * (other.y - this.y);
    return Math.sqrt(A + B);
  }

  @Override
  public String toString() {
    return "Position{" +
            "x=" + x +
            ", y=" + y +
            '}';
  }
}
