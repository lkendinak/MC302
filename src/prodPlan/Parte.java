package prodPlan;

public class Parte {

	private int cod;

	private String nome;

	private float valor;

	public int getCod() {
		return cod;
	}

	public void setCod(int cod) {
		this.cod = cod;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public float getValor() {
		return valor;
	}

	public void setValor(float valor) {
		this.valor = valor;
	}

	public Parte(int code, String name, float value) {
		this.setCod(code);
		this.setNome(name);
		this.setValor(value);
	}

}
