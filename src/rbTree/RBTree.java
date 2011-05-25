package rbTree;

import eda.util.ADTOverflowException;
import eda.util.ADTUnderflowException;


/**
 * A node of a RBTree. It must have fields for keeping a key of type K (comparable)
 * and a value of type V(also comparable). It also has a parent, a left child and a 
 * right child and a flag to determine if the node is black or not (if it is not
 * black, then it is assumed that the node is red).  
 */
class RBNode<K extends Comparable<K>, V extends Comparable<V>>{
	
	public boolean isEmpty(){
		//TODO implement the method
		return false;
	}
	
	public boolean isLeaf(){
		//TODO implement the method
		return false;
	}
	
	/**
	 * This method must return a string containing the key of a RB node. If the node is 
	 * black it simply returns the key (as String). Otherwise, it returns the key 
	 * between less and greater (that is, <key>). 
	 */
	@Override
	public String toString(){
		//TODO
		return null;
	}
}
/**
 * Interface representando uma arvore PV generica, que guarda valores do 
 * tipo V associados a chaves do tipo K em cada no.
 *
 * @param <K>
 * @param <V>
 */
public interface RBTree<K extends Comparable<K>,V extends Comparable<V>> {
	
	
	/**
	 * Returns the height of a RB tree. Nodes NIL do not influenciate the 
	 * height. 
	 */
	public int height();
	
	/**
	 * Returns the black height of a RB tree.
	 */
	public int blackHeight();
	
	/**
	 * Searchs the value associated to a given key. Returns null if the key is not
	 * in the tree.
	 */
	public V search(K key);
	
	/**
	 * Inserts a value associated with a given key in a RB tree. 
	 */
	public void insert(K key, V value) throws ADTOverflowException;
	
	/**
	 * Returns the maximum value (associated to the greatest key) of a RB tree. 
	 */
	public V maximum();

	/**
	 * Returns the minimum value (associated to the least key) of a RB tree. 
	 */
	public V minimum();
	
	/**
	 * Returns the maximum key of a tree or null if the tree is empty.
	 */
	public K maximumKey();

	/**
	 * Returns the minimum key of a tree or null if the tree is empty.
	 */
	public K minimumKey();
	
	/**
	 * Returns the node that is the the successor (according to the Cormen's book)
	 * of a node containing the given key. If the key is not in the tree
	 * returns null; 
	 */
	public RBNode<K,V> successor(K key);
	
	
	/**
	 * Returns the node that is the the predecessor (according to the Cormen's book)
	 * of a node containing the given key. If the key is not in the tree
	 * returns null; 
	 */
	public RBNode<K,V> predecessor(K key);
	
	/**
	 * Removes the node containing a given key.
	 * @param key
	 */
	public void delete(K key) throws ADTUnderflowException;
	
	/**
	 * Returns how many elements were put into the tree. 
	 */
	public int size();
	
	/**
	 * Traverses the tree in order and returns an array containing the values 
	 * in that order.
	 */
	public V[] preOrder();

	/**
	 * Traverses the tree in order and returns an array containing the values 
	 * in that order.
	 */
	public V[] order();

	/**
	 * Traverses the tree in post-order and returns an array containing the values 
	 * in that order.
	 */
	public V[] postOrder();
	
}