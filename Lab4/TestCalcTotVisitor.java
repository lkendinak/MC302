package prodPlan;

import java.util.List;
import java.util.Map;

/**
 * Exemplo de uso dos visitors CalcTotVisitor e Lab4AuxVisitor
 *
 */
public class TestCalcTotVisitor {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		/** criação da lista de objetos Item **/
		List<Item> lista = Dados.criaListaItens();
		
		/**  visita à lista usando CalcTotVisitor (e que, neste caso
		 *   o objeto retornado é um Float)
		 **/
		Float tot = CalcTotVisitor.visit(lista);
		System.out.println("total:"+tot.toString());
		
	}

}
