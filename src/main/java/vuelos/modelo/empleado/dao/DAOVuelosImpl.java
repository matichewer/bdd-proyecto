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
		// DUDA: ¿hay que chequear que ya hayamos creado un objeto de tipo AeropuertoBean ??
		/** 
		 * TODO Debe retornar una lista de vuelos disponibles para ese día con origen y destino según los parámetros. 
		 *      Debe propagar una excepción si hay algún error en la consulta.    
		 *      
		 *      Nota: para acceder a la B.D. utilice la propiedad "conexion" que ya tiene una conexión
		 *      establecida con el servidor de B.D. (inicializada en el constructor DAOVuelosImpl(...)).  
		 */
		//Datos estáticos de prueba. Quitar y reemplazar por código que recupera los datos reales.
		//ArrayList<InstanciaVueloBean> resultado = DAOVuelosDatosPrueba.generarVuelos(fechaVuelo);
		ArrayList<InstanciaVueloBean> resultado = new ArrayList<InstanciaVueloBean>();
		String sql = "SELECT fecha, ciudad_sale, estado_sale, pais_sale, ciudad_llega, estado_llega, pais_llega, "
				+ "nro_vuelo, modelo, dia_sale, hora_sale, hora_llega, tiempo_estimado, codigo_aero_sale, "
				+ "codigo_aero_llega, nombre_aero_sale, nombre_aero_llega "
				+ "FROM vuelos_disponibles "
				+ "WHERE fecha='"+ fechaVuelo + "' AND " +
						"ciudad_sale='" + origen.getCiudad() + "' AND " +
						"estado_sale='" + origen.getEstado() + "' AND " +
						"pais_sale='" + origen.getPais() + "' AND " +
						"ciudad_llega='" + destino.getCiudad() + "' AND " +
						"estado_sale='" + destino.getEstado() + "' AND " +
						"pais_sale='" + destino.getPais() + "'";
	
		try { 
			Statement stmt = conexion.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			logger.debug("Ejecutando sentencia SQL: " + sql);

			while (rs.next()){
				InstanciaVueloBean inst_vuelo = new InstanciaVueloBeanImpl();
				inst_vuelo.setModelo(rs.getString("modelo"));
				inst_vuelo.setNroVuelo(rs.getString("nro_vuelo"));
				inst_vuelo.setFechaVuelo(rs.getDate("fecha"));
				inst_vuelo.setTiempoEstimado(rs.getTime("tiempo_estimado"));
				inst_vuelo.setDiaSalida(rs.getString("dia_sale"));
				inst_vuelo.setHoraSalida(rs.getTime("hora_sale"));
				inst_vuelo.setHoraLlegada(rs.getTime("hora_llega"));				

				AeropuertoBean aero_origen = new AeropuertoBeanImpl(); 
				aero_origen.setCodigo(rs.getString("codigo_aero_sale"));
				aero_origen.setNombre(rs.getString("nombre_aero_sale"));
				aero_origen.setUbicacion(origen);
				inst_vuelo.setAeropuertoSalida(aero_origen);
				
				AeropuertoBean aero_destino = new AeropuertoBeanImpl();
				aero_destino.setCodigo(rs.getString("codigo_aero_llega"));
				aero_destino.setNombre(rs.getString("nombre_aero_llega"));
				aero_destino.setUbicacion(destino);
				inst_vuelo.setAeropuertoLlegada(aero_destino);
				
				resultado.add(inst_vuelo);				
			}
			
		} catch (SQLException ex) {
			logger.error("SQLException: " + ex.getMessage());
			logger.error("SQLState: " + ex.getSQLState());
			logger.error("VendorError: " + ex.getErrorCode());
			throw new Exception("Error en la conexion con la BD.");
		}
		return resultado;
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
		//ArrayList<DetalleVueloBean> resultado = DAOVuelosDatosPrueba.generarDetalles(vuelo);
		ArrayList<DetalleVueloBean> resultado = new ArrayList<DetalleVueloBean>();		
		String sql = "SELECT precio, clase, asientos_disponibles FROM vuelos_disponibles";		

		try { 
			Statement stmt = conexion.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			logger.debug("Recuperando detalle vuelo: " + sql);

			while (rs.next()){
				DetalleVueloBean dvb = new DetalleVueloBeanImpl();
				dvb.setVuelo(vuelo);
				dvb.setPrecio(rs.getFloat("precio"));
				dvb.setClase(rs.getString("clase"));
				dvb.setAsientosDisponibles(rs.getInt("asientos_disponibles"));
			}
			
		} catch (SQLException ex) {
			logger.error("SQLException: " + ex.getMessage());
			logger.error("SQLState: " + ex.getSQLState());
			logger.error("VendorError: " + ex.getErrorCode());
			throw new Exception("Error en la conexion con la BD.");
		}
		
		return resultado; 
	}
}













