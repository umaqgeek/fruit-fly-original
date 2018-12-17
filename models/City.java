/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

/**
 *
 * @author umarmukhtar
 */
public class City {
    
    private String name;
    private float xAxis;
    private float yAxis;
    
    public City() {
        name = "-";
        xAxis = 0.00f;
        yAxis = 0.00f;
    }
    
    public City(String name, float x, float y) {
        this.name = name;
        this.xAxis = x;
        this.yAxis = y;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
}
