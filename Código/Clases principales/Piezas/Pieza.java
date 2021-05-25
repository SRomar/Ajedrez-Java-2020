package Piezas;

import java.awt.Color;
import java.util.Collection;

import javax.print.attribute.standard.MediaSize.Other;

import Tablero.Movimiento;
import Tablero.Tablero;

public abstract class Pieza {

	protected final TipoPieza tipoPieza;
	protected final int posPieza;
	protected final Color_Pieza colorPieza;
	protected final boolean esPrimerMovimiento;
	protected final int valorHash;

	protected Pieza(final int posPieza, final Color_Pieza colorPieza, final TipoPieza tipoPieza) {

		this.tipoPieza = null;
		this.posPieza = posPieza;
		this.colorPieza = colorPieza;

		this.esPrimerMovimiento = false;
		this.valorHash = calcularHash();
	}

	private int calcularHash() { // se utiliza 31 porque es un numero que evita menos coliciones administrando mejor los espacios del hash
		int res = tipoPieza.hashCode();
		res = 31 * res + colorPieza.hashCode();
		res = 31 * res + posPieza;
		res = 31 * res + (esPrimerMovimiento ? 1 : 0);//si es el primer movimiento => 1 sino 0 
		return res;
	}

	@Override
	public boolean equals(final Object otro) { // permite saber con mayor exactidud si dos piezas son iguales ya q el
												// equals original trabaja solo con la referencia
		if (this == otro) {
			return true;
		}
		if (!(otro instanceof Pieza)) {
			return false;
		}
		final Pieza otraPieza = (Pieza) otro;

		return posPieza == otraPieza.getPosicion() && tipoPieza == otraPieza.getTipo()
				&& colorPieza == otraPieza.getColorPieza() && esPrimerMovimiento == otraPieza.esPrimerMovimiento();
	}

	@Override
	public int hashCode() {

		return valorHash;

	}

	public TipoPieza getTipo() {
		return this.tipoPieza;
	}

	public int getPosicion() {
		return posPieza;
	}

	public Color_Pieza getColorPieza() {
		return colorPieza;
	}

	public boolean esPrimerMovimiento() {
		return this.esPrimerMovimiento;
	}

	public abstract Collection<Movimiento> calcularMovimientosLegales(final Tablero tablero);

	public abstract Pieza moverPieza(Movimiento movimiento);

	public enum TipoPieza {

		PEON("P") {
			@Override
			public boolean esRey() {
				return false;
			}

			@Override
			public boolean esTorre() {
				// TODO Auto-generated method stub
				return false;
			}
		},
		CABALLO("C") {
			@Override
			public boolean esRey() {
				return false;
			}

			@Override
			public boolean esTorre() {
				// TODO Auto-generated method stub
				return false;
			}
		},
		ALFIL("A") {
			@Override
			public boolean esRey() {
				return false;
			}

			@Override
			public boolean esTorre() {
				// TODO Auto-generated method stub
				return false;
			}
		},
		TORRE("T") {
			@Override
			public boolean esRey() {
				return false;
			}

			@Override
			public boolean esTorre() {
				// TODO Auto-generated method stub
				return true;
			}
		},
		DAMA("D") {
			@Override
			public boolean esRey() {
				return false;
			}

			@Override
			public boolean esTorre() {
				// TODO Auto-generated method stub
				return false;
			}
		},
		REY("R") {
			@Override
			public boolean esRey() {
				return true;
			}

			@Override
			public boolean esTorre() {
				// TODO Auto-generated method stub
				return false;
			}
		};

		private String nombre;

		TipoPieza(String nombre) {
			this.nombre = nombre;
		}

		@Override
		public String toString() {
			return this.nombre;
		}

		public abstract boolean esRey();

		public abstract boolean esTorre();

	}

}
