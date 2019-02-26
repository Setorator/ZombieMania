package se.liu.ida.davsc542kimla207.tddd78.project.zombiemania;


/**
 * Created by Kim on 26/04/15
 */

public class Creep extends GameObject
{

    private int speed;

    public Creep(int x, int y) {
	setX(x);
	setY(y);

    }

    // setters
    public void setSpeed(int x) {
	this.speed = x;
    }


    // move's the creep depending on which "movement-boolean" is set to true.
    public void move() {
	if (isRight()) {
	    this.image = rightImage;
	    this.x = x + speed;
	} else if (isLeft()) {
	    this.image = leftImage;
	    this.x = x - speed;
	}

	// after moving the creep we need to update the edges since they depend on the x and yPos
	updateEdges();
    }


}
