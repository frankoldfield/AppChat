package umu.tds.apps.AppChat.vistas;

import java.awt.GridBagLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JList;
import javax.swing.JScrollPane;

import umu.tds.apps.AppChat.controlador.AppChat;
import umu.tds.apps.AppChat.dominio.Contacto;
import umu.tds.apps.AppChat.dominio.ContactoIndividual;

public class VentanaContactos {
	private JFrame frmRegistro;

	 public VentanaContactos() {
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
	    	ArrayList<Contacto> cont = AppChat.INSTANCE.usuarioActual.getContactos();
	    	List<ContactoIndividual> contactos = new ArrayList<>();

	    	for (Contacto c : cont) {
	    	    if (c instanceof ContactoIndividual) {
	    	        contactos.add((ContactoIndividual) c);
	    	    }
	    	}

	    	JList<ContactoIndividual> listaContactos = new JList<>(contactos.toArray(new ContactoIndividual[0]));
	    	listaContactos.setCellRenderer(new ContactoListCellRenderer());

	    	JScrollPane scrollPane = new JScrollPane(listaContactos);
	    	frmRegistro.getContentPane().add(scrollPane);
	    }
}
