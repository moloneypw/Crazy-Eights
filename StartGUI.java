import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class StartGUI extends JPanel implements ActionListener{

	private JButton card1 = new JButton("Start Game");
	private JLabel numPlayers = new JLabel("Enter Number of Players: ");
	private JTextField players = new JTextField(10);
	private int num=0;

	/**
	 * Creates the startup up frame 
	 * that lets the uses enter the amount of players between 2-4
	 */
	public StartGUI() {
		JPanel mainPanel = new JPanel();
		JPanel top = new JPanel();
		JPanel bottom = new JPanel();

		mainPanel.setLayout(new BorderLayout());

		top.setLayout(new BorderLayout());
		top.setPreferredSize(new Dimension(1000, 700));
		top.setBackground(Color.green);

		bottom.setPreferredSize(new Dimension(6, 50));
		bottom.setLayout(new FlowLayout());
		bottom.add(numPlayers);
		bottom.add(players);
		bottom.add(card1);
		card1.addActionListener(this);

		mainPanel.add(top, BorderLayout.NORTH);
		mainPanel.add(bottom, BorderLayout.SOUTH);

		this.add(mainPanel);
	}



	public void actionPerformed(ActionEvent e){
		try{
			num = Integer.parseInt(players.getText());


			if(e.getSource().equals(card1)){
				if(num>=2&&num<=4){
					GameGUI newGUI = new GameGUI(num);
					removeAll();
					add(newGUI);
					validate();
					repaint();
				}
				else
					JOptionPane.showMessageDialog(null,"Error, wrong number of players.");
			}
		}
		catch(Exception E){
			JOptionPane.showMessageDialog(null,"Error, wrong number of players.");
		}

	}

}

