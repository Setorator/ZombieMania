package main.java;

/**
 * Created by David on 04/05/15.
 */
public class CollisionHandler
{
    private int mapWidth;
    private int mapHeight;
    private Player player;
    private Board board;
    private int offset = GameComponent.BLOCK_SIZE / 5;
    private int hitboxOffset = GameComponent.BLOCK_SIZE / 10;

    public CollisionHandler(Board board) {
	this.mapWidth = board.getMap()[0].length;
	this.mapHeight = board.getMap().length;
	this.player = board.getPlayer();
	this.board = board;
    }

    public int closestBlock(int xPos, int yPos, String direction) {
	// xPos and yPos converted to map-index
	int xMap = xPos / GameComponent.BLOCK_SIZE;
	int yMap = yPos / GameComponent.BLOCK_SIZE;


	switch (direction) {

	    case "left":
		for (int x = xMap; x >= 0; x--) {
		    if (occupied(x, yMap)) {
			// -100 because otherwise we are checking the occupying blocktypes left edge
			return xPos - x * 100 - 100;
		    }
		}
		return xPos;

	    case "right":
		for (int x = xMap; x < mapWidth; x++) {
		    if (occupied(x, yMap)) {
			return x * 100 - xPos;
		    }
		}
		return mapWidth * 100 - xPos;

	    case "down":
		for (int y = yMap; y < mapHeight; y++) {
		    if (occupied(xMap, y)) {
			return y * 100 - yPos;
		    }
		}
		return mapHeight * 100 - yPos;

	    case "up":
		for (int y = yMap; y >= 0; y--) {
		    if (occupied(xMap, y)) {
			// -100 because otherwise we are checking the occupying blocktypes top edge
			return yPos - y * 100 - 100;
		    }
		}
		return yPos;

	    default:
		return 0;
	}

    }

    // returns true if player are standing on a spike.
    // we are subtracts BLOCK_SIZE/2 so that at least half of the player's bootom touches the spike before it reacts.
    public boolean standingOnSpike() {
	return (board.getBlockType((player.getRightEdge() - GameComponent.BLOCK_SIZE / 2) / GameComponent.BLOCK_SIZE,
				   (player.getBottomEdge() - offset) / GameComponent.BLOCK_SIZE).equals(BlockType.SPIKE));

    }

    // returns true if we have reached the "finish-gate".
    public boolean finish() {
	return (board.getBlockType((player.getRightEdge() - GameComponent.BLOCK_SIZE / 2) / GameComponent.BLOCK_SIZE,
				   (player.getBottomEdge() - offset) / GameComponent.BLOCK_SIZE).equals(BlockType.FINISH));
    }

    // returns true if the player is hit by an enemy
    public boolean hitByEnemy() {
	for (Creep creep : board.getCreepList()) {
	    if (((player.getRightEdge() - hitboxOffset >= creep.getLeftEdge()) &&
		 player.getRightEdge() - hitboxOffset <= creep.getRightEdge()) ||
		((player.getLeftEdge() + hitboxOffset <= creep.getRightEdge() &&
		  player.getLeftEdge() + hitboxOffset >= creep.getLeftEdge()))) {
		if (((player.getBottomEdge() + hitboxOffset >= creep.getTopEdge()) &&
		     player.getBottomEdge() - hitboxOffset <= creep.getBottomEdge()) ||
		    ((player.getTopEdge() + hitboxOffset <= creep.getBottomEdge() &&
		      player.getTopEdge() + hitboxOffset >= creep.getTopEdge()))) {
		    return true;
		}
	    }
	}
	return false;
    }

    // returns true if the block at position (x,y) is a block which you can't move through
    public boolean occupied(int x, int y) {

	return (board.getBlockType(x, y).equals(BlockType.BOX)) ||
	       (board.getBlockType(x, y).equals(BlockType.DIRT)) ||
	       (board.getBlockType(x, y).equals(BlockType.GROUND));

    }

    // returns true if there is a block in the player's way.
    // +10/-10 is so we still will be able to move just under/above a gap of two blocks wide.
    public boolean cantMoveRight() {

	return closestBlock(player.getRightEdge() - hitboxOffset, player.getTopEdge() + hitboxOffset, "right") < hitboxOffset ||
	       closestBlock(player.getRightEdge() - hitboxOffset, player.getCenterEdge(), "right") < hitboxOffset ||
	       closestBlock(player.getRightEdge() - hitboxOffset, player.getBottomEdge() - hitboxOffset, "right") <
	       hitboxOffset;
    }

    public boolean cantMoveLeft() {

	return closestBlock(player.getLeftEdge() + hitboxOffset, player.getTopEdge() + hitboxOffset, "left") < hitboxOffset ||
	       closestBlock(player.getLeftEdge() + hitboxOffset, player.getCenterEdge(), "left") < hitboxOffset ||
	       closestBlock(player.getLeftEdge() + hitboxOffset, player.getBottomEdge() - hitboxOffset, "left") < hitboxOffset;
    }

    public boolean cantMoveDown() {
	return closestBlock(player.getLeftEdge() + hitboxOffset, player.getBottomEdge() - hitboxOffset, "down") <
	       hitboxOffset ||
	       closestBlock(player.getRightEdge() - hitboxOffset, player.getBottomEdge() - hitboxOffset, "down") < hitboxOffset;
    }

    public boolean cantMoveUp() {

	return closestBlock(player.getLeftEdge() + hitboxOffset, player.getTopEdge() + hitboxOffset, "up") < hitboxOffset ||
	       closestBlock(player.getRightEdge() - hitboxOffset, player.getTopEdge() + hitboxOffset, "up") < hitboxOffset;
    }

    // returns true if there is a block in the creep's way or if the next block to step on is "non-steppable"
    public boolean creepCantMoveRight(Creep creep) {

	return (closestBlock(creep.getRightEdge() - hitboxOffset, creep.getTopEdge() + hitboxOffset, "right") < hitboxOffset ||
		closestBlock(creep.getRightEdge() - hitboxOffset, creep.getCenterEdge(), "right") < hitboxOffset ||
		closestBlock(creep.getRightEdge() - hitboxOffset, creep.getBottomEdge() - hitboxOffset, "right") < hitboxOffset)

	       ||

	       // Checks if the next block to step on is a "steppable block"
	       !occupied((creep.getRightEdge() + hitboxOffset) / GameComponent.BLOCK_SIZE,
			 creep.getBottomEdge() / GameComponent.BLOCK_SIZE);


    }

    public boolean creepCantMoveLeft(Creep creep) {

	return closestBlock(creep.getLeftEdge() + hitboxOffset, creep.getTopEdge() + hitboxOffset, "left") < hitboxOffset ||
	       closestBlock(creep.getLeftEdge() + hitboxOffset, creep.getCenterEdge(), "left") < hitboxOffset ||
	       closestBlock(creep.getLeftEdge() + hitboxOffset, creep.getBottomEdge() - hitboxOffset, "left") < hitboxOffset ||
	       // Checks if the next block to step on is a "steppable block"
	       !occupied((creep.getLeftEdge() - hitboxOffset) / GameComponent.BLOCK_SIZE,
			 creep.getBottomEdge() / GameComponent.BLOCK_SIZE);
    }
}
