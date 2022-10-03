package vuelos.modelo.empleado.beans;

import java.sql.Date;
import java.util.ArrayList;

public interface ReservaBean {
	
	public int getNumero();

	public void setNumero(int numero);
	
	// ATENCION: es importante setear este parametro al crear el objeto 
	boolean esIdaVuelta();

	void setEsIdaVuelta(boolean esIdaVuelta);

	public Date getFecha();

	public void setFecha(Date fecha);

	public Date getVencimiento();

	public void setVencimiento(Date vencimiento);

	public String getEstado();

	public void setEstado(String estado);

	public PasajeroBean getPasajero();

	public void setPasajero(PasajeroBean pasajero);

	public EmpleadoBean getEmpleado();

	public void setEmpleado(EmpleadoBean empleado);

	/** 
	 * Obtiene la lista de instancias de vuelos y la clase asociada para la reserva
	 *  
	 * @return
	 */
	public ArrayList<InstanciaVueloClaseBean> getVuelosClase();

	public void setVuelosClase(ArrayList<InstanciaVueloClaseBean> vuelosClase);

}