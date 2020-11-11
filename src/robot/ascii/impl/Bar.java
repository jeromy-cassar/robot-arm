package robot.ascii.impl;

import com.googlecode.lanterna.terminal.swing.SwingTerminalFrame;

public class Bar implements Drawable {
	private int height;
	private int XPos;
	private int YPos;

	public Bar(int height, int XPos) {
		this.height = height;
		this.XPos = XPos;
	}

	@Override
	public void draw(SwingTerminalFrame terminalFrame) {
		YPos = terminalFrame.getTerminalSize().getRows() - 1;
		//---set colours---
		terminalFrame.setForegroundColor(Drawable.BAR_COLOR_FG);
		terminalFrame.setBackgroundColor(Drawable.BAR_COLOR_BG);

		for (int barSize = 0; barSize < height; barSize++) {
			// apply vertical and horizontal scaling by looping to draw multiple chars
			for (int rowScale = 0; rowScale < Drawable.V_SCALE_FACTOR; rowScale++) {
				for (int colScale = 0; colScale < Drawable.H_SCALE_FACTOR; colScale++) {
					terminalFrame.setCursorPosition(XPos * Drawable.H_SCALE_FACTOR + colScale, YPos - rowScale);
					terminalFrame.putCharacter(Drawable.BAR_CHAR);
				}
			}
			YPos -= Drawable.V_SCALE_FACTOR;
		}
	}
}