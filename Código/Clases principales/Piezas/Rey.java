package Piezas;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.google.common.collect.ImmutableList;

import Tablero.Casilla;
import Tablero.Movimiento;
import Tablero.Tablero;
import Tablero.Utilidades_Tablero;

public class Rey extends Pieza {

	private final static int[] POSIBLES_COORDENADAS = { -9, -8, -7, -1, 1, 7, 8, 9 };

	public Rey(final Color_Pieza colorPieza, final int posPieza) {
		super(posPieza, colorPieza, TipoPieza.REY);
	}

	@Override
	public Collection<Movimiento> calcularMovimientosLegales(Tablero tablero) {

		final List<Movimiento> movimientosLegales = new ArrayList<>();

		for (final int posiblesCoordenadas : POSIBLES_COORDENADAS) {

			final int posibleCoordenadaDestino = this.posPieza + posiblesCoordenadas;

			if (siExcluirPrimeraColumna(this.posPieza, posiblesCoordenadas)
					|| siExcluirOctavaColumna(this.posPieza, posiblesCoordenadas)) {
				continue;
			}

			if (Utilidades_Tablero.siCoordenadaValida(posibleCoordenadaDestino)) {

				final Casilla posibleCasilla = tablero.getCasilla(posibleCoordenadaDestino);

				if (!posibleCasilla.siCasillaOcupada()) {
					movimientosLegales.add(new Movimiento.MovimientoGrande(tablero, this, posibleCoordenadaDestino));
				} else {
					final Pieza piezaEnCasilla = posibleCasilla.getPiezaCasilla();
					final Color_Pieza colorPieza = piezaEnCasilla.getColorPieza();

					if (this.colorPieza != colorPieza) { // Si en la casilla se encuentra una pieza de un color
															// distinto, se valida el movimiento
						movimientosLegales.add(new Movimiento.MovimientoAtaque(tablero, this, posibleCoordenadaDestino,
								piezaEnCasilla));
					}
				}
			}

		}

		return ImmutableList.copyOf(movimientosLegales);
	}

	private static boolean siExcluirPrimeraColumna(final int posicionActual, final int posiblePosicion) {

		return Utilidades_Tablero.PRIMERA_COLUMNA[posicionActual]
				&& ((posiblePosicion == -9) || (posiblePosicion == -1) || (posiblePosicion == 7));
	}

	private static boolean siExcluirOctavaColumna(final int posicionActual, final int posiblePosicion) {
		return Utilidades_Tablero.OCTAVA_COLUMNA[posicionActual]
				&& ((posiblePosicion == -7) || (posiblePosicion == 1) || (posiblePosicion == 9));
	}

	@Override
	public String toString() {
		return TipoPieza.REY.toString();
	}

	@Override
	public Rey moverPieza(final Movimiento movimiento) {// CREA UNA NUEVA PIEZA CON LAS COORDENAS DESTINO

		return new Rey(movimiento.getPiezaMovida().getColorPieza(), movimiento.getCoordenadasDestino());
	}

}
