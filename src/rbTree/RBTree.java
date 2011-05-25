package rbTree;

import rbTree.RBTreeImpl;
import util.ADTOverflowException;
import util.ADTUnderflowException;

/**
 * Um nó de uma RBTree. Deve ter campos para manter uma chave de tipo K
 * (comparável) e um valor de tipo V (também comparáveis). Ele também tem um
 * pai, um filho esquerdo e um direito da criança e uma bandeira para determinar
 * se o nó é preto ou não (se ele não é negro, então é assumido que o nó é
 * vermelho).
 */
class RBNode<K extends Comparable<K>, V extends Comparable<V>> {

	private K chave;
	private V dadoSatelite;
	private RBTreeImpl<K, V> filhoEsquerdo;
	private RBTreeImpl<K, V> filhoDireito;
	private RBTreeImpl<K, V> pai;
	private PVCores cor = PVCores.PRETO;

	/**
	 * Metodo para verificar se o no eh vazio ou nao
	 * 
	 * @return true ou false, dependendo se o filho esquerdo e direito sao
	 *         nulos.
	 */
	public boolean isEmpty() {
		return this.getFilhoEsquerdo() == null && this.getFilhoEsquerdo() == null;
	}

	/**
	 * Metodo para verificar se o no eh folha ou nao
	 * 
	 * @return true ou false, dependendo se o filho esquerdo e direito sao
	 *         vazios.
	 */
	public boolean isLeaf() {
		boolean resp = false;
		if (!isEmpty()) {
			resp = this.getFilhoEsquerdo().isEmpyt() && this.getFilhoDireito().isEmpyt();
		}
		return resp;
	}

	/**
	 * Este método deve retornar uma string contendo a chave de um nó de RB. Se
	 * o nó é preto ele simplesmente retorna a chave (como String). Caso
	 * contrário, ele retorna a chave entre menor e maior (ou seja, <key>).
	 */
	@Override
	public String toString() {
//		if (isEmpty()) {
//			System.out.println("<empty tree>");
//			return;
//		}
//		if (!this.getFilhoDireito().isEmpty()) {
//			right.print(indent + INDENT_STEP);
//		}
//		for (int i = 0; i < indent; i++)
//			System.out.print(" ");
//		if (this.color == Color.BLACK)
//			System.out.println(this.key);
//		else
//			System.out.println("<" + this.key + ">");
//		if (!left.isEmpty()) {
//			left.print(indent + INDENT_STEP);
//		}
		StringBuilder resp = new StringBuilder();
		if (!isEmpty()) {
			if (this.getCor().equals(PVCores.PRETO)) {
				resp.append("<" + this.getDadoSatelite() + "> ");
			} else {
				resp.append(this.getDadoSatelite() + " ");
			}
			resp.append(this.getFilhoEsquerdo().toString());
			resp.append(this.getFilhoDireito().toString());
		} else {
			resp.append("<arvore vazia>");
		}
		return resp.toString();
	}

	public K getChave() {
		return chave;
	}

	public PVCores getCor() {
		return cor;
	}

	public V getDadoSatelite() {
		return dadoSatelite;
	}

	public RBTreeImpl<K, V> getFilhoDireito() {
		return filhoDireito;
	}

	public RBTreeImpl<K, V> getFilhoEsquerdo() {
		return filhoEsquerdo;
	}

	public RBTreeImpl<K, V> getPai() {
		return pai;
	}

	public void setChave(K chave) {
		this.chave = chave;
	}

	public void setCor(PVCores cor) {
		this.cor = cor;
	}

	public void setDadoSatelite(V dadoSatelite) {
		this.dadoSatelite = dadoSatelite;
	}

	public void setFilhoDireito(RBTreeImpl<K, V> filhoDireito) {
		this.filhoDireito = filhoDireito;
	}

	public void setFilhoEsquerdo(RBTreeImpl<K, V> filhoEsquerdo) {
		this.filhoEsquerdo = filhoEsquerdo;
	}

	public void setPai(RBTreeImpl<K, V> pai) {
		this.pai = pai;
	}
}

/**
 * Interface representando uma arvore PV generica, que guarda valores do tipo V
 * associados a chaves do tipo K em cada no.
 * 
 * @param <K>
 * @param <V>
 */
public interface RBTree<K extends Comparable<K>, V extends Comparable<V>> {

	/**
	 * Retorna a altura de uma árvore RB. NIL nós não influenciam os altura.
	 */
	public int height();

	/**
	 * Retorna a altura de uma árvore negra RB.
	 */
	public int blackHeight();

	/**
	 * Procura o valor associado a uma determinada chave. Retorna nulo se a
	 * chave é não na árvore.
	 */
	public V search(K key);

	/**
	 * Insere um valor associado a uma determinada chave em uma árvore RB.
	 */
	public void insert(K key, V value) throws ADTOverflowException;

	/**
	 * Returns the maximum value (associated to the greatest key) of a RB tree.
	 */
	public V maximum();

	/**
	 * Retorna o valor máximo (associada à maior chave) de uma árvore RB.
	 */
	public V minimum();

	/**
	 * Retorna a chave máxima de uma árvore ou nulo, se a árvore está vazia.
	 */
	public K maximumKey();

	/**
	 * Retorna a chave mínima de uma árvore ou nulo, se a árvore está vazia.
	 */
	public K minimumKey();

	/**
	 * Returns the node that is the the successor (according to the Cormen's
	 * book) of a node containing the given key. If the key is not in the tree
	 * returns null;
	 */
	public RBNode<K, V> successor(K key);

	/**
	 * Retorna o nó que é o sucessor (de acordo com o Cormen está livro) de um
	 * nó que contém a chave dada. Se a chave não está na árvore retorna null;
	 */
	public RBNode<K, V> predecessor(K key);

	/**
	 * Remove o nó que contém uma determinada chave.
	 * 
	 * @param key
	 */
	public void delete(K key) throws ADTUnderflowException;

	/**
	 * Retorna quantos elementos foram colocados na árvore.
	 */
	public int size();

	/**
	 * percorre a árvore em ordem e retorna um array contendo os valores nessa
	 * ordem.
	 */
	public V[] preOrder();

	/**
	 * percorre a árvore em ordem e retorna um array contendo os valores nessa
	 * ordem.
	 */
	public V[] order();

	/**
	 * percorre a árvore em pós-ordem e retorna um array contendo os Os valores,
	 * nessa ordem.
	 */
	public V[] postOrder();

}