package prodPlan;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;
/**
 * Teste de XMLGenVisitor:
 * A implementação de XMLGenVisitor usa um mapa 
 *
 */

public class TestLab4_1 {

	static final String OUTFNAME = "saidaLab4.xml";
	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		
		/** cria lista de itens **/
		List<Item> lista = Dados.criaListaItens();
		
		/** cria visitor que gera o mapa de objetos Parte, indexado pelo código **/
		Lab4AuxVisitor vcv = new Lab4AuxVisitor();
		Map<Integer, Visitable> mapaCod = vcv.visit(lista);
		
		/** cria o visitor a ser testado **/
		Lab4XMLVisitor xgv = new Lab4XMLVisitor(mapaCod);
		
		/** visita cada item da lista e retorna o texto XML **/
		String xml = xgv.visit(lista);
		
		/** escreve a saída num arquivo **/
		PrintWriter pw = new PrintWriter(new FileWriter(OUTFNAME));
		pw.println(xml);
		pw.close();
		
		/** 'pasteuriza' o texto XML gerado para verificação pelo Suzy **/
		System.out.println(XMLCruncher.pasteurize(OUTFNAME));
	}

}
