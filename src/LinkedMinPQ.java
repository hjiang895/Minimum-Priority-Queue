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
  private Node getNode(int z, Node traverse){
	// If index z is 1, return traverse (top node)
    if(z == 1){
      return traverse;
    }
    else{
      // If index is even, return left child of getNode on parent index
      if(z%2 == 0){
        return getNode(z/2, traverse).left;
      }
	    // If index is odd, return right child of getNode on parent index
      else{
        return getNode(z/2, traverse).right;
      }
    }
  }

  // 2. public void sink (Node n)
  //    A method that will sink new info down to a node where it
  //    is smaller than larger child but bigger than its parent.
  public void sink (Node n){
    // Create compared node to assign to either left or right child (reduces repeat code)
	Node compared;
    if(n.right != null){
      if(n.left != null){
		    // Condition 1: right and left both != null --> compare to smaller child
        if(n.left.info.compareTo(n.right.info) < 0){
          compared = n.left;
        }
        else{
          compared = n.right;
        }
      }
      else{
		    // Condition 2: right != null, left == null --> compare to right child
        compared = n.right;
      }
    }
    else if(n.left != null){
	    // Condition 3: right == null, left != null --> compare to left child
      compared = n.left;
    }
    else{
      // Condition 4: no children --> sinking complete
      return;
    }
    if(compared.info.compareTo(n.info) < 0){
      // If compared child is smaller than n, swap info and call sink on compared
      T temp = n.info;
      n.info = compared.info;
      compared.info = temp;
      sink(compared);
    }
  }

  // 3. public void swim (Node n)
  //    A method that will swim info up from the bottom to a node
  //    where it's bigger than its parent and smaller than its children.
  public void swim (Node n){
    if(n.parent != null){
      // Passes if parent exists (n is not top)
      if(n.parent.info.compareTo(n.info) > 0){
        // If parent is larger than n, swap info and call swim on the modified parent node
        T temp = n.info;
        n.info = n.parent.info;
        n.parent.info = temp;
        swim(n.parent);
      }
    }
  }

  // 4. String printThisLevel (Node root, int level) {
  //    A helper method that is used in the instance method,
  //    toString() defined for you below.
  String printThisLevel (Node root, int level) {
    StringBuilder s = new StringBuilder();

    // Base case 1: if the current node is null, return the current string.
    if (root == null) {
      return s.toString();
    }

    // Base case 2: If you're at the first level, append the
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
  public T delMin(){
    System.out.println("delMin method called"); //Debug print
	   // Assigns top.info to topData to preserve value after modifying top
    T topData = top.info;
	   // Base case 1: if there are no values in queue, return null
    if(size == 0){
      return null;
    }
	  // Base case 2: if there is only one value, remove and return its info
    if(size == 1){
      top = null;
    }
	// Case 3: size > 1, bring the largest value to the top and sink it
  	else {
      top.info = getNode(size, top).info;
      Node parent = getNode(size/2, top);
  		// Eliminate largest value's parent's pointer to largest value
      if(parent.right != null){
      	parent.right = null;
      }
      else{
      	parent.left = null;
      }
        sink(top);
	  }
    size--;
    return topData;
  }


  // public void insert(T key);
  // Insert a new element.
  // You will call swim() and getNode()
  public void insert(T key){
    System.out.println("insert method called"); // Debug print
    Node current = new Node(key);
	  // Base case: if queue empty, add to top
    if(size == 0){
      top = current;
    }
    else{
		  // Find parent of index where the next node would be added and
		  // add pointers between parent and current
    	Node parent = getNode((size+1)/2, top);
    	current.parent = parent;
    	if(parent.left == null){
        	parent.left = current;
     	}
      else if(parent.right == null){
      	parent.right = current;
      }
		  // Swim the new node up from the bottom of the queue
      swim(current);
    }
    size++;
  }

  // Return true if the PQ is empty
  // public boolean isEmpty();
  public boolean isEmpty(){
    System.out.println("isEmpty method called"); // Debug print
    return (size == 0);
  }

  // Return the size of the PQ.
  // public int size();
  public int size(){
    System.out.println("size method called"); // Debug print
    return size;
  }

  // Return a string showing the PQ in level order, i.e.,
  // containing the info at each node, L to R, from top level to bottom.
  // I've implemented this for you! It uses a helper method from above.
  public String toString() {
    // Create a StringBuilder object to make it more efficient.
    System.out.println("toString method called");
    StringBuilder sb = new StringBuilder();

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
    LinkedMinPQ <String> queue = new LinkedMinPQ <> ();
    // Call insert() and toString() and delMin() a bunch of times.
	  System.out.println(queue.isEmpty()); // True
    queue.insert("K");
    System.out.println(queue); // Level 1: K
    queue.insert("A");
    System.out.println(queue); // Level 1: A, Level 2: K
    queue.insert("T");
    System.out.println(queue); // Level 1: A, Level 2: K T
    queue.insert("L");
    System.out.println(queue); // Level 1: A, Level 2: K T, Level 3: L
    queue.insert("S");
    System.out.println(queue); // Level 1: A, Level 2: K T, Level 3: L S
    queue.insert("E");
    System.out.println(queue); // Level 1: A, Level 2: K E, Level 3: L S T
    queue.insert("D");
    System.out.println(queue); // Level 1: A, Level 2: K D, Level 3: L S T E
    queue.insert("F");
    System.out.println(queue); // Level 1: A, Level 2: F D, Level 3: K S T E, Level 4: L
    queue.insert("L");
    System.out.println(queue); // Level 1: A, Level 2: F D, Level 3: K S T E, Level 4: L L
	  System.out.println(queue.size()); // 9
    queue.delMin();
    System.out.println(queue); // Level 1: D, Level 2: F E, Level 3: K S T L, Level 4: L
    queue.delMin();
    System.out.println(queue); // Level 1: E, Level 2: F L, Level 3: K S T L
    queue.delMin();
    System.out.println(queue); // Level 1: F, Level 2: K L, Level 3: L S T
    queue.delMin();
    System.out.println(queue); // Level 1: K, Level 2: L L, Level 3: T S
	  System.out.println(queue.size()); // 5
    // Make sure you don't get null pointer exceptions.
    // Make sure you don't get infinite loops.
    // Make sure your tree looks right after each insert or delMin.
    // Helpful hint: insert lots of System.out.println statements
    // (e.g., at the beginning of each function announcing what
    // function has been called) so that you know where you are
    // getting stuck!

  }
}
