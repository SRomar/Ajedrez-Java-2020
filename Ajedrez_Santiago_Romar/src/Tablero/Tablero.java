package Tablero;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;

import Jugadores.Jugador;
import Jugadores.JugadorBlancas;
import Jugadores.JugadorNegras;
import Piezas.Alfil;
import Piezas.Caballo;
import Piezas.Color_Pieza;
import Piezas.Dama;
import Piezas.Peon;
import Piezas.Pieza;
import Piezas.Rey;
import Piezas.Torre;

public class Tablero {

	private final List<Casilla> tableroJuego;
	private final Collection<Pieza> piezasBlancas;
	private final Collection<Pieza> piezasNegras;

	private final JugadorBlancas jBlancas;
	private final JugadorNegras jNegras;
	private final Jugador jugadorActual;
	
	private final Peon peonPasado;

	private Tablero(final Constructor constructor) {

		this.tableroJuego = crearTableroJuego(constructor);//creo el "tablero" base para poner las piezas
		this.piezasBlancas = calcularPiezasActivas(this.tableroJuego, Color_Pieza.BLANCAS);//
		this.piezasNegras = calcularPiezasActivas(this.tableroJuego, Color_Pieza.NEGRAS);
		this.peonPasado = constructor.peonPasado;

		final Collection<Movimiento> movimientosBaseBlancas = calcularMovimientosLegales(this.piezasBlancas);
		final Collection<Movimiento> movimientosBaseNegras = calcularMovimientosLegales(this.piezasNegras);

		this.jBlancas = new JugadorBlancas(this, movimientosBaseBlancas, movimientosBaseNegras);
		this.jNegras = new JugadorNegras(this, movimientosBaseBlancas, movimientosBaseNegras);
		this.jugadorActual = constructor.siguienteMovimiento.elegirJugador(this.jBlancas, this.jNegras);
		
	}

	@Override
	public String toString() {//para visualizarlo en la consola
		final StringBuilder constructor = new StringBuilder();
		for (int i = 0; i < Utilidades_Tablero.NUMERO_CASILLAS_TOTALES; i++) {

			final String textoCasilla = this.tableroJuego.get(i).toString();

			constructor.append(String.format("%3s", textoCasilla)); // "%s" formato "any tipe" '3' es el padding

			if ((i + 1) % Utilidades_Tablero.NUMERO_CASILLAS_POR_FILA == 0) {
				constructor.append("\n");
			}
		}
		return constructor.toString();
	}

	public Collection<Pieza> getPiezasNegras() {
		return this.piezasNegras;
	}

	public Collection<Pieza> getPiezasBlancas() {
		return this.piezasBlancas;
	}

	private Collection<Movimiento> calcularMovimientosLegales(final Collection<Pieza> piezas) {

		final List<Movimiento> movimientosLegales = new ArrayList<>();

		for (final Pieza pieza : piezas) {

			movimientosLegales.addAll(pieza.calcularMovimientosLegales(this));

		}

		return ImmutableList.copyOf(movimientosLegales);
	}

	private static Collection<Pieza> calcularPiezasActivas(List<Casilla> tableroJuego, Color_Pieza colorPieza) {

		final List<Pieza> piezasActivas = new ArrayList<Pieza>();

		for (final Casilla casilla : tableroJuego) {
			if (casilla.siCasillaOcupada()) {
				
				final Pieza pieza = casilla.getPiezaCasilla();
				if (pieza.getColorPieza() == colorPieza) {

					piezasActivas.add(pieza);

				}
			}

		}

		return ImmutableList.copyOf(piezasActivas);

	}

	public Casilla getCasilla(final int coordenadasCasilla) {
		return tableroJuego.get(coordenadasCasilla);

	}

	private static List<Casilla> crearTableroJuego(final Constructor constructor) {

		final Casilla[] casillas = new Casilla[Utilidades_Tablero.NUMERO_CASILLAS_TOTALES];
		for (int i = 0; i < Utilidades_Tablero.NUMERO_CASILLAS_TOTALES; i++) {

			casillas[i] = Casilla.crearCasilla(i, constructor.configTablero.get(i));

		}

		return ImmutableList.copyOf(casillas);
	}

