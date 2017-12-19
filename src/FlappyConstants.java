import java.awt.Color;


public class FlappyConstants {

	//Canvas
	public static final int APP_WIDTH = 450; //canvas will be 16 smaller
	public static final int APP_HEIGHT = 550; // canvas will be 38 smaller
	public static final int CANVAS_WIDTH = APP_WIDTH-16; //canvas will be 16 smaller
	public static final int CANVAS_HEIGHT = APP_HEIGHT-38; // canvas will be 38 smaller
	public static final int GRASS_HEIGHT = 15;
	public static final int GROUND_HEIGHT = 75;
	
	//Pipes
	public static final int PIPE_WIDTH = 40;
	public static final int PIPE_PAD = 40;
	public static final int PIPE_SPEED = 10;
	public static final int PIPE_FREQ = 1450;
	public static final int OPENING_HEIGHT = 90;
	
	//Rims
	public static final int RIM_HEIGHT = 12;
	public static final int RIM_WIDTH = RIM_HEIGHT+PIPE_WIDTH;	
	
	//Bird
	public static final int BIRD_SIZE = 20;
	public static final int BIRD_SPEED = 32;
	public static final int JUMP_STRENGTH = -9;
	public static final Color BIRD_COLOR = Color.RED;
	
}
