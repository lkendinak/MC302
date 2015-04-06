package prodPlan;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ParteComposta extends Parte {

	List<Item> itens;

	public ParteComposta(int code, String name, String descrip, float value) {
		super(code, name, descrip, value);
	}

	public void agregaItem(Parte parte, int quantidade) throws Exception {
		// Caso seja a primeira vez, instancia o conjunto items
		if (null == this.getItens()) {
			this.setItens(new ArrayList<Item>());
		}
		// Caso a parte seja nula, ou já esteja no conjunto, joga uma exceção
		if (null == parte || this.getItens().contains(parte)) {
			throw new Exception("Essa Parte já existe, ou é nula");
		}

		// Adiciona o novo item no conjunto
		this.getItens().add(new Item(parte, quantidade));
	}

	List<Item> listaDeItens() {
		List<Item> list = new ArrayList<Item>();
		// Itera no conjunto de items adicionando numa lista
		for (Item i : this.getItens()) {
			list.add(i);
		}
		// Usa o método sort() para ordenar a lista de acordo com o compareTo
		// implementado na classe Item
		Collections.sort(list);
		return list;
	}

	// Calcula o valor total da Parte Composta
	public float calculaValor() {
		// inicialmente pega o valor principal do pai
		float valor = super.calculaValor();

		// Itera pelo conjunto de items
		for (Item i : this.getItens()) {
			// Verifica se a parte do item é uma parte composta
			// caso seja, chama o método calcula valor novamente
			// se não retorna o valor da parte multiplicada pela quantidade
			if (i.getParte() instanceof ParteComposta) {
				valor = valor + i.calculaValor();
			} else {
				valor = valor + (i.getParte().getValor() * i.getQuantidade());
			}
		}
		return valor;
	}

	// Usa o método pai para retornar em formato String
	public String toString() {
		return super.toString();
	}

	@Override
	public Object accept(ProdPlanVisitor visitor) {
		return visitor.visit(this);
	}

	// método ParteComposta.getItens() deverá retornar o mesmo valor que o
	// método ParteComposta.listaDeItens()
	public List<Item> getItens() {
		return this.listaDeItens();
	}

	public void setItens(List<Item> itens) {
		this.itens = itens;
	}

}
