package vuelos.modelo.empleado.beans;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//CLASE IMPLEMENTADA PROVISTA POR LA CATEDRA
//Representa como un objeto información relevante a una reserva
public class ReservaBeanImpl implements Serializable, ReservaBean {

	private static Logger logger = LoggerFactory.getLogger(ReservaBeanImpl.class);
	
	private static final long serialVersionUID = 1L;
	
	private int numero;
	private Date fecha;
	private Date vencimiento;
	private String estado;
	private PasajeroBean pasajero;
	private EmpleadoBean empleado;
	private ArrayList<InstanciaVueloClaseBean> vuelosClase; 
	private boolean idaVuelta;
	
	@Override
	public int getNumero() {
		return numero;
	}
	@Override
	public void setNumero(int numero) {
		this.numero = numero;
	}
	@Override
	public Date getFecha() {
		return fecha;
	}
	@Override
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	@Override
	public Date getVencimiento() {
		return vencimiento;
	}
	@Override
	public void setVencimiento(Date vencimiento) {
		this.vencimiento = vencimiento;
	}
	@Override
	public String getEstado() {
		return estado;
	}
	@Override
	public void setEstado(String estado) {
		this.estado = estado;
	}
	@Override
	public PasajeroBean getPasajero() {
		return pasajero;
	}
	@Override
	public void setPasajero(PasajeroBean pasajero) {
		this.pasajero = pasajero;
	}
	@Override
	public EmpleadoBean getEmpleado() {
		return empleado;
	}
	@Override
	public void setEmpleado(EmpleadoBean empleado) {
		this.empleado = empleado;
	}
	@Override
	public ArrayList<InstanciaVueloClaseBean> getVuelosClase() {
		return vuelosClase;
	}
	@Override
	public void setVuelosClase(ArrayList<InstanciaVueloClaseBean> vuelosClase) {
		this.vuelosClase = vuelosClase;
	}
	@Override
	public boolean esIdaVuelta() {
		return idaVuelta;
	}
	@Override
	public void setEsIdaVuelta(boolean esIdaVuelta) {
		this.idaVuelta = esIdaVuelta;
	}
}
