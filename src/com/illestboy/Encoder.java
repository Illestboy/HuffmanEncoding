package com.illestboy;

import java.util.*;

public class Encoder {

    private static Set<Character> validCharacters;
    private String text;
    private Map<Character, Integer> charsFrequency;
    private Map<Character, String> codeTable;
    private PriorityQueue<Node> queue;

    public Encoder() {
        charsFrequency = new HashMap<>();
        validCharacters = new HashSet<>();
        queue = new PriorityQueue<>();
        codeTable = new HashMap<>();
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

    public void encode(String s) {
        setText(s);
        this.charsFrequency.forEach((key, value) -> this.queue.add(new Node(key, value)));
        if (queue.isEmpty()) return;
        while (queue.size() > 1) {
            this.queue.add(new Node(queue.poll(), queue.poll()));
        }
        buildTable(queue.poll(), "");
    }

    public String getEncodedText() {
        StringBuilder result = new StringBuilder();
        for (char c : text.toCharArray()) {
            if (validCharacters.contains(c)) {
                result.append(this.codeTable.get(c));
            }
        }
        return result.toString();
    }

    private void buildTable(Node node, String code) {
        if (node.getCharacter() != null) {
            codeTable.put(node.getCharacter(), code.length() > 0 ? code : "0");
            return;
        }
        if (node.getLeftNode() != null) {
            buildTable(node.getLeftNode(), code + "0");
        }
        if (node.getRightNode() != null) {
            buildTable(node.getRightNode(), code + "1");
        }
    }

    private void setText(String s) {
        this.text = s;
        for (char c : s.toCharArray()) {
            if (validCharacters.contains(c)) {
                charsFrequency.put(c, charsFrequency.getOrDefault(c, 0) + 1);
            }
        }
    }

    public Map<Character, String> getCodeTable() {
        return Collections.unmodifiableMap(codeTable);
    }
}