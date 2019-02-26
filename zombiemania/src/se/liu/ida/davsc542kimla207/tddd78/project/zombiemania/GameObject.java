package se.liu.ida.davsc542kimla207.tddd78.project.zombiemania;

import java.awt.*;

/**
 * Created by David on 04/05/15.
 */
public abstract class GameObject extends Movement
{
    protected int x, y;
    protected int height;
    protected int width;
    protected BlockType type = null;
    protected Image image = null;
    protected Image leftImage = null;
    protected Image rightImage = null;

    // The Players edges in "pixel-coordinates", used for collision handling.
    protected int leftEdge;
    protected int rightEdge;
    protected int topEdge;
    protected int centerEdge;
    protected int bottomEdge;

    // Getters
    public int getX() {
	return x;
    }
    public int getY() {
	return y;
    }
    public int getWidth() {
	return width;
    }
    public int getHeight() {
	return height;
    }
    public Image getImage() {
	return image;
    }
    public Image getLeftImage() {
	return leftImage;
    }
    public BlockType getType() {
	return type;
    }
    public int getBottomEdge() {
	return bottomEdge;
    }
    public int getCenterEdge() {
	return centerEdge;
    }
    public int getTopEdge() {
	return topEdge;
    }
    public int getRightEdge() {
	return rightEdge;
    }
    public int getLeftEdge() {
	return leftEdge;
    }

    // Setters
    public void setX(int x) {
	this.x = x;
	updateEdges();
    }
    public void setY(int y) {
	this.y = y;
	updateEdges();
    }
    public void setHeight(final int height) {
	this.height = height;
    }
    public void setWidth(final int width) {
	this.width = width;
    }
    public void setType(final BlockType type) {
	this.type = type;
    }
    public void setLeftImage(final Image leftImage) {
	this.leftImage = leftImage;
    }
    public void setImage(final Image image) {
	this.image = image;
    }
    public void setRightImage(final Image rightImage) {
	this.rightImage = rightImage;
    }

    // Every time we move the player we must change the coordinates of the edges.
    public void updateEdges() {
	this.leftEdge = x;
	this.rightEdge = (x + (width * GameComponent.BLOCK_SIZE));
	this.topEdge = y;
	this.centerEdge = (y + (height * GameComponent.BLOCK_SIZE / 2));
	this.bottomEdge = (y + (height * GameComponent.BLOCK_SIZE));

    }
}
