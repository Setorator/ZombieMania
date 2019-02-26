package se.liu.ida.davsc542kimla207.tddd78.project.zombiemania;

import javax.swing.*;
import java.awt.*;

/**
 * Created by David on 14/04/15.
 */
public class ZombieFrame extends JFrame
{
    final static int FRAME_WIDTH = 1500;
    final static int FRAME_HEIGHT = 1000;

    public ZombieFrame(Board board) {
	super("Zombie Mania");
	GameComponent comp = new GameComponent(board);
	board.addBoardListener(comp);
	add(comp);
	this.setSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
	this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	this.setLocationRelativeTo(null);

	this.setVisible(true);

	System.out.println("Frame width = " + FRAME_WIDTH);
	System.out.println("Frame height = " + FRAME_HEIGHT);


    }


}
