# AVL Tree

This repository contains a class that implements an AVL Tree, as well a testing suite for operations on the AVL Tree. In order to use the AVLTree, refer to the public methods below.

## Public Methods

`AVLTree()` - default constructor, creating a new, empty instance of an AVL Tree.

`void insert(int val)` - create a new node with val and insert into the tree

`void delete(int val)` - removes val from the tree

`boolean search(int val)` - returns boolean indicating if the tree contains the val or not

`List<Integer> getInOrderTraversal` - returns a List of integers in the order that they are traversed in the tree using an in order traversal.

`int size()` - returns number of elements in the tree.

`void printTree()` - prints a string representation of the tree. Each node is printed with its label, value, balance factor, and height (in that order). The root is labelled `RT` while right and left subtrees are labelled `R` and `L`. The root is printed first, and indentation level is used to show subtree level.

EXAMPLE:

    RT:[V,BF,H: 6,1,2]
            R[V,BF,H: 9,0,1]
                    R[V,BF,H: 10,0,0]
                            R[]
                            L[]
                    L[V,BF,H: 7,0,0]
                            R[]
                            L[]
            L[V,BF,H: 6,0,0]
                    R[]
                    L[]

## Testing

The test suite provides public methods that test all of the tree operations utilizing entirely random inputs. Alter main() and invoke the following methods while passing in the desired amount of elements to test the tree with in order to create more tests.

`boolean insertTest(int amount)`

`boolean deleteTest(int amount)`

`boolean searchTest(int amount)`

All test results are printed to standard output. Run the program with redirection to create test logs.

`java TestAVLTree > testOutput.txt`
