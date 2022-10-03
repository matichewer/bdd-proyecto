package vuelos.controlador;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import vuelos.modelo.ModeloLogin;
import vuelos.modelo.ModeloLoginImpl;
import vuelos.modelo.empleado.ModeloEmpleado;
import vuelos.modelo.empleado.beans.DetalleVueloBean;
import vuelos.modelo.empleado.beans.InstanciaVueloBean;
import vuelos.modelo.empleado.beans.PasajeroBean;
import vuelos.modelo.empleado.beans.PasajeroBeanImpl;
import vuelos.modelo.empleado.beans.ReservaBean;
import vuelos.modelo.empleado.beans.UbicacionesBean;
import vuelos.vista.empleado.VentanaEmpleado;
import vuelos.vista.login.VentanaLogin;
import vuelos.vista.login.VentanaLoginImpl;

public class ControladorEmpleadoImpl implements ControladorEmpleado {

	private static Logger logger = LoggerFactory.getLogger(ControladorEmpleadoImpl.class);
	
	public ControladorEmpleadoImpl(VentanaEmpleado ventana, ModeloEmpleado modelo) {
		logger.debug("Se crea el controlador del Empleado.");		
		this.ventana = ventana;
		this.modelo = modelo;
		this.ventana.registrarControlador(this);		
	}
	
	@Override
	public void ejecutar() {
		try {
			this.ventana.mostrarEmpleadoLogueado(this.modelo.obtenerEmpleadoLogueado());
			logger.debug("muestra la ventana");
			this.ventana.mostrarVentana();

			// Muestra directamente la pantalla de vuelos disponibles
			this.mostrarVuelosDisponibles();
			
		} catch (Exception e) {
			String s = "Se produjo un error al intentar crear la ventana";
			logger.error(e.getMessage());
			this.ventana.informar(e.getMessage());
		}
	}

	@Override
	public void salirAplicacion() {
		logger.info("Saliendo de la aplicación.");
		this.modelo.desconectar();
		this.ventana.eliminarVentana();
		System.exit(0);
	}

	@Override
	public void cerrarSesion() {
		logger.info("Cerrando la sesión.");
		this.modelo.desconectar();
		this.ventana.eliminarVentana();

		logger.info("Creando la ventana de login.");
		ModeloLogin modeloLogin = new ModeloLoginImpl();  
		VentanaLogin ventanaLogin = new VentanaLoginImpl();
		@SuppressWarnings("unused")
		ControladorLogin controlador = new ControladorLoginImpl(ventanaLogin, modeloLogin);
	}

	private VentanaEmpleado ventana;
	private ModeloEmpleado modelo;
	
	@Override
	public void mostrarVuelosDisponibles() {
		logger.info("Recuperar del modelo los destinos posibles");
		try {
			ArrayList<UbicacionesBean> ubicaciones = this.modelo.recuperarUbicaciones();

			logger.info("informar a la ventana los destinos posibles");
			this.ventana.poblarUbicaciones(ubicaciones);
			
			logger.info("Muestra el panel de consulta/reserva de vuelos.");
			this.ventana.mostrarPanel(VentanaEmpleado.VUELOS_DISPONIBLES);

		} catch (Exception e) {
			logger.warn("Se produjo una excepción {}", e.getMessage());
			this.ventana.informar("Hubo un problema para recuperar los destinos.");
		}
	}

