package prodPlan;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 *  Outro exemplo de visitor: visita 'recursivamente' os itens e partes que
 *  compõem uma lista verificando se não existem objetos diferentes com o mesmo
 *  código (cada código deve sempre se referir ao mesmo objeto)
 *
 */
public class Lab4AuxVisitor extends ProdPlanVisitor {

	/** 
	 * associa cada código a um objeto Visitable 
	 */
	private Map<Integer, Visitable> mapaCod;
	
	/**
	 * construtor, sem parâmetros
	 */
	public Lab4AuxVisitor(){
		mapaCod = new LinkedHashMap<Integer, Visitable>();
	}
	
		
	/**
	 * Método auxiliar: verifica se um dado código se refere ao mesmo
	 * objeto que já está no mapa. Caso não esteja no mapa, o mesmo é inserido.
	 * @param cod
	 * @param obj
	 * @return
	 */
	private void insereNoMapa(int cod, Visitable obj){
		Object myObj = mapaCod.get(cod);
		if(myObj == null) {
			mapaCod.put(cod, obj);
			return ;
		}
		assert myObj == obj;  // devem se referir ao mesmo objeto !!!
	}
	
	/**
	 * Visita cada item de uma lista através do método accept().
	 * Notar que este método não está definido em ProdPlanVisitor.
	 * @param lista
	 * @return
	 */
	public Map<Integer, Visitable> visit(List<Item> lista){
		for(Item item: lista) item.accept(this);
		return mapaCod;
	}

	@Override
	public Object visit(ParteEspecifica parte) {
		insereNoMapa(parte.getCod(), parte);
		return null;
	}

	@Override
	public Object visit(Motor motor) {
		insereNoMapa(motor.getCod(), motor);
		return null;
	}

	@Override
	public Object visit(Parafuso parafuso) {
		insereNoMapa(parafuso.getCod(), parafuso);
		return null;
	}

	@Override
	public Object visit(ParteComposta parte) {
		insereNoMapa(parte.getCod(), parte);
		visit(parte.listaDeItens()); // entendeu ?
		return null;
	}

	@Override
	public Object visit(Item item) {
		item.getParte().accept(this);
		return null;
	}

	@Override
	public Object visit(Caracteristica caracteristica) {
		return new Boolean(true); 
	}

}
