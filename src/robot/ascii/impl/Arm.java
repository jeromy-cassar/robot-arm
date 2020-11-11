package robot.ascii.impl;

import com.googlecode.lanterna.terminal.swing.SwingTerminalFrame;
import robot.RobotMovement;

public class Arm implements RobotMovement, Drawable {
	private int height;
	private int YPos;
	private int width;
	private int XPos;
	private int depth;
	private int YDepth;

	public Arm(int height, int width, int depth) {
		this.height = height;
		this.width = width;
		this.depth = depth;
	}

	@Override
	public void draw(SwingTerminalFrame terminalFrame) {
		//---set colours---
		terminalFrame.setForegroundColor(Drawable.ARM_COLOR_FG);
		terminalFrame.setBackgroundColor(Drawable.ARM_COLOR_BG);
		
		//---draw bar height---
		YPos = terminalFrame.getTerminalSize().getRows() - 1;
		for (int barSize = 0; barSize < height; barSize++) {
			drawBar(terminalFrame, 0, YPos);
			YPos -= Drawable.V_SCALE_FACTOR;
		}
		
		//---draw bar width---
		XPos = Drawable.H_SCALE_FACTOR;
		for (int barSize = 0; barSize < width; barSize++) {
			drawBar(terminalFrame, XPos, terminalFrame.getTerminalSize().getRows() - 1 - (height - 1) * Drawable.V_SCALE_FACTOR);
			XPos += Drawable.H_SCALE_FACTOR;
		}
		
		//---draw bar depth---
		YDepth = terminalFrame.getTerminalSize().getRows() - 1 - (height - 2) * Drawable.V_SCALE_FACTOR;
		for (int barSize = 0; barSize < depth; barSize++) {
			drawBar(terminalFrame, width * Drawable.H_SCALE_FACTOR, YDepth);
			YDepth += Drawable.V_SCALE_FACTOR;
		}
	}
		
	private void drawBar(SwingTerminalFrame terminalFrame, int x, int y) {
		for (int rowScale = 0; rowScale < Drawable.V_SCALE_FACTOR; rowScale++) {
			for (int colScale = 0; colScale < Drawable.H_SCALE_FACTOR; colScale++) {
				terminalFrame.setCursorPosition(x + colScale, y - rowScale);
				terminalFrame.putCharacter(Drawable.BAR_CHAR);
			}
		}
	}

	@Override
	public void pick() {

	}

	@Override
	public void drop() {

	}

	@Override
	public void up() {
		height++;
	}

	@Override
	public void down() {
		height--;
	}

	@Override
	public void contract() {
		width--;
	}

	@Override
	public void extend() {
		width++;
	}

	@Override
	public void lower() {
		depth++;
	}

	@Override
	public void raise() {
		depth--;
	}
}
