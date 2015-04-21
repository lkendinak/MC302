package lab06;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Lab06 {

	public static void main(String[] args) {
		Lab06 lab06 = new Lab06();
		lab06.run();
	}

	public void run() {
		String csvFile = "alunos.csv";
		 
		
		BufferedReader buffer = null;
		String line = "";
		String cvsSplitBy = ";";

		try {
			// Map para Name/NRep, Rg/NRep
			Map<String, Integer> mapName = new HashMap<String, Integer>();
			Map<String, Integer> mapRg = new HashMap<String, Integer>();

			// Map Curso/NAlunos
			Map<String, Integer> mapCurs = new HashMap<String, Integer>();

			// Contadores para Homonimos de nome e documento
			int countHom = 0;
			int countDoc = 0;

			// Buffer do arquivo csv
			buffer = new BufferedReader(new FileReader(csvFile));
			// Itera linha por linha
			while ((line = buffer.readLine()) != null) {

				// usa ponto e virgula como separador e coloca no vetor de
				// Strings alunos
				String[] alunos = line.split(cvsSplitBy);

				// Ignora a primeira linha com o nome das colunas
				if (alunos[0].equals("nome")) {
					continue;
				}

				// Map Name
				// Se mapName está vazio atualiza com o primeiro valor
				// A repetição sempre é iniciada como zero já que quando é
				// inserido um novo dado no Map, ainda não houve nenhuma
				// repetição
				if (mapName.isEmpty()) {
					mapName.put(alunos[0], 0);
				} else {
					// Se ja existe no Map, atualiza o numero de vzs que repete,
					// se não insere um novo
					if (mapName.containsKey(alunos[0])) {
						mapName.put(alunos[0], mapName.get(alunos[0]) + 1);
					} else {
						mapName.put(alunos[0], 0);
					}
					// Conta o número de homonimos
					if (mapName.get(alunos[0]) >= 2) {
						countHom++;
					}
				}

				// Map Rg
				// Se mapRg está vazio atualiza com o primeiro valor
				// A repetição sempre é iniciada como zero já que quando é
				// inserido um novo dado no Map, ainda não houve nenhuma
				// repetição
				if (mapRg.isEmpty()) {
					mapRg.put(alunos[1], 0);
				} else {
					// Se ja existe no Map, atualiza o numero de vzs que repete,
					// se não insere um novo
					if (mapRg.containsKey(alunos[1])) {
						mapRg.put(alunos[1], mapRg.get(alunos[1]) + 1);
					} else {
						mapRg.put(alunos[1], 0);
					}
					// Conta o número de homonimos
					if (mapRg.get(alunos[1]) > 1) {
						countDoc++;
					}
				}

				// Map Curso
				// Se mapCurs está vazio atualiza com o primeiro valor
				// Em mapCurs, mapeamos o curso com o numero de alunos, logo
				// quando um novo dado é inserido, quer dizer que existe pelo
				// menos um aluno no curso
				if (mapCurs.isEmpty()) {
					mapCurs.put(alunos[3], 1);
				} else {
					// Se ja existe no Map, atualiza o numero de alunos,
					// se não insere um novo
					if (mapCurs.containsKey(alunos[3])) {
						mapCurs.put(alunos[3], mapCurs.get(alunos[3]) + 1);
					} else {
						mapCurs.put(alunos[3], 1);
					}
				}

			}

			// Impressão para Homonimos de nomes
			System.out.println("Homonimos: " + countHom);
			for (Map.Entry<String, Integer> entry : mapName.entrySet()) {
				if (entry.getValue() >= 2) {
					System.out.println(entry.getKey() + " " + entry.getValue());
				}
			}
			// Impressão para Documentos
			System.out.println("\nDocumentos repetidos: " + countDoc);
			for (Map.Entry<String, Integer> entry : mapRg.entrySet()) {
				if (entry.getValue() >= 2) {
					System.out.println(entry.getKey() + " " + entry.getValue());
				}
			}
			// Impressão para Cursos
			System.out.println("\nCursos: " + (mapCurs.size()));
			for (Map.Entry<String, Integer> entry : mapCurs.entrySet()) {
				System.out.println(entry.getKey() + " " + entry.getValue());
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			// Ao final fecha o buffer
			if (buffer != null) {
				try {
					buffer.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
