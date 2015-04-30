package Ativ7;

public class MultithreadSorter<T> implements Runnable {

	// Teto para execução do bubbleSort
	private final int M = 7;

	// Lista a ser ordenada
	private T[] list;

	// Comparator utilizado
	private Comparator<T> comp;

	// Posições inicial e final para a ordenação do vetor
	private int ini, fim;

	// Construtor inicial
	public MultithreadSorter(T[] lista, Comparator<T> comparator) {
		this.list = lista;
		this.comp = comparator;
	}

	// Overload do construtor inicial, modificado apra receber os indices
	// inicial e final da lista
	public MultithreadSorter(T[] lista, Comparator<T> comparator, int i, int j) {
		this.list = lista;
		this.comp = comparator;
		this.ini = i;
		this.fim = j;
	}

	// Método sort() inicial
	public void sort() throws InterruptedException {
		// Os valores ini e fim são inicializados para a primeira chamada que
		// utiliza o construtor inicial
		this.ini = 0;
		this.fim = this.list.length - 1;

		// Inicia a primeira thread
		Thread t1 = new Thread(new MultithreadSorter<T>(list, comp, this.ini,
				this.fim));
		t1.start();
		t1.join();
	}

	// Override do método run(), que executa sempre que uma thread é startada,
	// executa o quicksort concorrente com os parâmetros inicializados no
	// construtor
	@Override
	public void run() {
		try {
			quickSort(this.list, this.ini, this.fim);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private void quickSort(T[] array, int start, int end)
			throws InterruptedException {
		int left = start;
		int right = end;

		if (array.length < M) {
			bubbleSort(array, start, end);
			return;
		}
		T pivot = array[(left + right) / 2];

		while (left <= right) {

			// Enquanto o valor apontado por left for menor que o pivot,
			// atualiza o ponteiro
			while (this.comp.precede(array[left], pivot)) {
				left++;
			}

			// Equanto o valor apontado por right for maior que o pivot,
			// atualiza o ponteiro
			while (this.comp.precede(pivot, array[right])) {
				right--;
			}

			// Se encontrarmos algum valor na lista esquerda que é maior que o
			// pivot e se encontrarmos um valor na lista direita que é menor que
			// o pivot entao nós trocamos os valores, e em seguida atuliza os
			// ponteiros
			if (left <= right) {
				swap(list, left, right);
				left++;
				right--;
			}
		}

		// Cria uma thread nova para a lista esquerda
		if (start < right) {
			Thread ts1 = new Thread(new MultithreadSorter<T>(list, comp, start,
					right));
			ts1.start();

			// O join é necessário pois como na chamada recursiva, a thread deve
			// terminar de executar para podermos garantir a corretude dos dados
			// antes de executarmos para a lista direita
			ts1.join();
		}

		// Cria uma thread nova para a lista direita
		if (left < end) {
			Thread ts2 = new Thread(new MultithreadSorter<T>(list, comp, left,
					end));
			ts2.start();
			// O join é necessário pois como na chamada recursiva, a thread deve
			// terminar de executar para podermos garantir a corretude dos dados
			ts2.join();
		}

	}

	public void bubbleSort(T[] array, int start, int end) {
		boolean inOrder = false;
		while (!inOrder) {
			inOrder = true;
			for (int i = start + 1; i < end; i++) {
				if (this.comp.precede(array[i], array[i - 1])) {
					inOrder = false;
					swap(array, i, i - 1);
				}
			}
		}
	}

	private void swap(T[] array, int i, int j) {
		T aux = array[i];
		array[i] = array[j];// = a[j];
		array[j] = aux;
	}

}