	@SuppressWarnings("deprecation")
	@Override
	public void buscarVuelosDisponibles(boolean esIdaVuelta, 
										Date fechaDesde, 
										Date fechaHasta, 
										UbicacionesBean origen,
										UbicacionesBean destino) {
		
		logger.info("Solicita información de vuelos disponibles: esIdaVuelta:{} fechaDesde:{} fechaHasta:{} Origen:{} Destino:{}.",
					esIdaVuelta, 
					(fechaDesde==null)?fechaDesde:fechaDesde.getDate(), 
					(fechaHasta==null)?fechaHasta:fechaHasta.getDate(),
					origen.toString(),destino.toString());
		/*
		 * Control de los parametros
		 * - Origen y Destino no pueden ser null y deben ser distintos.
		 * - Si es ida: fechaHasta debe ser null
		 * - Si es idaVuelta: fechaDesde debe ser menor o igual a fechaHasta
		 */
		if (Objects.isNull(origen)) { 
			logger.warn("La ubicación de origen está vacía.");
			this.ventana.informar("Debe seleccionar una ubicación de origen.");
			return;
		}
		if (Objects.isNull(destino)) { 
			logger.warn("La ubicación destino está vacía.");
			this.ventana.informar("Debe seleccionar una ubicación de destino.");
			return;
		}
		if (origen.equals(destino)) { //UbicacionesBeanImpl hace un override de equals
			logger.warn("La ubicación origen es igual a la ubicación destino.");
			this.ventana.informar("La ubicación de origen y destino deben ser diferentes.");
			return;
		}
		if (Objects.isNull(fechaDesde)) { 
			logger.warn("La fecha desde no debe estar vacía.");
			this.ventana.informar("Debe seleccionar una fecha de ida (desde).");
			return;
		}
		
		this.limpiarSeleccion();
		
		if (esIdaVuelta) {
			if (Objects.isNull(fechaHasta)) { 
				logger.warn("La fecha hasta no debe estar vacía.");
				this.ventana.informar("Debe seleccionar una fecha de regreso (hasta).");
				return;
			}
			if (fechaDesde.after(fechaHasta)) { 
				logger.warn("La fecha de desde en mayor a la fecha hasta.");
				this.ventana.informar("Debe fecha de ida no debe ser posterior a la fecha de regreso.");
				return;
			}
			this.setEsVueloIdaVuelta(true);
			this.buscarVuelosDisponiblesParaIdaVuelta(fechaDesde, fechaHasta, origen, destino);
		} else { // es solo ida
			if (!Objects.isNull(fechaHasta)) { 
				logger.warn("La fecha hasta debe estar vacía.");
				this.ventana.informar("No debe seleccionar una fecha de regreso si es solo ida.");
				return;
			}
			this.setEsVueloIdaVuelta(false);
			this.buscarVuelosDisponiblesParaIda(fechaDesde, origen, destino);
		}
		return;
	}	
	
    /**
	 * Metodo que se encarga de buscar los vuelos disponibles (solo ida) sabiendo que los parametros están chequeados.
	 * 
	 * @param fechaVuelo
	 * @param origen
	 * @param destino
	 */
	private void buscarVuelosDisponiblesParaIda(Date fechaVuelo, UbicacionesBean origen, UbicacionesBean destino) {
		logger.info("Solicita información de vuelos disponibles de ida");
		try {
			ArrayList<InstanciaVueloBean> listaVuelos = this.modelo.obtenerVuelosDisponibles(fechaVuelo, origen, destino);
			logger.debug("La cantidad de vuelos recuperados es {} ", listaVuelos.size());
			this.ventana.mostrarVuelosDisponiblesSoloIda(listaVuelos);
		} catch (Exception e) {
			logger.warn("Se produjo una excepción {}", e.getMessage());
			this.ventana.informar("Hubo un problema para recuperar los vuelos disponibles (solo IDA).");
		}
	} 
	
	/**
	 * Metodo que se encarga de buscar los vuelos disponibles (ida y vuelta) sabiendo que los parametros están chequeados.
	 * 
	 * @param fechaDesde
	 * @param fechaHasta
	 * @param origen
	 * @param destino
	 */
	private void buscarVuelosDisponiblesParaIdaVuelta(Date fechaDesde, Date fechaHasta, UbicacionesBean origen, UbicacionesBean destino) {
		logger.info("Solicita información de vuelos disponibles de ida y vuelta");
		try {
			ArrayList<InstanciaVueloBean> listaVuelosIda = this.modelo.obtenerVuelosDisponibles(fechaDesde, origen, destino);			
			ArrayList<InstanciaVueloBean> listaVuelosVuelta = this.modelo.obtenerVuelosDisponibles(fechaHasta, destino, origen);
			logger.debug("La cantidad de vuelos ida es {} y los vuelos de vuelta es {}", listaVuelosIda.size(), listaVuelosVuelta.size());
			this.ventana.mostrarVuelosDisponiblesVuelta(listaVuelosIda, listaVuelosVuelta);
		} catch (Exception e) {
			logger.warn("Se produjo una excepción {}", e.getMessage());
			this.ventana.informar("Hubo un problema para recuperar los vuelos disponibles (Ida y Vuelta).");
		}
	}

