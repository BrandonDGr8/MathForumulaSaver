import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.*;
import java.util.ArrayList;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.swing.*;


public class SelForm extends JFrame implements ActionListener {
	
	static Font font = new Font("Monospaced", Font.BOLD, 14);
	static Dimension quesD = new Dimension(200, 60);
	static Dimension formD = new Dimension(200, 60);
	static Dimension butD = new Dimension(200, 30);
	
	static JPanel start = new JPanel();
	JLabel ques = new JLabel("<html><body>Which formula would you like to use?</body></html");
	//JTextArea form = new JTextArea();
	JComboBox choose = new JComboBox(Formula.loadFile().toArray());
	JButton go = new JButton("Done");
	
	SelForm() {
		super("SelForm");
		setSize(225, 350);
		setResizable(false);
		setFont(font);
		
		start.setLayout(new FlowLayout(FlowLayout.CENTER));
		
		ques.setPreferredSize(quesD);
		ques.setFont(font);
		start.add(ques);
		
		/*form.setPreferredSize(formD);
		form.setFont(font);
		start.add(form);*/
		
		choose.setPreferredSize(formD);
		choose.setFont(font);
		start.add(choose);
		
		go.setPreferredSize(butD);
		go.setFont(font);
		go.addActionListener(this);
		start.add(go);
		
		add(start);
		start.setVisible(true);
		setVisible(true);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == go) {
			//InputVars iv = new InputVars(form.getText());
			String sel = choose.getSelectedItem().toString();
			String[] titleAndForm = sel.split("\\[");
			String selection = titleAndForm[0];
			InputVars iv = new InputVars(selection);
		}
	}
	
	public static void main(String[] args) {
		SelForm uf = new SelForm();

	}
}
