import java.io.*;
import java.util.*;


public class Formula {

	public static String title;
	public static String formula;




	public Formula(String t, String f){
		loadFile();
		addCustomForm();
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

	}

	public static void addText(String s){
		if (checkSameForm(s)){
			try{
				Writer output;
				output = new BufferedWriter(new FileWriter("Formulas.txt", true));
				output.append("\n"+s);
				output.close();
			}
			catch(IOException i){
				System.out.println("Error: "+i);
			}
		}
		else
			System.out.println("Error: Cannot have same formula name or formula as another.");
	}

	public static void loadFile(){
		try{
			BufferedReader in = new BufferedReader(new FileReader("Formulas.txt"));
			String line;
			while((line = in.readLine()) != null){
				System.out.println(line);
			}
			in.close();
		}
		catch(IOException i){
			System.out.println("Error: "+i);
		}
	}

	public static void findForm(){
		try{
			BufferedReader in = new BufferedReader(new FileReader("Formulas.txt"));
			String line;
			int c = 0;
			while((line = in.readLine()) != null && c<2){
				if (line.equals(title)){
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

}
