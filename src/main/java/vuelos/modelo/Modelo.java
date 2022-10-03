package vuelos.modelo;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface Modelo {

	/**
	 * Intenta realizar la conexion a la BD con el par (username, password)
	 * 
	 * @param username
	 * @param password
	 * @return verdadero si pudo conectar, falso en caso contrario.
	 */
	public boolean conectar(String username, String password);
	
	public void desconectar();
	
	/**
	  * Método encargado de realizar una consulta SQL recibida como parámetro.
	  */
	public ResultSet consulta(String sql);
	
	/**
	  * Método encargado de ejecutar una actualizacion en la base de datos con la sentencia SQL recibida por parámetro
	  */
	public void actualizacion (String sql);


}
