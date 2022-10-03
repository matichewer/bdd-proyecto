package vuelos.modelo.empleado.beans;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Time;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//CLASE IMPLEMENTADA PROVISTA POR LA CATEDRA
//Representa como un objeto informacion de una instancia de vuelo  
public class InstanciaVueloBeanImpl implements Serializable, InstanciaVueloBean  {

	private static final long serialVersionUID = 1L;

	private static Logger logger = LoggerFactory.getLogger(InstanciaVueloBeanImpl.class);

	private String nroVuelo;
	private String modelo;	
	private String diaSalida;	
	private Time horaSalida;	
	private Time horaLlegada;	
	private Time tiempoEstimado;
	private Date fechaVuelo;	
	private AeropuertoBean aeropuertoSalida;	
	private AeropuertoBean aeropuertoLlegada;
	
	@Override
	public String getNroVuelo() {
		return nroVuelo;
	}
	@Override
	public void setNroVuelo(String nroVuelo) {
		this.nroVuelo = nroVuelo;
	}
	@Override
	public String getModelo() {
		return modelo;
	}
	@Override
	public void setModelo(String modelo) {
		this.modelo = modelo;
	}
	@Override
	public String getDiaSalida() {
		return diaSalida;
	}
	@Override
	public void setDiaSalida(String diaSalida) {
		this.diaSalida = diaSalida;
	}
	@Override
	public Time getHoraSalida() {
		return horaSalida;
	}
	@Override
	public void setHoraSalida(Time horaSalida) {
		this.horaSalida = horaSalida;
	}
	@Override
	public Time getHoraLlegada() {
		return horaLlegada;
	}
	@Override
	public void setHoraLlegada(Time horaLlegada) {
		this.horaLlegada = horaLlegada;
	}
	@Override
	public Time getTiempoEstimado() {
		return tiempoEstimado;
	}
	@Override
	public void setTiempoEstimado(Time tiempoEstimado) {
		this.tiempoEstimado = tiempoEstimado;
	}
	@Override
	public Date getFechaVuelo() {
		return fechaVuelo;
	}
	@Override
	public void setFechaVuelo(Date fechaVuelo) {
		this.fechaVuelo = fechaVuelo;
	}
	@Override
	public AeropuertoBean getAeropuertoSalida() {
		return aeropuertoSalida;
	}
	@Override
	public void setAeropuertoSalida(AeropuertoBean aeropuertoSalida) {
		this.aeropuertoSalida = aeropuertoSalida;
	}
	@Override
	public AeropuertoBean getAeropuertoLlegada() {
		return aeropuertoLlegada;
	}
	@Override
	public void setAeropuertoLlegada(AeropuertoBean aeropuertoLlegada) {
		this.aeropuertoLlegada = aeropuertoLlegada;
	}
	
}
