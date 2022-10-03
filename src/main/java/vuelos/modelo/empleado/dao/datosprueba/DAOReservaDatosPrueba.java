package vuelos.modelo.empleado.dao.datosprueba;

import java.sql.Date;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import vuelos.modelo.empleado.beans.DetalleVueloBean;
import vuelos.modelo.empleado.beans.EmpleadoBean;
import vuelos.modelo.empleado.beans.InstanciaVueloBean;
import vuelos.modelo.empleado.beans.InstanciaVueloClaseBean;
import vuelos.modelo.empleado.beans.InstanciaVueloClaseBeanImpl;
import vuelos.modelo.empleado.beans.PasajeroBean;
import vuelos.modelo.empleado.beans.ReservaBean;
import vuelos.modelo.empleado.beans.ReservaBeanImpl;
import vuelos.utils.Fechas;

/*CLASE IMPLEMENTADA PROVISTA POR LA CATEDRA
Contiene datos estáticos de prueba para mostrar en la aplicación parcialemente implementada.
Una vez completada la implementacion esta clase ya no se utilizará. */
public class DAOReservaDatosPrueba {
	
	private static Logger logger = LoggerFactory.getLogger(DAOReservaDatosPrueba.class);
	
	public DAOReservaDatosPrueba() {		
	}
	
	private static ReservaBean reserva = new ReservaBeanImpl();
	
	public static ReservaBean getReserva() {
		return reserva;
	}
	
	public static void registrarReservaSoloIda(PasajeroBean pasajero, 
											   InstanciaVueloBean vuelo, 
											   DetalleVueloBean detalleVuelo,
											   EmpleadoBean empleado) throws Exception {
		
		logger.info("se actualiza la reserva con una reserva de solo ida");
		
		InstanciaVueloClaseBean vueloClase = new InstanciaVueloClaseBeanImpl();
		vueloClase.setVuelo(vuelo);
		vueloClase.setClase(detalleVuelo);
		
		ArrayList<InstanciaVueloClaseBean> vuelos = new ArrayList<InstanciaVueloClaseBean>();
		vuelos.add(vueloClase);
				
		reserva.setEsIdaVuelta(false);
		reserva.setEmpleado(empleado);
		reserva.setPasajero(pasajero);
		reserva.setVuelosClase(vuelos);
		
		reserva.setNumero(recuperarCodigoReserva(pasajero.getNroDocumento()));
		reserva.setEstado(recuperarEstadoReserva(reserva.getNumero()));
		reserva.setFecha(Fechas.hoy());
		reserva.setVencimiento(Fechas.hoy());
	}
	
	public static void registrarReservaIdaVuelta(PasajeroBean pasajero, 
												 InstanciaVueloBean vueloIda,
												 DetalleVueloBean detalleVueloIda,
												 InstanciaVueloBean vueloVuelta,
												 DetalleVueloBean detalleVueloVuelta,
												 EmpleadoBean empleado) throws Exception {
		
		logger.info("se actualiza la reserva con una reserva de ida y vuelta");
		
		InstanciaVueloClaseBean vueloClaseIda = new InstanciaVueloClaseBeanImpl();
		vueloClaseIda.setVuelo(vueloIda);
		vueloClaseIda.setClase(detalleVueloIda);
		
		InstanciaVueloClaseBean vueloClaseVuelta = new InstanciaVueloClaseBeanImpl();
		vueloClaseVuelta.setVuelo(vueloVuelta);
		vueloClaseVuelta.setClase(detalleVueloVuelta);
				
		ArrayList<InstanciaVueloClaseBean> vuelos = new ArrayList<InstanciaVueloClaseBean>();
		vuelos.add(vueloClaseIda);
		vuelos.add(vueloClaseVuelta);
				
		reserva.setEsIdaVuelta(true);
		reserva.setEmpleado(empleado);
		reserva.setPasajero(pasajero);
		reserva.setVuelosClase(vuelos);
		
		reserva.setNumero(recuperarCodigoReserva(pasajero.getNroDocumento()));
		reserva.setEstado(recuperarEstadoReserva(reserva.getNumero()));
		reserva.setFecha(Fechas.hoy());
		reserva.setVencimiento(Fechas.hoy());
	}
	
	protected static int recuperarCodigoReserva(int nroDoc) throws Exception {
		/*
		 * Datos de prueba:
		 * 
		 * - Si pasajero tiene nro_doc igual a 1 retorna 101 codigo de reserva y si se pregunta por dicha reserva como dato de prueba resultado "Reserva confirmada"
		 * - Si pasajero tiene nro_doc igual a 2 retorna 102 codigo de reserva y si se pregunta por dicha reserva como dato de prueba resultado "Reserva en espera"
		 * - Si pasajero tiene nro_doc igual a 3 se genera una excepción, resultado "No hay asientos disponibles"
		 * - Si pasajero tiene nro_doc igual a 4 se genera una excepción, resultado "El empleado no es válido"
		 * - Si pasajero tiene nro_doc igual a 5 se genera una excepción, resultado "El pasajero no está registrado"
		 * - Si pasajero tiene nro_doc igual a 6 se genera una excepción, resultado "El vuelo no es válido"
		 * - Si pasajero tiene nro_doc igual a 7 se genera una excepción de conexión.
		 */		
		int codigo = 0;
		switch (nroDoc) {
			case 1: codigo=101;	break;
			case 2: codigo=102; break;
			case 3: throw new Exception("No hay asientos disponibles.");
			case 4: throw new Exception("El empleado no es válido.");
			case 5: throw new Exception("El pasajero no está registrado.");
			case 6: throw new Exception("El vuelo no es válido.");
			case 7: throw new SQLException("Error en la conexión con la BD.");
			default: throw new Exception("El pasajero no está registrado.");
		}
		return codigo;
	}

	protected static String recuperarEstadoReserva(int codigoReserva) throws Exception {
		
		String estado = "";
		
		switch (codigoReserva) {
			case 101: 
				estado = "Reserva confirmada";
				break;
			case 102: 
				estado = "Reserva en espera";
				break;
			default:
				throw new Exception("Código de reserva no válido");				
		}
		
		return estado;
	}

}
