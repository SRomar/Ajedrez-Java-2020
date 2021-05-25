package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;

import com.google.common.primitives.Ints;

import Piezas.Pieza;
import Tablero.Movimiento;
import java.awt.Component;
import java.awt.ComponentOrientation;

public class PiezasComidas extends JPanel {

	
	private final JPanel panelSuperior;
	private final JPanel panelInferior;
	private static final EtchedBorder PARTE_IZQUIERDA_PANEL = new EtchedBorder(EtchedBorder.RAISED);
	private static final Color COLOR_PANEL = Color.decode("#786838");
	private static final Dimension PANEL_PIEZAS_COMIDAS_DIMESION = new Dimension(100, 1000);
	private static String iconosPiezasPath = "IconosPiezas/SalvaElPantano/";
	private static String iconosAvisosPath = "IconosAvisos/";
	private static String formatoIconos = ".gif";
	/**
	 * Create the panel.
	 */
	public PiezasComidas() {
		setBackground(COLOR_PANEL);
		setBorder(PARTE_IZQUIERDA_PANEL);
		setLayout(new GridLayout(0, 1, 0, 0));
		this.panelSuperior = new JPanel();
		panelSuperior.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		panelSuperior.setAlignmentX(Component.LEFT_ALIGNMENT);
		this.panelSuperior.setBackground(COLOR_PANEL);
		this.add(this.panelSuperior);
		panelSuperior.setLayout(new GridLayout(8, 2, 0, 0));
		
		setPreferredSize(new Dimension(120, 471));
		
		this.panelInferior = new JPanel();
		add(panelInferior);
		panelInferior.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		this.panelInferior.setBackground(COLOR_PANEL);
		panelInferior.setAlignmentX(Component.LEFT_ALIGNMENT);
		panelInferior.setLayout(new GridLayout(8, 2, 0, 0));
	}
	

	public void rehacer(final Mesa.RegistroMovimientos registroMovimientos) {
		
		this.panelInferior.removeAll();
		this.panelSuperior.removeAll();
		
		final List<Pieza> piezasBlancasComidas = new ArrayList<>();
		final List<Pieza> piezasNegrasComidas = new ArrayList<>();
		
		for(final Movimiento movimiento: registroMovimientos.getMovimientos()) {
			if (movimiento.siAtaque()) {
				final Pieza piezaComida = movimiento.getPiezaAtacada();
				if (piezaComida.getColorPieza().esBlanca()) {
					
					piezasBlancasComidas.add(piezaComida);
					
					
				}else if (piezaComida.getColorPieza().esNegra()) {
					
					piezasNegrasComidas.add(piezaComida);
					
				}else {
					throw new RuntimeException("No se encontro color pieza");
				}
				
			}
		}
		Collections.sort(piezasBlancasComidas, new Comparator<Pieza>() {
			
			@Override
			public int compare (Pieza p1, Pieza p2) {
				return Ints.compare(p1.getValorPieza(), p2.getValorPieza());
			}
			
		});
		
		Collections.sort(piezasNegrasComidas, new Comparator<Pieza>() {
			
			@Override
			public int compare (Pieza p1, Pieza p2) {
				return Ints.compare(p1.getValorPieza(), p2.getValorPieza());
			}
			
		});
		
		
		for(final Pieza piezaComida: piezasBlancasComidas) {

			try {
				final BufferedImage img = ImageIO.read(getClass().getClassLoader().getResource(iconosPiezasPath + piezaComida.getColorPieza().toString().substring(0, 1)
															+ "" + piezaComida.toString() + formatoIconos));
				
				final ImageIcon icon = new ImageIcon(img);
				final JLabel imgLabel = new JLabel(new ImageIcon(icon.getImage().getScaledInstance(
                        icon.getIconWidth() -60,  icon.getIconHeight() -60, img.SCALE_SMOOTH)));
			
				
				this.panelSuperior.add(imgLabel);
				
				
				
			} catch (final IOException e) {
				e.printStackTrace();
			}
		}
		for(final Pieza piezaComida: piezasNegrasComidas) {
			
			try {
				final BufferedImage img = ImageIO.read(
						getClass().getClassLoader().getResource(iconosPiezasPath + piezaComida.getColorPieza().toString().substring(0, 1)
								+ "" + piezaComida.toString() + formatoIconos));
				final ImageIcon icon = new ImageIcon(img);
				final JLabel imgLabel = new JLabel(new ImageIcon(icon.getImage().getScaledInstance(
                        icon.getIconWidth() -60, icon.getIconHeight() -60, img.SCALE_SMOOTH)));
				
				
				
				
				
				
			this.panelInferior.add(imgLabel);
			} catch (final IOException e) {
				e.printStackTrace();
			}
		}
		
		validate();
	}

}