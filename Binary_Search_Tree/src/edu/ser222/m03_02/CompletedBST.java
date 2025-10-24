package edu.ser222.m03_02;

/**
 * A binary search tree based implementation of a symbol table.
 * 
 * Completion time: 4.25 hrs
 *
 * @author Shazeb Mallick, Sedgewick, Acuna
 * @version 1.0
 */

import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Queue;

public class CompletedBST<Key extends Comparable<Key>, Value> implements BST<Key, Value> {
    private Node<Key, Value> root;

    @Override
    public int size() {
        return size(root);
    }

    private int size(Node x) {
        if (x == null)
            return 0;
        else
            return x.N;
    }

    @Override
    public Value get(Key key) {
        Node<Key, Value> iter = root;

        while(iter != null) {
            int cmp = key.compareTo(iter.key);

            if (cmp < 0)
                iter = iter.left;
            else if (cmp > 0)
                iter = iter.right;
            else
                return iter.val;
        }

        return null;
    }

    private Value get(Node<Key, Value> x, Key key) {
        // Return value associated with key in the subtree rooted at x;
        // return null if key not present in subtree rooted at x.
        if (x == null) return null;
        int cmp = key.compareTo(x.key);
        if (cmp < 0) return get(x.left, key);
        else if (cmp > 0) return get(x.right, key);
        else return x.val;
    }

    @Override
    public void put(Key key, Value val) {
        root = put(root, key, val);
    }

    private Node put(Node<Key, Value> x, Key key, Value val) {
        if (x == null)
            return new Node(key, val, 1);

        int cmp = key.compareTo(x.key);
        if (cmp < 0)
            x.left = put(x.left, key, val);
        else if (cmp > 0)
            x.right = put(x.right, key, val);
        else
            x.val = val;
        x.N = size(x.left) + size(x.right) + 1;

        return x;
    }

    @Override
    public Key min() {
        if(root == null)
            throw new NoSuchElementException();
        return min(root).key;
    }

    private Node<Key, Value> min(Node x) {
        if (x.left == null)
            return x;
        return min(x.left);
    }

    @Override
    public Key max() {
        if(root == null)
            throw new NoSuchElementException();
        return max(root).key;
    }

    private Node<Key, Value> max(Node x) {
    if (x.right == null) return x;
        return max(x.right);
    }

    @Override
    public Key floor(Key key) {
        if(root == null)
            throw new NoSuchElementException();

        Node<Key, Value> x = floor(root, key);
        if (x == null)
            return null;
        return x.key;
    }

    private Node<Key, Value> floor(Node<Key, Value> x, Key key) {
        if (x == null)
            return null;
        int cmp = key.compareTo(x.key);
        if (cmp == 0) return x;
        if (cmp < 0) return floor(x.left, key);
        Node<Key, Value> t = floor(x.right, key);
        if (t != null) return t;
        else return x;
    }

    @Override
    public Key select(int k) {
        return select(root, k).key;
    }

    private Node<Key, Value> select(Node x, int k) {
        if (x == null) return null;
        int t = size(x.left);
        if (t > k) return select(x.left, k);
        else if (t < k) return select(x.right, k-t-1);
        else return x;
    }

    @Override
    public int rank(Key key) {
        return rank(key, root);
    }

    private int rank(Key key, Node<Key, Value> x) {
        // Return number of keys less than x.key in the subtree rooted at x.
        if (x == null) return 0;
        int cmp = key.compareTo(x.key);
        if (cmp < 0) return rank(key, x.left);
        else if (cmp > 0) return 1 + size(x.left) + rank(key, x.right);
        else return size(x.left);
    }

    @Override
    public void deleteMin() {
        if(root == null)
            throw new NoSuchElementException();
        root = deleteMin(root);
    }

    private Node<Key, Value> deleteMin(Node x) {
        if (x.left == null) return x.right;
        x.left = deleteMin(x.left);
        x.N = size(x.left) + size(x.right) + 1;
        return x;
    }

