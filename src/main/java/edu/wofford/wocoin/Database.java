package edu.wofford.wocoin;


public class Database {

    public Database(String filename) {
        Utilities.createNewDatabase(filename);
    }
}