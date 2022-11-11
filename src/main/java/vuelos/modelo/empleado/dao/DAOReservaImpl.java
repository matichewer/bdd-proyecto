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
import vuelos.modelo.empleado.beans.DetalleVueloBeanImpl;
import vuelos.modelo.empleado.beans.EmpleadoBean;
import vuelos.modelo.empleado.beans.EmpleadoBeanImpl;
import vuelos.modelo.empleado.beans.InstanciaVueloBean;
import vuelos.modelo.empleado.beans.InstanciaVueloBeanImpl;
import vuelos.modelo.empleado.beans.InstanciaVueloClaseBean;
import vuelos.modelo.empleado.beans.InstanciaVueloClaseBeanImpl;
import vuelos.modelo.empleado.beans.PasajeroBean;
import vuelos.modelo.empleado.beans.PasajeroBeanImpl;
import vuelos.modelo.empleado.beans.ReservaBean;
import vuelos.modelo.empleado.beans.ReservaBeanImpl;
import vuelos.modelo.empleado.beans.UbicacionesBean;
import vuelos.modelo.empleado.beans.UbicacionesBeanImpl;
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
		int numeroDeReserva = -1;
		 try {
			 String sql = "CALL reservaSoloIda(?, ?, ?, ?, ?, ?)";
			 CallableStatement cstmt = conexion.prepareCall(sql);  
			 
			 cstmt.setString(1, vuelo.getNroVuelo());
			 cstmt.setDate(2, Fechas.convertirDateADateSQL(vuelo.getFechaVuelo()));
			 cstmt.setString(3, detalleVuelo.getClase());
			 cstmt.setString(4, pasajero.getTipoDocumento());
			 cstmt.setInt(5, pasajero.getNroDocumento());
			 cstmt.setInt(6, empleado.getLegajo());	  

			 ResultSet rs = cstmt.executeQuery();
			 if (rs.next()) {
				 String resultado = rs.getString("resultado");
	        	 if (resultado.equals("Reserva exitosa")) {
	        		 numeroDeReserva = rs.getInt("numero_reserva");
	        	 	 logger.debug(resultado + ". Numero de reserva: " + numeroDeReserva);
	        	 }
	        	 else {
		        	 logger.debug(resultado);
	        		 throw new Exception(resultado);
	        	 }
			 }	 
	         rs.close();
			 cstmt.close();
		  }
		  catch (SQLException ex){
		  		logger.debug("Error al consultar la BD. SQLException: {}. SQLState: {}. VendorError: {}.", ex.getMessage(), ex.getSQLState(), ex.getErrorCode());
		   		throw ex;
		  } 		 
		return numeroDeReserva;
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
		 */
		
		 int numeroDeReserva = -1;
		 try {
			 String sql = "CALL reservaIdaVuelta(?, ?, ?, ?, ?, ?, ?, ?, ?)";
			 CallableStatement cstmt = conexion.prepareCall(sql);  
			 
			 cstmt.setString(1, vueloIda.getNroVuelo());
			 cstmt.setDate(2, Fechas.convertirDateADateSQL(vueloIda.getFechaVuelo()));
			 cstmt.setString(3, detalleVueloIda.getClase());
			 
			 cstmt.setString(4, vueloVuelta.getNroVuelo());
			 cstmt.setDate(5, Fechas.convertirDateADateSQL(vueloVuelta.getFechaVuelo()));
			 cstmt.setString(6, detalleVueloVuelta.getClase());
			 
			 cstmt.setString(7, pasajero.getTipoDocumento());
			 cstmt.setInt(8, pasajero.getNroDocumento());
			 cstmt.setInt(9, empleado.getLegajo());	  

			 ResultSet rs = cstmt.executeQuery();
			 if (rs.next()) {
				 String resultado = rs.getString("resultado");
	        	 if (resultado.equals("Reserva exitosa")) {
	        		 numeroDeReserva = rs.getInt("numero_reserva");
	        	 	 logger.debug(resultado + ". Numero de reserva: " + numeroDeReserva);
	        	 }
	        	 else {
		        	 logger.debug(resultado);
	        		 throw new Exception(resultado);
	        	 }
			 }	 
	         rs.close();
			 cstmt.close();
		  }
		  catch (SQLException ex){
		  		logger.debug("Error al consultar la BD. SQLException: {}. SQLState: {}. VendorError: {}.", ex.getMessage(), ex.getSQLState(), ex.getErrorCode());
		   		throw ex;
		  } 
		return numeroDeReserva;
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

		ReservaBean reserva = new ReservaBeanImpl();
		
		
		ArrayList<InstanciaVueloClaseBean> lista = new ArrayList<InstanciaVueloClaseBean>();
		try { 
			
			String sql1 = "SELECT numero, fecha, vencimiento, doc_tipo, doc_nro, legajo, estado, IF(COUNT(*)='2','si', 'no') AS idaVuelta "
						+ "FROM reservas NATURAL JOIN reserva_vuelo_clase "
						+ "WHERE numero=" + codigoReserva;

			logger.debug(sql1);
			Statement stmt1 = conexion.createStatement();
		    ResultSet rs1 = stmt1.executeQuery(sql1);			

	        if (rs1.next()) {
	        	reserva.setNumero(rs1.getInt("numero"));
	        	reserva.setFecha(rs1.getDate("fecha"));
	        	reserva.setVencimiento(rs1.getDate("vencimiento"));
	        	reserva.setEstado(rs1.getString("estado"));
	        	reserva.setEsIdaVuelta(rs1.getString("idaVuelta").equals("si"));	        	

		        DAOPasajero daoPasajero = new DAOPasajeroImpl(this.conexion);
		        DAOEmpleado daoEmpleado =new DAOEmpleadoImpl(this.conexion);
				EmpleadoBean empleado = daoEmpleado.recuperarEmpleado(rs1.getInt("legajo"));
				PasajeroBean pasajero = daoPasajero.recuperarPasajero(rs1.getString("doc_tipo"),rs1.getInt("doc_nro"));
				reserva.setEmpleado(empleado);
				reserva.setPasajero(pasajero);	
	        	
	        	logger.debug("Se recuperó la reserva: {}, {}", reserva.getNumero(), reserva.getEstado());	
	        }   
	        
	        rs1.close();
	        stmt1.close();
	        
	        String sql2 = "SELECT DISTINCT fecha, ciudad_sale, estado_sale, pais_sale, u_sale.huso AS huso_sale, ciudad_llega, estado_llega, pais_llega, u_llega.huso AS huso_llega "
	        			+ "FROM ((vuelos_disponibles vd JOIN reserva_vuelo_clase rvc ON vd.nro_vuelo=rvc.vuelo AND vd.fecha=rvc.fecha_vuelo AND vd.clase=rvc.clase) "
	        			+ "	JOIN ubicaciones u_llega ON u_llega.ciudad=ciudad_llega AND u_llega.pais=pais_llega AND u_llega.estado=estado_llega) "
	        			+ "	JOIN ubicaciones u_sale ON u_sale.ciudad=ciudad_sale AND u_sale.pais=pais_sale AND u_sale.estado=estado_sale "
	        			+ "WHERE rvc.numero= " + codigoReserva;

			logger.debug(sql2);
			Statement stmt2 = conexion.createStatement();
		    ResultSet rs2 = stmt2.executeQuery(sql2);	
	        
	        while(rs2.next()) {	
				UbicacionesBean origen = new UbicacionesBeanImpl();
				origen.setCiudad(rs2.getString("ciudad_sale"));
				origen.setEstado(rs2.getString("estado_sale"));
				origen.setPais(rs2.getString("pais_sale"));
				origen.setHuso(rs2.getInt("huso_sale"));
				
				UbicacionesBean destino = new UbicacionesBeanImpl();
				destino.setCiudad(rs2.getString("ciudad_llega"));
				destino.setEstado(rs2.getString("estado_llega"));
				destino.setPais(rs2.getString("pais_llega"));
				destino.setHuso(rs2.getInt("huso_llega"));

				DAOVuelosImpl daoVuelos = new DAOVuelosImpl(this.conexion);
				InstanciaVueloBean vuelo = (daoVuelos.recuperarVuelosDisponibles(rs2.getDate("fecha"), origen, destino)).get(0);				
				DetalleVueloBean clase = (daoVuelos.recuperarDetalleVuelo(vuelo)).get(0);				

				InstanciaVueloClaseBean toAddToList = new InstanciaVueloClaseBeanImpl();
				toAddToList.setClase(clase);
				toAddToList.setVuelo(vuelo);

				lista.add(toAddToList);	        	
	        }	        
	        reserva.setVuelosClase(lista);
		}
		catch (SQLException ex)
		{			
			logger.error("SQLException: " + ex.getMessage());
			logger.error("SQLState: " + ex.getSQLState());
			logger.error("VendorError: " + ex.getErrorCode());
			throw new Exception("Error inesperado al consultar la B.D.");
		}	
		return reserva;
	}
	

}
