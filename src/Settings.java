import java.awt.Dimension;


public class Settings {
	public static final double groundLevel = 0.8;
	public static final boolean musicPlaying = false;
	public static final boolean musicPaused = false;
	public static final boolean titleMusic = false;
	
	public static Dimension dim = new Dimension(1280, 720);
	public static int height() {
		return dim.height;
	}

	public static int width() {
		return dim.width;
	}
}