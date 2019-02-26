package Tests.Main;

import org.junit.After;
import org.junit.Before;

import se.liu.ida.davsc542kimla207.tddd78.project.zombiemania.ZombieMania;

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

}
