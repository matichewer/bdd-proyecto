package vuelos.modelo.empleado.dao.datosprueba;

import java.util.ArrayList;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import vuelos.modelo.empleado.beans.AeropuertoBean;
import vuelos.modelo.empleado.beans.AeropuertoBeanImpl;
import vuelos.modelo.empleado.beans.DetalleVueloBean;
import vuelos.modelo.empleado.beans.DetalleVueloBeanImpl;
import vuelos.modelo.empleado.beans.InstanciaVueloBean;
import vuelos.modelo.empleado.beans.InstanciaVueloBeanImpl;
import vuelos.modelo.empleado.beans.UbicacionesBean;
import vuelos.modelo.empleado.beans.UbicacionesBeanImpl;

/*CLASE IMPLEMENTADA PROVISTA POR LA CATEDRA
Contiene datos estáticos de prueba para mostrar en la aplicación parcialemente implementada.
Una vez completada la implementacion esta clase ya no se utilizará. */
public class DAOVuelosDatosPrueba {

	private static Logger logger = LoggerFactory.getLogger(DAOVuelosDatosPrueba.class);
	
	public static ArrayList<InstanciaVueloBean> generarVuelos(Date fechaVuelo) {
		
		DAOUbicacionesDatosPrueba.poblar();		
		UbicacionesBean bsas = DAOUbicacionesDatosPrueba.obtenerUbicacion("bsas");		
		UbicacionesBean chicago = DAOUbicacionesDatosPrueba.obtenerUbicacion("chicago");		
		UbicacionesBean barcelona = DAOUbicacionesDatosPrueba.obtenerUbicacion("barcelona");		
		
		AeropuertoBean ezeiza = new AeropuertoBeanImpl();
		ezeiza.setCodigo("EZE");
		ezeiza.setNombre("Ministro Pistarini");
		ezeiza.setUbicacion(bsas);

		AeropuertoBean ohare = new AeropuertoBeanImpl();
		ohare.setCodigo("ORD");
		ohare.setNombre("O'Hare Intl.");
		ohare.setUbicacion(chicago);
		
		AeropuertoBean bcn = new AeropuertoBeanImpl();
		bcn.setCodigo("BCN");
		bcn.setNombre("Barcelona Intl.");
		bcn.setUbicacion(barcelona);
		
		ArrayList<InstanciaVueloBean> vuelosDisponibles = new ArrayList<InstanciaVueloBean>();
		
		InstanciaVueloBean vuelo1 = new InstanciaVueloBeanImpl();
		vuelo1.setNroVuelo("EZEORD01");
		vuelo1.setModelo("Boeing 747");
		vuelo1.setDiaSalida("Lu");
		vuelo1.setFechaVuelo(new java.sql.Date(fechaVuelo.getDate()));
		vuelo1.setHoraSalida(new java.sql.Time(21, 20, 00));
		vuelo1.setHoraLlegada(new java.sql.Time(9, 49, 00));
		vuelo1.setTiempoEstimado(new java.sql.Time(36, 29, 00));
		vuelo1.setAeropuertoSalida(ezeiza);
		vuelo1.setAeropuertoLlegada(ohare);
		vuelosDisponibles.add(vuelo1);
		
		InstanciaVueloBean vuelo2 = new InstanciaVueloBeanImpl();
		vuelo2.setNroVuelo("EZEBCN01");
		vuelo2.setModelo("Airbus A320");
		vuelo2.setDiaSalida("Ma");
		vuelo2.setFechaVuelo(new java.sql.Date(fechaVuelo.getDate()));
		vuelo2.setHoraSalida(new java.sql.Time(21, 05, 00));
		vuelo2.setHoraLlegada(new java.sql.Time(8, 35, 00));
		vuelo2.setTiempoEstimado(new java.sql.Time(11, 30, 00));
		vuelo2.setAeropuertoSalida(ezeiza);
		vuelo2.setAeropuertoLlegada(bcn);
		vuelosDisponibles.add(vuelo2);

		InstanciaVueloBean vuelo3 = new InstanciaVueloBeanImpl();
		vuelo3.setNroVuelo("BCNORD01");
		vuelo3.setModelo("Boeing 787");
		vuelo3.setDiaSalida("Mi");
		vuelo3.setFechaVuelo(new java.sql.Date(fechaVuelo.getDate()));
		vuelo3.setHoraSalida(new java.sql.Time(12, 28, 00));
		vuelo3.setHoraLlegada(new java.sql.Time(18, 38, 00));
		vuelo3.setTiempoEstimado(new java.sql.Time(6, 10, 00));
		vuelo3.setAeropuertoSalida(bcn);
		vuelo3.setAeropuertoLlegada(ohare);
		vuelosDisponibles.add(vuelo3);
		
		InstanciaVueloBean vuelo4 = new InstanciaVueloBeanImpl();
		vuelo4.setNroVuelo("BCNEZE01");
		vuelo4.setModelo("Airbus A330");
		vuelo4.setDiaSalida("Ju");
		vuelo4.setFechaVuelo(new java.sql.Date(fechaVuelo.getDate()));
		vuelo4.setHoraSalida(new java.sql.Time(8, 20, 00));
		vuelo4.setHoraLlegada(new java.sql.Time(19, 50, 00));
		vuelo4.setTiempoEstimado(new java.sql.Time(11, 30, 00));
		vuelo4.setAeropuertoSalida(bcn);
		vuelo4.setAeropuertoLlegada(ezeiza);
		vuelosDisponibles.add(vuelo4);
		
		return vuelosDisponibles;		
	}
	
