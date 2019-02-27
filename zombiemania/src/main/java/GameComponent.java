package main.java;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.AbstractMap;
import java.util.EnumMap;


/**
 * Created by David on 16/04/15.
 */
@SuppressWarnings("ALL")
public class GameComponent extends JComponent implements BoardListener
{
    private Board board;
    private final Image background;
    private final Image heart;
    private AbstractMap<BlockType, Image> imageMap;
    public final static int BLOCK_SIZE = 100;


    //contains information about the color of each BlockType
    public GameComponent(final Board board) {
	this.board = board;
	this.heart = ResourceLoader.loadImageResource("main/resources/Pictures/heart.png").getImage();
	imageMap = new EnumMap<>(BlockType.class);
	this.background = ResourceLoader.loadImageResource("main/resources/Pictures/background.gif").getImage();

	setImages();


	// we are moving our player around by using key-bindings which relies on the InputMap and ActionMap classes that we
	// are using below this actions.
	Action moveLeft = new AbstractAction()
	{
	    public void actionPerformed(ActionEvent e) {
		if (!board.getCollisionHandler().cantMoveLeft()) {
		    board.getPlayer().setLeft(true);
		}
	    }
	};
	Action moveRight = new AbstractAction()
	{
	    public void actionPerformed(ActionEvent e) {
		if (!board.getCollisionHandler().cantMoveRight()) {
		    board.getPlayer().setRight(true);
		}
	    }
	};
	Action fallDown = new AbstractAction()
	{
	    public void actionPerformed(ActionEvent e) {
		if (!board.getCollisionHandler().cantMoveDown()) {
		    board.getPlayer().fallDown();
		}
	    }
	};
	Action jump = new AbstractAction()
	{
	    @Override public void actionPerformed(final ActionEvent e) {
		if (!board.getCollisionHandler().cantMoveUp() && board.getCollisionHandler().cantMoveDown()) {
		    board.getPlayer().setJump(true);
		    board.getPlayer().setMaxJumpHeight(board.getPlayer().getY() - (3 * BLOCK_SIZE));
		}
	    }
	};

	// when we releases the left or right key we calls this methods.
	Action rMoveLeft = new AbstractAction()
	{
	    public void actionPerformed(final ActionEvent e) {
		board.getPlayer().setLeft(false);
		board.getPlayer().rMoveLeft();
	    }
	};
	Action rMoveRight = new AbstractAction()
	{
	    public void actionPerformed(final ActionEvent e) {
		board.getPlayer().setRight(false);
		board.getPlayer().rMoveRight();
	    }
	};


	this.getInputMap().put(KeyStroke.getKeyStroke("LEFT"), "moveLeft");
	this.getInputMap().put(KeyStroke.getKeyStroke("RIGHT"), "moveRight");
	this.getInputMap().put(KeyStroke.getKeyStroke("DOWN"), "fallDown");
	this.getInputMap().put(KeyStroke.getKeyStroke("released LEFT"), "rMoveLeft");
	this.getInputMap().put(KeyStroke.getKeyStroke("released RIGHT"), "rMoveRight");
	this.getInputMap().put(KeyStroke.getKeyStroke("released DOWN"), "rMoveDown");
	this.getInputMap().put(KeyStroke.getKeyStroke("SPACE"), "jump");


	this.getActionMap().put("moveLeft", moveLeft);
	this.getActionMap().put("moveRight", moveRight);
	this.getActionMap().put("fallDown", fallDown);
	this.getActionMap().put("jump", jump);
	this.getActionMap().put("rMoveLeft", rMoveLeft);
	this.getActionMap().put("rMoveRight", rMoveRight);


    }

    // sets all the images for our block types.
    public void setImages() {
	imageMap.put(BlockType.GROUND, ResourceLoader.loadImageResource("main/resources/Pictures/grassblock.png").getImage());
	imageMap.put(BlockType.BOX, ResourceLoader.loadImageResource("main/resources/Pictures/woodblock.png").getImage());
	imageMap.put(BlockType.SPIKE, ResourceLoader.loadImageResource("main/resources/Pictures/spikes.png").getImage());
	imageMap.put(BlockType.STARTSIGN, ResourceLoader.loadImageResource("main/resources/Pictures/start.png").getImage());
	imageMap.put(BlockType.INSTRUCTIONS, ResourceLoader.loadImageResource("main/resources/Pictures/startsign.png").getImage());
	imageMap.put(BlockType.FINISH, ResourceLoader.loadImageResource("main/resources/Pictures/gateway.gif").getImage());
	imageMap.put(BlockType.DIRT, ResourceLoader.loadImageResource("main/resources/Pictures/dirtblock.png").getImage());
    }

