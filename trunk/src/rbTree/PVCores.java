package rbTree;

public enum PVCores {
	PRETO("No Preto"), VERMELHO("No Vermelho");

	private String cor;

	public String getCor() {
		return this.cor;
	}

	private PVCores(String cor) {
		this.cor = cor;
	}
}