package vuelos.modelo.empleado.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import vuelos.modelo.empleado.beans.DetalleVueloBean;
import vuelos.modelo.empleado.beans.EmpleadoBean;
import vuelos.modelo.empleado.beans.EmpleadoBeanImpl;
import vuelos.modelo.empleado.beans.InstanciaVueloBean;
import vuelos.modelo.empleado.beans.InstanciaVueloClaseBean;
import vuelos.modelo.empleado.beans.PasajeroBean;
import vuelos.modelo.empleado.beans.PasajeroBeanImpl;
import vuelos.modelo.empleado.beans.ReservaBean;
import vuelos.modelo.empleado.beans.ReservaBeanImpl;
import vuelos.modelo.empleado.dao.datosprueba.DAOReservaDatosPrueba;
import vuelos.utils.Fechas;

public class DAOReservaImpl implements DAOReserva {

	private static Logger logger = LoggerFactory.getLogger(DAOReservaImpl.class);
	
	//conexión para acceder a la Base de Datos
	private Connection conexion;
	
	public DAOReservaImpl(Connection conexion) {
		this.conexion = conexion;
	}
		
	
	@Override
	public int reservarSoloIda(PasajeroBean pasajero, 
							   InstanciaVueloBean vuelo, 
							   DetalleVueloBean detalleVuelo,
							   EmpleadoBean empleado) throws Exception {
		logger.info("Realiza la reserva de solo ida con pasajero {}", pasajero.getNroDocumento());

		/**
		 * TODO (parte 2) Realizar una reserva de ida solamente llamando al Stored Procedure (S.P.) correspondiente. 
		 *      Si la reserva tuvo exito deberá retornar el número de reserva. Si la reserva no tuvo éxito o 
		 *      falla el S.P. deberá propagar un mensaje de error explicativo dentro de una excepción.
		 *      La demás excepciones generadas automáticamente por algun otro error simplemente se propagan.
		 *      
		 *      Nota: para acceder a la B.D. utilice la propiedad "conexion" que ya tiene una conexión
		 *      establecida con el servidor de B.D. (inicializada en el constructor DAOReservaImpl(...)).
		 *		
		 * 
		 * @throws Exception. Deberá propagar la excepción si ocurre alguna. Puede capturarla para loguear los errores
		 *		   pero luego deberá propagarla para que el controlador se encargue de manejarla.
		 */
		
		 try {
			  CallableStatement cstmt = conexion.prepareCall("CALL PROCEDURE reservaSoloIda(?, ?, ?, ?, ?, ?)");
			  cstmt.setInt(1, Integer.parseInt(vuelo.getNroVuelo()));
			  cstmt.setDate(2, Fechas.convertirDateADateSQL(vuelo.getFechaVuelo()));
			  cstmt.setString(3, detalleVuelo.getClase());
			  cstmt.setString(4, pasajero.getTipoDocumento());
			  cstmt.setInt(5, pasajero.getNroDocumento());
			  cstmt.setInt(6, empleado.getLegajo());	
			  
			  ResultSet rs = cstmt.executeQuery();
			  if(rs.next()) {
				  String resultado = rs.getString("resultado");		
				  logger.debug(resultado);
				  
			  }
			  
		  }
		  catch (SQLException ex){
		  		logger.debug("Error al consultar la BD. SQLException: {}. SQLState: {}. VendorError: {}.", ex.getMessage(), ex.getSQLState(), ex.getErrorCode());
		   		throw ex;
		  } 
		 
		
		/*
		 * Datos estaticos de prueba: Quitar y reemplazar por código que invoca al S.P.
		 * 
		 * - Si pasajero tiene nro_doc igual a 1 retorna 101 codigo de reserva y si se pregunta por dicha reserva como dato de prueba resultado "Reserva confirmada"
		 * - Si pasajero tiene nro_doc igual a 2 retorna 102 codigo de reserva y si se pregunta por dicha reserva como dato de prueba resultado "Reserva en espera"
		 * - Si pasajero tiene nro_doc igual a 3 se genera una excepción, resultado "No hay asientos disponibles"
		 * - Si pasajero tiene nro_doc igual a 4 se genera una excepción, resultado "El empleado no es válido"
		 * - Si pasajero tiene nro_doc igual a 5 se genera una excepción, resultado "El pasajero no está registrado"
		 * - Si pasajero tiene nro_doc igual a 6 se genera una excepción, resultado "El vuelo no es válido"
		 * - Si pasajero tiene nro_doc igual a 7 se genera una excepción de conexión.
		 */
		DAOReservaDatosPrueba.registrarReservaSoloIda(pasajero, vuelo, detalleVuelo, empleado);
		ReservaBean r = DAOReservaDatosPrueba.getReserva();
		logger.debug("Reserva: {}, {}", r.getNumero(), r.getEstado());
		int resultado = DAOReservaDatosPrueba.getReserva().getNumero();
		
		return resultado;
		// Fin datos estáticos de prueba.
	}
	
