package prodPlan;

public abstract class Visitor {
	
	public abstract Object visit(ParteEspecifica parte);
	public abstract Object visit(Motor motor);
	public abstract Object visit(Parafuso parafuso);
	public abstract Object visit(ParteComposta parte);
	public abstract Object visit(Item item);

}
