package vuelos.vista.empleado;

import java.util.ArrayList;
import vuelos.controlador.ControladorEmpleado;
import vuelos.modelo.empleado.beans.DetalleVueloBean;
import vuelos.modelo.empleado.beans.EmpleadoBean;
import vuelos.modelo.empleado.beans.InstanciaVueloBean;
import vuelos.modelo.empleado.beans.PasajeroBean;
import vuelos.modelo.empleado.beans.ReservaBean;
import vuelos.modelo.empleado.beans.UbicacionesBean;
import vuelos.vista.Ventana;

public interface VentanaEmpleado extends Ventana {

	public final static String VACIO = "vacio";
	
	/**
	 * Constante que identifica la opcion del menu que muestra los vuelos y permite realizar reservas
	 */
	public final static String VUELOS_DISPONIBLES = "vuelosDisponibles";
	
	/**
	 * Constantes que identifican las pantallas de la busqueda de vuelos y reservas
	 */
	public final static String VUELOS_DISPONIBLES_BUSCAR = "busqueda";
	public final static String VUELOS_DISPONIBLES_BUSQUEDA_RESULTADO_SOLO_IDA = "busqueda_ida";
	public final static String VUELOS_DISPONIBLES_BUSQUEDA_RESULTADO_IDA_VUELTA = "busqueda_idavuelta";
	public final static String VUELOS_DISPONIBLES_RESERVA_SOLO_IDA  = "reserva_ida";
	public final static String VUELOS_DISPONIBLES_RESERVA_IDA_VUELTA = "reserva_idavuelta";
	public final static String VUELOS_DISPONIBLES_RESERVA_RESULTADO_SOLO_IDA  = "reserva_resultado_ida";
	public final static String VUELOS_DISPONIBLES_RESERVA_RESULTADO_IDA_VUELTA = "reserva_resultado_idavuelta";	
	
	/**
	 * Columnas de la tabla de resultado de la busqueda
	 */
	public final static String TABLA_VUELOS_DISPONIBLES_NRO_VUELO = "nro_vuelo";
	public final static String TABLA_VUELOS_DISPONIBLES_AEROPUERTO_SALIDA = "aeropuerto_salida";
	public final static String TABLA_VUELOS_DISPONIBLES_HORA_SALIDA = "hora_salida";
	public final static String TABLA_VUELOS_DISPONIBLES_AEROPUERTO_LLEGADA = "aeropuerto_llegada";
	public final static String TABLA_VUELOS_DISPONIBLES_HORA_LLEGADA = "hora_llegada";
	public final static String TABLA_VUELOS_DISPONIBLES_MODELO_AVION = "modelo_avion";
	public final static String TABLA_VUELOS_DISPONIBLES_TIEMPO_ESTIMADO = "tiempo_estimado";

	public final static String TABLA_DETALLE_VUELOS_DISPONIBLES_CLASE = "clase";
	public final static String TABLA_DETALLE_VUELOS_DISPONIBLES_PRECIO = "precio";
	public final static String TABLA_DETALLE_VUELOS_DISPONIBLES_ASIENTOS_DISPONIBLES = "asientos_disponibles";
	
	/**
	 * Muestra el panel (ventana) que corresponda a alguna de las opciones del menu principal
	 * 
	 * @param panel
	 */
	public void mostrarPanel(String panel);
	
	/**
	 * Establece quien es el controlador donde deberá realizar las solicitudes
	 * 
	 * @param controlador
	 */
	public void registrarControlador(ControladorEmpleado controlador);
	
	/**
	 * Muestra en la ventana la información sobre el empleado que está logueado.
	 * 
	 * @param empleado objeto de tipo EmpleadoBean 
	 */
	public void mostrarEmpleadoLogueado(EmpleadoBean empleado);	
	