	public static ArrayList<DetalleVueloBean> generarDetalles(InstanciaVueloBean vuelo) {
		
		ArrayList<DetalleVueloBean> detallesVuelo = new ArrayList<DetalleVueloBean>();
		DetalleVueloBean detalle1 = new DetalleVueloBeanImpl();
		DetalleVueloBean detalle2 = new DetalleVueloBeanImpl();
		DetalleVueloBean detalle3 = new DetalleVueloBeanImpl();
		detalle1.setVuelo(vuelo);
		detalle2.setVuelo(vuelo);
		detalle3.setVuelo(vuelo);
		
		switch (vuelo.getNroVuelo()) {
			case "EZEORD01":
				detalle1.setClase("Primera");
				detalle1.setPrecio(82350);				
				detalle1.setAsientosDisponibles(4);

				detalle2.setClase("Turista");
				detalle2.setPrecio(41876);
				detalle2.setAsientosDisponibles(16);

				detallesVuelo.add(detalle1);
				detallesVuelo.add(detalle2);
				break;
			case "EZEBCN01": 
				detalle1.setClase("Ejecutiva");
				detalle1.setPrecio(67252);
				detalle1.setAsientosDisponibles(8);

				detalle2.setClase("Primera");
				detalle2.setPrecio(39650);
				detalle2.setAsientosDisponibles(12);

				detalle3.setClase("Turista");
				detalle3.setPrecio(28670);
				detalle3.setAsientosDisponibles(23);

				detallesVuelo.add(detalle1);
				detallesVuelo.add(detalle2);
				detallesVuelo.add(detalle3);
				break;
			case "BCNORD01":
				detalle1.setClase("Business");
				detalle1.setPrecio(16470);
				detalle1.setAsientosDisponibles(29);

				detalle2.setClase("Economy");
				detalle2.setPrecio(8845);
				detalle2.setAsientosDisponibles(34);

				detallesVuelo.add(detalle1);
				detallesVuelo.add(detalle2);
				break;
			case "BCNEZE01":
				detalle1.setClase("First Class");
				detalle1.setPrecio(99675);
				detalle1.setAsientosDisponibles(0);

				detalle2.setClase("Premium Economy");
				detalle2.setPrecio(51850);
				detalle2.setAsientosDisponibles(3);

				detalle3.setClase("Economy");
				detalle3.setPrecio(32025);
				detalle3.setAsientosDisponibles(27);

				detallesVuelo.add(detalle1);
				detallesVuelo.add(detalle2);
				detallesVuelo.add(detalle3);				
				break;
		}
		return detallesVuelo;
	}	
}
