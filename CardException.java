
public class CardException extends Exception{
	
	public CardException(){
		super("Illegal card move. Please choose another card.");
	}
	
	public CardException(String s)  {
		super(s);
	}

}