	@Override
	public void cambioSeleccionVueloViajeSoloIda(InstanciaVueloBean vueloSeleccion) {
				
		this.setVueloIdaSeleccionado(vueloSeleccion);
		this.setDetalleVueloIdaSeleccionado(null);
		
		if (Objects.isNull(vueloSeleccion)) {
			logger.info("Se deseleccionó el vuelo");
			return;
		}
		logger.info("Solicita detalle del vuelo {} con fecha {} para un viaje de ida.",vueloSeleccion.getNroVuelo(),vueloSeleccion.getFechaVuelo());
		try {
			ArrayList<DetalleVueloBean> detalle = this.modelo.obtenerDetalleVuelo(vueloSeleccion);
			logger.debug("Se recuperaron {} clases para el vuelo {}", detalle.size(), vueloSeleccion.getNroVuelo());
			this.ventana.mostrarDetalleVuelosDisponiblesSoloIda(detalle);
			
		} catch (Exception e) {
			logger.warn("Se produjo una excepción {}", e.getMessage());
			this.ventana.informar("Hubo un problema para recuperar las clases y asientos del vuelo " + vueloSeleccion.getNroVuelo());
		}
	}

	@Override
	public void cambioSeleccionVueloIdaViajeIdaVuelta(InstanciaVueloBean vueloSeleccion) {
		
		this.setVueloIdaSeleccionado(vueloSeleccion);
		this.setDetalleVueloIdaSeleccionado(null);
		
		if (Objects.isNull(vueloSeleccion)) {
			logger.info("Se deseleccionó el vuelo");
			return;
		}
		logger.info("Solicita detalle del vuelo {} con fecha {} para ida y vuelta sobre el vuelo de ida.",vueloSeleccion.getNroVuelo(),vueloSeleccion.getFechaVuelo());
		try {
			ArrayList<DetalleVueloBean> detalle = this.modelo.obtenerDetalleVuelo(vueloSeleccion);
			logger.debug("Se recuperaron {} clases para el vuelo {}", detalle.size(), vueloSeleccion.getNroVuelo());
			this.ventana.mostrarDetalleVuelosDisponiblesIdaViajeIdaVuelta(detalle);
			
		} catch (Exception e) {
			logger.warn("Se produjo una excepción {}", e.getMessage());
			this.ventana.informar("Hubo un problema para recuperar las clases y asientos del vuelo " + vueloSeleccion.getNroVuelo());
		}
		
	}

	@Override
	public void cambioSeleccionVueloVueltaViajeIdaVuelta(InstanciaVueloBean vueloSeleccion) {
		
		this.setVueloVueltaSeleccionado(vueloSeleccion);
		this.setDetalleVueloVueltaSeleccionado(null);
		
		if (Objects.isNull(vueloSeleccion)) {
			logger.info("Se deseleccionó el vuelo");
			return;
		}
		logger.info("Solicita detalle del vuelo {} con fecha {} para ida y vuelta sobre el vuelo de vuelta.",vueloSeleccion.getNroVuelo(),vueloSeleccion.getFechaVuelo());
		try {
			ArrayList<DetalleVueloBean> detalle = this.modelo.obtenerDetalleVuelo(vueloSeleccion);
			logger.debug("Se recuperaron {} clases para el vuelo {}", detalle.size(), vueloSeleccion.getNroVuelo());
			this.ventana.mostrarDetalleVuelosDisponiblesVueltaViajeIdaVuelta(detalle);
			
		} catch (Exception e) {
			logger.warn("Se produjo una excepción {}", e.getMessage());
			this.ventana.informar("Hubo un problema para recuperar las clases y asientos del vuelo " + vueloSeleccion.getNroVuelo());
		}
	} 

	@Override
	public void cambioSeleccionDetalleVueloViajeSoloIda(DetalleVueloBean detalle) {
		logger.info("Se actualiza el detalle seleccionada para un vuelo de Solo Ida.");
		this.setDetalleVueloIdaSeleccionado(detalle);
	}

	@Override
	public void cambioSeleccionDetalleVueloIdaViajeIdaVuelta(DetalleVueloBean detalle) {
		logger.info("Se actualiza el detalle del vuelo de IDA seleccionado para un vuelo de Ida y Vuelta.");
		this.setDetalleVueloIdaSeleccionado(detalle);
	}

