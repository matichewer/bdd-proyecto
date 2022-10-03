package vuelos.vista.empleado;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Objects;

import javax.swing.AbstractButton;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import com.toedter.calendar.JDateChooser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import vuelos.controlador.ControladorEmpleado;
import vuelos.modelo.empleado.beans.DetalleVueloBean;
import vuelos.modelo.empleado.beans.EmpleadoBean;
import vuelos.modelo.empleado.beans.InstanciaVueloBean;
import vuelos.modelo.empleado.beans.InstanciaVueloClaseBean;
import vuelos.modelo.empleado.beans.PasajeroBean;
import vuelos.modelo.empleado.beans.ReservaBean;
import vuelos.modelo.empleado.beans.UbicacionesBean;

//CLASE IMPLEMENTADA PROVISTA POR LA CATEDRA
public class VentanaEmpleadoImpl extends JFrame implements VentanaEmpleado {

	private static Logger logger = LoggerFactory.getLogger(VentanaEmpleadoImpl.class);

	private static final long serialVersionUID = 1L;
	
	public VentanaEmpleadoImpl() {		
		inicializar();
		this.frame.setVisible(true);
		
	}

	@Override
	public void registrarControlador(ControladorEmpleado controlador) {
		this.controlador = controlador;
	}
	
	@Override
	public void mostrarPanel(String panel) {
		this.frameLayout.show(this.panelInterno, panel);
	}	

	@Override
	public void mostrarVentana() throws Exception {
		if (this.frame != null) {
			this.frame.setVisible(true);
		}
		else 
		{
			throw new Exception("Error la ventana no está disponible");			
		}		
	}

	@Override
	public void eliminarVentana() {
		logger.info("Eliminación de la ventana.");
		this.frame.dispose();
	}

	@Override
	public void informar(String mensaje) {
		logger.info("Crea una ventana modal informando sobre el mensaje.");
		JOptionPane.showMessageDialog(this.frame,mensaje);
	}
	
	public void mostrarEmpleadoLogueado(EmpleadoBean empleado) {
		logger.info("Se registra al empleado {}, {} con legajo {}", empleado.getApellido(), empleado.getNombre(), empleado.getLegajo());
		
		this.lblEmpleado.setText("Legajo: " + String.valueOf(empleado.getLegajo()) + " - " + 
								 empleado.getApellido() + ", " + empleado.getNombre());
		
	}	

	protected void mostrarPanelVuelosDisponibles(String panel) {
		((CardLayout) this.panelVuelosDisponibles.getLayout()).show(this.panelVuelosDisponibles, panel);
	}	
	
	@Override
	public void poblarUbicaciones(ArrayList<UbicacionesBean> ubicaciones) {
		logger.info("Carga los combos de ciudades con las {} ubicaciones recuperados", ubicaciones.size());

		this.cmbOrigen.removeAllItems();
		this.cmbDestino.removeAllItems();
		
		for (UbicacionesBean ubicacion : ubicaciones) {
			this.cmbOrigen.addItem(ubicacion);
			this.cmbDestino.addItem(ubicacion);
		}
	}
	
	@Override
	public void mostrarVuelosDisponiblesSoloIda(ArrayList<InstanciaVueloBean> vuelosDisponibles) {
		logger.info("Se solicita a la ventana mostrar la información de {} vuelos para viaje de ida", vuelosDisponibles.size());
		/*
		 * Se cambia la pantalla mostrada 
		 */
		this.mostrarPanelVuelosDisponibles(VentanaEmpleado.VUELOS_DISPONIBLES_BUSQUEDA_RESULTADO_SOLO_IDA);
		/*
		 * Poblar los componentes
		 */
		this.lblVuelosDisponiblesIDA.setText("Vuelos desde " + ((UbicacionesBean) this.cmbOrigen.getSelectedItem()).getCiudad() +  
												" hasta " + ((UbicacionesBean) this.cmbDestino.getSelectedItem()).getCiudad() + 
												" disponibles para " + this.dateChooserDesde.getDate().toString());
		
		this.poblarTablaVuelosDisponibles(this.modeloTablaVuelosDisponiblesIda, vuelosDisponibles);
		/*
		 * Actualiza la variable interna de vuelos disponibles para cuando debe pasarle info al controlador 
		 * que no está presente en la tabla (para no poner columnas ocultas)
		 */
		this.vuelosDisponiblesIda = vuelosDisponibles;
	}

	@Override
	public void mostrarVuelosDisponiblesVuelta(	ArrayList<InstanciaVueloBean> vuelosDisponiblesIda,
												ArrayList<InstanciaVueloBean> vuelosDisponiblesVuelta) {
		logger.info("Se solicita a la ventana mostrar la información de {} vuelos para viaje de ida y vuelta", vuelosDisponiblesIda.size());
		/*
		 * Se cambia la pantalla mostrada 
		 */
		this.mostrarPanelVuelosDisponibles(VentanaEmpleado.VUELOS_DISPONIBLES_BUSQUEDA_RESULTADO_IDA_VUELTA);
		/*
		 * Poblar los componentes
		 */
		this.lblVuelosDisponiblesIDAenIDAVUELTA.setText("Vuelos desde " + ((UbicacionesBean) this.cmbOrigen.getSelectedItem()).getCiudad() +  
				" hasta " + ((UbicacionesBean) this.cmbDestino.getSelectedItem()).getCiudad() + 
				" disponibles para " + this.dateChooserDesde.getDate().toString());

		this.lblVuelosDisponiblesVUELTAenIDAVUELTA.setText("Vuelos desde " + ((UbicacionesBean) this.cmbDestino.getSelectedItem()).getCiudad() +  
				" hasta " + ((UbicacionesBean) this.cmbOrigen.getSelectedItem()).getCiudad() + 
				" disponibles para " + this.dateChooserHasta.getDate().toString());

		this.poblarTablaVuelosDisponibles(this.modeloTablaVuelosDisponiblesIda, vuelosDisponiblesIda);
		this.poblarTablaVuelosDisponibles(this.modeloTablaVuelosDisponiblesVuelta, vuelosDisponiblesVuelta);
		/*
		 * Actualiza la variable interna de vuelos disponibles para cuando debe pasarle info al controlador 
		 * que no está presente en la tabla (para no poner columnas ocultas)
		 */
		this.vuelosDisponiblesIda = vuelosDisponiblesIda;
		this.vuelosDisponiblesVuelta = vuelosDisponiblesVuelta;
	}
	
	private void poblarTablaVuelosDisponibles(DefaultTableModel modelo, ArrayList<InstanciaVueloBean> vuelosDisponibles) {
		logger.info("Carga la tabla de IDA.");
		
		modelo.setRowCount(0);
		
		for (InstanciaVueloBean vuelo: vuelosDisponibles) {
			
			String[] fila = new String[modelo.getColumnCount()];
			
			fila[modelo.findColumn(VentanaEmpleado.TABLA_VUELOS_DISPONIBLES_NRO_VUELO)] = String.valueOf(vuelo.getNroVuelo());
			fila[modelo.findColumn(VentanaEmpleado.TABLA_VUELOS_DISPONIBLES_AEROPUERTO_SALIDA)] = String.valueOf(vuelo.getAeropuertoSalida().getNombre());
			fila[modelo.findColumn(VentanaEmpleado.TABLA_VUELOS_DISPONIBLES_HORA_SALIDA)] = String.valueOf(vuelo.getHoraSalida().toString());
			fila[modelo.findColumn(VentanaEmpleado.TABLA_VUELOS_DISPONIBLES_AEROPUERTO_LLEGADA)] = String.valueOf(vuelo.getAeropuertoLlegada().getNombre());
			fila[modelo.findColumn(VentanaEmpleado.TABLA_VUELOS_DISPONIBLES_HORA_LLEGADA)] = String.valueOf(vuelo.getHoraLlegada().toString());
			fila[modelo.findColumn(VentanaEmpleado.TABLA_VUELOS_DISPONIBLES_MODELO_AVION)] = String.valueOf(vuelo.getModelo());
			fila[modelo.findColumn(VentanaEmpleado.TABLA_VUELOS_DISPONIBLES_TIEMPO_ESTIMADO)] = String.valueOf(vuelo.getTiempoEstimado().toString());
			
			modelo.addRow(fila); 		
		}
		
	}
	
	@Override
	public void mostrarDetalleVuelosDisponiblesSoloIda(ArrayList<DetalleVueloBean> detalle) {
		logger.info("Carga el detalle tabla de detalle de solo IDA.");
		this.DetallesIda = detalle;
		this.DetallesVuelta = null;
		this.poblarTablaDetalleVuelosDisponibles(this.modeloTablaDetalleVuelosDisponiblesIda, detalle);
	}
	
	private void poblarTablaDetalleVuelosDisponibles(DefaultTableModel modelo, ArrayList<DetalleVueloBean> detalles) {
		modelo.setRowCount(0);
		
		for (DetalleVueloBean detalle: detalles) {
			
			String[] fila = new String[modelo.getColumnCount()];
			
			fila[modelo.findColumn(VentanaEmpleado.TABLA_DETALLE_VUELOS_DISPONIBLES_CLASE)] = String.valueOf(detalle.getClase());
			fila[modelo.findColumn(VentanaEmpleado.TABLA_DETALLE_VUELOS_DISPONIBLES_PRECIO)] = String.valueOf(detalle.getPrecio());
			fila[modelo.findColumn(VentanaEmpleado.TABLA_DETALLE_VUELOS_DISPONIBLES_ASIENTOS_DISPONIBLES)] = String.valueOf(detalle.getAsientosDisponibles());
			
			modelo.addRow(fila); 		
		}	
	}
	
	@Override
	public void mostrarDetalleVuelosDisponiblesIdaViajeIdaVuelta(ArrayList<DetalleVueloBean> detalle) {
		logger.info("Carga el detalle tabla de detalle de IDA en viaje de Ida y Vuelta.");
		this.DetallesIda = detalle;
		this.poblarTablaDetalleVuelosDisponibles(this.modeloTablaDetalleVuelosDisponiblesIda, detalle);
	}

	@Override
	public void mostrarDetalleVuelosDisponiblesVueltaViajeIdaVuelta(ArrayList<DetalleVueloBean> detalle) {
		logger.info("Carga el detalle tabla de detalle de Vuelta en viaje de Ida y Vuelta.");
		this.DetallesVuelta = detalle;
		this.poblarTablaDetalleVuelosDisponibles(this.modeloTablaDetalleVuelosDisponiblesVuelta, detalle);
	}
	
