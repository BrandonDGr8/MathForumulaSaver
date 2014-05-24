import java.io.*;
import java.util.*;

public class Runner {

	public static boolean run;

	static Scanner read = new Scanner(System.in);

	public static void main(String[] args) {
		run();

	}

	public static void run(){

		run=true;
		while(run){
			askAction();

		}

	}

	public static void askAction(){
		System.out.println("What do you want to do?\nUseCalculator, UseFormula, CreateFormula, RemoveFormula, or end? (Case Sensitive)");
		String answer = read.next();
		if (answer.equals("UseCalculator")){

		}
		else if (answer.equals("UseFormula")){
			System.out.println("What is the name of the formula you would like to use?");
			String t = read.next();
			Formula.findForm(t);
		}
		else if (answer.equals("CreateFormula")){
			Formula.addCustomForm();
		}
		else if (answer.equals("RemoveFormula")){
			System.out.println("What is the name of the formula you would like to remove?");
			String r = read.next();
			Formula.removeFormula("Formulas.txt", Formula.getFormula(r));
			Formula.removeFormula("Formulas.txt", r);
		}
		else if (answer.equals("end")){
			run=false;
		}
		
		else{
			System.out.println("Error: Command not recognized. Check spelling and capitalization.");
		}

	}
}
