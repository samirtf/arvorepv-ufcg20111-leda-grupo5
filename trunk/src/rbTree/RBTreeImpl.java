package rbTree;

import util.ADTOverflowException;
import util.ADTUnderflowException;

/**
 * Estrutura representa uma arvore PV implementada de forma nao recursiva. 
 * Os metodos podem ser recursivos (recomendado) e nao precisam ser definidos
 * na classe RBNode. A estrutura guarda apenas a raiz. 
 * A estrutura deve ter um tamanho inicial, um tamanho maximo que pode 
 * crescer e um FATOR de crescimento. Quando instanciada a estrutura tem um tamanho 
 * inicial. Quando ela vai crescendo e enche, ela aumenta de tamanho de acordo com o 
 * fator de crescimento desde que nao atinja ou ultrapasse o tamanho  maximo. A partir 
 * dai a estrutura nao pode mais crescer e pode ficar cheia. Use as constantes 
 * definidas em eda.util.Constantes para inicializar os valores internos de sua 
 * estrutura. Faca protected qualquer outro metodo auxiliar. Alguns deles necessarios
 * estao ja com as assianturas abaixo. Entretanto, voce pode precisar implementar
 * outros para facilitar sua vida, ou ate mesmo modificar a classe RBNode. 
 * Concentre-se nas rotacoes, elas sao de extrema
 * importancia para a balancear a arvore. Tente fazer de forma parecida com a splay 
 * tree mostrada em sala.
 */
public class RBTreeImpl<K extends Comparable<K>, V extends Comparable<V>> implements RBTree<K, V> {
	
	RBNode<K, V> raiz = new RBNode<K, V>();

	//verify all properties
	protected boolean checkProperties(){
		return 	this.checkProperty1()
				&& this.checkProperty2()
				&& this.checkProperty3()
				&& this.checkProperty4()
				&& this.checkProperty5();
	}
	
	//all nodes (different of NIL) are black or red. 
	private boolean checkProperty1(){
		//TODO
		boolean resp = true;

		return resp;
	}
	
	//all leaves NIL are black
	private boolean checkProperty2(){
		//TODO
		boolean resp = true;

		return resp;
	}
	
	//the root is black
	private boolean checkProperty3(){
		//TODO
		boolean resp = true;
		
		return resp;
	}
	
	//all red node has black children
	private boolean checkProperty4(){
		//TODO
		boolean resp = true;

		return resp;
	}
	//same black-height for all paths of a node to a leaf
	private boolean checkProperty5(){
		//TODO
		boolean resp = true;
		
		return resp;
	}
	
	protected void fixUp(){
		//TODO
	}

	protected RBNode<K,V> leftRotation(){
//		RBTreeImpl<K,V> raiz = this;
//		RBTreeImpl<K,V> pivo = this.right;
//		RBTreeImpl<K,V> paiRoot = raiz.parent;
//		
//		RBTreeImplementada<K,V> novoFilhoEsquerdaRoot = new RBTreeImplementada<K,V>();
//		novoFilhoEsquerdaRoot.setSatteliteData(root.getSatteliteData());
//		novoFilhoEsquerdaRoot.setKey(root.getKey());
//		novoFilhoEsquerdaRoot.setColor(root.getColor());
//		novoFilhoEsquerdaRoot.setLeft(root.getLeft());
//		novoFilhoEsquerdaRoot.left.parent = novoFilhoEsquerdaRoot;
//		novoFilhoEsquerdaRoot.setRight(pivot.getLeft());
//		novoFilhoEsquerdaRoot.right.parent = novoFilhoEsquerdaRoot;
//		novoFilhoEsquerdaRoot.setParent(root);
//		root.setLeft(novoFilhoEsquerdaRoot);
//		root.setSatteliteData(pivot.getSatteliteData());
//		root.setKey(pivot.getKey());
//		root.setColor(pivot.getColor());
//		//se for o root da arvore entao no pode ser vermelho
//		if(root.parent == null && root.color.equals(Color.RED)){
//			root.setColor(Color.BLACK);
//		}
//		root.setRight(pivot.getRight());
//		root.right.setParent(root);
//		root.setParent(paiRoot);
//		pivot.parent = root.parent;
		return null;
	}

	protected RBNode<K,V> rightRotation(){
		//TODO
		return null;
	}

	@Override
	public int height() {
//		int result = -1;
//		if (!raiz.isEmpty()) {
//			if (isLeaf()) {
//				result = 0;
//			} else {
//				result = 1 + greater(left.height(), right.height());
//			}
//		}
//		return result;
		return 0;
	}

	@Override
	public int blackHeight() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public V search(K key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void insert(K key, V value) throws ADTOverflowException {
		// TODO Auto-generated method stub

	}

	@Override
	public V maximum() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public V minimum() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public K maximumKey() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public K minimumKey() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RBNode<K, V> successor(K key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RBNode<K, V> predecessor(K key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(K key) throws ADTUnderflowException {
		// TODO Auto-generated method stub

	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public V[] preOrder() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public V[] order() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public V[] postOrder() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public boolean isEmpyt() {		
		return raiz.isEmpty();
	}
	
	public String toString() {
		return raiz.toString();
	}

}