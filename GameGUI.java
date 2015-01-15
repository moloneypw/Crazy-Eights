import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;


public class GameGUI extends JPanel implements ActionListener{
	
	LauncherGUI lg2;
	JButton drawCard = new JButton("Draw Card");
	JButton saveGame = new JButton("Save Game");
	JButton loadGame = new JButton("Load Game");
	
	JPanel mainPanel = new JPanel();
	JPanel top = new JPanel();
	JPanel bottom = new JPanel();
	JPanel cards = new JPanel();
	JPanel drawCards = new JPanel();
	
	JLabel pile = new JLabel("");
	JLabel p2 = new JLabel("");
	JLabel p3 = new JLabel("");
	JLabel p4 = new JLabel("");
	ImageIcon ic;
	private CardUtil cu;
	
	public GameGUI(int numPlayers){
		
		cu = new CardUtil(numPlayers);
		
		p2.setText("Player 2: " + cu.p2Cards.size() + " cards");
		
		ic = new ImageIcon("cards\\"+cu.getPileTop()+".gif");
		pile.setIcon(ic);
		
		//layout and size set
		mainPanel.setLayout(new BorderLayout());
		mainPanel.setPreferredSize(new Dimension(1000,700));
		
		
		//layout, size, and labels are added in appropriate positions
		top.setPreferredSize(new Dimension(1000,300));
		top.setLayout(new BorderLayout());
		p2.setPreferredSize(new Dimension(333, 100));
		p2.setHorizontalAlignment(JLabel.CENTER);
		p3.setPreferredSize(new Dimension(333, 100));
		p4.setPreferredSize(new Dimension(333, 100));
		p4.setHorizontalAlignment(JLabel.RIGHT);
		pile.setPreferredSize(new Dimension(333, 100));
		pile.setHorizontalAlignment(JLabel.CENTER);		
		top.add(p2, BorderLayout.NORTH);
		top.add(p3, BorderLayout.WEST);
		top.add(p4, BorderLayout.EAST);
		if(numPlayers==3)
			p3.setText("Player 3: " + cu.p3Cards.size() + " cards");
		if(numPlayers==4){
			p3.setText("Player 3: " + cu.p3Cards.size() + " cards");
			p4.setText("Player 4: " + cu.p4Cards.size() + " cards");
		}
		top.add(pile, BorderLayout.CENTER);
		
		bottom.setPreferredSize(new Dimension(1000,400));
		bottom.setLayout(new BorderLayout());
		
		//add card buttons
		if(numPlayers==2)
			for(int i=0; i<7; i++){
				addButton(cu.p1Cards.get(i));
			}
		else{
			for(int i=0; i<5; i++){
				addButton(cu.p1Cards.get(i));
			}
		}
		
		cards.setPreferredSize(new Dimension(1000,400));
		drawCards.setPreferredSize(new Dimension(100,300));
		drawCards.add(drawCard);
		drawCards.add(saveGame);
		drawCards.add(loadGame);
		drawCard.addActionListener(this);
		saveGame.addActionListener(this);
		loadGame.addActionListener(this);
		drawCard.setHorizontalAlignment(JButton.CENTER);
		
		bottom.add(cards, BorderLayout.CENTER);
		bottom.add(drawCards, BorderLayout.WEST);
		
		
		top.setBackground(Color.green);
		bottom.setBackground(Color.gray);
		mainPanel.add(top, BorderLayout.NORTH);
		mainPanel.add(bottom, BorderLayout.SOUTH);
		add(mainPanel);
	}
	
	public void addButton(String temp){
		ImageIcon ic = new ImageIcon("cards\\"+temp + ".gif");
		JButton butt = new JButton();
		butt.setIcon(ic);
		butt.setActionCommand(temp);
		butt.addActionListener(this);
		cards.add(butt);
	}

	public void removeButton(Object temp){
		for(int i = 0; i<cu.p1Cards.size(); i++){
			if(cards.getComponent(i).equals(temp)){
				cards.remove(i);
				cu.p1Cards.remove(i);
				break;
			}
		}
		validate();
		repaint();
	}

