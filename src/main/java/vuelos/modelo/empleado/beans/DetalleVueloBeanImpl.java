package vuelos.modelo.empleado.beans;

//CLASE IMPLEMENTADA PROVISTA POR LA CATEDRA
//Representa como un objeto información relevante a una clase brindada por una instancia de vuelo
public class DetalleVueloBeanImpl implements DetalleVueloBean {
	
	private InstanciaVueloBean vuelo;
	private String clase;
	private float precio;	
	private int asientosDisponibles;
	
	@Override
	public InstanciaVueloBean getVuelo() {
		return vuelo;
	}
	@Override
	public void setVuelo(InstanciaVueloBean vuelo) {
		this.vuelo = vuelo;
	}
	@Override
	public float getPrecio() {
		return precio;
	}
	@Override
	public void setPrecio(float precio) {
		this.precio = precio;
	}
	@Override
	public String getClase() {
		return clase;
	}
	@Override
	public void setClase(String clase) {
		this.clase = clase;
	}
	@Override
	public int getAsientosDisponibles() {
		return asientosDisponibles;
	}
	@Override
	public void setAsientosDisponibles(int asientosDisponibles) {
		this.asientosDisponibles = asientosDisponibles;
	}
	
}
