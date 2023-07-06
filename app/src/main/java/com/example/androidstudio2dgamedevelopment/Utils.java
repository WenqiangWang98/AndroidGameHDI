package com.example.androidstudio2dgamedevelopment;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.PointF;
import android.util.Log;

import com.example.androidstudio2dgamedevelopment.game.Country;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Scanner;

public class Utils {


    public static double getDistanceBetweenPoints(PointF p1, PointF p2) {
        return Math.sqrt(Math.pow(p1.x - p2.x, 2) + Math.pow(p1.y - p2.y, 2));
    }
    public static PointF rotatedPoint(PointF origin, PointF point, double radians)
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
    public static List<Country> initializeCSV(Context context){

        try {
            InputStream is = context.getResources().openRawResource(R.raw.hdi);
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(is, StandardCharsets.UTF_8));
            String line = "";
            List<Country> countries = new ArrayList<>();
            Resources resources=context.getResources();
            int i=1;
            while ((line = reader.readLine()) != null) {
                // nextLine[] is an array of values from the line
                String[] nextLine=line.split(",");
                countries.add(new Country(i,
                        Integer.parseInt(nextLine[0]),
                        nextLine[1],Float.parseFloat(nextLine[2]),
                        Float.parseFloat(nextLine[3]),
                        Float.parseFloat(nextLine[4]),
                        Integer.parseInt(nextLine[5]),
                        resources.getDrawable(resources.getIdentifier(nextLine[6],"drawable",context.getPackageName()))
                        ));
                i++;
            }
            return countries;
        } catch (Exception e) {
            Log.d("Utils.java", "initializeCSV: failed");

        }

        return null;
    }

}
