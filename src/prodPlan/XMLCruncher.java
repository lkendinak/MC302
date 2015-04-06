package prodPlan;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import java.io.File;
import java.io.StringWriter;
 
/**
 * Ferramentas auxiliares para tratar XML
 *
 */

public class XMLCruncher {

	/**
	 * Le o 'documento' contido num arquivo XML.
	 * @param fName nome do arquivo XML
	 * @return objeto 'org.w3c.dom.Document' que representa o documento no padrão 'w3c.dom'.
	 * @throws Exception caso haja algum problema na leitura do arquivo ou no formato xml do mesmo.
	 */
	public static Document docRead(String fName) throws Exception{
		File fXmlFile = new File(fName);
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(fXmlFile);
		return doc;
	}
	
	/**
	 * Gera o texto XML correspondente ao arquivo lido num formato padronizado pelas
	 * ferramentas do pacote javax.xml.transformer
	 * @param fName
	 * @return
	 * @throws Exception
	 */
    public static String pasteurize(String fName) throws Exception{
    	Document doc = docRead(fName);
		DOMSource domSource = new DOMSource(doc);
		StringWriter writer = new StringWriter();
		StreamResult result = new StreamResult(writer);
		TransformerFactory tf = TransformerFactory.newInstance();
		Transformer transformer = tf.newTransformer();
		transformer.transform(domSource, result);
		return writer.toString();		
    }
	
	
	/**
	 * Apenas um programa de teste
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		
		// le o documento representado no arquivo
		Document doc = docRead("alunos.xml");
		
		// pega a lista de alunos contida no documento
		NodeList lista = doc.getElementsByTagName("aluno");
		System.out.println("número de elementos <aluno>:"+lista.getLength());
		
		/** pasteuriza o texto xml e mostra na telinha */
		System.out.println("XML IN String format is: \n" + pasteurize("alunos.xml"));		
		
		System.out.println("FIM");

	}

}
