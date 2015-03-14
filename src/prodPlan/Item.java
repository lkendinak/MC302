package prodPlan;

public class Item {

	private Parte parte;

	private int quantidade;

	public Item(Parte parte2, int i) {
		this.setParte(parte2);
		this.setQuantidade(i);
	}

	public Parte getParte() {
		return parte;
	}

	public void setParte(Parte parte) {
		this.parte = parte;
	}

	public int getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}

	public float calculaValor() {
		return (this.getParte().getValor() * this.quantidade);
	}

	public String toString() {

		// String itemToString = StringUtils.replaceEach(this.parte.toString(),
		// new String[]{"codigo", "valor"},
		// new String[]{"cod", "valor unitario"});

		String itemToString = this.parte.toString().replace("codigo", "cod");
		itemToString = itemToString.replace("valor", "valor unitário")
				+ " valor:" + this.calculaValor();
		return itemToString;

	}

}
