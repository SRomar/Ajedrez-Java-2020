package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.sun.nio.sctp.Notification;

import Tablero.Movimiento;
import Tablero.Tablero;

public class HistorialMovimientosJuego extends JPanel {

	private static final Dimension DIMESION_PANEL_HISTORIAL = new Dimension(100, 400);
	private final DataModel modelo;
	private final JScrollPane scrollPane;
	
	public HistorialMovimientosJuego() {
		setBackground(Color.ORANGE);
		this.setLayout(new BorderLayout());
		this.modelo = new DataModel();
		
		final JTable tabla = new JTable(modelo);
		tabla.setBackground(Color.WHITE);
		tabla.setRowHeight(15);
		tabla.getTableHeader().setResizingAllowed(false);
		tabla.getTableHeader().setReorderingAllowed(false);
		this.scrollPane = new JScrollPane(tabla);
		scrollPane.setBackground(Color.ORANGE);
		scrollPane.setColumnHeaderView(tabla.getTableHeader());
		scrollPane.setPreferredSize(DIMESION_PANEL_HISTORIAL);
	
		this.add(scrollPane, BorderLayout.CENTER);
		this.setVisible(true);
	}
	
	void rehacer(final Tablero tablero, final Mesa.RegistroMovimientos registroMovimientos) {
		
		
		int filaActual = 0;
		this.modelo.clear();
		for(final Movimiento movimiento: registroMovimientos.getMovimientos()) {
			final String textoMovimiento = movimiento.toString();
			if(movimiento.getPiezaMovida().getColorPieza().esBlanca()) {
				this.modelo.setValueAt(textoMovimiento, filaActual, 0);
			} else if(movimiento.getPiezaMovida().getColorPieza().esNegra()) {
				this.modelo.setValueAt(textoMovimiento, filaActual, 1);
				filaActual++;
			}
		}
		
		if(registroMovimientos.getMovimientos().size() > 0) {
			final Movimiento ultimoMovimiento = registroMovimientos.getMovimientos().get(registroMovimientos.size() -1);
			final String textoMovimiento = ultimoMovimiento.toString();
			
			
			
			if (ultimoMovimiento.getPiezaMovida().getColorPieza().esBlanca()) {
				this.modelo.setValueAt(textoMovimiento + siJaqueOJaqueMate(tablero), filaActual, 0);
				
				
				
			}else if(ultimoMovimiento.getPiezaMovida().getColorPieza().esNegra()) {
				this.modelo.setValueAt(textoMovimiento + siJaqueOJaqueMate(tablero), filaActual-1, 1);
				
			}
			
		}
		
		final JScrollBar vertical = scrollPane.getVerticalScrollBar();
		vertical.setValue(vertical.getMaximum());
		
	}
	
	private String siJaqueOJaqueMate(Tablero tablero) {
		if (tablero.getJugadorActual().JaqueMate()) {
			JOptionPane.showMessageDialog(null, "GANAN LAS " + tablero.getJugadorActual().getRival().getColorPieza().toString());
			return "#";
			
		}else if(tablero.getJugadorActual().Jaque()) {
			
			return "+";
		}
		return "";
	}

	private static class DataModel extends DefaultTableModel{
		
		private final List <Fila> valores;
		private static final String[] NOMBRES = {"Blancas", "Negras"};
		
		DataModel(){
			this.valores = new ArrayList<>();
			
		}
		
		public void clear() {
			this.valores.clear();
			setRowCount(0);
		}
		
		@Override
		public int getRowCount() {
			if(this.valores == null) {
				return 0;
			}
			return this.valores.size();
		}
		
		@Override
		public int getColumnCount() {
			return NOMBRES.length;
		}
		
		@Override
		public Object getValueAt(final int fila, final int columna) {
			final Fila filaActual = this.valores.get(fila);
					if(columna == 0) {
						return filaActual.getMovimientoBlancas();
					} else if (columna == 1) {
						return filaActual.getMovimientoNegras();
					}
					return null;
		}
		
		@Override 
		public void setValueAt(final Object valor,
								final int fila,
								final int columna) {
			
			final Fila filaActual;
			if(this.valores.size() <= fila) {
				filaActual = new Fila();
				this.valores.add(filaActual);
			}else {
				filaActual = this.valores.get(fila);
			}
			if(columna == 0) {
				filaActual.setMovimientoBlancas((String) valor);
				fireTableRowsInserted(fila, fila);
				
			} else if(columna == 1) {
				filaActual.setMovimientoNegras((String)valor);
				fireTableCellUpdated(fila, columna);
			}
		}
		
		@Override
		public Class<?> getColumnClass(final int columna){
			return Movimiento.class;
		}
		
		@Override
		public String getColumnName (final int columna) {
			return NOMBRES[columna];
		}
		
	}
	
	private static class Fila{
		
		private String movimientoBlancas;
		private String movimientoNegras;
		
		Fila(){
			
		}
		
		public String getMovimientoBlancas() {
			return this.movimientoBlancas;
		}
		public String getMovimientoNegras() {
			return this.movimientoNegras;
		}
		
		public void setMovimientoBlancas(final String movimiento) {
			this.movimientoBlancas = movimiento;
		}
		public void setMovimientoNegras(final String movimiento) {
			this.movimientoNegras = movimiento;
		}
		
	}

}
