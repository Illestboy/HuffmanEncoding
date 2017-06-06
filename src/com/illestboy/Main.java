package com.illestboy;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
	    Scanner scanner = new Scanner(System.in);
        Encoder encoder = new Encoder();
	    String input;
        do {
            input = scanner.nextLine();
            for (char c : input.toCharArray()) {
                encoder.addChar(c);
            }
        } while (!input.equals(""));
        encoder.encodeChars();

        System.out.println(encoder.getChars());
    }
}
