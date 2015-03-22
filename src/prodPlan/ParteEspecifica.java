package prodPlan;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ParteEspecifica extends Parte {

	Map<String, String> caracateristicas;

	public ParteEspecifica(int code, String name, String descrip, float value) {
		super(code, name, descrip, value);
	}

	public void agregaCaracteristica(String nome, String conteudo)
			throws Exception {
		// Verifica se o HashMap já foi iniciado, caso negativo instancia o
		// hashMap
		if (null == caracateristicas) {
			caracateristicas = new HashMap<String, String>();
		}
		// Verifica se o nome ou conteudo são nulos, caso positivo retorna uma
		// exceçao
		if (nome == null || conteudo == null) {
			throw new Exception("Valor nulo");
		}
		// Verifica se já existe uma característica com aquele nome, caso
		// positivo joga uma exceção
		if (caracateristicas.containsKey(nome)) {
			throw new Exception("Já existe uma caracteristica com esse nome");
		}

		// Adiciona o nome/conteudo no hashMap
		caracateristicas.put(nome, conteudo);
	}

	public String caracteristica(String nome) {
		// Verifica se existe uma caracteristica com o nome fornecido, caso
		// positivo retorna o conteudo associado ao nome
		if (caracateristicas.containsKey(nome)) {
			return caracateristicas.get(nome);
		}

		// Caso não exista a o nome no Map de caracteristicas, retorna nulo
		return null;
	}

	public List<Caracteristica> listaDeCaracteristicas() {
		List<Caracteristica> list = new ArrayList<Caracteristica>();
		// Itera no HashMap, adicionando numa lista de Caracteristica
		for (Map.Entry<String, String> entry : caracateristicas.entrySet()) {
			list.add(new Caracteristica(entry.getKey(), entry.getValue()));
		}

		return list;
	}

	@Override
	public Object accept(ProdPlanVisitor visitor) {
		return visitor.visit(this);
	}

}
