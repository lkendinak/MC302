package Gomoku;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JFileChooser;
import javax.swing.JPanel;

public class View extends JPanel {

	public int size;
	public char[][] board; 
	JFileChooser fc;

	public View() {
		super();
		//FileChooser para as opcoes salvar e carregar
		fc = new JFileChooser();
		size = Model.SIZE;
		board = new char[size][size];
		init();
	}

	// Inicia o painel zerando todos os campos
	public void init() {
		for (int i = 0; i < size; i++)
			for (int j = 0; j < size; j++)
				board[i][j] = ' ';
	}

	/**
	 * Pinta o Grid
	 */
	@Override
	protected void paintComponent(Graphics g1) {

		super.paintComponent(g1);

		// Configuração inicial do grid 2D com Anti-Aliasing linhas normais e de
		// cor preta
		Graphics2D g = (Graphics2D) g1;
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		g.setStroke(new BasicStroke(1));

		int x1, y1, x2, y2;

		g.setColor(Color.BLACK);
		for (int i = 0; i <= size; i++) {
			// Linhas Horizontais
			x1 = 0;

			// Usa ponto flutuante para diminuir a acumulação de erros
			y1 = (int) (i * (1.0 * this.getHeight() / size));

			// Limite para a dimensão do grid
			if (y1 >= this.getHeight())
				y1 = this.getHeight() - 1;
			x2 = this.getWidth();
			y2 = y1;
			g.drawLine(x1, y1, x2, y2);

			// Linhas Vesticais
			x1 = (int) (i * (1.0 * this.getWidth() / size));
			// Limite para a dimensão do grid
			if (x1 >= this.getWidth())
				x1 = this.getWidth() - 1;
			y1 = 0;
			x2 = x1;
			y2 = this.getHeight();
			g.drawLine(x1, y1, x2, y2);

		} 

		// Desenha os marcadores
		for (int i = 0; i < size; i++)
			for (int j = 0; j < size; j++) {
				if (board[i][j] != ' ')
					drawMarker(g, i, j);
			}

	}

	/**
	 * Desenha os marcadores para a posição[i][j], a razão xgap/ygap serve para
	 * evitar as bordas do grid
	 */
	private void drawMarker(Graphics2D g, int i, int j) {
		char marker = board[i][j];
		double xgap = this.getWidth() / (size * 6.0);
		double ygap = this.getHeight() / (size * 6.0);
		int x1 = (int) (1.0 * j * this.getWidth() / size + xgap);
		int y1 = (int) (1.0 * i * this.getHeight() / size + ygap);
		int width = (int) (1.0 * this.getWidth() / size - 2 * xgap);
		int height = (int) (1.0 * this.getHeight() / size - 2 * ygap);
		g.setStroke(new BasicStroke(2));
		if (marker == 'X')
			g.setColor(Color.BLUE);
		else
			g.setColor(Color.RED);

		// Desenha O e X no grid
		if (marker == 'O' || marker == 'o')
			g.drawOval(x1, y1, width, height);
		else if (marker == 'X' || marker == 'x') {
			int x2 = x1 + width;
			int y2 = y1 + height;
			g.drawLine(x1, y1, x2, y2);
			g.drawLine(x1, y2, x2, y1);
		}
	}

	/**
	 * Translada a coordenada y para linha de acordo com o tamanho do panel
	 */
	public int getRow(int y) {
		int row = y * size / this.getHeight();
		if (row == size)
			row = size - 1;
		return row;
	}

	/**
	 * Translada a coordenada x para Coluna de acordo com o tamanho do panel
	 */
	public int getColumn(int x) {
		int column = x * size / this.getWidth();
		if (column == size)
			column = size - 1;
		return column;
	}

}
