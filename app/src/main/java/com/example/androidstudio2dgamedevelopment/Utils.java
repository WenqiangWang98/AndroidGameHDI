package com.example.androidstudio2dgamedevelopment;

import android.graphics.PointF;

public class Utils {

    /**
     * getDistanceBetweenPoints returns the distance between 2d points p1 and p2
     * @param p1x
     * @param p1y
     * @param p2x
     * @param p2y
     * @return
     */
    public static double getDistanceBetweenPoints(double p1x, double p1y, double p2x, double p2y) {
        return Math.sqrt(Math.pow(p1x - p2x, 2) + Math.pow(p1y - p2y, 2));
    }
    public static double getAreaOfTriangle(float Ax, float Ay, float Bx, float By, float Cx, float Cy){
        return Math.abs( (Bx * Ay - Ax * By) + (Cx * By - Bx * Cy) + (Ax * Cy - Cx * Ay) ) / 2.0;
    }
    public static PointF rotatedRectangle(PointF origin, PointF point, double radians)
    {
        double dx = (point.x-origin.x) * Math.cos(radians) - (point.y-origin.y) * Math.sin(radians);
        double dy = (point.x-origin.x) * Math.sin(radians) + (point.y-origin.y) * Math.cos(radians);
        return new PointF(origin.x + (float)dx, origin.y + (float)dy);
    }
    public static boolean isPointInsideRotatedRectangle(PointF pt, PointF[] rectCorners){
        double x1 = rectCorners[0].x;
        double x2 = rectCorners[1].x;
        double x3 = rectCorners[2].x;
        double x4 = rectCorners[3].x;

        double y1 = rectCorners[0].y;
        double y2 = rectCorners[1].y;
        double y3 = rectCorners[2].y;
        double y4 = rectCorners[3].y;

        double a1 = Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
        double a2 = Math.sqrt((x2 - x3) * (x2 - x3) + (y2 - y3) * (y2 - y3));
        double a3 = Math.sqrt((x3 - x4) * (x3 - x4) + (y3 - y4) * (y3 - y4));
        double a4 = Math.sqrt((x4 - x1) * (x4 - x1) + (y4 - y1) * (y4 - y1));

        double b1 = Math.sqrt((x1 - pt.x) * (x1 - pt.x) + (y1 - pt.y) * (y1 - pt.y));
        double b2 = Math.sqrt((x2 - pt.x) * (x2 - pt.x) + (y2 - pt.y) * (y2 - pt.y));
        double b3 = Math.sqrt((x3 - pt.x) * (x3 - pt.x) + (y3 - pt.y) * (y3 - pt.y));
        double b4 = Math.sqrt((x4 - pt.x) * (x4 - pt.x) + (y4 - pt.y) * (y4 - pt.y));

        double u1 = (a1 + b1 + b2) / 2;
        double u2 = (a2 + b2 + b3) / 2;
        double u3 = (a3 + b3 + b4) / 2;
        double u4 = (a4 + b4 + b1) / 2;

        double A1 = Math.sqrt(u1 * (u1 - a1) * (u1 - b1) * (u1 - b2));
        double A2 = Math.sqrt(u2 * (u2 - a2) * (u2 - b2) * (u2 - b3));
        double A3 = Math.sqrt(u3 * (u3 - a3) * (u3 - b3) * (u3 - b4));
        double A4 = Math.sqrt(u4 * (u4 - a4) * (u4 - b4) * (u4 - b1));

        double difference = A1 + A2 + A3 + A4 - a1 * a2;
        return difference < 1;
    }

}
