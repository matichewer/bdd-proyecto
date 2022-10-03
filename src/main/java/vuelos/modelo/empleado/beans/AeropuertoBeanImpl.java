package vuelos.modelo.empleado.beans;

//CLASE IMPLEMENTADA PROVISTA POR LA CATEDRA
//Representa como un objeto una tupla de la tabla "aeropuertos"
public class AeropuertoBeanImpl implements AeropuertoBean {
	

	private String codigo;
	private String nombre;
	private String telefono;
	private String direccion;
	private UbicacionesBean ubicacion;
	
	@Override
	public String getCodigo() {
		return codigo;
	}
	@Override
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	@Override
	public String getNombre() {
		return nombre;
	}
	@Override
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	@Override
	public String getTelefono() {
		return telefono;
	}
	@Override
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	@Override
	public String getDireccion() {
		return direccion;
	}
	@Override
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	@Override
	public UbicacionesBean getUbicacion() {
		return ubicacion;
	}
	@Override
	public void setUbicacion(UbicacionesBean ubicacion) {
		this.ubicacion = ubicacion;
	}

	@Override
	public String toString() {
		return "[" + codigo + "] " + nombre;
	}
}
