package vuelos.controlador;

import java.util.Date;

import vuelos.modelo.empleado.beans.DetalleVueloBean;
import vuelos.modelo.empleado.beans.InstanciaVueloBean;
import vuelos.modelo.empleado.beans.UbicacionesBean;

public interface ControladorEmpleado extends ControladorVuelo {

	@Override
	public void cerrarSesion();

	@Override
	public void salirAplicacion();

	@Override
	public void ejecutar();
	
	
	/*
	 * Metodos que informan al controlador que debe mostrar una ventana en particular 
	 */
	public void mostrarVuelosDisponibles();
	
	/*
	 * Métodos para realizar las operaciones
	 */
	
	/**
	 * Informa al controlador que se solicita mostrar información sobre los vuelos disponibles teniendo en cuenta las
	 * siguientes restricciones.
	 * 
	 * @param esIdaVuelta (requerido) indica si quiere ida y vuelta (true) o ida solo (false)
	 * @param fechaDesde (requerido) vuelos que salgan en esta fecha
	 * @param fechaHasta si es ida y vuelta esta fecha se utiliza para la vuelta (solo será requerido si es ida y vuelta)
	 * @param origen (requerido) ubicación de origen para el vuelo de ida
	 * @param destino (requerido) ubicación destino para el vuelo de ida (será origen del vuelo de vuelta)
	 */
	public void buscarVuelosDisponibles(boolean esIdaVuelta, Date fechaDesde, Date fechaHasta, UbicacionesBean origen,
			UbicacionesBean destino);	
	
	/**
	 * Informa al controlador que se seleccionó un vuelo y se requiere obtener el detalle del mismo.
	 * La selección ocurre para un viaje de ida solamente.
	 * 
	 * @param vueloSeleccion
	 */
	public void cambioSeleccionVueloViajeSoloIda(InstanciaVueloBean vueloSeleccion);

	/**
	 * Informa al controlador que se seleccionó un vuelo y se requiere obtener el detalle del mismo 
	 * La selección ocurre para un viaje de ida y vuelta sobre el vuelo de ida.
	 * 
	 * @param vueloSeleccion
	 */
	public void cambioSeleccionVueloIdaViajeIdaVuelta(InstanciaVueloBean vueloSeleccion);
	
	/**
	 * Informa al controlador que se seleccionó un vuelo y se requiere obtener el detalle del mismo
	 * La selección ocurre para un viaje de ida y vuelta sobre el vuelo de vuelta.
	 * 
	 * @param vueloSeleccion
	 */
	public void cambioSeleccionVueloVueltaViajeIdaVuelta(InstanciaVueloBean vueloSeleccion);

	/**
	 * Informa al controlador que se seleccionó una clase.
	 * La selección ocurre para un viaje de ida solamente.
	 * 
	 * @param detalle
	 */
	public void cambioSeleccionDetalleVueloViajeSoloIda(DetalleVueloBean detalle);

	/**
	 * Informa al controlador que se seleccionó una clase. 
	 * La selección ocurre para un viaje de ida y vuelta sobre el vuelo de ida.
	 * 
	 * @param detalle
	 */
	public void cambioSeleccionDetalleVueloIdaViajeIdaVuelta(DetalleVueloBean detalle);
	
	/**
	 * Informa al controlador que se seleccionó una clase.
	 * La selección ocurre para un viaje de ida y vuelta sobre el vuelo de vuelta.
	 * 
	 * @param detalle
	 */
	public void cambioSeleccionDetalleVueloVueltaViajeIdaVuelta(DetalleVueloBean detalle);

	/**
	 * Quita todos las selecciones realizadas
	 */
	public void limpiarSeleccion();
	
	/**
	 * Informa al controlador que se ha realizado la seleccion de vuelos que desea reservar. 
	 * 
	 * Internamente el controlador guarda los objetos seleccionados.
	 *
	 */
	public void vueloSeleccionado();

	/**
	 * Informa al controlador que se desea buscar los datos del pasajero. 
	 * 
	 * @param tipoDoc es un string con el tipo de documento
	 * @param numero es un string con el codigo de documento
	 */
	public void buscarPasajero(String tipoDoc, String numero); 
	
	/**
	 * Informa al controlador que el usuario desea volver a la pantalla de selección de 
	 * vuelos. 
	 * 
	 * Internamente elimina la selección de pasajero realizada anteriormente.
	 */
	public void volverASeleccionVuelo();
	
	/**
	 * Informa al controlador que el usuario desea confirmar los datos ingresados y realizar la reserva.
	 * 
	 * Los datos que se utilizan para la reserva son los internos que dispone el controlador.
	 */
	public void reservar();

	/** 
	 * Ventana informa al controlador que hubo algun error en los datos que se esperaban para mostrar
	 * 
	 * @param msj
	 */
	void errorEnReserva(String msj);
	
	
}
