package vuelos.modelo.empleado.dao.datosprueba;

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import vuelos.modelo.empleado.beans.UbicacionesBean;
import vuelos.modelo.empleado.beans.UbicacionesBeanImpl;

/*CLASE IMPLEMENTADA PROVISTA POR LA CATEDRA
  Contiene datos estáticos de prueba para mostrar en la aplicación parcialemente implementada.
  Una vez completada la implementacion esta clase ya no se utilizará. */
public class DAOUbicacionesDatosPrueba {

	private static Logger logger = LoggerFactory.getLogger(DAOUbicacionesDatosPrueba.class);
	
	private static HashMap<String,UbicacionesBean> datos = new HashMap<String,UbicacionesBean>();
	
	public static void poblar() {
		UbicacionesBean bsas = new UbicacionesBeanImpl();		
		bsas.setPais("Argentina");
		bsas.setEstado("CABA");
		bsas.setCiudad("Buenos Aires");
		bsas.setHuso(3);

		UbicacionesBean chicago = new UbicacionesBeanImpl();		
		chicago.setPais("USA");
		chicago.setEstado("Illinois");
		chicago.setCiudad("Chicago");
		chicago.setHuso(5);
		
		UbicacionesBean barcelona = new UbicacionesBeanImpl();		
		barcelona.setPais("España");
		barcelona.setEstado("Cataluña");
		barcelona.setCiudad("Barcelona");
		barcelona.setHuso(-3);
		
		UbicacionesBean cordoba = new UbicacionesBeanImpl();		
		cordoba.setPais("Argentina");
		cordoba.setEstado("Buenos Aires");
		cordoba.setCiudad("Córdoba");
		cordoba.setHuso(3);
		
		datos.clear();
		datos.put("bsas", bsas);
		datos.put("chicago", chicago);
		datos.put("barcelona", barcelona);	
		datos.put("cordoba", cordoba);
	}
	
	public static UbicacionesBean obtenerUbicacion(String key) {
		return datos.get(key);
	}
	
}
