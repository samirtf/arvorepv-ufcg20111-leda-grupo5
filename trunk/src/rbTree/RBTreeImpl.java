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
public class RBTreeImpl<K extends Comparable<K>, V extends Comparable<V>>
		implements RBTree<K, V> {
	
	private K chave;
	private V dadoSatelite;
	private RBTreeImplementada<K, V> filhoEsquerdo;
	private RBTreeImplementada<K, V> filhoDireito;
	private RBTreeImplementada<K, V> pai;
	private Cores color = Cores.PRETO;

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
		//TODO
		return null;
	}

	protected RBNode<K,V> rightRotation(){
		//TODO
		return null;
	}

	@Override
	public int height() {
		// TODO Auto-generated method stub
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

}

enum Color {
	RED(1), BLACK(2)
}