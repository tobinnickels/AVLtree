/**
 * FILE: AVLTree.java
 * COURSE: CSc 345 - Spring 2023
 * PURPOSE: The purpose of this file is to hold code which can create an AVL
 * Tree. An AVL Tree is a self balancing binary search tree (SBBST) where
 * the balance factor (defined as right subtree height minus left subtree
 * height) is either -1,0, or 1.
 */
public class AVLTree {
    /**
     * This represents how many nodes are currently in the tree
     */
    private int size;

    /**
     * The root node. The root may switch to another node in order to
     * maintain balance.
     */
    private Node root;

    /**
     * The default constructor, sets root to null and size to 0.
     */
    public AVLTree() {
        this.size = 0;
        this.root = null;
    }

    /**
     * Removes the value from the tree. Currently there is no check on
     * if the value exists within the tree, however it is okay to do so.
     * 
     * @param val the value to be removed from the tree
     */
    public void delete(int val) {
        root = deleteHelper(root, val);
        this.size--;
    }

    /**
     * This helper method finds the node that is to be deleted, deletes the node by
     * invoking deleteNode() and on the recursive callback updates currentNode's
     * values and returns the balanced tree.
     * 
     * @param currentNode
     * @param value
     * @return
     */
    private Node deleteHelper(Node currentNode, int value) {
        if (currentNode == null) {
            return null;
        }
        int currentVal = currentNode.getVal();
        if (currentVal == value) {
            return deleteNode(currentNode, value);
        } else if (currentVal < value) {
            currentNode.setRight(deleteHelper(currentNode.getRight(), value));
        } else {
            currentNode.setRight(deleteHelper(currentNode.getLeft(), value));
        }
        currentNode.updateHeight();
        return balanceTree(currentNode);
    }

    /**
     * This helper method is responsible for properly updating the tree
     * by 'removing' the node, either by updating the value and shifting
     * the left/right subtree or returning null if a leaf node is found.
     * 
     * @param toDelete the node to be 'removed'
     * @param value    the value to be removed
     * @return the new node that exists where toDelete is (which may be null)
     */
    private Node deleteNode(Node toDelete, int value) {
        if (toDelete.getLeft() == null && toDelete.getRight() == null) {
            return null; // leaf node found, thus return null so previous node points to null
        }
        // The following block is responsible for determining which node will replace
        // the current node
        if (toDelete.getLeft() != null && toDelete.getRight() == null) {
            // contains left subtree but no right subtree
            toDelete.setVal(toDelete.getLeft().getVal());
            toDelete.setRight(toDelete.getLeft().getRight());
            toDelete.setLeft(toDelete.getLeft().getLeft());
        } else if (toDelete.getLeft() == null && toDelete.getRight() != null) {
            // contains right subtree but no left subtree
            toDelete.setVal(toDelete.getRight().getVal());
            toDelete.setLeft(toDelete.getRight().getLeft());
            toDelete.setRight(toDelete.getRight().getRight());
        } else {
            // contains a left/right subtree
            // TODO test further
            // TODO improve checker (ex: determine where to search)
            // TODO currently gets smallest val in right subtree
            Node min = toDelete.getRight();
            while (min.getLeft() != null) {
                min = min.getLeft();
            }
            toDelete.setVal(min.getVal());
            // This ensure thats the node replacing toDelete gets properly removed
            toDelete.setRight(deleteHelper(toDelete.getRight(), min.getVal()));
        }
        return toDelete;
    }

    /**
     * This function prints out the tree with indentation in order to see the
     * structure of the tree.
     */
    public void printTree() {
        System.out.println("--------------------");
        printHelper(0, root, "RT:");
        System.out.println("--------------------");

    }

