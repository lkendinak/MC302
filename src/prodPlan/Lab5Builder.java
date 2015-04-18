package prodPlan;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class Lab5Builder extends DefaultHandler implements InterfaceLab5 {

	private List<Item> itemList;
	private List<Item> agregaItemList;
	private Map<Integer, Item> itemMap;
	private ParteComposta partCompTmp;
	private Item itemTmp;
	private Motor motorTmp;
	private Parafuso parafusoTmp;
	private ParteEspecifica parteEspcTmp;
	Boolean parts = false;
	int auxCode;
	int code;
	int qtde;

	//Construtor Vazio
	public Lab5Builder() {

	}

	public List<Item> getItemList() {
		return itemList;
	}

	@Override
	public List<Item> ItemsFromXml(String fileName) throws Exception {

		SAXParserFactory spf = SAXParserFactory.newInstance();
		SAXParser saxParser = spf.newSAXParser();
		Lab5Builder handler = new Lab5Builder();
		saxParser.parse(fileName, handler);
		return handler.getItemList();
	}

	@Override
	public void startElement(String uri, String localName, String tagName,
			Attributes attributes) throws SAXException {

		// Switch para o tagName

		if (tagName.equals("lista")) {
			// Inicia o itemList e o itemMap;
			itemList = new ArrayList<Item>();
			itemMap = new LinkedHashMap<Integer, Item>();
		} else if (tagName.equals("item")) {
			// Verifica se existem atributos para a tag Item
			if (attributes != null) {
				// Aloca nas variaveis auxliares o código e a quantidade de cada
				// Item
				auxCode = Integer.parseInt(attributes.getValue(0));
				qtde = Integer.parseInt(attributes.getValue(1));
			}
		} else if (tagName.equals("partes")) {
			// Seta a variável booleana parts para true
			parts = true;
		} else if (tagName.equals("partecomposta")) {
			// Inicia a lista de items que será agregada a parteComposta
			agregaItemList = new ArrayList<Item>();

			// Coloca o valor do código da parteComposta em uma variavel
			// auxliar;
			code = Integer.parseInt(attributes.getValue(0));

			// Verifica se já existe no itemMap algum item com o mesmo código
			if (itemMap.containsKey(code)) {
				// Coloca no item auxiliar o item encontrado no Map
				itemTmp = itemMap.get(code);

				// Inicia a parteComposta axiliar uma nova Parte Composta com
				// os dados parseados no XML
				partCompTmp = new ParteComposta(code, attributes.getValue(1),
						attributes.getValue(2), Float.parseFloat(attributes
								.getValue(3)));
				// Adiciona a parte ao item auxliar
				itemTmp.setParte(partCompTmp);
			}

		} else if (tagName.equals("parafuso")) {
			// Coloca o valor do código em uma variavel auxliar;
			code = Integer.parseInt(attributes.getValue(0));

			// Verifica se já existe no itemMap algum item com o mesmo código
			if (itemMap.containsKey(code)) {
				// Coloca na item auxiliar o item encontrado no Map
				itemTmp = itemMap.get(code);

				// Inicia o Parafuso axiliar um novo Parafuso com
				// os dados parseados no XML
				parafusoTmp = new Parafuso(code, attributes.getValue(1),
						attributes.getValue(2), Float.parseFloat(attributes
								.getValue(3)), Float.parseFloat(attributes
								.getValue(4)), Float.parseFloat(attributes
								.getValue(5)));
				// Adiciona a parte ao item auxliar
				itemTmp.setParte(parafusoTmp);
			}
		} else if (tagName.equals("parteespecifica")) {
			// Código análogo ao tagName = parafuso, escrito acima
			code = Integer.parseInt(attributes.getValue(0));
			if (itemMap.containsKey(code)) {
				itemTmp = itemMap.get(code);
				parteEspcTmp = new ParteEspecifica(code,
						attributes.getValue(1), attributes.getValue(2),
						Float.parseFloat(attributes.getValue(3)));
				itemTmp.setParte(parteEspcTmp);
			}
		} else if (tagName.equals("caracteristica")) {

			try {
				// Agrega Caracteristica a parteEspecifica auxliar com os dados
				// do XML
				parteEspcTmp.agregaCaracteristica(attributes.getValue(0),
						attributes.getValue(1));
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (tagName.equals("motor")) {
			// Código análogo ao tagName = parafuso e parteEspcifica
			code = Integer.parseInt(attributes.getValue(0));
			if (itemMap.containsKey(code)) {
				itemTmp = itemMap.get(code);
				motorTmp = new Motor(code, attributes.getValue(1),
						attributes.getValue(2), Float.parseFloat(attributes
								.getValue(3)), Float.parseFloat(attributes
								.getValue(4)), Float.parseFloat(attributes
								.getValue(5)), Integer.parseInt(attributes
								.getValue(6)));
				itemTmp.setParte(motorTmp);
			}
		}

	}

	@Override
	public void endElement(String uri, String localName, String tagName)
			throws SAXException {

		if (tagName.equals("item")) {
			Item auxItem = null;
			// Verifica através do Booleano se a TagName partes já foi aberta
			if (!parts) {
				// Inicia no itemAuxiliar um novo item com os dados lidos do XML
				auxItem = new Item(new ParteComposta(auxCode), qtde);

				// Adiciona no itemList e no itemMap o item criado.
				itemList.add(auxItem);
				itemMap.put(auxCode, auxItem);
			} else {
				// Verifica se o item já existe no mapa
				if (itemMap.containsKey(auxCode)) {
					// Inicia um novo itemAuxiliar com o item que estava no
					// itemMap
					auxItem = new Item(itemMap.get(auxCode).getParte(), qtde);

					// Adiciona na lista de itens que será agregado a
					// parteComposta e em seguida atualiza a parteComposta
					agregaItemList.add(auxItem);
					partCompTmp.setItens(agregaItemList);
				} else {
					// Inicia no itemAuxiliar um novo item com os dados lidos do
					// XML e em seguida adiciona o novo item no mapa
					auxItem = new Item(new Parte(auxCode), qtde);
					itemMap.put(auxCode, auxItem);
				}
			}

		} else if (tagName.equals("parafuso")) {
			// Adiciona na lista de itens que será agregado a
			// parteComposta e em seguida atualiza a parteComposta
			agregaItemList.add(itemTmp);
			partCompTmp.setItens(agregaItemList);
		} else if (tagName.equals("motor")) {
			// Adiciona na lista de itens que será agregado a
			// parteComposta e em seguida atualiza a parteComposta
			agregaItemList.add(itemTmp);
			partCompTmp.setItens(agregaItemList);
		} else if (tagName.equals("parteespecifica")) {
			// Adiciona na lista de itens que será agregado a
			// parteComposta e em seguida atualiza a parteComposta
			agregaItemList.add(itemTmp);
			partCompTmp.setItens(agregaItemList);
		}

	}

	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {

	}

}
