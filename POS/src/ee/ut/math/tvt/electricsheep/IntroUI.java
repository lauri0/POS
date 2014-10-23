package ee.ut.math.tvt.electricsheep;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.apache.log4j.Logger;

public class IntroUI extends JFrame {
	
	private static final Logger log = Logger.getLogger(IntroUI.class);
	
	private String introString;
	private String versionString;
	private String rawImageLocation;
	private String imageLocation;
	
	public IntroUI() throws FileNotFoundException, IOException {
	    try {
	    	BufferedReader br = new BufferedReader(new FileReader("application.properties"));
	        StringBuilder sb = new StringBuilder();
	        StringBuilder imageLocationBuilder = new StringBuilder();
	        sb.append("<html>");
	        String line = br.readLine();
	        int linenum1 = 1;
	        
	        while (line != null) {
	        	if (linenum1 == 4) {
	        		imageLocationBuilder.append(br.readLine());
	        		rawImageLocation = imageLocationBuilder.toString();
	        		String[] parts = rawImageLocation.split(":");
	        		imageLocation = parts[1];
	        	}
	        	else {
	        		sb.append(line);
		            sb.append("<br>");
		            line = br.readLine();
	        	}
		        linenum1 += 1;
	        }
	        sb.append("<br>");
	        introString = sb.toString();
	        br.close();
//	        System.out.println(everything);
	    }
	    
	    catch (Exception e) {
	    	log.info("Encountered an error while trying to read from application.properties file.");
	    }
	    
	    try {
	    	BufferedReader br2 = new BufferedReader(new FileReader("version.properties"));
	        StringBuilder sb2 = new StringBuilder();
	        String line2 = br2.readLine();
	        int linenum2 = 1;

	        while (line2 != null) {
	        	if (linenum2 == 5) {
	        		sb2.append(line2);
	        	}
	        	line2 = br2.readLine();
	        	linenum2 += 1;
	        }
	        sb2.append("</html>");
	        versionString = sb2.toString();
	        br2.close();
	    }
	    
	    catch (Exception f) {
	    	log.info("Encountered an error while trying to read from version.properties file.");
	    }
	    
		setTitle("Intro");
		setSize(800, 800);
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(3, 1));
		
		JLabel title = new JLabel();
		title.setText("Electric Sheep");
		title.setFont(new Font("Serif", Font.BOLD, 38));
		title.setHorizontalAlignment(JLabel.CENTER);
		
		JLabel introLabel = new JLabel();
		introLabel.setFont(new Font("Serif", Font.BOLD, 26));
		introLabel.setText(introString + versionString);
		introLabel.setHorizontalAlignment(JLabel.CENTER);
		
		ImageIcon image = new ImageIcon(imageLocation);
		JLabel imageLabel = new JLabel();
		imageLabel.setIcon(image);
		imageLabel.setHorizontalAlignment(JLabel.CENTER);
		
		panel.add(title);
		panel.add(imageLabel);
		panel.add(introLabel);
		panel.setBackground(Color.WHITE);
		
		this.getContentPane().add(panel);
		
		log.info("Intro window created.");
		
	}
}