	@Override
	public int reservarIdaVuelta(PasajeroBean pasajero, 
				 				 InstanciaVueloBean vueloIda,
				 				 DetalleVueloBean detalleVueloIda,
				 				 InstanciaVueloBean vueloVuelta,
				 				 DetalleVueloBean detalleVueloVuelta,
				 				 EmpleadoBean empleado) throws Exception {
		
		logger.info("Realiza la reserva de ida y vuelta con pasajero {}", pasajero.getNroDocumento());
		/**
		 * TODO (parte 2) Realizar una reserva de ida y vuelta llamando al Stored Procedure (S.P.) correspondiente. 
		 *      Si la reserva tuvo exito deberá retornar el número de reserva. Si la reserva no tuvo éxito o 
		 *      falla el S.P. deberá propagar un mensaje de error explicativo dentro de una excepción.
		 *      La demás excepciones generadas automáticamente por algun otro error simplemente se propagan.
		 *      
		 *      Nota: para acceder a la B.D. utilice la propiedad "conexion" que ya tiene una conexión
		 *      establecida con el servidor de B.D. (inicializada en el constructor DAOReservaImpl(...)).
		 * 
		 * @throws Exception. Deberá propagar la excepción si ocurre alguna. Puede capturarla para loguear los errores
		 *		   pero luego deberá propagarla para que se encargue el controlador.
		 *
		 * try (CallableStatement ... )
		 * {
		 *  ...
		 * }
		 * catch (SQLException ex){
		 * 			logger.debug("Error al consultar la BD. SQLException: {}. SQLState: {}. VendorError: {}.", ex.getMessage(), ex.getSQLState(), ex.getErrorCode());
		 *  		throw ex;
		 * } 
		 */
		
		/*
		 * Datos státicos de prueba: Quitar y reemplazar por código que invoca al S.P.
		 * 
		 * - Si pasajero tiene nro_doc igual a 1 retorna 101 codigo de reserva y si se pregunta por dicha reserva como dato de prueba resultado "Reserva confirmada"
		 * - Si pasajero tiene nro_doc igual a 2 retorna 102 codigo de reserva y si se pregunta por dicha reserva como dato de prueba resultado "Reserva en espera"
		 * - Si pasajero tiene nro_doc igual a 3 se genera una excepción, resultado "No hay asientos disponibles"
		 * - Si pasajero tiene nro_doc igual a 4 se genera una excepción, resultado "El empleado no es válido"
		 * - Si pasajero tiene nro_doc igual a 5 se genera una excepción, resultado "El pasajero no está registrado"
		 * - Si pasajero tiene nro_doc igual a 6 se genera una excepción, resultado "El vuelo no es válido"
		 * - Si pasajero tiene nro_doc igual a 7 se genera una excepción de conexión.
		 */		
		DAOReservaDatosPrueba.registrarReservaIdaVuelta(pasajero, vueloIda, detalleVueloIda, vueloVuelta, detalleVueloVuelta, empleado);
		int resultado = DAOReservaDatosPrueba.getReserva().getNumero();
		
		return resultado;
		// Fin datos estáticos de prueba.
	}
	
