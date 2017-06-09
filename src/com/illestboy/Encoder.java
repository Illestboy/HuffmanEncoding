package com.illestboy;

import java.util.*;

public class Encoder {

    private static class Node implements Comparable<Node> {

        private Integer frequency;

        private Character character;

        private Node leftNode;

        private Node rightNode;

        Node(Character character, int frequency) {
            this.frequency = frequency;
            this.character = character;
        }

        Node(Node leftNode, Node rightNode) {
            this.character = null;
            this.leftNode = leftNode;
            this.rightNode = rightNode;
            this.frequency = leftNode.frequency + rightNode.frequency;
        }

        Character getCharacter() {
            return character;
        }

        Node getLeftNode() {
            return leftNode;
        }

        Node getRightNode() {
            return rightNode;
        }

        @Override
        public int compareTo(Node o) {
            return this.frequency.compareTo(o.frequency);
        }

        @Override
        public String toString() {
            return String.valueOf(this.frequency);
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        StringBuilder text = new StringBuilder();
        while (!input.equals("") && scanner.hasNextLine()) {
            text.append(input);
            input = scanner.nextLine();
        }
        scanner.close();

        Encoder encoder = new Encoder();
        encoder.setText(text.toString());
        encoder.encode();
        System.out.println(encoder.getTable());
        System.out.println(encoder.getEncodedText());
    }

    private String inputText;

    private Map<Character, Integer> chars;

    private Map<Character, String> table;

    private PriorityQueue<Node> queue;

    private static Set<Character> validCharacters;

    public Encoder() {
        chars = new HashMap<>();
        validCharacters = new HashSet<>();
        queue = new PriorityQueue<>();
        table = new LinkedHashMap<>();
        inputText = "";
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

    public void encode() {
        this.chars.forEach((key, value) -> this.queue.add(new Node(key, value)));
        while (queue.size() > 1) {
            this.queue.add(new Node(queue.poll(), queue.poll()));
        }
        buildTable(queue.poll(), "");
    }

    public String getEncodedText() {
        StringBuilder result = new StringBuilder();
        for (char c : inputText.toCharArray()) {
            if (validCharacters.contains(c)) {
                result.append(this.table.get(c));
            }
        }
        return result.toString();
    }

    private void buildTable(Node node, String code){
        if (node.getCharacter() != null) {
            table.put(node.getCharacter(), code.length() > 0 ? code : "0");
            return;
        }
        if (node.getLeftNode() != null) {
            buildTable(node.getLeftNode(), code + "0");
        }
        if (node.getRightNode() != null) {
            buildTable(node.getRightNode(), code + "1");
        }
    }

    public void setText(String s) {
        this.inputText = s;
        for (char c : s.toCharArray()) {
            if (validCharacters.contains(c)) {
                chars.put(c, chars.getOrDefault(c, 0) + 1);
            }
        }
    }

    public Map<Character, String> getTable() {
        return Collections.unmodifiableMap(table);
    }
}
