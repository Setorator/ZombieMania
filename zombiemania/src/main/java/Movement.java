package main.java;

/**
 * Created by David on 25/04/15.
 */
public abstract class Movement
{
    private Boolean left = false;
    private Boolean right = false;
    private Boolean jump = false;
    private int maxJumpHeight;
    private Boolean facingRight = true;

    // getters.
    public int getYPos() {
	return maxJumpHeight;
    }
    public boolean isLeft() {
	return left;
    }
    public boolean isRight() {
	return right;
    }
    public boolean isJump() {
	return jump;
    }
    public boolean getFacingRight() {
	return facingRight;
    }

    // setters.
    public void setMaxJumpHeight(final int y) {
	this.maxJumpHeight = y;
    }
    public void setLeft(Boolean left) {
	this.left = left;
	this.right = false;
        this.facingRight = false; //true is right false is left
    }
    public void setRight(Boolean right) {
	this.right = right;
	this.left = false;
        this.facingRight = true; //true is right false is left
    }
    public void setJump(Boolean jump) {
	this.jump = jump;
    }


}