	@Override
	public void cambioSeleccionDetalleVueloVueltaViajeIdaVuelta(DetalleVueloBean detalle) {
		logger.info("Se actualiza el detalle del vuelo de VUELTA seleccionado para un vuelo de Ida y Vuelta.");
		this.setDetalleVueloVueltaSeleccionado(detalle);
	}
	
	@Override
	public void limpiarSeleccion() {
		logger.info("Se solicita quitar todas las selecciones.");
		this.setVueloIdaSeleccionado(null);
		this.setVueloVueltaSeleccionado(null);
		this.setDetalleVueloIdaSeleccionado(null);
		this.setDetalleVueloVueltaSeleccionado(null);
		this.setPasajeroSeleccionado(null);
	}
	
	@Override
	public void vueloSeleccionado() {
		logger.info("Valida que haya seleccionado todo e informa a la ventana que muestre la pantalla de reserva.");
		/*
		 * Se debe validar que todos los valores han sido seleccionados, es decir,
		 * que si es un vuelo de ida, haya un vuelo seleccionado y una clase
		 * si es un vuelo de ida y vuelta, haya dos vuelos seleccionados y dos clases. 
		 * 
		 * Debe solicitar al modelo una lista de tipos de documento, e informar a la ventana que
		 * muestre la pantalla de reserva para que el usuario ingrese la persona que realiza la reserva.
		 */
		if (esVueloIdaVuelta()) {
			if (getVueloIdaSeleccionado() == null) {
				this.ventana.informar("Debe seleccionar un vuelo de IDA para realizar una reserva.");
				return;
			}
			if (getDetalleVueloIdaSeleccionado() == null) {
				this.ventana.informar("Debe seleccionar una clase del vuelo de IDA para realizar una reserva.");
				return;
			}
			if (getVueloVueltaSeleccionado() == null) {
				this.ventana.informar("Debe seleccionar un vuelo de VUELTA para realizar una reserva.");
				return;
			}
			if (getDetalleVueloVueltaSeleccionado() == null) {
				this.ventana.informar("Debe seleccionar una clase del vuelo de VUELTA para realizar una reserva.");
				return;
			}
			this.ventana.mostrarSolicitudReservaIdaVuelta(	this.modelo.obtenerTiposDocumento(), 
															getVueloIdaSeleccionado(), 
															getDetalleVueloIdaSeleccionado(), 
															getVueloVueltaSeleccionado(), 
															getDetalleVueloVueltaSeleccionado()
														);			
		} else {  // vuelo solo de ida
			if (getVueloIdaSeleccionado() == null) {
				this.ventana.informar("Debe seleccionar un vuelo para realizar una reserva.");
				return;
			}
			if (getDetalleVueloIdaSeleccionado() == null) {
				this.ventana.informar("Debe seleccionar una clase para realizar una reserva.");
				return;
			}
			this.ventana.mostrarSolicitudReservaSoloIda(this.modelo.obtenerTiposDocumento(), 
														getVueloIdaSeleccionado(), 
														getDetalleVueloIdaSeleccionado()
														);			
		}
	}	
	
	@Override
	public void buscarPasajero(String tipoDoc, String numero) {
		logger.info("Busca al pasajero {} {}", tipoDoc, numero);
		if (tipoDoc == null || tipoDoc == "") {
			this.ventana.informar("Debe seleccionar un tipo de documento.");
			return;
		}
		int nroDoc = 0;
		try {
			nroDoc = Integer.valueOf(numero);			
		}
		catch (NumberFormatException e) {
			this.ventana.informar("El número de documento no tiene un formato válido.");
			return;
		}
		try {
			PasajeroBean pasajero = this.modelo.obtenerPasajero(tipoDoc, nroDoc);
			// informar a la ventana que muestre el pasajero
			this.ventana.mostrarPasajero(this.esVueloIdaVuelta(),pasajero);
			// registrar en el controlador el pasajero seleccionado
			this.setPasajeroSeleccionado(pasajero);
		} catch (Exception e) {
			logger.info("No se encontró un pasajero con documento {} {}", tipoDoc, nroDoc);
			this.ventana.informar("No se encontró un pasajero con ese número y tipo de documento.");
			return;
		}
	}
	

	@Override
	public void volverASeleccionVuelo() {
		this.pasajeroSeleccionado = null;		
	}

