package se.liu.ida.davsc542kimla207.tddd78.project.zombiemania;

import java.awt.Image;

/**
 * Created by David on 2015-04-15.
 */

@SuppressWarnings("ALL")
public class Player extends GameObject
{
    private static final int PLAYER_WIDTH = 1;
    private static final int PLAYER_HEIGHT = 2;
    private int lives = 3;
    private MusicPlayer sounds;
    private int gravity;
    private final static Image zombieRight = ResourceLoader.loadImageResource("Resources/Pictures/zombieright.gif").getImage();
    private final static Image zombieLeft = ResourceLoader.loadImageResource("Resources/Pictures/zombieleft.gif").getImage();
    private final static Image zombieJumpLeft = ResourceLoader.loadImageResource("Resources/Pictures/zombiejumpleft.gif").getImage();
    private final static Image zombieJumpRight = ResourceLoader.loadImageResource("Resources/Pictures/zombiejumpright.gif").getImage();
    private final static Image zombieLookLeft = ResourceLoader.loadImageResource("Resources/Pictures/zombielookleft.gif").getImage();
    private final static Image zombieLookRight = ResourceLoader.loadImageResource("Resources/Pictures/zombielookright.gif").getImage();


    public Player(int x, int y) {
	setX(x);
	setY(y);
	this.width = PLAYER_WIDTH;
	this.height = PLAYER_HEIGHT;
	this.type = BlockType.PLAYER;
	this.sounds = new MusicPlayer();
	this.gravity = 5;
	updateEdges();

	setImage(zombieLookRight);

	// The players three y-coordinates in "pixel-coordinates"


    }


    // Getters
    public int getLives() {
	return lives;
    }

    // plays an audio clip of a voice that says "No".
    public void sayNo() {
	sounds.setPath("Resources/Sounds/no.wav");
	sounds.load();
	sounds.play();
    }

    // plays an audioclip of a voice talking.
    public void sayIntro() {
	sounds.setPath("Resources/Sounds/journey.wav");
	sounds.load();
	sounds.play();
    }

    // reducing the player's life variable by 1.
    public void looseLife() {
	this.lives--;
	System.out.println("Remaining lives: " + lives);
    }

    // methods that changes the player's pixel-coordinates.
    public void moveLeft() {
	setImage(zombieLeft);
	this.x = getX() - 4;
	updateEdges();
    }
    public void moveRight() {

	setImage(zombieRight);
	this.x = getX() + 4;
	updateEdges();

    }
    public void fallDown() {
	setImage(zombieJumpRight);
	this.y = getY() + gravity;
	updateEdges();
    }
    public void moveJump() {
	setImage(zombieJumpRight);
	this.y = getY() - gravity;
	updateEdges();

    }
    public void rMoveLeft() {
	setImage(zombieLookLeft);
	updateEdges();

    }
    public void rMoveRight() {
	setImage(zombieLookRight);
	updateEdges();

    }
}
