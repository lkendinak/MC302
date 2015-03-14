package prodPlan;

public class Motor extends Parte {

	private float potencia;
	private float corrente;
	private int rpm;

	public Motor(int code, String name, String descrip, float value, float pot,
			float current, int rotation) {
		super(code, name, descrip, value);
		this.setPotencia(pot);
		this.setRpm(rotation);
		this.setCorrente(current);

	}

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

	public String toString() {
		String motorTostring = super.toString() + " potencia:"
				+ this.getPotencia() + " corrente:" + this.getCorrente()
				+ " rpm:" + this.getRpm();

		return motorTostring;

	}

}