	@Override
	public void mostrarSolicitudReservaSoloIda(ArrayList<String> tiposDocumento, InstanciaVueloBean vuelo,
			DetalleVueloBean detalle) {
		logger.info("El controlador solicitó que se muestre la pantalla para realizar una reserva.");

		lblSeleccionSoloIda.setText("Vuelo desde " + ((UbicacionesBean) this.cmbOrigen.getSelectedItem()).getCiudad() +  
				" hasta " + ((UbicacionesBean) this.cmbDestino.getSelectedItem()).getCiudad() + 
				" para " + this.dateChooserDesde.getDate().toString());
		
		this.lblVueloSeleccionSoloIda.setText(setlblVueloSeleccion(vuelo));
		this.lblDetalleSeleccionSoloIda.setText(setlblDetalleSeleccion(detalle));
		this.lblPasajeroSeleccionSoloIda.setText(setlblPasajeroSeleccion(null));
		this.txtDNIPasajeroSeleccionSoloIda.setText("");
				
		if (!Objects.isNull(tiposDocumento)) {
			this.poblarComboTipoDocumento(tiposDocumento);
		} else {
			logger.error("El arreglo de documentos es null.");
		}
		
		this.mostrarPanelVuelosDisponibles(VentanaEmpleado.VUELOS_DISPONIBLES_RESERVA_SOLO_IDA);
	}

	@Override
	public void mostrarSolicitudReservaIdaVuelta(ArrayList<String> tiposDocumento, InstanciaVueloBean vueloIda,
			DetalleVueloBean detalleIda, InstanciaVueloBean vueloVuelta, DetalleVueloBean detalleVuelta) {
		logger.info("El controlador solicitó que se muestre la pantalla para realizar una reserva IDA y VUELTA.");

		lblSeleccionIda.setText("Vuelo de ida desde " + ((UbicacionesBean) this.cmbOrigen.getSelectedItem()).getCiudad() +  
				" hasta " + ((UbicacionesBean) this.cmbDestino.getSelectedItem()).getCiudad() + 
				" para " + this.dateChooserDesde.getDate().toString());
		lblSeleccionVuelta.setText("Vuelo retorno desde " + ((UbicacionesBean) this.cmbDestino.getSelectedItem()).getCiudad() +  
				" hasta " + ((UbicacionesBean) this.cmbOrigen.getSelectedItem()).getCiudad() + 
				" para " + this.dateChooserHasta.getDate().toString());		
		
		this.lblVueloSeleccionIda.setText(setlblVueloSeleccion(vueloIda));
		this.lblDetalleSeleccionIda.setText(setlblDetalleSeleccion(detalleIda));
		this.lblVueloSeleccionVuelta.setText(setlblVueloSeleccion(vueloVuelta));
		this.lblDetalleSeleccionVuelta.setText(setlblDetalleSeleccion(detalleVuelta));	
		this.lblPasajeroSeleccionIdaVuelta.setText(setlblPasajeroSeleccion(null));
		this.txtDNIPasajeroSeleccionIdaVuelta.setText("");
		
		if (!Objects.isNull(tiposDocumento)) {
			this.poblarComboTipoDocumento(tiposDocumento);
		} else {
			logger.error("El arreglo de documentos es null.");
		}
		
		this.mostrarPanelVuelosDisponibles(VentanaEmpleado.VUELOS_DISPONIBLES_RESERVA_IDA_VUELTA);
	}

	
	@Override
	public void mostrarResultadoReservaSoloIda(ReservaBean reserva) {
		logger.info("Se solicita mostrar la reserva cuyo estado es {}", reserva.getEstado());

		try {
			this.validarReserva(reserva);
		} catch (Exception e) {
			this.controlador.errorEnReserva(e.getLocalizedMessage());
			return;
		}
		
		InstanciaVueloBean vuelo = reserva.getVuelosClase().get(0).getVuelo();
		DetalleVueloBean detalle = reserva.getVuelosClase().get(0).getClase();
		
		UbicacionesBean origen = vuelo.getAeropuertoSalida().getUbicacion();
		UbicacionesBean destino = vuelo.getAeropuertoLlegada().getUbicacion();
		
		this.lblResultadoSeleccionSoloIda.setText("Vuelo desde " + origen.getCiudad() +  
												  " hasta " + destino.getCiudad() +
												  " para " + vuelo.getFechaVuelo().toString() 
												  );

		this.lblResultadoVueloSeleccionSoloIda.setText(setlblVueloSeleccion(vuelo));
		this.lblResultadoDetalleSeleccionSoloIda.setText(setlblDetalleSeleccion(detalle));
		
		this.lblResultadoPasajeroSeleccionSoloIda.setText(setlblResultadoPasajero(reserva.getPasajero()));

		this.lblResultadoReservaSoloIda.setText(setlblResultadoReserva(reserva));
		
		this.mostrarPanelVuelosDisponibles(VentanaEmpleado.VUELOS_DISPONIBLES_RESERVA_RESULTADO_SOLO_IDA);
	}
	
	@Override
	public void mostrarResultadoReservaIdaVuelta(ReservaBean reserva) {
		logger.info("Se solicita mostrar la reserva cuyo estado es {}", reserva.getEstado());

		try {
			this.validarReserva(reserva);
		} catch (Exception e) {
			this.controlador.errorEnReserva(e.getLocalizedMessage());
			return;
		}
		
		InstanciaVueloBean vueloIda = reserva.getVuelosClase().get(0).getVuelo();
		DetalleVueloBean detalleIda = reserva.getVuelosClase().get(0).getClase();
		
		UbicacionesBean origenIda = vueloIda.getAeropuertoSalida().getUbicacion();
		UbicacionesBean destinoIda = vueloIda.getAeropuertoLlegada().getUbicacion();

		InstanciaVueloBean vueloVuelta = reserva.getVuelosClase().get(1).getVuelo();
		DetalleVueloBean detalleVuelta = reserva.getVuelosClase().get(1).getClase();
		
		UbicacionesBean origenVuelta = vueloVuelta.getAeropuertoSalida().getUbicacion();
		UbicacionesBean destinoVuelta = vueloVuelta.getAeropuertoLlegada().getUbicacion();
		
		this.lblResultadoSeleccionIda.setText("Vuelo desde " + origenIda.getCiudad() +  
												  " hasta " + destinoIda.getCiudad() +
												  " para " + vueloIda.getFechaVuelo().toString() 
												  );

		this.lblResultadoVueloSeleccionIda.setText(setlblVueloSeleccion(vueloIda));
		this.lblResultadoDetalleSeleccionIda.setText(setlblDetalleSeleccion(detalleIda));
		
		this.lblResultadoSeleccionVuelta.setText("Vuelo desde " + origenVuelta.getCiudad() +  
				  " hasta " + destinoVuelta.getCiudad() +
				  " para " + vueloVuelta.getFechaVuelo().toString() 
				  );

		this.lblResultadoVueloSeleccionVuelta.setText(setlblVueloSeleccion(vueloVuelta));
		this.lblResultadoDetalleSeleccionVuelta.setText(setlblDetalleSeleccion(detalleVuelta));		
		
		/*
		 * Informacion de Pasajero y Reserva
		 */
		
		this.lblResultadoPasajeroSeleccionIdaVuelta.setText(setlblResultadoPasajero(reserva.getPasajero()));

		this.lblResultadoReservaIdaVuelta.setText(setlblResultadoReserva(reserva));
		
		this.mostrarPanelVuelosDisponibles(VentanaEmpleado.VUELOS_DISPONIBLES_RESERVA_RESULTADO_IDA_VUELTA);
	}

	/*
	 * Verifica que la reserva tenga todos los objetos ingresados, si no valida tira excepcion con mensaje a mostrar
	 */
	private boolean validarReserva(ReservaBean reserva) throws Exception {
		
		if (reserva.esIdaVuelta() && reserva.getVuelosClase().size() != 2) {
			throw new Exception("La cantidad de instancias de vuelo no es la indicada.");
		}			

		if (!reserva.esIdaVuelta() && reserva.getVuelosClase().size() != 1) {
			throw new Exception("La cantidad de instancias de vuelo no es la indicada.");
		}

		for (Iterator<InstanciaVueloClaseBean> i = reserva.getVuelosClase().iterator(); i.hasNext();) {
			InstanciaVueloClaseBean vc = (InstanciaVueloClaseBean) i.next();
			
			if (vc.getVuelo() == null) {
				throw new Exception("No hay vuelo en la reserva.");
			}
					
			if (vc.getClase() == null) {
				throw new Exception("Falta la clase en la reserva.");
			}
			
		}
		
		if (reserva.getPasajero() == null) {
			throw new Exception("Falta el pasajero en la reserva.");
		}
		
		if (reserva.getEmpleado() == null) {
			throw new Exception("Falta el empleado en la reserva.");
		}
		
		return true;
	}
	
	/*
	 * Propiedades 
	 */	
	protected ControladorEmpleado controlador;
	protected VentanaEmpleado ventana;
	
	protected JFrame frame;
	protected CardLayout frameLayout;
	/** 
	 * Panel que contiene las diferentes pantallas a mostrar segun la opcion elegida
	 */
	protected JPanel panelInterno;

	protected JMenuItem mntmCerrarSesion;
	protected JMenuItem mntmSalir;	
    
	protected JLabel lblEmpleado;	

	/*
	 * Campos para Vuelos Disponibles
	 */
	protected JPanel panelVuelosDisponibles;	
	protected JRadioButton rIda,rIdaVuelta;    
	protected JDateChooser dateChooserDesde, dateChooserHasta;	
	protected JComboBox<UbicacionesBean> cmbOrigen, cmbDestino;
	protected JButton btnBuscarVuelosDisponibles;
	
	protected JLabel lblVuelosDisponiblesIDA;	
	protected JLabel lblVuelosDisponiblesIDAenIDAVUELTA;
	protected JLabel lblVuelosDisponiblesVUELTAenIDAVUELTA;
	
	/**
	 * Modelos para las tablas de Vuelos Disponibles
	 */
	protected DefaultTableModel modeloTablaVuelosDisponiblesIda;	
	protected DefaultTableModel modeloTablaDetalleVuelosDisponiblesIda;
	protected DefaultTableModel modeloTablaVuelosDisponiblesVuelta;	
	protected DefaultTableModel modeloTablaDetalleVuelosDisponiblesVuelta;
		
	/*
	 * Listas obtenidas del controlador que guardan los objetos en su clase original.
	 * 
	 * se puede utilizar el metodo getVuelo o getDetalle para buscar sobre ellas. 
	 */
	private ArrayList<InstanciaVueloBean> vuelosDisponiblesIda;
	private ArrayList<InstanciaVueloBean> vuelosDisponiblesVuelta;
	private ArrayList<DetalleVueloBean> DetallesIda;
	private ArrayList<DetalleVueloBean> DetallesVuelta;
	
