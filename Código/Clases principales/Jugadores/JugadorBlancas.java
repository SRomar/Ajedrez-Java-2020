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

public class JugadorBlancas extends Jugador {

	public JugadorBlancas(Tablero tablero, Collection<Movimiento> movimientosBaseBlancas,
			Collection<Movimiento> movimientosBaseNegras) {

		super(tablero, movimientosBaseBlancas, movimientosBaseNegras);
	}

	@Override
	public Collection<Pieza> getPiezasActivas() {
		return this.tablero.getPiezasBlancas();
	}

	@Override
	public Color_Pieza getColorPieza() {

		return Color_Pieza.BLANCAS;
	}

	@Override
	public Jugador getRival() {
		// TODO Auto-generated method stub
		return this.tablero.Negras();
	}

	@Override
	protected final Collection<Movimiento> calcularEnroque(final Collection<Movimiento> jugadasLegales,
			final Collection<Movimiento> jugadasLegalesRival) {

		final List<Movimiento> posiblesEnroques = new ArrayList<>();

		if (this.reyJugador.esPrimerMovimiento() && !this.Jaque()) {
			// enroque blanco //TODO si + || - -> 4 a la casilla rey => constante ???
			if (!this.tablero.getCasilla(61).siCasillaOcupada() && !this.tablero.getCasilla(62).siCasillaOcupada()) {
				final Casilla casillaTorre = this.tablero.getCasilla(63);

				if (casillaTorre.siCasillaOcupada() && casillaTorre.getPiezaCasilla().esPrimerMovimiento()) {

					if (Jugador.siCasillaEsAtacada(61, jugadasLegalesRival).isEmpty()
							&& Jugador.siCasillaEsAtacada(62, jugadasLegalesRival).isEmpty()
							&& casillaTorre.getPiezaCasilla().getTipo().esTorre()) {

						// TODO terminar funcion
						posiblesEnroques.add(new Movimiento.EnroqueCorto(this.tablero, this.reyJugador, 62,
								(Torre) casillaTorre.getPiezaCasilla(), casillaTorre.getCoordenadasCasilla(), 61));

					}

				}
			}

			if (!this.tablero.getCasilla(59).siCasillaOcupada() && !this.tablero.getCasilla(58).siCasillaOcupada()
					&& !this.tablero.getCasilla(57).siCasillaOcupada()) {

				final Casilla casillaTorre = this.tablero.getCasilla(56);

				if (casillaTorre.siCasillaOcupada() && casillaTorre.getPiezaCasilla().esPrimerMovimiento()) {

					// TODO terminar funcion
					posiblesEnroques.add(new Movimiento.EnroqueLargo(this.tablero, this.reyJugador, 58,
							(Torre) casillaTorre.getPiezaCasilla(), casillaTorre.getCoordenadasCasilla(), 59));

				}
			}

		}

		return ImmutableList.copyOf(posiblesEnroques);

	}

}
