import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.*;
import java.util.ArrayList;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.swing.*;


public class UseForm extends JFrame implements ActionListener {
	
	static Font font = new Font("Monospaced", Font.BOLD, 14);
	static Dimension quesD = new Dimension(200, 60);
	static Dimension formD = new Dimension(200, 60);
	static Dimension butD = new Dimension(200, 30);
	static Dimension varD = new Dimension(200, 30);
	static Dimension valD = new Dimension(200, 30);
	
	static JPanel start = new JPanel();
	JLabel ques = new JLabel("<html><body>Which formula would you like to use?</body></html");
	JTextArea form = new JTextArea();
	JButton go = new JButton("Done");
	
	static JPanel calc = new JPanel();	
	static ArrayList<JLabel> var = new ArrayList<JLabel>();
	static ArrayList<JTextArea> val = new ArrayList<JTextArea>();
	JButton calculate = new JButton("Calculate");
	
	static JPanel done = new JPanel();
	static JLabel ansL = new JLabel("Answer:");
	static JTextArea ansV = new JTextArea();
	
	UseForm() {
		super("UseForm");
		setSize(225, 350);
		//setResizable(false);
		setFont(font);
		setLayout(new FlowLayout(FlowLayout.CENTER));
		
		start.setLayout(new FlowLayout(FlowLayout.CENTER));
		
		ques.setPreferredSize(quesD);
		ques.setFont(font);
		start.add(ques);
		
		form.setPreferredSize(formD);
		form.setFont(font);
		start.add(form);
		
		go.setPreferredSize(butD);
		go.setFont(font);
		go.addActionListener(this);
		start.add(go);
		
		add(start);
		//add(calc);
		//add(done);
		//start.setVisible(true);
		setVisible(true);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == go) {
			calc.setLayout(new FlowLayout(FlowLayout.CENTER));
			createComps(form.getText());
			start.setVisible(false);
			calc.setVisible(true);
		}
		
		if (e.getSource() == calculate) {	
			double ans = Formula.calculate(form.getText());
			done.setLayout(new FlowLayout(FlowLayout.CENTER));
			done.add(ansL);
			done.add(ansV);
			ansV.setEditable(false);
			ansV.setText(String.valueOf(ans));
			
			calc.setVisible(false);
			done.setVisible(true);
		}
	}
	
	public static void createComps(String t) {
		ArrayList<String> vars = Formula.variablize(t);
		for (int i = 0; i < vars.size(); i++){
			var.add(new JLabel());
			var.get(i).setPreferredSize(varD);
			var.get(i).setFont(font);
			var.get(i).setText("Value for"+vars.get(i)+". If unknown, type 'null'.");
			calc.add(var.get(i));
			
			val.add(new JTextArea());
			val.get(i).setPreferredSize(valD);
			val.get(i).setFont(font);
			calc.add(val.get(i));
		}
	}
	public static ArrayList<String> valueToVar(String t){
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
	}

	public static double calculate(String t){
		ScriptEngineManager mgr = new ScriptEngineManager();
		ScriptEngine engine = mgr.getEngineByName("JavaScript");
		String form = setVariables(t);
		String[] parts = form.split("=");
		String part1 = parts[0];
		String part2 = parts[1];
		if (Formula.isNumeric(part1)){
			try {
				return (Double) engine.eval(part1);
			} catch (ScriptException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else {
			try {
				return (Double) engine.eval(part2);
			} catch (ScriptException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return 0.0;
	}
	
	public static void main(String[] args) {
		UseForm uf = new UseForm();

	}
}
