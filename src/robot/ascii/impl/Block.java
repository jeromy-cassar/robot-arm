package robot.ascii.impl;

import com.googlecode.lanterna.terminal.swing.SwingTerminalFrame;

public class Block implements Drawable {
	private int height;
	private int XPos;
	private int YPos;
	private Boolean move = false;

	public Block(int height, int XPos, int YPos) {
		this.height = height;
		this.XPos = XPos;
		this.YPos = YPos;
	}

	@Override
	public void draw(SwingTerminalFrame terminalFrame) {
		int moveToY = terminalFrame.getTerminalSize().getRows() - 1 - YPos * Drawable.V_SCALE_FACTOR;
		
		//---set colours---
		terminalFrame.setForegroundColor(Drawable.BLOCK_COLORS_FG[height - 1]);
		terminalFrame.setBackgroundColor(Drawable.BLOCK_COLORS_BG[height - 1]);

		for (int barSize = 0; barSize < height; barSize++) {
			// apply vertical and horizontal scaling by looping to draw multiple chars
			for (int rowScale = 0; rowScale < Drawable.V_SCALE_FACTOR; rowScale++) {
				for (int colScale = 0; colScale < Drawable.H_SCALE_FACTOR; colScale++) {
					terminalFrame.setCursorPosition(XPos * Drawable.H_SCALE_FACTOR + colScale, moveToY - rowScale);
					terminalFrame.putCharacter(Drawable.BAR_CHAR);
				}
			}
			moveToY -= Drawable.V_SCALE_FACTOR;
		}
	}

	public void startMove() {
		this.move = true;
	}

	public void stopMove() {
		this.move = false;
	}

	public void up() {
		if (move)
			YPos++;
	}

	public void down() {
		if (move)
			YPos--;
	}

	public void contract() {
		if (move)
			XPos--;
	}

	public void extend() {
		if (move)
			XPos++;
	}

	public void lower() {
		if (move)
			YPos--;
	}

	public void raise() {
		if (move)
			YPos++;
	}
}