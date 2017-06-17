package com.illestboy;

import java.util.*;

public class Encoder {

    private static Set<Character> validCharacters;
    private final Map<String, Character> codeCharTable;
    private String text;
    private Map<Character, Integer> charsFrequency;
    private Map<Character, String> charCodeTable;
    private PriorityQueue<Node> queue;

    public Encoder() {
        charsFrequency = new HashMap<>();
        validCharacters = new HashSet<>();
        queue = new PriorityQueue<>();
        charCodeTable = new HashMap<>();
        codeCharTable = new HashMap<>();
        text = "";
        initializeAllowedCharacters();
    }

    private void initializeAllowedCharacters() {
        for (char c = 'a'; c <= 'z'; c++)
            validCharacters.add(c);
        for (char c = 'A'; c <= 'Z'; c++)
            validCharacters.add(c);
        for (char c = 'А'; c <= 'я'; c++)
            validCharacters.add(c);
        for (char c = '0'; c <= '9'; c++)
            validCharacters.add(c);
        validCharacters.add(',');
        validCharacters.add('.');
        validCharacters.add(' ');
        validCharacters.add(':');
        validCharacters.add('Ё');
        validCharacters.add('ё');
    }

    public void setText(String s) {
        this.text = s;
        for (char c : s.toCharArray()) {
            if (validCharacters.contains(c)) {
                charsFrequency.put(c, charsFrequency.getOrDefault(c, 0) + 1);
            }
        }
        this.charsFrequency.forEach((key, value) -> this.queue.add(new Node(key, value)));
        if (queue.isEmpty()) return;
        while (queue.size() > 1) {
            this.queue.add(new Node(queue.poll(), queue.poll()));
        }
        buildTables(queue.poll(), "");
    }

    public String getEncodedText() {
        StringBuilder result = new StringBuilder();
        for (char c : text.toCharArray()) {
            if (validCharacters.contains(c)) {
                result.append(this.charCodeTable.get(c));
            }
        }
        return result.toString();
    }

    public String decode(String encodedString) {
        StringBuilder resultTextBuilder = new StringBuilder();
        char[] chars = encodedString.toCharArray();
        String code = "";
        for (char aChar : chars) {
            code += aChar;
            if (this.codeCharTable.containsKey(code)) {
                resultTextBuilder.append(this.codeCharTable.get(code));
                code = "";
            }
        }
        return resultTextBuilder.toString();
    }

    private void buildTables(Node node, String code) {
        if (node.getCharacter() != null) {
            charCodeTable.put(node.getCharacter(), code.length() > 0 ? code : "0");
            codeCharTable.put(code.length() > 0 ? code : "0", node.getCharacter());
            return;
        }
        if (node.getLeftNode() != null) {
            buildTables(node.getLeftNode(), code + "0");
        }
        if (node.getRightNode() != null) {
            buildTables(node.getRightNode(), code + "1");
        }
    }

    public Map<Character, String> getCharCodeTable() {
        return Collections.unmodifiableMap(charCodeTable);
    }

    public Map<String, Character> getCodeCharTable() {
        return Collections.unmodifiableMap(codeCharTable);
    }
}