package Tablero;

import Piezas.Peon;
import Piezas.Pieza;
import Piezas.Torre;
import Tablero.Tablero.Constructor;

public abstract class Movimiento {

	final Tablero tablero;
	final Pieza piezaMovida;
	final int coordenadasDestino;

	private Movimiento(final Tablero tablero, final Pieza piezaMovida, final int coordenadasMovimiento) {
		this.tablero = tablero;
		this.piezaMovida = piezaMovida;
		this.coordenadasDestino = coordenadasMovimiento;
	}

	@Override
	public int hashCode() {

		final int primo = 31; // se utiliza 31 porque es un numero que evita menos coliciones administrando mejor los espacios del hash
		int res = 1;

		res = primo * res + this.piezaMovida.getPosicion();
		res = primo * res + this.piezaMovida.hashCode();

		return res;
	}

	@Override
	public boolean equals(final Object otro) {// permite saber con mayor exactidud si dos piezas son iguales ya q el
												// equals original trabaja solo con la referencia
		if (this == otro) {
			return true;
		}
		if (!(otro instanceof Movimiento)) {
			return false;
		}
		final Movimiento otroMovimiento = (Movimiento) otro;
		return getCoordenadasDestino() == otroMovimiento.getCoordenadasDestino()
				&& getPiezaMovida().equals(otroMovimiento.getPiezaMovida());
	}

	public Pieza getPiezaMovida() {
		return this.piezaMovida;
	}

	public int getCoordenadasDestino() {
		return this.coordenadasDestino;
	}

	public int getCoordenadaActual() {
		return this.getCoordenadaActual();
	}

	public boolean siAtaque() {
		return false;
	}

	public boolean siEnroque() {
		return false;
	}

	public Pieza getPiezaAtacada() {
		return null;
	}

	public Tablero efectuar() {

		final Tablero.Constructor constructor = new Tablero.Constructor();

		for (final Pieza pieza : this.tablero.getJugadorActual().getPiezasActivas()) {//Se colocan las piezas del jugador
			// TODO FALTA EL HASH
			if (!this.piezaMovida.equals(pieza)) {
				constructor.setPieza(pieza);
			}
		}

		for (final Pieza pieza : this.tablero.getJugadorActual().getRival().getPiezasActivas()) {//Se colocan las piezas del rival
			constructor.setPieza(pieza);
		}

		//
		constructor.setPieza(this.piezaMovida.moverPieza(this));// INFORMA AL CONSTRUCTOR DE LA POSICION DE LA PIEZA
																// MOVIDA
		constructor.setMovimiento(this.tablero.getJugadorActual().getRival().getColorPieza());// Establece el turno para
																								// el jugador que no
																								// movio
		//

		return constructor.construir();
	}

	public static final class MovimientoGrande extends Movimiento {

		public MovimientoGrande(final Tablero tablero, final Pieza piezaMovida, final int coordenadasMovimiento) {
			super(tablero, piezaMovida, coordenadasMovimiento);
		}

	}

	public static class MovimientoAtaque extends Movimiento {

		final Pieza piezaAtacada;

		public MovimientoAtaque(final Tablero tablero, final Pieza piezaMovida, final int coordenadasMovimiento,
				final Pieza piezaAtacada) {
			super(tablero, piezaMovida, coordenadasMovimiento);
			this.piezaAtacada = piezaAtacada;
		}

		@Override
		public int hashCode() {
			return this.piezaAtacada.hashCode() + super.hashCode();
		}

		@Override
		public boolean equals(final Object otro) {
			if (this == otro) {
				return true;
			}
			if (!(otro instanceof MovimientoAtaque)) {
				return false;
			}
			final MovimientoAtaque otroMovimientoAtaque = (MovimientoAtaque) otro;
			return super.equals(otroMovimientoAtaque);
		}

		@Override
		public Tablero efectuar() {

			return null;

		}

		@Override
		public boolean siAtaque() {
			return true;
		}

		@Override
		public Pieza getPiezaAtacada() {
			return this.piezaAtacada;
		}

	}

	public static final class MovimientoPeon extends Movimiento {
		public MovimientoPeon(final Tablero tablero, final Pieza piezaMovida, final int coordenadasMovimiento) {
			super(tablero, piezaMovida, coordenadasMovimiento);
		}
	}

	public static class MovimientoAtaquePeon extends MovimientoAtaque {

		public MovimientoAtaquePeon(final Tablero tablero, final Pieza piezaMovida, final int coordenadasMovimiento,
				final Pieza piezaAtacada) {
			super(tablero, piezaMovida, coordenadasMovimiento, piezaAtacada);
		}
	}

	public static final class MovimientoAtaquePeonPasado extends MovimientoAtaque {

