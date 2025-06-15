package umu.tds.apps.AppChat.vistas;

import javax.swing.*;

import umu.tds.apps.AppChat.controlador.AppChat;

import java.awt.*;

public class VentanaLogin {

	private JFrame frmLogin;
	private JTextField textUsuario;
	private JPasswordField textPassword;

	public VentanaLogin() {
		initialize();
	}

	public void mostrarVentana() {
		frmLogin.setLocationRelativeTo(null);
		frmLogin.setVisible(true);
	}

	private void initialize() {
		frmLogin = new JFrame("VentanaLogin");
		frmLogin.setBounds(100, 100, 450, 300);
		frmLogin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		crearPanelPrincipal();	
	}

	private void crearPanelPrincipal() {
		JPanel panelPrincipal = new JPanel();
		panelPrincipal.setLayout(new GridLayout(2, 1));
		frmLogin.getContentPane().add(panelPrincipal);
        
		crearPanelLogin(panelPrincipal);
		crearPanelBotones(panelPrincipal);
	}
	
	private void crearPanelLogin(JPanel panelPrincipal) {
		JPanel panelLogin = new JPanel();
		panelLogin.setLayout(new GridBagLayout());
		panelLogin.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
	    
	    GridBagConstraints gbc = new GridBagConstraints();
	    gbc.insets = new Insets(5, 10, 10, 10);
	    gbc.fill = GridBagConstraints.HORIZONTAL;

	    //Título de la aplicación
	    JLabel lblTitulo = new JLabel("AppChat", SwingConstants.CENTER);
	    lblTitulo.setFont(new Font("Tahoma", Font.BOLD, 20));
	    gbc.gridx = 0;
	    gbc.gridy = 0;
	    gbc.gridwidth = 2;
	    panelLogin.add(lblTitulo, gbc);

	    //Etiqueta Teléfono
	    gbc.gridwidth = 1;
	    gbc.gridx = 0;
	    gbc.gridy = 1;
	    gbc.anchor = GridBagConstraints.EAST;
	    JLabel lblTelefono = new JLabel("Telefono:");
	    lblTelefono.setFont(new Font("Tahoma", Font.PLAIN, 13));
	    panelLogin.add(lblTelefono, gbc);

	    //Campo Teléfono
	    textUsuario = new JTextField(15);
	    gbc.gridx = 1;
	    gbc.gridy = 1;
	    gbc.weightx = 1.0;
	    gbc.anchor = GridBagConstraints.CENTER;
	    panelLogin.add(textUsuario, gbc);

	    //Etiqueta Contraseña
	    gbc.gridx = 0;
	    gbc.gridy = 2;
	    gbc.anchor = GridBagConstraints.EAST;
	    JLabel lblPassword = new JLabel("Contraseña:");
	    lblPassword.setFont(new Font("Tahoma", Font.PLAIN, 13));
	    panelLogin.add(lblPassword, gbc);

	    //Campo Contraseña
	    textPassword = new JPasswordField(15);
	    gbc.gridx = 1;
	    gbc.gridy = 2;
	    gbc.weightx = 1.0;
	    gbc.anchor = GridBagConstraints.CENTER;
	    panelLogin.add(textPassword, gbc);

	    panelPrincipal.add(panelLogin);
	}

	private void crearPanelBotones(JPanel panelPrincipal) {
		JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 30));
	    JButton btnRegistrar = new JButton("Registrar");
	    JButton btnCancelar = new JButton("Cancelar");
	    JButton btnAceptar = new JButton("Aceptar");
	    panelBotones.add(btnRegistrar);
	    panelBotones.add(btnCancelar);
	    panelBotones.add(btnAceptar);
	    
	    //Nos movemos a la ventana de registro
	    btnRegistrar.addActionListener(e -> {
	    	frmLogin.dispose();
	        VentanaRegistro ventanaRegistro = new VentanaRegistro();
	        ventanaRegistro.mostrarVentana();
	    });
	    
	    //Cerramos la aplicacion en el caso de cancelar
	    btnCancelar.addActionListener(e -> {
	        System.exit(0);
	    });
	    
	    //TODO LLAMADA A CONTROLADOR: COMPROBACION DE USUARIO
	    btnAceptar.addActionListener(e -> {
	    	int returnCode = AppChat.INSTANCE.login(textUsuario.getText(), new String(textPassword.getPassword()));
	    	switch(returnCode) {
	    		case 0:
	    			VentanaPrincipal ventana = new VentanaPrincipal();
	    			ventana.mostrarVentana();
	    			frmLogin.dispose();	  
	    			break;
	    		case -1:
	    			JOptionPane.showMessageDialog(frmLogin, "Número de telefono no registrado.", "Error", JOptionPane.ERROR_MESSAGE);
	                return;
	    		case -2:
	    			JOptionPane.showMessageDialog(frmLogin, "Contraseña incorrecta.", "Error", JOptionPane.ERROR_MESSAGE);
	                return;
	    	}
	    });
	    
	    
	    panelPrincipal.add(panelBotones);
	}
}