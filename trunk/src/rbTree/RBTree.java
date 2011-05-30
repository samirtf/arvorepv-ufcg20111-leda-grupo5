package rbTree;

import util.ADTOverflowException;
import util.ADTUnderflowException;

/**
 * Um nÃ³ de uma RBTree. Deve ter campos para manter uma chave de tipo K
 * (comparÃ¡vel) e um valor de tipo V (tambÃ©m comparÃ¡veis). Ele tambÃ©m tem um
 * pai, um filho esquerdo e um direito da crianÃ§a e uma bandeira para determinar
 * se o nÃ³ Ã© preto ou nÃ£o (se ele nÃ£o Ã© negro, entÃ£o Ã© assumido que o nÃ³ Ã©
 * vermelho).
 */
class RBNode<K extends Comparable<K>, V extends Comparable<V>> {

    private RBNode<K, V> pai;
    private RBNode<K, V> filhoEsquerda;
    private RBNode<K, V> filhoDireita;
    private K chave;
    private V valor;
    private boolean isBlack = true;

    /**
     * Metodo para verificar se o no eh vazio ou nao
     * 
     * @return true ou false, dependendo se o filho esquerdo e direito sao
     *         nulos.
     */
    public boolean isEmpty() {
        return this.getFilhoEsquerda() == null && this.getFilhoEsquerda() == null;
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
            resp = this.getFilhoEsquerda().isEmpty() && this.getFilhoDireita().isEmpty();
        }
        return resp;
    }

    /**
     * Este mÃ©todo deve retornar uma string contendo a chave de um nÃ³ de RB. Se
     * o nÃ³ Ã© preto ele simplesmente retorna a chave (como String). Caso
     * contrÃ¡rio, ele retorna a chave entre menor e maior (ou seja, <key>).
     */
    @Override
    public String toString() {
        StringBuilder resp = new StringBuilder();
        if (!isEmpty()) {
            if (this.getCor()) {
                resp.append(this.getValor() + " ");
            } else {
                resp.append("<" + this.getValor() + "> ");

            }
            resp.append(this.getFilhoEsquerda().toString());
            resp.append(this.getFilhoDireita().toString());
        }
        return resp.toString();
    }

    public RBNode<K, V> getPai() {
        return this.pai;
    }

    public RBNode<K, V> getFilhoEsquerda() {
        return this.filhoEsquerda;
    }

    public RBNode<K, V> getFilhoDireita() {
        return this.filhoDireita;
    }

    public K getChave() {
        return this.chave;
    }

    public V getValor() {
        return this.valor;
    }

    public boolean getCor() {
        return this.isBlack;
    }

    public void setPai(RBNode<K, V> pai) {
        this.pai = pai;
    }

    public void setFilhoEsquerda(RBNode<K, V> filhoEsquerda) {
        this.filhoEsquerda = filhoEsquerda;
    }

    public void setFilhoDireita(RBNode<K, V> filhoDireita) {
        this.filhoDireita = filhoDireita;
    }

    public void setChave(K chave) {
        this.chave = chave;
    }

    public void setValor(V valor) {
        this.valor = valor;
    }

    public void setCor() {
        if (isBlack) {
            this.isBlack = false;
        } else {
            this.isBlack = true;
        }
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
     * Retorna a altura de uma Ã¡rvore RB. NIL nÃ³s nÃ£o influenciam os altura.
     */
    public int height();

    /**
     * Retorna a altura de uma Ã¡rvore negra RB.
     */
    public int blackHeight();

    /**
     * Procura o valor associado a uma determinada chave. Retorna nulo se a
     * chave Ã© nÃ£o na Ã¡rvore.
     */
    public V search(K key);

    /**
     * Insere um valor associado a uma determinada chave em uma Ã¡rvore RB.
     */
    public void insert(K key, V value) throws ADTOverflowException;

    /**
     * Returns the maximum value (associated to the greatest key) of a RB tree.
     */
    public V maximum();

    /**
     * Retorna o valor mÃ¡ximo (associada Ã  maior chave) de uma Ã¡rvore RB.
     */
    public V minimum();

    /**
     * Retorna a chave mÃ¡xima de uma Ã¡rvore ou nulo, se a Ã¡rvore estÃ¡ vazia.
     */
    public K maximumKey();

    /**
     * Retorna a chave mÃ­nima de uma Ã¡rvore ou nulo, se a Ã¡rvore estÃ¡ vazia.
     */
    public K minimumKey();

    /**
     * Returns the node that is the the successor (according to the Cormen's
     * book) of a node containing the given key. If the key is not in the tree
     * returns null;
     */
    public RBNode<K, V> successor(K key);

    /**
     * Retorna o nÃ³ que Ã© o sucessor (de acordo com o Cormen estÃ¡ livro) de um
     * nÃ³ que contÃ©m a chave dada. Se a chave nÃ£o estÃ¡ na Ã¡rvore retorna null;
     */
    public RBNode<K, V> predecessor(K key);

    /**
     * Remove o nÃ³ que contÃ©m uma determinada chave.
     * 
     * @param key
     */
    public void delete(K key) throws ADTUnderflowException;

    /**
     * Retorna quantos elementos foram colocados na Ã¡rvore.
     */
    public int size();

    /**
     * percorre a Ã¡rvore em ordem e retorna um array contendo os valores nessa
     * ordem.
     */
    public V[] preOrder();

    /**
     * percorre a Ã¡rvore em ordem e retorna um array contendo os valores nessa
     * ordem.
     */
    public V[] order();

    /**
     * percorre a Ã¡rvore em pÃ³s-ordem e retorna um array contendo os Os valores,
     * nessa ordem.
     */
    public V[] postOrder();
}