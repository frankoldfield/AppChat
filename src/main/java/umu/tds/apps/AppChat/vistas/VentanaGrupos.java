package umu.tds.apps.AppChat.vistas;

import java.awt.BorderLayout;
import java.awt.*;
import javax.swing.*;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import umu.tds.apps.AppChat.dominio.Grupo;

public class VentanaGrupos {
	
	private JFrame frmGrupos;
	private JPanel scrollPanelIzquierdo;
    private JPanel scrollPanelDerecho;
    private String nombreGrupo;
	
    public VentanaGrupos(String nombreGrupo) {
        initialize();
        this.nombreGrupo = nombreGrupo;
    }

    public void mostrarVentana() {
    	frmGrupos.setLocationRelativeTo(null);
    	frmGrupos.setVisible(true);
        
    }

	private void initialize() {
		frmGrupos = new JFrame("AppChat");
		frmGrupos.setSize(750, 400);
		frmGrupos.setLayout(new BorderLayout());

		frmGrupos.add(crearPanelCentral(), BorderLayout.CENTER);

		scrollPanelIzquierdo = crearPanelIzquierdo();
		frmGrupos.add(scrollPanelIzquierdo, BorderLayout.WEST);

		scrollPanelDerecho = crearPanelDerecho();
		frmGrupos.add(scrollPanelDerecho, BorderLayout.EAST);

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

		panel.add(btnDcha);
		panel.add(Box.createVerticalStrut(10)); // Separación
		panel.add(btnIzq);

		return panel;
	}

	private JPanel crearPanelIzquierdo() {
	    JList<String> lista = new JList<>(new String[]{
	        "Irene master", "Diego Sevilla", "Javier Candel", "Jose Hoyos"
	    });
	    lista.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

	    JScrollPane scroll = new JScrollPane(lista);
	    scroll.setPreferredSize(new Dimension(300, 0)); // Aumentamos el ancho
	    scroll.setBorder(BorderFactory.createTitledBorder("Contactos"));

	    // 👇 Lo envolvemos en un panel con margen
	    JPanel contenedor = new JPanel(new BorderLayout());
	    contenedor.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 5)); // margen izquierdo
	    contenedor.add(scroll, BorderLayout.CENTER);

	    return contenedor;
	}

	private JPanel crearPanelDerecho() {
	    JList<String> lista = new JList<>(new String[]{
	        "Jose Hoyos", "Javier Bermudez", "Carlos Candel"
	    });
	    lista.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

	    JScrollPane scroll = new JScrollPane(lista);
	    scroll.setPreferredSize(new Dimension(300, 0)); // Aumentamos el ancho
	    scroll.setBorder(BorderFactory.createTitledBorder("Contactos añadidos"));

	    // 👇 Lo envolvemos en un panel con margen
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
			// TODO: Enviar cambios al backend
			JOptionPane.showMessageDialog(frmGrupos, "Cambios guardados");
		});

		panel.add(btnAceptar);
		panel.add(btnCancelar);

		return panel;
	}
}
