package Jugadores;

import Tablero.Movimiento;
import Tablero.Tablero;

/**
 * 
 * @author Santiago_Romar
 *
 */

public class Movilizador {

	private final Tablero tableroTemporal;
	private final Movimiento movimiento;
	private final EstadoMovilidad estadoMobilidad;

	public Movilizador(final Tablero tableroTemporal, final Movimiento movimiento,
			final EstadoMovilidad estadoMobilidad) {

		this.tableroTemporal = tableroTemporal;
		this.movimiento = movimiento;
		this.estadoMobilidad = estadoMobilidad;
	}

	public EstadoMovilidad getEstadoMovimiento() {

		return this.estadoMobilidad;
	}

}
