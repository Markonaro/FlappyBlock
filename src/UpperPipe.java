import java.awt.Color;

import acm.graphics.GRect;

public class UpperPipe extends GRect implements Runnable{

	public UpperPipe(int center){
		super(PIPE_WIDTH, center-OPENING_HEIGHT/2);
		setFillColor(Color.GREEN);
		setFilled(true);
	}
	
	public void run(){
		while(getX() > -PIPE_WIDTH && gameOn){
			move(-1, 0);
			pause(PIPE_SPEED);
			gameOn = FlappyBird.gameOn;
			FlappyBird.score += score();
		}
	}
	
	public int score(){
		if(getX()+getWidth()+(RIM_WIDTH-PIPE_WIDTH)/2 + 1 == (WIDTH-BIRD_SIZE)/2){
			return 1;
		} else {
			return 0;
		}
	}

	private static final int PIPE_SPEED = FlappyConstants.PIPE_SPEED;
	private static final int OPENING_HEIGHT = FlappyBird.OPENING_HEIGHT;
	private static final int PIPE_WIDTH = FlappyConstants.PIPE_WIDTH;
	private static final int RIM_WIDTH = FlappyConstants.RIM_WIDTH;
	private static final int BIRD_SIZE = FlappyConstants.BIRD_SIZE;
	private static final int WIDTH = FlappyConstants.APP_WIDTH;

	private static boolean gameOn = FlappyBird.gameOn;
}
