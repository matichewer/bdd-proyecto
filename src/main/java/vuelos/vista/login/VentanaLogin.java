package vuelos.vista.login;

import vuelos.controlador.ControladorLogin;
import vuelos.vista.Ventana;

public interface VentanaLogin extends Ventana {

	/**
	 * Informa a la vista quien es su controlador de Login
	 * 
	 * @param controlador
	 */
	public void registrarControlador(ControladorLogin controlador);
		
	
}