	/*
	 * componentes del panel de reserva
	 */
	private JLabel lblSeleccionSoloIda;
	private JLabel lblVueloSeleccionSoloIda;
	private JLabel lblDetalleSeleccionSoloIda;

	private JLabel lblSeleccionIda;
	private JLabel lblVueloSeleccionIda;
	private JLabel lblDetalleSeleccionIda;
	private JLabel lblSeleccionVuelta;
	private JLabel lblVueloSeleccionVuelta;
	private JLabel lblDetalleSeleccionVuelta;	
	
	protected JComboBox<String> cmbTipoDNISoloIda;	
	protected JComboBox<String> cmbTipoDNIIdaVuelta;
	
	private JLabel lblPasajeroSeleccionSoloIda;
	private JLabel lblPasajeroSeleccionIdaVuelta;
	private JTextField txtDNIPasajeroSeleccionSoloIda;
	private JTextField txtDNIPasajeroSeleccionIdaVuelta; 
	
	/*
	 * componentes del panel de resultado de reserva Solo Ida
	 */
	private JLabel lblResultadoSeleccionSoloIda;
	private JLabel lblResultadoVueloSeleccionSoloIda;
	private JLabel lblResultadoDetalleSeleccionSoloIda;

	private JLabel lblResultadoTipoDNISoloIda;	
	private JLabel lblResultadoPasajeroSeleccionSoloIda;
	private JLabel lblResultadoReservaSoloIda;
	
	/*
	 * componentes del panel de resultado de reserva Ida y Vuelta
	 */
	private JLabel lblResultadoSeleccionIda;
	private JLabel lblResultadoVueloSeleccionIda;
	private JLabel lblResultadoDetalleSeleccionIda;
	private JLabel lblResultadoSeleccionVuelta;
	private JLabel lblResultadoVueloSeleccionVuelta;
	private JLabel lblResultadoDetalleSeleccionVuelta;	

	private JLabel lblResultadoTipoDNIIdaVuelta;
	private JLabel lblResultadoPasajeroSeleccionIdaVuelta;
	private JLabel lblResultadoReservaIdaVuelta;
	
	/*
	 * Getters de los componentes
	 * 
	 */
	private AbstractButton getMenuItemCerrarSesion() {
		return this.mntmCerrarSesion;
	}

	
	private AbstractButton getMenuItemSalir() {
		return this.mntmSalir;
	}

	private JRadioButton getRadioIda() {
		return this.rIda;
	}
	
	private JRadioButton getRadioIdaVuelta() {
		return this.rIdaVuelta;
	}
		
	private JComboBox<UbicacionesBean> getCmbOrigen(){
		return this.cmbOrigen;
	}

	private JComboBox<UbicacionesBean> getCmbDestino(){
		return this.cmbDestino;
	}
	
	/**
	 * Determina si selecciono Ida y Vuelta 
	 * @return
	 */
	protected boolean isIdaVuelta() {
		return this.getRadioIdaVuelta().isSelected();
	}
	
	protected JDateChooser getFechaDesde() {
		return this.dateChooserDesde;
	}

	protected JDateChooser getFechaHasta() {
		return this.dateChooserHasta;
	}
	
	private AbstractButton getBtnBuscarVuelosDisponibles() {
		return this.btnBuscarVuelosDisponibles;
	}
	
	/*
	 * Metodo encargado de iniciar la ventana y sus componentes
	 */
	private void inicializar()
	{ 
		logger.debug("Inicializacion de la ventana del Empleado");
		
		this.frame = new JFrame();
		this.frame.setResizable(false);
		this.frame.setTitle("Administración de Vuelos");
		this.frame.setBounds(20, 20, 1200, 600);
		/*
		 * Se evita el comportamiento por defecto que hace un dispose de la ventana y cierra, porque 
		 * se requiere que el controlador se haga cargo de cerrar todo, para cerrar las conexiones a la BD y luego
		 * las ventanas.
		 */
		this.frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.frame.addWindowListener(new WindowAdapter() {
			@Override
		    public void windowClosing(WindowEvent we){
				controlador.salirAplicacion();
		    }
		});
		
		this.frameLayout = new CardLayout();

		this.panelInterno = new JPanel();
		this.panelInterno.setLayout(this.frameLayout);
		this.panelInterno.add(this.crearPanelVacio(),VentanaEmpleado.VACIO);
		this.panelInterno.add(this.crearPanelVuelosDisponibles(),VentanaEmpleado.VUELOS_DISPONIBLES);
		
		this.lblEmpleado = new JLabel();
		this.lblEmpleado.setFont(new Font("Arial", Font.BOLD, 12));
		this.lblEmpleado.setHorizontalAlignment(SwingConstants.RIGHT);
		
		this.frame.getContentPane().setLayout(new BorderLayout(1,0));
		this.frame.getContentPane().add(panelInterno,BorderLayout.CENTER);
		this.frame.getContentPane().add(lblEmpleado,BorderLayout.NORTH);
		this.frame.setJMenuBar(this.crearMenuOpciones());
		
		logger.debug("Se registran los listeners.");
		this.registrarEventos();
	}	
	
