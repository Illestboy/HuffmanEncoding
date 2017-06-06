package com.illestboy;

import java.util.*;

/**
 * Created by illestboy on 05.06.17.
 */
public class Encoder {

    private static class Node implements Comparable<Node> {

        private Integer frequency;

        private Character character;

        private Node leftNode;

        private Node rightNode;

        public Node(Character character, int frequency) {
            this.frequency = frequency;
            this.character = character;
        }

        public Node(Node leftNode, Node rightNode) {
            this.character = null;
            this.leftNode = leftNode;
            this.rightNode = rightNode;
            this.frequency = leftNode.getFrequency() + rightNode.getFrequency();
        }

        public int getFrequency() {
            return frequency;
        }

        public void setFrequency(int frequency) {
            this.frequency = frequency;
        }

        public Character getCharacter() {
            return character;
        }

        public void setCharacter(Character character) {
            this.character = character;
        }

        public Node getLeftNode() {
            return leftNode;
        }

        public void setLeftNode(Node leftNode) {
            this.leftNode = leftNode;
        }

        public Node getRightNode() {
            return rightNode;
        }

        public void setRightNode(Node rightNode) {
            this.rightNode = rightNode;
        }

        @Override
        public int compareTo(Node o) {
            int result = this.frequency.compareTo(o.getFrequency());
            //if (result == 0 && this.character != null && o.getCharacter() != null) {
            //    result = o.getCharacter().compareTo(this.character);
            //}
            return result;
        }

        @Override
        public String toString() {
            return String.valueOf(this.getFrequency());
        }
    }

    private Map<Character, Integer> chars;

    private Map<Character, String> table = new HashMap<>();

    private PriorityQueue<Node> queue = new PriorityQueue<>();

    private Node root;

    private static Set<Character> characters;

    public Encoder() {
        chars = new HashMap<>();
        characters = new HashSet<>();
        initializeAllowedCharacters();
    }

    private void initializeAllowedCharacters() {
        for (char c = 'a'; c <= 'z'; c++) {
            characters.add(c);
        }

        for (char c = 'A'; c <= 'Z'; c++) {
            characters.add(c);
        }

        for (char c = 'А'; c <= 'я'; c++) {
            characters.add(c);
        }
        for (char c = '0'; c <= '9'; c++) {
            characters.add(c);
        }

        characters.add('!'); // Убрать
        characters.add(',');
        characters.add('.');
        characters.add(' ');
        characters.add(':');
        characters.add('Ё');
        characters.add('ё');
    }

    public void encodeChars() {
        buildTree();
    }

    private void buildTree() {
        chars = sortMap(chars);
        fillQueue();
        if (queue.size() > 1 ) {
            while (queue.size() > 2) {
                this.queue.add(new Node(queue.poll(), queue.poll()));
            }
            this.root = new Node(queue.poll(), queue.poll());
        } else if (queue.size() == 1) {
            this.root = queue.poll();
        }
        buildTable(root, "");
        System.out.println(table);
    }

    private void buildTable(Node node, String code){
        if (node.getCharacter() != null) {
            table.put(node.getCharacter(), code);
            return;
        }
        if (node.getLeftNode() != null) {
            buildTable(node.getLeftNode(), code + "0");
        }
        if (node.getRightNode() != null) {
            buildTable(node.getRightNode(), code + "1");
        }
    }

    private boolean canVisit(Node node, List<Node> visited) {
        return (!visited.contains(node) && node.getLeftNode() != null && node.getRightNode() != null);
    }

    private void fillQueue() {
        this.chars.forEach((key, value) -> this.queue.add(new Node(key, value)));
    }

    public void addChar(Character c) {
        if (characters.contains(c)) {
            chars.put(c, chars.getOrDefault(c, 0) + 1);
        }
    }

    public Map<Character, Integer> getChars() {
        return Collections.unmodifiableMap(chars);
    }

    public static <K, V extends Comparable<? super V>> Map<K, V> sortMap( Map<K, V> map ) {
        List<Map.Entry<K, V>> list =
                new LinkedList<>(map.entrySet());
        Collections.sort( list, Comparator.comparing(o -> (o.getValue())));

        Map<K, V> result = new LinkedHashMap<>();
        for (Map.Entry<K, V> entry : list) {
            result.put( entry.getKey(), entry.getValue() );
        }
        return result;
    }
}
