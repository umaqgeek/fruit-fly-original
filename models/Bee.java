/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.util.ArrayList;
import java.util.Properties;

/**
 *
 * @author umarmukhtar
 */
public class Bee {
    
    private float xAxis;
    private float yAxis;
    private ArrayList<Properties> paths;
    private double totalDistance;
    private double smellRadius;
    
    public Bee() {
        this.xAxis = 0.00f;
        this.yAxis = 0.00f;
        this.paths = new ArrayList<Properties>();
        this.totalDistance = 0.00;
        this.smellRadius = 0.00;
    }
    
    public Bee(float x, float y) {
        this.xAxis = x;
        this.yAxis = y;
        this.paths = new ArrayList<Properties>();
        this.totalDistance = 0.00;
        this.smellRadius = 0.00;
    }
    
    public Bee(float x, float y, double sr) {
        this.xAxis = x;
        this.yAxis = y;
        this.paths = new ArrayList<Properties>();
        this.totalDistance = 0.00;
        this.smellRadius = sr;
    }

    public float getxAxis() {
        return xAxis;
    }

    public void setxAxis(float xAxis) {
        this.xAxis = xAxis;
    }

    public float getyAxis() {
        return yAxis;
    }

    public void setyAxis(float yAxis) {
        this.yAxis = yAxis;
    }

    public ArrayList<Properties> paths() {
        return paths;
    }

    public void setPaths(ArrayList<Properties> paths) {
        this.paths.removeAll(this.paths);
        this.paths = paths;
    }

    public double getTotalDistance() {
        return totalDistance;
    }

    public void setTotalDistance(double totalDistance) {
        this.totalDistance = totalDistance;
    }
}
