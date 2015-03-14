package br.com.lab01;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Fracao {

	// Numerador
	private int n;

	// Denominador
	private int d;

	//Construtor que ja simplifica caso necessario
	public Fracao(int i, int j) {
		int c = Math.abs(mdc(i, j));
		this.setN(i / c);
		this.setD(j / c);
	}

	//Gettlers and Settlers para o numerador e denominador
	public int getN() {
		return n;
	}

	public void setN(int n) {
		this.n = n;
	}

	public int getD() {
		return d;
	}

	public void setD(int d) {
		this.d = d;
	}

	// metodo de soma
	public Fracao add(Fracao f) {

		// denR -> denominador resultante -> d1*d2
		int denR = this.getD() * f.getD();
		// numR -> numerador resultante -> ((denR/d1)*n1 + (denR/d2)*n2)
		int numR = ((denR / this.getD()) * this.getN() + (denR / f.getD())
				* f.getN());
		
		//simplifica e retorna
		Fracao result = new Fracao(numR, denR);
		simplify(result);
		return result;
	}

	//metodo de subtraçao
	public Fracao sub(Fracao f) {
		// denR -> denominador resultante -> d1*d2
		int denR = this.getD() * f.getD();
		// numR -> numerador resultante -> ((denR/d1)*n1 + (denR/d2)*n2)
		int numR = ((denR / this.getD()) * this.getN() - (denR / f.getD())
				* f.getN());

		//simplifica e retorna
		Fracao result = new Fracao(numR, denR);
		simplify(result);
		return result;

	}

	//metodo de multiplicacao
	public Fracao mult(Fracao f) {

		Fracao result = new Fracao((this.getN() * f.getN()),
				(this.getD() * f.getD()));
		simplify(result);
		return result;

	}

	//metodo de divisao
	public Fracao div(Fracao f) {
		//Pode - se dizer que a divisao de 2 fraçoes representa a multiplicacao
		//da primeira fracao com a inversa da segunda
		Fracao result = new Fracao((this.getN() * f.getD()),
				(this.getD() * f.getN()));
		simplify(result);

		return result;
	}

	//metodo que converte para float
	public float toFloat() {
		//Aplica o cast para float na divisao e retorna
		return (float) this.getN() / this.getD();
		
	}

	//metodo que converte para string
	public String toString() {
		//Concatena os termos da string com os valores da fracao e retorna
		return "( " + String.valueOf(this.getN()) + " / "
				+ String.valueOf(this.getD()) + " )";

	}

	//metodo que compara frações
	public int compareTo(Fracao f) {
		//Transforma as frações em float
		float f1 = this.toFloat();
		float f2 = f.toFloat();
		
		// Retorna -1, zero ou 1 se o resultado da comparação for ‘menor’,
		// ‘igual’ ou maior, respectivamente
		//
		if (f1 == f2) {
			return 0;
		} else if (f1 > f2) {
			return 1;
		} else {
			return -1;
		}

	}
	
	//metodo privado que retorna o maximo divisor comum
	private static int mdc(int a, int b) {
	    return b == 0 ? a : mdc(b, a % b);
	}

	//metodo privado que simplifica a fraçao
	private static void simplify(Fracao f) {
	    int a = f.getN();
	    int b = f.getD();
		int c = Math.abs(mdc(a, b));
		f.setN(a / c);
		f.setD(b / c);
	}
	
	
	
	//TODO Complemento
	
	public static Fracao geratriz(String dizima) {
		float error = 10000;
		
		//Splita para o termo anterior da virgula
		String[] aux = dizima.split("\\.");
		
		//Separa o termo recorrente que está entre parenteses
		int startIndex = aux[1].indexOf("(");
		int endIndex = aux[1].indexOf(")");
		String recurring = aux[1].substring(startIndex+1, endIndex);
		
		//Regex para retornar todos os numeros após a virgula(termos decimais)
		Pattern p = Pattern.compile("(\\d+)");
		Matcher m = p.matcher(aux[1]);
		
		//Concatena todos os termos da sequencia correta.
		String test = aux[0]+".";
		while (m.find()) {
			test = test + m.group(1);
		}
		//repete o termo recorrente de acordo com o nivel de erro para transforma-lo em float
		while (error > 1){
			test = test + recurring;
			error = error/10;
		}
		
		//Parse da string do numero para float
		float num = Float.parseFloat(test);
		
		//Cria a Fracao de retorna usando a funcao que recebe a dizima e um nivel de erro
		Fracao result =float_to_fraction(num, (float)0.001);
		
		simplify(result);	
		return result;

	}

	
	private static Fracao float_to_fraction (float x, float error)	{
	    
		double n = Math.floor(x);
	    x -= n;
	    if (x < error)	{
	        return new Fracao((int) n, 1);
	    } else if (1 - error < x){
	        return new Fracao((int) (n+1), 1);
	    }
	    // The lower fraction is 0/1
	    int lower_n = 0;
	    int lower_d = 1;
	    // The upper fraction is 1/1
	    int upper_n = 1;
	    int upper_d = 1;
	    while (true)	{
	        // The middle fraction is (lower_n + upper_n) / (lower_d + upper_d)
	        int middle_n = lower_n + upper_n;
	        int middle_d = lower_d + upper_d;
	        // If x + error < middle
	        if (middle_d * (x + error) < middle_n)	{
	            // middle is our new upper
	            upper_n = middle_n;
	            upper_d = middle_d;
	        // Else If middle < x - error
	        } else if (middle_n < (x - error) * middle_d)	{
	            // middle is our new lower
	            lower_n = middle_n;
	            lower_d = middle_d;
	        // Else middle is our best fraction
	        } else{
	            return new Fracao ((int) (n * middle_d + middle_n), middle_d);
	        }
	    }
	}

	public String dizima() {
		float number = (float) this.getN() / this.getD();
		String[] strNumber = String.valueOf(number).split("\\.");
		int aux = strNumber[1].length();
//		strNumber[1].
		
		float x = (float) this.getN() * 9;
	    float z = x;
	    double k = 1;
	    while ((z%this.getD()) != 0)	{
	        z = z * 10 + x;
	        k += 1;
	    }
		return "a";
	}
	
 	
}
