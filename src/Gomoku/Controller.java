package Gomoku;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Controller extends JFrame {

	int currentPlayer;
	View playgrid; 
	boolean gameOver;
	Model model;
	Map<Integer, Character> mapPlayer;

	public Controller() {
		model = new Model();

		playgrid = new View();
		int panelsize = setPanelSize(Model.SIZE);
		this.setSize(panelsize, panelsize + 20);

		// Seta o Título do panel, bem como a localização inicial do centro da
		// tela bem como o botão exit
		this.setTitle("Gomuku MC302");
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Mapeia os player para os respectivos caracteres
		mapPlayer = new LinkedHashMap<Integer, Character>();
		mapPlayer.put(Model.BLACK, 'X');
		mapPlayer.put(Model.WHITE, 'O');

		currentPlayer = Model.BLACK;

		// Mouse listener que toma as decisões quando o mouse é pressionado
		playgrid.addMouseListener(new MouseAdapter() {

			public void mousePressed(MouseEvent e) {

				// Converte a coordenada Y para linha i e a coordenada X para a
				// coluna j
				int i = playgrid.getRow(e.getY());
				int j = playgrid.getColumn(e.getX());

				// Faz a jogada dado as coordenadas i, j com o respectivo player
				executaJogada(currentPlayer, i, j);
				// Verifica se o jogo terminou
				isGameOver();

				playgrid.repaint(); // sufficient to repaint the playgrid rather
									// than all of this JFrame
			}

		});

		// Configura os botões
		Font bbFont = new Font("Dialog", Font.PLAIN, 12);
		// Cria barra de botoes
		JPanel buttonBar = new JPanel();

		// Cria os botoes
		JButton jbtReset = new JButton("Novo Jogo");
		JButton jbtSave = new JButton("Salvar");
		JButton jbtLoad = new JButton("Carregar");

		// Configra fonte dos botoes
		jbtReset.setFont(bbFont);
		jbtSave.setFont(bbFont);
		jbtLoad.setFont(bbFont);

		// Adiciona os botoes à barra de botoes
		buttonBar.add(jbtReset);
		buttonBar.add(jbtSave);
		buttonBar.add(jbtLoad);

		// listeners dos botoes
		jbtReset.addActionListener(new ResetListener());
		jbtSave.addActionListener(new SaveListener());
		jbtLoad.addActionListener(new LoadListener());

		JPanel contentPane = new JPanel();
		contentPane.setLayout(new BorderLayout(2, 2));
		contentPane.add(playgrid, BorderLayout.CENTER); 
		contentPane.add(buttonBar, BorderLayout.SOUTH); 

		this.add(contentPane);
	}

	private void executaJogada(int jogador, int x, int y) {
		if (model.joga(jogador, x, y)) {
			playgrid.board[x][y] = mapPlayer.get(jogador);
			playgrid.repaint();
			// Muda para o próximo jogador
			if (currentPlayer == Model.BLACK) {
				currentPlayer = Model.WHITE;
			} else {
				currentPlayer = Model.BLACK;
			}
		}
	}

	/**
	 * Verifica se o jogo terminou
	 */
	private void isGameOver() {
		int v = model.verifica();

		if (v != Model.NONE) {
			gameOver = true;
			if (v == Model.BLACK) {
				JOptionPane.showMessageDialog(playgrid, "Jogador \""
						+ mapPlayer.get(Model.BLACK).toString() + "\" venceu!");
			} else {
				JOptionPane.showMessageDialog(playgrid, "Jogador \""
						+ mapPlayer.get(Model.WHITE).toString() + "\" venceu!");
			}
		}
		gameOver = false;
	}

	private int setPanelSize(int size) {
		// Seta o tamanho do panel
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Dimension screen = toolkit.getScreenSize();
		int screenheight = (int) screen.getHeight();
		int screenwidth = (int) screen.getWidth();
		int maxpanelsize = (int) (0.8 * screenheight);
		if (screenwidth < screenheight)
			maxpanelsize = (int) (0.8 * screenwidth);
		if (size * 60 > maxpanelsize)
			return maxpanelsize;
		else
			return size * 60;

	}

	// Listener para o botao reset, inicia uma nova partida, limpa o grid
	class ResetListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			model.iniciaPartida();
			playgrid.init();
			gameOver = false;
			playgrid.repaint();
		}
	}

	// Listener para o botão salvar, carrega o JFileChooser, e chama a funcao
	// salva partida do Model, em seguida manda uma mensagem para o usuario de
	// sucesso
	class SaveListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			int returnVal = playgrid.fc.showSaveDialog(playgrid);
			if (returnVal == playgrid.fc.APPROVE_OPTION) {
				File file = playgrid.fc.getSelectedFile();
				if (model.salvaPartida(file.getPath())) {
					JOptionPane.showMessageDialog(playgrid,
							"Partida salva com sucesso!");
				} else {
					JOptionPane.showMessageDialog(playgrid,
							"Não foi possível salvar o jogo.");
				}
			}
		}
	}

	// Listener para o botão carregar, carrega o JFileChooser, e chama a funcao
	// carregar partida do Model, em seguida remapeia o grid com os dados
	// carregados e depois manda uma mensagem para o usuario de
	// sucesso
	class LoadListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			int returnVal = playgrid.fc.showOpenDialog(playgrid);

			if (returnVal == playgrid.fc.APPROVE_OPTION) {
				File file = playgrid.fc.getSelectedFile();
				// Zera o model antes de carregar o arquivo
				model.iniciaPartida();
				if (model.carregaPartida(file.getPath())) {
					reMap();
					playgrid.repaint();
					JOptionPane.showMessageDialog(playgrid,
							"Partida carregada com sucesso!");
					isGameOver();

				} else {
					JOptionPane.showMessageDialog(playgrid,
							"Não foi possível carregar o jogo.");
				}
			}
		}
	}

	// Método que remapeia o board do grid no View
	private void reMap() {
		int[][] matrix = new int[Model.SIZE][Model.SIZE];
		// método que copia a matriz positions do model
		model.copiaPosição(matrix);
		for (int i = 0; i < Model.SIZE; i++) {
			for (int j = 0; j < Model.SIZE; j++) {
				if (matrix[i][j] != model.NONE) {
					playgrid.board[i][j] = mapPlayer.get(matrix[i][j]);
				} else {
					playgrid.board[i][j] = ' ';
				}
			}
		}
	}

	public static void main(String[] args) {

		Controller frame = new Controller();
		frame.setVisible(true);
	}

}
