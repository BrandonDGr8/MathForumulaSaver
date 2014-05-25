import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class Calculator extends JFrame implements ActionListener {
	
	JPanel[] row = new JPanel[7];
	JButton[] button = new JButton[19];
	String[] buttonString = {	"AC", "()", "\u221A", "/",
								"7", "8", "9", "*",
								"4", "5", "6", "-",
								"1", "2", "3", "+",
								".", "0", "="};
	Dimension displayDimension = new Dimension(100, 35);
	Dimension buttonDimension = new Dimension(50, 40);
	Dimension zeroButDimension = new Dimension(101, 40);
	ArrayList<Integer> function = new ArrayList<Integer>();
	ArrayList<Double> temporary = new ArrayList<Double>();
	ArrayList<Integer> pFunction = new ArrayList<Integer>();
	ArrayList<Double> pTemporary = new ArrayList<Double>();
	JTextArea display1 = new JTextArea(1,10);
	JTextArea display2 = new JTextArea(1,10);
	Font font1 = new Font("Monospaced", Font.BOLD, 16);
	Font font2 = new Font("Calibri", Font.BOLD, 26);
	boolean reset = true;
	boolean parOpen = false;
	StringBuilder num = new StringBuilder();
	
	Calculator() {
		super("Calculator");
		setDesign();
		setSize(225, 350);
		setResizable(false);
		GridLayout grid = new GridLayout(7,1);
		setLayout(grid);
		
		FlowLayout f1 = new FlowLayout(FlowLayout.CENTER);
		FlowLayout f2 = new FlowLayout(FlowLayout.CENTER,1,1);
		for(int i = 0; i < 7; i++)
			row[i] = new JPanel();
		row[0].setLayout(f1);
		row[1].setLayout(f1);
		for(int i = 2; i < 7; i++)
			row[i].setLayout(f2);
		for(int i = 0; i < 19; i++) {
			button[i] = new JButton();
			button[i].setText(buttonString[i]);
			button[i].setFont(font1);
			button[i].addActionListener(this);
		}
        display1.setFont(font2);
        display1.setEditable(false);
        display1.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        display1.setPreferredSize(displayDimension);
        
        display2.setFont(font2);
        display2.setEditable(false);
        display2.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        display2.setPreferredSize(displayDimension);
        
        for(int i=0; i < 18; i++) {
        	button[i].setPreferredSize(buttonDimension);
        }
        button[18].setPreferredSize(zeroButDimension);
        
        row[0].add(display1);
        add(row[0]);
        row[1].add(display2);
        add(row[1]);
        
        for(int i = 0; i < 4; i++) {
        	row[2].add(button[i]);
        }
        add(row[2]);
        
        for(int i = 4; i < 8; i++) {
        	row[3].add(button[i]);
        }
        add(row[3]);
        
        for(int i = 8; i < 12; i++) {
        	row[4].add(button[i]);
        }
        add(row[4]);
        
        for(int i = 12; i < 16; i++) {
        	row[5].add(button[i]);
        }
        add(row[5]);
        
        for(int i = 16; i < 19; i++) {
        	row[6].add(button[i]);
        }
        add(row[6]);
        
        setVisible(true);
	}

	public void clear() {
		try {
			display1.setText("");
			display2.setText("");
			function.clear();
			temporary.clear();
			parOpen = false;
			reset = false;
		} catch (NullPointerException npe) {
		}
	}
	
	public void error() {
		display2.setText("ERROR");
		pTemporary.clear();
		pFunction.clear();
		temporary.clear();
		function.clear();
		reset = true;
	}
	
	public void getPar() {
		try {
			for(int i = 0; i < pFunction.size(); i++) {
				if(pFunction.get(i)==4) {
					pTemporary.set(i,  Math.sqrt(pTemporary.get(i)));
					for (int x = i+1; x < pFunction.size()-1; x++) {
						pFunction.set(x, pFunction.get(x+1));
					}
					pFunction.remove(pFunction.size()-1);
					i--;
				}
			}
			for(int i = 0; i < pFunction.size(); i++) {
				if(pFunction.get(i) == 2) {
					pTemporary.set(i, pTemporary.get(i) * pTemporary.get(i+1));
					for (int x = i+1; x < pFunction.size()-1; x++) {
						pFunction.set(x, pFunction.get(x+1));
						pTemporary.set(x+1, pTemporary.get(x+2));
					}
					pFunction.remove(pFunction.size()-1);
					pTemporary.remove(pTemporary.size()-1);
					i--;
				} else if (pFunction.get(i) == 3) {
					pTemporary.set(i, pTemporary.get(i) / pTemporary.get(i+1));
					for (int x = i+1; x < pFunction.size()-1; x++) {
						pFunction.set(x, pFunction.get(x+1));
						pTemporary.set(x+1, pTemporary.get(x+2));
					}
					pFunction.remove(pFunction.size()-1);
					pTemporary.remove(pTemporary.size()-1);
					i--;
				}
			}
			for(int i = 0; i < pFunction.size(); i++) {
				if(pFunction.get(i) == 0) {
					pTemporary.set(i, pTemporary.get(i) + pTemporary.get(i+1));
					for (int x = i+1; x < pFunction.size()-1; x++) {
						pFunction.set(x, pFunction.get(x+1));
						pTemporary.set(x+1, pTemporary.get(x+2));
					}
					i--;
					pFunction.remove(pFunction.size()-1);
					pTemporary.remove(pTemporary.size()-1);
				} else if (pFunction.get(i) == 1) {
					pTemporary.set(i, pTemporary.get(i) - pTemporary.get(i+1));
					for (int x = i+1; x < pFunction.size()-1; x++) {
						pFunction.set(x, pFunction.get(x+1));
						pTemporary.set(x+1, pTemporary.get(x+2));
					}
					i--;
					pFunction.remove(pFunction.size()-1);
					pTemporary.remove(pTemporary.size()-1);
				}
				
			}
			temporary.add(pTemporary.get(0));
			pFunction.clear();
			pTemporary.clear();
		} catch(NumberFormatException e) {
		}
	}
	
	public void getResult() {
			try {
			for(int i = 0; i < function.size(); i++) {
				if(function.get(i) == 4) {
					temporary.set(i, Math.sqrt(temporary.get(i)));
					for (int x = i+1; x < pFunction.size()-1; x++) {
						function.set(x, function.get(x+1));
					}
					function.remove(function.size()-1);
					i--;
				}
			}
			for(int i = 0; i < function.size(); i++) {
				if(function.get(i) == 2) {
					temporary.set(i, temporary.get(i) * temporary.get(i+1));
					for (int x = i+1; x < pFunction.size()-1; x++) {
						function.set(x, function.get(x+1));
						temporary.set(x+1, temporary.get(x+2));
					}
					function.remove(function.size()-1);
					temporary.remove(temporary.size()-1);
					i--;
				} else if (function.get(i) == 3) {
					temporary.set(i, temporary.get(i) / temporary.get(i+1));
					for (int x = i+1; x < pFunction.size()-1; x++) {
						function.set(x, function.get(x+1));
						temporary.set(x+1, temporary.get(x+2));
					}
					function.remove(function.size()-1);
					temporary.remove(temporary.size()-1);
					i--;
				}
			}
			for(int i = 0; i < function.size(); i++) {
				if(function.get(i) == 0) {
					temporary.set(i, temporary.get(i) + temporary.get(i+1));
					for (int x = i+1; x < pFunction.size()-1; x++) {
						function.set(x, function.get(x+1));
						temporary.set(x+1, temporary.get(x+2));
					}
					function.remove(function.size()-1);
					temporary.remove(temporary.size()-1);
					i--;
				} else if (function.get(i) == 1) {
					temporary.set(i, temporary.get(i) - temporary.get(i+1));
					for (int x = i+1; x < pFunction.size()-1; x++) {
						function.set(x, function.get(x+1));
						temporary.set(x+1, temporary.get(x+2));
					}
					function.remove(function.size()-1);
					temporary.remove(temporary.size()-1);
					i--;
				}
			}
		} catch(NumberFormatException e) {
		}
		DecimalFormat df = new DecimalFormat("###.######");
		df.format(temporary.get(0));
		display2.setText(df.format(temporary.get(0)).toString());
		num.setLength(0);
		function.clear();
		temporary.clear();
		reset = true;
	}
	
	public final void setDesign() {
		try {
			UIManager.setLookAndFeel(
					"com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch(Exception e) {  
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {

		if(e.getSource() == button[0])
			clear();
		if(e.getSource() == button[1]) {
			if (reset) {
				display1.setText("");
				display2.setText("");
				reset=false;
			} 
			if(!parOpen) {
				display1.append("\u0029");
				parOpen=true;
			} else {
				display1.append("\u0028");
				if (!num.toString().equals("")) {
					pTemporary.add(Double.parseDouble(num.toString()));
				} else {
					error();
				}
				num.setLength(0);
				parOpen=false;
				getPar();
			}
		}
		if(e.getSource() == button[2]) {
			if (reset) {
				display1.setText("");
				display2.setText("");
				reset=false;
			} 
			display1.append("\u221A");
			if(parOpen) {
				pFunction.add(4);
			} else {
				function.add(4);
			}
		}	
		if(e.getSource() == button[3]) {
			if (reset) {
				display1.setText("");
				display1.append(display2.getText());
				if (!num.toString().equals("")) {
					temporary.add(Double.parseDouble(num.toString()));
					function.add(3);
				} else {
					error();
				}
				display2.setText("");
				reset=false;
			} else {
				if (parOpen) {
					if (!num.toString().equals("")) {
						pTemporary.add(Double.parseDouble(num.toString()));
						pFunction.add(3);
					} else {
						error();
					}
				} else {
					if (!num.toString().equals("")) {
						temporary.add(Double.parseDouble(num.toString()));
						function.add(3);
					} else {
						error();
					}
				}
			}
			num.setLength(0);
			display1.append("/");
		}
		if(e.getSource() == button[4]) {
			if (reset){
				clear();
				reset = false;
			}
			display1.append("7");
			num.append("7");
		}
		if(e.getSource() == button[5]) {
			if (reset){
				clear();
				reset = false;
			}
			display1.append("8");
			num.append("8");
		}
		if(e.getSource() == button[6]) {
			if (reset){
				clear();
				reset = false;
			}
			display1.append("9");
			num.append("9");
		}
		if(e.getSource() == button[7]) {
			if (reset) {
				display1.setText("");
				display1.append(display2.getText());
				if (!num.toString().equals("")) {
					temporary.add(Double.parseDouble(display2.getText()));
					function.add(2);
				} else {
					error();
				}
				display2.setText("");
				reset=false;
			} else {
				if (parOpen){
					if (!num.toString().equals("")) {
						pTemporary.add(Double.parseDouble(num.toString()));
						pFunction.add(2);
					} else {
						error();
					}
				} else {
					if (!num.toString().equals("")) {
						if (!num.toString().equals("")) {
							temporary.add(Double.parseDouble(num.toString()));
							function.add(2);
						} else {
							error();
						}
					}
				}
			}
			num.setLength(0);
			display1.append("*");
		}
		if(e.getSource() == button[8]) {
			if (reset){
				clear();
				reset = false;
			}
			display1.append("4");
			num.append("4");
		}
		if(e.getSource() == button[9]) {
			if (reset){
				clear();
				reset = false;
			}
			display1.append("5");
			num.append("5");
		}
		if(e.getSource() == button[10]) {
			if (reset){
				clear();
				reset = false;
			}
			display1.append("6");
			num.append("6");
		}
		if(e.getSource() == button[11]) {
			if (reset) {
				display1.setText("");
				display1.append(display2.getText());
				
				if (!num.toString().equals("")) {
					temporary.add(Double.parseDouble(display2.getText()));
					function.add(1);
				} else {
					error();
				}
				display2.setText("");
				reset = false;
			} else {
				if (parOpen){
					if (!num.toString().equals("")) {
						pTemporary.add(Double.parseDouble(num.toString()));
						pFunction.add(1);
					} else {
						error();
					}
				} else {
					if (!num.toString().equals("")) {
						temporary.add(Double.parseDouble(num.toString()));
						function.add(1);
					} else {
						error();
					}
				}
			}
			num.setLength(0);
			display1.append("-");
		}
		if(e.getSource() == button[12]) {
			if (reset){
				clear();
				reset = false;
			}
			display1.append("1");
			num.append("1");
		}
		if(e.getSource() == button[13]) {
			if (reset){
				clear();
				reset = false;
			}
			display1.append("2");
			num.append("2");
		}
		if(e.getSource() == button[14]) {
			if (reset){
				clear();
				reset = false;
			}
			display1.append("3");
			num.append("3");
		}
		if(e.getSource() == button[15]) {
			if (reset) {
				display1.setText("");
				display1.append(display2.getText());
				
				if (!num.toString().equals("")) {
					temporary.add(Double.parseDouble(display2.getText()));
					function.add(0);
				} else {
					error();
				}
				display2.setText("");
				reset = false;
			} else {
				if (parOpen){
					if (!num.toString().equals("")) {
						pTemporary.add(Double.parseDouble(num.toString()));
						pFunction.add(0);
					} else {
						error();
					}
				} else {
					if (!num.toString().equals("")) {
						temporary.add(Double.parseDouble(num.toString()));
						function.add(0);
					} else {
						error();
					}
				}
			}
			num.setLength(0);
			display1.append("+");
		}
		if(e.getSource() == button[16]) {
			if (reset){
				clear();
				reset = false;
			}
			display1.append(".");
			num.append(".");
		}
		if(e.getSource() == button[17]) {
			if (reset){
				clear();
				reset = false;
			}
			display1.append("0");
			num.append("0");
		}
		if(e.getSource() == button[18]) {
			if (!num.toString().equals("")) {
				temporary.add(Double.parseDouble(num.toString()));
			}
			getResult();
		}
	}

	public static void main(String[] args) {
		Calculator c = new Calculator();
		
/*		double a = 0;
		double b = 0;
		double ans = 0;
		String op;
		int counter = args.length;

		if (counter < 3 || counter % 2 != 1) {
			System.out.print("Error...counter less than three");
		} else {

			op = args[1];
			try {
				a = Double.parseDouble(args[0]);
			} catch (NumberFormatException nfe) {
				System.out.print("First argument must be an integer");
			}
			try {
				b = Double.parseDouble(args[2]);
			} catch (NumberFormatException nfe) {
				System.out.print("Third argument must be an integer");
			}

			if (op.equals("+")) {
				ans = a + b;
			} else if (op.equals("-")) {
				ans = a - b;
			} else if (op.equals("*")) {
				ans = a * b;
			} else if (op.equals("/")) {
				ans = a / b;
			} else if (op.equals("^")) {
				ans = Math.pow(a, b);
			} else {
				System.out.print("Error: invalid operation");
				System.exit(0);
			}

			System.out.print(a + op + b + "=" + ans);
		} */
	}

}
