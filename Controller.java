import java.awt.Graphics;
import java.util.Vector;


public class Controller extends Vector<Block>{

    public Controller() {
		
    }
	
    public void draw(Graphics g) {
	for(Block b: this) {
	    b.draw(g);
	}
    }
	
    public boolean checkCollision(Block block) {
		
	for(Block b: this) {
	    if (b != block && b.intersects(block)) {
		return true;
	    }
	}
		
	return false;
    }
	
    public void checkLine() {
		
    }
}
