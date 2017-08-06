import java.awt.Color;
import java.awt.Rectangle;


public class BlockMaker {

    private static final int CD = Tetris.CUBE_DIM;
	
    public static void makeSquare(Block b) {
	b.add(new Rectangle(b.getX(), b.getY(), CD, CD));
	b.add(new Rectangle(b.getX() + CD, b.getY(), CD, CD));
	b.add(new Rectangle(b.getX(), b.getY() + CD, CD, CD));
	b.add(new Rectangle(b.getX() + CD, b.getY() + CD, CD, CD));
		
	b.setColor(Color.RED);
    }
	
    public static void makeLine(Block b) {
	b.add(new Rectangle(b.getX() + CD, b.getY(), CD, CD));
	b.add(new Rectangle(b.getX() + 2 * CD, b.getY(), CD, CD));
	b.add(new Rectangle(b.getX() + 3 * CD, b.getY(), CD, CD));
	b.add(new Rectangle(b.getX() + 4 * CD, b.getY(), CD, CD));
		
	b.setColor(Color.CYAN);
    }
	
    public static void makeSquiggleRight(Block b) {
	b.add(new Rectangle(b.getX() + CD, b.getY(), CD, CD));
	b.add(new Rectangle(b.getX() + 2 * CD, b.getY(), CD, CD));
	b.add(new Rectangle(b.getX(), b.getY() + CD, CD, CD));
	b.add(new Rectangle(b.getX() + CD, b.getY() + CD, CD, CD));
		
	b.setColor(Color.GREEN);
    }
	
    public static void makeSquiggleLeft(Block b) {
	b.add(new Rectangle(b.getX(), b.getY(), CD, CD));
	b.add(new Rectangle(b.getX() + CD, b.getY(), CD, CD));
	b.add(new Rectangle(b.getX() + CD, b.getY() + CD, CD, CD));
	b.add(new Rectangle(b.getX() + 2 * CD, b.getY() + CD, CD, CD));
		
	b.setColor(Color.YELLOW);
    }
	
    public static void makeTBlock(Block b) {
	b.add(new Rectangle(b.getX(), b.getY(), CD, CD));
	b.add(new Rectangle(b.getX() + CD, b.getY(), CD, CD));
	b.add(new Rectangle(b.getX() + 2 * CD, b.getY(), CD, CD));
	b.add(new Rectangle(b.getX() + CD, b.getY() + CD, CD, CD));
		
	b.setColor(Color.PINK);
    }
	
    public static void makeLBlockRight(Block b) {
	b.add(new Rectangle(b.getX(), b.getY(), CD, CD));
	b.add(new Rectangle(b.getX(), b.getY() + CD, CD, CD));
	b.add(new Rectangle(b.getX(), b.getY() + 2 * CD, CD, CD));
	b.add(new Rectangle(b.getX() + CD, b.getY() + 2 * CD, CD, CD));
		
	b.setColor(Color.ORANGE);
    }
	
    public static void makeLBlockLeft(Block b) {
	b.add(new Rectangle(b.getX() + CD, b.getY(), CD, CD));
	b.add(new Rectangle(b.getX() + CD, b.getY() + CD, CD, CD));
	b.add(new Rectangle(b.getX() + CD, b.getY() + 2 * CD, CD, CD));
	b.add(new Rectangle(b.getX(), b.getY() + 2 * CD, CD, CD));
		
	b.setColor(Color.MAGENTA);
    }
}
