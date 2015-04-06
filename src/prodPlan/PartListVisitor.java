package prodPlan;

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

	private Map<Integer, Integer>	mapCodQtde;
	
	private void insereNoMapa(int cod, Visitable obj, int qtde) {
		int myObj = mapCodQtde.get(cod);
		if (myObj > 0) {
			mapCodQtde.put(cod, qtde+myObj);
			return;
		}
	}
	
	public String visit(List<Item> lista) {
		// TODO Auto-generated method stub
		for(Item item : lista)	{
			item.accept(this);
		}
		return mapCodQtde.toString();
	}

	@Override
	public Object visit(ParteEspecifica parte) {
		// TODO Auto-generated method stub
		insereNoMapa(parte.getCod(), parte, 1);
		return null;
	}

	@Override
	public Object visit(Motor motor) {
		// TODO Auto-generated method stub
		insereNoMapa(motor.getCod(), motor, 1);
		return null;
	}

	@Override
	public Object visit(Parafuso parafuso) {
		// TODO Auto-generated method stub
		insereNoMapa(parafuso.getCod(), parafuso, 1);
		return null;
	}

	@Override
	public Object visit(ParteComposta parte) {
		insereNoMapa(parte.getCod(), parte, 1);
		visit(parte.listaDeItens()); // entendeu ?
		return null;
	}

	@Override
	public Object visit(Item item) {
		// TODO Auto-generated method stub
		item.getParte().accept(this);
		return null;
	}

	@Override
	public Object visit(Caracteristica caracteristica) {
		// TODO Auto-generated method stub
		return new Boolean(true); 
	}

	public Map<Integer, Integer> getMapCodQtde() {
		return mapCodQtde;
	}

	public void setMapCodQtde(Map<Integer, Integer> mapCodQtde) {
		this.mapCodQtde = mapCodQtde;
	}


}
