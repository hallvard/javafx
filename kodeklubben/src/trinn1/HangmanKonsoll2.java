package trinn1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class HangmanKonsoll2 {

	List<String> words;
	
	String word;

	List<String> guessed, wrong;
	
	int tries;

	void init() {
		words = Arrays.asList("kode", "kurs");
		word = words.get((int) (Math.random() * words.size()));
		
		guessed = new ArrayList<String>();
		wrong = new ArrayList<String>();
		
		tries = 7;
	}
	
	void run() {
		Scanner scanner = new Scanner(System.in);
		while (tries > 0) {
			ask();
			checkInput(scanner);
			System.out.println();
		}
		scanner.close();
		finish();
	}

	boolean ask() {
		String out = "";
		for (int i = 0; i < word.length(); i++) {
			String letter = word.substring(i, i + 1);
			if (guessed.indexOf(letter) < 0) {
				letter = "_";
			}
			out = out + letter;
		}
		System.out.println("Gjett en bokstav i ordet: " + out);
		System.out.println(tries + " forsøk igjen");
		return out.equals(word);
	}
	
	void checkInput(Scanner scanner) {
		String guess = scanner.next();
		if (guess.length() != 1) {
			System.out.println("Du må gjette én bokstav om gangen!");
		} else if (guessed.contains(guess) || wrong.contains(guess)) {
			System.out.println("Bokstaven er allerede gjettet på:" + guess);
		} else if (word.indexOf(guess) >= 0) {
			System.out.println("Yay");
			guessed.add(guess);
		} else {
			System.out.println("Nope");
			tries = tries - 1;
			wrong.add(guess);
		}
	}
	
	void finish() {
		if (tries > 0) {
			System.out.println("Du gjettet " + word);
		} else {
			System.out.println("Du klarte ikke å gjette " + word);
		}
	}

	public static void main(String[] args) {
		HangmanKonsoll2 program = new HangmanKonsoll2();
		program.init();
		program.run();
	}
}
