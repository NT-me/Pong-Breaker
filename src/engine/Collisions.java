package engine;

import data.*;
import tools.PBUtils;
import tools.Position;

public class Collisions{

    public static boolean collisionPaletteBalls(Palette pal, Ball ba){
        Position NO = pal.getPosition();
        Position NE = new Position(NO.x+pal.getHeight(), NO.y);
        Position SE = new Position(NO.x+pal.getHeight(),NO.y+ pal.getWidth());
        Position SO = new Position(NO.x,NO.y+ pal.getWidth());

        return PBUtils.pointInRectangle(NO, NE, SE, SO, ba.getPosition())
                || PBUtils.intersectCercle(ba.getPosition(), ba.getRayon(), NO, NE)
                || PBUtils.intersectCercle(ba.getPosition(), ba.getRayon(), NE, SE)
                || PBUtils.intersectCercle(ba.getPosition(), ba.getRayon(), SE, SO)
                || PBUtils.intersectCercle(ba.getPosition(), ba.getRayon(), SO, NO);
    }

    public static boolean collisionBallBrick(Ball b, Brick bri){
        Position NO = bri.getPosition();
        Position NE = new Position(NO.x+90, NO.y);
        Position SE = new Position(NO.x+90,NO.y+ 80);
        Position SO = new Position(NO.x,NO.y+ 80);

        return (PBUtils.pointInRectangle(NO, NE, SE, SO, b.getPosition())
                || PBUtils.intersectCercle(b.getPosition(), b.getRayon(), NO, NE)
                || PBUtils.intersectCercle(b.getPosition(), b.getRayon(), NE, SE)
                || PBUtils.intersectCercle(b.getPosition(), b.getRayon(), SE, SO)
                || PBUtils.intersectCercle(b.getPosition(), b.getRayon(), SO, NO)
        ) && b.getPlayer() != bri.getColor();
    }

    public static boolean collisionWallBall(Ball ba, Wall north, Wall south){
        double circleDistanceNorth = ba.getPosition().y-north.getPosition().y;
        double circleDistanceSouth = south.getPosition().y-ba.getPosition().y;

        if (circleDistanceNorth > (20 + ba.getRayon())) {
            if (circleDistanceSouth > (20 + ba.getRayon())){
                return false;
            }
            else
                return true;
        }
        else
            return true;
    }

    public static boolean collisionGoalMainBall(Ball mainBall, Goal goalBlue, Goal goalRed){
        double circleDistanceBlue = mainBall.getPosition().x-goalBlue.getPosition().x;
        double circleDistanceRed = goalRed.getPosition().x-mainBall.getPosition().x;

        if (circleDistanceBlue <= (0 + mainBall.getRayon()))
            return true;
        if (circleDistanceRed <= (0 + mainBall.getRayon()))
            return true;
        return false;
    }
}
