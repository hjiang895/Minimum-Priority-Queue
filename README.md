## Problem Set 5: Minimum priority queue

### Due Tuesday, March 19 @ 11:59pm

---

## Background
As we have seen, *complete binary trees* can be efficiently represented in a sequential array by allocating the elements of the tree in level-order in the array. Binary trees can also be represented with a linked data structures, as shown below, in which each node contains some value and has a pointer to the left child and a pointer to the right child.


```java
class Node {
                                           +-----------------+
  T info;                                  |       info      |
  Node lchild;                             +--------+--------+
  Node rchild;                             | lchild | rchild |
  ...                                      +--------+--------+
}
```

In this problem set, you will implement a **minimum priority queue** using a **heap-ordered binary tree**, which you will implement with a **triply linked Node** structure instead of an array. You will need **three** links per node: two to traverse down the tree (one for the left child and one for the right child) and one to traverse up the tree to the node's parent, like this:


```java
class Node {
                                           +-----------------+
  T info;                                  |       parent    |
  Node lchild;                             +-----------------+
  Node rchild;                             |       info      |
  Node parent;                             +--------+--------+
  ...                                      | lchild | rchild |
                                           +--------+--------+
}
```

Please use the following interface for your ADT (included in the `src` directory as `MinPQ.java`):

```java
public interface MinPQ<T extends Comparable<T>> {
  T delMin();
  void insert(T key);
  boolean isEmpty();
  int size();
  String toString();
}
```

Note that the specification of the generic type variable `T` in:

```java
T extends Comparable<T>
```

means that the type variable `T` can be replaced by any type that includes an `int compareTo(T other)` function. You don't need to implement anything special! Just remember that when you declare an instance of `LinkedMinPQ`, you should only allow it to store something that extends `Comparable` (e.g., a String, an Integer). Recall that `compareTo` returns a positive number when the object calling it is larger than the argument, 0 if they're equal, and a negative number if the object calling is smaller than the argument.

## Implementation
I have included some skeleton code to get you started in the `LinkedMinPQ.java` file in the `src` directory. You do not have to use this code, but you should name your implementation `LinkedMinPQ.java`, and as always, the file should go in the `src` directory.

### insert and delete
As with the sequential implementation, the `insert` operation must find the tree node to which a new entry is to be attached, i.e., the next available place to attach a new node. It should then "swim" that new entry up to a node where it is smaller than its children but bigger than its parent.

Similarly, the `delMin` operation, after it removes the top (minimum) node, must replace the info in the top node with the last entry in the complete binary tree, and then "sink" it down to a location where it is smaller than its children but bigger than its parent. 

### `toString()` method
The toString() method should return the values in the nodes in *level-order* (a.k.a. "breadth first"). That is, it should print the value of each node on each level from left to right, starting at the level of the root node and proceeding down  level by level. I have provided some code for this in the `example_code` repo on binary trees, which you can repurpose for this problem set.

### `sink()` and `swim()` helper methods
You should write a `sink()` method and a `swim()` method to help you with your insert and delete methods. Remember that you are not actually sinking or swimming a `Node`. Instead, you just swap the data in the `info` field of the current `Node` with the data in the `info` field in the parent `Node` (if swimming) or child `Node` (if sinking). These will be functions with return type `void`.

### Finding the bottom node
You should write a method for finding the "bottom node". In the sequential implementation, the bottom node location was easy to find using the size of the tree to compute the appropriate array index. With this linked implementation, a little more work is required. You can still use the size of the tree, but you will use it to compute the path from the root to the desired node using integer division by 2 and the modulus operator.

Here's an intuition about how this will work. Look at the tree below, where the nodes are numbered from right to left on each level.
```
                                                     1 
                                                   /    \
                                                 /       \
                                                /         \
                                               2           3
                                             /   \       /   \
                                            4     5     6     7
                                           / \   / \   / \   / \
                                          8  9  10 11 12 13 14  15

```

**Observation** What do all left children have in common? They are all even numbers (i.e., for each left child `l`, `l%2==0`). What do all right children have in common? They are all odd numbers (i.e., for each right child `r`, `r%2==1`).

**Observation** How are the numbers on each level arranged? The leftmost number is the power of 2 (e.g., 1, 2, 4, 8), and each subsequent number on that level is one more than the previous number until the rightmost number, which is 1 less than then next power of 2 (3, 7, 15).

To navigate in a tree to a node according to its number, you can do this:

1. Start with a pointer, `traverse`, pointing at the root `Node`, and set an int variable, `z`, equal to the number of the `Node` you want to reach. 

2. Get the remainder of `z` when dividing by 2 using the modulus operator. If `z%2==0`, set `traverse` to `traverse.lchild`. If `z%2==1`, set `traverse` to `traverse.left`. Set `z` to `z/2` (integer division).

3. Repeat until `z` is equal to 1. Return `traverse`.

When you **remove the min** from the root, you want to *find* the bottom node, so you can move its info to the root node and then sink it down to where it belongs. (You'll also want to disconnect it from its parent node.)

When you **add an element**, you want to find where that new bottom node is going to go. This means that you are actually trying to find the parent node of the new node. 

You will have to use a different value for `z` above, depending on whether you want it to return a pointer to the `Node` you have to delete (`z` should be the size of the PQ) or to the `Node` whose right or left child you want to create (`z` should be the future size of the PQ divided by 2).

## Testing your code
As usual, write a few unit tests in order to demonstrate that the code is working correctly. Remember: the top of the heap should be the **smallest** item (e.g., the small number, the String that comes first alphabetically). You might find it easiest to test with Strings that are individual single-case letters or integers so that you're putting things in an order that's easy to check.

