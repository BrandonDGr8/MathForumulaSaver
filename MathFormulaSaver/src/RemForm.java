import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;


public class RemForm extends JFrame implements ActionListener  {
	Font font = new Font("Monospaced", Font.BOLD, 14);
	Dimension lDim = new Dimension(200, 50);
	Dimension tDim = new Dimension (200, 50);
	Dimension butDim = new Dimension(200, 30);
	
	JLabel label = new JLabel("<html><body>What is the name of the formula you would like to remove?</body></html>");
	JTextArea title = new JTextArea();
	JButton go = new JButton("Done");
	
	RemForm() {
		super("RemForm");
		setSize(225, 350);
		setResizable(false);
		setFont(font);
		
		setLayout(new FlowLayout(FlowLayout.CENTER));
		
		label.setPreferredSize(lDim);
		label.setFont(font);
		add(label);
		title.setPreferredSize(tDim);
		title.setFont(font);
		add(title);
		go.addActionListener(this);
		go.setPreferredSize(butDim);
		go.setFont(font);
		add(go);
		
		setVisible(true);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == go) {
			String r = title.getText();
			Object[] option = {"Yes, remove this formula", "No, I want to keep this formula"};
			int sure = JOptionPane.showOptionDialog(null, "Are you sure you want to remove your formula \""+r+"\"?", "Are you sure?", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, option, option[1]);
			
			if (sure == 0) {
				Formula.removeFormula("Formulas.txt", Formula.getFormula(r));
				Formula.removeFormula("Formulas.txt", r);
				
				JOptionPane.showMessageDialog(null, "Your formula \""+r+"\" was removed!");
			}
		}
		
	}

}
