package rbTree;

import util.ADTOverflowException;
import util.ADTUnderflowException;

/**
 * Estrutura representa uma arvore PV implementada de forma nao recursiva. Os
 * metodos podem ser recursivos (recomendado) e nao precisam ser definidos na
 * classe RBNode. A estrutura guarda apenas a raiz. A estrutura deve ter um
 * tamanho inicial, um tamanho maximo que pode crescer e um FATOR de
 * crescimento. Quando instanciada a estrutura tem um tamanho inicial. Quando
 * ela vai crescendo e enche, ela aumenta de tamanho de acordo com o fator de
 * crescimento desde que nao atinja ou ultrapasse o tamanho maximo. A partir dai
 * a estrutura nao pode mais crescer e pode ficar cheia. Use as constantes
 * definidas em eda.util.Constantes para inicializar os valores internos de sua
 * estrutura. Faca protected qualquer outro metodo auxiliar. Alguns deles
 * necessarios estao ja com as assianturas abaixo. Entretanto, voce pode
 * precisar implementar outros para facilitar sua vida, ou ate mesmo modificar a
 * classe RBNode. Concentre-se nas rotacoes, elas sao de extrema importancia
 * para a balancear a arvore. Tente fazer de forma parecida com a splay tree
 * mostrada em sala.
 */
