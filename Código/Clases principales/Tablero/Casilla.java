package Tablero;

import java.util.HashMap;
import java.util.Map;

import com.google.common.collect.ImmutableMap;

//import com.google.common.collect.ImmutableMap;

import Piezas.Pieza;

public abstract class Casilla {

	protected final int posCasilla;

	private static final Map<Integer, casillaVacia> Casilla_Vacia = casillasVaciasPosibles();

	private static Map<Integer, casillaVacia> casillasVaciasPosibles() {

		final Map<Integer, casillaVacia> CasillaVaciaMap = new HashMap<>();

		for (int i = 0; i < Utilidades_Tablero.NUMERO_CASILLAS_TOTALES; i++) {
			CasillaVaciaMap.put(i, new casillaVacia(i));
		}

		return ImmutableMap.copyOf(CasillaVaciaMap);
	}

	public static Casilla crearCasilla(final int coordenadasCasilla, final Pieza pieza) {

		return pieza != null ? new casillaOcupada(coordenadasCasilla, pieza) : Casilla_Vacia.get(coordenadasCasilla);
	}

	Casilla(final int posCasilla) {
		this.posCasilla = posCasilla;
	}

	
	
	public abstract boolean siCasillaOcupada();

	public abstract Pieza getPiezaCasilla();

	public static final class casillaVacia extends Casilla {

		casillaVacia(final int coordenadas) {
			super(coordenadas);

		}

		@Override
		public String toString() {
			return "-";
		}

		@Override
		public boolean siCasillaOcupada() {
			return false;
		}

		@Override
		public Pieza getPiezaCasilla() {
			return null;
		}

	}

	public static final class casillaOcupada extends Casilla {

		private final Pieza piezaCasilla;

		casillaOcupada(final int coordenadasCasilla, final Pieza piezaCasilla) {
			super(coordenadasCasilla);
			this.piezaCasilla = piezaCasilla;
		}

		@Override
		public String toString() {
			return getPiezaCasilla().getColorPieza().esBlanca() ? getPiezaCasilla().toString()
					: getPiezaCasilla().toString().toLowerCase();
		}

		@Override
		public boolean siCasillaOcupada() {
			return true;
		}

		@Override
		public Pieza getPiezaCasilla() {
			return this.piezaCasilla;
		}

	}

	public int getCoordenadasCasilla() {
		// TODO Auto-generated method stub
		return this.posCasilla;
	}
}
