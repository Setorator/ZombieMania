package Tests.Main;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import org.junit.rules.ExpectedException;

import static org.junit.Assert.assertEquals;

import se.liu.ida.davsc542kimla207.tddd78.project.zombiemania.Player;



public class PlayerTest {
    private Player p;

    @Before
    public void setup() {
        p = new Player(0,0);
    }

    @After
    public void tearDown() {
        System.out.println("Tearing down PlayerTest");
    }

    @Rule
    public final ExpectedException exception =ExpectedException.none();

    @Test
    public void moveRight() {
        // Player moves with 4 positions per step
        final Player p1 = new Player(4, 0);
        p.moveRight();
        assertEquals("The excepted position", p1.getX(), p.getX());
    }

    @Test
    public void moveLeft() {
        // Player moves with 4 positions per step
        final Player p1 = new Player(0, 0);
        p.moveLeft();
        assertEquals("The excepted position:", p1.getX(), p.getX());
        p.moveLeft();
        assertEquals("The expected position:", p1.getX(), p.getX());
    }
}
