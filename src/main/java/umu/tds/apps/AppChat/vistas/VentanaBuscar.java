package umu.tds.apps.AppChat.vistas;

import umu.tds.apps.AppChat.controlador.AppChat;
import umu.tds.apps.AppChat.dominio.*;
import umu.tds.apps.AppChat.persistencia.RepositorioMensajes;
import umu.tds.apps.AppChat.utils.StyleUtils;

import javax.swing.*;

import tds.BubbleText;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

public class VentanaBuscar{

    private JTextField txtTexto;
    private JTextField txtTelefono;
    private JTextField txtNombreContacto;
    private JPanel panelResultados;
    private JFrame frmBuscar;
    private JScrollPane scrollPanelResultados; 



    public VentanaBuscar() {
    	initialize();
    }
    
    public void mostrarVentana() {
		frmBuscar.setLocationRelativeTo(null);
		frmBuscar.setVisible(true);
	}
    
    private void initialize() {
        frmBuscar = new JFrame("Buscar mensajes");
        frmBuscar.setLayout(new BorderLayout());
        frmBuscar.setSize(700, 500);

        frmBuscar.add(crearPanelBusqueda(), BorderLayout.NORTH);
        scrollPanelResultados = crearPanelResultados();
        frmBuscar.add(scrollPanelResultados, BorderLayout.CENTER);


        frmBuscar.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    private JPanel crearPanelBusqueda() {
        JPanel panelBusqueda = new JPanel(new GridBagLayout());
        panelBusqueda.setBorder(BorderFactory.createTitledBorder("Buscar mensaje"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        txtTexto = new JTextField();
        txtTelefono = new JTextField();
        txtNombreContacto = new JTextField();
        JButton btnBuscar = new JButton("🔍");

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;
        panelBusqueda.add(new JLabel("Texto a buscar:"), gbc);

        gbc.gridy = 1;
        gbc.gridx = 0;
        gbc.gridwidth = 3;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        panelBusqueda.add(txtTexto, gbc);

        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        gbc.gridx = 0;
        panelBusqueda.add(new JLabel("Teléfono:"), gbc);
        gbc.gridx = 1;
        panelBusqueda.add(new JLabel("Contacto:"), gbc);

        gbc.gridy = 3;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        gbc.gridx = 0;
        panelBusqueda.add(txtTelefono, gbc);
        gbc.gridx = 1;
        panelBusqueda.add(txtNombreContacto, gbc);
        gbc.gridx = 2;
        panelBusqueda.add(btnBuscar, gbc);
        
        btnBuscar.addActionListener(e -> buscarMensajes());

        return panelBusqueda;
    }

    private JScrollPane crearPanelResultados() {
        panelResultados = new JPanel();
        panelResultados.setLayout(new BoxLayout(panelResultados, BoxLayout.Y_AXIS));
        JScrollPane scrollResultados = new JScrollPane(panelResultados);
        scrollResultados.setPreferredSize(new Dimension(500, 400));
        scrollResultados.getVerticalScrollBar().setUnitIncrement(16);
        return scrollResultados;
    }

    private void buscarMensajes() {
    	limpiarPanelResultados();
        
        String texto = txtTexto.getText().trim();
        String telefono = txtTelefono.getText().trim();
        String nombre = txtNombreContacto.getText().trim();

        Usuario usuario = AppChat.getInstance().usuarioActual;
        String miNumero = usuario.getMovil();

        
        
        if (!telefono.isEmpty() && !nombre.isEmpty()) {
        	JOptionPane.showMessageDialog(frmBuscar, "Introducir solo teléfono o contacto", "Error", JOptionPane.ERROR_MESSAGE);
        	return;
        }
       
        List<Mensaje> resultados = AppChat.INSTANCE.buscarMensajes(texto, telefono, nombre);
        
        
    	if (resultados.isEmpty()) {
            panelResultados.add(new JLabel("No se encontraron mensajes."));
        } else {
            for (Mensaje m : resultados) {
            	String textoMensaje = m.getTexto();
            	String emisor;
            	String receptor;
            	if(m.getEmoji()!=-1) {
            		textoMensaje = "Emoji";
            	}
            	if(m.getContacto_emisor().getMovil().equals(miNumero)) {
            		emisor = usuario.getNombre();
            		if(m.getContacto_receptor() instanceof ContactoIndividual) {
            			receptor = usuario.getContactoIndividual(((ContactoIndividual) m.getContacto_receptor()).getMovil()).getNombre();
                		if(receptor.isEmpty()) {
                			receptor = ((ContactoIndividual) m.getContacto_receptor()).getMovil();
                		}
            		}
            		else {
            			receptor = m.getContacto_receptor().getNombre();
            		}
            		
            	}
            	else {
            		emisor = usuario.getContactoIndividual(m.getContacto_emisor().getMovil()).getNombre();
            		if(emisor.isEmpty()) {
            			emisor = m.getContacto_emisor().getMovil();
            		}
            		receptor = usuario.getNombre();
            	}
                
                panelResultados.add(crearPanelMensaje(emisor, receptor, textoMensaje, m.getEmoji()));
            }
        }	
    	frmBuscar.revalidate(); 
        frmBuscar.repaint(); 
        scrollPanelResultados.revalidate(); 
        scrollPanelResultados.repaint(); 
        
    }

    private JPanel crearPanelMensaje(String emisor, String receptor, String mensaje, int Emoji) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2)); // Borde del panel principal del mensaje
        
        // Subpanel para emisor y receptor
        JPanel cabecera = new JPanel(new BorderLayout());
        JLabel labelEmisor = new JLabel(emisor);
        JLabel labelReceptor = new JLabel(receptor, SwingConstants.RIGHT);
        cabecera.add(labelEmisor, BorderLayout.WEST);
        cabecera.add(labelReceptor, BorderLayout.EAST);

     // Área del mensaje con scroll opcional
        JLabel areaMensaje;
        if(Emoji!=-1) {
        	areaMensaje = new JLabel(BubbleText.getEmoji(Emoji));
        }
        else {
        	areaMensaje = new JLabel(mensaje);
        }
//        areaMensaje.setLineWrap(true);
//        areaMensaje.setWrapStyleWord(true);
//        areaMensaje.setEditable(false);
        areaMensaje.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Márgenes internos del texto
        areaMensaje.setBackground(StyleUtils.BACKGROUND_DARKER);
        
        // Panel contenedor del área de mensaje
        JPanel panelMensaje = new JPanel(new BorderLayout());
        panelMensaje.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1)); // Borde del panel
        panelMensaje.setMinimumSize(new Dimension(200, 60)); // Tamaño mínimo
        panelMensaje.add(areaMensaje, BorderLayout.CENTER);


        // Ensamblar todo
        cabecera.setBackground(StyleUtils.BACKGROUND_DARKER);
        panelMensaje.setBackground(StyleUtils.BACKGROUND_DARKER);
        panel.add(cabecera, BorderLayout.NORTH);
        panel.add(panelMensaje, BorderLayout.CENTER);
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Espaciado interior (padding)
        panel.setBackground(StyleUtils.BACKGROUND_DARKER);
        JPanel panelConBorde = new JPanel(new BorderLayout());
        panelConBorde.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2)); // Borde exterior final del panel
        panelConBorde.add(panel, BorderLayout.CENTER);
        panelConBorde.setBackground(StyleUtils.BACKGROUND_DARKER);
        
        JPanel contenedor = new JPanel(new BorderLayout());
        contenedor.setMaximumSize(new Dimension(Integer.MAX_VALUE, 120));
        contenedor.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Margen exterior
        contenedor.add(panelConBorde, BorderLayout.CENTER);
        return contenedor;

    }

    
    private void limpiarPanelResultados() {
        frmBuscar.remove(scrollPanelResultados); 
        scrollPanelResultados = crearPanelResultados(); 
        frmBuscar.add(scrollPanelResultados, BorderLayout.CENTER); 
        frmBuscar.revalidate(); 
        frmBuscar.repaint(); 
    }

}
