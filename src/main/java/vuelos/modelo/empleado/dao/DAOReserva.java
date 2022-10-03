package vuelos.modelo.empleado.dao;

import vuelos.modelo.empleado.beans.DetalleVueloBean;
import vuelos.modelo.empleado.beans.EmpleadoBean;
import vuelos.modelo.empleado.beans.InstanciaVueloBean;
import vuelos.modelo.empleado.beans.PasajeroBean;
import vuelos.modelo.empleado.beans.ReservaBean;

public interface DAOReserva {

	
	/**
	 * Realiza la reserva segun los datos de los parámetros. Si tiene exito retorna un entero con el 
	 * código de reserva. Si falla se produce una excepción con el error que se produjo.
	 * 
	 * @param pasajero
	 * @param vuelo
	 * @param detalleVuelo
	 * @param empleado
	 * @return int que indica el codigo de la reserva registrada
	 * @throws Exception Error que se produjo a realizar la reserva.
	 */
	public int reservarSoloIda(PasajeroBean pasajero, 
							   InstanciaVueloBean vuelo, 
							   DetalleVueloBean detalleVuelo,
							   EmpleadoBean empleado) throws Exception;

	/**
	 * Realiza la reserva segun los datos de los parámetros. Si tiene exito retorna un entero con el 
	 * código de reserva. Si falla se produce una excepción con el error que se produjo.
	 * 
	 * @param pasajero
	 * @param vueloIda
	 * @param detalleVueloIda
	 * @param vueloVuelta
	 * @param detalleVueloVuelta
	 * @param empleado
	 * @return int que indica el codigo de la reserva registrada
	 * @throws Exception Error que se produjo a realizar la reserva.
	 */
	public int reservarIdaVuelta(PasajeroBean pasajero, 
			   					 InstanciaVueloBean vueloIda,
								 DetalleVueloBean detalleVueloIda, 
								 InstanciaVueloBean vueloVuelta,
								 DetalleVueloBean detalleVueloVuelta, 
								 EmpleadoBean empleado) throws Exception;

	/**
	 * Recupera la reserva con todas las instancias de vuelo asociadas a una reserva. 
	 * 
	 * @param codigoReserva
	 * @return
	 * @throws Exception
	 */
	public ReservaBean recuperarReserva(int codigoReserva) throws Exception;
}