	@Override
	public void reservar() {
		logger.info("Se solicita comenzar el proceso de reserva.");
		if (this.getPasajeroSeleccionado() == null) {
			this.ventana.informar("Debe seleccionar un pasajero");
			return;
		}
		if (this.esVueloIdaVuelta()) {
			try {
				ReservaBean reserva = this.modelo.reservarIdaVuelta(this.getPasajeroSeleccionado(),
											  				   this.getVueloIdaSeleccionado(),
											  				   this.getDetalleVueloIdaSeleccionado(),
											  				   this.getVueloVueltaSeleccionado(),
											  				   this.getDetalleVueloVueltaSeleccionado());
				logger.info("Realizó la reserva y solicita que la ventana muestre la info");
				this.ventana.mostrarResultadoReservaIdaVuelta(reserva);
			} catch (Exception e) {
				logger.error("Hubo un error al realizar la reserva de ida y vuelta: {}", e.getMessage());
				this.ventana.informar("No se pudo realizar la reserva de ida y vuelta.");
			}
		} else {
			try {
				ReservaBean reserva = this.modelo.reservarSoloIda(this.getPasajeroSeleccionado(),
															 this.getVueloIdaSeleccionado(),
															 this.getDetalleVueloIdaSeleccionado());
				logger.info("Realizó la reserva y solicita que la ventana muestre la info");
				this.ventana.mostrarResultadoReservaSoloIda(reserva);				 
			} catch (Exception e) {
				// Ejemplo de como mostrar el stackTrace en el logger
				Writer buffer = new StringWriter();
				PrintWriter pw = new PrintWriter(buffer);
				e.printStackTrace(pw);
				
				logger.error("Hubo un error al realizar la reserva de solo ida: {}", buffer.toString());
				
				this.ventana.informar(e.getLocalizedMessage());
			}
		}
		
	}
	
	@Override
	public void errorEnReserva(String msj) {
		this.ventana.informar(msj);
	}
	
	/*
	 * propiedades y metodos para registrar el vuelo seleccionado
	 * 
	 */
	private boolean esVueloIdaVuelta = false;
	private InstanciaVueloBean vueloIdaSeleccionado;
	private InstanciaVueloBean vueloVueltaSeleccionado;
	private DetalleVueloBean detalleVueloIdaSeleccionado;
	private DetalleVueloBean detalleVueloVueltaSeleccionado;
	private PasajeroBean pasajeroSeleccionado;
	
	protected boolean esVueloIdaVuelta() {
		return esVueloIdaVuelta;
	}

	protected void setEsVueloIdaVuelta(boolean esVueloIdaVuelta) {
		this.esVueloIdaVuelta = esVueloIdaVuelta;
	}

	protected InstanciaVueloBean getVueloIdaSeleccionado() {
		return vueloIdaSeleccionado;
	}

	protected void setVueloIdaSeleccionado(InstanciaVueloBean vueloIdaSeleccionado) {
		this.vueloIdaSeleccionado = vueloIdaSeleccionado;
	}

	protected InstanciaVueloBean getVueloVueltaSeleccionado() {
		return vueloVueltaSeleccionado;
	}

	protected void setVueloVueltaSeleccionado(InstanciaVueloBean vueloVueltaSeleccionado) {
		this.vueloVueltaSeleccionado = vueloVueltaSeleccionado;
	}

	protected DetalleVueloBean getDetalleVueloIdaSeleccionado() {
		return this.detalleVueloIdaSeleccionado;
	}

	protected void setDetalleVueloIdaSeleccionado(DetalleVueloBean detalleVueloIdaSeleccionado) {
		this.detalleVueloIdaSeleccionado = detalleVueloIdaSeleccionado;
	}

	protected DetalleVueloBean getDetalleVueloVueltaSeleccionado() {
		return this.detalleVueloVueltaSeleccionado;
	}

	protected void setDetalleVueloVueltaSeleccionado(DetalleVueloBean detalleVueloVueltaSeleccionado) {
		this.detalleVueloVueltaSeleccionado = detalleVueloVueltaSeleccionado;
	}

	protected void setPasajeroSeleccionado(PasajeroBean pasajeroSeleccionado) {
		this.pasajeroSeleccionado = pasajeroSeleccionado;
	}

	protected PasajeroBean getPasajeroSeleccionado() {
		return this.pasajeroSeleccionado;
	}


}