	public static Tablero crearTableroBase() {
		final Constructor constructor = new Constructor();
		// Locacion Piezas negras tablero
		constructor.setPieza(new Torre(Color_Pieza.NEGRAS, 0));
		constructor.setPieza(new Caballo(Color_Pieza.NEGRAS, 1));
		constructor.setPieza(new Alfil(Color_Pieza.NEGRAS, 2));
		constructor.setPieza(new Dama(Color_Pieza.NEGRAS, 3));
		constructor.setPieza(new Rey(Color_Pieza.NEGRAS, 4));
		constructor.setPieza(new Alfil(Color_Pieza.NEGRAS, 5));
		constructor.setPieza(new Caballo(Color_Pieza.NEGRAS, 6));
		constructor.setPieza(new Torre(Color_Pieza.NEGRAS, 7));
		constructor.setPieza(new Peon(Color_Pieza.NEGRAS, 8));
		constructor.setPieza(new Peon(Color_Pieza.NEGRAS, 9));
		constructor.setPieza(new Peon(Color_Pieza.NEGRAS, 10));
		constructor.setPieza(new Peon(Color_Pieza.NEGRAS, 11));
		constructor.setPieza(new Peon(Color_Pieza.NEGRAS, 12));
		constructor.setPieza(new Peon(Color_Pieza.NEGRAS, 13));
		constructor.setPieza(new Peon(Color_Pieza.NEGRAS, 14));
		constructor.setPieza(new Peon(Color_Pieza.NEGRAS, 15));
		// Locacion Piezas Blancas tablero
		constructor.setPieza(new Peon(Color_Pieza.BLANCAS, 48));
		constructor.setPieza(new Peon(Color_Pieza.BLANCAS, 49));
		constructor.setPieza(new Peon(Color_Pieza.BLANCAS, 50));
		constructor.setPieza(new Peon(Color_Pieza.BLANCAS, 51));
		constructor.setPieza(new Peon(Color_Pieza.BLANCAS, 52));
		constructor.setPieza(new Peon(Color_Pieza.BLANCAS, 53));
		constructor.setPieza(new Peon(Color_Pieza.BLANCAS, 54));
		constructor.setPieza(new Peon(Color_Pieza.BLANCAS, 55));
		constructor.setPieza(new Torre(Color_Pieza.BLANCAS, 56));
		constructor.setPieza(new Caballo(Color_Pieza.BLANCAS, 57));
		constructor.setPieza(new Alfil(Color_Pieza.BLANCAS, 58));
		constructor.setPieza(new Dama(Color_Pieza.BLANCAS, 59));
		constructor.setPieza(new Rey(Color_Pieza.BLANCAS, 60));
		constructor.setPieza(new Alfil(Color_Pieza.BLANCAS, 61));
		constructor.setPieza(new Caballo(Color_Pieza.BLANCAS, 62));
		constructor.setPieza(new Torre(Color_Pieza.BLANCAS, 63));

		constructor.setMovimiento(Color_Pieza.BLANCAS);

		return constructor.construir();

	}

	public static class Constructor {

		Map<Integer, Pieza> configTablero;
		Color_Pieza siguienteMovimiento;
		Peon peonPasado;//  guarda al peon que se pueda comer al paso para el nuevo tablero

		public Constructor() {
			this.configTablero = new HashMap<>();
		}

		public Constructor setPieza(final Pieza pieza) {
			this.configTablero.put(pieza.getPosicion(), pieza);
			return this;
		}

		public Constructor setMovimiento(final Color_Pieza siguienteMovimiento) {
			this.siguienteMovimiento = siguienteMovimiento;
			return this;

		}

		public Tablero construir() {

			return new Tablero(this);

		}

		public Constructor setPeonPasado(Peon peonPasado) {

			this.peonPasado = peonPasado;
			return this;
		}
	}

	public Jugador Negras() {
		return this.jNegras;
	}

	public Jugador Blancas() {

		return this.jBlancas;
	}

	public Jugador getJugadorActual() {

		return this.jugadorActual;

	}

	public Iterable<Movimiento> getTodosMovimientosLegales() {
		// TODO Auto-generated method stub
		return Iterables.unmodifiableIterable(
				Iterables.concat(this.jBlancas.getMovimientosLegales(), this.jNegras.getMovimientosLegales()));
	}

	public Peon getPeonPasado() {
		// TODO Auto-generated method stub
		return this.peonPasado;
	}
}
