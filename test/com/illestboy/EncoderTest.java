package com.illestboy;

import com.illestboy.Encoder;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EncoderTest {

    private String getEncodedString(String inputString) {
        Encoder encoder = new Encoder();
        encoder.encode(inputString);
        return encoder.getEncodedText();
    }

    private Map<Character, String> getCodeTable(String allAllowedChars) {
        Encoder encoder = new Encoder();
        encoder.encode(allAllowedChars);
        return encoder.getCodeTable();
    }

    @Test
    void whenEmptyString() {
        String encodedString = getEncodedString("");
        assertEquals("", encodedString);
    }

    @Test
    void whenOneCharString() {
        String encodedString = getEncodedString("A");
        assertEquals(1, encodedString.length());
    }

    @Test
    void whenTwoCharString() {
        String encodedString = getEncodedString("AB");
        assertEquals(2, encodedString.length());
    }

    @Test
    void whenThreeSameCharString() {
        String encodedString = getEncodedString("AAA");
        assertEquals(3, encodedString.length());
    }

    @Test
    void whenThreeDifferentCharString() {
        String encodedString = getEncodedString("ABC");
        assertEquals(5, encodedString.length());
    }

    @Test
    void example() {
        String encodedString = getEncodedString("ABCAD");
        assertEquals(10, encodedString.length());
    }

    @Test
    void onlyOneAndZero() {
        String encodedString = getEncodedString("ABCADabcadasklgjsdklnvsdwrjoiznlxvn");
        assertEquals(true, encodedString.matches("^[01]*$"));
    }

    @Test
    void onlyAllowedChars() {
        String encodedString = getEncodedString("!\n\r~");
        assertEquals(0, encodedString.length());
    }

    @Test
    void allAllowedEncode() {
        String allAllowedChars = "АБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯ" +
                "абвгдеёжзийклмнопрстуфхцчшщъыьэюя" +
                "ABCDEFGHIJKLMNOPQRSTUVWXYZ" +
                "abcdefghijklmnopqrstuvwxyz" +
                "0123456789" +
                ":,. ";
        Map<Character, String> codeTable = getCodeTable(allAllowedChars);
        assertEquals(codeTable.size(), allAllowedChars.length());
    }

    @Test
    void isPrefixCode() {
        String allAllowedChars = "АБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯ";
        Map<Character, String> codeTable = getCodeTable(allAllowedChars);
        boolean havePrefix = codeTable.entrySet().stream()
                .anyMatch(entry -> codeTable.values().stream()
                        .anyMatch(code -> !code.equals(entry.getValue()) && code.startsWith(entry.getValue())));
        assertEquals(false, havePrefix);
    }
}