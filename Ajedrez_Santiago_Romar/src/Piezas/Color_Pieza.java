package Piezas;

import Jugadores.Jugador;
import Jugadores.JugadorBlancas;
import Jugadores.JugadorNegras;
import Tablero.Utilidades_Tablero;

public enum Color_Pieza {
	BLANCAS {
		@Override
		public int getDireccion() {
			return -1;
		}

		@Override
		public boolean esBlanca() {
			return true;
		}

		@Override
		public boolean esNegra() {
			return false;
		}

		@Override
		public Jugador elegirJugador(final JugadorBlancas jBlancas, final JugadorNegras jNegras) {
			// TODO Auto-generated method stub
			return jBlancas;
		}

		@Override
		public int getDireccionOpuesta() {
			// TODO Auto-generated method stub
			return 1;
		}

		@Override
		public boolean siCoronacionPeon(int posicion) {
			// TODO Auto-generated method stub
			return Utilidades_Tablero.OCTAVA_FILA[posicion];
		}
	},
	NEGRAS {
		@Override
		public int getDireccion() {
			return 1;
		}

		@Override
		public boolean esBlanca() {
			return false;
		}

		@Override
		public boolean esNegra() {
			return true;
		}

		@Override
		public Jugador elegirJugador(final JugadorBlancas jBlancas, final JugadorNegras jNegras) {
			// TODO Auto-generated method stub
			return jNegras;
		}

		@Override
		public int getDireccionOpuesta() {
			// TODO Auto-generated method stub
			return -1;
		}

		@Override
		public boolean siCoronacionPeon(int posicion) {
			// TODO Auto-generated method stub
			return Utilidades_Tablero.PRIMERA_FILA[posicion];
		}
	};

	public abstract int getDireccion();
	
	public abstract int getDireccionOpuesta();

	public abstract boolean esBlanca();

	public abstract boolean siCoronacionPeon(int posicion);
	
	public abstract boolean esNegra();

	public abstract Jugador elegirJugador(final JugadorBlancas jBlancas, final JugadorNegras jNegras);
}
