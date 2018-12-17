/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import helpers.Func;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Random;
import models.Bee;
import models.City;

public class FruitFlySmellRadius {

    private static final float MULTIPLIER = 1f;
    private static final float LATITUTE_RANGE = MULTIPLIER * 0.005f; // latitute range of bee around current place.
    private static final float LONGITUTE_RANGE = MULTIPLIER * 0.0025f; // longitute range of bee around current place.

    public void process(Object initCities[][], int numBees) {

        FruitFlySmellRadius ffsr = new FruitFlySmellRadius();

        // init set cities
        City cities[] = new City[initCities.length];
        for (int cityIndex = 0; cityIndex < initCities.length; cityIndex++) {
            String cityName = (String) initCities[cityIndex][0];
            float cityX = (float) initCities[cityIndex][1];
            float cityY = (float) initCities[cityIndex][2];
            cities[cityIndex] = new City(cityName, cityX, cityY);
        }

        // set bees
        // Bee bee = new Bee(cities[0].getxAxis(), cities[0].getyAxis(), 3);

        // init fly
        // Properties propPath = ffsr.getCityPath(bee, cities);
        // bee = (Bee) propPath.get(Func.INNER_BEE_TAG);

        Bee bestBee = new Bee(cities[0].getxAxis(), cities[0].getyAxis());
        double bestTotalDistance = 10000.00;
        int bestIndex = 0;

        // process search fly

            for (int beeIndex = 0; beeIndex < numBees; beeIndex++) {

                // start back at city 1 for each iteration
                Bee bee = new Bee(cities[0].getxAxis(), cities[0].getyAxis());

                // go fly
                Properties propPath = ffsr.getCityPath(bee, cities);
                bee = (Bee) propPath.get(Func.INNER_BEE_TAG);

                // process to ONLY calculate the distance between their cities, exclude the bee distances.
                ArrayList<City> beePath = new ArrayList<City>();
                for (int cityIndex = 0; cityIndex < bee.paths().size(); cityIndex++) {
                    City beeCity = new City();
                    beeCity.setName((String) bee.paths().get(cityIndex).get(Func.CITY_NAME_TAG));
                    beeCity.setxAxis((float) bee.paths().get(cityIndex).get(Func.CITY_X_TAG));
                    beeCity.setyAxis((float) bee.paths().get(cityIndex).get(Func.CITY_Y_TAG));
                    beePath.add(beeCity);
                }
                Properties propBee = ffsr.getCitiesTotalDistances(bee, beePath);
                bee = (Bee) propBee.get(Func.INNER_BEE_TAG);
                bee.setTotalDistance((double) propBee.get(Func.DISTANCE_TAG));

                // view each bee performance.
                // Func.viewAllPath(beeIndex+1, bee);

                // compare best distance with current distance
                if (bee.getTotalDistance() < bestTotalDistance) {
                    bestTotalDistance = bee.getTotalDistance();
                    bestBee.setxAxis(bee.getxAxis());
                    bestBee.setyAxis(bee.getyAxis());
                    bestBee.setTotalDistance(bee.getTotalDistance());
                    bestBee.setPaths(bee.paths());
                    bestIndex = beeIndex;
                }
            }

        // view the best path
        System.out.println("BEST BEE:");
        System.out.println("-------------");
        Func.viewAllPath(bestIndex+1, bestBee);
    }

