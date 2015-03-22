package prodPlan;

public class Parafuso extends Parte {

	private float comprimento;

	private float diametro;

	// Construtor
	public Parafuso(int code, String name, String descrip, float value,
			float comp, float diam) {
		super(code, name, descrip, value);
		this.setComprimento(comp);
		this.setDiametro(diam);
	}

	public float getComprimento() {
		return comprimento;
	}

	public void setComprimento(float comprimento) {
		this.comprimento = comprimento;
	}

	public float getDiametro() {
		return diametro;
	}

	public void setDiametro(float diametro) {
		this.diametro = diametro;
	}

	// Método que retorna a string no formato pedido concatenado com o método do pai
	public String toString()	{
		String parafusoToString = super.toString() + " comprimento:"+this.getComprimento()+" diametro:"+this.getDiametro();
		
		return parafusoToString;
	}
	
	@Override
	public Object accept(ProdPlanVisitor visitor) {
		return visitor.visit(this);
	}
}
