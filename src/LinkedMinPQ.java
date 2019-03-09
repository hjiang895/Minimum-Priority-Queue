public class LinkedMinPQ<T extends Comparable<T>> implements MinPQ<T> {

  ////////////////////////
  // Instance variables //
  ////////////////////////
  Node top;
  int size;


  /////////////////////////////////
  // Node inner class definition //
  /////////////////////////////////

  // Node class
  class Node {
    T info;
    Node left;
    Node right;
    Node parent;

    Node(T info) {
      this.info = info;
    }
  }

  /////////////////////////////////////////////
  //     Helper methods to implement.       //
  ////////////////////////////////////////////

  // 1. Node getNode(int z, Node traverse)
  //    A method that returns a pointer to a specific
  //    node in the tree, according to its number.
  //    You will use this to identify the "bottomn node"
  //    in delMin() and to identify where to insert a
  //    new node in insert().

  // 2. public void sink (Node n)
  //    A method that will sink new info down to a node where it
  //    is smaller than larger child but bigger than its parent.

  // 3. public void swim (Node n)
  //    A method that will swim info up from the bottom to a node
  //    where it's bigger than its parent and smaller than its children.

  // 4. String printThisLevel (Node root, int level) {
  //    A helper method that is used in the instance method,
  //    toString() defined for you below.
  String printThisLevel (Node root, int level) {
    StringBuilder s = new StringBuilder();

    // Base case 1: if the current node is null, return the current string.
    if (root == null) {
      return s.toString();
    }

    // Base case 2: If you're at thge first level, append the
    // info field of the current node.
    if (level == 1) {
      s.append( root.info.toString());
    }
    // Recursive calls: otherwise call the method on the left
    // and on the right of the next lower level.
    else if (level > 1)  {
      s.append( printThisLevel(root.left, level-1));
      s.append( printThisLevel(root.right, level-1));
    }
    return s.toString();
  }


  ///////////////////////////////////////////////////////
  // Methods you must implement from the PQ interface //
  //////////////////////////////////////////////////////

  // public T delMin ();
  // Remove and return the min (top) element
  // You will call sink() and getNode()

  // public void insert(T key);
  // Insert a new element.
  // You will call swim() and getNode()

  // Return true if the PQ is empty
  // public boolean isEmpty();

  // Return the size of the PQ.
  // public int size();

  // Return a string showing the PQ in level order, i.e.,
  // containing the info at each node, L to R, from top level to bottom.
  // I've implemented this for you! It uses a helper method from above.
  public String toString() {
    // Create a StringBuilder object to make it more efficient.
    StringBuilder sb=new StringBuilder();

    // get the height of the tree
    int height = (int)Math.ceil(Math.log(size+1) / Math.log(2));

    // for each level in the tree, call printThisLevel and
    // append the output to the StringBuilder
    for (int i=1; i<=height; i++) {
      sb.append("level " + i + ": "+ printThisLevel(this.top, i) + "\n");
    }

    // Return the string of the StringBuilder object
    return sb.toString();
  }


  ////////////////////////////////////////////////////////////
  // Main method you must write to test out your code above //
  ////////////////////////////////////////////////////////////

  public static void main (String[] args) {
    // Instantiate a LinkedMinPQ using <> to define the type T.
    // Call insert() and toString() and delMin() a bunch of times.
    // Make sure you don't get null pointer exceptions.
    // Make sure you don't get infinite loops.
    // Make sure your tree looks right after each insert or delMin.
    // Helpful hint: insert lots of System.out.println statements
    // (e.g., at the beginning of each function announcing what
    // function has been called) so that you know where you are
    // getting stuck!

  }

}
