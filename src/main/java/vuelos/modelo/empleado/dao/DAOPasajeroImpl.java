package vuelos.modelo.empleado.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import vuelos.modelo.empleado.beans.PasajeroBean;
import vuelos.modelo.empleado.beans.PasajeroBeanImpl;
import vuelos.modelo.empleado.dao.datosprueba.DAOPasajeroDatosPrueba;

public class DAOPasajeroImpl implements DAOPasajero {

	private static Logger logger = LoggerFactory.getLogger(DAOPasajeroImpl.class);
	
	private static final long serialVersionUID = 1L;

	//conexión para acceder a la Base de Datos
	private Connection conexion;
	
	public DAOPasajeroImpl(Connection conexion) {
		this.conexion = conexion;
	}


	@Override
	public PasajeroBean recuperarPasajero(String tipoDoc, int nroDoc) throws Exception {
		/**
		 * TODO (parte 2) Deberá recuperar de la B.D. los datos de un pasajero que tenga el tipo de documento y 
		 *      numero pasados como parámetro y devolver los datos en un objeto EmpleadoBean. 
		 *      Si no existe el pasajero deberá retornar null y si ocurre algun error deberá 
		 *      generar y propagar una excepción.
		 *
		 *      Nota: para acceder a la B.D. utilice la propiedad "conexion" que ya tiene una conexión
		 *      establecida con el servidor de B.D. (inicializada en el constructor DAOPasajeroImpl(...)). 
		 */		

		
		/*
		 * Datos estáticos de prueba. Quitar y reemplazar por código que recupera los datos reales.  
		 */	
		PasajeroBean pasajero = null;	

		if (tipoDoc.isEmpty() || tipoDoc == null) {
			logger.debug("tipoDoc vacio/nulo");
		} else {			
			try { 
				String sql = "SELECT * FROM pasajeros WHERE doc_tipo=? AND doc_nro=?";
				PreparedStatement stmt = conexion.prepareStatement(sql);				
				stmt.setString(1, tipoDoc);
				stmt.setInt(2, nroDoc);			
				ResultSet rs = stmt.executeQuery();			
					
				if (rs.next()){
					pasajero = new PasajeroBeanImpl();
					pasajero.setTipoDocumento(tipoDoc);
					pasajero.setNroDocumento(nroDoc);
					pasajero.setApellido(rs.getString("apellido"));
					pasajero.setNombre(rs.getString("nombre"));
					pasajero.setDireccion(rs.getString("direccion"));
					pasajero.setTelefono(rs.getString("telefono"));
					pasajero.setNacionalidad(rs.getString("nacionalidad"));
					
					logger.debug("Pasajero recuperado: tipoDoc = " + tipoDoc + " y nroDoc = " + nroDoc);
				} else {
					logger.debug("Pasajero no encontrado: tipoDoc = " + tipoDoc + " y nroDoc = " + nroDoc);
				}				
				stmt.close();
				rs.close();
				
			} catch (SQLException ex) {
				logger.error("SQLException: " + ex.getMessage());
				logger.error("SQLState: " + ex.getSQLState());
				logger.error("VendorError: " + ex.getErrorCode());
		        throw new Exception("Error en la conexion con la BD.");
			}
		}
		return pasajero;
	}
}
