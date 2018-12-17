/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import controllers.FruitFlySmellRadius;
import controllers.FruitFlyTwoBest;
import helpers.Func;

/**
 *
 * @author umarmukhtar
 */
public class MainPage {

    public static void main(String[] args) {

        long startTime = System.currentTimeMillis();

        Object initCities[][] = {
            {"MiTC Melaka", 2.2714843f, 102.28636f},
            {"Zoo Melaka", 2.2778543f, 102.2991534f},
            {"Taman Botanikal Melaka", 2.280702f, 102.2984493f},
            {"Menara Taming Sari Melaka", 2.1909778f, 102.2450914f},
            {"Dataran Pahlawan Melaka", 2.1900741f, 102.2502719f},
            {"Asam Pedas Melaka", 2.1959882f, 102.2411785f},
            {"7e Taman Tasik Melaka", 2.2718076f, 102.2820603f},
            {"Hotel Kobemas Melaka", 2.2683585f, 102.2840123f},
            {"Jabatan Imigrisen Melaka", 2.26617f, 102.2949613f}
        };
        try {
            if (args[0] != null) {
                String fileName = args[0];
                Func func = new Func();
                initCities = func.getCitiesFromFile(fileName);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Arguments error! System will use default config and maps.");
        }

        // fruit fly process
        FruitFlySmellRadius ffsr = new FruitFlySmellRadius();
        int numBees = 100;
        ffsr.process(initCities, numBees);

        System.out.print("Execution time: ");
        long stopTime = System.currentTimeMillis();
        long elapsedTime = stopTime - startTime;
        System.out.println(elapsedTime + " ms");
    }
}
