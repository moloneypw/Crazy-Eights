import javax.swing.JFrame;
import javax.swing.JPanel;
/**
 * 
 * The main method for Crazy Eights
 * Sets up the frame in which the game is played
 * 
 * @author Pat Moloney
 */
public class LauncherGUI{

	static JFrame window = new JFrame();

	/**
	 * creates a frame and set the content to the StartGUI frame
	 * @param args
	 */
	public static void main(String[] args) {
		
		StartGUI content = new StartGUI();
		window.setContentPane(content);
		window.pack();
		window.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		window.setVisible(true);
		
	}
	
	/**
	 * used to change the frame when a new game is declared
	 * @param content
	 */
	public void setPanel(JPanel content){
		window.setContentPane(content);
		window.validate();
		window.repaint();
	}

}
