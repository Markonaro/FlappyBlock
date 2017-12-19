/* ------------------------------------------------------------------
 * This program is a game based on Flappy Bird.
 * The concept is that the Program will create a canvas, upon which 
 * it will add a "Bird" (a GRect) that has the potential to collide
 * with "Pipes" (a set of GRects). If the bird collides with any part
 * of a pipe, or if it collides with the ground, the game ends. For
 * each pipe that the bird flies past, a point is awarded.
 * ------------------------------------------------------------------
 */

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import acm.graphics.GCanvas;
import acm.graphics.GLabel;
import acm.graphics.GPoint;
import acm.graphics.GRect;
import acm.program.Program;
import acm.util.RandomGenerator;

import javax.swing.Timer;

public class FlappyBird extends Program implements ActionListener, MouseListener{
	
	private static final long serialVersionUID = 1L;
	
	public static final int APPLICATION_WIDTH = FlappyConstants.APP_WIDTH;
	public static final int APPLICATION_HEIGHT = FlappyConstants.APP_HEIGHT;

	public static int WIDTH = FlappyConstants.CANVAS_WIDTH;
	public static int HEIGHT = FlappyConstants.CANVAS_HEIGHT;	

	public static volatile boolean gameOn = false;
	
	public static Integer score = new Integer(0);
	
	public static final int GRASS_HEIGHT = FlappyConstants.GRASS_HEIGHT;
	public static final int GROUND_HEIGHT = FlappyConstants.GROUND_HEIGHT;
	public static final int BIRD_SIZE = FlappyConstants.BIRD_SIZE;
	public static final int PIPE_FREQ = FlappyConstants.PIPE_FREQ;
	public static final int OPENING_HEIGHT = FlappyConstants.OPENING_HEIGHT;
	public static final int PIPE_PAD = FlappyConstants.PIPE_PAD;
	public static final int RIM_HEIGHT = FlappyConstants.RIM_HEIGHT;
	public static final int RIM_WIDTH = FlappyConstants.RIM_WIDTH;
	public static final int PIPE_WIDTH = FlappyConstants.PIPE_WIDTH;
	
	public void init() {
		add(canvas);
		canvas.addKeyListener(this);
		canvas.addMouseListener(this);
		canvas.setBackground(Color.CYAN.brighter().brighter());
		
		GRect ground = new GRect(0, HEIGHT-GROUND_HEIGHT, WIDTH, GROUND_HEIGHT);
		ground.setFillColor(Color.ORANGE.darker());
		ground.setFilled(true);
		canvas.add(ground);
		
		GRect grass = new GRect(0, HEIGHT-GROUND_HEIGHT, WIDTH, GRASS_HEIGHT);
		grass.setFillColor(Color.GREEN.darker());
		grass.setFilled(true);
		canvas.add(grass);
	}
	