    /**
     * This helper function works recursively in order to print out
     * the structure of the tree. It does a pre-order traversal and
     * prints out identifiers in order to see the structure more easily.
     * 
     * @param depth    an int representing how deep the node is from the root
     * @param node     the node to be printed
     * @param identity a String (either RT,R, or L) representing the position of
     *                 this node relative to the parent
     */
    private void printHelper(int depth, Node node, String identity) {
        String indent = "";
        for (int i = 0; i < depth; i++) {
            indent += '\t';
        }
        if (node == null) {
            System.out.println(indent + identity + "[]");
            return;
        }
        System.out.println(indent + identity + node);
        printHelper(depth + 1, node.getRight(), "R");
        printHelper(depth + 1, node.getLeft(), "L");

    }

    /**
     * This function inserts the value into the tree. It is assumed currently that
     * the value is not already in the tree. When a value is inserted, the tree
     * is balanced.
     * 
     * @param value the value to be inserted
     */
    public void insert(int value) {
        root = insertHelper(root, value);
        this.size++;
    }

    /**
     * This helper function inserts the node into the tree, returning
     * Node objects in order to maintain balance after inserting the value.
     * 
     * @param node  the current Node object, or null
     * @param value the value to be inserted
     * @return a Node object which represents the new root of the subtree (or
     *         possibly the entire tree)
     */
    private Node insertHelper(Node node, int value) {
        if (node == null) {
            return new Node(value);
        }
        if (node.getVal() < value) {
            node.setRight(insertHelper(node.getRight(), value));
        } else {
            node.setLeft(insertHelper(node.getLeft(), value));
        }
        node.updateHeight();
        return balanceTree(node);
    }

    /**
     * This function is called whenever a node becomes inserted so that
     * the balance factor is maintained.
     * 
     * @param node the root of the tree that is to be balanced
     * @return the root of the newly balanced tree
     */
    private Node balanceTree(Node node) {
        int balance = node.getBalanceFactor();
        // If a node becomes unbalanced, we must balance it and return the newly
        // balanced tree
        if (balance == -2) {
            /*
             * This code is responsible for balancing left heavy trees. The cases
             * signal the direction of the imbalance from the current node
             * (Ex: left-right means node has a left subtree which has a right subtree)
             */
            if (node.getLeft().getBalanceFactor() <= 0) {
                // left-left case: / ==> ^
                return rotateRight(node);
            } else {
                // left-right case: < ==> / ==> ^
                node.setLeft(rotateLeft(node.getLeft())); // This transforms the tree to be left-left
                return rotateRight(node);
            }
        } else if (balance == 2) {
            /*
             * This code is responsible for balancing right heavy trees. The cases
             * signal the direction of the imbalance from the current node
             * (Ex: right-left means node has a right subtree which has a left subtree)
             */
            if (node.getRight().getBalanceFactor() >= 0) {
                // right-right case: \ ==> ^
                return rotateLeft(node);
            } else {
                // right-left case: > ==> \ ==> ^
                node.setRight(rotateRight(node.getRight())); // This transforms the tree to be right-right
                return rotateLeft(node);
            }
        }
        return node; // if nothing has to be balanced, we can return the node as is
    }

    /**
     * This helper function rotates the tree right, or in other words
     * shifts the nodes so that the left subtree becomes the new root
     * 
     * @param node the node that will be shifted
     * @return the new root of the tree
     */
    private Node rotateRight(Node node) {
        Node newRoot = node.getLeft();
        node.setLeft(newRoot.getRight());
        newRoot.setRight(node);
        node.updateHeight();
        newRoot.updateHeight();
        return newRoot;
    }

    /**
     * This helper function rotates the tree left, or in other words
     * shifts the nodes so that the right subtree becomes the new root
     * 
     * @param node the node that will be shifted
     * @return the new root of the tree
     */
    private Node rotateLeft(Node node) {
        Node newRoot = node.getRight();
        node.setRight(newRoot.getLeft());
        newRoot.setLeft(node);
        node.updateHeight();
        newRoot.updateHeight();
        return newRoot;
    }

