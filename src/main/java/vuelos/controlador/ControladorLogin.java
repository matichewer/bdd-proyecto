package vuelos.controlador;

public interface ControladorLogin { 

	/**
	 * Informa al controlador que se desea ingresar con un usuario con rol EMPLEADO
	 * 
	 * @param username
	 * @param password
	 */
	public void ingresarComoEmpleado(String legajo, char[] password);
	
}
