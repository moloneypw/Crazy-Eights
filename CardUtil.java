import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JOptionPane;


public class CardUtil {

	int players;
	ArrayList<String> p1Cards = new ArrayList<String>();
	ArrayList<String> p2Cards = new ArrayList<String>();
	ArrayList<String> p3Cards = new ArrayList<String>();
	ArrayList<String> p4Cards = new ArrayList<String>();
	ArrayList<String> underPile = new ArrayList<String>();
	String pileTop = "";
	ArrayList<String> deck = new ArrayList<String>();
	
	public CardUtil(int numPlayers){
		setPlayers(numPlayers);
		loadCardDeck("deck.txt");
		setP1Cards(deck);
		setP2Cards(deck);
		if(numPlayers==3)
			setP3Cards(deck);
		else{
			setP3Cards(deck);
			setP4Cards(deck);	
		}
		setPileTop(deck.get(0));
		
	}

	
	/**
	 * load a deck of cards from a text file
	 * if the file is not there an error is reported
	 * an algorithm is used to sort the cards
	 * @param filename
	 */
	public void loadCardDeck (String filename){
		try {
			Scanner in = new Scanner(new FileInputStream(filename));
			int swap;

			while(in.hasNext()){
				deck.add(in.nextLine());
			}
			for(int i=0; i<deck.size(); i++){
				swap = i +((int)(Math.random()*deck.size()-i));
				String temp = deck.get(i);
				deck.set(i, deck.get(swap));
				deck.set(swap, temp);
			}

		} catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(null, "Error loading deck from file.");
		}

	}

	public ArrayList<String> getP1Cards() {
		return p1Cards;
	}





	public void setP1Cards(ArrayList<String> deck) {
		if(getPlayers()==2){
			for(int i=0; i<7; i++){
				p1Cards.add(deck.get(i));
				deck.remove(i);
			}
		}
		else{
			for(int i=0; i<5; i++){
				p1Cards.add(deck.get(i));
				deck.remove(i);
			}
		}
	}





	public ArrayList<String> getP2Cards() {
		return p2Cards;
	}





	public void setP2Cards(ArrayList<String> deck) {
		if(getPlayers()==2){
			for(int i=0; i<7; i++){
				p2Cards.add(deck.get(i));
				deck.remove(i);
			}
		}
		else{
			for(int i=0; i<5; i++){
				p2Cards.add(deck.get(i));
				deck.remove(i);
			}
		}	
	}
	
	public void drawCard(int playerNum){
		if(deck.size()==0){
			for(String s: underPile){
				deck.add(s);
			}
		}
		if(playerNum==1)
			p1Cards.add(deck.get(0));
		else if(playerNum==2)
			p2Cards.add(deck.get(0));
		else if(playerNum==3)
			p3Cards.add(deck.get(0));
		else if(playerNum==4)
			p4Cards.add(deck.get(0));
		deck.remove(0);
	}





	public ArrayList<String> getP3Cards() {
		return p3Cards;
	}





	public void setP3Cards(ArrayList<String> deck) {
		if(getPlayers()==2){
			for(int i=0; i<7; i++){
				p3Cards.add(deck.get(i));
				deck.remove(i);
			}
		}
		else{
			for(int i=0; i<5; i++){
				p3Cards.add(deck.get(i));
				deck.remove(i);
			}
		}
	}





	public String getPileTop() {
		return pileTop;
	}





	public void setPileTop(String topCard) {
		if(pileTop.equals("")){
			pileTop = deck.get(0);
			deck.remove(0);
		}
		else{
			pileTop=topCard;
			underPile.add(topCard);
		}
	}





	public int getPlayers() {
		return players;
	}





	public void setPlayers(int nPlayers) {
			players = nPlayers;
	}





	public ArrayList<String> getP4Cards() {
		return p4Cards;
	}





	public void setP4Cards(ArrayList<String> deck) {
		if(getPlayers()==2){
			for(int i=0; i<7; i++){
				p4Cards.add(deck.get(i));
				deck.remove(i);
			}
		}
		else{
			for(int i=0; i<5; i++){
				p4Cards.add(deck.get(i));
				deck.remove(i);
			}
		}
	}
	
	public void playCard(String card) throws CardException{
		if(getCardNumber(card).equals("8")){
			setPileTop(eightCard());
		}
		else if(getCardNumber(card).equals(getCardNumber(getPileTop()))){
			if(getCardNumber(card).equals("8")){
				setPileTop(eightCard());
			}
			else
				setPileTop(card);
		}
		else if(getCardSuit(card).equals(getCardSuit(getPileTop()))){
			setPileTop(card);
		} 
		else
			throw new CardException();
	}
	
	public String getCardNumber(String card){
		int i=0;
		String temp="",cardNum="";
		
		while (card.charAt(i)!=' ') {
			temp+=card.charAt(i);
			i++;
		}
		
		cardNum = temp;
		return cardNum;
	}
	
	public String getCardSuit(String card){
		String suit ="";
		
		if(card.contains("hearts"))
			suit = "hearts";
		if(card.contains("clubs"))
			suit = "clubs";
		if(card.contains("spades"))
			suit = "spades";
		if(card.contains("diamonds"))
			suit = "diamonds";
		return suit;
	}
	
	public String eightCard(){
		String s = "";
		Object[] possibilities = {"hearts", "diamonds", "spades", "clubs"};
		s = (String)JOptionPane.showInputDialog(
		                    null,
		                    "Please select a suit.",
		                    "Eight's Suit",
		                    JOptionPane.PLAIN_MESSAGE,
		                    null,
		                    possibilities,
		                    "hearts");
		s = "8 "+s;
		if(!s.equals("8 null"))
				return s;
		else
			return eightCard();
	}
	
	public String scoring(){
		int score =0;
		String card,message="";
		
		for(int j=1; j<players+1; j++){
			if (j ==1 ){
				score=0;
				for(int i=0; i<p1Cards.size(); i++){
					card = getCardNumber(p1Cards.get(i));
					if(card.equals("8"))
						score = score -50;
					else if(card.equals("j"))
						score = score - 10;
					else if(card.equals("q"))
						score = score - 10;
					else if(card.equals("k"))
						score = score - 10;
					else if(card.equals("a"))
						score = score - 1;
					else
						score = score - Integer.parseInt(card);
				}
				if(p1Cards.size()==0)
					score =0;
				message = message + "\nPlayer " + j + ": " + score;
			}
			if (j ==2 ){
				score=0;
				for(int i=0; i<p2Cards.size(); i++){
					card = getCardNumber(p2Cards.get(i));
					if(card.equals("8"))
						score = score -50;
					else if(card.equals("j"))
						score = score - 10;
					else if(card.equals("q"))
						score = score - 10;
					else if(card.equals("k"))
						score = score - 10;
					else if(card.equals("a"))
						score = score - 1;
					else
						score = score - Integer.parseInt(card);
				}
				if(p2Cards.size()==0)
					score =0;
				message = message + "\nPlayer " + j + ": " + score;
			}
			if (j ==3 ){
				for(int i=0; i<p3Cards.size(); i++){
					score=0;
					card = getCardNumber(p3Cards.get(i));
					if(card.equals("8"))
						score = score -50;
					else if(card.equals("j"))
						score = score - 10;
					else if(card.equals("q"))
						score = score - 10;
					else if(card.equals("k"))
						score = score - 10;
					else if(card.equals("a"))
						score = score - 1;
					else
						score = score - Integer.parseInt(card);
				}
				if(p3Cards.size()==0)
						score=0;
				message = message + "\nPlayer " + j + ": " + score;
			}
			if (j ==4 ){
				score=0;
				for(int i=0; i<p4Cards.size(); i++){
					card = getCardNumber(p4Cards.get(i));
					if(card.equals("8"))
						score = score -50;
					else if(card.equals("j"))
						score = score - 10;
					else if(card.equals("q"))
						score = score - 10;
					else if(card.equals("k"))
						score = score - 10;
					else if(card.equals("a"))
						score = score - 1;
					else
						score = score - Integer.parseInt(card);
				}
				if(p4Cards.size()==0)
					score = 0;
				message = message + "\nPlayer " + j + ": " + score;
			}
		}
		return message;
	}
	
	/*
	 * rather long method
	 * AI is used to play the computers cards
	 * Takes in a parameter of the player that is using the AI.
	 * Uses if statments to find which player's cards to used.
	 * If no card can be played a card is drawn
	 */
	public void AIMove(int playerNum) throws CardException, GameOverException{	
		boolean cardPlayed = false, hasEight = false;
		int eightNum=0;
		
		if(playerNum==2){
			for(int i=0; i<p2Cards.size(); i++){
				if(getCardNumber(p2Cards.get(i)).equals("8")){
					 hasEight = true;
					 eightNum=i;
				}
				else if(getCardNumber(p2Cards.get(i)).equals(getCardNumber(getPileTop()))){
					playCard(p2Cards.get(i));
					p2Cards.remove(i);
					cardPlayed=true;
					if(p2Cards.size()==0)
						throw new GameOverException("Player 2");
					break;
				}
				else if(getCardSuit(p2Cards.get(i)).equals(getCardSuit(getPileTop()))){
					playCard(p2Cards.get(i));
					p2Cards.remove(i);
					cardPlayed = true;
					if(p2Cards.size()==0)
						throw new GameOverException("Player 2");
					break;
				}		
			}
			
			if(hasEight&&!cardPlayed){
				p2Cards.remove(eightNum);
				if(p2Cards.size()==0)
					throw new GameOverException("Player 2");
				String top = "8 " + getCardSuit(p2Cards.get(0));
				setPileTop(top);
			}
			else if(!cardPlayed){
				drawCard(2);
			}
		}
		
		
		if(playerNum==3){
			for(int i=0; i<p3Cards.size(); i++){
				if(getCardNumber(p3Cards.get(i)).equals("8")){
					 hasEight = true;
					 eightNum=i;
				}
				else if(getCardNumber(p3Cards.get(i)).equals(getCardNumber(getPileTop()))){
					playCard(p3Cards.get(i));
					p3Cards.remove(i);
					cardPlayed=true;
					if(p3Cards.size()==0)
						throw new GameOverException("Player 3");
					break;
				}
				else if(getCardSuit(p3Cards.get(i)).equals(getCardSuit(getPileTop()))){
					playCard(p3Cards.get(i));
					p3Cards.remove(i);
					cardPlayed = true;
					if(p3Cards.size()==0)
						throw new GameOverException("Player 3");
					break;
				}		
			}
			
			if(hasEight&&!cardPlayed){
				p3Cards.remove(eightNum);
				
				if(p3Cards.size()==0)
					throw new GameOverException("Player 3");
				String top = "8 " + getCardSuit(p3Cards.get(0));
				setPileTop(top);
			}
			else if(!cardPlayed){
				drawCard(3);
			}
		}
		
		
		if(playerNum==4){
			for(int i=0; i<p4Cards.size(); i++){
				if(getCardNumber(p4Cards.get(i)).equals("8")){
					 hasEight = true;
					 eightNum=i;
				}
				else if(getCardNumber(p4Cards.get(i)).equals(getCardNumber(getPileTop()))){
					playCard(p4Cards.get(i));
					p4Cards.remove(i);
					cardPlayed=true;
					if(p4Cards.size()==0)
						throw new GameOverException("Player 4");
					break;
				}
				else if(getCardSuit(p4Cards.get(i)).equals(getCardSuit(getPileTop()))){
					playCard(p4Cards.get(i));
					p4Cards.remove(i);
					cardPlayed = true;
					if(p4Cards.size()==0)
						throw new GameOverException("Player 4");
					break;
				}		
			}
			
			if(hasEight&&!cardPlayed){
				p4Cards.remove(eightNum);
				
				if(p4Cards.size()==0)
					throw new GameOverException("Player 4");
				String top = "8 " + getCardSuit(p4Cards.get(0));
				setPileTop(top);
			}
			else if(!cardPlayed){
				drawCard(4);
			}
		}
	}
	
	public void saveGame(){
		try {
			
			String fName = JOptionPane.showInputDialog(null, "Please enter the name of the file you want to save.");
			ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(fName));
			out.write(players);
			out.writeObject(p1Cards);
			out.writeObject(p2Cards);
			if(players==3)
				out.writeObject(p3Cards);
			else if(players==4){
				out.writeObject(p3Cards);
				out.writeObject(p4Cards);
			}
			out.writeObject(deck);
			out.writeUTF(pileTop);
			out.close();

		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Error with saving file.");
		}
	}

	public void displayWinnerMessage(String message) {
		JOptionPane.showMessageDialog(null,message);
	}

}
