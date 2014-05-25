import java.io.*;
import java.util.*;

import javax.script.ScriptEngineManager;
import javax.script.ScriptEngine;
import javax.script.ScriptException;
import javax.swing.JTextArea;


public class Formula {

	public static String title;
	public static String formula;


	public Formula(){
		loadFile();
		addCustomForm();
		loadFile();
		System.out.println();
		removeFormula("Formulas.txt","Math");
		loadFile();
	}


	public static void addCustomForm(){
		Scanner scan = new Scanner(System.in);
		System.out.println("Enter formula title:");
		String t = scan.next();
		addText(t);
		System.out.println("Enter a formula. Put variables in brackets([]); no spaces:");
		String f = scan.next();
		addText(f);

		System.out.println("Your formula \""+t+"\" ("+f+") was created!");

	}

	public static void addText(String s){
		if (checkSameForm(s)){
			try{
				BufferedWriter output = new BufferedWriter(new FileWriter("Formulas.txt", true));
				output.append(s);
				output.newLine();
				output.close();
			}
			catch(IOException i){
				System.out.println("Error: "+i);
			}
		}
		else{
			System.out.println("Error: Cannot have same formula name or formula as another.");
			return;
		}
	}

	public static ArrayList<String> loadFile(){
		ArrayList<String> titles = new ArrayList<String>();
		try{
			BufferedReader in = new BufferedReader(new FileReader("Formulas.txt"));
			String line;
			boolean formula = false;
			StringBuilder temp = new StringBuilder();
			while((line = in.readLine()) != null){
				if (!formula) {
					temp.append(line);
					formula=true;
				} else {
					temp.append(line);
					formula=false;
					titles.add(temp.toString());
					temp.setLength(0);
				}
				
			}
			in.close();
			
		}
		catch(IOException i){
			System.out.println("Error: "+i);
		}
		return titles;
	}

	public static void findForm(String t){
		try{
			BufferedReader in = new BufferedReader(new FileReader("Formulas.txt"));
			String line;
			int c = 0;
			while((line = in.readLine()) != null && c<2){
				if (line.equals(t)){
					System.out.println(line);
					c++;
				}
				if (c==1){
					System.out.println(line);
				}
			}
			in.close();
		}
		catch(IOException i){
			System.out.println("Error: "+i);
		}
	}

	public static boolean checkSameForm(String t){
		try{
			BufferedReader in = new BufferedReader(new FileReader("Formulas.txt"));
			String line;
			while((line = in.readLine()) != null){
				if (line.equals(t))
					return false;
			}
			in.close();
		}
		catch(IOException i){
			System.out.println("Error: "+i);
		}
		return true;
	}

	public static void removeFormula(String file, String lineToRemove) {

		try {

			File inFile = new File(file);

			if (!inFile.isFile()) {
				System.out.println("Parameter is not an existing file");
				return;
			}

			//Construct the new file that will later be renamed to the original filename.
			File tempFile = new File(inFile.getAbsolutePath() + ".tmp");

			BufferedReader br = new BufferedReader(new FileReader(file));
			PrintWriter pw = new PrintWriter(new FileWriter(tempFile));

			String line = null;


			//Read from the original file and write to the new
			//unless content matches data to be removed.
			while ((line = br.readLine()) != null) {

				if (!line.trim().equals(lineToRemove)) {

					pw.println(line);
					pw.flush();


				}

			}
			pw.close();
			br.close();

			//Delete the original file
			if (!inFile.delete()) {
				System.out.println("Could not delete file");
				return;
			}

			//Rename the new file to the filename the original file had.
			if (!tempFile.renameTo(inFile))
				System.out.println("Could not rename file");

		}
		catch (FileNotFoundException ex) {
			ex.printStackTrace();
		}
		catch (IOException ex) {
			ex.printStackTrace();
		}

	}
	public static void getTitle(){

	}

	public static String getFormula(String t){
		String a = null;
		try{
			BufferedReader in = new BufferedReader(new FileReader("Formulas.txt"));
			String line;
			int c = 0;
			while((line = in.readLine()) != null && c<2){
				if (line.equals(t)){
					c++;
					a=in.readLine();
				}
				//				if (c==1){
				//					a=line;
				//					c++;
				//				}
			}
			in.close();
		}
		catch(IOException i){
			System.out.println("Error: "+i);
		}
		return a;
	}

	public static ArrayList<String> variablize(String t){
		String f = getFormula(t);
		ArrayList<String> var = new ArrayList<String>();
		for (int i = 0; i<f.length(); i++){
			String c = Character.toString(f.charAt(i));
			if (c.equals("[")){
				if (!isNumeric(Character.toString(f.charAt(i+1))))
					var.add(Character.toString(f.charAt(i+1)));
			}
		}
		return var;
	}

	public static ArrayList<String> valueToVar(String t, ArrayList<String> var, ArrayList<JTextArea> val){
		for (int i = 0; i < var.size(); i++){
				String a = val.get(i).getText();
				var.set(i, a);
				if (a.equals("null")){
					var.set(i, var.get(i));
				}
			}
		return var;
	}

	public static String setVariables(String t, ArrayList<String> var, ArrayList<JTextArea> val){
		int a = 0;
		String f = getFormula(t);
		String newForm = "";
		ArrayList<String> vars = valueToVar(t, var, val);
		for (int i = 0; i<f.length(); i++){
			String c = Character.toString(f.charAt(i));
			if (!(c.equals("[")))
				if(!(c.equals("]")))
					newForm+=c;
			if (c.equals("[")){
				if (a<vars.size()){
					if (vars.get(a).equals("null")){
						newForm+=Character.toString(f.charAt(i+1));
					}
					else{
						newForm+=vars.get(a);
					}
					a++;
					i++;
				}
			}
		}
		return newForm;
	}

	public static void main(String[] args) {
		//System.out.println(calculate("Formula2"));

	}

	public static double calculate(String t, ArrayList<String> var, ArrayList<JTextArea> val){
		ScriptEngineManager mgr = new ScriptEngineManager();
		ScriptEngine engine = mgr.getEngineByName("JavaScript");
		String form = setVariables(t, var, val);
		String[] parts = form.split("=");
		String part1 = parts[0];
		String part2 = parts[1];
		if (isNumeric(part1)){
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

	public static boolean isNumeric(String str)  
	{  
		try  
		{  
			double d = Double.parseDouble(str);  
		}  
		catch(NumberFormatException nfe)  
		{  
			return false;  
		}  
		return true;  
	}



}