	/**
	 * Carga en las componentes que permiten seleccionar la ciudad origen y destino
	 * las diferentes opciones.
	 * 
	 * @param List<UbicacionesBean> ubicaciones  
	 */
	public void poblarUbicaciones(ArrayList<UbicacionesBean> ubicaciones);
	
	/**
	 * Muestra la lista de vuelos disponibles para un viaje de IDA en la componente (tabla) dispuesta
	 * 
	 * @param vuelosDisponibles
	 */
	public void mostrarVuelosDisponiblesSoloIda(ArrayList<InstanciaVueloBean> vuelosDisponibles);
	
	/**
	 * Muestra la lista de vuelos disponibles para un viaje de VUELTA en la componente (tabla) dispuesta
	 * 
	 * @param vuelosDisponibles
	 */
	public void mostrarVuelosDisponiblesVuelta(	ArrayList<InstanciaVueloBean> vuelosDisponiblesIda,
												ArrayList<InstanciaVueloBean> vuelosDisponiblesVuelta);

	/**
	 * Muestra la lista de clases, asientos disponibles y precio en la tabla de detalle en Solo Ida
	 * 
	 * @param detalle
	 */
	public void mostrarDetalleVuelosDisponiblesSoloIda(ArrayList<DetalleVueloBean> detalle);

	/**
	 * Muestra la lista de clases, asientos disponibles y precio en la tabla de detalle de Ida en los viajes de Ida y Vuelta
	 * 
	 * @param detalle
	 */
	public void mostrarDetalleVuelosDisponiblesIdaViajeIdaVuelta(ArrayList<DetalleVueloBean> detalle);	

	/**
	 * Muestra la lista de clases, asientos disponibles y precio en la tabla de detalle de Vuelta en los viajes de Ida y Vuelta
	 * 
	 * @param detalle
	 */
	public void mostrarDetalleVuelosDisponiblesVueltaViajeIdaVuelta(ArrayList<DetalleVueloBean> detalle);	

	/**
	 * Muestra la pantalla que permite realizar la solicitud de reservas.
	 * 
	 * @param tiposDocumento Recibe una lista de tipos de documento válidos.
	 * @param vuelo Recibe el vuelo seleccionado
	 * @param detalle Recibe la clase seleccionada
	 */
	public void mostrarSolicitudReservaSoloIda(ArrayList<String> tiposDocumento, InstanciaVueloBean vuelo, DetalleVueloBean detalle);
	
	/**
	 * Muestra la pantalla que permite realizar la solicitud de reservas.
	 * 
	 * @param tiposDocumento Recibe una lista de tipos de documento válidos.
	 * @param vueloIda Recibe el vuelo de ida seleccionado
	 * @param detalleIda Recibe la clase de ida seleccionada
	 * @param vueloVuelta Recibe el vuelo de vuelta seleccionado
	 * @param detalleVuelta Recibe la clase de vuelta seleccionada
	 */
	public void mostrarSolicitudReservaIdaVuelta(ArrayList<String> tiposDocumento, InstanciaVueloBean vueloIda, DetalleVueloBean detalleIda, InstanciaVueloBean vueloVuelta, DetalleVueloBean detalleVuelta);

	/**
	 * Muestra en pantalla la información del pasajero que va a realizar la reserva.
	 * 
	 * @param esIdaVuelta indica si está en la reserva de SoloIda (false) o idaVuelta (true)
	 * @param pasajero
	 */
	public void mostrarPasajero(boolean esIdaVuelta, PasajeroBean pasajero);

	/**
	 * Muestra la pantalla final del proceso de reserva donde se muestran todos los datos
	 * de la reserva.
	 * 
	 * @param reserva
	 */
	public void mostrarResultadoReservaSoloIda(ReservaBean reserva);

	/**
	 * Muestra la pantalla final del proceso de reserva donde se muestran todos los datos
	 * de la reserva.
	 * 
	 * @param reserva
	 */
	public void mostrarResultadoReservaIdaVuelta(ReservaBean reserva);

}