public class RBTreeImpl<K extends Comparable<K>, V extends Comparable<V>>
		implements RBTree<K, V> {

	RBNode<K, V> raiz = new RBNode<K, V>();

	// verify all properties
	protected boolean checkProperties() {
		return this.checkProperty1() && this.checkProperty2()
				&& this.checkProperty3() && this.checkProperty4()
				&& this.checkProperty5();
	}

	// all nodes (different of NIL) are black or red.
	private boolean checkProperty1() {
		boolean resp = true;
		if (!isEmpty()) {
			resp = (this.getCor().equals(PVCores.PRETO) || this.getCor()
					.equals(PVCores.VERMELHO))
					&& this.getFilhoEsquerdo().checkProperty1()
					&& this.getFilhoDireito().checkProperty1();
		}
		return resp;
	}

	// all leaves NIL are black
	private boolean checkProperty2() {
		boolean resp = true;
		if (isEmpty()) {
			resp = this.getCor().equals(PVCores.PRETO);
		} else {
			resp = this.getFilhoEsquerdo().checkProperty2()
					&& this.getFilhoDireito().checkProperty2();
		}
		return resp;
	}

	// the root is black
	private boolean checkProperty3() {
		boolean resp = true;
		if (!isEmpty()) {
			resp = this.getPai() == null && this.getCor().equals(PVCores.PRETO);
		}
		return resp;
	}

	// all red node has black children
	private boolean checkProperty4() {
		boolean resp = true;
		if (!isEmpty()) {
			if (this.getCor().equals(PVCores.VERMELHO)) {
				resp = this.getFilhoEsquerdo().getCor().equals(PVCores.PRETO)
						&& this.getFilhoDireito().getCor()
								.equals(PVCores.PRETO)
						&& this.getFilhoEsquerdo().checkProperty4()
						&& this.getFilhoDireito().checkProperty4();
			} else {
				resp = this.getFilhoEsquerdo().checkProperty4()
						&& this.getFilhoDireito().checkProperty4();
			}
		}
		return resp;
	}

	// same black-height for all paths of a node to a leaf
	private boolean checkProperty5() {
		boolean resp = true;
		if (!isEmpty()) {
			resp = this.getFilhoEsquerdo().blackHeight() == this
					.getFilhoDireito().blackHeight();
		}
		return resp;
	}

	protected void fixUp() {
		// TODO
	}

	protected RBNode<K, V> leftRotation() {
		RBTreeImpl<K, V> root = this;
		RBTreeImpl<K, V> pivot = this.getFilhoDireito();
		RBTreeImpl<K, V> paiRoot = root.getPai();

		RBTreeImpl<K, V> novoFilhoEsquerdaRoot = new RBTreeImpl<K, V>();
		novoFilhoEsquerdaRoot.setDadoSatelite(root.getDadoSatelite());
		novoFilhoEsquerdaRoot.setChave(root.getChave());
		novoFilhoEsquerdaRoot.setCor(root.getCor());
		novoFilhoEsquerdaRoot.setFilhoEsquerdo(root.getFilhoEsquerdo());
		novoFilhoEsquerdaRoot.getFilhoEsquerdo().setPai(novoFilhoEsquerdaRoot);
		novoFilhoEsquerdaRoot.setFilhoDireito(pivot.getFilhoEsquerdo());
		novoFilhoEsquerdaRoot.getFilhoDireito().setPai(novoFilhoEsquerdaRoot);
		novoFilhoEsquerdaRoot.setPai(root);
		root.setFilhoDireito(novoFilhoEsquerdaRoot);
		root.setDadoSatelite(pivot.getDadoSatelite());
		root.setChave(pivot.getChave());
		root.setCor(pivot.getCor());
		// se for o root da arvore entao no pode ser vermelho
		if (root.getPai() == null && root.getCor().equals(PVCores.VERMELHO)) {
			root.setCor(PVCores.PRETO);
		}
		root.setFilhoDireito(pivot.getFilhoDireito());
		root.getFilhoDireito().setPai(root);
		root.setPai(paiRoot);
		pivot.setPai(root.getPai());
		return raiz;
	}

	protected RBNode<K, V> rightRotation() {
		RBTreeImpl<K, V> root = this;
		RBTreeImpl<K, V> pivot = this.getFilhoEsquerdo();
		RBTreeImpl<K, V> paiRoot = root.getPai();

		RBTreeImpl<K, V> novoFilhoDireitaRoot = new RBTreeImpl<K, V>();
		novoFilhoDireitaRoot.setDadoSatelite(root.getDadoSatelite());
		novoFilhoDireitaRoot.setChave(root.getChave());
		novoFilhoDireitaRoot.setCor(root.getCor());
		novoFilhoDireitaRoot.setFilhoDireito(root.getFilhoDireito());
		novoFilhoDireitaRoot.getFilhoDireito().setPai(novoFilhoDireitaRoot);
		novoFilhoDireitaRoot.setFilhoEsquerdo(pivot.getFilhoDireito());
		novoFilhoDireitaRoot.getFilhoEsquerdo().setPai(novoFilhoDireitaRoot);
		novoFilhoDireitaRoot.setPai(root);
		root.setFilhoDireito(novoFilhoDireitaRoot);
		root.setDadoSatelite(pivot.getDadoSatelite());
		root.setChave(pivot.getChave());
		root.setCor(pivot.getCor());
		// se for o root da arvore entao no pode ser vermelho
		if (root.getPai() == null && root.getCor().equals(PVCores.VERMELHO)) {
			root.setCor(PVCores.PRETO);
		}
		root.setFilhoEsquerdo(pivot.getFilhoEsquerdo());
		root.getFilhoEsquerdo().setPai(root);
		root.setPai(paiRoot);
		pivot.setPai(root.getPai());

		return raiz;
	}

	@Override
	public int height() {
		int result = -1;
		if (!isEmpty()) {
			if (isLeaf()) {
				result = 0;
			} else {
				result = 1 + Math.max(getFilhoEsquerdo().height(),
						getFilhoDireito().height());
			}
		}
		return result;
	}

	@Override
	public int blackHeight() {
		int result = 0;
		if (!isEmpty()) {
			if (this.getCor().equals(PVCores.PRETO)) {
				result = 1 + this.getFilhoEsquerdo().blackHeight();
			} else {
				int rightBlackHeight = this.getFilhoDireito().blackHeight();
				int leftBlackHeight = this.getFilhoEsquerdo().blackHeight();
				if (rightBlackHeight != leftBlackHeight) {
					result = -1;
				} else {
					result = this.getFilhoEsquerdo().blackHeight();
				}
			}
		}
		return result;
	}

	@Override
	public V search(K key) {
		V resp = null;
		if (!isEmpty()) {
			if (key != null) {
				if (key.equals(this.getChave())) {
					resp = this.getDadoSatelite();
				} else {
					if (key.compareTo(this.getChave()) < 0) {
						resp = this.getFilhoEsquerdo().search(key);
					} else {
						resp = this.getFilhoDireito().search(key);
					}
				}
			}
		}
		return resp;
	}

	@Override
	public void insert(K key, V value) throws ADTOverflowException {
		// TODO Auto-generated method stub

	}

	@Override
	public V maximum() {
		return this.search(this.maximumKey());
	}

	@Override
	public V minimum() {
		return this.search(this.minimumKey());
	}

	@Override
	public K maximumKey() {
		K maxChave = this.getChave();
		if (!isEmpty()) {
			K maxChaveFilhoDireita = this.getFilhoDireito().maximumKey();
			if (maxChaveFilhoDireita != null
					&& maxChaveFilhoDireita.compareTo(maxChave) > 0) {
				maxChave = maxChaveFilhoDireita;
			}
		}
		return maxChave;
	}

	@Override
	public K minimumKey() {
		K minChave = this.getChave();
		if (!isEmpty()) {
			K minKeyFilhoEsquerda = this.getFilhoEsquerdo().minimumKey();
			if (minKeyFilhoEsquerda != null
					&& minKeyFilhoEsquerda.compareTo(minChave) < 0) {
				minChave = minKeyFilhoEsquerda;
			}
		}
		return minChave;
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
		// StringBuilder stringPre = new StringBuilder();
		// if (!isEmpty()) {
		// if (this.getCor().equals(PVCores.PRETO)) {
		// stringPre.append("<" + this.getChave() + "> ");
		// } else {
		// stringPre.append(this.getChave() + " ");
		// }
		// stringPre.append(this.getFilhoEsquerdo().preOrder());
		// stringPre.append(this.getFilhoDireito().preOrder());
		// }
		// return stringPre;

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

	public boolean isEmpty() {
		return raiz.isEmpty();
	}

	private boolean isLeaf() {
		return raiz.isLeaf();

	}

	public String toString() {
		return raiz.toString();
	}

	private RBTreeImpl<K, V> getFilhoDireito() {
		return raiz.getFilhoDireito();

	}

	private RBTreeImpl<K, V> getFilhoEsquerdo() {
		return raiz.getFilhoEsquerdo();
	}

	private K getChave() {
		return raiz.getChave();
	}

	private V getDadoSatelite() {
		return raiz.getDadoSatelite();
	}

	private RBTreeImpl<K, V> getPai() {
		return raiz.getPai();
	}

	public RBNode<K, V> getRaiz() {
		return raiz;
	}

	private PVCores getCor() {
		return raiz.getCor();
	}

	public void setRaiz(RBNode<K, V> raiz) {
		this.raiz = raiz;
	}

	public void setChave(K novaChave) {
		raiz.setChave(novaChave);
	}

	public void setCor(PVCores novaCor) {
		raiz.setCor(novaCor);
	}

	public void setDadoSatelite(V novoDadoSatelite) {
		raiz.setDadoSatelite(novoDadoSatelite);
	}

	public void setFilhoDireito(RBTreeImpl<K, V> novoFilhoDireito) {
		raiz.setFilhoDireito(novoFilhoDireito);
	}

	public void setFilhoEsquerdo(RBTreeImpl<K, V> novoFilhoEsquerdo) {
		raiz.setFilhoEsquerdo(novoFilhoEsquerdo);
	}

	public void setPai(RBTreeImpl<K, V> novoPai) {
		raiz.setPai(novoPai);
	}

}