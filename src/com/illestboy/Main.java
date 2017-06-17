package com.illestboy;

import java.io.*;

public class Main {
    public static void main(String[] args) {
        String text = readInput(args);
        Encoder encoder = new Encoder();
        encoder.setText(text);
        System.out.println(encoder.getCharCodeTable());
        System.out.println(encoder.getEncodedText());
    }

    private static String readInput(String[] args) {
        InputStream inputStream = args.length > 0 ? getFileInputStream(args[0]) : System.in;
        try (BufferedReader buffer = new BufferedReader(new InputStreamReader(inputStream))) {
            String input = buffer.readLine();
            StringBuilder builder = new StringBuilder();
            while (input != null && !input.equals("")) {
                builder.append(input);
                input = buffer.readLine();
            }
            return builder.toString();
        } catch (IOException e) {
            System.out.println("Неудалось прочитать текст");
            return "";
        }
    }

    private static InputStream getFileInputStream(String filePath) {
        InputStream inputStream;
        File inputFile = new File(filePath);
        try {
            inputStream = new FileInputStream(inputFile.toString());
        } catch (FileNotFoundException e) {
            System.out.println("Файл " + inputFile + " не найден");
            inputStream = System.in;
        }
        return inputStream;
    }
}