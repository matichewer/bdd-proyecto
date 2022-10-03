package vuelos.modelo.empleado.beans;

public interface UbicacionesBean {

	/**
	 * @return El pais
	 */
	String getPais();

	/**
	 * @param pais a asignar
	 */
	void setPais(String pais);

	/**
	 * @return El Estado
	 */
	String getEstado();

	/**
	 * @param Estado a asignar
	 */
	void setEstado(String estado);

	/**
	 * @return La ciudad
	 */
	String getCiudad();

	/**
	 * @param Ciudad a asignar
	 */
	void setCiudad(String ciudad);

	/**
	 * @return Huso horario
	 */
	int getHuso();

	/**
	 * @param nroCliente the nroCliente to set
	 */
	void setHuso(int huso);
	
	/**
	 * Permite mostrar en los combos la propiedad deseada.
	 */
	@Override
	String toString();
}
