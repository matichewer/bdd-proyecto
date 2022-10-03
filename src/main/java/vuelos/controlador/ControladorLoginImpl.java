package vuelos.controlador;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import vuelos.modelo.ModeloLogin;
import vuelos.modelo.UsuarioBean;
import vuelos.modelo.empleado.ModeloEmpleado;
import vuelos.modelo.empleado.ModeloEmpleadoImpl;
import vuelos.vista.empleado.VentanaEmpleado;
import vuelos.vista.empleado.VentanaEmpleadoImpl;
import vuelos.vista.login.VentanaLogin;

//CLASE IMPLEMENTADA PROVISTA POR LA CATEDRA 
public class ControladorLoginImpl implements ControladorLogin { 
	
	private static Logger logger = LoggerFactory.getLogger(ControladorLoginImpl.class);	
	
	private VentanaLogin ventana; 
	private ModeloLogin modelo;	
	
	public ControladorLoginImpl(VentanaLogin ventana, ModeloLogin theModel) {
		this.ventana = ventana;	
		this.modelo = theModel;
		
		try {
			logger.info("Se inicia la carga de parametros para conectar con la BD.");
			this.modelo.iniciarConexion();
			
			logger.info("Se inicializa la ventana de login.");
			
			logger.debug("se registra el controlador.");
			this.ventana.registrarControlador(this);
			
			logger.debug("se muestra la ventana.");
			this.ventana.mostrarVentana();
		
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}
	

	@Override
	public void ingresarComoEmpleado(String legajo, char[] password) {
		logger.debug("Intenta ingresar como Empleado con legajo {}, password {}", legajo, String.valueOf(password));
		
		logger.debug("Recupera el usuario de BD para {} para poder conectarse con los permisos de dicho usuario.","Empleado");
		UsuarioBean usuario = this.modelo.obtenerUsuario("Empleado");

		if (usuario != null) {
			
			logger.debug("usuario {}, password {}",usuario.getUsername(), usuario.getPassword());
			
			ModeloEmpleado modeloEmpleado = new ModeloEmpleadoImpl();
			
			if (modeloEmpleado.conectar(usuario.getUsername(), usuario.getPassword())) {
			
				try {
					if (modeloEmpleado.autenticarUsuarioAplicacion(legajo, String.valueOf(password))) {

						logger.info("Empleado {} autenticado",legajo);
						
						VentanaEmpleado ventanaEmpleado = new VentanaEmpleadoImpl();
						ControladorEmpleado controladorEmpleado = new ControladorEmpleadoImpl(ventanaEmpleado, modeloEmpleado);
						
						logger.info("Transfiere el control al nuevo controlador");
						controladorEmpleado.ejecutar();
						
						logger.info("Informa a la vista que puede eliminar la ventana de login.");					
						this.ventana.eliminarVentana();
					}
					else
					{
						logger.error("Hubo un error en la autenticación.");
						this.ventana.informar("El usuario o contraseña ingresados son incorrectos.");
					}
				} catch (Exception e) {
					logger.error("Hubo un error en la autenticación.");
					this.ventana.informar(e.getMessage());
				}
			}
			else
			{
				logger.error("No se pudo conectar a la BD.");
				this.ventana.informar("Error en la conexión con la BD.");
			}			
		}
		else
		{
			logger.error("No se pudo recuperar la información del empleado.");
			this.ventana.informar("Error en el acceso a la información del empleado.");
		}		
	}
	
}