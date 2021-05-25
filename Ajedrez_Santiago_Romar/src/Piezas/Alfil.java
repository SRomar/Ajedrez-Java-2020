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

public class Alfil extends Pieza {

	private final static int[] POSIBLES_COORDENADAS = { -9, -7, 7, 9 };

	public Alfil(final Color_Pieza colorPieza, final int posPieza) {
		super(posPieza, colorPieza, TipoPieza.ALFIL, true);
	}
	
	public Alfil (final Color_Pieza colorPieza,
			  final int posPieza,
			  final boolean siPrimerMovimiento) {
	super(posPieza, colorPieza, TipoPieza.TORRE, siPrimerMovimiento);
}

	@Override
	public Collection<Movimiento> calcularMovimientosLegales(Tablero tablero) {

		final List<Movimiento> movimientosLegales = new ArrayList<>();

		for (final int posiblesCoordenadas : POSIBLES_COORDENADAS) {

			int posibleCoordenadaDestino = this.posPieza;

			while (Utilidades_Tablero.siCoordenadaValida(posibleCoordenadaDestino)) {

				if (siExcluirPrimeraColumna(posibleCoordenadaDestino, posiblesCoordenadas)
						|| siExcluirOctavaColumna(posibleCoordenadaDestino, posiblesCoordenadas)) {
					break;
				}

				posibleCoordenadaDestino += posiblesCoordenadas;

				if (Utilidades_Tablero.siCoordenadaValida(posibleCoordenadaDestino)) {

					final Casilla posibleCasilla = tablero.getCasilla(posibleCoordenadaDestino);

					if (!posibleCasilla.siCasillaOcupada()) { // Si no esta ocupada, es un movimiento pasivo
						movimientosLegales
								.add(new Movimiento.MovimientoPrincipal(tablero, this, posibleCoordenadaDestino));
					} else {
						final Pieza piezaEnCasilla = posibleCasilla.getPiezaCasilla();
						final Color_Pieza colorPieza = piezaEnCasilla.getColorPieza();

						if (this.colorPieza != colorPieza) { // Si en la casilla se encuentra una pieza de un color
																// distinto, se agrega la opcion de comer
							movimientosLegales.add(new Movimiento.MovimientoPrincipalAtaque(tablero, this,
									posibleCoordenadaDestino, piezaEnCasilla));
						}
						break;
					}

				}

			}

		}
		return ImmutableList.copyOf(movimientosLegales);
	}

	private static boolean siExcluirPrimeraColumna(final int posicionActual, final int posiblePosicion) {

		return Utilidades_Tablero.PRIMERA_COLUMNA[posicionActual]
				&& ((posiblePosicion == -9) || (posiblePosicion == 7));
	}

	private static boolean siExcluirOctavaColumna(final int posicionActual, final int posiblePosicion) {
		return Utilidades_Tablero.OCTAVA_COLUMNA[posicionActual] && ((posiblePosicion == -7) || (posiblePosicion == 9));

	}

	@Override
	public String toString() {
		return TipoPieza.ALFIL.toString();
	}

	@Override
	public Alfil moverPieza(final Movimiento movimiento) {// CREA UNA NUEVA PIEZA CON LAS COORDENAS DESTINO

		return new Alfil(movimiento.getPiezaMovida().getColorPieza(), movimiento.getCoordenadasDestino());
	}

}