	public void run() {
		//Wait to start the game upon mouse click
			GLabel start = new GLabel("Click to start!");
			start.setFont("Arial-48");
			canvas.add(start, (WIDTH-start.getWidth())/2, (HEIGHT-start.getHeight())/2);
			while(!gameOn){
				pause(1);
			}
			canvas.remove(start);
		
		//Create the bird and score counter, as well as initialize the first pipe 
			bird();
			scoreDisplay();
			pipe();
		
		//Keep track of the 4 corners of the bird
			topRight = new GPoint(myBird.right+1, myBird.currentTop+1);
			topLeft = new GPoint(myBird.left-1, myBird.currentTop+1); 
			bottomRight = new GPoint(myBird.right+1, myBird.currentBottom-1);
			bottomLeft = new GPoint(myBird.left-1, myBird.currentBottom-1);
		
		//Initialize an array to hold the corner points, to allow for easier cycling
			GPoint colliders[] = new GPoint[4];
			
		//Create a timer that will create pipes at a given interval
			ActionListener taskPerformer = new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					pipe();
				}
			};
			Timer timer = new Timer(PIPE_FREQ, taskPerformer);
			timer.start();
			
		//So long as any of the 4 corners of the bird are not colliding with anything, execute
			while(gameOn){
			//Continuously update the corner points
				topRight.setLocation(myBird.right+1, myBird.currentTop+1);
				topLeft.setLocation(myBird.left-1, myBird.currentTop+1);
				bottomRight.setLocation(myBird.right+1, myBird.currentBottom-1);
				bottomLeft.setLocation(myBird.left-1, myBird.currentBottom-1);
			
			//Reassign the corner points in the array
				colliders[0] = topRight;
				colliders[1] = topLeft;
				colliders[2] = bottomRight;
				colliders[3] = bottomLeft;
				
			//Check to see if any of the corners have collided with anything- pipe or ground
				for(GPoint point : colliders){
					if(canvas.getElementAt(point) != null){
						gameOn = false;
						timer.stop();
						break;
					}
				}
			}
			
		birdCheck(colliders);
			
	//Repaint the bird on top of the pipes
		canvas.add(myBird);
		gameOver();
	}

	/*
	private void background(){
		Image img = null;
		try {
			img = ImageIO.read(new File("background.png"));
		} catch (IOException e) {
		}
		
		Graphics g = null;
		g.drawImage(img, 0, 0, null);
	} 
	
	public void paint(Graphics g){
		
	}
	*/
	
	public void birdCheck(GPoint[] corners){
		int rate = myBird.RATE;
		int count = 0;
	//If the bird has crashed while flying upwards
		if(rate < 0){
			System.out.println("if, upper");
		//Figure out how far the bird overshot upwards into the GObject
			for(count = 0; count <= rate; count++){
				if(canvas.getElementAt(corners[0].getX(), corners[0].getY()+count) == null &&
						canvas.getElementAt(corners[1].getX(), corners[1].getY()+count) == null){
					System.out.println("In while loop, count: " + count);
					break;
				}
				count++;
				System.out.println("Count: " + count);
			}
			System.out.println("Post while loop");
		} else if(rate > 0){
			System.out.println("if, lower");
			while(true){
				if(canvas.getElementAt(corners[2].getX(), corners[2].getY()-count) == null &&
						canvas.getElementAt(corners[3].getX(), corners[3].getY()-count) == null){
					System.out.println("In if statement, count: " + count);
					break;
				}
				count--;
				System.out.println("Count: " + count);
			}
			System.out.println("Post while loop");
		}
		myBird.move(0, count);
	}
	
	public void scoreDisplay(){
		scoreLabel.setLabel(score.toString());
		scoreLabel.setFont("Arial-36");
		scoreLabel.setLocation((canvas.getWidth()-scoreLabel.getWidth())/2, 30);
		canvas.add(scoreLabel);
	}
	
	private void bird(){
		//Create a new bird, assign it to a thread, kick it off		
		myBird = new Bird(BIRD_SIZE, Color.RED);
		canvas.add(myBird, (WIDTH-BIRD_SIZE)/2, (HEIGHT-GROUND_HEIGHT-BIRD_SIZE)/2);
		Thread b = new Thread(myBird);
		b.start();
	}
	
	private void pipe(){
		
	//Create a random center point for the pipes to be drawn around
		int center = rgen.nextInt(PIPE_PAD+OPENING_HEIGHT/2, HEIGHT-PIPE_PAD-GROUND_HEIGHT-OPENING_HEIGHT/2);
		
		Rim botRim = new Rim(center);
		Rim topRim = new Rim(center);
		LowerPipe botPipe = new LowerPipe(center);
		UpperPipe topPipe = new UpperPipe(center);

		canvas.add(botPipe, WIDTH+(RIM_WIDTH-PIPE_WIDTH)/2, center+OPENING_HEIGHT/2);
		canvas.add(topPipe, WIDTH+(RIM_WIDTH-PIPE_WIDTH)/2, 0);
		canvas.add(botRim, WIDTH, center+OPENING_HEIGHT/2);
		canvas.add(topRim, WIDTH, center-OPENING_HEIGHT/2-RIM_HEIGHT);
		
		Thread br = new Thread(botRim);
		Thread tr = new Thread(topRim);
		Thread bp = new Thread(botPipe);
		Thread tp = new Thread(topPipe);
		
		br.start();
		tr.start();
		bp.start();
		tp.start();
		
	//Write the score on top of the pipes
		scoreDisplay();
	}
	
	//Animate the bird falling and end the game
	private void gameOver(){
		pause(PIPE_FREQ);
		while(myBird.getY()+myBird.getHeight() <= HEIGHT){
			myBird.move(0, 10);
			pause(FlappyConstants.BIRD_SPEED);
		}
		GLabel gameOver = new GLabel("Game Over");
		gameOver.setFont("Arial-48");
		gameOver.setLocation((WIDTH-gameOver.getWidth())/2, (HEIGHT-gameOver.getHeight())/2);
		
		canvas.add(gameOver);
	}
	
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_SPACE) {
			myBird.jump();
		}
    }
	
	public void keyTyped(KeyEvent e){
	}
	
	public void keyReleased(KeyEvent e){	
	}
	
	public void mouseClicked(MouseEvent e){
		if(!gameOn){
			gameOn = true;
		}
	}
	
	private RandomGenerator rgen = RandomGenerator.getInstance();
	private Bird myBird;
	private GCanvas canvas = new GCanvas();
	
	private GPoint topRight;
	private GPoint bottomRight;
	private GPoint topLeft; 
	private GPoint bottomLeft;
	
	private GLabel scoreLabel = new GLabel("");
			
}