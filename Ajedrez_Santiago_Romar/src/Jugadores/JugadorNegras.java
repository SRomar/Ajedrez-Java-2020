package Jugadores;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.google.common.collect.ImmutableList;

import Piezas.Color_Pieza;
import Piezas.Pieza;
import Piezas.Torre;
import Tablero.Casilla;
import Tablero.Movimiento;
import Tablero.Tablero;

public class JugadorNegras extends Jugador {

	public JugadorNegras(Tablero tablero, Collection<Movimiento> movimientosBaseBlancas,
			Collection<Movimiento> movimientosBaseNegras) {

		super(tablero, movimientosBaseNegras, movimientosBaseBlancas);

	}

	@Override
	public Collection<Pieza> getPiezasActivas() {
		return this.tablero.getPiezasNegras();
	}

	@Override
	public Color_Pieza getColorPieza() {
		// TODO Auto-generated method stub
		return Color_Pieza.NEGRAS;
	}

	@Override
	public Jugador getRival() {
		// TODO Auto-generated method stub
		return this.tablero.Blancas();
	}

	@Override
	protected Collection<Movimiento> calcularEnroque(Collection<Movimiento> jugadasLegales,
			Collection<Movimiento> jugadasLegalesRival) {
		// TODO Auto-generated method stub

		final List<Movimiento> posiblesEnroques = new ArrayList<>();

		if (this.reyJugador.esPrimerMovimiento() && !this.Jaque()) {
			// enroque negro //TODO si + || - -> 4 a la casilla rey => constante ???
			if (!this.tablero.getCasilla(5).siCasillaOcupada() && !this.tablero.getCasilla(6).siCasillaOcupada()) {
				final Casilla casillaTorre = this.tablero.getCasilla(7);

				if (casillaTorre.siCasillaOcupada() && casillaTorre.getPiezaCasilla().esPrimerMovimiento()) {

					if (Jugador.siCasillaEsAtacada(5, jugadasLegalesRival).isEmpty()
							&& Jugador.siCasillaEsAtacada(6, jugadasLegalesRival).isEmpty()
							&& casillaTorre.getPiezaCasilla().getTipo().esTorre()) {

						// TODO terminar funcion
						posiblesEnroques.add(new Movimiento.EnroqueCorto(this.tablero, this.reyJugador, 6,
								(Torre) casillaTorre.getPiezaCasilla(), casillaTorre.getCoordenadasCasilla(), 5));

					}

				}
			}

			if (!this.tablero.getCasilla(1).siCasillaOcupada() && !this.tablero.getCasilla(2).siCasillaOcupada()
					&& !this.tablero.getCasilla(3).siCasillaOcupada()) {

				final Casilla casillaTorre = this.tablero.getCasilla(0);

				if (casillaTorre.siCasillaOcupada() && casillaTorre.getPiezaCasilla().esPrimerMovimiento() &&
						Jugador.siCasillaEsAtacada(2, jugadasLegalesRival).isEmpty() &&
						Jugador.siCasillaEsAtacada(3, jugadasLegalesRival).isEmpty() &&
						casillaTorre.getPiezaCasilla().getTipo().esTorre()) {

					// TODO terminar funcion
					posiblesEnroques.add(new Movimiento.EnroqueLargo(this.tablero, this.reyJugador, 2,
							(Torre) casillaTorre.getPiezaCasilla(), casillaTorre.getCoordenadasCasilla(), 3));

				}
			}
		}

		return ImmutableList.copyOf(posiblesEnroques);
	}
}
