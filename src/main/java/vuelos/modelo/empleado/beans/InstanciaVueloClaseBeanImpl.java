package vuelos.modelo.empleado.beans;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//CLASE IMPLEMENTADA PROVISTA POR LA CATEDRA
public class InstanciaVueloClaseBeanImpl implements InstanciaVueloClaseBean {
	
	private static final long serialVersionUID = 1L;
	
	private static Logger logger = LoggerFactory.getLogger(InstanciaVueloClaseBeanImpl.class);
	
	private InstanciaVueloBean vuelo;
	private DetalleVueloBean clase;	

	@Override
	public InstanciaVueloBean getVuelo() {
		return vuelo;
	}
	
	@Override
	public DetalleVueloBean getClase() {
		return clase;
	}
	
	@Override
	public void setVuelo(InstanciaVueloBean vuelo) {
		this.vuelo = vuelo;
	}
	
	@Override
	public void setClase(DetalleVueloBean clase) {
		this.clase = clase;
	}	
	
}