    // Our main method for drawing the board, player etc. in the frame.
    @Override protected void paintComponent(Graphics g) {
	super.paintComponent(g);
	Player player = board.getPlayer();
	g.drawImage(background, 0, 0, ZombieFrame.FRAME_WIDTH, ZombieFrame.FRAME_HEIGHT, null);

	// draws out the hearts representing the player's lives in the upper left corner of the frame.
	g.setColor(Color.RED);
	g.drawString("L i v e s : ", 30, BLOCK_SIZE / 5);
	//we felt it was unnecesary to see this as a magic number since it is the placement on the preset screen we created

	final int maxLives = 3;
	if (player.getLives() == maxLives) {
	    g.drawImage(heart, 120, 40, BLOCK_SIZE / 2, BLOCK_SIZE / 2, null);
	    g.drawImage(heart, 70, 40, BLOCK_SIZE / 2, BLOCK_SIZE / 2, null);

	} else if (player.getLives() == 2) {
	    g.drawImage(heart, 70, 40, BLOCK_SIZE / 2, BLOCK_SIZE / 2, null);
	}
	g.drawImage(heart, BLOCK_SIZE / 5, 40, BLOCK_SIZE / 2, BLOCK_SIZE / 2, null);


	// this is used so that we gets a "camera" that follows the player as he moves through the board.
	g.translate(-player.getX() + BLOCK_SIZE * 3, 0);

	// goes through the map and paints all the components that is supposed to be drawn out.
	for (int y = 0; y < board.getMap().length; y++) {
	    for (int x = 0; x < board.getMap()[0].length; x++) {


		if (board.getBlockType(x, y).equals(BlockType.GROUND) || board.getBlockType(x, y).equals(BlockType.BOX) ||
		    board.getBlockType(x, y).equals(BlockType.SPIKE) || board.getBlockType(x, y).equals(BlockType.STARTSIGN) ||
		    board.getBlockType(x, y).equals(BlockType.DIRT)) {
		    g.drawImage(imageMap.get(board.getBlockType(x, y)), x * BLOCK_SIZE, y * BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE,
				null);

		} else if (board.getBlockType(x, y).equals(BlockType.MAN)) {

		    // when we finds a creep in the map we creates a Creep and adds it to our array of creeps.
		    // we also remove it from the map so that we don't paint it out twice by using both pixel-coordinates and
		    // map-coordinates.
		    Creep man = EnemyFactory.getCreep((x * BLOCK_SIZE), (y * BLOCK_SIZE), BlockType.MAN);
		    board.addCreep(man);
		    board.removeFromMap(x, y);

		} else if (board.getBlockType(x, y).equals(BlockType.HOUND)) {

		    Creep hound = EnemyFactory.getCreep((x * BLOCK_SIZE), (y * BLOCK_SIZE), BlockType.HOUND);
		    board.addCreep(hound);
		    board.removeFromMap(x, y);

		} else if (board.getBlockType(x, y).equals(BlockType.INSTRUCTIONS)) {
		    g.drawImage(imageMap.get(board.getBlockType(x, y)), x * BLOCK_SIZE, y * BLOCK_SIZE, BLOCK_SIZE * 3,
				BLOCK_SIZE * 2, null);

		} else if (board.getBlockType(x, y).equals(BlockType.FINISH)) {
		    g.drawImage(imageMap.get(board.getBlockType(x, y)), x * BLOCK_SIZE, y * BLOCK_SIZE, BLOCK_SIZE,
				BLOCK_SIZE * 2, null);
		}
	    }
	}

	// goes through our creep-array and paint them out depending on their pixel-coordinates.
	for (Creep creep : board.getCreepList()) {
	    g.drawImage(creep.getImage(), creep.getX(), creep.getY(), creep.getWidth() * BLOCK_SIZE,
			creep.getHeight() * BLOCK_SIZE, null);
	}

	// paints out player depending on his pixel-coordinates.
	g.drawImage(player.getImage(), player.getX(), player.getY(), player.getWidth() * BLOCK_SIZE,
		    player.getHeight() * BLOCK_SIZE, null);

    }

    // repaints our frame every time anything has changed in it (player has moved, or a block has been removed).
    public void boardChanged() {
	this.repaint();
    }


}
