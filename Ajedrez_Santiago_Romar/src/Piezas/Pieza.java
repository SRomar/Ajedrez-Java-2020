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
	protected final boolean siPrimerMovimiento;
	protected final int valorHash;

	protected Pieza(final int posPieza, final Color_Pieza colorPieza, final TipoPieza tipoPieza, final boolean siPrimerMovimiento) {

		this.tipoPieza = tipoPieza;
		this.posPieza = posPieza;
		this.colorPieza = colorPieza;

		this.siPrimerMovimiento = siPrimerMovimiento;
		this.valorHash = calcularHash();
	}

	private int calcularHash() { // se utiliza 31 porque es un numero que evita menos coliciones administrando mejor los espacios del hash
		int res = tipoPieza.hashCode();
		res = 31 * res + colorPieza.hashCode();
		res = 31 * res + posPieza;
		res = 31 * res + (siPrimerMovimiento ? 1 : 0);//si es el primer movimiento => 1 sino 0 
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
				&& colorPieza == otraPieza.getColorPieza() && siPrimerMovimiento == otraPieza.esPrimerMovimiento();
	}

	@Override
	public int hashCode() {

		return valorHash;

	}

	public TipoPieza getTipo() {
		return this.tipoPieza;
	}
	
	public int getValorPieza() {
		// TODO Auto-generated method stub
		return this.tipoPieza.getValorPieza();
	}

	public int getPosicion() {
		return posPieza;
	}

	public Color_Pieza getColorPieza() {
		return colorPieza;
	}

	public boolean esPrimerMovimiento() {
		return this.siPrimerMovimiento;
	}

	public abstract Collection<Movimiento> calcularMovimientosLegales(final Tablero tablero);

	public abstract Pieza moverPieza(Movimiento movimiento);

	public enum TipoPieza {

		PEON(100,"P") {
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
		CABALLO(300, "C") {
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
		ALFIL(300, "A") {
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
		TORRE(500, "T") {
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
		DAMA(900, "D") {
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
		REY(10000, "R") {
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
		private int valorPieza;

		TipoPieza(final int valorPieza, String nombre) {
			this.nombre = nombre;
			this.valorPieza = valorPieza;
		}

		@Override
		public String toString() {
			return this.nombre;
		}
		public int getValorPieza() {
			return this.valorPieza;
		}
		
		public abstract boolean esRey();

		public abstract boolean esTorre();

	}

	

}
