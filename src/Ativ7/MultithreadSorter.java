package Ativ7;


public class MultithreadSorter<T> implements Runnable {

	private final int M = 7;
	private T[] list;
	private Comparator<T> comp;
	private int ini, fim;
	
	public MultithreadSorter(T[] lista, Comparator<T> comparator) {
		this.list = lista;
		this.comp = comparator;
	}
	
	public MultithreadSorter(T[] lista, int i, int j) {
		this.list = lista;
		this.ini = i;
		this.fim = j;
	}

	public void sort() throws InterruptedException {
		Thread t1 = new Thread(new MultithreadSorter<T>(list, comp));
		t1.start();
		t1.join();
	}

	@Override
	public void run() {
		quickSort(this.list, 0, this.list.length - 1);
	}

	private void quickSort(T[] array, int start, int end) {
		T pivot = array[start]; // consider this as hole at inList[start],
		int leftPointer = start;
		int rightPointer = end;
		final int LEFT = 1;
		final int RIGHT = -1;
		int pointerSide = RIGHT; // we start with right as pivot is from left

		while (leftPointer != rightPointer) {
			if (pointerSide == RIGHT) {
				if (this.comp.precede(array[rightPointer], pivot)) {
					array[leftPointer] = array[rightPointer];
					leftPointer++;
					pointerSide = LEFT;
				} else {
					rightPointer--;
				}
			} else if (pointerSide == LEFT) {
				if (this.comp.precede(pivot, array[leftPointer])) {
					array[rightPointer] = array[leftPointer];
					rightPointer--;
					pointerSide = RIGHT;
				} else {
					leftPointer++;
				}
			}
		}

		// put the pivot where leftPointer and rightPointer collide i.e.
		// leftPointer == rightPointer
		array[leftPointer] = pivot;

		if ((leftPointer - start) > M) {
			quickSort(array, start, leftPointer - 1);

		} else {
			bubbleSort(array, start, leftPointer - 1);
		}

		if ((end - leftPointer) > M) {
			quickSort(array, leftPointer + 1, end);
		} else {
			bubbleSort(array, leftPointer + 1, end);
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