	@Override
	public ReservaBean recuperarReserva(int codigoReserva) throws Exception {
		
		logger.info("Solicita recuperar información de la reserva con codigo {}", codigoReserva);
		
		/**
		 * TODO (parte 2) Debe realizar una consulta que retorne un objeto ReservaBean donde tenga los datos de la
		 *      reserva que corresponda con el codigoReserva y en caso que no exista generar una excepción.
		 *
		 * 		Debe poblar la reserva con todas las instancias de vuelo asociadas a dicha reserva y 
		 * 		las clases correspondientes.
		 * 
		 * 		Los objetos ReservaBean además de las propiedades propias de una reserva tienen un arraylist
		 * 		con pares de instanciaVuelo y Clase. Si la reserva es solo de ida va a tener un unico
		 * 		elemento el arreglo, y si es de ida y vuelta tendrá dos elementos. 
		 * 
		 *      Nota: para acceder a la B.D. utilice la propiedad "conexion" que ya tiene una conexión
		 *      establecida con el servidor de B.D. (inicializada en el constructor DAOReservaImpl(...)).
		 */
		/*
		 * Importante, tenga en cuenta de setear correctamente el atributo IdaVuelta con el método setEsIdaVuelta en la ReservaBean
		 */
		// Datos estáticos de prueba. Quitar y reemplazar por código que recupera los datos reales.
		

		
		String sql = "SELECT * FROM reservas AS r NATURAL JOIN pasajeros AS p JOIN empleados AS e ON r.legajo=e.legajo  WHERE numero=" + codigoReserva;
		ReservaBean reserva =null;
		ArrayList<DetalleVueloBean> lista = new ArrayList<DetalleVueloBean>();
		try{ 
			
			PreparedStatement stmt = conexion.prepareStatement(sql);
	        ResultSet rs = stmt.executeQuery(sql);
	        PasajeroBean pasajero= new PasajeroBeanImpl();
	        EmpleadoBean empleado=new EmpleadoBeanImpl();
	     ArrayList<InstanciaVueloClaseBean> vuelosClase=new  ArrayList<InstanciaVueloClaseBean>(); 

	        if (rs.next()) {
	        	reserva = new ReservaBeanImpl();
	        	reserva.setNumero(rs.getInt("numero"));
	        	reserva.setFecha(rs.getDate("fecha"));
	        	reserva.setVencimiento(rs.getDate("vencimiento"));
	        	reserva.setEstado(rs.getString("estado"));
	        	pasajero.setApellido(rs.getString("p.apellido"));
	        	pasajero.setDireccion(rs.getString("p.direccion"));
	        	pasajero.setNacionalidad(rs.getString("p.nacionalidad"));
	        	pasajero.setNombre(rs.getString("p.nombre"));
	        	pasajero.setNroDocumento(rs.getInt("p.doc_nro"));
	        	pasajero.setTelefono(rs.getString("p.telefono"));
	        	pasajero.setTipoDocumento(rs.getString("p.doc_tipo"));
	        	empleado.setApellido(rs.getString("e.apellido"));
	        	empleado.setDireccion(rs.getString("e.direccion"));
	        	empleado.setLegajo(rs.getInt("e.legajo"));
	        	empleado.setNombre(rs.getString("e.nombre"));
	        	empleado.setNroDocumento(rs.getInt("e.doc_nro"));
	        	empleado.setPassword(rs.getString("e.password"));
	        	empleado.setTelefono(rs.getString("e.telefono"));
	        	empleado.setTipoDocumento(rs.getString("e.doc_tipo"));
	        	reserva.setEsIdaVuelta(rs.getBoolean("idaVuelta"));
	        	
	        	logger.debug("Se recuperó la reserva: {}, {}", reserva.getNumero(), reserva.getEstado());	
	        }   
	        
	        stmt.close();
	        rs.close();
		}
		catch (SQLException ex)
		{			
			logger.error("SQLException: " + ex.getMessage());
			logger.error("SQLState: " + ex.getSQLState());
			logger.error("VendorError: " + ex.getErrorCode());
			throw new Exception("Error inesperado al consultar la B.D.");
		}		
				
		
		
		return reserva;
		// Fin datos estáticos de prueba.
	}
	

}
