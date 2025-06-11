package umu.tds.apps.AppChat.vistas;

import umu.tds.apps.AppChat.controlador.AppChat;
import umu.tds.apps.AppChat.dominio.*;
import umu.tds.apps.AppChat.persistencia.RepositorioMensajes;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

public class VentanaBuscar{

    private JTextField txtTexto;
    private JTextField txtTelefono;
    private JTextField txtNombreContacto;
    private JPanel panelResultados;
    private JFrame frmBuscar;


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
        frmBuscar.add(crearPanelResultados(), BorderLayout.CENTER);

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
        return scrollResultados;
    }

    private void buscarMensajes() {
        panelResultados.removeAll();

        String texto = txtTexto.getText().trim();
        String telefono = txtTelefono.getText().trim();
        String nombre = txtNombreContacto.getText().trim();
        boolean existe = false;

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
            	System.out.println(m);
                String emisor = m.getContacto_emisor().getMovil().equals(miNumero) ? "Yo" : "Él/Ella";
                JLabel lbl = new JLabel(emisor + ": " + m.getTexto());
                lbl.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
                panelResultados.add(lbl);
            }
        }	

        panelResultados.revalidate();
        panelResultados.repaint();
    }
}
