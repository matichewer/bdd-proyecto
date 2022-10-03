package vuelos.modelo;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import vuelos.utils.Conexion;


public class ModeloImpl implements Modelo {
	
	private static Logger logger = LoggerFactory.getLogger(ModeloImpl.class);	

	protected Connection conexion = null;

     /**
	 * TODO Inicializar la propiedad "conexión" con una conexion establecida con el servidor de B.D.
	 *      utilizando el método estático Connection getConection(username, password) de la clase vuelos.util.Conexion.  
	 *      Retornar verdadero si se pudo establecer la conexión (conexion!= null) y falso en caso contrario
	 */
	@Override
	public boolean conectar(String username, String password) {
		logger.info("Se establece la conexión a la BD.");
		
		// Código de prueba. Quitar y reemplazar por código que establece y retorna la conexión.             
    	   return true;
    	// fin código de prueba   
	}

	@Override
	public void desconectar() {
		logger.info("Se cierra la conexión a la BD.");
		Conexion.closeConnection(this.conexion);		
	}

	/**
	 * TODO Utilizando la propiedad "conexión" ejecuta la consulta SQL recibida como parámetro y 
	 *      retorna el resultado como un objeto ResulSet.
	 *      Si se produce una excepción retorna null. El codigo para manejar la excepción ya 
	 *      se encuentra implementado, solo se registran los errores en el log.     
	 */
	@Override
	public ResultSet consulta(String sql) 
	{
		logger.info("Se intenta realizar la siguiente consulta {}",sql);
		ResultSet rs= null;		
		/* Descomentar y completar el codigo dentro de try{...} para realizar la consulta SQL 
		try
		{       
			...
		}
		catch (SQLException ex){
		   logger.error("SQLException: " + ex.getMessage());
		   logger.error("SQLState: " + ex.getSQLState());
		   logger.error("VendorError: " + ex.getErrorCode());				   
		}	
		*/
		return rs;
	}	
	
	/**
	 * TODO Utilizando la propiedad "conexión" ejecuta la sentencia de 
	 *      actualización (i.e insert, delete , update, ...) SQL recibida como parámetro        
	 */
	@Override
	public void actualizacion (String sql)
	{
		logger.info("Se intenta realizar la siguiente actualizacion {}",sql);
				
		/* Descomentar y completar codigo dentro de try{...} para realizar la sentencia de actualización SQL 
		try
		{       
			...
		}
		catch (SQLException ex){
		   logger.error("SQLException: " + ex.getMessage());
		   logger.error("SQLState: " + ex.getSQLState());
		   logger.error("VendorError: " + ex.getErrorCode());				   
		}	
		*/		
	}	
}