    @Override
    public void delete(Key key) {
        root = delete(root, key);
    }

    private Node<Key, Value> delete(Node<Key, Value> x, Key key) {
        if (x == null) return null;
        int cmp = key.compareTo(x.key);
        if (cmp < 0) x.left = delete(x.left, key);
        else if (cmp > 0) x.right = delete(x.right, key);
        else
        {
            if (x.right == null) return x.left;
            if (x.left == null) return x.right;
            Node t = x;
            x = min(t.right);
            x.right = deleteMin(t.right);
            x.left = t.left;
        }
        x.N = size(x.left) + size(x.right) + 1;
        return x;
    }

    @Override
    public Iterable<Key> keys() {
        if (root == null)
            return new LinkedList<>();
        else
            return keys(min(), max());
    }

    @Override
    public Iterable<Key> keys(Key lo, Key hi)
    {
        Queue<Key> queue = new LinkedList<>();
        keys(root, queue, lo, hi);
        return queue;
    }

    private void keys(Node<Key, Value> x, Queue<Key> queue, Key lo, Key hi)
    {
        if (x == null) return;
        int cmplo = lo.compareTo(x.key);
        int cmphi = hi.compareTo(x.key);
        if (cmplo < 0) keys(x.left, queue, lo, hi);
        if (cmplo <= 0 && cmphi >= 0) queue.add(x.key);
        if (cmphi > 0) keys(x.right, queue, lo, hi);
    }

    public Key ceiling(Key key) {
        //SKIP, UNNEEDED
        return null;
    }
    public Node getRoot() {
        return root;
    }

    public boolean contains(Key key) {
        return get(key) != null;
    }

    public boolean isEmpty() {
        return root == null;
    }

    public void deleteMax() {
        if (root == null) {
            throw new NoSuchElementException();
        }
        root = deleteMax(root);
    }

    private Node<Key, Value> deleteMax(Node x) {
        if (x.right == null) return x.left;
        x.right = deleteMax(x.right);
        x.N = size(x.left) + size(x.right) + 1;
        return x;
    }

    public int size(Key lo, Key hi) {
        return size(root, lo, hi);
    }

    private int size(Node<Key, Value> x, Key lo, Key hi) {
        if (x == null) {
            return 0;
        }

        int cmplo = lo.compareTo(x.key);
        int cmphi = hi.compareTo(x.key);

        if (cmplo <= 0 && cmphi >= 0) {
            return 1 + size(x.left, lo, hi) + size(x.right, lo, hi);
        } else if (cmplo > 0) {
            return size(x.right, lo, hi);
        } else {
            return size(x.left, lo, hi);
        }
    }


    public void putFast(Key key, Value val) {
        if (key == null) {
            throw new IllegalArgumentException();
        }

        Node<Key, Value> newNode = new Node<>(key, val, 1); // Create a new node
        if (root == null) {
            root = newNode; // If the tree is empty, the new node becomes the root
            return;
        }

        Node<Key, Value> current = root; // Start from the root
        Node<Key, Value> parent = null;

        while (current != null) {
            parent = current;
            int cmp = key.compareTo(current.key);

            if (cmp < 0) {
                // Traverse to the left subtree
                current = current.left;
            } else if (cmp > 0) {
                // Traverse to the right subtree
                current = current.right;
            } else {
                // If the key already exists, update the value and return
                current.val = val;
                return;
            }
        }

        // Insert the new node as a child of the last visited parent
        int cmp = key.compareTo(parent.key);
        if (cmp < 0) {
            parent.left = newNode;
        } else {
            parent.right = newNode;
        }

        // Update the size for all nodes along the path
        current = root; // Start from the root
        while (current != null) {
            current.N = size(current.left) + size(current.right) + 1;
            int cmpUpdate = key.compareTo(current.key);
            if (cmpUpdate < 0) {
                current = current.left;
            } else if (cmpUpdate > 0) {
                current = current.right;
            } else {
                break; // Stop when the inserted node is already accounted for
            }
        }
    }


