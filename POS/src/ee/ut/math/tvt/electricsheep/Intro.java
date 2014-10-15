package ee.ut.math.tvt.electricsheep;

import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.WindowConstants;

public class Intro {
// oleme lambakesed
	public static void main(String[] args) throws FileNotFoundException, IOException {
		final IntroUI ui = new IntroUI();
		ui.setVisible(true);
		ui.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	}
}
