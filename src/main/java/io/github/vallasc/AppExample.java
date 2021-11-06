package io.github.vallasc;

import java.io.IOException;

import io.github.vallasc.WlanScanner.OperatingSystemNotDefinedException;

public class AppExample {

    // mvn compile exec:java
    public static void main( String[] args ) {
        System.out.println( "\nWlanScanner\n" );
        WlanScanner w = new WlanScanner();
        try {
            APInfo[] apList = w.scanNetworks();
            for(APInfo ap : apList){
                System.out.println(ap + "\n");
            }
        } catch (OperatingSystemNotDefinedException | IOException e) {
            e.printStackTrace();
        }
    }
}
