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
        Users tester = new Users();

    }

    @Test
    public void UsersTest(){
        //second test is check username + password
        Users tester = new Users(); //should we set this to Feature01Main?
        //if we do, how do we test username + password ??
        /*
        assertEquals(tester.SaltPassword("jdoe", "ebd3528832b124bb7886cd8e8d42871c99e06d5f3ad0c6ee883f6219b2b6a955"),
                "username is: \npassword is:");
        assertEquals(tester.SaltPassword("jsmith", "9d3194cf601e62d35f144abebcea7704ad005402e102d134bd8f82ac469c2ec9"),
                "username is: \npassword is:");
        assertEquals(tester.SaltPassword("hjones", "5d94ecaff496ac900a1f68ec950153aa1f500d06227b65167f460e5dd20a959b"),
                "username is: \npassword is:");
        assertEquals(tester.SaltPassword("srogers", "26f2573d733da38fb3cd09eb79f884bbe63010570d394de7d8809b65823da85a"),
                "username is: \npassword is:");
         */
    }

    @Test
    public void UsersTest3(){
        //third test on if user exists / can be created
        Users tester = new Users();

    }

}

