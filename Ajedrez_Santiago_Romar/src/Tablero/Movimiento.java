package Tablero;

import Piezas.Peon;
import Piezas.Pieza;
import Piezas.Torre;
import Tablero.Tablero.Constructor;

public abstract class Movimiento {

	protected final Tablero tablero;
	protected final Pieza piezaMovida;
	protected final int coordenadasDestino;
	protected final boolean siPrimerMovimiento;

	private Movimiento(final Tablero tablero, final Pieza piezaMovida, final int coordenadasDestino) {
		this.tablero = tablero;
		this.piezaMovida = piezaMovida;
		this.coordenadasDestino = coordenadasDestino;
		this.siPrimerMovimiento = piezaMovida.esPrimerMovimiento();
	}

	private Movimiento(final Tablero tablero, final int coordenadasDestino) {
		this.tablero = tablero;
		this.piezaMovida = null;
		this.coordenadasDestino = coordenadasDestino;
		this.siPrimerMovimiento = false;
	}

	@Override
	public int hashCode() {

		final int primo = 31; // se utiliza 31 porque es un numero que evita menos coliciones administrando
								// mejor los espacios del hash
		int res = 1;

		res = primo * res + this.piezaMovida.getPosicion();
		res = primo * res + this.piezaMovida.hashCode();
		res = primo * res + this.piezaMovida.getPosicion();

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

		return getCoordenaActual() == otroMovimiento.getCoordenaActual()
				&& getCoordenadasDestino() == otroMovimiento.getCoordenadasDestino()
				&& getPiezaMovida().equals(otroMovimiento.getPiezaMovida());
	}

	/*
	 * public String disambiguationFile() { for(final Movimiento movimiento :
	 * this.tablero.getJugadorActual().getMovimientosLegales()) {
	 * if(movimiento.getCoordenadasDestino() == this.coordenadasDestino &&
	 * !this.equals(movimiento) &&
	 * this.piezaMovida.getTipo().equals(movimiento.getPiezaMovida().getTipo())) {
	 * return
	 * Utilidades_Tablero.INSTANCE.(this.movedPiece.getPiecePosition()).substring(0,
	 * 1); } } return ""; }
	 */
	public Pieza getPiezaMovida() {
		return this.piezaMovida;
	}

	public int getCoordenadasDestino() {
		return this.coordenadasDestino;
	}

