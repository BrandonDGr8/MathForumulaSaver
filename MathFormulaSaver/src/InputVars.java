import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;


public class InputVars extends JFrame implements ActionListener {

	static Font font = new Font("Monospaced", Font.BOLD, 14);
	static Dimension quesD = new Dimension(200, 40);
	static Dimension valD = new Dimension(200, 20);
	static Dimension butD = new Dimension(200, 20);
	
	static String title;
	static String variable;
	static ArrayList<String> var = new ArrayList<String>();
	static ArrayList<JLabel> ques = new ArrayList<JLabel>();
	static ArrayList<JTextArea> val = new ArrayList<JTextArea>();
	JButton calculate = new JButton("Calculate");
	
	InputVars(String t) {
		setSize(225, 350);
		setResizable(false);
		setFont(font);
		setLayout(new FlowLayout(FlowLayout.CENTER));
		
		var = Formula.variablize(t);
		for (int i = 0; i < var.size(); i++){
			ques.add(new JLabel());
			ques.get(i).setPreferredSize(quesD);
			ques.get(i).setFont(font);
			ques.get(i).setText("<html><body>Value for "+var.get(i)+". If unknown, type 'null'.</body></html>");
			add(ques.get(i));
			
			val.add(new JTextArea());
			val.get(i).setPreferredSize(valD);
			val.get(i).setFont(font);
			add(val.get(i));
		}
		calculate.setPreferredSize(butD);
		calculate.setFont(font);
		calculate.addActionListener(this);
		add(calculate);
		
		setVisible(true);
		title = t;
	}
	
/*	public static ArrayList<String> valueToVar(String t){
		ArrayList<String> vars = Formula.variablize(t);
		for (int i = 0; i < vars.size(); i++){
			String a = val.get(i).toString();
			vars.set(i, a);
			if (a.equals("null")){
				vars.set(i, vars.get(i));
			}
		}
		return vars;
	}

	public static String setVariables(String t){
		int a = 0;
		String f = Formula.getFormula(t);
		String newForm = "";
		ArrayList<String> var = valueToVar(t);
		for (int i = 0; i<f.length(); i++){
			String c = Character.toString(f.charAt(i));
			if (!(c.equals("[")))
				if(!(c.equals("]")))
					newForm+=c;
			if (c.equals("[")){
				if (a<var.size()){
					if (var.get(a).equals("null")){
						newForm+=Character.toString(f.charAt(i+1));
					}
					else{
						newForm+=var.get(a);
					}
					a++;
					i++;
				}
			}
		}
		return newForm;
	}*/
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==calculate) {
			double ans = Formula.calculate(title, var, val);
			JOptionPane.showMessageDialog(null, "The answer is "+ans+".");
		}
		
	}
	
	public static void main(String[] args) {
		InputVars iv = new InputVars("Force");
	}
	

}
