package tools;

public class PBUtils {
    private double crossproduct(Position p, Position q, Position s, Position t){
        return (((q.x-p.x)*(t.y-s.y))-((q.y-p.y)*(t.x-s.x)));
    }

    public static double dotProd(Position p, Position q, Position s, Position t){
        return ((q.x-p.x)*(t.x-s.x)+(q.y-p.y)*(t.y-s.y));
    }

    /**
     * Soit AB une droite et C quelconque
     */
    public static Position getPerpendicular(Position A, Position B, Position C){
        double vx = B.x-A.x; //x du vecteur AB
        double vy = B.y-A.y; //y du vecteur AB
        double ab2 = vx*vx + vy*vy; //norme au carré de AB
        double u = ((C.x - A.x) * vx + (C.y - A.y) * vy) / (ab2);
        return new Position ((int)Math.round(A.x + u * vx), (int)Math.round(A.y + u * vy)); //D appartient à AB
    }

    public static boolean intersectCercle(Position centreCercle, double rayonCercle, Position A, Position B){
        Position intersectPoint = getPerpendicular(A, B, centreCercle);
        if (0 <= dotProd(A, B, A, intersectPoint) && dotProd(A, B, A, intersectPoint) <= dotProd(A, B, A, B)){
            return centreCercle.distance(intersectPoint) <= rayonCercle;
        }

        return false;
    }

    /**
     * Soit ABCD les points du rectangle et P le centre du cercle et rayon le rayon du cercle
     */
    public static boolean pointInRectangle(Position A, Position B, Position C, Position D, Position P){
        // 0 ≤ AP·AB ≤ AB·AB and 0 ≤ AP·AD ≤ AD·AD
        double firstCP = dotProd(A, P, A, B);
        double firstComp = dotProd(A, B, A, B);

        double secondCP = dotProd(A, P, A, D);
        double secondComp = dotProd(A, D, A, D);

        return (0 <= firstCP && firstCP <= firstComp) && (0 <= secondCP && secondCP <= secondComp);

    }

}
