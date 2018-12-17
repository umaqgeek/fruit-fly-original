/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package helpers;

import java.io.BufferedReader;
import java.io.FileReader;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Random;
import models.Bee;
import models.City;

/**
 *
 * @author umarmukhtar
 */
public class Func {

    public static final double MAX_DOUBLE_VALUE = 10000.00;
    public static final String DISTANCE_TAG = "distance";
    public static final String SMELL_CONCENTRATION_TAG = "smellConcentration";
    public static final String BEST_CITY_TAG = "bestCity";
    public static final String INNER_BEE_TAG = "innerBee";
    public static final String BEE_CITY_PATH = "beeCityPath";
    public static final String CITY_NAME_TAG = "name";
    public static final String CITY_X_TAG = "xCityAxis";
    public static final String CITY_Y_TAG = "yCityAxis";
    public static final String X_TAG = "xAxis";
    public static final String Y_TAG = "yAxis";

    public static String getFormatDistanceInKM(double num) {
        DecimalFormat df = new DecimalFormat("#,##0.00");
        return df.format(num);
    }

    public static String getFormatDistanceInKM(float num) {
        DecimalFormat df = new DecimalFormat("#,##0.00");
        return df.format(num);
    }

    public static String getFormatDistanceInCoordinate(double num) {
        DecimalFormat df = new DecimalFormat("#,##0.0000000");
        return df.format(num);
    }

    public static String getFormatDistanceInCoordinate(float num) {
        DecimalFormat df = new DecimalFormat("#,##0.0000000");
        return df.format(num);
    }

    public static int getRandomInt(int min, int max) {
        Random random = new Random();
        double d = min + random.nextDouble() * (max-min);
        return (int) d;
    }

    public static float getRandom() {
        Random random = new Random();
        return random.nextFloat();
    }

    public static double getRandomDouble() {
        Random random = new Random();
        return random.nextInt(1000) / 1000.0;
    }

    public static double getDistanceInKM(float locA[], float locB[]) {
        double d = 0.00;
        float lat1 = locA[0],
                lon1 = locA[1],
                lat2 = locB[0],
                lon2 = locB[1];

//        double R = 6373.00;
//        double dlon = lon2 - lon1;
//        double dlat = lat2 - lat1;
//        double aTemp = Math.pow(Math.sin(dlat / 2), 2) + Math.cos(lat1) * Math.cos(lat2) * Math.sin(dlon / 2);
//        double a = Math.pow(aTemp, 2);
//        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
//        d = R * c;

        /**
         * Distance, d = 3963.0 * arccos[(sin(lat1) * sin(lat2)) + cos(lat1) * cos(lat2) * cos(long2 – long1)]
         */
        double lati1 = lat1 / (180 / Math.PI);
        double lati2 = lat2 / (180 / Math.PI);
        double long1 = lon1 / (180 / Math.PI);
        double long2 = lon2 / (180 / Math.PI);
        double m = 3963.0 * Math.acos( (Math.sin(lati1) * Math.sin(lati2)) + Math.cos(lati1) * Math.cos(lati2) * Math.cos(long2 - long1) );
        d = m * 1.609344;

        return d;
    }

    public static void main(String [] args) {
      float locA[] = {Float.valueOf(args[0]), Float.valueOf(args[1])};
      float locB[] = {Float.valueOf(args[2]), Float.valueOf(args[3])};
      double d = getDistanceInKM(locA, locB);
      System.out.println(d + " km");
    }

    public static double acceptanceProbability(double currentDistance, double newDistance, double temperature) {
        if (newDistance < currentDistance) {
            return 1.0;
        }
        return Math.exp((currentDistance - newDistance) / temperature);
    }

    public static void viewAllPath(int index, Bee bee) {
        System.out.println("Travel Path Bee #" + (index) + ":");
        for (int cityIndex = 0; cityIndex < bee.paths().size(); cityIndex++) {
            System.out.print("- City #" + (cityIndex+1) + ": ");
            System.out.print((String) bee.paths().get(cityIndex).get(CITY_NAME_TAG) + " ");
            String xCity = getFormatDistanceInCoordinate((float) bee.paths().get(cityIndex).get(CITY_X_TAG));
            String yCity = getFormatDistanceInCoordinate((float) bee.paths().get(cityIndex).get(CITY_Y_TAG));
            System.out.print("(" + xCity + ", " + yCity + "), ");
            String xBee = getFormatDistanceInCoordinate((float) bee.paths().get(cityIndex).get(X_TAG));
            String yBee = getFormatDistanceInCoordinate((float) bee.paths().get(cityIndex).get(Y_TAG));
            System.out.print("Bee (" + xBee + ", " + yBee + ")");
            String d = getFormatDistanceInKM((double) bee.paths().get(cityIndex).get(DISTANCE_TAG));
            // System.out.println(d + " km");
            System.out.println();
        }

        // **
        bee.setTotalDistance(bee.getTotalDistance() * 4);

        String totalDistance = getFormatDistanceInKM(bee.getTotalDistance());
        System.out.println("Total Distance: " + totalDistance + " km");
        System.out.println("");
    }

    public Object[][] getCitiesFromFile(String fileName) {
        ArrayList<Object[]> data = new ArrayList<Object[]>();
        int numCols = 0;
        BufferedReader br = null;
        FileReader fr = null;
        try {
            fr = new FileReader(fileName);
            br = new BufferedReader(fr);
            String sCurrentLine;
            while ((sCurrentLine = br.readLine()) != null) {
                if (sCurrentLine.length() > 2 && sCurrentLine.charAt(0) != '#') {
                    Object d[] = sCurrentLine.split(",");
                    numCols = d.length;
                    if (numCols == 3) {
                      data.add(d);
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Error: "+e.getMessage());
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
                if (fr != null) {
                    fr.close();
                }
            } catch (Exception ex) {
                System.out.println("Error: "+ex.getMessage());
            }
        }
        Object objData[][] = null;
        try {
            objData = new Object[data.size()][numCols];
            for (int index = 0; index < data.size(); index++) {
                objData[index][0] = (String) data.get(index)[0];
                objData[index][1] = Float.parseFloat((String) data.get(index)[1]);
                objData[index][2] = Float.parseFloat((String) data.get(index)[2]);
            }
        } catch (Exception e) {
            System.out.println("Error: "+e.getMessage());
        }
        return objData;
    }
}
