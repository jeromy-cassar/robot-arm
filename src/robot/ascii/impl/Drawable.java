package robot.ascii.impl;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.terminal.swing.SwingTerminalFrame;

public interface Drawable
{
	// set robot speed to 0 initially? (for testing)
	public static final boolean START_PAUSED=false;

	// thickness of item segments in robot co-ordinates (unscaled)
	public static final int ARM1_WIDTH=1;
	public static final int ARM2_HEIGHT=1;
	public static final int ARM3_WIDTH=1;
	public static final int BAR_WIDTH = 1;
	public static final int BLOCK_WIDTH = 1;
	// display chars (can be set individually in AbstractItem subclasses) constructors
	public static final char OVERFLOW_DIGIT='+';
	public static final char ARM_CHAR=' ';
	public static final char BAR_CHAR=' ';
	public static final char BLOCK_CHAR=' ';

	public static final TextColor.ANSI[] BLOCK_COLORS_FG = new TextColor.ANSI[]
	{ TextColor.ANSI.YELLOW, TextColor.ANSI.RED, TextColor.ANSI.BLUE };
	public static final TextColor.ANSI[] BLOCK_COLORS_BG = new TextColor.ANSI[]
	{ TextColor.ANSI.YELLOW, TextColor.ANSI.RED, TextColor.ANSI.BLUE };
	public static final TextColor ARM_COLOR_FG = TextColor.ANSI.WHITE;
	public static final TextColor ARM_COLOR_BG = TextColor.ANSI.WHITE;
	public static final TextColor BAR_COLOR_FG = TextColor.ANSI.GREEN;
	public static final TextColor BAR_COLOR_BG = TextColor.ANSI.GREEN;

	public static final int H_SCALE_FACTOR=3;
	public static final int V_SCALE_FACTOR=2;

	public abstract void draw(SwingTerminalFrame terminalFrame);
}