import java.awt.Color;

import acm.graphics.GRect;

public class Bird extends GRect implements Runnable{
	
	public double currentTop, currentBottom, right, left;
	public int RATE = 0;
	
	public Bird(int size, Color color){
		super(size, size);
		setFilled(true);
		setFillColor(color);       
	}

	public void run() {
		
		right = getX() + getWidth();
		left = getX();
		
		while(gameOn){
			gameOn = FlappyBird.gameOn;
			currentTop = getY();
			currentBottom = getY() + getHeight();
			move(0, accelerate());			
			pause(BIRD_SPEED);
		}
		setFillColor(Color.BLACK);
	}
	
	public int accelerate(){
		RATE++;
		return RATE;
	}
	
	public void jump() {
		if(getY() >= 0){
			RATE = JUMP_STRENGTH;
		}
	}
	
	private static final int JUMP_STRENGTH = FlappyConstants.JUMP_STRENGTH;
	private static final int BIRD_SPEED = FlappyConstants.BIRD_SPEED;
	
	private static boolean gameOn = FlappyBird.gameOn;
}