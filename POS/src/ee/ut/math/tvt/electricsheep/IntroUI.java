package ee.ut.math.tvt.electricsheep;

import java.awt.Font;
import java.awt.GridLayout;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class IntroUI extends JFrame {
	
	private String everything;
	
	public IntroUI() throws FileNotFoundException, IOException {
	    try(BufferedReader br = new BufferedReader(new FileReader("application.properties"))) {
	        StringBuilder sb = new StringBuilder();
	        sb.append("<html>");
	        String line = br.readLine();

	        while (line != null) {
	            sb.append(line);
	            sb.append("<br>");
	            line = br.readLine();
	        }
	        sb.append("</html>");
	        everything = sb.toString();
	        System.out.println(everything);
	    }
		setTitle("Intro");
		setSize(800, 600);
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(2, 1));
		
		JLabel title = new JLabel();
		title.setText("Electric Sheep");
		title.setFont(new Font("Serif", Font.BOLD, 38));
		title.setHorizontalAlignment(JLabel.CENTER);
		
		JLabel introLabel = new JLabel();
		introLabel.setFont(new Font("Serif", Font.BOLD, 26));
		introLabel.setText(everything);
		introLabel.setHorizontalAlignment(JLabel.CENTER);
		panel.add(title);
		panel.add(introLabel);
		panel.setAlignmentX(title.CENTER_ALIGNMENT);
		panel.setAlignmentX(introLabel.CENTER_ALIGNMENT);
		this.getContentPane().add(panel);
	}
}
