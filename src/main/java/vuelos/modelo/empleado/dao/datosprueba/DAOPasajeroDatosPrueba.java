package vuelos.modelo.empleado.dao.datosprueba;

import vuelos.modelo.empleado.beans.PasajeroBean;

import vuelos.modelo.empleado.beans.PasajeroBeanImpl;
/*CLASE IMPLEMENTADA PROVISTA POR LA CATEDRA
Contiene datos estáticos de prueba para mostrar en la aplicación parcialemente implementada.
Una vez completada la implementacion esta clase ya no se utilizará. */
public class DAOPasajeroDatosPrueba {

	public static PasajeroBean obtenerPasajero(int nroDoc) {
		
		PasajeroBean pasajero = new PasajeroBeanImpl();
		pasajero.setTipoDocumento("DNI");
		pasajero.setNroDocumento(nroDoc);
		pasajero.setApellido("Apellido " + String.valueOf(nroDoc));
		pasajero.setNombre("Nombre " + String.valueOf(nroDoc));
		pasajero.setDireccion("Direccion " + String.valueOf(nroDoc));
		pasajero.setNacionalidad("Argentino");

		String str = String.valueOf(nroDoc);
		String telefono = "";
       	for ( int i=0;i<6;i++)
       	{
           telefono = telefono + str;
       	}

		pasajero.setTelefono("291-"  + telefono);

		
		return pasajero;
	}

}
