package edu.wofford.wocoin.main;

import edu.wofford.wocoin.Utilities;
import java.util.Scanner;

public class Feature01Main {
    //main file for Feature01
    public static void main(String[] args) {
        if (args.length > 0) {
            Utilities.createNewDatabase("Test.db");
        } else {
            System.out.println("You must specify the database filepath as a command-line argument.");
        }
    }
}
