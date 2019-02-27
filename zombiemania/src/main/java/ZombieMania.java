package main.java;

import javax.swing.*;
import java.awt.event.ActionEvent;


/**
 * Created by Kim on 16/04/15.
 */
public final class ZombieMania
{

    private Board board = null;
    private JFrame frame = null;
    private MapLoader mapLoader = new MapLoader();
    private int currentLevel;


    public ZombieMania() {
	this.currentLevel = 0;
	loadNextLevel();
	this.frame = new ZombieFrame(board);
	board.getPlayer().sayIntro();
	final int tickFrequenzy = 10;
	Action gameStep = new AbstractAction()
	{
	    public void actionPerformed(final ActionEvent e) {
		run();
	    }
	};
	Timer timer = new Timer(tickFrequenzy, gameStep);
	timer.setCoalesce(true);
	timer.start();

    }

    public void loadNextLevel() {
	try {
	    board = new Board(mapLoader.getMapArray().get(currentLevel));
	    currentLevel++;
	    System.out.println("current level " + currentLevel);

	} catch (RuntimeException ignored) {
	    currentLevel = 0;
	    board = new Board(mapLoader.getMapArray().get(currentLevel));
	    currentLevel++;
	    System.out.println("current level " + currentLevel);

	}

    }
    public void run() {
	board.handlePlayerTick();
	board.handleEnemyTick();

	if (board.isLevelCompleate()) {
	    loadNextLevel();
	    board.setLevelCompleate(false);
	    frame.dispose();
	    frame = new ZombieFrame(board);


	} else if (board.isRestart()) {
	    currentLevel = 0;
	    loadNextLevel();
	    board.setRestart(false);
	    frame.dispose();
	    frame = new ZombieFrame(board);
	}
    }
    public void addMusic() {
	MusicPlayer musicPlayer = new MusicPlayer();
	musicPlayer.setPath("main/resources/Sounds/gameMusic.wav");
	musicPlayer.play();
    }

    public static void main(String[] args) {
	final ZombieMania zombieMania = new ZombieMania();
	zombieMania.addMusic();


    }

}
