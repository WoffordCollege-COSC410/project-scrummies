package edu.wofford.wocoin;
import gherkin.lexer.Fi;

import java.io.File;


public class Database {

    public Database(String filename) {
        Utilities.createNewDatabase(filename);
        File f = new File(filename);
    }
    public boolean FileExist(String filename) {
        File f = new File(filename);
        if (f.exists()) {
            return true;
        }
        return false;
    }
    public void AddUser(String username, String password) {

    }
}