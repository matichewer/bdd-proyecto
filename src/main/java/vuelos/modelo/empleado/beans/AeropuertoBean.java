package vuelos.modelo.empleado.beans;

public interface AeropuertoBean {

	String getCodigo();

	void setCodigo(String codigo);

	String getNombre();

	void setNombre(String nombre);

	String getTelefono();

	void setTelefono(String telefono);

	String getDireccion();

	void setDireccion(String direccion);

	UbicacionesBean getUbicacion();

	void setUbicacion(UbicacionesBean ubicacion);

}