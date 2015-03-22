package prodPlan;

public class Caracteristica {
	
	private String nome;
	private String conteudo;
	
	//Construtor
	public Caracteristica(String n, String c)	{
		this.setNome(n);
		this.setConteudo(c);
	}
	
	//gettlers and settlers
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getConteudo() {
		return conteudo;
	}
	public void setConteudo(String conteudo) {
		this.conteudo = conteudo;
	}
	
	public String toString()	{
		//TODO
		
		return "a";
	}

}