    private Properties getCityPath(Bee bee, City cities[]) {
        Properties prop = new Properties();
        ArrayList<City> cityPath = new ArrayList<City>();
        ArrayList<Properties> paths = new ArrayList<Properties>();
        // bee.setPaths(paths);
        try {

            double beeTotalDistance = 0.00;

            for (int cityIndex = 0; cityIndex < cities.length; cityIndex++) {

                boolean isMainCity = (cityIndex == 0);

                if (cityIndex != 0 && cities[cityIndex].getName().contains(cities[0].getName())) {
                  continue;
                }

                FruitFlySmellRadius ffsr = new FruitFlySmellRadius();
                Properties propBest = ffsr.getNearestCity(cityIndex, bee, cities, isMainCity);

                City bestCity = (City) propBest.get(Func.BEST_CITY_TAG);
                bee = (Bee) propBest.get(Func.INNER_BEE_TAG);

                float XIndex = Math.abs(bestCity.getxAxis() + (Func.getRandom() * (LATITUTE_RANGE * 2)) - LATITUTE_RANGE);
                float YIndex = Math.abs(bestCity.getyAxis() + (Func.getRandom() * (LONGITUTE_RANGE * 2)) - LONGITUTE_RANGE);

                bee.paths().get(cityIndex).put(Func.CITY_NAME_TAG, bestCity.getName());
                bee.paths().get(cityIndex).put(Func.CITY_X_TAG, bestCity.getxAxis());
                bee.paths().get(cityIndex).put(Func.CITY_Y_TAG, bestCity.getyAxis());
                bee.paths().get(cityIndex).put(Func.X_TAG, bee.getxAxis());
                bee.paths().get(cityIndex).put(Func.Y_TAG, bee.getyAxis());

                bee.setxAxis(XIndex);
                bee.setyAxis(YIndex);

                if (cityIndex < cities.length - 1) {
                    float locA[] = {bestCity.getxAxis(), bestCity.getyAxis()};
                    float locB[] = {XIndex, YIndex};
                    double beeShortDistance = (isMainCity) ? (0.00) : (Func.getDistanceInKM(locA, locB));
//                    double beeShortDistance = 0.00;
                    double currentDistance = (double) bee.paths().get(cityIndex).get(Func.DISTANCE_TAG);
                    currentDistance += beeShortDistance;
                    bee.paths().get(cityIndex).put(Func.DISTANCE_TAG, currentDistance);
                }

                beeTotalDistance += (double) bee.paths().get(cityIndex).get(Func.DISTANCE_TAG);

                cityPath.add(bestCity);
            }

            FruitFlySmellRadius ffsr = new FruitFlySmellRadius();
            Properties propBest = ffsr.getNearestCity(cities.length, bee, cities, true);
            City bestCity = (City) propBest.get(Func.BEST_CITY_TAG);
            bee = (Bee) propBest.get(Func.INNER_BEE_TAG);
            float XIndex = Math.abs(bestCity.getxAxis() + (Func.getRandom() * (LATITUTE_RANGE * 2)) - LATITUTE_RANGE);
            float YIndex = Math.abs(bestCity.getyAxis() + (Func.getRandom() * (LONGITUTE_RANGE * 2)) - LONGITUTE_RANGE);
            bee.paths().get(cities.length).put(Func.CITY_NAME_TAG, bestCity.getName());
            bee.paths().get(cities.length).put(Func.CITY_X_TAG, bestCity.getxAxis());
            bee.paths().get(cities.length).put(Func.CITY_Y_TAG, bestCity.getyAxis());
            bee.paths().get(cities.length).put(Func.X_TAG, XIndex);
            bee.paths().get(cities.length).put(Func.Y_TAG, YIndex);
            bee.setxAxis(XIndex);
            bee.setyAxis(YIndex);

            beeTotalDistance += (double) bee.paths().get(cities.length).get(Func.DISTANCE_TAG);

            bee.setTotalDistance(beeTotalDistance);

            cityPath.add(bestCity);

        } catch (Exception e) {
            cityPath.removeAll(cityPath);
            cityPath = new ArrayList<City>();
        }
        prop.put(Func.INNER_BEE_TAG, bee);
        prop.put(Func.BEE_CITY_PATH, cityPath);
        return prop;
    }

