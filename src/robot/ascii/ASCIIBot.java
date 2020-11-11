/*
 Programming 1 S2 : Assignment 3 Part B
 s3717004 | Jeromy Cassar
 */
package robot.ascii;

import control.RobotControl;
import robot.Robot;
import robot.ascii.impl.Arm;
import robot.ascii.impl.Bar;
import robot.ascii.impl.Block;
import robot.ascii.impl.Drawable;
import robot.impl.RobotImpl;
import robot.impl.RobotInitException;

// ASCIIBot template code Programming 1 s1, 2018
// designed by Caspar, additional code by Ross
public class ASCIIBot extends AbstractASCIIBot implements Robot {
	public static void main(String[] args) {
		new RobotControl().control(new ASCIIBot(), null, null);
	}

	// MUST CALL DEFAULT SUPERCLASS CONSTRUCTOR!
	public ASCIIBot() {
		super();
	}

	@Override
	public void init(int[] barHeights, int[] blockHeights, int height, int width, int depth) {
		// in addition to validating init params this also sets up keyboard support for
		// the ASCIIBot!
		try {
			RobotImpl.validateInitParams(this, barHeights, blockHeights, height, width, depth);
		} catch (RobotInitException e) {
			System.err.println(e.getMessage());
			System.exit(0);
		}

		// clears previous frame/screen
		terminalFrame.clearScreen();

		// write init code here to place bars, blocks and initial arm position
		// suggest writing a separate method e.g. initImpl()
		initImpl(barHeights, blockHeights, height, width, depth);

		// call this to delay/animate and write out contents after draw
		// (i.e. after each robot move)
		delayAnimation();
	}
	//---private variables---
	private int[] barHeights;
	private int[] blockHeights;
	private int Height = 0;
	private int currentBlock;
	private Bar[] Bar;
	private Block[] Block;
	private Arm Arm;

	// this is just a dummy method to show you how to draw chars using Lanterna
	// you need to implement the provided classes to do your own ASCIIBot robot
	// drawing!
	// REMOVE when done
	private void initImpl(int[] barHeights, int[] blockHeights, int height, int width, int depth) {
		this.barHeights = barHeights;
		this.blockHeights = blockHeights;
		this.currentBlock = blockHeights.length - 1;
		
		//---create bar and block object---
		Bar = new Bar[this.barHeights.length];
		Block = new Block[this.blockHeights.length];

		int X = terminalFrame.getTerminalSize().getColumns() / Drawable.H_SCALE_FACTOR - 1;
		
		//---initialize array for bar and block---
		for (int i = 0; i < this.barHeights.length; i++) {
			Bar[i] = new Bar(this.barHeights[i], i + 3);
		}
		for (int i = 0; i < this.blockHeights.length; i++) {
			Block[i] = new Block(this.blockHeights[i], X, Height);
			Height += this.blockHeights[i];
		}
		//---update bar and blocks---
		updateBars();
		updateBlocks();		
		//---draw arm---
		Arm = new Arm(height, width, depth); //create bar object
		Arm.draw(terminalFrame); //draw arm
	}

	//---updating bar and blocks---
	private void updateBars() {
		// Draw bar array
		for (int i = 0; i < barHeights.length; i++) {
			Bar[i].draw(terminalFrame);
		}
	}
	public void updateBlocks() {
		// Draw block array
		for (int i = 0; i < blockHeights.length; i++) {
			Block[i].draw(terminalFrame);
		}
	}
	
	@Override
	public void pick() {
		// implement methods to draw robot environment using your implementation of
		// Arm.draw(), Bar.draw() etc.
		terminalFrame.clearScreen();
		if (currentBlock >= 0)
			Block[currentBlock].startMove();
		Arm.draw(terminalFrame);
		updateBars();
		updateBlocks();		
		delayAnimation();
		System.out.println("pick()");
	}

	@Override
	public void drop() {
		terminalFrame.clearScreen();
		Arm.drop();
		Block[currentBlock].stopMove();
		if (currentBlock > 0)
			currentBlock--;
		Arm.draw(terminalFrame);
		updateBars();
		updateBlocks();		
		delayAnimation();
		System.out.println("drop()");
	}

	@Override
	public void up() {
		terminalFrame.clearScreen();
		Arm.up();
		Block[currentBlock].up();
		Arm.draw(terminalFrame);
		updateBars();
		updateBlocks();		
		delayAnimation();
		System.out.println("up()");
	}

	@Override
	public void down() {
		terminalFrame.clearScreen();
		Arm.down();
		Block[currentBlock].down();
		Arm.draw(terminalFrame);
		updateBars();
		updateBlocks();		
		delayAnimation();
		System.out.println("down()");
	}

	@Override
	public void contract() {
		terminalFrame.clearScreen();
		Arm.contract();
		Block[currentBlock].contract();
		Arm.draw(terminalFrame);
		updateBars();
		updateBlocks();		
		delayAnimation();
		System.out.println("contract()");
	}

	@Override
	public void extend() {
		terminalFrame.clearScreen();
		Arm.extend();
		Block[currentBlock].extend();
		Arm.draw(terminalFrame);
		updateBars();
		updateBlocks();		
		delayAnimation();
		System.out.println("extend()");
	}

	@Override
	public void lower() {
		terminalFrame.clearScreen();
		Arm.lower();
		Block[currentBlock].lower();
		Arm.draw(terminalFrame);
		updateBars();
		updateBlocks();		
		delayAnimation();
		System.out.println("lower()");
	}

	@Override
	public void raise() {
		terminalFrame.clearScreen();
		Arm.raise();
		Block[currentBlock].raise();
		Arm.draw(terminalFrame);
		updateBars();
		updateBlocks();		
		delayAnimation();
		System.out.println("raise()");
	}
}