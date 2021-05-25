package Tablero;

import java.util.HashMap;
import java.util.Map;

import com.google.common.collect.ImmutableMap;


public class Utilidades_Tablero {

	
	public static final boolean[] PRIMERA_COLUMNA = inicializarColumna(0);
	public static final boolean[] SEGUNDA_COLUMNA = inicializarColumna(1);
	public static final boolean[] SEPTIMA_COLUMNA = inicializarColumna(6);
	public static final boolean[] OCTAVA_COLUMNA = inicializarColumna(7);
	
	public static final boolean[] OCTAVA_FILA = inicializarFila(0);
	public static final boolean[] SEPTIMA_FILA = inicializarFila(8);
	public static final boolean[] SEXTA_FILA = inicializarFila(16);
	public static final boolean[] QUINTA_FILA = inicializarFila(24);
	public static final boolean[] CUARTA_FILA = inicializarFila(32);
	public static final boolean[] TERCER_FILA = inicializarFila(40);
	public static final boolean[] SEGUNDA_FILA = inicializarFila(48);
	public static final boolean[] PRIMERA_FILA = inicializarFila(56);
	
	
	private static final String[] ALGEBREIC_NOTATION = inicializarNotacionAlgebraica();
	private static final Map<String, Integer> POSICION_A_COORDENAS = inicializarPosicionCoordenadasMap();


	private Utilidades_Tablero() {
		throw new RuntimeException("Utilidades Tablero no debe ser inicializada.");
	}

	private static Map<String, Integer> inicializarPosicionCoordenadasMap() {

		final Map<String, Integer> posicionCoordenadas = new HashMap<>();
		
		for(int i = 0; i < NUMERO_CASILLAS_TOTALES; i++) {
			posicionCoordenadas.put(ALGEBREIC_NOTATION[i], i);
		}
		
		return ImmutableMap.copyOf(posicionCoordenadas);
	}

	private static String[] inicializarNotacionAlgebraica() {
		// TODO Auto-generated method stub
		return  new String[] {
			"a8", "b8", "c8", "d8", "e8", "f8", "g8", "h8",
            "a7", "b7", "c7", "d7", "e7", "f7", "g7", "h7",
            "a6", "b6", "c6", "d6", "e6", "f6", "g6", "h6",
            "a5", "b5", "c5", "d5", "e5", "f5", "g5", "h5",
            "a4", "b4", "c4", "d4", "e4", "f4", "g4", "h4",
            "a3", "b3", "c3", "d3", "e3", "f3", "g3", "h3",
            "a2", "b2", "c2", "d2", "e2", "f2", "g2", "h2",
            "a1", "b1", "c1", "d1", "e1", "f1", "g1", "h1"
		};
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
	


	public static int CoordenadasPosicion (final String posicion) {
		return POSICION_A_COORDENAS.get(posicion);
	}


	public static String posicionCoordenadas(final int coordenadas) {
		
	
		
		return ALGEBREIC_NOTATION[coordenadas];
	}
	
	

}
