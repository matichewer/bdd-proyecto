package vuelos.vista.login;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.border.LineBorder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import vuelos.controlador.ControladorLogin;

//CLASE IMPLEMENTADA PROVISTA POR LA CATEDRA
public class VentanaLoginImpl extends JFrame implements VentanaLogin {

	private static Logger logger = LoggerFactory.getLogger(VentanaLoginImpl.class);
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public VentanaLoginImpl()
	{
		logger.info("Creación de la ventana de login");
				
		this.inicializar();
	}

	@Override
	public void eliminarVentana() {
		logger.info("Eliminación de la ventana de login.");
		this.dispose();
	}

	@Override
	public void informar(String mensaje) {
		logger.info("Crea una ventana modal informando: {}.", mensaje);
		
		JOptionPane.showMessageDialog(null,mensaje);
	}

	@Override
	public void mostrarVentana() throws Exception {
		this.setVisible(true);		
	}

	@Override
	public void registrarControlador(ControladorLogin controlador) {
		this.controlador = controlador;
	}
	
	private String getUserName() {

		String username = null;
		
		username = (String) this.getCampoEmpleadoUsername().getText();

		return username;
	}

	
	private char[] getPassword() {		

		char[] password = null;
		
		password = this.getCampoEmpleadoPassword().getPassword();
		
		return password;
		
	}
	
	/*
	 * Propiedades y m{etodos privados y protegidos
	 * 
	 * 
	 */
	protected ControladorLogin controlador;
	 
	
	protected JPanel mainPanel;	

	protected JPanel panelLogin;	
	protected CardLayout loginLayout;	

	// Card Empleado
	protected JTextField campoEmpleadoUsername;
	protected JPasswordField campoEmpleadoPassword;
	
	protected JButton btnAceptarLogin;	
	protected JButton btnCancelarLogin;
	
	/**
	  * Método encargado de inicializar todos los componentes de la ventana para logguearse
	  * 
	  * BorderLayout
	  * 
	  * +--------------------------------------+
	  * |               PAGE_START             |
	  * +--------------+----------+------------+
	  * |              |          |            |
	  * |  LINE_START  |  CENTER  |  LINE_END  |
	  * |              |          |            |
	  * +--------------+----------+------------+
	  * |               PAGE_END               |
	  * +--------------+----------+------------+
	  * 
	  */
	private void inicializar()
	{
		this.setType(Type.POPUP);
		this.setTitle("Ingreso al Sistema");
		this.setResizable(false);
		this.setBounds(100, 100, 406, 250);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		this.mainPanel = new JPanel();
		this.mainPanel.setLayout(new BorderLayout());
		this.mainPanel.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
				
		//this.mainPanel.add(this.crearPanelTipoUsuario(), BorderLayout.PAGE_START);
		this.mainPanel.add(this.crearPanelLogin(), BorderLayout.CENTER);		
		this.mainPanel.add(this.crearPanelButtons(), BorderLayout.PAGE_END);

		logger.debug("Se registran los listeners.");
		this.registrarEventos();
		
		this.setContentPane(this.mainPanel);
		this.pack();
		this.setVisible(true);
		
		//this.loginLayout.show(this.panelLogin, "empleado");
	}

	/**
	 * Crea el panel de la botonera 
	 * 
	 * @return JPanel
	 */
	private JPanel crearPanelButtons() {
		
		JPanel panelButtons = new JPanel();
		
		btnAceptarLogin = new JButton("Aceptar");
		panelButtons.add(btnAceptarLogin);

		btnCancelarLogin = new JButton("Cancelar");
		panelButtons.add(btnCancelarLogin);
		
		return panelButtons;
	}

	/**
	 * Panel que permite ingresar los datos
	 */
	private JPanel crearPanelLogin() {
		
		this.loginLayout = new CardLayout();
		
		this.panelLogin = new JPanel();
		this.panelLogin.setLayout(this.loginLayout);
		
		this.panelLogin.add(this.crearPanelLoginEmpleado(),"empleado");
		
		return this.panelLogin;
	}
	
	private JPanel crearPanelLoginEmpleado() {
		
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
		
		JPanel panelFila1 = new JPanel();
		((FlowLayout) panelFila1.getLayout()).setHgap(25);		

		JLabel lblUsername = new JLabel("Legajo:");
		this.campoEmpleadoUsername = new JTextField();
		this.campoEmpleadoUsername.setColumns(10);

		panelFila1.add(lblUsername);
		panelFila1.add(this.campoEmpleadoUsername);
		
		JPanel panelFila2 = new JPanel();
				
		JLabel lblPasswordLogin = new JLabel("Contraseña:");
				
		this.campoEmpleadoPassword = new JPasswordField();
		this.campoEmpleadoPassword.setColumns(10);

		panelFila2.add(lblPasswordLogin);		
		panelFila2.add(this.campoEmpleadoPassword);
		
		panel.add(panelFila1);
		panel.add(panelFila2);
		
		return panel;
	}
	
	/*
	 * Setters y Getters
	 * 
	 */

	protected JTextField getCampoEmpleadoUsername() {
		return campoEmpleadoUsername;
	}

	protected JPasswordField getCampoEmpleadoPassword() {
		return campoEmpleadoPassword;
	}

	protected JButton getBtnAceptarLogin() {
		return btnAceptarLogin;
	}

	protected JButton getBtnCancelarLogin() {
		return btnCancelarLogin;
	}

	/*
	 * Metodos para los listener
	 * 
	 * 
	 */
	protected void registrarEventos() {

		this.getCampoEmpleadoUsername().addActionListener(this.getIngresarListener());		
		this.getCampoEmpleadoPassword().addActionListener(this.getIngresarListener());

		this.getBtnAceptarLogin().addActionListener(this.getIngresarListener());		
		this.getBtnCancelarLogin().addActionListener(this.getCancelarListener());		
	}
	
	protected ActionListener getIngresarListener() {
		return new ActionListener() {
			public void actionPerformed(ActionEvent e){
            	controlador.ingresarComoEmpleado(getUserName(),getPassword());
            }
        };
	}

	protected ActionListener getCancelarListener() {
		return new ActionListener() {
			public void actionPerformed(ActionEvent e){
				System.exit(0);
            }
        };
	}	
}
