public class AVLTree {
    private int size;
    private Node root;

    public AVLTree() {
        this.size = 0;
        this.root = null;
    }

    private Node get(int val) {
        Node currentNode = root;
        while (currentNode != null) {
            int currentVal = currentNode.getVal();
            if (currentVal == val) {
                return currentNode;
            } else if (val < currentVal) {
                currentNode = currentNode.getLeft();
            } else {
                currentNode = currentNode.getRight();
            }
        }
        return null;
    }

    public void delete(int val) {
        Node toDelete = get(val);
        // The following block is responsible for determining which node (if any)
        // will replace the current node
        if (toDelete.getLeft() != null && toDelete.getRight() == null) {
            // contains left sub-tree but no right sub-tree
            toDelete.setVal(toDelete.getLeft().getVal());
            toDelete.setRight(toDelete.getLeft().getRight());
            toDelete.setLeft(toDelete.getLeft().getLeft());
        } else if (toDelete.getLeft() == null && toDelete.getRight() != null) {
            // contains right sub-tree but no left sub-tree
            toDelete.setVal(toDelete.getRight().getVal());
            toDelete.setLeft(toDelete.getRight().getLeft());
            toDelete.setRight(toDelete.getRight().getRight());
        } else if (toDelete.getLeft() != null && toDelete.getRight() != null) {
            // contains left/right sub-trees
            // TODO probably should implement checker so less work is done (ex: choose when
            // TODO to go left/right)
            Node beforeNext = toDelete.getRight();
            Node next = toDelete.getRight().getLeft();
            while (next != null && next.getLeft() != null) {
                beforeNext = beforeNext.getLeft();
                next = next.getLeft();
            }
            toDelete.setVal(next.getVal());
            beforeNext.setLeft(next.getRight());
        } else {
            // leaf node
        }
        this.size--;
        // TODO update height and then balance tree
    }

    public void printTree() {
        stringHelper(0, root);
    }

    private void stringHelper(int depth, Node node) {

        String indent = "";
        for (int i = 0; i < depth; i++) {
            indent += '\t';
        }
        if (node == null) {
            System.out.println(indent + "[]");
            return;
        }
        System.out.println(indent + node);
        stringHelper(depth + 1, node.getLeft());
        stringHelper(depth + 1, node.getRight());
    }

    public void insert(int value) {
        if (root == null) {
            root = new Node(value);
        }
        root = insertHelper(value);
        this.size++;
    }

    private Node insertHelper(int value) {
        return null;
    }

    public boolean search(int value) {
        if (root == null)
            return false;
        Node currentNode = root;
        int currentVal = currentNode.getVal();
        while (currentVal != value && currentNode != null) {
            // only greater/lesser comparison due to equal condition for while loop
            if (currentVal < value) {
                currentNode = currentNode.getRight();
            } else {
                currentNode = currentNode.getLeft();
            }
            if (currentNode != null) { // TODO can we simplify?
                currentVal = currentNode.getVal();
            }
        }
        return currentVal == value;
    }

    public int size() {
        return this.size;
    }

    public static void main(String[] args) {
        System.out.println("TESTING");
    }
}

class Node {
    private Node left;
    private Node right;
    private int val;
    private int height;

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

    public void setHeight(int val) {
        this.height = val;
    }

    public void setVal(int val) {
        this.val = val;
    }

    @Override
    public String toString() {
        // TODO
        return "[Node: " + this.val + "]";
    }

    public int getVal() {
        return this.val;
    }

    public Node getLeft() {
        return this.left;
    }

    public Node getRight() {
        return this.right;
    }

    /**
     * Returns the heigh of the node
     * 
     * @return the height of the node (-1,0,1)
     */
    public int getHeight() {
        return this.height;
    }
}