import java.awt.Color;

import acm.graphics.GRect;

public class LowerPipe extends GRect implements Runnable{
	
	public LowerPipe(int center){
		super(PIPE_WIDTH, HEIGHT-center-FlappyConstants.GROUND_HEIGHT-OPENING_HEIGHT/2);
		setFillColor(Color.GREEN);
		setFilled(true);
	}
	
	public void run(){
		while(getX() > -PIPE_WIDTH && gameOn){
			move(-1, 0);
			pause(PIPE_SPEED);
			gameOn = FlappyBird.gameOn;
		}
	}

	private static final int PIPE_SPEED = FlappyConstants.PIPE_SPEED;
	private static final int OPENING_HEIGHT = FlappyConstants.OPENING_HEIGHT;
	private static final int PIPE_WIDTH = FlappyConstants.PIPE_WIDTH;
	private static final int HEIGHT = FlappyBird.HEIGHT;

	private static boolean gameOn = FlappyBird.gameOn;
}
