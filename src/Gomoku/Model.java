package Gomoku;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Model implements ModelInterface {

	int currentPlayer;
	boolean gameOver;

	// Matriz de posições
	private int positions[][];

	public Model() {
		gameOver = false;
		this.positions = new int[SIZE][SIZE];
		iniciaPartida();
	}

	/**
	 * Inicia nova partida, 'zerando' o tabuleiro
	 */
	public void iniciaPartida() {
		gameOver = false;
		for (int i = 0; i < SIZE; i++)
			for (int j = 0; j < SIZE; j++)
				positions[i][j] = 0;
	}

	/**
	 * Salva a partida atual sob um nome, para continuar depois
	 * 
	 * @param nome
	 */
	public boolean salvaPartida(String nome) {
		try {

			FileWriter fileWriter = new FileWriter(nome);

			PrintWriter writer = new PrintWriter(fileWriter);

			for (int i = 0; i < SIZE; i++) {
				for (int j = 0; j < SIZE; j++) {
					writer.print(positions[i][j]);
				}
			}
			writer.close();
			return true;

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * Restaura a partida atual, para continuar o jogo
	 */
	public boolean carregaPartida(String nome) {

		try {
			FileReader fr;
			fr = new FileReader(nome);
			BufferedReader buffer = new BufferedReader(fr);

			for (int i = 0; i < SIZE; i++) {
				for (int j = 0; j < SIZE; j++) {
					positions[i][j] = Character.getNumericValue(buffer.read());
				}
			}
			return true;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return false;
	}

	/**
	 * Registra uma jogada.
	 * 
	 * @param jogador
	 * @param x
	 * @param y
	 * @return jogada valida (true) ou invalida (false)
	 */
	public boolean joga(int jogador, int x, int y) {
		// Se o jogo não terminou e se a jogada está dentro do grid
		if (!gameOver && x < SIZE && y < SIZE) {
			// Se a posição a ser jogada é NONE, é possível fazer a jogada, caso
			// negativo false
			if (positions[x][y] == NONE) {
				positions[x][y] = jogador;
				return true;
			}
		}

		return false;
	}

	/**
	 * Retorna a peca na posicao (x,y) : (NONE, BLACK, WHITE)
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	public int tab(int x, int y) {
		return positions[x][y];

	}

	/**
	 * Verifica se existe um vencedor (algum jogador fez '5 em linha')
	 * 
	 * @return
	 */
	public int verifica() {
		// Itera pelas linhas e colunas da matriz positions
		for (int r = 0; r < SIZE; r++) {
			for (int c = 0; c < SIZE; c++) {

				// Se a posição na matriz for diferente de NONE, verifica se
				// possui ou não vencedor
				if (positions[r][c] != NONE) {

					// Marca o player atual
					int marker = positions[r][c];

					// Itera no vetor de direções, para verificar todas as
					// direções possíveis
					for (int dir : Position.directions) {

						// Marca a posição atual
						Position atual = new Position(r, c);

						// Marca o número de pontos sequenciais de um dado
						// player
						int markerPoints = 1;

						for (int step = 1; step < LINELENG; step++) {
							atual = atual.proceed(dir);
							// Se atual for nulo está fora do grid, continua na
							// proxima direção
							if (atual == null)
								break;

							// Se o atual for igual ao player marcado, soma
							// pontos
							if (positions[atual.row][atual.column] == marker) {
								markerPoints++;
							}

							// Se o número de pontos for maior ou igual a
							// LINELENG, o vencedor é o player marcado
							if (markerPoints >= LINELENG) {
								gameOver = true;
								return marker;
							}
						}
					}
				}
			}
		}

		return NONE;
	}

	public String printTab(int i, int j, int k, int l) {
		return "";
	}
	
	// Método que copia a matriz positions
	public void copiaPosição(int[][] matrix) {
		for (int i = 0; i < SIZE; i++) {
			for (int j = 0; j < SIZE; j++) {
				matrix[i][j] = positions[i][j];
			}
		}

	}

	/**
	 * @author lkend_000 Classe auxiliar para verificar vencedor e posição
	 */
	public static class Position {

		public int row;
		public int column;

		/**
		 * Define as direções
		 */
		public static final int LESTE = 1;
		public static final int NORDESTE = 2;
		public static final int NORTE = 3;
		public static final int NOROESTE = 4;
		public static final int OESTE = 5;
		public static final int SUDESTE = 6;
		public static final int SULL = 7;
		public static final int SUDOESTE = 8;

		public final static int[] directions = { LESTE, NORDESTE, NORTE,
				NOROESTE, OESTE, SUDESTE, SULL, SUDOESTE };

		/**
		 * Construtor para posição
		 */
		public Position(int r, int c) {
			row = r;
			column = c;
		}

		/**
		 * @param direction
		 * @return Anda da posição atual para a próxima, dada uma determinada
		 *         direção se o próximo é maior que o SIZE, quer dizer que está
		 *         fora do grid e retorna null
		 */
		public Position proceed(int direction) {
			int r = this.row;
			int c = this.column;
			if (direction == LESTE || direction == NORDESTE
					|| direction == SUDOESTE)
				c++;
			if (direction == NORTE || direction == NORDESTE
					|| direction == NOROESTE)
				r--;
			if (direction == OESTE || direction == NOROESTE
					|| direction == SUDESTE)
				c--;
			if (direction == SULL || direction == SUDOESTE
					|| direction == SUDESTE)
				r++;
			if (r >= 0 && r < SIZE && c >= 0 && c < SIZE)
				return new Position(r, c);
			else
				return null;

		}

	}

	

}
