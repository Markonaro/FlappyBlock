import java.awt.Color;

import acm.graphics.GRect;

public class Rim extends GRect implements Runnable{
	
	public static int score;
	
	public Rim(int center){
		super(RIM_WIDTH, RIM_HEIGHT);
		setFillColor(Color.GREEN);
		setFilled(true);
	}
	
	public void run(){
		while(getX() > -RIM_WIDTH && gameOn){
			move(-1, 0);
			pause(PIPE_SPEED);
			gameOn = FlappyBird.gameOn;
		}
	}
	

	private static final int PIPE_SPEED = FlappyConstants.PIPE_SPEED;
	private static final int RIM_WIDTH = FlappyConstants.RIM_WIDTH;
	private static final int RIM_HEIGHT = FlappyConstants.RIM_HEIGHT;

	private static boolean gameOn = FlappyBird.gameOn;
}