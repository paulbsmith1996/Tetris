import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Vector;

public class Block extends Vector<Rectangle> {

    private int x, y, blockType;
    private int width, height;
	
    private Color color;

    private final int SQUARE = 0;
    private final int LINE = 1;
    private final int SQUIGGLE_RIGHT = 2;
    private final int SQUIGGLE_LEFT = 3;
    private final int T_BLOCK = 4;
    private final int L_BLOCK_RIGHT = 5;
    private final int L_BLOCK_LEFT = 6;

    public static final int NUM_BLOCKS = 7;

    private Controller controller;

    public Block(int x, int y, int blockType) {
	this.x = x * Tetris.CUBE_DIM + Tetris.X_OFFSET;
	this.y = y * Tetris.CUBE_DIM + Tetris.Y_OFFSET;
		
	this.color = Color.RED;

	this.blockType = blockType;

	switch (blockType) {
	case SQUARE:
	    BlockMaker.makeSquare(this);
	    break;
	case LINE:
	    BlockMaker.makeLine(this);
	    break;
	case SQUIGGLE_RIGHT:
	    BlockMaker.makeSquiggleRight(this);
	    break;
	case SQUIGGLE_LEFT:
	    BlockMaker.makeSquiggleLeft(this);
	    break;
	case T_BLOCK:
	    BlockMaker.makeTBlock(this);
	    break;
	case L_BLOCK_RIGHT:
	    BlockMaker.makeLBlockRight(this);
	    break;
	case L_BLOCK_LEFT:
	    BlockMaker.makeLBlockLeft(this);
	    break;
	}
    }

    public Color getColor() { return this.color; }
    public void setColor(Color color) { this.color = color; }
	
    public void setWidth(int width) {
	this.width = Tetris.CUBE_DIM * width;
    }

    public void setHeight(int height) {
	this.height = Tetris.CUBE_DIM * height;
    }

    public int getX() {
	return this.x;
    }

    public void setX(int x) {
	this.x = x;
    }

    public int getY() {
	return this.y;
    }

    public void setY(int y) {
	this.y = y;
    }

    public boolean intersects(Block b) {
	for (Rectangle blockRect : this) {
	    for (Rectangle othersRect : b) {
		if (blockRect.intersects(othersRect)) {
		    return true;
		}
	    }
	}

	return false;
    }

    public void rotate(Controller c) {
		
	for (Rectangle r : this) {

	    r.x -= x;
	    r.y -= y;

	    int temp = r.x;

	    r.x = -r.y - Tetris.CUBE_DIM;
	    r.y = temp;

	    r.x += x;
	    r.y += y;
	}
		
	if(c.checkCollision(this)) {
	    antiRotate(c);
	}
		
	boolean okMove = true;
		
	for (Rectangle r : this) {
	    if (r.x < Tetris.X_OFFSET ||
		r.x >= Tetris.X_OFFSET + Tetris.GAME_WIDTH) {
		okMove = false;
		break;
	    }
		}
		
		if(!okMove) {
			antiRotate(c);
		}

	}

	public void antiRotate(Controller c) {
		for (Rectangle r : this) {

			r.x -= x;
			r.y -= y;

			int temp = r.x;

			r.x = r.y;
			r.y = -temp-10;

			r.x += x;
			r.y += y;
		}
		
		if(c.checkCollision(this)) {
			rotate(c);
		}
	}

	public void moveUp() {
		for (Rectangle r : this) {
			r.y -= Tetris.CUBE_DIM;
		}
	}

	public boolean moveLeft(Controller c) {
		boolean okMove = true;

		for (Rectangle r : this) {
			if (r.x - Tetris.CUBE_DIM < Tetris.X_OFFSET) {
				okMove = false;
				break;
			}
		}

		if (okMove) {
			for (Rectangle r : this) {
				r.x -= Tetris.CUBE_DIM;
			}
			x -= Tetris.CUBE_DIM;
		}

		if (c.checkCollision(this)) {
			moveRight(c);
		}

		return okMove;
	}

	public boolean moveRight(Controller c) {
		boolean okMove = true;

		for (Rectangle r : this) {
			if (r.x + Tetris.CUBE_DIM >= Tetris.X_OFFSET + Tetris.GAME_WIDTH) {
				okMove = false;
				break;
			}
		}

		if (okMove) {
			for (Rectangle r : this) {
				r.x += Tetris.CUBE_DIM;
			}
			x += Tetris.CUBE_DIM;
		}

		if (c.checkCollision(this)) {
			moveLeft(c);
		}

		return okMove;
	}

	public boolean fall() {

		boolean onBottom = false;

		for (Rectangle r : this) {
			if (r.y + Tetris.CUBE_DIM >= Tetris.Y_OFFSET + Tetris.GAME_HEIGHT) {
				onBottom = true;
				break;
			}
		}

		if (!onBottom) {
			for (Rectangle r : this) {
				r.y += Tetris.CUBE_DIM;
			}

			y += Tetris.CUBE_DIM;

			return true;
		}

		return false;
	}

	public void draw(Graphics g) {
		for (Rectangle r : this) {
			g.setColor(getColor());
			g.fillRect(r.x, r.y, r.width, r.height);
			g.setColor(Color.BLACK);
			g.drawRect(r.x, r.y, r.width, r.height);
		}
	}
}
