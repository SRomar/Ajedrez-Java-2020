package Jugadores;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.management.RuntimeErrorException;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;

import Piezas.Color_Pieza;
import Piezas.Pieza;
import Piezas.Rey;
import Tablero.Movimiento;
import Tablero.Tablero;

public abstract class Jugador {
	protected final Tablero tablero;
	protected final Rey reyJugador;
	protected final Collection<Movimiento> movimientosLegales;
	private final boolean jaque;

	public Jugador(Tablero tablero, Collection<Movimiento> movimientosLegales,
			Collection<Movimiento> movimientosRival) {
		super();
		this.tablero = tablero;
		this.reyJugador = definirMovimientosRey();
		this.movimientosLegales = ImmutableList
				.copyOf(Iterables.concat(movimientosLegales, calcularEnroque(movimientosLegales, movimientosRival)));
		this.jaque = !Jugador.siCasillaEsAtacada(this.reyJugador.getPosicion(), movimientosRival).isEmpty();

	}

	protected static Collection<Movimiento> siCasillaEsAtacada(int posicion, Collection<Movimiento> movimientos) {

		final List<Movimiento> ataques = new ArrayList<>();
		for (final Movimiento movimiento : movimientos) {
			if (posicion == movimiento.getCoordenadasDestino()) {
				ataques.add(movimiento);
			}
		}
		return ImmutableList.copyOf(ataques);
	}

	private Rey definirMovimientosRey() {
		
		for (final Pieza pieza : getPiezasActivas()) {
			if (pieza.getTipo().esRey()) {
				return (Rey) pieza;
			}
		}
		throw new RuntimeException("Movimiento Ilegal");
	}

	public boolean esMovimientoLegal(final Movimiento movimiento) {
		return this.movimientosLegales.contains(movimiento);
	}

	protected boolean siEscape() {//Recorre todos los movimientos del jugador si no tiene ningun movimiento legal devuelve false

		for (final Movimiento movimiento : this.movimientosLegales) {
			final Movilizador elMovimiento = efectuarMovimiento(movimiento);
			if (elMovimiento.getEstadoMovimiento().siFinalizado()) {
				return true;
			}
		}
		return false;
	}

	// TODO IMPLEMENTAR METODOS
	public boolean Jaque() {
		return this.jaque;
	}

	public boolean JaqueMate() {
		return this.jaque && !siEscape();
	}

	public boolean Ahogado() {
		return !this.jaque && !siEscape();
	}

	public boolean Enrocado() {
		return false;
	}

	public Movilizador efectuarMovimiento(final Movimiento movimiento) {

		if (!esMovimientoLegal(movimiento)) {
			
			return new Movilizador(this.tablero, this.tablero, movimiento, EstadoMovilidad.MOVIMIENTO_ILEGAL);
		}

		
		final Tablero tableroTransicion = movimiento.efectuar();

		
		
		final Collection<Movimiento> ataquesRey = Jugador.siCasillaEsAtacada(
				tableroTransicion.getJugadorActual().getRival().getReyJugador().getPosicion(),
				tableroTransicion.getJugadorActual().getMovimientosLegales());
		if (!ataquesRey.isEmpty()) {
			
			return new Movilizador(this.tablero, this.tablero, movimiento, EstadoMovilidad.SEGUIR_EN_JAQUE);
		}

		return new Movilizador(this.tablero, tableroTransicion, movimiento, EstadoMovilidad.FINALIZADO);
	}

	
	
	
	public Collection<Movimiento> getMovimientosLegales() {
		// TODO Auto-generated method stub
		return this.movimientosLegales;
	}

	public Rey getReyJugador() {
		// TODO Auto-generated method stub
		return this.reyJugador;
	}

	public abstract Collection<Pieza> getPiezasActivas();

	public abstract Color_Pieza getColorPieza();

	public abstract Jugador getRival();

	protected abstract Collection<Movimiento> calcularEnroque(Collection<Movimiento> jugadasLegales,
			Collection<Movimiento> jugadasLegalesRival);

}
