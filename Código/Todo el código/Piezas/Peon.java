package Piezas;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.google.common.collect.ImmutableList;

import Tablero.Movimiento;
import Tablero.Tablero;
import Tablero.Utilidades_Tablero;

public class Peon extends Pieza {

	private final static int[] POSIBLES_COORDENADAS = { 7, 8, 9, 16 };

	public Peon(final Color_Pieza colorPieza, final int posPieza) {
		super(posPieza, colorPieza, TipoPieza.PEON);
	}

	@Override
	public Collection<Movimiento> calcularMovimientosLegales(Tablero tablero) {

		final List<Movimiento> movimientosLegales = new ArrayList<>();

		for (final int posiblesCoordenadas : POSIBLES_COORDENADAS) {

			final int posibleCoordenadaDestino = this.posPieza
					+ (this.getColorPieza().getDireccion() * posiblesCoordenadas);

			if (!Utilidades_Tablero.siCoordenadaValida(posibleCoordenadaDestino)) {
				continue;
			}
			if (posiblesCoordenadas == 8 && !tablero.getCasilla(posibleCoordenadaDestino).siCasillaOcupada()) {
				movimientosLegales.add(new Movimiento.MovimientoGrande(tablero, this, posibleCoordenadaDestino));

			}

			else if (posiblesCoordenadas == 16 && this.esPrimerMovimiento()
					&& (Utilidades_Tablero.SEGUNDA_FILA[this.posPieza] && this.colorPieza.esNegra())
					|| (Utilidades_Tablero.SEPTIMA_FILA[this.posPieza] && this.colorPieza.esBlanca())) {

				final int casillaAnteriorSalto = this.posPieza + (this.colorPieza.getDireccion() * 8);

				if (!tablero.getCasilla(casillaAnteriorSalto).siCasillaOcupada()
						&& !tablero.getCasilla(posibleCoordenadaDestino).siCasillaOcupada()) // para hacer el salto de
																								// peon no debe haber
																								// una pieza enfrente
				{
					movimientosLegales.add(new Movimiento.MovimientoGrande(tablero, this, posibleCoordenadaDestino));
				}

			} else if (posiblesCoordenadas == 7
					&& !((Utilidades_Tablero.OCTAVA_COLUMNA[this.posPieza] && this.colorPieza.esBlanca())
							|| (Utilidades_Tablero.PRIMERA_COLUMNA[this.posPieza] && this.colorPieza.esNegra()))) {
				if (tablero.getCasilla(posibleCoordenadaDestino).siCasillaOcupada()) {
					final Pieza piezaEnCoordenadas = tablero.getCasilla(posibleCoordenadaDestino)
							.getPiezaCasilla();
					if (this.colorPieza != piezaEnCoordenadas.getColorPieza()) {

						movimientosLegales
								.add(new Movimiento.MovimientoGrande(tablero, this, posibleCoordenadaDestino));

					}
				}
			} else if (posiblesCoordenadas == 9
					&& !((Utilidades_Tablero.PRIMERA_COLUMNA[this.posPieza] && this.colorPieza.esBlanca())
							|| (Utilidades_Tablero.OCTAVA_COLUMNA[this.posPieza] && this.colorPieza.esNegra()))) {
				if (tablero.getCasilla(posibleCoordenadaDestino).siCasillaOcupada()) {
					final Pieza piezaEnCoordenadas = tablero.getCasilla(posibleCoordenadaDestino)
							.getPiezaCasilla();
					if (this.colorPieza != piezaEnCoordenadas.getColorPieza()) {

						movimientosLegales
								.add(new Movimiento.MovimientoGrande(tablero, this, posibleCoordenadaDestino));

					}
				}
			}

		}

		return ImmutableList.copyOf(movimientosLegales);
	}

	@Override
	public String toString() {
		return TipoPieza.PEON.toString();
	}

	@Override
	public Peon moverPieza(final Movimiento movimiento) {// CREA UNA NUEVA PIEZA CON LAS COORDENAS DESTINO

		return new Peon(movimiento.getPiezaMovida().getColorPieza(), movimiento.getCoordenadasDestino());
	}

}
