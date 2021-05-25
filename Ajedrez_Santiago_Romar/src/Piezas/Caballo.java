package Piezas;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.google.common.collect.ImmutableList;

import Piezas.Pieza.TipoPieza;
import Tablero.Casilla;
import Tablero.Movimiento;
import Tablero.Tablero;
import Tablero.Utilidades_Tablero;

public class Caballo extends Pieza {

	private final static int[] POSIBLE_MOVIMIENTO = { -17, -15, -10, -6, 6, 10, 15, 17 };

	public Caballo(Color_Pieza colorPieza, int posPieza) {
		super(posPieza, colorPieza, TipoPieza.CABALLO, true);
	}
	public Caballo (final Color_Pieza colorPieza,
			  final int posPieza,
			  final boolean siPrimerMovimiento) {
	super(posPieza, colorPieza, TipoPieza.TORRE, siPrimerMovimiento);
}

	@Override
	public Collection<Movimiento> calcularMovimientosLegales(final Tablero tablero) {

		final List<Movimiento> movimientosLegales = new ArrayList<>();

		for (final int casillaActual : POSIBLE_MOVIMIENTO) {

			int posibleCoordenadaDestino;

			posibleCoordenadaDestino = this.posPieza + casillaActual;

			if (Utilidades_Tablero.siCoordenadaValida(posibleCoordenadaDestino)) {

				if (siExcluirPrimeraColumna(this.posPieza, casillaActual)
						|| siExcluirSegundaColumna(this.posPieza, casillaActual)
						|| siExcluirSeptimaColumna(this.posPieza, casillaActual)
						|| siExcluirOctavaColumna(this.posPieza, casillaActual)) {
					continue;
				}

				final Casilla posibleCasilla = tablero.getCasilla(posibleCoordenadaDestino);

				if (!posibleCasilla.siCasillaOcupada()) {
					movimientosLegales.add(new Movimiento.MovimientoPrincipal(tablero, this, posibleCoordenadaDestino));
				} else {
					final Pieza piezaEnCasilla = posibleCasilla.getPiezaCasilla();
					final Color_Pieza colorPieza = piezaEnCasilla.getColorPieza();

					if (this.colorPieza != colorPieza) { // Si en la casilla se encuentra una pieza de un color
															// distinto, se valida el movimiento
						movimientosLegales.add(new Movimiento.MovimientoPrincipalAtaque(tablero, this, posibleCoordenadaDestino,
								piezaEnCasilla));
					}
				}

			}

		}

		return ImmutableList.copyOf(movimientosLegales);
	}

	private static boolean siExcluirPrimeraColumna(final int posicionActual, final int posiblePosicion) {

		return Utilidades_Tablero.PRIMERA_COLUMNA[posicionActual] && ((posiblePosicion == -17)
				|| (posiblePosicion == -10) || (posiblePosicion == 6) || (posiblePosicion == 15));
	}

	private static boolean siExcluirSegundaColumna(final int posicionActual, final int posiblePosicion) {
		return Utilidades_Tablero.SEGUNDA_COLUMNA[posicionActual]
				&& ((posiblePosicion == -10) || (posiblePosicion == 6));
	}

	private static boolean siExcluirSeptimaColumna(final int posicionActual, final int posiblePosicion) {
		return Utilidades_Tablero.SEPTIMA_COLUMNA[posicionActual]
				&& ((posiblePosicion == -6) || (posiblePosicion == 10));

	}

	private static boolean siExcluirOctavaColumna(final int posicionActual, final int posiblePosicion) {
		return Utilidades_Tablero.OCTAVA_COLUMNA[posicionActual] && ((posiblePosicion == -15) || (posiblePosicion == -6)
				|| (posiblePosicion == 10) || (posiblePosicion == 17));

	}

	@Override
	public String toString() {
		return TipoPieza.CABALLO.toString();
	}

	@Override
	public Caballo moverPieza(final Movimiento movimiento) {// CREA UNA NUEVA PIEZA CON LAS COORDENAS DESTINO

		return new Caballo(movimiento.getPiezaMovida().getColorPieza(), movimiento.getCoordenadasDestino());
	}

}
