package vuelos.modelo.empleado.dao;

import java.sql.Connection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import vuelos.modelo.empleado.beans.PasajeroBean;
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
		PasajeroBean pasajero = DAOPasajeroDatosPrueba.obtenerPasajero(nroDoc);
				
		logger.info("El DAO retorna al pasajero {} {}", pasajero.getApellido(), pasajero.getNombre());
		
		return pasajero;
	    // Fin datos estáticos de prueba. 
		
	
	}
	
	

}
