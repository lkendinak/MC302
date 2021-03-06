package prodPlan;

public class Parte implements Visitable {

	private int cod;

	private String nome;

	private String descricao;

	private float valor;

	// Construtor
	public Parte(int code, String name, String descrip, float value) {
		this.setCod(code);
		this.setNome(name);
		this.setDescricao(descrip);
		this.setValor(value);
	}
	
	//Construtor apenas com o código
	public Parte(int code) {
	this.setCod(code);
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

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String desc) {
		this.descricao = desc;
	}

	// Método que retorna a string no formato pedido
	public String toString() {
		String parteToString = "codigo:" + this.getCod() + " nome:"
				+ this.getNome() + " descricao:" + this.getDescricao() + " valor:"
				+ this.getValor();

		return parteToString;
	}

	@Override
	public Object accept(ProdPlanVisitor visitor) {
		return visitor.visit((ParteEspecifica) this);
	}

}
