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
		// TODO este método associa uma ‘caracteristica específica’ ao objeto
		// ParteEspecífica, na forma
		// (valor,conteúdo). Caso um dos parâmetros seja igual a null ou caso o
		// objeto já tenha uma
		// característica do mesmo nome, este método deve gerar uma exceção.
		if (null == caracateristicas) {
			caracateristicas = new HashMap<String, String>();
		}
		if (nome == null || conteudo == null) {
			throw new Exception("Valor nulo");
		}
		if (caracateristicas.containsKey(nome)) {
			throw new Exception("Já existe uma caracteristica com esse nome");
		}
		caracateristicas.put(nome, conteudo);
	}

	public String caracteristica(String nome) {
		// este método retorna o ‘conteúdo’ associado a uma característica,
		// definida pelo seu nome.
		// Caso o objeto não tenha a característica desejada, o método deve
		// retornar null.
		if (caracateristicas.containsKey(nome)) {
			return caracateristicas.get(nome);
		}
		return null;
	}

	public List<Caracteristica> listaDeCaracteristicas() {
		List<Caracteristica> list = new ArrayList<Caracteristica>();
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
