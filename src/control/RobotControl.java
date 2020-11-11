 /*
 	COSC1073 - Programming 1 
 	s3717004 | Jeromy Cassar
 */

package control;

import robot.Robot;

//Robot Assignment for Programming 1 s1 2018
//Adapted by Caspar and Ross from original Robot code in written by Dr Charles Thevathayan
public class RobotControl implements Control {
	private int height = Control.INITIAL_HEIGHT;
	private int width = Control.INITIAL_WIDTH;
	private int depth = Control.INITIAL_DEPTH;
	private int[] barHeights;
	private int[] blockHeights;
	private Robot robot;
	protected int maxHeight = 0;
	
	// -----VARIABLES-----
	private int currentBlock; // KEEPS TRACK OF THE CURRENT BLOCK BEING DROPPED/PICKED
	private int[] columnHeights = new int[10];
	private int columnDestination = Control.MAX_BLOCK_HEIGHT; // USED TO TRACK WHEN A BLOCK SIZE OF 3 IS ON TOP OF THE STACK
	private int d; // STORES BLOCK DESTINATIONS LOWER THAN 3
	private int clearance; // USED TO FIND CLEARANCE HEIGHT

	// called by RobotImpl
	@Override
	public void control(Robot robot, int barHeightsDefault[], int blockHeightsDefault[]) {
		this.robot = robot;

		// some hard coded init values you can change these for testing
		this.barHeights = new int[] { 7, 3, 1, 7, 5, 3, 2 };
		this.blockHeights = new int[] { 3, 1, 2, 3, 1, 1, 1 };

		robot.init(this.barHeights, this.blockHeights, height, width, depth);
		// -----INITIALIZE HEIGHTS-----
		initColumnHeights();

		// -----INITIALIZE ROBOT-----
		moveBlocks();

		// ADD ASSIGNMENT PART A METHOD CALL(S) HERE

	}

	private void initColumnHeights() {
		// SET CURRENT BLOCK TO TOP BLOCK ON STACK
		this.currentBlock = this.blockHeights.length - 1;

		// HEIGHT OF BLOCK COLUMN --> SET TO ARRAY
		int srcHeight = 0;
		for (int i = 0; i < this.blockHeights.length; i++) {
			srcHeight += this.blockHeights[i];
		}
		this.columnHeights[Control.MAX_WIDTH - 1] = srcHeight;

		for (int i = 0; i < this.barHeights.length; i++) {
			this.columnHeights[i + 2] = this.barHeights[i];
		}
	}

	private void findMaxColumnHeight() {
		// SET VARIABLE FOR LARGEST COLUMN AND SIZE OF CURRENTBLOCK
		int max = 0;
		this.clearance = this.blockHeights[this.currentBlock];

		// LOOP THROUGH COLUMN HEIGHTS
		for (int i = 0; i < this.columnHeights.length - 1; i++) {
			// SET TO MAX COLUMN
			if (max <= this.columnHeights[i]) {
				max = this.columnHeights[i];
			}
		}
		// ADD SIZE OF CURRENTBLOCK TO HIGHEST COLUMN
		max += this.clearance;
		// IF LAST COLUMN IS HIGHER --> ASSIGN THE MAX TO THE LAST COLUMN 
		if (max < this.columnHeights[Control.MAX_WIDTH - 1]) {
			max = this.columnHeights[Control.MAX_WIDTH - 1];
		}
		if (max > (Control.MAX_HEIGHT - 1)) {
			max = Control.MAX_HEIGHT - 1; // TO ENSURE THAT IT CANNOT EXCEED THE MAXIMUM HEIGHT
		}
		// MOVE ARM 1 UP
				if (this.height < max + 1) {
					for (int i = this.height; i < max + 1; i++) {
						robot.up();
						this.height++;
					}
				// MOVE ARM 1 DOWN
				} else if (this.height > max) {
					for (int i = this.height; i > max + 1; i--) {
						robot.down();
						this.height--;
					}
				}
	}

	
	private void extendToWidth() {
		for (int i = this.width; i < Control.SRC_COLUMN; i++) {
			robot.extend();
			this.width++;
		}
	}

	private void pickupBlock() {
		// PICK UP BLOCK FROM THE SOURCE COLUMN
		int drop = this.height - this.columnHeights[9] - 1;
		// LOWER ARM 3 TO THE DESTINATION COLUMN
		for (int i = this.depth; i < drop; i++) {
			robot.lower();
			this.depth++;
		}
		// PICK UP BLOCK
		robot.pick();
		// SUBTRACT CURRENTBLOCK HEIGHT FROM FINAL COLUMN HEIGHT
		this.columnHeights[9] -= this.clearance;
		// RAISE ARM 3 BACK TO THE TOP
		for (int i = this.depth; i > 0; i--) {
			robot.raise();
			this.depth--;
		}
	}

	private void contractToDestination() {
		// IF BLOCK SIZE = 1 --> SET TO 1
		if (blockHeights[this.currentBlock] == 1) {
			this.d = 1;
		// IF BLOCK SIZE = 2 --> SET TO 2
		} else if (blockHeights[this.currentBlock] == 2) {
			this.d = 2;
		// IF BLOCK SIZE = 3 --> SET TO 3
		} else if (blockHeights[this.currentBlock] == 3) {
			this.d = this.columnDestination;
			// INCREMENT BY 1 TO CHANGE COLUMN DESTINATION
			this.columnDestination++;
		}
		// CONTRACT ARM TO DESTINATION COLUMN
		for (int i = this.width; i > this.d; i--) {
			robot.contract();
			this.width--;
		}
	}

	private void dropBlock() {
		// DROP THE BLOCK TO THE DESTINATION COLUMN
		int col = this.columnHeights[this.d - 1];
		col += this.clearance;
		// USE ARM HEIGHT TO FIGURE HOW MUCH THE ARM NEEDS TO LOWER
		int lowerCount = this.height - col - 1;
		// LOWER ARM
		for (int i = this.depth; i < lowerCount; i++) {
			robot.lower();
			this.depth++;
		}
		// DROP BLOCK
		robot.drop();
		// RAISE ARM TO THE TOP
		for (int i = this.depth; i > 0; i--) {
			robot.raise();
			this.depth--;
		}
		// UPDATE COLUMN HEIGHT
		this.columnHeights[this.d - 1] += this.clearance;
	}
	private void moveToDefault() {
		for(int i = this.width; i > Control.MIN_WIDTH; i--) {
			robot.contract();
			this.width--;
		}
	}
	private void moveBlocks() {
		do {
			// ARM WILL FIND THE MAX COLUMN HEIGHT, THEN ARM 1 WILL MOVE UP FOR CLEARANCE HEIGHT IF NEEDED
			findMaxColumnHeight();
			// ARM 2 WILL EXTEND TO THE SOURCE COLUMN
			extendToWidth();
			// ARM 3 WILL DROP TO PICK UP THE BLOCK
			pickupBlock();
			// ARMS 2 AND 3 WILL MOVE BACK; ARM 2 WILL MOVE TO THE DESINATION OF THE BLOCK
			contractToDestination();
			// ARM 3 LOWERS AND DROPS THE BLOCK ONTO THE 
			dropBlock();
			
			this.currentBlock--; // REMOVES THE PLACED BLOCK FROM THE SOURCE COLUMN ARRAY
			
		} while(this.currentBlock >= 0); // WHEN THERE ARE NO MORE BLOCKS THE LOOP WILL STOP
			// ROBOT WILL MOVE TO THE DEFAULT POSITION
			moveToDefault();
		}
	}

