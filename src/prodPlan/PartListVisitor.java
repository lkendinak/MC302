package prodPlan;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class PartListVisitor extends ProdPlanVisitor {

	//
	// e seu objetivo é, a partir de uma lista de itens, gerar uma listagem
	// contendo a quantidade total de cada um dos objetos
	// Parte usados nessa lista. A listagem gerada deve conter apenas os objetos
	// das classes Motor, Parafuso e
	// ParteEspecifica, relacionados direta ou indiretamente na ‘lista de
	// entrada’. No caso de objetos ParteComposta,
	// os objetos das demais classes usadas (Motor, Parafuso e ParteEspecifica)
	// devem ser totalizados na listagem
	// gerada. A figura abaixo, ilustra a classe PartListVisitor.

	// Mapa Cod/Parte
	private Map<Integer, Visitable> mapCod;

	// Mapa Cod/Quantidade
	private Map<Integer, Integer> mapQtde;

	// Construtor
	public PartListVisitor() {
		this.mapCod = new LinkedHashMap<Integer, Visitable>();

		// Por incrivel que pareça utilizando o HashMap a ordenação fica
		// diferente do LinkedHashMap.
		this.mapQtde = new LinkedHashMap<Integer, Integer>();
	}

	// Método auxiliar que adiciona os valores no mapa
	private void insereNoMapa(int cod, Visitable obj, int qtde) {
		Object myObj = mapCod.get(cod);

		// Caso não exista no mapa, adiciona
		if (myObj == null) {
			mapQtde.put(cod, qtde);
			mapCod.put(cod, obj);
			return;
		} else {
			// Caso já exista, atualiza os valores
			Integer qt = mapQtde.get(cod);
			mapQtde.put(cod, qt + qtde);
		}
	}

	public String visit(List<Item> lista) {
		for (Item item : lista) {
			item.accept(this);
		}
		return imprime(mapQtde);
	}

	// Para ParteEspecifica/Motor/Parafuso temos apenas um item por vez
	@Override
	public Object visit(ParteEspecifica parte) {
		insereNoMapa(parte.getCod(), parte, 1);
		return null;
	}

	@Override
	public Object visit(Motor motor) {
		insereNoMapa(motor.getCod(), motor, 1);
		return null;
	}

	@Override
	public Object visit(Parafuso parafuso) {
		insereNoMapa(parafuso.getCod(), parafuso, 1);
		return null;
	}

	// Em ParteComposta temos uma Lista de itens, visitamos cada item
	@Override
	public Object visit(ParteComposta parte) {
		for (Item item : parte.getItens()) {
			visit(item);
		}

		return null;
	}

	// Cada item possui uma certa quantidade, por isso damos accept para cada
	// um.
	@Override
	public Object visit(Item item) {
		for (int i = 0; i < item.getQuantidade(); i++) {
			item.getParte().accept(this);
		}
		return null;
	}

	@Override
	public Object visit(Caracteristica caracteristica) {
		// não é utilizado
		return null;
	}

	// método auxiliar para impressao:
	private String imprime(Map<Integer, Integer> map) {
		String saida = "";
		// Itera pelo LinkedHashMap associando chave/valor
		for (Integer i : map.keySet())
			saida = saida + "cod:" + i.toString() + " quant:"
					+ map.get(i).toString() + "\n";

		return saida;
	}

	// Gettlers and Settlers
	public Map<Integer, Visitable> getMapCodQtde() {
		return mapCod;
	}

	public void setMapCodQtde(Map<Integer, Visitable> mapCod) {
		this.mapCod = mapCod;
	}

	public Map<Integer, Integer> getMapQtde() {
		return mapQtde;
	}

	public void setMapQtde(Map<Integer, Integer> mapQtde) {
		this.mapQtde = mapQtde;
	}

}
