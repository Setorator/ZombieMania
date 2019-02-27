package main.java;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by David on 14/04/15.
 */
public class Board extends JPanel
{

    private BlockType[][] map;
    private Player player;
    private List<BoardListener> boardListeners;
    private List<Creep> creepList;
    private boolean levelCompleate = false;
    private boolean restart = false;
    private CollisionHandler collisionHandler;


    public Board(BlockType[][] map) {

	this.map = map;
	this.boardListeners = new ArrayList<>();
	this.creepList = new ArrayList<>();
	this.player = new Player(getPlayerXOrY('X'), getPlayerXOrY('Y'));
	this.collisionHandler=new CollisionHandler(this);
    }

    // Getters
    public BlockType getBlockType(int x, int y) {
	return map[y][x];
    }
    public CollisionHandler getCollisionHandler() {
	return collisionHandler;
    }
    public BlockType[][] getMap() {
	return map;
    }
    public Player getPlayer() {
	return player;
    }
    public int getPlayerXOrY(char XorY) {
	for (int y = 0; y < map.length; y++) {
	    for (int x = 0; x < map[0].length; x++) {
		if (getBlockType(x, y).equals(BlockType.PLAYER)) if (XorY == 'X' || XorY == 'x') {
		    return x * GameComponent.BLOCK_SIZE;

		} else {
		    return y * GameComponent.BLOCK_SIZE;
		}

	    }

	}
	return 0;
    }
    public Iterable<Creep> getCreepList() {
	return creepList;
    }
    public boolean isRestart() {
	return restart;
    }
    public boolean isLevelCompleate() {
	return levelCompleate;
    }

    // Setters
    public void setBlockType(int x, int y, BlockType type) {
	this.map[y][x] = type;
    }
    public void setRestart(final boolean restart) {
	this.restart = restart;
    }
    public void setLevelCompleate(final boolean levelCompleate) {
	this.levelCompleate = levelCompleate;
    }


    // returns the distance to the closest block in the wanted direction.
    // If no block is found, return the distance to the frame edge.
    // changes the players coordinates so that he no longer are in danger.
    public void returnToSafety() {


	if (collisionHandler.standingOnSpike() || collisionHandler.hitByEnemy()) {
	    player.setX(player.getX() - GameComponent.BLOCK_SIZE * 4);
	    player.setY(player.getY() - GameComponent.BLOCK_SIZE * 2);
	    returnToSafety();

	    // If the player have been accidently placed INSIDE another blocktype, we try to moving upward
	} else if (collisionHandler.cantMoveLeft() || collisionHandler.cantMoveRight()) {

	    player.setY(player.getY() - GameComponent.BLOCK_SIZE);
	    returnToSafety();
	}

    }

    // Creates dialog windows to get players name and to check if the player wants to play agian after game over.
    public void gameOverDialog() {

	int answer = JOptionPane.showConfirmDialog(null, "Do you want to be rescurrected?", "The humans killed you... again!",
						   JOptionPane.YES_NO_OPTION);
	if (answer == JOptionPane.YES_OPTION) {
	    this.restart = true;

	} else {
	    System.exit(1);
	}


    }
    public void nextLevel() {
	this.levelCompleate = true;
    }
    public void addBoardListener(BoardListener bl) {
	boardListeners.add(bl);
    }
    public void notifyListeners() {
	boardListeners.forEach(BoardListener::boardChanged);
    }
    public void addCreep(Creep creep) {
	creepList.add(creep);
    }
    public void removeFromMap(int x, int y) {
	map[y][x] = BlockType.EMPTY;
    }

    // Since we removes the creeps from the map during init. we must restore them to the map so that they will respawn if
    // we want to play the same level again.
    public void restoreCreeps() {
	for (Creep creep : creepList) {
	    setBlockType(creep.getX() / GameComponent.BLOCK_SIZE, creep.getY() / GameComponent.BLOCK_SIZE, creep.getType());
	}
    }

    // This is our main method where the game moves forward.
    public void handlePlayerTick() {

	// if the player has taken damage, return him to a safety.
	if (collisionHandler.standingOnSpike() || collisionHandler.hitByEnemy()) {
	    player.sayNo();
	    returnToSafety();

	    // if the player only has one remaining life, the game is lost.
	    if (player.getLives() == 1) {
		restoreCreeps();
		gameOverDialog();

	    } else {
		player.looseLife();
	    }
	}

	// if we have reached the finish we load the next level.
	if (collisionHandler.finish()) {
	    nextLevel();
	}

	// if there is nothing below the player and he is not jumping, he is affected by gravity.
	if (!collisionHandler.cantMoveDown() && !player.isJump()) {
	    player.fallDown();

	// if the player is jumping and there is nothing above him, he jumps.
	} else if (player.isJump()) {
	    if (player.getY() > player.getYPos() && !collisionHandler.cantMoveUp()) {
		player.moveJump();

	    } else {
		player.setJump(false);
	    }
	}

	// if the player can keep moving forward, he will.
	if (player.isLeft() && !collisionHandler.cantMoveLeft()) {
	    player.moveLeft();

	} else if (player.isRight() && !collisionHandler.cantMoveRight()) {
	    player.moveRight();
	}

	// if the player is not moving, we calls theese two methods. (r = released)
	if (player.getFacingRight() && !player.isRight() && !player.isLeft()) {
	    player.rMoveRight();

	} else if (!player.getFacingRight() && !player.isRight() && !player.isLeft()) {
	    player.rMoveLeft();
	}

	// when we have gone through all theese steps we notify our BoardListeners (GameComponent).
	notifyListeners();
    }
    public void handleEnemyTick(){
	// goes through our array of creeps and move's each and every one of them.
	for (Creep creep : creepList) {
	    if (collisionHandler.creepCantMoveRight(creep)) {
		creep.setRight(false);
		creep.setLeft(true);
		creep.move();
	    } else if (collisionHandler.creepCantMoveLeft(creep)) {
		creep.setLeft(false);
		creep.setRight(true);
		creep.move();
	    } else {
		creep.move();
	    }
	}
	notifyListeners();
    }

}
