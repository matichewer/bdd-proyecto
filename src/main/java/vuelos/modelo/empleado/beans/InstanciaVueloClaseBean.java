package vuelos.modelo.empleado.beans;

public interface InstanciaVueloClaseBean {

	InstanciaVueloBean getVuelo();

	DetalleVueloBean getClase();

	void setVuelo(InstanciaVueloBean vuelo);

	void setClase(DetalleVueloBean clase);

}