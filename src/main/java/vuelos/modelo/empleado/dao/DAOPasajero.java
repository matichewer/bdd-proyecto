package vuelos.modelo.empleado.dao;

import vuelos.modelo.empleado.beans.PasajeroBean;

public interface DAOPasajero {

	/**
	 * Recupera el pasajero que tenga un documento que se corresponda con los parámetros recibidos.  
	 *  
	 * @param tipoDoc
	 * @param nroDoc
	 * @return El pasajero correspodiente al tipoDoc y nroDoc. Si no existe retorna null.
	 * @throws Exception Si no existe dicho cliente o hay un error de conexión.
	 */
	public PasajeroBean recuperarPasajero(String tipoDoc, int nroDoc) throws Exception;

}
