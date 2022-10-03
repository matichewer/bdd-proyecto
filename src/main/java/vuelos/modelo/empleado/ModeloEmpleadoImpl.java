package vuelos.modelo.empleado;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import vuelos.modelo.ModeloImpl;
import vuelos.modelo.empleado.beans.DetalleVueloBean;
import vuelos.modelo.empleado.beans.EmpleadoBean;
import vuelos.modelo.empleado.beans.InstanciaVueloBean;
import vuelos.modelo.empleado.beans.PasajeroBean;
import vuelos.modelo.empleado.beans.ReservaBean;
import vuelos.modelo.empleado.beans.UbicacionesBean;
import vuelos.modelo.empleado.beans.UbicacionesBeanImpl;
import vuelos.modelo.empleado.dao.DAOEmpleado;
import vuelos.modelo.empleado.dao.DAOEmpleadoImpl;
import vuelos.modelo.empleado.dao.DAOPasajero;
import vuelos.modelo.empleado.dao.DAOPasajeroImpl;
import vuelos.modelo.empleado.dao.DAOReserva;
import vuelos.modelo.empleado.dao.DAOReservaImpl;
import vuelos.modelo.empleado.dao.DAOVuelos;
import vuelos.modelo.empleado.dao.DAOVuelosImpl;
import vuelos.modelo.empleado.dao.datosprueba.DAOUbicacionesDatosPrueba;

public class ModeloEmpleadoImpl extends ModeloImpl implements ModeloEmpleado {

	private static Logger logger = LoggerFactory.getLogger(ModeloEmpleadoImpl.class);	

	
	private Integer legajo = null;
	
	public ModeloEmpleadoImpl() {
		logger.debug("Se crea el modelo Empleado.");
	}
	

	@Override
	public boolean autenticarUsuarioAplicacion(String legajo, String password) throws Exception {
		logger.info("Se intenta autenticar el legajo {} con password {}", legajo, password);
		/** 
		 * TODO Código que autentica que exista un legajo de empleado y que el password corresponda a ese legajo
		 *      (recuerde que el password guardado en la BD está encriptado con MD5) 
		 *      En caso exitoso deberá registrar el legajo en la propiedad legajo y retornar true.
		 *      Si la autenticación no es exitosa porque el legajo no es válido o el password es incorrecto
		 *      deberá retornar falso y si hubo algún otro error deberá producir y propagar una excepción.
		 */
		
		// Datos estáticos de prueba. Quitar y reemplazar por código que recupera los datos reales.  		
		this.legajo = 1;
		return true;
		// Fin datos estáticos de prueba.
	}
	
	@Override
	public ArrayList<String> obtenerTiposDocumento() {
		logger.info("recupera los tipos de documentos.");
		/** 
		 * TODO Debe retornar una lista de strings con los tipos de documentos. 
		 *      Deberia propagar una excepción si hay algún error en la consulta.
		 */
		
		/*
		 * Datos estáticos de prueba. Quitar y reemplazar por código que recupera los datos reales. 
		 * 
		 *  Como no hay una tabla con los tipos de documento, se deberán recuperar todos los tipos validos
		 *  de la tabla de pasajeros
		 */
		ArrayList<String> tipos = new ArrayList<String>();
		tipos.add("DNI");
		tipos.add("Pasaporte");
		// Fin datos estáticos de prueba.
		
		return tipos;
	}		
	
	@Override
	public EmpleadoBean obtenerEmpleadoLogueado() throws Exception {
		logger.info("Solicita al DAO un empleado con legajo {}", this.legajo);
		if (this.legajo == null) {
			logger.info("No hay un empleado logueado.");
			throw new Exception("No hay un empleado logueado. La sesión terminó.");
		}
		
		DAOEmpleado dao = new DAOEmpleadoImpl(this.conexion);
		return dao.recuperarEmpleado(this.legajo);
	}	