	private void registrarEventos() {
		
		this.getMenuItemCerrarSesion().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				controlador.cerrarSesion();
			}
		});
			
		this.getMenuItemSalir().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				controlador.salirAplicacion();
			}
		});
	
		/*
		 * Eventos de vuelos disponibles
		 */
		this.getRadioIda().addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent e) {
				if (getRadioIda().isSelected()) {
					getFechaHasta().setDate(null);
				}
			}
		});
		
		this.getFechaDesde().getDateEditor().addPropertyChangeListener(new PropertyChangeListener() {
			/*
			 * Evento que escucha cuando cambia la fecha desde para impedir que la fecha hasta sea anterior
			 */

			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				if (evt.getPropertyName().equals("date"))
			    {
					if (getFechaDesde().getDate() != null && 
							getFechaHasta().getDate() != null &&
							getFechaDesde().getDate().compareTo(getFechaHasta().getDate()) > 0) {
						logger.debug("Fecha desde es mayor a fecha hasta");
						getFechaHasta().setDate(null);
					}
					// Setea la fecha hasta para que no pueda seleccionar un valor menor a fecha desde
					getFechaHasta().setMinSelectableDate(getFechaDesde().getDate());
			    }				
			}
		});
			
		this.getBtnBuscarVuelosDisponibles().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				logger.info("Ejecuta el listener de buscar vuelos disponibles.");
				
				limpiarTablasVuelosDisponibles();
				
				controlador.buscarVuelosDisponibles(
						isIdaVuelta(),
						getFechaDesde().getDate(),
						getFechaHasta().getDate(),
						(UbicacionesBean) getCmbOrigen().getSelectedItem(),
						(UbicacionesBean) getCmbDestino().getSelectedItem()						
				);
			};
		});
		
		/*
		 * Eventos de las tablas
		 */
		
	}	

	private JMenuBar crearMenuOpciones() {
		JMenuBar barraDeMenu = new JMenuBar();
		JMenu menuOpciones=new JMenu("Menu");
		menuOpciones.setFont(new Font("Segoe UI", Font.BOLD, 14));
		barraDeMenu.add(menuOpciones);

		this.mntmCerrarSesion = new JMenuItem("Cerrar Sesion");
		this.mntmCerrarSesion.setFont(new Font("Segoe UI", Font.BOLD, 12));
		menuOpciones.add(this.mntmCerrarSesion);

		this.mntmSalir = new JMenuItem("Salir");
		this.mntmSalir.setFont(new Font("Segoe UI", Font.BOLD, 12));
		menuOpciones.add(this.mntmSalir);
		
		return barraDeMenu;		
	}
	
	private JPanel crearPanelBotoneraVolverMenuPrincipal() {
		JPanel panelBotonera = new JPanel();		
		panelBotonera.setLayout(new FlowLayout(FlowLayout.RIGHT));
	
		JButton btnVolverMenuOpciones = new JButton("Cancelar");
		panelBotonera.add(btnVolverMenuOpciones);
		
		btnVolverMenuOpciones.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				limpiarPanelVuelosDisponibles();
				//mostrarPanel(VentanaEmpleado.VACIO);
				mostrarPanel(VentanaEmpleado.VUELOS_DISPONIBLES_BUSCAR);
			}
		});
		
		return panelBotonera;
	}		

	private Component crearPanelVuelosDisponibles() {
		
		this.modeloTablaVuelosDisponiblesIda = this.inicializarModelo(this.getColumnasTablaVuelosDisponibles());
		this.modeloTablaDetalleVuelosDisponiblesIda = this.inicializarModelo(this.getColumnasTablaDetalleVuelosDisponibles());
		this.modeloTablaVuelosDisponiblesVuelta = this.inicializarModelo(this.getColumnasTablaVuelosDisponibles());
		this.modeloTablaDetalleVuelosDisponiblesVuelta = this.inicializarModelo(this.getColumnasTablaDetalleVuelosDisponibles());

		/*
		 * Contenedor de las distintas ventanas
		 */
		this.panelVuelosDisponibles = new JPanel();
		this.panelVuelosDisponibles.setLayout(new CardLayout());
		this.panelVuelosDisponibles.add(this.crearPanelBusquedaVuelo(), VentanaEmpleado.VUELOS_DISPONIBLES_BUSCAR);
		this.panelVuelosDisponibles.add(this.crearPanelResultadoVueloIda(), VentanaEmpleado.VUELOS_DISPONIBLES_BUSQUEDA_RESULTADO_SOLO_IDA);
		this.panelVuelosDisponibles.add(this.crearPanelResultadoVueloIdaVuelta(), VentanaEmpleado.VUELOS_DISPONIBLES_BUSQUEDA_RESULTADO_IDA_VUELTA);
		this.panelVuelosDisponibles.add(this.crearPanelReservaSoloIda(), VentanaEmpleado.VUELOS_DISPONIBLES_RESERVA_SOLO_IDA);
		this.panelVuelosDisponibles.add(this.crearPanelReservaIdaVuelta(), VentanaEmpleado.VUELOS_DISPONIBLES_RESERVA_IDA_VUELTA);
		this.panelVuelosDisponibles.add(this.crearPanelResultadoReservaSoloIda(), VentanaEmpleado.VUELOS_DISPONIBLES_RESERVA_RESULTADO_SOLO_IDA);
		this.panelVuelosDisponibles.add(this.crearPanelResultadoReservaIdaVuelta(), VentanaEmpleado.VUELOS_DISPONIBLES_RESERVA_RESULTADO_IDA_VUELTA);
		
		/*
		 * Contenedor principal que tiene las ventanas y una botonera
		 */
		JPanel panelPpal = new JPanel();
		panelPpal.setLayout(new BorderLayout(0, 0));
		panelPpal.add(this.panelVuelosDisponibles, BorderLayout.CENTER);
		panelPpal.add(this.crearPanelBotoneraVolverMenuPrincipal(), BorderLayout.SOUTH);		
		panelPpal.setVisible(false);		
		
		return panelPpal;
	}

	@Override
	public void mostrarPasajero(boolean esIdaVuelta, PasajeroBean pasajero) {
		logger.info("La ventana muestra al pasajero.");
		if (esIdaVuelta) {
			logger.debug("Se muestra al pasajero en el panel de ida y vuelta");
			this.lblPasajeroSeleccionIdaVuelta.setText(setlblPasajeroSeleccion(pasajero));
		} else {
			logger.debug("Se muestra al pasajero en el panel de solo ida");
			this.lblPasajeroSeleccionSoloIda.setText(setlblPasajeroSeleccion(pasajero));	
		}		
	}
		
	/**
	 * Limpia las componentes de la pantalla de Busqueda de Vuelos Disponibles y
	 * muestra el panel de Busqueda.
	 */
	private void limpiarPanelVuelosDisponibles() {
		this.getFechaDesde().setDate(null);
		this.getFechaHasta().setDate(null);
		this.mostrarPanelVuelosDisponibles(VentanaEmpleado.VUELOS_DISPONIBLES_BUSCAR);
	}
	
	/**
	 * Muestra la pantalla de Busqueda de Vuelos Disponibles y limpia las busquedas previas
	 */
	private void volverBusquedaVuelosDisponibles() {
		this.limpiarTablasVuelosDisponibles();
		this.mostrarPanelVuelosDisponibles(VentanaEmpleado.VUELOS_DISPONIBLES_BUSCAR);
	}
	
	/**
	 * Muestra la pantalla de selección de Vuelos Disponibles
	 */
	private void volverSeleccionVuelosSoloIda() {
		this.mostrarPanelVuelosDisponibles(VentanaEmpleado.VUELOS_DISPONIBLES_BUSQUEDA_RESULTADO_SOLO_IDA);
	}
	
	private void volverSeleccionVuelosIdaVuelta() {
		this.mostrarPanelVuelosDisponibles(VentanaEmpleado.VUELOS_DISPONIBLES_BUSQUEDA_RESULTADO_IDA_VUELTA);
	}
	

	private void limpiarTablasVuelosDisponibles() {
		this.DetallesIda = null;
		this.DetallesVuelta = null;
		this.modeloTablaDetalleVuelosDisponiblesIda.setRowCount(0);
		this.modeloTablaDetalleVuelosDisponiblesVuelta.setRowCount(0);
		this.modeloTablaVuelosDisponiblesIda.setRowCount(0);
		this.modeloTablaVuelosDisponiblesVuelta.setRowCount(0);
	}
	
	/**
	 * Crea el panel para la busqueda de vuelo
	 * 
	 * @return Panel
	 */
	private Component crearPanelBusquedaVuelo() {
		/*
		 * Panel principal
		 */
		JPanel panelBusquedaVuelos = new JPanel();
		panelBusquedaVuelos.setLayout(new BorderLayout());

		/*
		 * Titulo
		 */
		JLabel lblBusquedaVuelos = new JLabel("Búsqueda de Vuelos Disponibles");
		lblBusquedaVuelos.setHorizontalAlignment(SwingConstants.CENTER);
		lblBusquedaVuelos.setFont(new Font("Arial", Font.BOLD, 14));
		panelBusquedaVuelos.add(lblBusquedaVuelos, BorderLayout.NORTH);
		
		/*
		 * Componentes
		 */
		this.rIda=new JRadioButton("Ida");    
		this.rIdaVuelta=new JRadioButton("Ida y Vuelta");    
		ButtonGroup bg=new ButtonGroup();    
		bg.add(this.rIda);
		bg.add(this.rIdaVuelta);
		this.rIda.setSelected(true);

		JPanel panelRadioButton = new JPanel(){ 
			
			private static final long serialVersionUID = 1L;

			public Insets getInsets() {// agrega un espacio al top
				    return new Insets(80, 0, 0, 0);
				  }
		};
		panelRadioButton.setLayout(new FlowLayout(FlowLayout.CENTER,20,0));
		panelRadioButton.add(this.rIda);
		panelRadioButton.add(this.rIdaVuelta);
		
		JPanel panelDesde = new JPanel();
		panelDesde.setLayout(new FlowLayout(FlowLayout.CENTER,10,0));
		JLabel lblDesde = new JLabel("Desde:");
		lblDesde.setHorizontalAlignment(SwingConstants.CENTER);
		dateChooserDesde = new JDateChooser();
		dateChooserDesde.setPreferredSize(new Dimension(150,25));		
		dateChooserDesde.setDateFormatString("dd/MM/yyyy");		
		//dateChooserDesde.add(lblDesde, BorderLayout.WEST);
		panelDesde.add(lblDesde);
		panelDesde.add(dateChooserDesde);
		
		JPanel panelHasta = new JPanel();
		panelHasta.setLayout(new FlowLayout(FlowLayout.CENTER,10,0));
		JLabel lblHasta = new JLabel("Hasta:");
		lblHasta.setHorizontalAlignment(SwingConstants.CENTER);		
		dateChooserHasta = new JDateChooser();
		dateChooserHasta.setPreferredSize(new Dimension(150,25));
		dateChooserHasta.setDateFormatString("dd/MM/yyyy");		
		//dateChooserHasta.add(lblHasta, BorderLayout.WEST);
		panelHasta.add(lblHasta);
		panelHasta.add(dateChooserHasta);

		JPanel panelDesdeHasta = new JPanel();
		panelDesdeHasta.setLayout(new FlowLayout(FlowLayout.CENTER,20,0));
		panelDesdeHasta.add(panelDesde);
		panelDesdeHasta.add(panelHasta);

		JPanel panelOrigen = new JPanel();
		panelOrigen.setLayout(new FlowLayout(FlowLayout.CENTER,10,0));
		JLabel lblOrigen = new JLabel("Origen:");		
		this.cmbOrigen = new JComboBox<UbicacionesBean>();
		panelOrigen.add(lblOrigen);
		panelOrigen.add(cmbOrigen);

		JPanel panelDestino = new JPanel();
		panelDestino.setLayout(new FlowLayout(FlowLayout.CENTER,10,0));
		JLabel lblDestino = new JLabel("Destino:");		
		this.cmbDestino = new JComboBox<UbicacionesBean>();
		panelDestino.add(lblDestino);
		panelDestino.add(cmbDestino);

		JPanel panelOrigenDestino = new JPanel();
		panelOrigenDestino.setLayout(new FlowLayout(FlowLayout.CENTER,20,0));
		panelOrigenDestino.add(panelOrigen);
		panelOrigenDestino.add(panelDestino);
					
		JPanel panelComponentes = new JPanel();
		panelComponentes.setLayout(new BoxLayout(panelComponentes,BoxLayout.Y_AXIS));
		panelComponentes.add(panelRadioButton);
		panelComponentes.add(panelDesdeHasta);
		panelComponentes.add(panelOrigenDestino);
		
		this.btnBuscarVuelosDisponibles = new JButton("Buscar");
		panelBusquedaVuelos.add(panelComponentes , BorderLayout.CENTER);
		panelBusquedaVuelos.add(btnBuscarVuelosDisponibles , BorderLayout.PAGE_END);
		
		
		return panelBusquedaVuelos;
	}
	
	/*
	 * Recupera un vuelo de una lista en base el nroVuelo. Si no lo encuentra retorna null
	 */
	private InstanciaVueloBean getVuelo(ArrayList<InstanciaVueloBean> lista, String nroVuelo) {
		InstanciaVueloBean vuelo = null;
		if (!Objects.isNull(lista)) {
			for (InstanciaVueloBean v : lista) {
				if (v.getNroVuelo() == nroVuelo) {
					vuelo = v;
					break;
				}
			}		
		}
		return vuelo;
	}
	
	/**
	 * Crea un String con HTML de la información del vuelo proporcionado para ser usado en un JLabel y que lo muestre
	 * en multiples lineas.
	 * 
	 * @param vuelo
	 * @return String con formato HTML
	 */
	private String setlblVueloSeleccion(InstanciaVueloBean vuelo) {
		if (Objects.isNull(vuelo)) {
			return "<html><strong>" + "No hay ningún vuelo<br>seleccionado</strong></html>";
		} else {
			String s = 	"<html>" + 
						VentanaEmpleado.TABLA_VUELOS_DISPONIBLES_NRO_VUELO + ": " + vuelo.getNroVuelo() + "<br>" +
						VentanaEmpleado.TABLA_VUELOS_DISPONIBLES_AEROPUERTO_SALIDA + ": " + vuelo.getAeropuertoSalida() + "<br>" +
						VentanaEmpleado.TABLA_VUELOS_DISPONIBLES_HORA_SALIDA + ": " + vuelo.getHoraSalida().toString() + "<br>" +
						VentanaEmpleado.TABLA_VUELOS_DISPONIBLES_AEROPUERTO_LLEGADA + ": " + vuelo.getAeropuertoLlegada() + "<br>" +
						VentanaEmpleado.TABLA_VUELOS_DISPONIBLES_HORA_LLEGADA + ": " + vuelo.getHoraLlegada().toString() + "<br>" +
						VentanaEmpleado.TABLA_VUELOS_DISPONIBLES_MODELO_AVION + ": " + vuelo.getModelo() + "<br>" +
						VentanaEmpleado.TABLA_VUELOS_DISPONIBLES_TIEMPO_ESTIMADO + ": " + vuelo.getTiempoEstimado().toString() +
						"</html>";
			return s;			
		}				
	}

	private DetalleVueloBean getDetalle(ArrayList<DetalleVueloBean> lista, String clase) {
		DetalleVueloBean detalle = null;
		if (!Objects.isNull(lista)) {
			for (DetalleVueloBean d : lista) {
				if (d.getClase() == clase) {
					detalle = d;
					break;
				}
			}		
		}
		return detalle;
	}
	
	private String setlblDetalleSeleccion(DetalleVueloBean detalle) {
		if (Objects.isNull(detalle)) {
			return "<html><strong>" + "No hay clase seleccionada</strong></html>";
		} else {
			String s = 	"<html>" + 
						VentanaEmpleado.TABLA_DETALLE_VUELOS_DISPONIBLES_CLASE + ": " + detalle.getClase() + "<br>" +
						VentanaEmpleado.TABLA_DETALLE_VUELOS_DISPONIBLES_PRECIO + ": " + String.valueOf(detalle.getPrecio()) + "<br>" +
						VentanaEmpleado.TABLA_DETALLE_VUELOS_DISPONIBLES_ASIENTOS_DISPONIBLES + ": " + String.valueOf(detalle.getAsientosDisponibles()) +
						"</html>";
			return s;			
		}
	}
	
	private String setlblPasajeroSeleccion(PasajeroBean pasajero) {
		if (Objects.isNull(pasajero)) {
			return "<html><strong>" + "Debe seleccionar un pasajero</strong></html>";
		} else {
			String s = 	"<html>" + 
						"Apellido: " + pasajero.getApellido() + "<br>" +
						"Nombre: " + pasajero.getNombre() + "<br>" +
						pasajero.getTipoDocumento() + ":" + String.valueOf(pasajero.getNroDocumento()) + "<br>" +
						"Nacionalidad: " + pasajero.getNacionalidad() + "<br>" +
						"Dirección: " + pasajero.getDireccion() + "<br>" +
						"Telefono: " + pasajero.getTelefono() + 						
						"</html>";
			return s;			
		}
	}
	
	private String setlblResultadoPasajero(PasajeroBean pasajero) {
		if (Objects.isNull(pasajero)) {
			return "<html><strong>" + "Debe seleccionar un pasajero</strong></html>";
		} else {
			String s = 	"<html>" + 
					    "<strong><span>" + "Datos del Pasajero" + "</span></strong><br><br>" +
						"Apellido: " + pasajero.getApellido() + "<br>" +
						"Nombre: " + pasajero.getNombre() + "<br>" +
						pasajero.getTipoDocumento() + ":" + String.valueOf(pasajero.getNroDocumento()) + "<br>" +
						"Nacionalidad: " + pasajero.getNacionalidad() + "<br>" +
						"Dirección: " + pasajero.getDireccion() + "<br>" +
						"Telefono: " + pasajero.getTelefono() + 						
						"</html>";
			return s;			
		}
	}
	
	private String setlblResultadoReserva(ReservaBean reserva) {
		String s = "";
		if (Objects.isNull(reserva)) {
			s = "<html><strong>" + "La reserva está vacía</strong></html>";
		} else {
			s = 	"<html>" + 
					"<strong><span style='color:blue;'>" + reserva.getEstado() + "</span></strong><br><br>" +
					"Número: " + reserva.getNumero() + "<br>" +
					"Fecha: " + reserva.getFecha().toString() + "<br>" +
					"Vencimiento: " + reserva.getVencimiento().toString() + "<br>" +
					"Registrada por el empleado: [" + reserva.getEmpleado().getLegajo() + "] " +
												 reserva.getEmpleado().getApellido() + ", " + 
												 reserva.getEmpleado().getNombre() + "<br>" +
					"</html>";
		}
		return s;
	}
		
	/**
	 * Crea el panel donde se muestra el resultado de la busqueda de vuelos disponibles 
	 * 
	 * @return Panel
	 */
	private Component crearPanelResultadoVueloIda() {
		JPanel panelResultadoVuelo = new JPanel();
		panelResultadoVuelo.setLayout(null);
		
		/*
		 * Label que diga Vuelos de IDA disponibles
		 * Tabla de Vuelos IDA - Tabla de Detalle de Vuelos IDA
		 * 
		 * Botonera Volver - Reservar
		 */
		JLabel lblVueloSeleccionIDA = new JLabel(setlblVueloSeleccion(null),JLabel.LEFT);
		lblVueloSeleccionIDA.setVerticalAlignment(JLabel.TOP);
		lblVueloSeleccionIDA.setFont(new Font("Verdana", Font.PLAIN, 12));
		lblVueloSeleccionIDA.setBounds(890, 30, 300, 380);

		JLabel lblDetalleSeleccionIDA = new JLabel(setlblDetalleSeleccion(null),JLabel.LEFT);
		lblDetalleSeleccionIDA.setVerticalAlignment(JLabel.TOP);
		lblDetalleSeleccionIDA.setFont(new Font("Verdana", Font.PLAIN, 12));
		lblDetalleSeleccionIDA.setBounds(890, 150, 300, 380);		
		
		JTable tablaVuelosDisponiblesIda = this.crearTabla(this.modeloTablaVuelosDisponiblesIda);
		tablaVuelosDisponiblesIda.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (! e.getValueIsAdjusting()){   // Porque sino se dispara multiples veces
					logger.debug("debe informar al controlador para que recupere la info del detalle");
					
					if (tablaVuelosDisponiblesIda.getSelectedRow() != -1) {
						String nroVuelo = (String) tablaVuelosDisponiblesIda.getValueAt(
														tablaVuelosDisponiblesIda.getSelectedRow(),
														modeloTablaVuelosDisponiblesIda.findColumn(TABLA_VUELOS_DISPONIBLES_NRO_VUELO)
											);
						InstanciaVueloBean vuelo = getVuelo(vuelosDisponiblesIda,nroVuelo);
						lblVueloSeleccionIDA.setText(setlblVueloSeleccion(vuelo));
						lblDetalleSeleccionIDA.setText(setlblDetalleSeleccion(null));
						controlador.cambioSeleccionVueloViajeSoloIda(vuelo);						
					} else {
						lblVueloSeleccionIDA.setText(setlblVueloSeleccion(null));
						lblDetalleSeleccionIDA.setText(setlblDetalleSeleccion(null));
						controlador.cambioSeleccionVueloViajeSoloIda(null);						
					}
				}
			}
		});
		
		JTable tablaDetalleVuelosDisponiblesIda = this.crearTabla(this.modeloTablaDetalleVuelosDisponiblesIda);
		tablaDetalleVuelosDisponiblesIda.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (! e.getValueIsAdjusting()){   // Porque sino se dispara multiples veces					
					logger.debug("se selecciono una clase o se cambio la seleccion de vuelo y se actualizaron las clases (deseleccion)");
					
					DetalleVueloBean detalle;
					if (tablaDetalleVuelosDisponiblesIda.getSelectedRow() != -1) {
						String clase = (String) tablaDetalleVuelosDisponiblesIda.getValueAt(
													tablaDetalleVuelosDisponiblesIda.getSelectedRow(),
													modeloTablaDetalleVuelosDisponiblesIda.findColumn(TABLA_DETALLE_VUELOS_DISPONIBLES_CLASE)
										);
						detalle = getDetalle(DetallesIda,clase);
					} else {
						detalle = getDetalle(DetallesIda,null);
					}
					lblDetalleSeleccionIDA.setText(setlblDetalleSeleccion(detalle));
					controlador.cambioSeleccionDetalleVueloViajeSoloIda(detalle);
				}
			}
		});
		
		this.lblVuelosDisponiblesIDA = new JLabel("Vuelos desde _ hasta _ disponibles para la fecha _ ");
		this.lblVuelosDisponiblesIDA.setBounds(12, 10, 530, 16);
		
		JPanel panelVuelosDisponiblesIda = new JPanel();
		panelVuelosDisponiblesIda.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		panelVuelosDisponiblesIda.setLayout(new GridLayout(1, 1, 0, 0));
		panelVuelosDisponiblesIda.add(new JScrollPane(tablaVuelosDisponiblesIda));
		panelVuelosDisponiblesIda.setBounds(12, 30, 550, 380);

		JLabel lblDetalleVuelosIDA = new JLabel("Detalle");
		lblDetalleVuelosIDA.setBounds(570, 10, 180, 16);
		
		JPanel panelDetalleIda = new JPanel();
		panelDetalleIda.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		panelDetalleIda.setLayout(new GridLayout(1, 1, 0, 0));
		panelDetalleIda.add(new JScrollPane(tablaDetalleVuelosDisponiblesIda));
		panelDetalleIda.setBounds(570, 30, 310, 380);
		
		/*
		 * Botonera
		 */
		JPanel panelBotonera = new JPanel();
		//panelBotonera.setBorder(new LineBorder(new Color(120, 0, 230), 1, true));
		panelBotonera.setLayout(new GridLayout(1, 3, 70, 0));
	
		JButton btnVolver = new JButton("Volver");
		btnVolver.setToolTipText("Para realizar otra búsqueda");
		btnVolver.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {				
				volverBusquedaVuelosDisponibles();
			}
		});		
		
		JButton btnLimpiar = new JButton("Limpiar Selección");		
		btnLimpiar.setToolTipText("Quita las selecciones realizadas.");
		btnLimpiar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				/*
				 * Quita la seleccion del vuelo y por lo tanto no tiene detalle
				 */
				DetallesIda = null;
				modeloTablaDetalleVuelosDisponiblesIda.setRowCount(0);
				tablaVuelosDisponiblesIda.clearSelection();
				controlador.limpiarSeleccion();
			}
		});
				
		JButton btnReservar = new JButton("Confirmar Selección");
		btnReservar.setToolTipText("Confirma que desea reservar los vuelos seleccionados y pasa a completar datos.");
		btnReservar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				controlador.vueloSeleccionado();
			}
		});
		
		panelBotonera.add(btnVolver);
		panelBotonera.add(btnLimpiar);
		panelBotonera.add(btnReservar);
		panelBotonera.setBounds(120, 435, 900, 30);
				
		panelResultadoVuelo.add(this.lblVuelosDisponiblesIDA);
		panelResultadoVuelo.add(panelVuelosDisponiblesIda);
		panelResultadoVuelo.add(lblDetalleVuelosIDA);
		panelResultadoVuelo.add(panelDetalleIda);
		panelResultadoVuelo.add(lblVueloSeleccionIDA);
		panelResultadoVuelo.add(lblDetalleSeleccionIDA);
		panelResultadoVuelo.add(panelBotonera);
		
		return panelResultadoVuelo;
	
	}
	
	private Component crearPanelResultadoVueloIdaVuelta() {
		JPanel panelResultadoVuelo = new JPanel();
		panelResultadoVuelo.setLayout(null);
		
		/*
		 * Label que diga Vuelos de IDA disponibles
		 * Tabla de Vuelos IDA - Tabla de Detalle de Vuelos IDA
		 * 
		 * Label que diga Vuelos de VUELTA disponibles
		 * Tabla de Vuelos VUELTA - Tabla de Detalle de Vuelos VUELTA
		 * 
		 * Botonera Volver - Reservar
		 */

		JLabel lblVueloSeleccionIDA = new JLabel(setlblVueloSeleccion(null),JLabel.LEFT);
		lblVueloSeleccionIDA.setVerticalAlignment(JLabel.TOP);
		lblVueloSeleccionIDA.setFont(new Font("Verdana", Font.PLAIN, 12));
		lblVueloSeleccionIDA.setBounds(890, 27, 300, 380);

		JLabel lblDetalleSeleccionIDA = new JLabel(setlblDetalleSeleccion(null),JLabel.LEFT);
		lblDetalleSeleccionIDA.setVerticalAlignment(JLabel.TOP);
		lblDetalleSeleccionIDA.setFont(new Font("Verdana", Font.PLAIN, 12));
		lblDetalleSeleccionIDA.setBounds(890, 145, 300, 380);		
		
		JTable tablaVuelosDisponiblesIda = this.crearTabla(this.modeloTablaVuelosDisponiblesIda);
		tablaVuelosDisponiblesIda.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (! e.getValueIsAdjusting()){   // Porque sino se dispara multiples veces
					logger.debug("debe informar al controlador para que recupere la info del detalle");
					
					if (tablaVuelosDisponiblesIda.getSelectedRow() != -1) {
						String nroVuelo = (String) tablaVuelosDisponiblesIda.getValueAt(
														tablaVuelosDisponiblesIda.getSelectedRow(),
														modeloTablaVuelosDisponiblesIda.findColumn(TABLA_VUELOS_DISPONIBLES_NRO_VUELO)
											);
						InstanciaVueloBean vuelo = getVuelo(vuelosDisponiblesIda,nroVuelo);
						lblVueloSeleccionIDA.setText(setlblVueloSeleccion(vuelo));
						lblDetalleSeleccionIDA.setText(setlblDetalleSeleccion(null));
						controlador.cambioSeleccionVueloIdaViajeIdaVuelta(vuelo);						
					} else {
						lblVueloSeleccionIDA.setText(setlblVueloSeleccion(null));
						lblDetalleSeleccionIDA.setText(setlblDetalleSeleccion(null));
						controlador.cambioSeleccionVueloIdaViajeIdaVuelta(null);						
					}
				}
			}
		});
		
		JTable tablaDetalleVuelosDisponiblesIda = this.crearTabla(this.modeloTablaDetalleVuelosDisponiblesIda);
		tablaDetalleVuelosDisponiblesIda.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (! e.getValueIsAdjusting()){   // Porque sino se dispara multiples veces					
					logger.debug("se selecciono una clase o se cambio la seleccion de vuelo y se actualizaron las clases (deseleccion)");
					
					DetalleVueloBean detalle;
					if (tablaDetalleVuelosDisponiblesIda.getSelectedRow() != -1) {
						String clase = (String) tablaDetalleVuelosDisponiblesIda.getValueAt(
													tablaDetalleVuelosDisponiblesIda.getSelectedRow(),
													modeloTablaDetalleVuelosDisponiblesIda.findColumn(TABLA_DETALLE_VUELOS_DISPONIBLES_CLASE)
										);
						detalle = getDetalle(DetallesIda,clase);
					} else {
						detalle = getDetalle(DetallesIda,null);
					}
					lblDetalleSeleccionIDA.setText(setlblDetalleSeleccion(detalle));
					controlador.cambioSeleccionDetalleVueloIdaViajeIdaVuelta(detalle);
				}
			}
		});

		this.lblVuelosDisponiblesIDAenIDAVUELTA = new JLabel("Vuelos de IDA Disponibles");
		this.lblVuelosDisponiblesIDAenIDAVUELTA.setBounds(12, 10, 530, 16);
		
		JPanel panelVuelosDisponiblesIda = new JPanel();
		panelVuelosDisponiblesIda.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		panelVuelosDisponiblesIda.setLayout(new GridLayout(1, 1, 0, 0));
		panelVuelosDisponiblesIda.add(new JScrollPane(tablaVuelosDisponiblesIda));
		panelVuelosDisponiblesIda.setBounds(12, 30, 550, 180);

		JLabel lblDetalleVuelosIDA = new JLabel("Detalle");
		lblDetalleVuelosIDA.setBounds(570, 10, 180, 16);
		
		JPanel panelDetalleIda = new JPanel();
		panelDetalleIda.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		panelDetalleIda.setLayout(new GridLayout(1, 1, 0, 0));
		panelDetalleIda.add(new JScrollPane(tablaDetalleVuelosDisponiblesIda));
		panelDetalleIda.setBounds(570, 30, 310, 180);

		JLabel lblSeleccion = new JLabel("Vuelo Seleccionado");
		lblSeleccion.setBounds(890, 10, 180, 16);
		
		panelResultadoVuelo.add(this.lblVuelosDisponiblesIDAenIDAVUELTA);
		panelResultadoVuelo.add(panelVuelosDisponiblesIda);
		panelResultadoVuelo.add(lblDetalleVuelosIDA);
		panelResultadoVuelo.add(panelDetalleIda);
		panelResultadoVuelo.add(lblSeleccion);
		panelResultadoVuelo.add(lblVueloSeleccionIDA);
		panelResultadoVuelo.add(lblDetalleSeleccionIDA);
		
		/*
		 * VUELTA
		 */
		
		this.lblVuelosDisponiblesVUELTAenIDAVUELTA = new JLabel("Vuelos de Vuelta Disponibles");
		this.lblVuelosDisponiblesVUELTAenIDAVUELTA.setBounds(12, 230, 530, 16);
		
		JLabel lblDetalleVuelosVuelta = new JLabel("Detalle");
		lblDetalleVuelosVuelta.setBounds(570, 230, 180, 16);

		JLabel lblVueloSeleccionVuelta = new JLabel(setlblVueloSeleccion(null),JLabel.LEFT);
		lblVueloSeleccionVuelta.setVerticalAlignment(JLabel.TOP);
		lblVueloSeleccionVuelta.setFont(new Font("Verdana", Font.PLAIN, 12));
		lblVueloSeleccionVuelta.setBounds(890, 247, 300, 380);

		JLabel lblDetalleSeleccionVuelta = new JLabel(setlblDetalleSeleccion(null),JLabel.LEFT);
		lblDetalleSeleccionVuelta.setVerticalAlignment(JLabel.TOP);
		lblDetalleSeleccionVuelta.setFont(new Font("Verdana", Font.PLAIN, 12));
		lblDetalleSeleccionVuelta.setBounds(890, 365, 300, 380);
		
		JTable tablaVuelosDisponiblesVuelta = this.crearTabla(this.modeloTablaVuelosDisponiblesVuelta);
		tablaVuelosDisponiblesVuelta.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (! e.getValueIsAdjusting()){   // Porque sino se dispara multiples veces
					logger.debug("debe informar al controlador para que recupere la info del detalle");
					
					if (tablaVuelosDisponiblesVuelta.getSelectedRow() != -1) {
						String nroVuelo = (String) tablaVuelosDisponiblesVuelta.getValueAt(
														tablaVuelosDisponiblesVuelta.getSelectedRow(),
														modeloTablaVuelosDisponiblesVuelta.findColumn(TABLA_VUELOS_DISPONIBLES_NRO_VUELO)
											);
						InstanciaVueloBean vuelo = getVuelo(vuelosDisponiblesVuelta,nroVuelo);
						lblVueloSeleccionVuelta.setText(setlblVueloSeleccion(vuelo));
						lblDetalleSeleccionVuelta.setText(setlblDetalleSeleccion(null));
						controlador.cambioSeleccionVueloVueltaViajeIdaVuelta(vuelo);						
					} else {
						lblVueloSeleccionVuelta.setText(setlblVueloSeleccion(null));
						lblDetalleSeleccionVuelta.setText(setlblDetalleSeleccion(null));
						controlador.cambioSeleccionVueloVueltaViajeIdaVuelta(null);						
					}
				}
			}
		});		
		
		JTable tablaDetalleVuelosDisponiblesVuelta = this.crearTabla(this.modeloTablaDetalleVuelosDisponiblesVuelta);
		tablaDetalleVuelosDisponiblesVuelta.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (! e.getValueIsAdjusting()){   // Porque sino se dispara multiples veces					
					logger.debug("se selecciono una clase o se cambio la seleccion de vuelo y se actualizaron las clases (deseleccion)");
					
					DetalleVueloBean detalle;
					if (tablaDetalleVuelosDisponiblesVuelta.getSelectedRow() != -1) {
						String clase = (String) tablaDetalleVuelosDisponiblesVuelta.getValueAt(
													tablaDetalleVuelosDisponiblesVuelta.getSelectedRow(),
													modeloTablaDetalleVuelosDisponiblesVuelta.findColumn(TABLA_DETALLE_VUELOS_DISPONIBLES_CLASE)
										);
						detalle = getDetalle(DetallesVuelta,clase);
					} else {
						detalle = getDetalle(DetallesVuelta,null);
					}
					lblDetalleSeleccionVuelta.setText(setlblDetalleSeleccion(detalle));
					controlador.cambioSeleccionDetalleVueloVueltaViajeIdaVuelta(detalle);
				}
			}
		});		
		
		JPanel panelVuelosDisponiblesVuelta = new JPanel();
		panelVuelosDisponiblesVuelta.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		panelVuelosDisponiblesVuelta.setLayout(new GridLayout(1, 1, 0, 0));
		panelVuelosDisponiblesVuelta.add(new JScrollPane(tablaVuelosDisponiblesVuelta));
		panelVuelosDisponiblesVuelta.setBounds(12, 250, 550, 180);

		JPanel panelDetalleVuelta = new JPanel();
		panelDetalleVuelta.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		panelDetalleVuelta.setLayout(new GridLayout(1, 1, 0, 0));
		panelDetalleVuelta.add(new JScrollPane(tablaDetalleVuelosDisponiblesVuelta));
		panelDetalleVuelta.setBounds(570, 250, 310, 180);
		
		JLabel lblSeleccionVuelta = new JLabel("Vuelo Seleccionado");
		lblSeleccionVuelta.setBounds(890, 230, 180, 16);
		
		/*
		 * Botonera
		 */
		JPanel panelBotonera = new JPanel();
		//panelBotonera.setBorder(new LineBorder(new Color(120, 0, 230), 1, true));
		panelBotonera.setLayout(new GridLayout(1, 3, 70, 0));
	
		JButton btnVolver = new JButton("Volver");
		btnVolver.setToolTipText("Para realizar otra búsqueda");
		btnVolver.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {				
				volverBusquedaVuelosDisponibles();
			}
		});		
		
		JButton btnLimpiar = new JButton("Limpiar Selección");		
		btnLimpiar.setToolTipText("Quita las selecciones realizadas.");
		btnLimpiar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				/*
				 * Quita la seleccion del vuelo y por lo tanto no tiene detalle
				 */
				DetallesIda = null;
				modeloTablaDetalleVuelosDisponiblesIda.setRowCount(0);
				DetallesVuelta = null;
				modeloTablaDetalleVuelosDisponiblesVuelta.setRowCount(0);
				tablaVuelosDisponiblesIda.clearSelection();
				tablaVuelosDisponiblesVuelta.clearSelection();
				controlador.limpiarSeleccion();
			}
		});
				
		JButton btnReservar = new JButton("Confirmar Selección");
		btnReservar.setToolTipText("Confirma que desea reservar los vuelos seleccionados y pasa a completar datos.");
		btnReservar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				controlador.vueloSeleccionado();
			
			}
		});
		
		panelBotonera.add(btnVolver);
		panelBotonera.add(btnLimpiar);
		panelBotonera.add(btnReservar);
		panelBotonera.setBounds(120, 440, 900, 30);		
		
		panelResultadoVuelo.add(this.lblVuelosDisponiblesVUELTAenIDAVUELTA);
		panelResultadoVuelo.add(panelVuelosDisponiblesVuelta);
		panelResultadoVuelo.add(lblDetalleVuelosVuelta);
		panelResultadoVuelo.add(panelDetalleVuelta);
		panelResultadoVuelo.add(lblVueloSeleccionVuelta);
		panelResultadoVuelo.add(lblDetalleSeleccionVuelta);
		panelResultadoVuelo.add(lblSeleccionVuelta);
		panelResultadoVuelo.add(panelBotonera);

		/*
		 * Fin panel vuelta
		 */		
		
		return panelResultadoVuelo;
	}
	
	private ArrayList<String> getColumnasTablaVuelosDisponibles() {
		ArrayList<String> columnas = new ArrayList<String>();
		columnas.add(VentanaEmpleado.TABLA_VUELOS_DISPONIBLES_NRO_VUELO);
		columnas.add(VentanaEmpleado.TABLA_VUELOS_DISPONIBLES_AEROPUERTO_SALIDA);
		columnas.add(VentanaEmpleado.TABLA_VUELOS_DISPONIBLES_HORA_SALIDA);
		columnas.add(VentanaEmpleado.TABLA_VUELOS_DISPONIBLES_AEROPUERTO_LLEGADA);
		columnas.add(VentanaEmpleado.TABLA_VUELOS_DISPONIBLES_HORA_LLEGADA);
		columnas.add(VentanaEmpleado.TABLA_VUELOS_DISPONIBLES_MODELO_AVION);
		columnas.add(VentanaEmpleado.TABLA_VUELOS_DISPONIBLES_TIEMPO_ESTIMADO);	
		return columnas;
	}
	
	private ArrayList<String> getColumnasTablaDetalleVuelosDisponibles() {
		ArrayList<String> columnas = new ArrayList<String>();
		columnas.add(VentanaEmpleado.TABLA_DETALLE_VUELOS_DISPONIBLES_CLASE);
		columnas.add(VentanaEmpleado.TABLA_DETALLE_VUELOS_DISPONIBLES_PRECIO);
		columnas.add(VentanaEmpleado.TABLA_DETALLE_VUELOS_DISPONIBLES_ASIENTOS_DISPONIBLES);
		return columnas;
	}
	
	private DefaultTableModel inicializarModelo(ArrayList<String> column) {
		DefaultTableModel modelo = new DefaultTableModel(){
			private static final long serialVersionUID = 1L;

			@Override
		    public boolean isCellEditable(int row, int column) {
		       //all cells false
		       return false;
		    }
		};
		// Inicializa las columnas
		for (String col : column ) {
			modelo.addColumn(col);
		}
		return modelo;
	}
	
	private JTable crearTabla(DefaultTableModel model) {
		JTable tabla = new JTable(model) {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public Component prepareRenderer(TableCellRenderer renderer, int row, int column)
			{
				Component c = super.prepareRenderer(renderer, row, column);
				Color foreground, background;
				Color color = new Color(255, 232, 219);
				Color seleccion = new Color(219, 219, 219);

				if (isRowSelected(row)) {
					foreground = Color.black;
					background = seleccion;
				} else {
					if (row % 2 != 0) {
		               foreground = Color.black;
		               background = Color.white;
					} else {
		               foreground = Color.black;
		               background = color;
					}
				}
		        c.setForeground(foreground);
		        c.setBackground(background);
		            
		        return c;
			}
		};	
		
		tabla.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		tabla.setAutoCreateRowSorter(true);		
		tabla.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);  //Solo puede seleccionar un item
		return tabla;
	}
	
	private Component crearPanelVacio() {
		JPanel panelPpal = new JPanel();
		panelPpal.setLayout(new BorderLayout(0, 0));
		panelPpal.setVisible(false);		
		
		return panelPpal;			
	}

	private void poblarComboTipoDocumento(ArrayList<String> tiposDocumento) {
		logger.info("Carga el combo con los {} tipos de documento recuperados", tiposDocumento.size());
		
		this.cmbTipoDNISoloIda.removeAllItems();
		this.cmbTipoDNIIdaVuelta.removeAllItems();
		for (String item: tiposDocumento) {
			this.cmbTipoDNISoloIda.addItem(item);
			this.cmbTipoDNIIdaVuelta.addItem(item);
		}
	}
	
	/**
	 * Crea el panel para la selección del pasajero a realizar la reserva
	 * 
	 * @return Panel
	 */
	private Component crearPanelReservaSoloIda() {
		/*
		 * Panel principal
		 */
		JPanel panelReserva = new JPanel();
		panelReserva.setLayout(null);
		
		/*
		 * Información de los vuelos seleccionados
		 */
		this.lblSeleccionSoloIda = new JLabel("");
		this.lblSeleccionSoloIda.setBounds(70, 30, 450, 16);

		this.lblVueloSeleccionSoloIda = new JLabel(setlblVueloSeleccion(null),JLabel.LEFT);
		this.lblVueloSeleccionSoloIda.setVerticalAlignment(JLabel.TOP);
		this.lblVueloSeleccionSoloIda.setFont(new Font("Verdana", Font.PLAIN, 12));
		this.lblVueloSeleccionSoloIda.setBounds(70, 57, 300, 380);

		this.lblDetalleSeleccionSoloIda = new JLabel(setlblDetalleSeleccion(null),JLabel.LEFT);
		this.lblDetalleSeleccionSoloIda.setVerticalAlignment(JLabel.TOP);
		this.lblDetalleSeleccionSoloIda.setFont(new Font("Verdana", Font.PLAIN, 12));
		this.lblDetalleSeleccionSoloIda.setBounds(70, 175, 300, 380);		

		panelReserva.add(lblSeleccionSoloIda);
		panelReserva.add(lblVueloSeleccionSoloIda);
		panelReserva.add(lblDetalleSeleccionSoloIda);
		
		/*
		 * Selección de la persona que desea realizar la reserva
		 */
		this.cmbTipoDNISoloIda = new JComboBox<String>();			
		JPanel panelTipoDNI = new JPanel();
		JLabel lblTipoDni = new JLabel("Tipo Documento:");
		panelTipoDNI.add(lblTipoDni);
		panelTipoDNI.add(this.cmbTipoDNISoloIda);
		panelTipoDNI.setBounds(100, 280, 200, 30);
		
		this.txtDNIPasajeroSeleccionSoloIda = new JTextField();
		this.txtDNIPasajeroSeleccionSoloIda.setColumns(10);		

		JPanel panelNumeroDNI = new JPanel();
		JLabel lblNumeroDNI = new JLabel("DNI:");
		panelNumeroDNI.add(lblNumeroDNI);
		panelNumeroDNI.add(this.txtDNIPasajeroSeleccionSoloIda);
		panelNumeroDNI.setBounds(130, 320, 160, 30);
		
		JButton btnBuscarPersona = new JButton("Buscar Persona");
		btnBuscarPersona.setBounds(330, 300, 160, 30);
		btnBuscarPersona.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				controlador.buscarPasajero((String) cmbTipoDNISoloIda.getSelectedItem(),
													txtDNIPasajeroSeleccionSoloIda.getText());
			}
		});		
		
		this.lblPasajeroSeleccionSoloIda = new JLabel("");
		this.lblPasajeroSeleccionSoloIda.setFont(new Font("Verdana", Font.PLAIN, 12));
		this.lblPasajeroSeleccionSoloIda.setBounds(550, 200, 350, 260);
		
		panelReserva.add(panelTipoDNI);
		panelReserva.add(panelNumeroDNI);
		panelReserva.add(btnBuscarPersona);
		panelReserva.add(lblPasajeroSeleccionSoloIda);		
		
		/*
		 * Botonera
		 */
		JPanel panelBotonera = new JPanel();
		panelBotonera.setLayout(new GridLayout(1, 2, 70, 0));
	
		JButton btnVolver = new JButton("Volver");
		btnVolver.setToolTipText("Cambiar selección");
		btnVolver.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				controlador.volverASeleccionVuelo();
				volverSeleccionVuelosSoloIda();
			}
		});		
				
		JButton btnReservar = new JButton("Reservar");
		btnReservar.setToolTipText("Reserva el vuelo seleccionado");
		btnReservar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				controlador.reservar();
			}
		});
		
		panelBotonera.add(btnVolver);
		panelBotonera.add(btnReservar);
		panelBotonera.setBounds(120, 440, 900, 30);		
		
		panelReserva.add(panelBotonera);

		return panelReserva;
	}
	
	private Component crearPanelReservaIdaVuelta() {
		/*
		 * Panel principal
		 */
		JPanel panelReserva = new JPanel();
		panelReserva.setLayout(null);
		
		/*
		 * Información de los vuelos seleccionados
		 */
		this.lblSeleccionIda = new JLabel("");
		this.lblSeleccionIda.setBounds(90, 15, 450, 16);

		this.lblVueloSeleccionIda = new JLabel(setlblVueloSeleccion(null),JLabel.LEFT);
		this.lblVueloSeleccionIda.setVerticalAlignment(JLabel.TOP);
		this.lblVueloSeleccionIda.setFont(new Font("Verdana", Font.PLAIN, 12));
		this.lblVueloSeleccionIda.setBounds(90, 47, 300, 380);

		this.lblDetalleSeleccionIda = new JLabel(setlblDetalleSeleccion(null),JLabel.LEFT);
		this.lblDetalleSeleccionIda.setVerticalAlignment(JLabel.TOP);
		this.lblDetalleSeleccionIda.setFont(new Font("Verdana", Font.PLAIN, 12));
		this.lblDetalleSeleccionIda.setBounds(90, 170, 300, 380);		

		this.lblSeleccionVuelta = new JLabel("");
		this.lblSeleccionVuelta.setBounds(640, 15, 450, 16);

		this.lblVueloSeleccionVuelta = new JLabel(setlblVueloSeleccion(null),JLabel.LEFT);
		this.lblVueloSeleccionVuelta.setVerticalAlignment(JLabel.TOP);
		this.lblVueloSeleccionVuelta.setFont(new Font("Verdana", Font.PLAIN, 12));
		this.lblVueloSeleccionVuelta.setBounds(640, 47, 300, 380);

		this.lblDetalleSeleccionVuelta = new JLabel(setlblDetalleSeleccion(null),JLabel.LEFT);
		this.lblDetalleSeleccionVuelta.setVerticalAlignment(JLabel.TOP);
		this.lblDetalleSeleccionVuelta.setFont(new Font("Verdana", Font.PLAIN, 12));
		this.lblDetalleSeleccionVuelta.setBounds(640, 170, 300, 380);

		panelReserva.add(lblSeleccionIda);
		panelReserva.add(lblVueloSeleccionIda);
		panelReserva.add(lblDetalleSeleccionIda);

		panelReserva.add(lblSeleccionVuelta);
		panelReserva.add(lblVueloSeleccionVuelta);
		panelReserva.add(lblDetalleSeleccionVuelta);
				
		/*
		 * Selección de la persona que desea realizar la reserva
		 */
		this.cmbTipoDNIIdaVuelta = new JComboBox<String>();			
		JPanel panelTipoDNI = new JPanel();
		JLabel lblTipoDni = new JLabel("Tipo Documento:");
		panelTipoDNI.add(lblTipoDni);
		panelTipoDNI.add(this.cmbTipoDNIIdaVuelta);
		panelTipoDNI.setBounds(100, 280, 200, 30);
		
		this.txtDNIPasajeroSeleccionIdaVuelta = new JTextField();
		this.txtDNIPasajeroSeleccionIdaVuelta.setColumns(10);		

		JPanel panelNumeroDNI = new JPanel();
		JLabel lblNumeroDNI = new JLabel("DNI:");
		panelNumeroDNI.add(lblNumeroDNI);
		panelNumeroDNI.add(this.txtDNIPasajeroSeleccionIdaVuelta);
		panelNumeroDNI.setBounds(130, 320, 160, 30);
		
		JButton btnBuscarPersona = new JButton("Buscar Persona");
		btnBuscarPersona.setBounds(330, 300, 160, 30);
		btnBuscarPersona.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				controlador.buscarPasajero((String) cmbTipoDNIIdaVuelta.getSelectedItem(),
													txtDNIPasajeroSeleccionIdaVuelta.getText());
			}
		});		
		
		this.lblPasajeroSeleccionIdaVuelta = new JLabel("");
		this.lblPasajeroSeleccionIdaVuelta.setFont(new Font("Verdana", Font.PLAIN, 12));
		this.lblPasajeroSeleccionIdaVuelta.setBounds(640, 200, 350, 260);
		
		panelReserva.add(panelTipoDNI);
		panelReserva.add(panelNumeroDNI);
		panelReserva.add(btnBuscarPersona);
		panelReserva.add(lblPasajeroSeleccionIdaVuelta);			
		/*
		 * Botonera
		 */
		JPanel panelBotonera = new JPanel();
		panelBotonera.setLayout(new GridLayout(1, 2, 70, 0));
	
		JButton btnVolver = new JButton("Volver");
		btnVolver.setToolTipText("Cambiar selección");
		btnVolver.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {				
				volverSeleccionVuelosIdaVuelta();
			}
		});		
				
		JButton btnReservar = new JButton("Reservar");
		btnReservar.setToolTipText("Reserva el vuelo seleccionado");
		btnReservar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				controlador.reservar();
			}
		});
		
		panelBotonera.add(btnVolver);
		panelBotonera.add(btnReservar);
		panelBotonera.setBounds(120, 440, 900, 30);		
		
		panelReserva.add(panelBotonera);

		return panelReserva;
	}

	/**
	 * Crea el panel para mostrar el resultado final de la reserva
	 * 
	 * @return Panel
	 */
	private Component crearPanelResultadoReservaSoloIda() {
		/*
		 * Panel principal
		 */
		JPanel panelReserva = new JPanel();
		panelReserva.setLayout(null);
	
		/*
		 * Información de los vuelos seleccionados
		 */
		this.lblResultadoSeleccionSoloIda = new JLabel("");
		this.lblResultadoSeleccionSoloIda.setBounds(70, 30, 450, 16);

		this.lblResultadoVueloSeleccionSoloIda = new JLabel(setlblVueloSeleccion(null),JLabel.LEFT);
		this.lblResultadoVueloSeleccionSoloIda.setVerticalAlignment(JLabel.TOP);
		this.lblResultadoVueloSeleccionSoloIda.setFont(new Font("Verdana", Font.PLAIN, 12));
		this.lblResultadoVueloSeleccionSoloIda.setBounds(70, 57, 300, 380);

		this.lblResultadoDetalleSeleccionSoloIda = new JLabel(setlblDetalleSeleccion(null),JLabel.LEFT);
		this.lblResultadoDetalleSeleccionSoloIda.setVerticalAlignment(JLabel.TOP);
		this.lblResultadoDetalleSeleccionSoloIda.setFont(new Font("Verdana", Font.PLAIN, 12));
		this.lblResultadoDetalleSeleccionSoloIda.setBounds(70, 175, 300, 380);		

		panelReserva.add(lblResultadoSeleccionSoloIda);
		panelReserva.add(lblResultadoVueloSeleccionSoloIda);
		panelReserva.add(lblResultadoDetalleSeleccionSoloIda);
		
		/*
		 * Selección de la persona que desea realizar la reserva
		 */
		
		this.lblResultadoPasajeroSeleccionSoloIda = new JLabel("");
		this.lblResultadoPasajeroSeleccionSoloIda.setFont(new Font("Verdana", Font.PLAIN, 12));
		this.lblResultadoPasajeroSeleccionSoloIda.setBounds(70, 200, 350, 260);
		
		panelReserva.add(lblResultadoPasajeroSeleccionSoloIda);	
		
		/*
		 * Datos de la reserva
		 */
		this.lblResultadoReservaSoloIda = new JLabel("");
		this.lblResultadoReservaSoloIda.setFont(new Font("Verdana", Font.PLAIN, 12));
		this.lblResultadoReservaSoloIda.setBounds(550, 200, 350, 260);
		
		panelReserva.add(lblResultadoReservaSoloIda);
		
		return panelReserva;
	}
	
	private Component crearPanelResultadoReservaIdaVuelta() {
		/*
		 * Panel principal
		 */
		JPanel panelReserva = new JPanel();
		panelReserva.setLayout(null);
	
		/*
		 * Información de los vuelos seleccionados
		 */
		
		// Vuelo de Ida
		
		this.lblResultadoSeleccionIda = new JLabel("");
		this.lblResultadoSeleccionIda.setBounds(70, 30, 450, 16);

		this.lblResultadoVueloSeleccionIda = new JLabel(setlblVueloSeleccion(null),JLabel.LEFT);
		this.lblResultadoVueloSeleccionIda.setVerticalAlignment(JLabel.TOP);
		this.lblResultadoVueloSeleccionIda.setFont(new Font("Verdana", Font.PLAIN, 12));
		this.lblResultadoVueloSeleccionIda.setBounds(70, 57, 300, 380);

		this.lblResultadoDetalleSeleccionIda = new JLabel(setlblDetalleSeleccion(null),JLabel.LEFT);
		this.lblResultadoDetalleSeleccionIda.setVerticalAlignment(JLabel.TOP);
		this.lblResultadoDetalleSeleccionIda.setFont(new Font("Verdana", Font.PLAIN, 12));
		this.lblResultadoDetalleSeleccionIda.setBounds(70, 175, 300, 380);		

		panelReserva.add(lblResultadoSeleccionIda);
		panelReserva.add(lblResultadoVueloSeleccionIda);
		panelReserva.add(lblResultadoDetalleSeleccionIda);
		
		// Vuelo de Vuelta
		
		this.lblResultadoSeleccionVuelta = new JLabel("");
		this.lblResultadoSeleccionVuelta.setBounds(570, 30, 450, 16);

		this.lblResultadoVueloSeleccionVuelta = new JLabel(setlblVueloSeleccion(null),JLabel.LEFT);
		this.lblResultadoVueloSeleccionVuelta.setVerticalAlignment(JLabel.TOP);
		this.lblResultadoVueloSeleccionVuelta.setFont(new Font("Verdana", Font.PLAIN, 12));
		this.lblResultadoVueloSeleccionVuelta.setBounds(570, 57, 300, 380);

		this.lblResultadoDetalleSeleccionVuelta = new JLabel(setlblDetalleSeleccion(null),JLabel.LEFT);
		this.lblResultadoDetalleSeleccionVuelta.setVerticalAlignment(JLabel.TOP);
		this.lblResultadoDetalleSeleccionVuelta.setFont(new Font("Verdana", Font.PLAIN, 12));
		this.lblResultadoDetalleSeleccionVuelta.setBounds(570, 175, 300, 380);		

		panelReserva.add(lblResultadoSeleccionVuelta);
		panelReserva.add(lblResultadoVueloSeleccionVuelta);
		panelReserva.add(lblResultadoDetalleSeleccionVuelta);
				
		/*
		 * Selección de la persona que desea realizar la reserva
		 */
		
		this.lblResultadoPasajeroSeleccionIdaVuelta = new JLabel("");
		this.lblResultadoPasajeroSeleccionIdaVuelta.setFont(new Font("Verdana", Font.PLAIN, 12));
		this.lblResultadoPasajeroSeleccionIdaVuelta.setBounds(70, 200, 350, 260);
		
		panelReserva.add(lblResultadoPasajeroSeleccionIdaVuelta);	
		
		/*
		 * Datos de la reserva
		 */
		this.lblResultadoReservaIdaVuelta = new JLabel("");
		this.lblResultadoReservaIdaVuelta.setFont(new Font("Verdana", Font.PLAIN, 12));
		this.lblResultadoReservaIdaVuelta.setBounds(570, 200, 350, 260);
		
		panelReserva.add(lblResultadoReservaIdaVuelta);
		
		return panelReserva;
	}

}