    public Value getFast(Key key) {
        if (key == null) {
            throw new IllegalArgumentException();
        }

        Node<Key, Value> current = root; // Start from the root

        while (current != null) {
            int cmp = key.compareTo(current.key);

            if (cmp < 0) {
                // Traverse left if the key is smaller
                current = current.left;
            } else if (cmp > 0) {
                // Traverse right if the key is larger
                current = current.right;
            } else {
                // If found, return the value
                return current.val;
            }
        }

        // Key not found
        return null;
    }

    private void collectKeysAndValues(Node<Key, Value> x, Queue<Key> keys, Queue<Value> values) {
        if (x == null) {
            return;
        }
        collectKeysAndValues(x.left, keys, values); // Process left subtree
        keys.add(x.key);                             // Add current key
        values.add(x.val);                           // Add current value
        collectKeysAndValues(x.right, keys, values); // Process right subtree
    }

    private Node<Key, Value> buildBalancedTree(Key[] keys, Value[] values, int lo, int hi) {
        if (lo > hi) {
            return null; // Base case: No elements in this range
        }
        int mid = lo + (hi - lo) / 2; // Choose the middle element as root
        Node<Key, Value> x = new Node<>(keys[mid], values[mid], 1);
        x.left = buildBalancedTree(keys, values, lo, mid - 1); // Recursively build left subtree
        x.right = buildBalancedTree(keys, values, mid + 1, hi); // Recursively build right subtree
        x.N = size(x.left) + size(x.right) + 1; // Update size of the tree
        return x;
    }

    public void balance() {
        if (root == null) {
            return; // Nothing to balance if the tree is empty
        }

        Queue<Key> keys = new LinkedList<>();
        Queue<Value> values = new LinkedList<>();

        // Collect keys and values in sorted order
        collectKeysAndValues(root, keys, values);

        // Convert queues to arrays for indexed access
        Key[] keyArray = (Key[]) keys.toArray(new Comparable[0]);
        Value[] valueArray = (Value[]) values.toArray(new Object[0]);


        // Rebuild the balanced tree and assign it to root
        root = buildBalancedTree(keyArray, valueArray, 0, keyArray.length - 1);
    }


    public String displayLevel(Key key) {
        if (key == null) {
            throw new IllegalArgumentException();
        }

        // Step 1: Find the node with the specified key
        Node<Key, Value> startNode = findNode(root, key);
        if (startNode == null) {
            return "empty";
        }

        // Step 2: Perform level-order traversal
        StringBuilder result = new StringBuilder();
        Queue<Node<Key, Value>> queue = new LinkedList<>();
        queue.add(startNode); // Add the root of the subtree

        while (!queue.isEmpty()) {
            Node<Key, Value> current = queue.poll(); // Dequeue the front of the queue
            result.append(current.val).append(" ");  // Append the value to the result

            if (current.left != null) {
                queue.add(current.left); // Enqueue the left child
            }
            if (current.right != null) {
                queue.add(current.right); // Enqueue the right child
            }
        }

        return result.toString().trim(); // Remove trailing space and return
    }

    // Helper method to find a node with the specified key
    private Node<Key, Value> findNode(Node<Key, Value> x, Key key) {
        while (x != null) {
            int cmp = key.compareTo(x.key);
            if (cmp < 0) {
                x = x.left; // Move to the left subtree
            } else if (cmp > 0) {
                x = x.right; // Move to the right subtree
            } else {
                return x; // Found the node
            }
        }
        return null; // Node not found
    }


    /**
     * entry point for testing.
     * 
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        BST<Integer, String> bst = new CompletedBST();
        
        bst.put(10, "TEN");
        bst.put(3, "THREE");
        bst.put(1, "ONE");
        bst.put(5, "FIVE");
        bst.put(2, "TWO");
        bst.put(7, "SEVEN");
        
        System.out.println("Before balance:");
        System.out.println(bst.displayLevel(10)); //root
        
        System.out.println("After balance:");
        bst.balance();
        System.out.println(bst.displayLevel(3)); //root
    }
}