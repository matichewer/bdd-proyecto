package vuelos.modelo.empleado;

import java.util.ArrayList;
import java.util.Date;

import vuelos.modelo.Modelo;
import vuelos.modelo.empleado.beans.DetalleVueloBean;
import vuelos.modelo.empleado.beans.EmpleadoBean;
import vuelos.modelo.empleado.beans.InstanciaVueloBean;
import vuelos.modelo.empleado.beans.PasajeroBean;
import vuelos.modelo.empleado.beans.ReservaBean;
import vuelos.modelo.empleado.beans.UbicacionesBean;

public interface ModeloEmpleado extends Modelo {
	
	/**
	 * Verifica que el usuario con el que intenta realizar el login corresponde a un empleado del vuelos.
	 * Antes de poder autenticar deberá estar conectado a la BD con un usuario de BD. ver conectar/2
	 * Registra en una propiedad interna el legajo del empleado si es autenticado.
	 *  
	 * @param legajo
	 * @param password
	 * @return verdadero si el usuario tiene acceso o falso en caso contrario. 
	 * @throws Exception Produce una excepción si hay algún problema con los parámetros o de conexión.
	 */
	public boolean autenticarUsuarioAplicacion(String legajo, String password) throws Exception;
	
	/**
	 * Recupera el empleado que se ha logueado para realizar las operaciones.
	 * 
	 * @return EmpleadoBean o null en caso que no haya ningún empleado logueado.
	 * @throws Exception si ocurre algun problema de conexión.
	 */
	public EmpleadoBean obtenerEmpleadoLogueado() throws Exception;

	/**
	 * Recupera una lista de ubicaciones (distinos  de viaje elegibles)
	 * 
	 * @return ArrayList<UbicacionesBean>
	 */
	public ArrayList<UbicacionesBean> recuperarUbicaciones() throws Exception;

	/**
	 * Recupera los vuelos disponibles para la fecha indicada
	 * 
	 * @param fechaVuelo
	 * @param origen
	 * @param destino
	 * @return
	 * @throws Exception 
	 */
	public ArrayList<InstanciaVueloBean> obtenerVuelosDisponibles(Date fechaVuelo, UbicacionesBean origen, UbicacionesBean destino) throws Exception;

	/**
	 * Recupera los asientos disponibles y el precio de cada clase del vuelo solicitado
	 * 
	 * @param InstanciaVueloBean
	 *
	 * @return
	 */
	public ArrayList<DetalleVueloBean> obtenerDetalleVuelo(InstanciaVueloBean vuelo) throws Exception;

	/**
	 * Recupera los tipos de DNI validos para realizar una reserva
	 * 
	 * @return
	 */
	public ArrayList<String> obtenerTiposDocumento();

	/**
	 * Busca un pasajero en funcion del tipo y nro de documento  
	 * 
	 * @param tipoDoc
	 * @param nroDoc
	 * @return PasajeroBean 
	 * @throws Exception Si no encuentra al pasajero.
	 */
	public PasajeroBean obtenerPasajero(String tipoDoc, int nroDoc) throws Exception;

	/**
	 * Realiza una reserva de UN asiento para el pasajero seleccionado, en el vuelo indicado 
	 * y en la clase que especifica detalleVuelo. Se implementa invocando al stored procedure correspondiente. 
	 *  
	 * @param pasajero
	 * @param vuelo
	 * @param detalleVuelo
	 * @return ReservaBean retorna una reserva con todos los datos
	 * @throws Exception Si no puede realizar la reserva.
	 */
	public ReservaBean reservarSoloIda(PasajeroBean pasajero, 
								   	   InstanciaVueloBean vuelo,
								       DetalleVueloBean detalleVuelo) throws Exception;

	/**
	 * Realiza una reserva de UN asiento para el pasajero seleccionado, en los dos
	 * vuelos indicados y en las clases mencionadas. Se implementa invocando al 
	 * stored procedure correspondiente 
	 * 
	 *  
	 * @param pasajeroSeleccionado
	 * @param vueloIdaSeleccionado
	 * @param detalleVueloIdaSeleccionado
	 * @param vueloVueltaSeleccionado
	 * @param detalleVueloVueltaSeleccionado
	 * @return ReservaBean retorna una reserva con todos los datos
	 * @throws Exception Si no puede realizar la reserva.
	 */
	public ReservaBean reservarIdaVuelta(PasajeroBean pasajeroSeleccionado, 
								         InstanciaVueloBean vueloIdaSeleccionado,
								         DetalleVueloBean detalleVueloIdaSeleccionado,
								         InstanciaVueloBean vueloVueltaSeleccionado,
								         DetalleVueloBean detalleVueloVueltaSeleccionado) throws Exception;
	
}