    private Properties getNearestCity(int ind, Bee bee, City cities[], boolean isMainCity) {
        Properties prop = new Properties();
        City bestCity = null;
        try {

            float XIndex = bee.getxAxis();
            float YIndex = bee.getyAxis();
            double bestSmell = 0.00;
            int bestCityIndex = 0;
            double bestDIndex = Func.MAX_DOUBLE_VALUE;

            if (isMainCity) {

                int cityIndex = 0;
                float locA[] = {cities[cityIndex].getxAxis(), cities[cityIndex].getyAxis()};
                float locB[] = {XIndex, YIndex};
                double DIndex = Func.getDistanceInKM(locA, locB);
                double SIndex = 1 / DIndex;
                double SmellIndex = SIndex;

                bestSmell = SmellIndex;
                bestCityIndex = cityIndex;
                bestDIndex = DIndex;
                bestCity = cities[bestCityIndex];

                Properties propBee = new Properties();
                propBee.put(Func.DISTANCE_TAG, bestDIndex);
                propBee.put(Func.SMELL_CONCENTRATION_TAG, bestSmell);
                bee.paths().add(propBee);

            } else {

                // To ask all cities, which one is the best closest city to the bee.
                for (int cityIndex = 0; cityIndex < cities.length; cityIndex++) {
                    FruitFlySmellRadius ffsr = new FruitFlySmellRadius();
                    if (!ffsr.isHasCity(bee, cities[cityIndex])) {
                        float locA[] = {cities[cityIndex].getxAxis(), cities[cityIndex].getyAxis()};
                        float locB[] = {XIndex, YIndex};
                        double DIndex = Func.getDistanceInKM(locA, locB);
                        double SIndex = 1 / DIndex; // Smell concentration = 1 over distance.

                        // the less DIndex (distance), the more SIndex (smell concentration).
                        // example:
                        // DIndex = 0.3
                        // SIndex = 3.333
                        // SIndex cannot more than 3
                        // We don't use the fitness function of (3 - S^2) because of above.
                        double SmellIndex = SIndex; // here doesn't use any fitness function, just measure distances.
                        if (SmellIndex > bestSmell) {
                            bestSmell = SmellIndex;
                            bestCityIndex = cityIndex;
                            bestDIndex = DIndex;
                        }
                    }
                }
                bestCity = cities[bestCityIndex];

                Properties propBee = new Properties();
                propBee.put(Func.DISTANCE_TAG, bestDIndex);
                propBee.put(Func.SMELL_CONCENTRATION_TAG, bestSmell);
                bee.paths().add(propBee);
            }

        } catch (Exception e) {
            bestCity = null;
            System.out.println("Error: "+e.getMessage());
        }
        prop.put(Func.BEST_CITY_TAG, bestCity);
        prop.put(Func.INNER_BEE_TAG, bee);
        return prop;
    }

    private boolean isHasCity(Bee bee, City city) {
        boolean status = false;
        try {

            ArrayList<Properties> paths = bee.paths();
            if (paths.size() > 0) {
                for (int index = 0; index < paths.size(); index++) {
                    Properties path = paths.get(index);
                    if (path.get(Func.CITY_NAME_TAG) != null) {
                        String cityName = (String) path.get(Func.CITY_NAME_TAG);
                        if (cityName.contains(city.getName())) {
                            status = true;
                            break;
                        }
                    }
                }
            }

        } catch (Exception e) {
            status = false;
            System.out.println("Error: "+e.getMessage());
        }
        return status;
    }

    private Properties getCitiesTotalDistances(Bee bee, ArrayList<City> cities) {
        double totalDistance = 0.00;
        Properties prop = new Properties();
        try {

            for (int cityIndex = 0; cityIndex < cities.size() - 1; cityIndex++) {

                float locA[] = {cities.get(cityIndex).getxAxis(), cities.get(cityIndex).getyAxis()};
                float locB[] = {cities.get(cityIndex + 1).getxAxis(), cities.get(cityIndex + 1).getyAxis()};
                double distance = Func.getDistanceInKM(locA, locB);
                totalDistance += distance;

                Properties propBee = new Properties();
                propBee.put(Func.DISTANCE_TAG, distance);
                propBee.put(Func.CITY_NAME_TAG, cities.get(cityIndex + 1).getName());
                propBee.put(Func.CITY_X_TAG, locB[0]);
                propBee.put(Func.CITY_Y_TAG, locB[1]);
                propBee.put(Func.X_TAG, locB[0]);
                propBee.put(Func.Y_TAG, locB[1]);
                bee.paths().set(cityIndex + 1, propBee);
            }

        } catch (Exception e) {
            totalDistance = 0.00;
            System.out.println("Error: "+e.getMessage());
        }
        prop.put(Func.DISTANCE_TAG, totalDistance);
        prop.put(Func.INNER_BEE_TAG, bee);
        return prop;
    }
}
