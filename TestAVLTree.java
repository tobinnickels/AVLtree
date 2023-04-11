
/**
 * File: TestAVLTree.java
 * Course: CSC 345, Spring 2023
 * Purpose: This file contains the TestAVLTree class which provides a set of
 *         static methods to test the AVLTree class.
 */

import java.util.Random;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TestAVLTree {

    /**
     * Alter the main method to create different tests. Each test utilizes random
     * input, so the results will vary each time the program is run.
     * Changing the "amount" parameter
     * will change the number of elements used in the tests if you wish to test
     * larger or smaller trees.
     * 
     */
    public static void main(String[] args) {
        int amount = 10;
        String insertResult = insertTest(amount) ? "passed" : "failed";
        String deleteResult = deleteTest(amount) ? "passed" : "failed";
        String searchResult = searchTest(amount) ? "passed" : "failed";
        System.out.println("\nTest Results: \n");
        System.out.println("Insertion Test: " + insertResult);
        System.out.println("Deletion Test: " + deleteResult);
        System.out.println("Search Test: " + searchResult);
    }

    /**
     * Tests that nodes are inserted in the correct order using random values.
     * 
     * @param amount the amount of elements to insert
     */
    public static boolean insertTest(int amount) {
        System.out.println("\n----------Random Insertion Test " + amount + " Elements -----------\n");
        List<Integer> nums = new ArrayList<>();
        Random rand = new Random();
        AVLTree tree = new AVLTree();
        for (int i = 0; i < amount; i++) {
            int next = rand.nextInt(amount);
            nums.add(next);
            tree.insert(next);
        }
        Collections.sort(nums);
        List<Integer> inOrder = tree.getInOrderTraversal();
        System.out.println("Expected In Order Traversal: " + nums + "\n");
        System.out.println("Actual In Order Traversal: " + inOrder + "\n");
        if (nums.equals(inOrder)) {
            System.out.println("Insertion test 1 passed!");
        } else {
            System.out.println("Insertion test 1 failed!");
        }
        tree.printTree();
        return nums.equals(inOrder);

    }

    /**
     * Tests that nodes are deleted in the correct order using random values.
     * 
     * @param amount the amount of elements to delete
     */
    public static boolean deleteTest(int amount) {
        System.out.println("\n---------- Random Deletion Test: " + amount + " Elements -----------\n");
        AVLTree tree = new AVLTree();
        List<Integer> nums = new ArrayList<>();
        Random rand = new Random();
        for (int i = 0; i < amount; i++) {
            int next = rand.nextInt(amount);
            nums.add(next);
            tree.insert(next);
        }
        int amountDeleted = 0;
        System.out.println("Before Deletion: " + tree.getInOrderTraversal());
        tree.printTree();
        while (amountDeleted < amount) {
            int toDeleteIndex = rand.nextInt(nums.size());
            int numtoDelete = nums.remove(toDeleteIndex);
            System.out.println("Deleting: " + numtoDelete);
            tree.delete(numtoDelete);
            Collections.sort(nums);
            List<Integer> inOrder = tree.getInOrderTraversal();
            System.out.println("Expected In Order Traversal: " + nums + "\n");
            System.out.println("Actual In Order Traversal: " + inOrder + "\n");
            tree.printTree();
            if (!nums.equals(inOrder)) {
                System.out.println("Deletion test failed for " + amount + " elements");
                return false;
            }
            amountDeleted++;
        }
        System.out.println("Deletion test passed for " + amount + " elements!, all elements deleted\n\n");
        return true;
    }

    /**
     * Tests that search finds elements that are in the tree and does not find
     * eleemnts that get deleted.
     * 
     * @param amount the amount of search and delete operations to perform
     */
    public static boolean searchTest(int amount) {
        System.out.println("\n---------- Search Test: " + amount + " Elements -----------\n");
        AVLTree tree = new AVLTree();
        List<Integer> nums = new ArrayList<>();
        // numbers must be unique for searching to fail when an element is deleted
        // so we use a fixed range rather than random.
        for (int i = 0; i < amount; i++) {
            nums.add(i);
        }
        // shuffle the list to create randomness instead
        Collections.shuffle(nums);
        for (int i = 0; i < nums.size(); i++) {
            tree.insert(nums.get(i));
        }
        System.out.println("Tree In Order Traversal: " + tree.getInOrderTraversal() + "\n");
        Random rand = new Random();
        int searches = 0;
        while (searches < amount) {
            int toSearchIndex = rand.nextInt(nums.size());
            int numtoSearch = nums.get(toSearchIndex);
            System.out.println("Searching for: " + numtoSearch);
            if (tree.search(numtoSearch)) {
                System.out.println("Found " + numtoSearch + " in the tree!");
            } else {
                System.out.println("Could not find " + numtoSearch + " in the tree!");
                System.out.println("Search test failed for " + amount + " elements");
                return false;
            }
            System.out.println("Deleting: " + numtoSearch);
            nums.remove(toSearchIndex);
            tree.delete(numtoSearch);
            System.out.println("Searching for: " + numtoSearch + " after deletion");
            if (!tree.search(numtoSearch)) {
                System.out.println("Did not find " + numtoSearch + " in the tree!");
            } else {
                System.out.println("Found " + numtoSearch + " in the tree!");
                System.out.println("Search test failed" + amount + " elements");
                return false;
            }
            searches++;
            System.out.println("\n");
        }
        System.out.println(
                "Search test passed for " + amount + " elements!, all elements found/not found appropriately\n\n");
        return true;
    }

}
