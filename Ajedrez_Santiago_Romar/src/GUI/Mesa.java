package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.imageio.ImageIO;
import javax.print.attribute.standard.Destination;
import javax.swing.ImageIcon;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;

import com.google.common.collect.Lists;

import Jugadores.Movilizador;
import Piezas.Pieza;
import Tablero.Casilla;
import Tablero.Movimiento;
import Tablero.Tablero;
import Tablero.Utilidades_Tablero;
import Tablero.Movimiento.CreadoraMovimiento;

public class Mesa {
	private final JFrame jFrame;
	private final PanelTablero panelTablero;
	private final HistorialMovimientosJuego historialMovimientos;
	private final PiezasComidas piezasComidas;
	private Tablero tablero;
	private final RegistroMovimientos registroMovimientos;
	private Casilla casillaOrigen;
	private Casilla casillaDestino;
	private Pieza piezaMovida;
	private DireccionTablero direccionTablero;

	private boolean resaltarMovimientosLegales;

	private final Color casillaBlanca = Color.decode("#EEDBA1");
	private final Color casillaNegra = Color.decode("#A9924E");
	private static String iconosPiezasPath = "IconosPiezas/SalvaElPantano/";
	private static String iconosAvisosPath = "IconosAvisos/";
	private static String formatoIconos = ".gif";

	private final static Dimension ESCALA_FRAME = new Dimension(1200, 1000);
	private final static Dimension DIMENSION_PANEL_TABLERO = new Dimension(800, 800);
	public final static Dimension DIMENSION_PANEL_CASILLA = new Dimension(100, 100);

	public Mesa() {

		this.tablero = Tablero.crearTableroBase();
		this.jFrame = new JFrame("Salva al Pantano!");
		this.jFrame.setLayout(new BorderLayout());
		final JMenuBar menuBarra = crearMenuBarra();
		this.panelTablero = new PanelTablero();
		
		this.resaltarMovimientosLegales = true;
		this.historialMovimientos = new HistorialMovimientosJuego();
		this.piezasComidas = new PiezasComidas();
		this.direccionTablero = direccionTablero.NORMAL;
		this.registroMovimientos =new RegistroMovimientos();
		this.jFrame.add(this.panelTablero, BorderLayout.CENTER);
		this.jFrame.add(this.piezasComidas, BorderLayout.WEST);
		
		this.jFrame.add(this.historialMovimientos, BorderLayout.EAST);
		this.jFrame.setJMenuBar(menuBarra);
		this.jFrame.setResizable(false);
		this.jFrame.setSize(ESCALA_FRAME);
		this.jFrame.setLocationRelativeTo(null);
		this.jFrame.setVisible(true);
		
		
	}

	private JMenuBar crearMenuBarra() {
		final JMenuBar menuBarra = new JMenuBar();
		menuBarra.add(crearArchivoMenu());
		menuBarra.add(crearPreferenciasMenu());

		return menuBarra;

	}

