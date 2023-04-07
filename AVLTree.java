public class AVLTree {
    private int size;
    private Node root;

    public AVLTree() {
        this.size = 0;
        this.root = null;
    }

    public boolean contains(int val) {
        // TODO
        return false;
    }

    public void delete() {
        // TODO
    }

    @Override
    public String toString() {
        return root.toString();
    }

    public void insert(int value) {
    }

    public boolean search(int value) {
        return false;
    }

    public int size() {
        return this.size;
    }
}

class Node {
    private Node left;
    private Node right;
    private int val;

    public Node(int val) {
        this.left = null;
        this.right = null;
    }

    public void setLeft(Node n) {
        this.left = n;
    }

    public void setRight(Node n) {
        this.right = n;
    }

    @Override
    public String toString() {
        // TODO
        return "Node: " + this.val;
    }

}