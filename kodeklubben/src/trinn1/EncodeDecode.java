package trinn1;

public class EncodeDecode {

	String alphabet = "abcdefghijklmnopqrstuvwxyz";

	char encode(char letter, int secret) {
	    int pos = alphabet.indexOf(letter);
	    int newpos = (pos + secret);
	    if (newpos >= 26) {
	    	newpos = newpos - 26;
	    }
	    return alphabet.charAt(newpos);
	}
	
	char decode(char letter, int secret) {
	    int pos = alphabet.indexOf(letter);
	    int newpos = (pos - secret);
	    if (newpos < 0) {
	        newpos = newpos + 26;
	    }
	    return alphabet.charAt(newpos);
	}

	void run() {
	    String message = "hello world";
	    int secret = 17;
	    String output = "";
	    for (int i = 0; i < message.length(); i++) {
	    	char character = message.charAt(i);
	        if (alphabet.indexOf(character) >= 0)
	            output = output + encode(character, secret);
	        else
	            output = output + character;
	        System.out.println(output);
	    }

	    message = "yvccf nficu";
	    output = "";
	    for (int i = 0; i < message.length(); i++) {
	    	char character = message.charAt(i);
	        if (alphabet.indexOf(character) >= 0)
	            output = output + decode(character, secret);
	        else
	            output = output + character;
	        System.out.println(output);
	    }
	}
	
	public static void main(String[] args) {
		new EncodeDecode().run();
	}
}