	private JMenu crearPreferenciasMenu() {

		// Invertir tablero
		final JMenu preferenciaMenu = new JMenu("Preferencias");
		final JMenuItem invertirTableroMenuItem = new JMenuItem("Invertir Tablero");
		invertirTableroMenuItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				direccionTablero = direccionTablero.opuesto();
				panelTablero.dibujarTablero(tablero);
			}
		});
		preferenciaMenu.add(invertirTableroMenuItem);
		preferenciaMenu.addSeparator();

		
		
		final JCheckBoxMenuItem resaltadorMovimientosLegalesCheckBox = new JCheckBoxMenuItem(
				"Resaltar Movimientos Legales", true);

		resaltadorMovimientosLegalesCheckBox.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				resaltarMovimientosLegales = resaltadorMovimientosLegalesCheckBox.isSelected();
			}
		});

		preferenciaMenu.add(resaltadorMovimientosLegalesCheckBox);

		return preferenciaMenu;
	}

	private JMenu crearArchivoMenu() {
		final JMenu opcionesMenu = new JMenu("Opciones");

		/*final JMenuItem abrirPGN = new JMenuItem("Cargar archivo PGN");

		abrirPGN.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Abriendo PGN...");
			}
		});
		opcionesMenu.add(abrirPGN);
		*/

		

		
		final JMenuItem menuItemCerrar = new JMenuItem("Salir");
menuItemCerrar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				System.exit(0);

			}

		});

		opcionesMenu.add(menuItemCerrar);
		
		final JMenuItem nuevaPartida = new JMenuItem("Nueva Partida");
		nuevaPartida.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {

				
				Mesa mesa = new Mesa();
				
			}
		});
		
		opcionesMenu.add(nuevaPartida);
		
		return opcionesMenu;
	}

	public static class RegistroMovimientos {
		private final List<Movimiento> movimientos;

		public RegistroMovimientos() {

			this.movimientos = new ArrayList<>();

		}

		public List<Movimiento> getMovimientos() {
			return this.movimientos;
		}

		public void addMovimiento(final Movimiento movimiento) {
			this.movimientos.add(movimiento);
		}

		public int size() {
			return this.movimientos.size();
		}

		public void clear() {
			this.movimientos.clear();
		}

		public Movimiento quitarMovimiento(int indiceMovimiento) {

			return this.movimientos.remove(indiceMovimiento);
		}

		public boolean quitarMovimiento(final Movimiento movimiento) {
			return this.movimientos.remove(movimiento);
		}

	}

	private class PanelTablero extends JPanel {
		final List<PanelCasilla> casillasTablero;

		public PanelTablero() {
			super(new GridLayout(8, 8));
			this.casillasTablero = new ArrayList<>();
			for (int i = 0; i < Utilidades_Tablero.NUMERO_CASILLAS_TOTALES; i++) {
				final PanelCasilla panelCasilla = new PanelCasilla(this, i);
				this.casillasTablero.add(panelCasilla);
				add(panelCasilla);

			}
			setPreferredSize(DIMENSION_PANEL_TABLERO);
			validate();
		}

		public void dibujarTablero(final Tablero tablero) {
			removeAll();
			for (final PanelCasilla panelCasilla : direccionTablero.atravesar(casillasTablero)) {
				panelCasilla.dibujarCasilla(tablero);
				add(panelCasilla);
			}
			validate();
			repaint();
		}
	}

	enum DireccionTablero {
		NORMAL {
			@Override
			List<PanelCasilla> atravesar(final List<PanelCasilla> casillasTablero) {
				return casillasTablero;
			}

			@Override
			DireccionTablero opuesto() {
				return INVERTIDO;
			}
		},
		INVERTIDO {
			@Override
			List<PanelCasilla> atravesar(final List<PanelCasilla> casillasTablero) {
				return Lists.reverse(casillasTablero); // Guava, invierta la lista
			}

			@Override
			DireccionTablero opuesto() {
				return NORMAL;
			}
		};

		abstract List<PanelCasilla> atravesar(final List<PanelCasilla> casillasTablero);

		abstract DireccionTablero opuesto();

	}

	private class PanelCasilla extends JPanel {

		private final int idCasilla;

		PanelCasilla(final PanelTablero panelTablero, final int idCasilla) {

			super(new GridBagLayout());
			this.idCasilla = idCasilla;
			setPreferredSize(DIMENSION_PANEL_CASILLA);
			setColorCasilla();
			setIconoPiezaCasilla(tablero);
			validate();

			addMouseListener(new MouseListener() {
				@Override
				public void mouseClicked(final MouseEvent e) {

					if (SwingUtilities.isRightMouseButton(e)) {

						casillaOrigen = null;
						casillaDestino = null;
						piezaMovida = null;

					} else if (SwingUtilities.isLeftMouseButton(e)) {

						if (casillaOrigen == null) {
							casillaOrigen = tablero.getCasilla(idCasilla);
							piezaMovida = casillaOrigen.getPiezaCasilla();
							if (piezaMovida == null) {
								casillaOrigen = null;
							}
						} else {
							casillaDestino = tablero.getCasilla(idCasilla);

						
							final Movimiento movimiento = Movimiento.CreadoraMovimiento.crearMovimiento(tablero,
									casillaOrigen.getCoordenadasCasilla(), casillaDestino.getCoordenadasCasilla());
							
							//System.out.println(movimiento.toString());//exf6
							
							
							final Movilizador elMovimiento = tablero.getJugadorActual().efectuarMovimiento(movimiento);
							
							if (elMovimiento.getEstadoMovimiento().siFinalizado()) {

							
								
								tablero = elMovimiento.getTableroTemporal();
								registroMovimientos.addMovimiento(movimiento);
								
								
							
								
							}
							casillaOrigen = null;
							casillaDestino = null;
							piezaMovida = null;
						}
					}
					SwingUtilities.invokeLater(new Runnable() {
						
						@Override
						public void run() {
							
							historialMovimientos.rehacer(tablero, registroMovimientos);
							
							piezasComidas.rehacer(registroMovimientos);

							panelTablero.dibujarTablero(tablero);
							
						}
					});

				}

				@Override
				public void mousePressed(MouseEvent e) {
					// TODO Auto-generated method stub

				}

				@Override
				public void mouseReleased(MouseEvent e) {
					// TODO Auto-generated method stub

				}

				@Override
				public void mouseEntered(MouseEvent e) {
					// TODO Auto-generated method stub

				}

				@Override
				public void mouseExited(MouseEvent e) {
					// TODO Auto-generated method stub

				}
			});
		}

		public void dibujarCasilla(final Tablero tablero) {

			setColorCasilla();
			setIconoPiezaCasilla(tablero);
			resaltarMovimientosLegales(tablero);
			validate();
			repaint();

		}

		private void resaltarMovimientosLegales(final Tablero tablero) {
			if (resaltarMovimientosLegales) {
				for (final Movimiento movimiento : movimientosLegalesPieza(tablero)) {
					if (movimiento.getCoordenadasDestino() == idCasilla) {
						try {
							add(new JLabel(new ImageIcon(getClass().getClassLoader().getResource(iconosAvisosPath + "huevo.png"))));
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			}
		}

		private Collection<Movimiento> movimientosLegalesPieza(final Tablero tablero) {
			if (piezaMovida != null && piezaMovida.getColorPieza() == tablero.getJugadorActual().getColorPieza()) {
				return piezaMovida.calcularMovimientosLegales(tablero);
			}
			return Collections.emptyList();
		}

		private void setIconoPiezaCasilla(final Tablero tablero) {

			this.removeAll();
			if (tablero.getCasilla(this.idCasilla).siCasillaOcupada()) {

				//new ImageIcon(getClass().getClassLoader().getResource("Alta_Producto.png"))
				
				try {
					// creo el path para las imagenes de las piezas
					final BufferedImage imagen = ImageIO.read(getClass().getClassLoader().getResource(iconosPiezasPath + 
							tablero.getCasilla(this.idCasilla).getPiezaCasilla().getColorPieza().toString().substring(0, 1)
							+ tablero.getCasilla(this.idCasilla).getPiezaCasilla().toString() + formatoIconos));
					
					
			
					
					JLabel label = new JLabel();
					label.setSize(100, 100);
					
					Image img = imagen.getScaledInstance(label.getWidth(), label.getHeight(),
					        Image.SCALE_SMOOTH);
					ImageIcon imageIcon = new ImageIcon(img);
					label.setIcon(imageIcon);
					
				
					
					add(label);
					
				} catch (IOException e) {

					e.printStackTrace();
				}
			}

		}

		private void setColorCasilla() {// pinta la casilla de negro o blanco dependiendo de si es divisible por 2
			if (Utilidades_Tablero.OCTAVA_FILA[this.idCasilla] || Utilidades_Tablero.SEXTA_FILA[this.idCasilla]
					|| Utilidades_Tablero.CUARTA_FILA[this.idCasilla]
					|| Utilidades_Tablero.SEGUNDA_FILA[this.idCasilla]) {
				setBackground(this.idCasilla % 2 == 0 ? casillaBlanca : casillaNegra);
			} else if (Utilidades_Tablero.SEPTIMA_FILA[this.idCasilla] || Utilidades_Tablero.QUINTA_FILA[this.idCasilla]
					|| Utilidades_Tablero.TERCER_FILA[this.idCasilla]
					|| Utilidades_Tablero.PRIMERA_FILA[this.idCasilla]) {
				setBackground(this.idCasilla % 2 != 0 ? casillaBlanca : casillaNegra);
			}

		}

	}
}
