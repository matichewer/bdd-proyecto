package vuelos;

import java.awt.EventQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import vuelos.controlador.ControladorLogin;
import vuelos.controlador.ControladorLoginImpl;
import vuelos.modelo.ModeloLogin;
import vuelos.modelo.ModeloLoginImpl;
import vuelos.vista.login.VentanaLogin;
import vuelos.vista.login.VentanaLoginImpl;


// CLASE IMPLEMENTADA PROVISTA POR LA CATEDRA
public class Vuelo {

	private static Logger logger = LoggerFactory.getLogger(Vuelo.class);
	
	/**
	 * Método para iniciar la aplicación
	 * 
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					
					logger.debug("Se inicia la aplicación.");
					
					ModeloLogin modelo = new ModeloLoginImpl();  
					VentanaLogin ventana = new VentanaLoginImpl();
					@SuppressWarnings("unused")
					ControladorLogin controlador = new ControladorLoginImpl(ventana, modelo);
					
				} catch (Exception e) {
					logger.error(e.getMessage());
					e.printStackTrace();
				}
			}
		});
	}
}
