import java.awt.*;
import java.awt.event.*;
import java.util.Scanner;

import javax.swing.*;


public class CreateForm extends JFrame implements ActionListener {
	Font font = new Font("Monospaced", Font.BOLD, 16);
	Dimension labelDim1 = new Dimension(200, 30);
	Dimension labelDim2 = new Dimension(200, 50);
	Dimension textDim = new Dimension(200, 95);
	Dimension butDim = new Dimension(200, 30);
	
	JLabel titleL = new JLabel("<html><body>Enter formula title<br>no spaces:</body></html>");
	JTextArea titleT = new JTextArea();
	JLabel formL = new JLabel("<html><body>Enter a formula<br>Put variables in brackets([])<br>no spaces:</body></html>");
	JTextArea formT = new JTextArea();
	JButton go = new JButton("Done");
	
	CreateForm() {
		super("CreateForm");
		setSize(225, 350);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setFont(font);
		
		setLayout(new FlowLayout(FlowLayout.CENTER));
		titleL.setPreferredSize(labelDim1);
		titleT.setPreferredSize(textDim);
		formL.setPreferredSize(labelDim2);
		formT.setPreferredSize(textDim);
		go.setPreferredSize(butDim);
		
		add(titleL);
		add(titleT);
		add(formL);
		add(formT);
		add(go);
		
		setVisible(true);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == go) {
			String t = titleT.getText();
			Formula.addText(t);
			String f = formT.getText();
			Formula.addText(f);
			
			JOptionPane.showMessageDialog(null, "Your formula \""+t+"\" ("+f+") was created!");
		}
		
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
}
