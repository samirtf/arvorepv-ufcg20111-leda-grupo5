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

	private RBNode<K, V> root = new RBNode<K, V>();

	// verify all properties
	protected boolean checkProperties() {
		return this.checkProperty1() && this.checkProperty2()
				&& this.checkProperty3() && this.checkProperty4()
				&& this.checkProperty5();
	}

	// all nodes (different of NIL) are black or red.
	private boolean checkProperty1() {
		return property1(root);
	}

	private boolean property1(RBNode<K, V> no) {
		boolean resp = true;
		if (!no.isEmpty()) {
			resp = (no.getCor() || !no.getCor())
					&& property1(no.getFilhoEsquerda())
					&& property1(no.getFilhoDireita());
		}
		return resp;
	}

	// all leaves NIL are black
	private boolean checkProperty2() {
		return property2(root);
	}

	private boolean property2(RBNode<K, V> no) {
		boolean resp = true;
		if (no.isEmpty()) {
			resp = no.getCor() == true;
		} else {
			resp = property2(no.getFilhoEsquerda())
					&& property2(no.getFilhoDireita());
		}
		return resp;
	}

	// the root is black
	private boolean checkProperty3() {
		return property3(root);
	}

	private boolean property3(RBNode<K, V> no) {
		boolean resp = true;
		if (!no.isEmpty()) {
			resp = no.getPai() == null && no.getCor() == true;
		}
		return resp;
	}

	// all red node has black children
	private boolean checkProperty4() {
		return property4(root);
	}

	private boolean property4(RBNode<K, V> no) {
		boolean resp = true;
		if (!no.isEmpty()) {
			if (!no.getCor()) {
				resp = no.getFilhoEsquerda().getCor() == true
						&& no.getFilhoDireita().getCor() == true
						&& property4(no.getFilhoEsquerda())
						&& property4(no.getFilhoDireita());
			} else {
				resp = property4(no.getFilhoEsquerda())
						&& property4(no.getFilhoDireita());
			}
		}
		return resp;
	}

	// same black-height for all paths of a node to a leaf
	private boolean checkProperty5() {
		return property5(root);
	}

	private boolean property5(RBNode<K, V> no) {
		boolean resp = true;
		if (!no.isEmpty()) {
			resp = blackHeight(no.getFilhoEsquerda()) == blackHeight(no
					.getFilhoDireita());
		}
		return resp;
	}

	protected void fixUp() {
		fixUp(root);
	}

	protected void fixUp(RBNode<K, V> no) {
		if (!no.getCor() && !no.getPai().getCor()) {// SE EU E MEU PAI SOMOS
			// VERMELHOS -
			// "PERNA ESTICADA"
			if (no.getPai() == no.getPai().getPai().getFilhoEsquerda()) { // SE
				// MEU
				// PAI
				// FOR
				// FILHO
				// ESQUERDO
				// DO
				// MEU
				// AVO
				if (!no.getPai().getPai().getFilhoDireita().getCor()) { // E
					// MEU
					// TIO
					// FOR
					// FOR
					// VERMELHO
					no.getPai().setCor();
					no.getPai().getPai().setCor();
					no.getPai().getPai().getFilhoDireita().setCor();
					// SE O AVO FOR RAIZ DEVE SER PRETO
					if (no.getPai().getPai().getPai() == null) {// PAI DO AVO
						// FOR NULL
						// ELE E
						// RAIZ
						setCorBlack(no.getPai().getPai());// ATUALIZA PARA PRETO
						// // >>>TESTAR SE DESSA
						// FORMA O NO E
						// REALMENTE PRETO<<<<
					}

				} else if (((no == no.getPai().getFilhoDireita()) && (no
						.getPai() == no.getPai().getPai().getFilhoEsquerda()))// SE
						// EU
						// SOU
						// FILHO
						// DIREITO
						// DO
						// MEU
						// PAI
						// E
						// MEU
						// PAI
						// FOR
						// FILHO ESQUERDO DO MEU AVO OU
						|| ((no == no.getPai().getFilhoEsquerda()) && (no
								.getPai() == no.getPai().getPai()
								.getFilhoDireita()))) {// EU SOU FILHO ESQUERDO
					// DO MEU PAI E MEU PAI
					// EH
					// FILHO DIREITO DO MEU AVO - JOELHO
					if ((no == no.getPai().getFilhoDireita())
							&& (no.getPai() == no.getPai().getPai()
									.getFilhoEsquerda())) {// SE EU SOU FILHO
						// DIREITO DO MEU
						// PAI E MEU PAI EH
						// FILHO ESQUERDO DO
						// MEU AVO
						leftRotation(no.getPai());// ROTACIONA MEU PAI A
						// ESQUERDA EU ERA O PIVO
						// QUE TEVE O FILHO
						// MODIFICADO PARA O AVO NA
						// ROTACAO
						fixUp(no.getPai().getFilhoEsquerda().getFilhoEsquerda());
					}
					if ((no == no.getPai().getFilhoEsquerda())
							&& (no.getPai() == no.getPai().getPai()
									.getFilhoDireita())) {// SE EU SOU FILHO A
						// ESQUERDA DO MEU
						// PAI E MEU PAI EH
						// FILHO A DIREITA
						// DO MEU AVO
						rightRotation(no.getPai());// ROTACIONA MEU PAI A
						// DIREITA EU ERA O PIVO
						// QUE TEVE O FILHO
						// MODIFICADO PARA O AVO
						// NA ROTACAO
						fixUp(no.getPai().getPai().getFilhoDireita());
					}

				} else if (((no == no.getPai().getFilhoEsquerda()) && (no
						.getPai() == no.getPai().getPai().getFilhoEsquerda()))
						|| ((no == no.getPai().getFilhoDireita()) && (no
								.getPai() == no.getPai().getPai()
								.getFilhoDireita()))) {// CASO 3 TIO PRETO E
					// FILHO, PAI E AVO
					// FORMAM LINHA RETA
					if (((no == no.getPai().getFilhoEsquerda()) && (no.getPai() == no
							.getPai().getPai().getFilhoEsquerda()))) {
						no.getPai().getPai().setCor();// Verificar a cor
						no.getPai().setCor(); // Verificar a cor
						rightRotation(no.getPai().getPai());

					}
					if (((no == no.getPai().getFilhoDireita()) && (no.getPai() == no
							.getPai().getPai().getFilhoDireita()))) {
						no.getPai().getPai().setCor();// Verificar a cor
						no.getPai().setCor();// Verificar a cor
						leftRotation(no.getPai().getPai());
					}
				}
			} else {
				if (!no.getPai().getPai().getFilhoEsquerda().getCor()) { // ESPELHO
					// DO
					// CASO
					// 1
					// -
					// TIO
					// VERMELHO
					// SENDO
					// FILHO
					// A
					// ESQUERDA
					// DO
					// AVO
					no.getPai().setCor();// Verificar a cor
					no.getPai().getPai().setCor();// Verificar a cor
					// se o avor for raiz deve deixar a cor preta
					if (no.getPai().getPai().getPai() == null) {
						setCorBlack(no.getPai().getPai());// Verificar a cor
					}
					no.getPai().getPai().getFilhoEsquerda().setCor();// Verificar
					// a cor
				} else if (((no == no.getPai().getFilhoDireita()) && (no
						.getPai() == no.getPai().getPai().getFilhoEsquerda()))
						|| ((no == no.getPai().getFilhoEsquerda()) && (no
								.getPai() == no.getPai().getPai()
								.getFilhoDireita()))) {// CASO 2 TIO PRETO E
					// FILHO PAI E AVO
					// FORMAM JOELHO
					if ((no == no.getPai().getFilhoDireita())
							&& (no.getPai() == no.getPai().getPai()
									.getFilhoEsquerda())) {
						leftRotation(no.getPai());
						fixUp(no.getPai().getFilhoEsquerda());
					}
					if ((no == no.getPai().getFilhoEsquerda())
							&& (no.getPai() == no.getPai().getPai()
									.getFilhoDireita())) {
						rightRotation(no.getPai());
						fixUp(no.getPai().getFilhoDireita());
					}
					// como recai sempre no caso 3 chama recursivamente o metodo
					// fixUp();
				} else if (((no == no.getPai().getFilhoEsquerda()) && (no
						.getPai() == no.getPai().getPai().getFilhoEsquerda()))
						|| ((no == no.getPai().getFilhoDireita()) && (no
								.getPai() == no.getPai().getPai()
								.getFilhoDireita()))) {// CASO 3 TIO PRETO E
					// FILHO, PAI E AVO
					// FORMAM LINHA RETA
					if (((no == no.getPai().getFilhoEsquerda()) && (no.getPai() == no
							.getPai().getPai().getFilhoEsquerda()))) {
						no.getPai().getPai().setCor();// Verificar a cor
						no.getPai().setCor();// Verificar a cor
						rightRotation(no.getPai().getPai());// Verificar a cor

					}
					if (((no == no.getPai().getFilhoDireita()) && (no.getPai() == no
							.getPai().getPai().getFilhoDireita()))) {
						// as cores sao modificadas antes porque o avo eh
						// rotacionado
						no.getPai().getPai().setCor();// Verificar a cor
						no.getPai().setCor();// Verificar a cor
						leftRotation(no.getPai().getPai());

					}
				}
			}
		}
		// quando o fixUp terminar os tres casos ele deve ser apicado ao avo
		// para consertar
		// problemas que eventualmente surjam por causa da recursao. E o
		// fixUp deve ser aplicado
		// ao avo se ele nao for o root da arvore.
		if (no.getPai() != null) {
			if (no.getPai().getPai() != null) {
				if (no.getPai().getPai().getPai() != null) {
					fixUp(no.getPai().getPai());
				}
			}
		}
	}

	protected RBNode<K, V> leftRotation() {
		return leftRotation(root);
	}

	private RBNode<K, V> leftRotation(RBNode<K, V> no) {
		RBNode<K, V> tmp = no.getFilhoDireita();
		substituirNo(no, tmp);
		setRightNode(no, tmp.getFilhoEsquerda());
		setLeftNode(tmp, no);
		return root;
	}

	/**
	 * O metodo de substituir no atual pelo novo no
	 * 
	 * @param atual
	 *            - no para ser trocado.
	 * @param novoNo
	 *            - novo no para troca.
	 */
	private void substituirNo(RBNode<K, V> atual, RBNode<K, V> novoNo) {
		if (atual.getPai() != null) {
			if (atual.equals(atual.getPai().getFilhoEsquerda())) {
				setLeftNode(atual.getPai(), novoNo);
			} else {
				setRightNode(atual.getPai(), novoNo);
			}
		} else {
			root = novoNo;
			root.setPai(null);
		}

	}

	/**
	 * O metodo faz com que novo filho a esquerda deixe de ser a filho do atual
	 * 
	 * @param atual
	 *            - para definir o no filho a esquerda
	 * @param novoFilhoAEsquerda
	 *            - um novo no deixou de ser filho esquerdo do atual
	 */
	private void setLeftNode(RBNode<K, V> atual, RBNode<K, V> novoFilhoAEsquerda) {
		atual.setFilhoEsquerda(novoFilhoAEsquerda);
		novoFilhoAEsquerda.setPai(atual);
	}

	/**
	 * O metodo faz com que novo filho a direito deixe de ser a filho do atual
	 * 
	 * @param atual
	 *            - para definir o no filho a direita
	 * @param novoFilhoADireita
	 *            - um novo no deixou de ser filho direito do atual
	 */
	private void setRightNode(RBNode<K, V> atual, RBNode<K, V> novoFilhoADireita) {
		atual.setFilhoDireita(novoFilhoADireita);
		novoFilhoADireita.setPai(atual);
	}

	protected RBNode<K, V> rightRotation() {
		return rightRotation(root);
	}

	private RBNode<K, V> rightRotation(RBNode<K, V> no) {
		RBNode<K, V> tmp = no.getFilhoEsquerda();
		substituirNo(no, tmp);
		setLeftNode(no, tmp.getFilhoDireita());
		setRightNode(tmp, no);
		return root;
	}

	@Override
	public int height() {
		return height(root);
	}

	private int height(RBNode<K, V> no) {
		int result = -1;
		if (!no.isEmpty()) {
			if (no.isLeaf()) {
				result = 0;
			} else {
				result = 1 + Math.max(height(no.getFilhoEsquerda()),
						height(no.getFilhoDireita()));
			}
		}
		return result;
	}

	@Override
	public int blackHeight() {
		return blackHeight(root);
	}

	private int blackHeight(RBNode<K, V> no) {
		int result = 0;
		if (!no.isEmpty()) {
			if (no.getCor()) {
				result = 1 + blackHeight(no.getFilhoEsquerda());
			} else {
				int rightBlackHeight = blackHeight(no.getFilhoDireita());
				int leftBlackHeight = blackHeight(no.getFilhoEsquerda());
				if (rightBlackHeight != leftBlackHeight) {
					result = -1;
				} else {
					result = blackHeight(no.getFilhoEsquerda());
				}
			}
		}
		return result;
	}

	@Override
	public V search(K key) {
		return search(key, root);
	}

	private V search(K key, RBNode<K, V> no) {
		V resp = null;
		if (!no.isEmpty()) {
			if (key != null) {
				if (key.equals(no.getChave())) {
					resp = no.getValor();
				} else {
					if (key.compareTo(no.getChave()) < 0) {
						resp = search(key, no.getFilhoEsquerda());
					} else {
						resp = search(key, no.getFilhoDireita());
					}
				}
			}
		}
		return resp;
	}

	/**
	 * Metodo que busca valor associado a uma determinada chave. Retorna nulo se
	 * a chave eh nao na arvore.
	 * 
	 * @param key
	 *            - chave a procurar na arvore
	 * @return a arvore da chave associada
	 */
	public RBNode<K, V> busca(K key) {
		return busca(key, root);
	}

	private RBNode<K, V> busca(K key, RBNode<K, V> no) {
		RBNode<K, V> resp = null;
		if (!no.isEmpty()) {
			if (key != null) {
				if (key.equals(no.getChave())) {
					resp = no;
				} else {
					if (key.compareTo(no.getChave()) < 0) {
						resp = busca(key, no.getFilhoEsquerda());
					} else {
						resp = busca(key, no.getFilhoDireita());
					}
				}
			}
		}
		return resp;
	}

	@Override
	public void insert(K key, V value) throws ADTOverflowException {
		insert(key, value, root);
	}

	private void insert(K key, V value, RBNode<K, V> no)
			throws ADTOverflowException {
		if (no.isEmpty()) {
			no.setChave(key);
			no.setValor(value);
			if (no.getPai() != null) {
				setCorRed(no); // Verificar cor
			}
			no.setFilhoEsquerda(new RBNode<K, V>());
			no.getFilhoEsquerda().setPai(no);
			no.setFilhoDireita(new RBNode<K, V>());
			no.getFilhoDireita().setPai(no);
			fixUp(no);// ATENCAO ERRO AQUI ENTENDER
		} else {
			if (!no.getChave().equals(key)) {
				if (key.compareTo(no.getChave()) < 0) {
					insert(key, value, no.getFilhoEsquerda());
				} else {
					insert(key, value, no.getFilhoDireita());
				}
			}
		}
	}

	@Override
	public V maximum() {
		return maximum(root);
	}

	private V maximum(RBNode<K, V> no) {
		return search(maximumKey(no));
	}

	@Override
	public V minimum() {
		return minimum(root);
	}

	private V minimum(RBNode<K, V> no) {
		return search(minimumKey(no));
	}

	@Override
	public K maximumKey() {
		return maximumKey(root);
	}

	private K maximumKey(RBNode<K, V> no) {
		K maxChave = no.getChave();
		if (!no.isEmpty()) {
			K maxChaveFilhoDireita = maximumKey(no.getFilhoDireita());
			if (maxChaveFilhoDireita != null
					&& maxChaveFilhoDireita.compareTo(maxChave) > 0) {
				maxChave = maxChaveFilhoDireita;
			}
		}
		return maxChave;
	}

	@Override
	public K minimumKey() {
		return minimumKey(root);
	}

	private K minimumKey(RBNode<K, V> no) {
		K minChave = no.getChave();
		if (!no.isEmpty()) {
			K minKeyFilhoEsquerda = minimumKey(no.getFilhoEsquerda());
			if (minKeyFilhoEsquerda != null
					&& minKeyFilhoEsquerda.compareTo(minChave) < 0) {
				minChave = minKeyFilhoEsquerda;
			}
		}
		return minChave;

	}

	@Override
	public RBNode<K, V> successor(K key) {
		RBNode<K, V> baseNode = busca(key);
		RBNode<K, V> resp = null;
		if (!baseNode.isEmpty()) {
			if (!baseNode.getFilhoDireita().isEmpty()) {
				// o minimo filho direito
				resp = maisAEsquerda(baseNode.getFilhoDireita());
			} else {
				RBNode<K, V> baseParentNode = baseNode.getPai();
				while (!baseParentNode.isEmpty()
						&& baseNode.equals(baseParentNode.getFilhoDireita())) {
					if (!baseParentNode.equals(new RBNode<K, V>())) {
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
		// ??
		return resp;
	}

	/**
	 * O metodo retorna o no mais a esquerda de um dado no.
	 * 
	 * @param atual
	 *            - arvore atual
	 * @return a arvore mais a esquerda
	 */
	private RBNode<K, V> maisAEsquerda(RBNode<K, V> atual) {
		RBNode<K, V> iterator = atual;
		while (!iterator.getFilhoEsquerda().isEmpty()) {
			iterator = iterator.getFilhoEsquerda();
		}
		return iterator;
	}

	/**
	 * O metodo retorna o no mais a direita de um determinado no.
	 * 
	 * @param atual
	 *            - arvore atual
	 * @return ravore mais a direita
	 */
	private RBNode<K, V> maisADireita(RBNode<K, V> atual) {
		RBNode<K, V> iterator = atual;
		while (!iterator.getFilhoDireita().isEmpty()) {
			iterator = iterator.getFilhoDireita();
		}
		return iterator;
	}

	@Override
	public RBNode<K, V> predecessor(K key) {
		RBNode<K, V> baseNode = busca(key);// PEGA O ARVORE DO NO
		RBNode<K, V> resp = null;// AXILIAR
		if (!baseNode.isEmpty()) {// SE ENCONTROU E POR QUE POSSIVELMENTE TEM
			// PREDECESSOR
			if (!baseNode.getFilhoEsquerda().isEmpty()) {// VOU PARA MEU FILHO
				// ESQUERDO E PEGO O
				// NO MAIS A DIREITA
				// o minimo filho esquerdo
				resp = maisADireita(baseNode.getFilhoEsquerda());// NO
				// F.ESQUERDO
				// NA FOLHA
				// MAIS A
				// DIREITA
				// ESTA O
				// PREDECESSOR
			} else {
				RBNode<K, V> baseParentNode = baseNode.getPai();
				while (!baseParentNode.isEmpty()
						&& baseNode.equals(baseParentNode.getFilhoEsquerda())) {
					if (!baseParentNode.equals(new RBNode<K, V>())) {
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
		return resp;
	}

	@Override
	public void delete(K key) throws ADTUnderflowException {
		RBNode<K, V> no = busca(key);
		if (no != null) {// chave nao foi encontrada... nao faca nada..
			if (!no.getFilhoEsquerda().isEmpty()
					&& !no.getFilhoDireita().isEmpty()) {
				// copie a chave e valor do predecessor e delete ele.
				RBNode<K, V> predecessor = maximumNode(no.getFilhoEsquerda());
				no.setChave(predecessor.getChave());
				no.setValor(predecessor.getValor());
				no = predecessor;
			}

			RBNode<K, V> filho = (no.getFilhoDireita() == null) ? no
					.getFilhoEsquerda() : no.getFilhoDireita();
			if (no.getCor()) {
				setCorDoOutro(no, filho);
				deleteFixUP(no);
			}
			trocaNo(no, filho);

			if (!root.getCor()) {
				setCorBlack(root);
			}

			checkProperties();
		}

	}

	private RBNode<K, V> maximumNode(RBNode<K, V> no) {
		if (no != null) {
			while (no.getFilhoDireita() != null) {
				no = no.getFilhoDireita();
			}
		}
		return no;
	}

	private void trocaNo(RBNode<K, V> antigoNo, RBNode<K, V> novoNo) {
		if (antigoNo.getPai() == null) {
			root = novoNo;
		} else {
			if (antigoNo == antigoNo.getPai().getFilhoEsquerda())
				antigoNo.getPai().setFilhoEsquerda(novoNo);
			else
				antigoNo.getPai().setFilhoDireita(novoNo);
		}
		if (novoNo != null) {
			novoNo.setPai(antigoNo.getPai());
		}
	}

	private void deleteFixUP(RBNode<K, V> atual) {
		while ((!atual.equals(root)) && atual.getCor()) {
			if (atual.equals(atual.getPai().getFilhoEsquerda())) {
				RBNode<K, V> brother = atual.getPai().getFilhoDireita();
				if (!brother.getCor()) {
					// caso1 - irmão direito é vermelho
					setCorBlack(brother);

					setCorRed(atual.getPai());

					leftRotation(atual.getPai());
					brother = atual.getPai().getFilhoDireita();
				}
				if (brother.getFilhoEsquerda().getCor()
						&& brother.getFilhoDireita().getCor()) {
					// Caso2 - irmão direita e preto e seus dois filhos
					// pretos

					setCorRed(brother);

					atual = atual.getPai();
				} else {
					// um filho do irmao de direito e vermelho
					if (brother.getFilhoDireita().getCor()) {
						// Caso3 - filho da direita irmao direito e preto
						setCorBlack(brother.getFilhoEsquerda());
						rightRotation(brother);
						brother = atual.getPai().getFilhoDireita();
					}
					// caso4 - o filho deixou o irmão certo é preto
					setCorDoOutro(brother, atual.getPai());
					setCorBlack(atual.getPai());
					setCorBlack(brother.getFilhoDireita());
					leftRotation(atual.getPai());
					atual = root;
				}
			} else {
				// casos simetricos como acima
				RBNode<K, V> brother = atual.getPai().getFilhoEsquerda();
				if (!brother.getCor()) {
					// caso1 - irmão esquerda e vermelho
					setCorBlack(brother);
					setCorRed(atual.getPai());
					rightRotation(atual.getPai());
					brother = atual.getPai().getFilhoEsquerda();
				}
				if (brother.getFilhoEsquerda().getCor()
						&& brother.getFilhoDireita().getCor()) {
					// Caso 2 - irmão esquerdo e preto e os seus dois filhos
					// preto
					setCorRed(brother);
					atual = atual.getPai();
				} else {
					// um filho do irmao de esquerda e vermelho
					if (brother.getFilhoEsquerda().getCor()) {
						// Caso3
						setCorBlack(brother.getFilhoDireita());
						leftRotation(brother);
						brother = atual.getPai().getFilhoEsquerda();
					}
					// caso4
					setCorDoOutro(brother, atual.getPai());
					setCorBlack(atual.getPai());
					setCorBlack(brother.getFilhoEsquerda());
					rightRotation(atual.getPai());
					atual = root;
				}
			}
		}
		setCorBlack(atual);
	}

	@Override
	public int size() {
		return size(root);
	}

	private int size(RBNode<K, V> no) {
		int resp = 0;
		if (!no.isEmpty()) {
			resp = 1 + size(no.getFilhoEsquerda()) + size(no.getFilhoDireita());
		}
		return resp;

	}

	@Override
	public V[] preOrder() {
		return null;
	}

	@Override
	public V[] order() {
		return null;

	}

	@Override
	public V[] postOrder() {
		// TODO Auto-generated method stub
		return null;
	}

	public String toString() {
		return root.toString();
	}

	public void setCorBlack(RBNode<K, V> no) {
		if (!no.getCor()) {
			no.setCor();
		}
	}

	public void setCorRed(RBNode<K, V> no) {
		if (no.getCor()) {
			no.setCor();
		}
	}

	public void setCorDoOutro(RBNode<K, V> noAtual, RBNode<K, V> noComparacao) {
		if (noAtual.getCor() != noComparacao.getCor()) {
			setCor(noAtual, noComparacao.getCor());
		}
	}

	private void setCor(RBNode<K, V> no, boolean isBlack) {
		if (isBlack) {
			setCorBlack(no);
		} else {
			setCorRed(no);
		}
	}

	public static void main(String[] args) throws ADTOverflowException,
			ADTUnderflowException {
		RBTreeImpl<Integer, Integer> t = new RBTreeImpl<Integer, Integer>();

		boolean ok = t.checkProperties();

		for (int i = 21; i > -1; i--) {
			t.insert(i, i);
			ok = t.checkProperties();
			System.out.println("T :: nos:" + t.size() + " - altura:"
					+ t.height() + "- RB(" + ok + ")");
		}

		for (int i = 0; i < 50; i++) {
			t.insert(i, i);
			ok = t.checkProperties();
			System.out.println("T :: nos:" + t.size() + " - altura:"
					+ t.height() + "- RB(" + ok + ")");
		}
		System.out.println("ARVORE T");
		for (int i = 1; i < 49; i++) {// COMECA DO 1 POIS UM DA PAL NO
			// PREDECESSOR
			System.out.println("sucessor de " + i + ": "
					+ t.successor(i).getChave() + " - predeecessor: "
					+ t.predecessor(i).getChave());

		}
		System.out.println("deletando");
		for (int i = 0; i < 50; i++) {
			t.delete(i);
		}
		System.out.println("buscando");
		for (int i = 0; i < 50; i++) {
			System.out.println("buscando " + i + ": " + t.search(i));
		}
		System.out.println(t.toString());

		System.out.println("----- PRE-ORDEM--------------");
		System.out.println(t.preOrder());

	}
}