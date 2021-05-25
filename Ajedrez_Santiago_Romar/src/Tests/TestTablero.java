package Tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import org.junit.jupiter.api.Test;

import Tablero.Tablero;

class TestTablero {

	@Test
	void tableroInicial() {
		final Tablero tablero = Tablero.crearTableroBase();
		assertEquals(tablero.getJugadorActual().getMovimientosLegales().size(), 20);
		assertEquals(tablero.getJugadorActual().getRival().getMovimientosLegales().size(), 20);
		assertFalse(tablero.getJugadorActual().Jaque());
		assertFalse(tablero.getJugadorActual().JaqueMate());
		assertFalse(tablero.getJugadorActual().Enrocado());
		
		assertEquals(tablero.getJugadorActual(), tablero.Blancas());
		assertEquals(tablero.getJugadorActual().getRival(), tablero.Negras());
		
		assertFalse(tablero.getJugadorActual().getRival().Jaque());
		assertFalse(tablero.getJugadorActual().getRival().JaqueMate());
		assertFalse(tablero.getJugadorActual().getRival().Enrocado());
		
		
		
	}

}
