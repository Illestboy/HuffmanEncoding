package com.illestboy;

class Node implements Comparable<Node> {

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
}
