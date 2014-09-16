package trinn1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class HangmanKonsoll1 {

	List<String> words = Arrays.asList("kode", "kurs");
	
	void run() {
		String word = words.get((int) (Math.random() * words.size()));
		List<String> guessed = new ArrayList<String>();
		List<String> wrong = new ArrayList<String>();
		int tries = 7;
		Scanner scanner = new Scanner(System.in);
		while (tries > 0) {
			String out = "";
			for (int i = 0; i < word.length(); i++) {
				String letter = word.substring(i, i + 1);
				if (guessed.indexOf(letter) < 0) {
					letter = "_";
				}
				out = out + letter;
			}
			if (out.equals(word)) {
				break;
			}
			System.out.println("Gjett en bokstav i ordet: " + out);
			System.out.println(tries + " forsøk igjen");
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
		    System.out.println();
		}
		scanner.close();
		if (tries > 0) {
			System.out.println("Du gjettet " + word);
		} else {
			System.out.println("Du klarte ikke å gjette " + word);
		}
	}
	
	public static void main(String[] args) {
		HangmanKonsoll1 program = new HangmanKonsoll1();
		program.run();
	}
}
