package umu.tds.apps.AppChat.vistas;

import javax.imageio.ImageIO;
import javax.swing.*;

import umu.tds.apps.AppChat.controlador.AppChat;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;
import java.util.List;

public class VentanaRegistro {
	
    private JFrame frmRegistro;
    private JTextField txtNombre, txtApellidos, txtTelefono, txtFecha;
    private JPasswordField txtPassword1, txtPassword2;
    private JTextArea txtSaludo;
    private JLabel lblImagen;

    public VentanaRegistro() {
        initialize();
    }

    public void mostrarVentana() {
        frmRegistro.setLocationRelativeTo(null);
        frmRegistro.setVisible(true);
    }

    private void initialize() {
        frmRegistro = new JFrame("VentanaRegistro");
        frmRegistro.setBounds(100, 100, 600, 400);
        frmRegistro.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        crearPanelPrincipal();
    }

    private void crearPanelPrincipal() {
        JPanel panelPrincipal = new JPanel(new GridBagLayout());
        frmRegistro.getContentPane().add(panelPrincipal, BorderLayout.CENTER);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        crearPanelCampos(panelPrincipal, gbc);
        crearPanelBotones(panelPrincipal, gbc);
    }

    private void crearPanelCampos(JPanel panel, GridBagConstraints gbc) {
        //Nombre
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(new JLabel("Nombre:"), gbc);
        txtNombre = new JTextField(15);
        gbc.gridx = 1;
        gbc.gridwidth = 3;        
        panel.add(txtNombre, gbc);

        //Apellidos
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        panel.add(new JLabel("Apellidos:"), gbc);
        txtApellidos = new JTextField(15);
        gbc.gridx = 1;
        gbc.gridwidth = 3;
        panel.add(txtApellidos, gbc);

        //Teléfono
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        panel.add(new JLabel("Teléfono:"), gbc);
        txtTelefono = new JTextField(10);
        gbc.gridx = 1;
        panel.add(txtTelefono, gbc);

        //Contraseña 1
        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(new JLabel("Contraseña:"), gbc);
        txtPassword1 = new JPasswordField(10);
        gbc.gridx = 1;
        panel.add(txtPassword1, gbc);

        //Contraseña 2
        gbc.gridx = 2;
        panel.add(new JLabel("Contraseña:"), gbc);
        txtPassword2 = new JPasswordField(10);
        gbc.gridx = 3;
        panel.add(txtPassword2, gbc);

        //Fecha
        gbc.gridx = 0;
        gbc.gridy = 4;
        panel.add(new JLabel("Fecha:"), gbc);
        txtFecha = new JTextField(8);
        gbc.gridx = 1;
        panel.add(txtFecha, gbc);
        JButton btnCalendario = new JButton("📅");
        gbc.gridx = 2;
        panel.add(btnCalendario, gbc);
        
        //Seleccionar una fecha con JCalendar
        btnCalendario.addActionListener(e -> {
            JDialog dialog = new JDialog(frmRegistro, "Selecciona una fecha", true);
            dialog.setLayout(new BorderLayout());
            com.toedter.calendar.JCalendar calendar = new com.toedter.calendar.JCalendar();
            
            dialog.add(calendar, BorderLayout.CENTER);

            JButton btnSeleccionar = new JButton("Aceptar");
            btnSeleccionar.addActionListener(ev -> {
                java.util.Date fechaSeleccionada = calendar.getDate();
                java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd/MM/yyyy");
                txtFecha.setText(sdf.format(fechaSeleccionada));
                dialog.dispose();
            });

            JPanel panelBoton = new JPanel();
            panelBoton.add(btnSeleccionar);
            dialog.add(panelBoton, BorderLayout.SOUTH);

            dialog.pack();
            dialog.setLocationRelativeTo(frmRegistro);
            dialog.setVisible(true);
        });	

        //Saludo
        gbc.gridx = 0; 
        gbc.gridy = 5;
        panel.add(new JLabel("Saludo:"), gbc);
        txtSaludo = new JTextArea(2, 15);
        txtSaludo.setLineWrap(true);
        txtSaludo.setWrapStyleWord(true);
        JScrollPane scrollSaludo = new JScrollPane(txtSaludo);
        gbc.gridx = 1;
        gbc.gridwidth = 2;
        panel.add(scrollSaludo, gbc);

        //Imagen
        gbc.gridx = 3;
        gbc.gridy = 4;
        gbc.gridheight = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        
        lblImagen = new JLabel(); 
        lblImagen.setPreferredSize(new Dimension(80, 80));
        lblImagen.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        panel.add(lblImagen, gbc);
        gbc.gridheight = 1;
    }

    private void crearPanelBotones(JPanel panel, GridBagConstraints gbc) {
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
	    JButton btnCancelar = new JButton("Cancelar");
	    JButton btnAceptar = new JButton("Aceptar");
	    JButton btnImagen = new JButton("Sel. Imagen");
	    panelBotones.add(btnCancelar);
	    panelBotones.add(btnAceptar);
	    panelBotones.add(btnImagen);
	    
        gbc.gridx = 1;
        gbc.gridy = 6;
        gbc.gridwidth = 3;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(panelBotones, gbc);
        
        btnAceptar.addActionListener(e ->{
        	String con1 = new String(txtPassword1.getPassword());
        	String con2 = new String(txtPassword2.getPassword());
        	String nombre = txtNombre.getText().trim();
            String apellidos = txtApellidos.getText().trim();
            String telefono = txtTelefono.getText().trim();
            
            //Comprobacion de que todos los campos OBLIGATORIOS no están vacíos
            if (nombre.isEmpty() || apellidos.isEmpty() || telefono.isEmpty() || con1.isEmpty() || con2.isEmpty()) {
                JOptionPane.showMessageDialog(frmRegistro, "Es necesario llenar todos los campos.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            //Comprobación de que las contraseñas son iguales
        	if (!con1.equals(con2)) {
                JOptionPane.showMessageDialog(frmRegistro, "Contraseñas desiguales", "Error", JOptionPane.ERROR_MESSAGE);
                return;
        	}
        	//TODO LLAMADA A CONTROLADOR: GUARDAR EN DB
        	VentanaLogin ventana = new VentanaLogin();
        	//Volvemos al login
        	ventana.mostrarVentana();
        	frmRegistro.dispose();
        });
        
        btnCancelar.addActionListener(e ->{       	
        	//Volvemos al login
        	VentanaLogin ventanaLogin = new VentanaLogin();
        	ventanaLogin.mostrarVentana();   
        	frmRegistro.dispose();
        }); 
        
        btnImagen.addActionListener(e -> {
        	//Codigo de la clase dada en el AV
            PanelArrastraImagen panelArrastre = new PanelArrastraImagen(frmRegistro);
            List<File> imagenes = panelArrastre.showDialog();

            if (imagenes != null && !imagenes.isEmpty()) {
                File archivoImagen = imagenes.get(0);
                String rutaAbsoluta = archivoImagen.getAbsolutePath();

                //Cargar la imagen directamente desde la ruta absoluta
                ImageIcon iconoImagen = new ImageIcon(rutaAbsoluta);
                Image imagenEscalada = iconoImagen.getImage()
                        .getScaledInstance(100, 100, Image.SCALE_SMOOTH);

                lblImagen.setIcon(new ImageIcon(imagenEscalada));
                lblImagen.setText("");
            }

            //Cerrar panel
            panelArrastre.setVisible(false);
            panelArrastre.dispose();
        });
    }
}
