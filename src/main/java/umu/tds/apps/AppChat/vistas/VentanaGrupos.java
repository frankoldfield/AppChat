package umu.tds.apps.AppChat.vistas;

import java.awt.BorderLayout;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import umu.tds.apps.AppChat.controlador.AppChat;
import umu.tds.apps.AppChat.dominio.ContactoIndividual;
import umu.tds.apps.AppChat.dominio.Grupo;

public class VentanaGrupos {
	
	private VentanaPrincipal ventanaPrincipal;
	private JFrame frmGrupos;
	private JPanel scrollPanelIzquierdo;
    private JPanel scrollPanelDerecho;
    private String nombreGrupo;
    private AppChat appChat;
    
    
    DefaultListModel<ContactoIndividual> modeloContactos;
    DefaultListModel<ContactoIndividual> modeloContactosEnGrupo;
    private JList<ContactoIndividual> listaContactos;
    private JList<ContactoIndividual> listaContactosInGrupo;
	
    public VentanaGrupos(VentanaPrincipal ventanaPrincipal, String nombreGrupo) {
    	this.ventanaPrincipal = ventanaPrincipal;
    	this.nombreGrupo = nombreGrupo;
        initialize();
        
    }

    public void mostrarVentana() {
    	frmGrupos.setLocationRelativeTo(null);
    	frmGrupos.setVisible(true);
        
    }

	private void initialize() {
		appChat = AppChat.getInstance();
		frmGrupos = new JFrame("AppChat");
		frmGrupos.setSize(750, 400);
		frmGrupos.setLayout(new BorderLayout());

		frmGrupos.add(crearPanelCentral(), BorderLayout.CENTER);

		scrollPanelDerecho = crearPanelDerecho();
		frmGrupos.add(scrollPanelDerecho, BorderLayout.EAST);
		
		scrollPanelIzquierdo = crearPanelIzquierdo();
		frmGrupos.add(scrollPanelIzquierdo, BorderLayout.WEST);

		frmGrupos.add(crearPanelInferior(), BorderLayout.SOUTH);
	}

	private JPanel crearPanelCentral() {
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.setBorder(new EmptyBorder(100, 10, 100, 10)); // Espacio superior/inferior

		JButton btnDcha = new JButton("->");
		JButton btnIzq = new JButton("<-");

		Dimension btnSize = new Dimension(60, 30);
		btnDcha.setMaximumSize(btnSize);
		btnIzq.setMaximumSize(btnSize);

		btnDcha.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnIzq.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		btnDcha.addActionListener(e -> {
		    int[] indices = listaContactos.getSelectedIndices();
		    for (int i = indices.length - 1; i >= 0; i--) { // de atrás hacia adelante para evitar problemas al eliminar
		    	ContactoIndividual contacto = modeloContactos.getElementAt(indices[i]);
		        modeloContactosEnGrupo.addElement(contacto);
		        modeloContactos.removeElementAt(indices[i]);
		    }
		});
		
		btnIzq.addActionListener(e -> {
		    int[] indices = listaContactosInGrupo.getSelectedIndices();
		    for (int i = indices.length - 1; i >= 0; i--) {
		    	ContactoIndividual contacto = modeloContactosEnGrupo.getElementAt(indices[i]);
		        modeloContactos.addElement(contacto);
		        modeloContactosEnGrupo.removeElementAt(indices[i]);
		    }
		});

		panel.add(btnDcha);
		panel.add(Box.createVerticalStrut(10)); // Separación
		panel.add(btnIzq);

		return panel;
	}

	private JPanel crearPanelIzquierdo() {
		modeloContactos = new DefaultListModel<ContactoIndividual>();
		
		List<ContactoIndividual> contactos = appChat.getListaContactosIndividuales();
        List<ContactoIndividual> nombres = contactos.stream()
        		  .filter(c -> !modeloContactosEnGrupo.contains(c.getNombre()))
				  .collect(Collectors.toList());
        
        nombres.forEach(modeloContactos::addElement);
        
        listaContactos = new JList<>(modeloContactos);
        
        listaContactos.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

	    JScrollPane scroll = new JScrollPane(listaContactos);
	    scroll.setPreferredSize(new Dimension(300, 0)); // Aumentamos el ancho
	    scroll.setBorder(BorderFactory.createTitledBorder("Contactos"));

	    // 👇 Lo envolvemos en un panel con margen
	    JPanel contenedor = new JPanel(new BorderLayout());
	    contenedor.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 5)); // margen izquierdo
	    contenedor.add(scroll, BorderLayout.CENTER);

	    return contenedor;
	}

	private JPanel crearPanelDerecho() {
		modeloContactosEnGrupo = new DefaultListModel<ContactoIndividual>();
		
		Grupo grupo;
		grupo = appChat.getGrupo(nombreGrupo);
        if(grupo != null) {
        	List<ContactoIndividual> nombres = grupo.getContactos().stream()
  				  .collect(Collectors.toList());
        	
          nombres.forEach(modeloContactosEnGrupo::addElement);
        }
        
        listaContactosInGrupo = new JList<>(modeloContactosEnGrupo);
        
	    listaContactosInGrupo.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

	    JScrollPane scroll = new JScrollPane(listaContactosInGrupo);
	    scroll.setPreferredSize(new Dimension(300, 0)); // Aumentamos el ancho
	    scroll.setBorder(BorderFactory.createTitledBorder("Contactos añadidos"));

	    // Lo envolvemos en un panel con margen
	    JPanel contenedor = new JPanel(new BorderLayout());
	    contenedor.setBorder(BorderFactory.createEmptyBorder(10, 5, 10, 10)); // margen derecho
	    contenedor.add(scroll, BorderLayout.CENTER);

	    return contenedor;
	}

	private JPanel crearPanelInferior() {
		JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));

		JButton btnAceptar = new JButton("Aceptar");
		JButton btnCancelar = new JButton("Cancelar");

		btnCancelar.addActionListener(e -> frmGrupos.dispose());
		btnAceptar.addActionListener(e -> {
			List<ContactoIndividual> contactosEnGrupo = appChat.getListaContactosIndividuales().stream()
				    .filter(c -> (c instanceof ContactoIndividual) && IntStream.range(0, modeloContactosEnGrupo.getSize())
				        .mapToObj(modeloContactosEnGrupo::getElementAt)
				        .anyMatch(contacto -> contacto.getMovil().equals(c.getMovil())))
				    .collect(Collectors.toList());

			appChat.CrearOActualizarGrupo(nombreGrupo, contactosEnGrupo);
			
			JOptionPane.showMessageDialog(frmGrupos, "Grupo creado o modificado");
			frmGrupos.dispose();
			ventanaPrincipal.refrescarPanelIzquierdo();
			
		});

		panel.add(btnAceptar);
		panel.add(btnCancelar);

		return panel;
	}
}
