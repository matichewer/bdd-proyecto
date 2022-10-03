package vuelos.modelo;

import java.util.List;


public interface ModeloLogin { 
	
	/**
	 * Realiza la carga de propiedades para establecer la conexión
	 * 
	 * @exception Si la carga de parametros falla.
	 */
	public void iniciarConexion() throws Exception;
	
	/**
	 * Recupera la información del usuario según el displayname
	 * 
	 * @param displayname valor que aparece en la componente que se muestra al usuario, nombre representativo
	 * 
	 * @return información del usuario
	 */
	public UsuarioBean obtenerUsuario(String displayname);
	
}
