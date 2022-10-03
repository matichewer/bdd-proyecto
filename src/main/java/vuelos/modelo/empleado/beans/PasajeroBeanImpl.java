package vuelos.modelo.empleado.beans;

import java.io.Serializable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//CLASE IMPLEMENTADA PROVISTA POR LA CATEDRA
//Representa como un objeto una tupla de la tabla "pasajeros"
public class PasajeroBeanImpl implements Serializable, PasajeroBean  {

	private static Logger logger = LoggerFactory.getLogger(PasajeroBeanImpl.class);
	
	private static final long serialVersionUID = 1L;

	private String tipoDocumento;
	private int nroDocumento;
	private String apellido;
	private String nombre;
	private String direccion;	
	private String telefono;
	private String nacionalidad;
	
	@Override
	public String toString() {
		return "PasajeroBeanImpl [tipoDocumento=" + tipoDocumento + ", nroDocumento=" + nroDocumento + ", apellido="
				+ apellido + ", nombre=" + nombre + ", direccion=" + direccion + ", telefono=" + telefono
				+ ", nacionalidad=" + nacionalidad + "]";
	}
	
	@Override
	public String getTipoDocumento() {
		return tipoDocumento;
	}
	
	@Override
	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}
	
	@Override
	public int getNroDocumento() {
		return nroDocumento;
	}
	
	@Override
	public void setNroDocumento(int nroDocumento) {
		this.nroDocumento = nroDocumento;
	}
	
	@Override
	public String getApellido() {
		return apellido;
	}
	
	@Override
	public void setApellido(String apellido) {
		this.apellido = apellido;
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
	public String getDireccion() {
		return direccion;
	}
	
	@Override
	public void setDireccion(String direccion) {
		this.direccion = direccion;
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
	public String getNacionalidad() {
		return nacionalidad;
	}
	
	@Override
	public void setNacionalidad(String nacionalidad) {
		this.nacionalidad = nacionalidad;
	}
		
}
