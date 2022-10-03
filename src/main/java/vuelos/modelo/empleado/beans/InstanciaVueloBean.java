package vuelos.modelo.empleado.beans;

import java.sql.Date;
import java.sql.Time;

public interface InstanciaVueloBean {

	String getNroVuelo();

	void setNroVuelo(String nroVuelo);

	String getModelo();

	void setModelo(String modelo);

	String getDiaSalida();

	void setDiaSalida(String diaSalida);

	Time getHoraSalida();

	void setHoraSalida(Time horaSalida);

	Time getHoraLlegada();

	void setHoraLlegada(Time horaLlegada);

	Time getTiempoEstimado();

	void setTiempoEstimado(Time tiempoEstimado);

	Date getFechaVuelo();

	void setFechaVuelo(Date fechaVuelo);

	AeropuertoBean getAeropuertoSalida();

	void setAeropuertoSalida(AeropuertoBean aeropuertoSalida);

	AeropuertoBean getAeropuertoLlegada();

	void setAeropuertoLlegada(AeropuertoBean aeropuertoLlegada);

}