	public void actionPerformed(ActionEvent e){
		String card = e.getActionCommand();

		try {
			if(e.getSource().equals(saveGame)){
				cu.saveGame();
			}
			else if(e.getSource().equals(loadGame)){
				loadGame();
			}

			else{
				if(e.getSource().equals(drawCard)){
					cu.drawCard(1);
					this.addButton(cu.p1Cards.get(cu.p1Cards.size()-1));
					for(int i=2; i<cu.getPlayers()+1; i++){
						cu.AIMove(i);
						if(i==2)
							p2.setText("Player 2: "+cu.p2Cards.size()+" cards.");
						else if(i==3)
							p3.setText("Player 3: "+cu.p3Cards.size()+" cards.");
						else if(i==4)
							p4.setText("Player 4: "+cu.p4Cards.size()+" cards.");
					}
					ic = new ImageIcon("cards\\"+cu.getPileTop()+".gif");
					pile.setIcon(ic);
				}
				else{
					cu.playCard(card);
					ic = new ImageIcon("cards\\"+cu.getPileTop()+".gif");
					pile.setIcon(ic);
					removeButton(e.getSource());
					if(cu.p1Cards.size()==0)
						throw new GameOverException("You");
					for(int i=2; i<=cu.getPlayers(); i++){
						cu.AIMove(i);
						ic = new ImageIcon("cards\\"+cu.getPileTop()+".gif");
						pile.setIcon(ic);
						if(i==2)
							p2.setText("Player 2: "+cu.p2Cards.size()+" cards.");
						if(i==3)
							p3.setText("Player 3: "+cu.p3Cards.size()+" cards.");
						if(i==4)
							p4.setText("Player 4: "+cu.p4Cards.size()+" cards.");
					}
				}
			}
		} catch (CardException e1) {	
			JOptionPane.showMessageDialog(null, e1.getMessage());
		} catch(GameOverException e2){
			int answer = JOptionPane.showConfirmDialog(null, e2.getMessage()+" won!" +
					"\nWould you like to play another game?"
					+"\n\nScoring"
					+cu.scoring(),
					"Game Over" ,
					JOptionPane.YES_NO_OPTION);
			if(answer==0){
				GameGUI newGG = new GameGUI(cu.getPlayers());
				LauncherGUI lg = new LauncherGUI();
				lg.setPanel(newGG);
			}
			else if(answer==1)
				System.exit(0);
		}
	}
	
	
	@SuppressWarnings("unchecked")
	public void loadGame(){
		try {
			String fName = JOptionPane.showInputDialog(null, "Please enter what game you want to load.");
			ObjectInputStream in = new ObjectInputStream(new FileInputStream(fName));
			
			cards.removeAll();
			validate();
			repaint();
			
			cu.setPlayers(in.read());
			cu.p1Cards = (ArrayList<String>)in.readObject();
			cu.p2Cards = (ArrayList<String>)in.readObject();
			if(cu.getPlayers()==3){
				cu.p3Cards = (ArrayList<String>)in.readObject();
				p3.setText("Player 3: "+cu.p3Cards.size()+" cards.");
			}
			else if(cu.getPlayers()==4){
				cu.p3Cards = (ArrayList<String>)in.readObject();
				p3.setText("Player 3: "+cu.p3Cards.size()+" cards.");
				cu.p4Cards = (ArrayList<String>)in.readObject();
				p4.setText("Player 4: "+cu.p4Cards.size()+" cards.");
			}
			else{
				p3.setText("");
				p4.setText("");
			}
			
			cu.deck=(ArrayList<String>)in.readObject();
			cu.setPileTop(in.readUTF());
			in.close();
			
			ic = new ImageIcon("cards\\"+cu.getPileTop()+".gif");
			pile.setIcon(ic);
			p2.setText("Player 2: "+cu.p2Cards.size()+" cards.");

			for(int i=0; i<cu.p1Cards.size(); i++){
				addButton(cu.p1Cards.get(i));
			}
			
		} catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(null, "The file was not found.");
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Error with the input of file.");
		} catch (ClassNotFoundException e) {
			JOptionPane.showMessageDialog(null, "The file was not found.");
		}
	}
}