	@Override
	public ArrayList<UbicacionesBean> recuperarUbicaciones() throws Exception {
		
		logger.info("recupera las ciudades que tienen aeropuertos.");
		/** 
		 * TODO Debe retornar una lista de UbicacionesBean con todas las ubicaciones almacenadas en la B.D. 
		 *      Deberia propagar una excepción si hay algún error en la consulta.
		 *      
		 *      Reemplazar el siguiente código de prueba por los datos obtenidos desde la BD.
		 */
		ArrayList<UbicacionesBean> ubicaciones = new ArrayList<UbicacionesBean>();

		// Datos estáticos de prueba. Quitar y reemplazar por código que recupera las ubicaciones de la B.D. en una lista de UbicacionesBean		 
		DAOUbicacionesDatosPrueba.poblar();
		ubicaciones.add(DAOUbicacionesDatosPrueba.obtenerUbicacion("bsas"));
		ubicaciones.add(DAOUbicacionesDatosPrueba.obtenerUbicacion("chicago"));
		ubicaciones.add(DAOUbicacionesDatosPrueba.obtenerUbicacion("barcelona"));
		ubicaciones.add(DAOUbicacionesDatosPrueba.obtenerUbicacion("cordoba"));	
		// Fin datos estáticos de prueba.
	
		return ubicaciones;
	}


	@Override
	public ArrayList<InstanciaVueloBean> obtenerVuelosDisponibles(Date fechaVuelo, UbicacionesBean origen, UbicacionesBean destino) throws Exception {
		
		logger.info("Recupera la lista de vuelos disponibles para la fecha {} desde {} a {}.", fechaVuelo, origen, destino);

		DAOVuelos dao = new DAOVuelosImpl(this.conexion);		
		return dao.recuperarVuelosDisponibles(fechaVuelo, origen, destino);
	}
	
	@Override
	public ArrayList<DetalleVueloBean> obtenerDetalleVuelo(InstanciaVueloBean vuelo) throws Exception {

		logger.info("Recupera la cantidad de asientos y precio del vuelo {} .", vuelo.getNroVuelo());
		
		DAOVuelos dao = new DAOVuelosImpl(this.conexion);		
		return dao.recuperarDetalleVuelo(vuelo);
	}


	@Override
	public PasajeroBean obtenerPasajero(String tipoDoc, int nroDoc) throws Exception {
		logger.info("Solicita al DAO un pasajero con tipo {} y nro {}", tipoDoc, nroDoc);
		
		DAOPasajero dao = new DAOPasajeroImpl(this.conexion);
		return dao.recuperarPasajero(tipoDoc, nroDoc);
	}


	@Override
	public ReservaBean reservarSoloIda(PasajeroBean pasajero, InstanciaVueloBean vuelo, DetalleVueloBean detalleVuelo)
			throws Exception {
		logger.info("Se solicita al modelo realizar una reserva solo ida");

		EmpleadoBean empleadoLogueado = this.obtenerEmpleadoLogueado();
		
		DAOReserva dao = new DAOReservaImpl(this.conexion);
		int nroReserva = dao.reservarSoloIda(pasajero, vuelo, detalleVuelo, empleadoLogueado);
		
		ReservaBean reserva = dao.recuperarReserva(nroReserva); 
		return reserva;
	}


	@Override
	public ReservaBean reservarIdaVuelta(PasajeroBean pasajeroSeleccionado, 
									 InstanciaVueloBean vueloIdaSeleccionado,
									 DetalleVueloBean detalleVueloIdaSeleccionado, 
									 InstanciaVueloBean vueloVueltaSeleccionado,
									 DetalleVueloBean detalleVueloVueltaSeleccionado) throws Exception {
		
		logger.info("Se solicita al modelo realizar una reserva de ida y vuelta");
		
		EmpleadoBean empleadoLogueado = this.obtenerEmpleadoLogueado();
		
		DAOReserva dao = new DAOReservaImpl(this.conexion);
		
		int nroReserva = dao.reservarIdaVuelta(pasajeroSeleccionado, 
									 vueloIdaSeleccionado, 
									 detalleVueloIdaSeleccionado, 
									 vueloVueltaSeleccionado, 
									 detalleVueloVueltaSeleccionado, 
									 empleadoLogueado);
		
		ReservaBean reserva = dao.recuperarReserva(nroReserva); 
		return reserva;		
	}
}
