package Tablero;

import javax.management.RuntimeErrorException;

public class Utilidades_Tablero {

	public static final boolean[] PRIMERA_COLUMNA = inicializarColumna(0);
	public static final boolean[] SEGUNDA_COLUMNA = inicializarColumna(1);
	public static final boolean[] SEPTIMA_COLUMNA = inicializarColumna(6);
	public static final boolean[] OCTAVA_COLUMNA = inicializarColumna(7);

	public static final boolean[] SEGUNDA_FILA = inicializarFila(8);
	public static final boolean[] SEPTIMA_FILA = inicializarFila(48);

	private Utilidades_Tablero() {
		throw new RuntimeException("Utilidades Tablero no debe ser inicializada.");
	}

	private static boolean[] inicializarFila(int numeroFila) {

		final boolean[] fila = new boolean[NUMERO_CASILLAS_TOTALES];

		do {
			fila[numeroFila] = true;
			numeroFila++;

		} while (numeroFila % NUMERO_CASILLAS_POR_FILA != 0);

		return fila;
	}

	public static final int NUMERO_CASILLAS_TOTALES = 64;
	public static final int NUMERO_CASILLAS_POR_FILA = 8;

	private static boolean[] inicializarColumna(int numeroColumna) {

		final boolean[] columna = new boolean[NUMERO_CASILLAS_TOTALES];

		do {
			columna[numeroColumna] = true;
			numeroColumna += NUMERO_CASILLAS_POR_FILA;

		} while (numeroColumna < NUMERO_CASILLAS_TOTALES);

		return columna;
	}

	public static boolean siCoordenadaValida(final int coordenadasPosibleCasilla) {

		return coordenadasPosibleCasilla >= 0 && coordenadasPosibleCasilla < 64;
	}

}
