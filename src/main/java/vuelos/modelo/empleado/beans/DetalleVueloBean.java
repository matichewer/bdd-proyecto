package vuelos.modelo.empleado.beans;

public interface DetalleVueloBean {

	InstanciaVueloBean getVuelo();

	void setVuelo(InstanciaVueloBean vuelo);

	String getClase();
	
	void setClase(String clase);
	
	float getPrecio();

	void setPrecio(float precio);	

	int getAsientosDisponibles();

	void setAsientosDisponibles(int asientosDisponibles);

}
