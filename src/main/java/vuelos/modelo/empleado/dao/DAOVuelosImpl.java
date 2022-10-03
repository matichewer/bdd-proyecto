package vuelos.modelo.empleado.dao;

import java.sql.Connection;
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
	public ArrayList<InstanciaVueloBean> recuperarVuelosDisponibles(Date fechaVuelo, UbicacionesBean origen, UbicacionesBean destino)  throws Exception {
		/** 
		 * TODO Debe retornar una lista de vuelos disponibles para ese día con origen y destino según los parámetros. 
		 *      Debe propagar una excepción si hay algún error en la consulta.    
		 *      
		 *      Nota: para acceder a la B.D. utilice la propiedad "conexion" que ya tiene una conexión
		 *      establecida con el servidor de B.D. (inicializada en el constructor DAOVuelosImpl(...)).  
		 */
		//Datos estáticos de prueba. Quitar y reemplazar por código que recupera los datos reales.
		ArrayList<InstanciaVueloBean> resultado = DAOVuelosDatosPrueba.generarVuelos(fechaVuelo);  
		
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
