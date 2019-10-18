package edu.wofford.wocoin;
import static org.junit.Assert.*;

import edu.wofford.wocoin.main.Feature01Main;
import org.junit.Test;
import org.junit.Before;

public class UsersTest {

    @Test
    public void Feature01MainTest(){
        //first test should be to determine if database exists
        //String databaseName = Feature01Main;
        Feature01Main tester = new Feature01Main();

    }
    public void saltpasswordTest() {
        int int_salt = Utilities.generateSalt();
        String salt = Integer.toString(int_salt);
        String hash = "" + Utilities.applySha256("password") + salt;

    }

}

