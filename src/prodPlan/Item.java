package prodPlan;

public class Item implements Visitable, Comparable<Item> {

	Parte parte;

	int quantidade;

	// Construtor de item
	public Item(Parte parte2, int i) {
		this.setParte(parte2);
		this.setQuantidade(i);
	}

	// Gettlers and Setters
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

	// Método que retorna o valor total do item de acordo com a quantidade de
	// partes
	public float calculaValor() {
		return (this.getParte().calculaValor() * this.getQuantidade());
	}

	// Método que retorna a string no formato pedido
	public String toString() {

		String itemToString = "cod:" + this.getParte().getCod() + " nome:"
				+ this.getParte().getNome() + " quantidade:"
				+ this.getQuantidade() + " valor unitario:"
				+ this.getParte().getValor() + " valor:" + this.calculaValor();
		return itemToString;

	}

	// Método override do compareTo, ascendente de acordo com as quantidades
	@Override
	public int compareTo(Item i) {
		int qtde = i.getQuantidade();
		if (this.getQuantidade() > qtde) {
			return 1;
		} else if (this.getQuantidade() < qtde) {
			return -1;
		} else {
			return 0;
		}
	}

	@Override
	public Object accept(ProdPlanVisitor visitor) {
		return visitor.visit(this);
	}

}
