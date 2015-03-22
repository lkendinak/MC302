package prodPlan;

public class Parte implements Visitable {

	int cod;

	String nome;

	String descricao;

	float valor;

	// Construtor
	public Parte(int code, String name, String descrip, float value) {
		this.setCod(code);
		this.setNome(name);
		this.setDesc(descrip);
		this.setValor(value);
	}

	// Gettlers and Setters
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

	public float calculaValor() {
		return this.getValor();
	}

	public String getDesc() {
		return descricao;
	}

	public void setDesc(String desc) {
		this.descricao = desc;
	}

	// Método que retorna a string no formato pedido
	public String toString() {
		String parteToString = "codigo:" + this.getCod() + " nome:"
				+ this.getNome() + " descricao:" + this.getDesc() + " valor:"
				+ this.getValor();

		return parteToString;
	}

	@Override
	public Object accept(ProdPlanVisitor visitor) {
		return visitor.visit((ParteEspecifica) this);
	}

}
