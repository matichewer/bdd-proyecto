package vuelos.modelo.empleado.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import vuelos.modelo.empleado.beans.AeropuertoBean;
import vuelos.modelo.empleado.beans.AeropuertoBeanImpl;
import vuelos.modelo.empleado.beans.DetalleVueloBean;
import vuelos.modelo.empleado.beans.DetalleVueloBeanImpl;
import vuelos.modelo.empleado.beans.InstanciaVueloBean;
import vuelos.modelo.empleado.beans.InstanciaVueloBeanImpl;
import vuelos.modelo.empleado.beans.UbicacionesBean;
import vuelos.modelo.empleado.beans.UbicacionesBeanImpl;
import vuelos.modelo.empleado.dao.datosprueba.DAOVuelosDatosPrueba;

public class DAOVuelosImpl implements DAOVuelos {

	private static Logger logger = LoggerFactory.getLogger(DAOVuelosImpl.class);
	
	//conexión para acceder a la Base de Datos
	private Connection conexion;
	
	public DAOVuelosImpl(Connection conexion) {
		this.conexion = conexion;
	}

	@Override
	public ArrayList<InstanciaVueloBean> recuperarVuelosDisponibles(Date fechaVuelo, UbicacionesBean origen, UbicacionesBean destino) throws Exception{
		/** 
		 * TODO Debe retornar una lista de vuelos disponibles para ese día con origen y destino según los parámetros. 
		 *      Debe propagar una excepción si hay algún error en la consulta.    
		 *      
		 *      Nota: para acceder a la B.D. utilice la propiedad "conexion" que ya tiene una conexión
		 *      establecida con el servidor de B.D. (inicializada en el constructor DAOVuelosImpl(...)).  
		 */
		//Datos estáticos de prueba. Quitar y reemplazar por código que recupera los datos reales.
		ArrayList<InstanciaVueloBean> resultado = DAOVuelosDatosPrueba.generarVuelos(fechaVuelo);  
		String sql = "SELECT fecha, ciudad_sale, estado_sale, pais_sale, ciudad_llega, estado_llega, pais_llega, "
				+ "nro_vuelo, modelo, dia_sale, hora_sale, hora_llega, tiempo_estimado, codigo_aero_sale, codigo_aero_llega "
				+ "FROM vuelos_disponibles NATURAL JOIN  "
				+ "WHERE fecha="+ fechaVuelo + " AND " +
						"ciudad_sale='" + origen.getCiudad() + "' AND " +
						"estado_sale='" + origen.getEstado() + "' AND " +
						"pais_sale='" + origen.getPais() + "' AND " +
						"ciudad_llega='" + destino.getCiudad() + "' AND " +
						"estado_sale='" + destino.getEstado() + "' AND " +
						"pais_sale='" + destino.getPais() + "'";
	
		/*
		private String nroVuelo;
		private String modelo;	
		private String diaSalida;	
		private Time horaSalida;	
		private Time horaLlegada;	
		private Time tiempoEstimado;
		private Date fechaVuelo;	
		private AeropuertoBean aeropuertoSalida;	
		private AeropuertoBean aeropuertoLlegada;
	*/
		try { 
			Statement select = conexion.createStatement();
			ResultSet rs = select.executeQuery(sql);

			if (rs.next()){
				logger.debug("");
				InstanciaVueloBean iv = new InstanciaVueloBeanImpl();
				//iv.setAeropuertoLlegada(rs.getString())
				
			} else {
				logger.debug("");
			}
			
		} catch (SQLException ex) {
			logger.error("SQLException: " + ex.getMessage());
			logger.error("SQLState: " + ex.getSQLState());
			logger.error("VendorError: " + ex.getErrorCode());
			throw new Exception("Error en la conexion con la BD.");
		}
		return resultado;
		
		// Fin datos estáticos de prueba.
	}

	@Override
	public ArrayList<DetalleVueloBean> recuperarDetalleVuelo(InstanciaVueloBean vuelo) throws Exception {
		/** 
		 * TODO Debe retornar una lista de clases, precios y asientos disponibles de dicho vuelo.		   
		 *      Debe propagar una excepción si hay algún error en la consulta.    
		 *      
		 *      Nota: para acceder a la B.D. utilice la propiedad "conexion" que ya tiene una conexión
		 *      establecida con el servidor de B.D. (inicializada en el constructor DAOVuelosImpl(...)).
		 */
		//Datos estáticos de prueba. Quitar y reemplazar por código que recupera los datos reales.
		ArrayList<DetalleVueloBean> resultado = DAOVuelosDatosPrueba.generarDetalles(vuelo);
		
		return resultado; 
		// Fin datos estáticos de prueba.
	}
}
