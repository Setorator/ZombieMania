package test;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import main.java.ZombieMania;

public class MainTest {
    private ZombieMania zm;

    @Before
    public void setup() {
        zm = new ZombieMania();
    }

    @After
    public void tearDown() {
        System.out.println("Tearing down test suite");
    }

    @Test
    public void testPlayer() {
        PlayerTest pt = new PlayerTest();
        pt.moveLeft();
        pt.moveRight();
    }
}
