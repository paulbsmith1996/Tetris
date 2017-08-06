import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;


public class KeyInput extends KeyAdapter {

	private Block block;
	private Tetris t;
	private Controller cont;
	private boolean pause;
	
    public KeyInput(Block block, Tetris t) {
	this.block = block;
	this.t = t;
	this.pause = false;
    }
	
    public void setBlock(Block b) { this.block = b; }
	
    public boolean pause() { return this.pause; }
	
    @Override
    public void keyPressed(KeyEvent e) {
		
	int key = e.getKeyCode();
		
	if(key == KeyEvent.VK_LEFT) {
	    block.moveLeft(t.getController());
	} else if(key == KeyEvent.VK_RIGHT) {
	    block.moveRight(t.getController());
	} else if(key == KeyEvent.VK_DOWN) {
	    block.fall();
	} else if(key == KeyEvent.VK_UP) {
	    block.rotate(t.getController());
	} else if(key == KeyEvent.VK_SPACE) {
	    pause = !pause;
	}
    }
	
    @Override
    public void keyReleased(KeyEvent e) {
		
    }
}
