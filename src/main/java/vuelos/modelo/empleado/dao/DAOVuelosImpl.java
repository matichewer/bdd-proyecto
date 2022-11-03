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

import vuelos.utils.Fechas;

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
		
		String sql = "SELECT DISTINCT fecha, ciudad_sale, estado_sale, pais_sale, ciudad_llega, estado_llega, pais_llega, "
				+ "nro_vuelo, modelo, dia_sale, hora_sale, hora_llega, tiempo_estimado, codigo_aero_sale, "
				+ "codigo_aero_llega, nombre_aero_sale, nombre_aero_llega, "
				+ "aero_sale.telefono AS aero_sale_telefono, aero_sale.direccion AS aero_sale_direccion, "
				+ "aero_llega.telefono AS aero_llega_telefono, aero_llega.direccion AS aero_llega_direccion "
				+ "FROM vuelos_disponibles "
				+ "JOIN aeropuertos AS aero_sale ON aero_sale.codigo=codigo_aero_sale "
				+ "JOIN aeropuertos AS aero_llega ON aero_llega.codigo=codigo_aero_llega "
				+ "WHERE fecha='"+ Fechas.convertirDateADateSQL(fechaVuelo) + "' AND " +
						"ciudad_sale='" + origen.getCiudad() + "' AND " +
						"estado_sale='" + origen.getEstado() + "' AND " +
						"pais_sale='" + origen.getPais() + "' AND " +
						"ciudad_llega='" + destino.getCiudad() + "' AND " +
						"estado_llega='" + destino.getEstado() + "' AND " +
						"pais_llega='" + destino.getPais() + "'";
	

		ArrayList<InstanciaVueloBean> resultado = new ArrayList<InstanciaVueloBean>();
		logger.debug("Ejecutando sentencia SQL: " + sql);
		try { 
			Statement stmt = conexion.createStatement();
			ResultSet rs = stmt.executeQuery(sql);

			ArrayList<AeropuertoBean> lista_aeropuertos = new ArrayList<AeropuertoBean>();
			while (rs.next()){
				InstanciaVueloBean inst_vuelo = new InstanciaVueloBeanImpl();
				inst_vuelo.setModelo(rs.getString("modelo"));
				inst_vuelo.setNroVuelo(rs.getString("nro_vuelo"));
				inst_vuelo.setFechaVuelo(rs.getDate("fecha"));
				inst_vuelo.setTiempoEstimado(rs.getTime("tiempo_estimado"));
				inst_vuelo.setDiaSalida(rs.getString("dia_sale"));
				inst_vuelo.setHoraSalida(rs.getTime("hora_sale"));
				inst_vuelo.setHoraLlegada(rs.getTime("hora_llega"));				

				
				AeropuertoBean aero_sale;
				String codigo_aero_sale = rs.getString("codigo_aero_sale");
				boolean encontre = false;
				for (int i=0; !encontre && i<lista_aeropuertos.size(); i++) {
					aero_sale = lista_aeropuertos.get(i);
					if(aero_sale.getCodigo().equals(codigo_aero_sale)) {
						inst_vuelo.setAeropuertoSalida(aero_sale);
						encontre = true;
					}
				}
				if (!encontre){
					aero_sale = new AeropuertoBeanImpl(); 	
					aero_sale.setCodigo(codigo_aero_sale);
					aero_sale.setNombre(rs.getString("nombre_aero_sale"));
					aero_sale.setUbicacion(origen);
					aero_sale.setDireccion(rs.getString("aero_sale_direccion"));
					aero_sale.setTelefono(rs.getString("aero_sale_telefono"));
					inst_vuelo.setAeropuertoSalida(aero_sale);
					lista_aeropuertos.add(aero_sale);
				}
				

				AeropuertoBean aero_llega;
				String codigo_aero_llega = rs.getString("codigo_aero_llega");
				encontre = false;
				for (int i=0; !encontre && i<lista_aeropuertos.size(); i++) {
					aero_llega = lista_aeropuertos.get(i);
					if(aero_llega.getCodigo().equals(codigo_aero_llega)) {
						inst_vuelo.setAeropuertoLlegada(aero_llega);
						encontre = true;
					}
				}
				if (!encontre){
					aero_llega = new AeropuertoBeanImpl(); 	
					aero_llega.setCodigo(codigo_aero_llega);
					aero_llega.setNombre(rs.getString("nombre_aero_llega"));
					aero_llega.setUbicacion(destino);
					aero_llega.setDireccion("aero_llega_direccion");
					aero_llega.setTelefono("aero_llega_telefono");
					inst_vuelo.setAeropuertoLlegada(aero_llega);
					lista_aeropuertos.add(aero_llega);
				}
				
				resultado.add(inst_vuelo);				
			}
			
			stmt.close();
			rs.close();
			
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
		
		String nroVuelo = vuelo.getNroVuelo();
		Date fechaVuelo = Fechas.convertirDateADateSQL(vuelo.getFechaVuelo());


		ArrayList<DetalleVueloBean> resultado = new ArrayList<DetalleVueloBean>();		
		String sql = "SELECT precio, clase, asientos_disponibles " +
					 "FROM vuelos_disponibles " +
					 "WHERE nro_vuelo='" + nroVuelo + 
					 "' AND fecha='" + fechaVuelo + "'";
		
		logger.debug("Recuperando detalle vuelo: " + sql);	
		
		try { 
			Statement stmt = conexion.createStatement();
			ResultSet rs = stmt.executeQuery(sql);

			while (rs.next()){
				DetalleVueloBean dvb = new DetalleVueloBeanImpl();
				dvb.setVuelo(vuelo);
				dvb.setPrecio(rs.getFloat("precio"));
				dvb.setClase(rs.getString("clase"));
				dvb.setAsientosDisponibles(rs.getInt("asientos_disponibles"));
				resultado.add(dvb);
			}
			
			stmt.close();
			rs.close();
			
		} catch (SQLException ex) {
			logger.error("SQLException: " + ex.getMessage());
			logger.error("SQLState: " + ex.getSQLState());
			logger.error("VendorError: " + ex.getErrorCode());
			throw new Exception("Error en la conexion con la BD.");
		}
		
		return resultado; 
	}
}