	public int getCoordenadaActual() {
		return this.piezaMovida.getPosicion();
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

		for (final Pieza pieza : this.tablero.getJugadorActual().getPiezasActivas()) {// Se colocan las piezas del
																						// jugador
			// TODO FALTA EL HASH
			if (!this.piezaMovida.equals(pieza)) {
				constructor.setPieza(pieza);
			}
		}

		for (final Pieza pieza : this.tablero.getJugadorActual().getRival().getPiezasActivas()) {// Se colocan las
																		// piezas del rival
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

	public static final class MovimientoPrincipal extends Movimiento {

		public MovimientoPrincipal(final Tablero tablero, final Pieza piezaMovida, final int coordenadasMovimiento) {
			super(tablero, piezaMovida, coordenadasMovimiento);
		}

		@Override
		public boolean equals(final Object otro) {
			return this == otro || otro instanceof MovimientoPrincipal && super.equals(otro);
		}

		@Override
		public String toString() {
			return piezaMovida.getTipo().toString() + Utilidades_Tablero.posicionCoordenadas(this.coordenadasDestino);
		}

	}

	public static class MovimientoAtaque extends Movimiento {

		final Pieza piezaAtacada;

		public MovimientoAtaque(final Tablero tablero, final Pieza piezaMovida, final int coordenadasDestino,
				final Pieza piezaAtacada) {

			super(tablero, piezaMovida, coordenadasDestino);
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
		public boolean siAtaque() {
			return true;
		}

		@Override
		public Pieza getPiezaAtacada() {
			return this.piezaAtacada;
		}

	}

	public static class MovimientoPrincipalAtaque extends MovimientoAtaque {

		public MovimientoPrincipalAtaque(final Tablero tablero, final Pieza piezaMovida, final int coordenadasDestino,
				final Pieza piezaAtacada) {

			super(tablero, piezaMovida, coordenadasDestino, piezaAtacada);
		}

		@Override
		public boolean equals(final Object otro) {
			return this == otro || otro instanceof MovimientoPrincipalAtaque && super.equals(otro);

		}

		@Override
		public String toString() {
			return piezaMovida.getTipo() + Utilidades_Tablero.posicionCoordenadas(this.coordenadasDestino);
		}

	}

	public static final class MovimientoPeon extends Movimiento {
		public MovimientoPeon(final Tablero tablero, final Pieza piezaMovida, final int coordenadasMovimiento) {
			super(tablero, piezaMovida, coordenadasMovimiento);
		}

		@Override
		public boolean equals(final Object otro) {
			return this == otro || otro instanceof MovimientoPeon && super.equals(otro);
		}

		@Override
		public String toString() {
			return Utilidades_Tablero.posicionCoordenadas(this.coordenadasDestino);
		}
	}

	public static class MovimientoAtaquePeon extends MovimientoAtaque {

		public MovimientoAtaquePeon(final Tablero tablero, final Pieza piezaMovida, final int coordenadasMovimiento,
				final Pieza piezaAtacada) {
			super(tablero, piezaMovida, coordenadasMovimiento, piezaAtacada);
		}

		@Override
		public boolean equals(final Object otro) {
			return this == otro || otro instanceof MovimientoAtaquePeon && super.equals(otro);
		}

		@Override
		public String toString() {
			return Utilidades_Tablero.posicionCoordenadas(this.piezaMovida.getPosicion()).substring(0, 1) + "x"
					+ Utilidades_Tablero.posicionCoordenadas(this.getCoordenadasDestino());
		}

	}

	public static final class MovimientoAtaquePeonPasado extends MovimientoAtaquePeon {

		public MovimientoAtaquePeonPasado(final Tablero tablero,
										final Pieza piezaMovida,
										final int coordenadasMovimiento, 
										final Pieza piezaAtacada) {
			super(tablero, piezaMovida, coordenadasMovimiento, piezaAtacada);
		}

		@Override
		public boolean equals(final Object otro) {
			return this == otro || otro instanceof MovimientoAtaquePeonPasado && super.equals(otro);
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
				if (!pieza.equals(this.getPiezaAtacada())) {
					constructor.setPieza(pieza);
				}
			}
			constructor.setPieza(this.piezaMovida.moverPieza(this));
			constructor.setMovimiento(this.tablero.getJugadorActual().getRival().getColorPieza());
			
			return constructor.construir();
			
		}

	}

	
	public static class CoronacionPeon extends Movimiento{
		
		final Movimiento decoMovimiento;
		final Peon peonPromovido;
		
		public CoronacionPeon(final Movimiento decoMovimiento) {
			super(decoMovimiento.getTablero(), decoMovimiento.getPiezaMovida(), decoMovimiento.getCoordenadasDestino());
			this.decoMovimiento = decoMovimiento;
			this.peonPromovido = (Peon) decoMovimiento.getPiezaMovida();
		}
		
		
		@Override
		public int hashCode() {
			return decoMovimiento.hashCode() + (31 * peonPromovido.hashCode());
		}
		
		@Override
		public boolean equals(final Object otro) {
			return this == otro || otro instanceof CoronacionPeon && (super.equals(otro));
		}
		
		@Override
		public Tablero efectuar() {
			
			final Tablero peonMovido = this.decoMovimiento.efectuar();
			final Tablero.Constructor constructor = new Constructor();
			for(final Pieza pieza : peonMovido.getJugadorActual().getPiezasActivas()) {
				if(!this.peonPromovido.equals(pieza)) {
					constructor.setPieza(pieza);
				}
			}
			for(final Pieza pieza: peonMovido.getJugadorActual().getRival().getPiezasActivas()) {
				constructor.setPieza(pieza);
			}
			constructor.setPieza(this.peonPromovido.getPiezaPromovida().moverPieza(this));
			constructor.setMovimiento(peonMovido.getJugadorActual().getColorPieza());
			return constructor.construir();
			
				}
		@Override
		public boolean siAtaque() {
			return this.decoMovimiento.siAtaque();
		}
		
		@Override
		public Pieza getPiezaAtacada() {
			return this.decoMovimiento.getPiezaAtacada();
		}
		
		@Override
		public String toString() {
			return Utilidades_Tablero.posicionCoordenadas(this.piezaMovida.getPosicion()) + "*" +
					Utilidades_Tablero.posicionCoordenadas(this.coordenadasDestino) + "=" + this.peonPromovido.getPiezaPromovida().getTipo();
		}
	}
	
	
	
	public static final class SaltoPeon extends Movimiento {

		public SaltoPeon(final Tablero tablero, final Pieza piezaMovida, final int coordenadasMovimiento) {
			super(tablero, piezaMovida, coordenadasMovimiento);
		}

		@Override
		public boolean equals(final Object otro) {
			return this == otro || otro instanceof SaltoPeon && super.equals(otro);
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

		@Override
		public String toString() {
			return Utilidades_Tablero.posicionCoordenadas(this.coordenadasDestino);
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

		@Override
		public int hashCode() {
			final int primo = 31;
			int res = super.hashCode();
			res = primo * res + this.enroqueTorre.hashCode();
			res = primo * res + this.destinoEnroqueTorre;
			return res;
		}

		@Override
		public boolean equals(final Object otro) {
			if (this == otro) {
				return true;
			}
			if (!(otro instanceof Enroque)) {
				return false;
			}
			final Enroque otroEnroque = (Enroque) otro;
			return super.equals(otroEnroque) && this.enroqueTorre.equals(otroEnroque.getEnroqueTorre());
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

		@Override
		public boolean equals(final Object otro) {

			return this == otro || otro instanceof EnroqueCorto && super.equals(otro);

		}

	}

	public static class EnroqueLargo extends Enroque {
		public EnroqueLargo(final Tablero tablero, final Pieza piezaMovida, final int coordenadasMovimiento,
				final Torre enroqueTorre, final int inicioEnroqueTorre, final int destinoEnroqueTorre) {
			super(tablero, piezaMovida, coordenadasMovimiento, enroqueTorre, inicioEnroqueTorre, destinoEnroqueTorre);
		}

		@Override
		public boolean equals(final Object otro) {

			return this == otro || otro instanceof EnroqueLargo && super.equals(otro);

		}

		@Override
		public String toString() {
			return "0-0-0";
		}

	}

	private static class MovimientoNulo extends Movimiento {
		public MovimientoNulo() {
			super(null, 65);
		}

		@Override
		public Tablero efectuar() {
			throw new RuntimeException("Movimiento Nulo");
		}

		@Override
		public int getCoordenaActual() {
			return -1;
		}
	}

	public static class CreadoraMovimiento {
		public static final Movimiento MOVIMIENTO_NULO = new MovimientoNulo();

		private CreadoraMovimiento() {
			throw new RuntimeException("Movimiento no instanciable");
		}

		public static Movimiento crearMovimiento(final Tablero tablero, final int coordenadaActual,
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

	public Tablero getTablero() {
		// TODO Auto-generated method stub
		return this.tablero;
	}
	

}
