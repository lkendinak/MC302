package prodPlan;

public class Motor extends Parte {

	private float potencia;
	private float corrente;
	private int rpm;

	// Construtor
	public Motor(int code, String name, String descrip, float value, float pot,
			float current, int rotation) {
		super(code, name, descrip, value);
		this.setPotencia(pot);
		this.setRpm(rotation);
		this.setCorrente(current);

	}

	// Gettlers and Setters
	public float getPotencia() {
		return potencia;
	}

	public void setPotencia(float potencia) {
		this.potencia = potencia;
	}

	public float getCorrente() {
		return corrente;
	}

	public void setCorrente(float corrente) {
		this.corrente = corrente;
	}

	public int getRpm() {
		return rpm;
	}

	public void setRpm(int rpm) {
		this.rpm = rpm;
	}

	// Método que retorna a string no formato pedido concatenado com o método do
	// pai
	public String toString() {
		String motorTostring = super.toString() + " potencia:"
				+ this.getPotencia() + " corrente:" + this.getCorrente()
				+ " rpm:" + this.getRpm();

		return motorTostring;

	}

	@Override
	public Object accept(ProdPlanVisitor visitor) {
		return visitor.visit(this);
	}

}
