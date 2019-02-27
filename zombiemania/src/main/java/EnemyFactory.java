package main.java;

/**
 * Created by Kim on 24/04/2015
 */


// this is a factory which returns a creep of our desire depending on input
@SuppressWarnings("ALL")
public final class EnemyFactory
{
    private EnemyFactory() {}

    // the main mathod which returns the creep with the right width, height, looks etc.
    public static Creep getCreep(int xPos, int yPos, BlockType type) {
	switch (type) {
	    case MAN:
		Creep creep = new Creep(xPos, yPos);
		creep.setType(BlockType.MAN);
		creep.setWidth(1);
		creep.setHeight(2);
		creep.updateEdges();
		creep.setLeftImage(ResourceLoader.loadImageResource("main/resources/Pictures/soldierleft.gif").getImage());
		creep.setRightImage(ResourceLoader.loadImageResource("main/resources/Pictures/soldierright.gif").getImage());
		creep.setImage(creep.getLeftImage());
		creep.setSpeed(2);
		creep.setRight(true);

		return creep;


	    case HOUND:
		Creep hound = new Creep(xPos, yPos);
		hound.setType(BlockType.HOUND);
		hound.setWidth(1);
		hound.setHeight(1);
		hound.updateEdges();
		hound.setLeftImage(ResourceLoader.loadImageResource("main/resources/Pictures/dog.png").getImage());
		hound.setRightImage(ResourceLoader.loadImageResource("main/resources/Pictures/dog.png").getImage());
		hound.setImage(hound.getLeftImage());
		hound.setSpeed(2);
		hound.setRight(true);

		return hound;


	    default:
		return null;
	}
    }
}