    /**
     * Returns a boolean indicating if the value exists within the tree or not.
     * 
     * @param value the integer to be found
     * @return true if it exists in the tree, false if not
     */
    public boolean search(int value) {
        Node currentNode = root;
        while (currentNode != null) {
            int currentVal = currentNode.getVal();
            if (currentVal == value) {
                return true;
            } else if (currentVal < value) {
                currentNode = currentNode.getRight();
            } else {
                currentNode = currentNode.getLeft();
            }
        }
        return false;
    }

    /**
     * Returns the amount of Nodes within the tree
     * 
     * @return
     */
    public int size() {
        return this.size;
    }

    public static void main(String[] args) {
        System.out.println("TESTING");
        AVLTree tree = new AVLTree();
        tree.insert(10);
        tree.insert(6);
        tree.printTree();
        tree.insert(3);
        tree.printTree();
        tree.insert(9);
        tree.insert(7);
        tree.printTree();
        tree.insert(15);
        tree.printTree();
        tree.insert(13);
        tree.printTree();
        System.out.println("Deleting!");
        tree.delete(13);
        tree.printTree();
    }
}

class Node {
    /**
     * The left subtree which contains values lesser than the current node's value
     */
    private Node left = null;
    /**
     * The right subtree which contains values greater than the current node's value
     */
    private Node right = null;
    /**
     * The value held by the node. It is assumed that no duplicate values will
     * be within the tree or inserted into the tree.
     */
    private int val;
    /**
     * This represents the furthest distance from a leaf node.
     * Leaf nodes have a height of zero.
     */
    private int height = 0;
    /**
     * The balance factor of a particular node is calculated by subtracting
     * the height of the left subtree from the right subtree.
     * 
     * Once a tree has been balanced, it will always be equal to either: {-1,0,1}
     */
    private int balanceFactor = 0;

    /**
     * The default constroctor for Node. Sets the value of the node to the given
     * value.
     * 
     * @param val any integer
     */
    public Node(int val) {
        this.val = val;
    }

    /**
     * Sets the nodes left subtree to the given node
     * 
     * @param n a Node object or null
     */
    public void setLeft(Node n) {
        this.left = n;
    }

    /**
     * Sets the nodes right subtree to the given node
     * 
     * @param n a Node object or null
     */
    public void setRight(Node n) {
        this.right = n;
    }

    /**
     * Sets the val of Node to the given value
     * 
     * @param val any integer
     */
    public void setVal(int val) {
        this.val = val;
    }

    /**
     * This function updates the height and balance factor values of the node.
     * It is called in the insertHelper() method after the node has been inserted
     * into the tree in order to determine if balancing the tree is necessary.
     */
    protected void updateHeight() {
        int leftHeight = this.left != null ? this.left.height : -1;
        int rightHeight = this.right != null ? this.right.height : -1;
        int maxHeight = leftHeight < rightHeight ? rightHeight : leftHeight;
        this.height = 1 + maxHeight;
        this.balanceFactor = rightHeight - leftHeight;
    }

    @Override
    public String toString() {
        return "[V,BF,H: " + this.val + ',' + this.balanceFactor + ',' + this.height + "]";
    }

    /**
     * Returns the value of the node
     * 
     * @return an int which represents the nodes value
     */
    public int getVal() {
        return this.val;
    }

    /**
     * Returns the balance factor of the node
     * 
     * @return the balance factor of the node
     * @see balanceFactor
     */
    public int getBalanceFactor() {
        return this.balanceFactor;
    }

    /**
     * Returns the left subtree of the node
     * 
     * @return a Node object, or null
     */
    public Node getLeft() {
        return this.left;
    }

    /**
     * Returns the right subtree of the node
     * 
     * @return a Node object, or null
     */
    public Node getRight() {
        return this.right;
    }

    /**
     * Returns the height of the node
     * 
     * @return the height of the node (-1,0,1)
     */
    public int getHeight() {
        return this.height;
    }
}