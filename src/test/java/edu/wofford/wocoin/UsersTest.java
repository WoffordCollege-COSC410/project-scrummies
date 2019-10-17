package edu.wofford.wocoin;
import static org.junit.Assert.*;

import edu.wofford.wocoin.main.Feature01Main;
import org.junit.Test;
import org.junit.Before;

public class UsersTest {

    @Test
    public void UsersTest1(){
        //first test should be to determine if database exists
        //String databaseName = Feature01Main;
        Users tester = new Users();

    }

    @Test
    public void UsersTest2(){
        //second test is see if admin info is correct
        Users tester = new Users();
        assertEquals(tester.AddUser("jdoe", 13587), "username is: \npassword is:");
        assertEquals(tester.AddUser("jsmith", 52196), "username is: \npassword is:");
        assertEquals(tester.AddUser("hjones", 47440), "username is: \npassword is:");
        assertEquals(tester.AddUser("srogers", 54419), "username is: \npassword is:");
    }

    @Test
    public void UsersTest3(){
        //third test on if user exists / can be created
        Users tester = new Users();

    }

}

