import java.applet.Applet;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;
import javax.swing.JFrame;

public class Tetris extends Applet implements Runnable {

    private Thread ticker;
    private boolean running;

    public static final int CUBE_DIM = 10;
    public static final int NUM_COLS = 16, NUM_ROWS = 35;
    public static final int GAME_WIDTH = NUM_COLS * CUBE_DIM,
	GAME_HEIGHT = NUM_ROWS * CUBE_DIM;

    private final long FRAMERATE = 1000 / 30;

    private final int WINDOW_WIDTH = 350, WINDOW_HEIGHT = 500;
    private final int SCORE_X = 250, SCORE_Y = 50;
    public static final int X_OFFSET = 50, Y_OFFSET = 30;

    private int speed, count, tick;
    private int score;
    private int[] blockCounts;

    private Controller controller;
    private Block curBlock;
    private KeyInput kInput;

    private Random r;
    private GameState gState;

    public Controller getController() {
	return this.controller;
    }

    public void setState(GameState gs) { this.gState = gs; }

    public void init() {
	//this.resize(WINDOW_WIDTH, WINDOW_HEIGHT);
	r = new Random();

	controller = new Controller();

	curBlock = new Block(8, 4, r.nextInt(Block.NUM_BLOCKS));

	controller.add(curBlock);

	kInput = new KeyInput(curBlock, this);
	this.addKeyListener(kInput);

	this.speed = 6;
	this.count = 0;
	this.score = 0;
	this.tick = 0;

	this.blockCounts = new int[NUM_ROWS];

	this.gState = GameState.RUNNING;
    }

    public void run() {
	init();
	while (running) {

	    if(gState == GameState.RUNNING) {
		for(int i = 0; i < 5; i++) {
		    if(blockCounts[i] > 0) {
			setState(GameState.OVER);
		    }
		}
		
		if (!kInput.pause()) {
		    if (count < speed) {
			count++;
		    } else {
			count = 0;
			
			boolean fallen = curBlock.fall();
			boolean collided = controller.checkCollision(curBlock);
			
			if (collided) {
			    curBlock.moveUp();
			}
			
			if (!fallen || collided) {
			    for (Rectangle r : curBlock) {
				int rowNum = (r.y - Y_OFFSET) / CUBE_DIM;
				this.blockCounts[rowNum]++;
			    }
			    
			    for (int i = 0; i < NUM_ROWS; i++) {
				int count = blockCounts[i];
				if (count >= NUM_COLS) {
				    tick++;
				    
				    if((tick + 1) % 10 == 0 && speed > 0) {
					System.out.println("Speed increased");
					speed--;
				    }
				    score += NUM_COLS;
				    for (Block block : controller) {
					int size = block.size();
					for (int k = 0; k < size; k++) {
					    Rectangle r = block.elementAt(k);
					    if ((r.y - Y_OFFSET) / CUBE_DIM == i) {
						block.set(k, null);
					    }
					}
					
					while (block.contains(null)) {
					    block.remove(null);
					}
					
					// i is the first row from the bottom that is full
					int j = i;
					while (blockCounts[j] > 0 && j >= 0) {
					    for (Rectangle r : block) {
						if ((r.y - Y_OFFSET) / CUBE_DIM == j - 1) {
						    r.y += CUBE_DIM;
						}
					    }
					    j--;
					}
				    }
				    
				    blockCounts[i] = 0;
				    
				    // i is the first full row from the bottom
				    int temp = i;
				    // loop through from ith row to top row
				    while (temp > 0 && blockCounts[temp - 1] > 0) {
					blockCounts[temp] = blockCounts[temp - 1];
					temp--;
				    }
				    
				    blockCounts[temp] = 0;
				    
				}
			    }
			    
			    curBlock = new Block(8, 4, r.nextInt(Block.NUM_BLOCKS));
			    kInput.setBlock(curBlock);
			    controller.add(curBlock);
			}
		    }
		}
	    }

	    repaint();

	    try {
		ticker.sleep(FRAMERATE);
	    } catch (InterruptedException e) {}
	}
    }

    public void paint(Graphics g) {
	if(gState == GameState.RUNNING) {
	    g.setColor(Color.BLUE);
	    g.fillRect(X_OFFSET, Y_OFFSET, GAME_WIDTH, GAME_HEIGHT);
	    
	    g.setColor(Color.BLACK);
	    g.drawRect(X_OFFSET, Y_OFFSET, GAME_WIDTH, GAME_HEIGHT);
	    
	    g.drawString("Score: " + score, SCORE_X, SCORE_Y);
	    g.drawRect(SCORE_X - 10, SCORE_Y - 15, 100, 30);
	    
	    controller.draw(g);
	} else if(gState == GameState.OVER) {
	    g.setColor(Color.BLACK);
	    g.fillRect(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);

	    g.setColor(Color.RED);
	    g.drawString("GAME OVER", WINDOW_WIDTH / 2, WINDOW_HEIGHT / 2);
	    g.drawString("Final Score: " + score,
			 WINDOW_WIDTH / 2, WINDOW_HEIGHT / 2 + 30);
	}
    }

    public void start() {
	// Check for either no Thread or a dead Thread
	if (ticker == null || !ticker.isAlive()) {
	    running = true;
	    // Reassign ticker in case it is only dead
	    ticker = new Thread(this);
	    ticker.setPriority(Thread.MIN_PRIORITY + 1);
	    ticker.start();
	}
    }

    public void stop() {
	running = false;
    }

    public static void main(String[] args) {

	final int WINDOW_WIDTH = 350, WINDOW_HEIGHT = 500;
	final String TITLE = "TETRIS!";
	
	JFrame frame = new JFrame();
	Tetris tetris = new Tetris();

	frame.setTitle(TITLE);
	frame.add(tetris);

	frame.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	frame.setFocusable(true);
	frame.setLocationRelativeTo(null);
	frame.setResizable(false);
	frame.setVisible(true);
	
	tetris.start();
    }
}
