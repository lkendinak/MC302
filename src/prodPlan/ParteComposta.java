package prodPlan;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ParteComposta extends Parte {

	Set<Item> items;

	public ParteComposta(int code, String name, String descrip, float value) {
		super(code, name, descrip, value);
	}

	public void agregaItem(Parte parte, int quantidade) throws Exception {
//		este método deve agregar um item ao objeto ParteComposta e tem como parâmetros a
//		referência a um objeto Parte e a quantidade que essa parte será usada na ParteComposta.
//		Este método deve gerar uma exceção se o objeto Parte referenciado pelo objeto Item já
//		estiver presente ou for nula e também no caso de a quantidade ser nula. 
		if ( null == items)	{
			items = new HashSet<Item>();
		}
		if (null == parte || items.contains(parte))	{
			throw new Exception("Essa Parte já existe, ou é nula");
		}
		items.add(new Item(parte, quantidade));
	}

	List<Item> listaDeItens() {
		List<Item> list = new ArrayList<Item>();
		for (Item i : items)	{
			list.add(i);
		}
		Collections.sort(list);
		return list;
	}

	public float calculaValor() {
		float valor = super.calculaValor();
		for (Item i : items)	{
			if(i.getParte() instanceof ParteComposta)	{
				valor = valor + i.calculaValor();
			} else	{
				valor = valor + (i.getParte().getValor()*i.getQuantidade());
			}
			
		}
		
		return valor;
	}
	
	public String toString()	{
		
		return super.toString();
	}

	@Override
	public Object accept(ProdPlanVisitor visitor) {
		return visitor.visit(this);
	}

}
