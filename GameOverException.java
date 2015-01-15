import javax.swing.JOptionPane;


public class GameOverException extends Exception{

	public GameOverException(){
		super("Winner");
	}
	
	public GameOverException(String s)  {
		super(s);
	}
}