		public MovimientoAtaquePeonPasado(final Tablero tablero, final Pieza piezaMovida,
				final int coordenadasMovimiento, final Pieza piezaAtacada) {
			super(tablero, piezaMovida, coordenadasMovimiento, piezaAtacada);
		}

	}

	public static final class SaltoPeon extends Movimiento {
		public SaltoPeon(final Tablero tablero, final Pieza piezaMovida, final int coordenadasMovimiento) {
			super(tablero, piezaMovida, coordenadasMovimiento);
		}

		@Override
		public Tablero efectuar() {
			final Constructor constructor = new Constructor();
			for (final Pieza pieza : this.tablero.getJugadorActual().getPiezasActivas()) {
				if (!this.piezaMovida.equals(pieza)) {
					constructor.setPieza(pieza);
				}
			}
			for (final Pieza pieza : this.tablero.getJugadorActual().getRival().getPiezasActivas()) {
				constructor.setPieza(pieza);
			}
			final Peon peonMovido = (Peon) this.piezaMovida.moverPieza(this);
			constructor.setPieza(peonMovido);
			constructor.setPeonPasado(peonMovido);
			constructor.setMovimiento(this.tablero.getJugadorActual().getRival().getColorPieza());
			return constructor.construir();
		}

	}

	public static class Enroque extends Movimiento {

		protected final Torre enroqueTorre;
		protected final int inicioEnroqueTorre;
		protected final int destinoEnroqueTorre;

		public Enroque(final Tablero tablero, final Pieza piezaMovida, final int coordenadasMovimiento,
				final Torre enroqueTorre, final int inicioEnroqueTorre, final int destinoEnroqueTorre) {
			super(tablero, piezaMovida, coordenadasMovimiento);
			this.enroqueTorre = enroqueTorre;
			this.inicioEnroqueTorre = inicioEnroqueTorre;
			this.destinoEnroqueTorre = destinoEnroqueTorre;
		}

		public Torre getEnroqueTorre() {
			return this.enroqueTorre;
		}

		@Override
		public boolean siEnroque() {
			return true;
		}

		@Override
		public Tablero efectuar() {
			final Constructor constructor = new Constructor();
			for (final Pieza pieza : this.tablero.getJugadorActual().getPiezasActivas()) {
				if (!this.piezaMovida.equals(pieza) && !this.enroqueTorre.equals(pieza)) {
					constructor.setPieza(pieza);
				}
			}
			for (final Pieza pieza : this.tablero.getJugadorActual().getRival().getPiezasActivas()) {
				constructor.setPieza(pieza);
			}
			constructor.setPieza(this.piezaMovida.moverPieza(this));
			constructor.setPieza(new Torre(this.enroqueTorre.getColorPieza(), this.destinoEnroqueTorre));
			constructor.setMovimiento(this.tablero.getJugadorActual().getRival().getColorPieza());
			return constructor.construir();

		}

	}

	public static class EnroqueCorto extends Enroque {
		public EnroqueCorto(final Tablero tablero, final Pieza piezaMovida, final int coordenadasMovimiento,
				final Torre enroqueTorre, final int inicioEnroqueTorre, final int destinoEnroqueTorre) {
			super(tablero, piezaMovida, coordenadasMovimiento, enroqueTorre, inicioEnroqueTorre, destinoEnroqueTorre);
		}

		@Override
		public String toString() {
			return "0-0";
		}

	}

	public static class EnroqueLargo extends Enroque {
		public EnroqueLargo(final Tablero tablero, final Pieza piezaMovida, final int coordenadasMovimiento,
				final Torre enroqueTorre, final int inicioEnroqueTorre, final int destinoEnroqueTorre) {
			super(tablero, piezaMovida, coordenadasMovimiento, enroqueTorre, inicioEnroqueTorre, destinoEnroqueTorre);
		}

		@Override
		public String toString() {
			return "0-0-0";
		}

	}

	private static class MovimientoNulo extends Movimiento {
		public MovimientoNulo() {
			super(null, null, -1);
		}

		@Override
		public Tablero efectuar() {
			throw new RuntimeException("Movimiento Nulo");
		}
	}

	public static class CreadoraMovimiento {
		public static final Movimiento MOVIMIENTO_NULO = new MovimientoNulo();

		private CreadoraMovimiento() {
			throw new RuntimeException("Movimiento no instanciable");
		}

		private static Movimiento crearMovimiento(final Tablero tablero, final int coordenadaActual,
				final int coordenadasDestino) {

			for (final Movimiento movimiento : tablero.getTodosMovimientosLegales()) {

				if (movimiento.getCoordenadaActual() == coordenadaActual
						&& movimiento.getCoordenadasDestino() == coordenadasDestino) {
					return movimiento;
				}
			}
			return MOVIMIENTO_NULO;
		}
	}

	public int getCoordenaActual() {
		// TODO Auto-generated method stub
		return this.getPiezaMovida().getPosicion();
	}

}
