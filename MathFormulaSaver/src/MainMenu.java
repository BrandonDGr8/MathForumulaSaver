import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;


public class MainMenu extends JFrame implements ActionListener {
	
	Font font = new Font("Monospaced", Font.BOLD, 16);
	JButton[] buttons = new JButton[4];
	
	MainMenu() {
		setSize(225, 350);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		setLayout(new GridLayout(4,1));
		
		buttons[0] = new JButton();
		buttons[0].setText("Calculator");
		buttons[0].setFont(font);
		buttons[0].addActionListener(this);
		add(buttons[0]);
		
		buttons[1] = new JButton();
		buttons[1].setText("Use a Formula");
		buttons[1].setFont(font);
		buttons[1].addActionListener(this);
		add(buttons[1]);
		
		buttons[2] = new JButton();
		buttons[2].setText("Create a Formula");
		buttons[2].setFont(font);
		buttons[2].addActionListener(this);
		add(buttons[2]);
		
		buttons[3] = new JButton();
		buttons[3].setText("Remove a Formula");
		buttons[3].setFont(font);
		buttons[3].addActionListener(this);
		add(buttons[3]);
		
		setVisible(true);
	}
	public static void main(String[] args) {
		MainMenu m = new MainMenu();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == buttons[0]) {
			System.out.print("Calculator");
		}
		if (e.getSource() == buttons[1]) {
			System.out.print("Use");
		}
		if (e.getSource() == buttons[2]) {
			System.out.print("Create");
		}
		if (e.getSource() == buttons[3]) {
			System.out.print("Remove");
		}
		
	}

}
