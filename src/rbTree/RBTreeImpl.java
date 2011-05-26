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

	private RBNode<K, V> raiz = new RBNode<K, V>();
	private RBNode<K, V> axiVazio = new RBNode<K,V>();

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
		if (this.getCor().equals(PVCores.VERMELHO)
				&& this.getPai().getCor().equals(PVCores.VERMELHO)) {/*
																	 * SE EU E
																	 * MEU SOMOS
																	 * VERMELHOS
																	 * -
																	 * "PERNA ESTICADA"
																	 */
			if (this.getPai() == this.getPai().getPai().getFilhoEsquerdo()) { /*
																			 * SE
																			 * MEU
																			 * PAI
																			 * FOR
																			 * FILHO
																			 * ESQUERDO
																			 * DO
																			 * MEU
																			 * AVO
																			 */
				if (this.getPai().getPai().getFilhoDireito().getCor()
						.equals(PVCores.VERMELHO)) { // E MEU TIO FOR FOR
														// VERMELHO
					this.getPai().mudaDeCor();
					this.getPai().getPai().mudaDeCor();
					this.getPai().getPai().getFilhoDireito().mudaDeCor();
					// SE O AVO FOR RAIZ DEVE SER PRETO
					if (this.getPai().getPai().getPai() == null) {// PAI DO AVO
						// NULL -
						// RAIZ
						this.getPai().getPai().setCor(PVCores.PRETO);// ATUALIZA
						// PARA
						// PRETO
					}

				} else if (((this == this.getPai().getFilhoDireito()) && (this
						.getPai() == this.getPai().getPai().getFilhoEsquerdo()))// SE
						// EU
						// SOU
						// FILHO
						// DIREITO
						// DO
						// MEU PAI E MEU PAI FOR
						// FILHO ESQUERDO DO MEU AVO
						// OU
						|| ((this == this.getPai().getFilhoEsquerdo()) && (this
								.getPai() == this.getPai().getPai()
								.getFilhoDireito()))) {// EU SOU FILHO
					// ESQUERDO DO MEU
					// PAI E MEU PAI EH
					// FILHO DIREITO DO
					// MEU AVO - JOELHO
					if ((this == this.getPai().getFilhoDireito())
							&& (this.getPai() == this.getPai().getPai()
									.getFilhoEsquerdo())) {// SE EU SOU
						// FILHO DIREITO
						// DO MEU PAI E
						// MEU PAI EH
						// FILHO
						// ESQUERDO DO
						// MEU AVO
						this.getPai().leftRotation();// ROTACIONA MEU PAI A
						// ESQUERDA
						// EU ERA O PIVO QUE TEVE O FILHO MODIFICADO PARA O
						// AVO NA ROTACAO
						this.getPai().getFilhoEsquerdo().getFilhoEsquerdo()
								.fixUp();
					}
					if ((this == this.getPai().getFilhoEsquerdo())
							&& (this.getPai() == this.getPai().getPai()
									.getFilhoDireito())) {// SE EU SOU FILHO
						// A ESQUERDA DO
						// MEU PAI E MEU
						// PAI EH FILHO
						// A DIREITA DO
						// MEU AVO
						this.getPai().rightRotation();// ROTACIONA MEU PAI A
						// DIREITA
						// EU ERA O PIVO QUE TEVE O FILHO MODIFICADO PARA O
						// AVO NA ROTACAO
						this.getPai().getPai().getFilhoDireito().fixUp();
					}

				} else if (((this == this.getPai().getFilhoEsquerdo()) && (this
						.getPai() == this.getPai().getPai().getFilhoEsquerdo()))
						|| ((this == this.getPai().getFilhoDireito()) && (this
								.getPai() == this.getPai().getPai()
								.getFilhoDireito()))) {// CASO 3 TIO PRETO E
					// FILHO, PAI E AVO
					// FORMAM LINHA RETA
					if (((this == this.getPai().getFilhoEsquerdo()) && (this
							.getPai() == this.getPai().getPai()
							.getFilhoEsquerdo()))) {
						this.getPai().getPai().mudaDeCor();
						this.getPai().mudaDeCor();
						this.getPai().getPai().rightRotation();

					}
					if (((this == this.getPai().getFilhoDireito()) && (this
							.getPai() == this.getPai().getPai()
							.getFilhoDireito()))) {
						this.getPai().getPai().mudaDeCor();
						this.getPai().mudaDeCor();
						this.getPai().getPai().leftRotation();
					}
				}
			} else {
				if (this.getPai().getPai().getFilhoEsquerdo().getCor()
						.equals(PVCores.VERMELHO)) { // ESPELHO DO CASO 1 - TIO
					// VERMELHO SENDO FILHO
					// A ESQUERDA DO AVO
					this.getPai().mudaDeCor();
					this.getPai().getPai().mudaDeCor();
					// se o avor for raiz deve deixar a cor preta
					if (this.getPai().getPai().getPai() == null) {
						this.getPai().getPai().setCor(PVCores.PRETO);
					}
					this.getPai().getPai().getFilhoEsquerdo().mudaDeCor();
				} else if (((this == this.getPai().getFilhoDireito()) && (this
						.getPai() == this.getPai().getPai().getFilhoEsquerdo()))
						|| ((this == this.getPai().getFilhoEsquerdo()) && (this
								.getPai() == this.getPai().getPai()
								.getFilhoDireito()))) {// CASO 2 TIO PRETO E
					// FILHO PAI E AVO
					// FORMAM JOELHO
					if ((this == this.getPai().getFilhoDireito())
							&& (this.getPai() == this.getPai().getPai()
									.getFilhoEsquerdo())) {
						this.getPai().leftRotation();
						this.getPai().getFilhoEsquerdo().fixUp();
					}
					if ((this == this.getPai().getFilhoEsquerdo())
							&& (this.getPai() == this.getPai().getPai()
									.getFilhoDireito())) {
						this.getPai().rightRotation();
						this.getPai().getFilhoDireito().fixUp();
					}
					// como recai sempre no caso 3 chama recursivamente o
					// metodo
					// fixUp();
				} else if (((this == this.getPai().getFilhoEsquerdo()) && (this
						.getPai() == this.getPai().getPai().getFilhoEsquerdo()))
						|| ((this == this.getPai().getFilhoDireito()) && (this
								.getPai() == this.getPai().getPai()
								.getFilhoDireito()))) {// CASO 3 TIO PRETO E
					// FILHO, PAI E AVO
					// FORMAM LINHA RETA
					if (((this == this.getPai().getFilhoEsquerdo()) && (this
							.getPai() == this.getPai().getPai()
							.getFilhoEsquerdo()))) {
						this.getPai().getPai().mudaDeCor();
						this.getPai().mudaDeCor();
						this.getPai().getPai().mudaDeCor();

					}
					if (((this == this.getPai().getFilhoDireito()) && (this
							.getPai() == this.getPai().getPai()
							.getFilhoDireito()))) {
						// as cores sao modificadas antes porque o avo eh
						// rotacionado
						this.getPai().getPai().mudaDeCor();
						this.getPai().mudaDeCor();
						this.getPai().getPai().leftRotation();

					}
				}
			}
		}
		// quando o fixUp terminar os tres casos ele deve ser apicado ao avo
		// para consertar
		// problemas que eventualmente surjam por causa da recursao. E o
		// fixUp deve ser aplicado
		// ao avo se ele nao for o root da arvore.
		if (this.getPai() != null) {
			if (this.getPai().getPai() != null) {
				if (this.getPai().getPai().getPai() != null) {
					this.getPai().getPai().fixUp();
				}
			}
		}
	}

	private void mudaDeCor() {
		if (this.getCor().equals(PVCores.PRETO)) {
			this.setCor(PVCores.VERMELHO);
		} else {
			this.setCor(PVCores.PRETO);
		}
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
	
	public RBTreeImpl<K, V> busca(K key) {
		RBTreeImpl<K, V> resp = null;
		if (!isEmpty()) {
			if (key != null) {
				if (key.equals(this.getChave())) {
					resp = this;
				} else {
					if (key.compareTo(this.getChave()) < 0) {
						resp = this.getFilhoEsquerdo().busca(key);
					} else {
						resp = this.getFilhoDireito().busca(key);
					}
				}
			}
		}
		return resp;
	}

	@Override
	public void insert(K key, V value) throws ADTOverflowException {
		if (isEmpty()) {
			this.setChave(key);
			this.setDadoSatelite(value);
			if (this.getPai() != null) {
				this.setCor(PVCores.VERMELHO);
			}
			this.setFilhoEsquerdo(new RBTreeImpl<K, V>());
			this.getFilhoEsquerdo().setPai(this);
			this.setFilhoDireito(new RBTreeImpl<K, V>());
			this.getFilhoDireito().setPai(this);
			fixUp();
		} else {
			if (!this.getChave().equals(key)) {
				if (key.compareTo(this.getChave()) < 0) {
					this.getFilhoEsquerdo().insert(key, value);
				} else {
					this.getFilhoDireito().insert(key, value);
				}
			}
		}

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
		RBTreeImpl<K, V> baseNode = busca(key);
		if (baseNode.isEmpty()) {
			return null;
		}
		RBTreeImpl<K, V> resp = null;
        if (!baseNode.isEmpty()) {
            if (!baseNode.getFilhoDireito().isEmpty()) {
                //o minimo filho direito
                resp = maisAEsquerda(baseNode.getFilhoDireito());
            } else {
                RBTreeImpl<K, V> baseParentNode = baseNode.getPai();
                while (!baseParentNode.equals(null) && baseNode.equals(baseParentNode.getFilhoDireito())) {
                    if (!baseParentNode.equals(axiVazio)) {
                        baseNode = baseParentNode;
                        baseParentNode = baseNode.getPai();
                    } else {
                        resp = null;
                    }
 
                }
                resp = baseParentNode;
            }
        } else {
            resp = null;
        }
        return resp.getNo();
	}
	
	/**
     * O metodo retorna o no mais a esquerda de um dado no.
     *
     * @param atual - arvore atual
     * @return a arvore mais a esquerda
     */
    private RBTreeImpl<K, V> maisAEsquerda(RBTreeImpl<K, V> atual) {
        RBTreeImpl<K, V> iterator = atual;
        while (!iterator.getFilhoEsquerdo().isEmpty()) {
            iterator = iterator.getFilhoEsquerdo();
        }
        return iterator;
    }
    
    /**
     * The method returns the most right node of a given node.
     *
     * @param current - node to start from
     * @return Most Right node
     */
    private RBTreeImpl<K, V> maisADireita(RBTreeImpl<K, V> atual) {
        RBTreeImpl<K, V> iterator = atual;
        while (!iterator.getFilhoDireito().isEmpty()) {
            iterator = iterator.getFilhoDireito();
        }
        return iterator;
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
		int resp = 0;
		if (!isEmpty()) {
			resp = 1 + this.getFilhoEsquerdo().size()
					+ this.getFilhoDireito().size();
		}
		return resp;
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

	public RBNode<K, V> getNo() {
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

	public static void main(String[] args) throws ADTOverflowException {
		RBTreeImpl<Integer, Integer> t = new RBTreeImpl<Integer, Integer>();
		// RBTreeImpl<Integer, Integer> t1 = new RBTreeImpl<Integer, Integer>();

		boolean ok = t.checkProperties();
		// t1.verifyProperties();
		// t.insert(1, 1);
		// t.insert(14, 14);
		// t.insert(25, 25);
		// t.print();

		for (int i = 21; i > -1; i--) {
			t.insert(i, i);
			// t1.insert1(i, i);
			ok = t.checkProperties();
			// t1.verifyProperties();
			System.out.println("T :: nos:" + t.size() + " - altura:"
					+ t.height() + "- RB(" + ok + ")");
			// System.out.println("T1 :: nos:" + t.size() + " - altura:" +
			// t.height() + "- RB(" + ok + ")");
		}

		// t.insert(50, 50);
		// t1.insert1(50, 50);

//		for (int i = 0; i < 50; i++) {
//			t.insert(i, i);
//			// t1.insert1(i, i);
//			// t.print();
//			ok = t.checkProperties();
//			// t1.verifyProperties();
//			System.out.println("T :: nos:" + t.size() + " - altura:"
//					+ t.height() + "- RB(" + ok + ")");
//			// System.out.println("T1 :: nos:" + t.size() + " - altura:" +
//			// t.height() + "- RB(" + ok + ")");
//		}
		
		
		System.out.println("ARVORE T");
		//for (int i = 0; i < 20; i++) {

			System.out.println("sucessor: " +t.successor(0));
			System.out.println("sucessor: " +t.successor(1));
			System.out.println("sucessor: " +t.successor(2));
			System.out.println("sucessor: " +t.successor(3));
			System.out.println("sucessor: " +t.successor(4));
			System.out.println("sucessor: " +t.successor(5));
			System.out.println("sucessor: " +t.successor(6));
			System.out.println("sucessor: " +t.successor(7));
			System.out.println("sucessor: " +t.successor(8));
			System.out.println("sucessor: " +t.successor(9));
			System.out.println("sucessor: " +t.successor(10));
			System.out.println("sucessor: " +t.successor(11));
			System.out.println("sucessor: " +t.successor(12));
			System.out.println("sucessor: " +t.successor(13));
			System.out.println("sucessor: " +t.successor(14));
			System.out.println("sucessor: " +t.successor(15));
			System.out.println("sucessor: " + t.successor(16));
			System.out.println("sucessor: " + t.successor(17));
			System.out.println("sucessor: " + t.successor(18));
			System.out.println("sucessor: " + t.successor(19));
			System.out.println("sucessor: " + t.successor(20));
			System.out.println("sucessor: " + t.successor(21));//NAO TEM
			
		//}
		
		
		t.toString();
		// System.out.println("ARVORE T1");
		// t1.print();

		System.out.println("----- PRE-ORDEM--------------");
		System.out.println(t.preOrder());

	}

}