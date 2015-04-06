package prodPlan;

import java.util.LinkedHashMap;
import java.util.Map;

public class Lab4XMLVisitor extends ProdPlanVisitor{
	
	private Map<Integer, Visitable> mapaCod;

	public Lab4XMLVisitor(Map<Integer, Visitable> mapaCod) {
		this.mapaCod = new LinkedHashMap<Integer, Visitable>();
	}
	
	private void insereNoMapa(int cod, Visitable obj){
		Object myObj = mapaCod.get(cod);
		if(myObj == null) {
			mapaCod.put(cod, obj);
			return ;
		}
		assert myObj == obj;  // devem se referir ao mesmo objeto !!!
	}

	@Override
	public Object visit(ParteEspecifica parte) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object visit(Motor motor) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object visit(Parafuso parafuso) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object visit(ParteComposta parte) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object visit(Item item) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object visit(Caracteristica caracteristica) {
		// TODO Auto-generated method stub
		return null;
	}

	public Map<Integer, Visitable> getMapaCod() {
		return mapaCod;
	}

	public void setMapaCod(Map<Integer, Visitable> mapaCod) {
		this.mapaCod = mapaCod;
	}

}
