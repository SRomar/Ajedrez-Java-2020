package Piezas;

import Jugadores.Jugador;
import Jugadores.JugadorBlancas;
import Jugadores.JugadorNegras;

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
			return null;
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
			return null;
		}
	};

	public abstract int getDireccion();

	public abstract boolean esBlanca();

	public abstract boolean esNegra();

	public abstract Jugador elegirJugador(final JugadorBlancas jBlancas, final JugadorNegras jNegras);
}
