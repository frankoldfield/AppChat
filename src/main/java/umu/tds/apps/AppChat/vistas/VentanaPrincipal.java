package umu.tds.apps.AppChat.vistas;

import javax.swing.*;
import java.util.List;
import java.util.stream.Collectors;

import tds.BubbleText;
import umu.tds.apps.AppChat.controlador.AppChat;
import umu.tds.apps.AppChat.dominio.Contacto;
import umu.tds.apps.AppChat.dominio.ContactoIndividual;
import umu.tds.apps.AppChat.dominio.Mensaje;
import umu.tds.apps.AppChat.persistencia.RepositorioMensajes;

import java.awt.*;

public class VentanaPrincipal {
    private JFrame frame;
    private JPanel panelChats;
    private JScrollPane scrollPanelIzquierdo;
    int filtroContactos = 0;

    public VentanaPrincipal() {
        initialize();
    }

    public void mostrarVentana() {
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        
    }

    private void initialize() {
        frame = new JFrame("AppChat");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(900, 600);
        frame.setLayout(new BorderLayout());
        

        frame.add(crearPanelSuperior(), BorderLayout.NORTH);
        scrollPanelIzquierdo = crearPanelIzquierdo();
        frame.add(scrollPanelIzquierdo, BorderLayout.WEST);
        frame.add(crearPanelDerecho(), BorderLayout.CENTER);
    }

    private JPanel crearPanelSuperior() {
        JPanel panelSuperior = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelSuperior.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        JComboBox<String> comboBusqueda = new JComboBox<>(new String[]{"Contactos o teléfonos", "Contactos", "Teléfonos"});
        JButton btnAceptar = new JButton("Filtrar");
        JButton btnBuscar = new JButton("🔍");
        JButton btnContactos = new JButton("Contactos");
        JButton btnPremium = new JButton("$ Premium");
        String nombre = AppChat.INSTANCE.usuarioActual.getNombre();
        String ruta = AppChat.INSTANCE.usuarioActual.getImagen();
        ImageIcon icon = new ImageIcon(ruta);
        Image img = icon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
        JLabel lblUsuario = new JLabel(nombre, new ImageIcon(img), JLabel.LEFT);
        
        btnContactos.addActionListener(e ->{ 
        	VentanaContactos ventana = new VentanaContactos();
        });
        btnBuscar.addActionListener(e->{
        	VentanaBuscar ventana = new VentanaBuscar();
        	ventana.mostrarVentana();
        });
        
        btnAceptar.addActionListener(e->{
        	filtroContactos = comboBusqueda.getSelectedIndex();
        	refrescarPanelIzquierdo();
        });

        panelSuperior.add(comboBusqueda);
        panelSuperior.add(btnAceptar);
        panelSuperior.add(btnBuscar);
        panelSuperior.add(btnContactos);
        panelSuperior.add(btnPremium);
        panelSuperior.add(lblUsuario);

        return panelSuperior;
    }

    private JScrollPane crearPanelIzquierdo() {
    	if(panelChats != null) {
    		
    	}
        panelChats = new JPanel();
        panelChats.setLayout(new BoxLayout(panelChats, BoxLayout.Y_AXIS));

        String miNumero = AppChat.getInstance().usuarioActual.getMovil();
        List<Contacto> contactos;
        switch(filtroContactos) {
        case 0:
        	contactos = AppChat.getInstance().usuarioActual.getContactos();
        	break;
        case 1:
        	contactos = AppChat.getInstance().usuarioActual.getContactos().stream().filter(c -> !c.getNombre().isEmpty()).collect(Collectors.toList());
        	break;
        case 2:
        	contactos = AppChat.getInstance().usuarioActual.getContactos().stream().filter(c -> c.getNombre().isEmpty()).collect(Collectors.toList());
        	break;
        default:
        	contactos = AppChat.getInstance().usuarioActual.getContactos();
        	break;
        }
        
        for (Contacto contacto : contactos) {
            String otroNumero = "";

            if (contacto instanceof ContactoIndividual) {
                otroNumero = ((ContactoIndividual) contacto).getMovil();
                List<Mensaje> conversacion = AppChat.INSTANCE.buscarMensajes("", miNumero, otroNumero);
                
                String ultimo = "";           
                if (!conversacion.isEmpty()) {
                	ultimo = conversacion.get(conversacion.size() - 1).getTexto();
                }
                if(!contacto.getNombre().isEmpty()) {
                	panelChats.add(crearElementoChat(contacto.getNombre(), ultimo, ((ContactoIndividual) contacto).getMovil()));
                }
                else {
                	panelChats.add(crearElementoChat("", ultimo, ((ContactoIndividual) contacto).getMovil()));
                }
            } else {
                continue; //TODO GRUPOS
            }

            
            
        }

        JScrollPane scroll = new JScrollPane(panelChats);
        scroll.setPreferredSize(new Dimension(250, 0));
        return scroll;
    }

    private JPanel crearElementoChat(String nombre, String ultimoMensaje, String movil) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));

        JLabel icono = new JLabel("🧑");
        icono.setPreferredSize(new Dimension(40, 40));
        JPanel texto = new JPanel(new GridLayout(2, 1));
        
        
        panel.add(icono, BorderLayout.WEST);
        panel.add(texto, BorderLayout.CENTER);
        
        if(nombre.isEmpty()) {
        	texto.add(new JLabel(movil));
        	JButton btnAñadir = new JButton("+");
            btnAñadir.addActionListener(e -> {
            	mostrarDialogoNuevoContacto(movil);
            });
            panelChats.add(btnAñadir);
            panel.add(btnAñadir, BorderLayout.EAST);
        } else {
        	texto.add(new JLabel(nombre));
        }
        texto.add(new JLabel(ultimoMensaje));
        
        return panel;
    }

    private JScrollPane crearPanelDerecho() {
    	JPanel chat=new JPanel();
    	JScrollPane scrollPane = new JScrollPane(chat);
    	scrollPane.setBorder(BorderFactory.createEmptyBorder());
    	chat.setLayout(new BoxLayout(chat,BoxLayout.Y_AXIS));
    	chat.setSize(400,700);
    	chat.setMinimumSize(new Dimension(400,700));
    	chat.setMaximumSize(new Dimension(400,700));
    	chat.setPreferredSize(new Dimension(400,700));
    	BubbleText burbuja;
    	burbuja=new BubbleText(chat,"Hola grupo!!", Color.GREEN, "J.Ramón", BubbleText.SENT);
    	chat.add(burbuja);
    	BubbleText burbuja2;
    	burbuja2=new BubbleText(chat,
    	"Hola, ¿Está seguro de que la burbuja usa varias lineas si es necesario?",
    	Color.LIGHT_GRAY, "Alumno", BubbleText.RECEIVED);
    	chat.add(burbuja2);
    	BubbleText burbuja3;
    	burbuja3=new BubbleText(chat,"No estoy seguro",  
    	Color.GREEN, "J.Ramón", BubbleText.SENT);
    	chat.add(burbuja3);
    	BubbleText burbuja4=new BubbleText(chat, 0, Color.GREEN, "J.Ramón", BubbleText.SENT, 12);
    	chat.add(burbuja4);
    	JLabel x=new JLabel();
    	x.setIcon(BubbleText.getEmoji(3));
    	return scrollPane;
    }
    
    private void mostrarDialogoNuevoContacto(String movil) {
        JDialog dialog = new JDialog(frame, "Nuevo Contacto", true);
        dialog.setSize(500, 200);
        dialog.setLayout(new GridLayout(3, 1, 10, 10)); 
        dialog.setLocationRelativeTo(frame);

        JPanel panelCampos = new JPanel(new GridBagLayout());
        panelCampos.setBorder(BorderFactory.createEmptyBorder(10, 10, 5, 10));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        JLabel lblNombre = new JLabel("Nombre:");
        JTextField txtNombre = new JTextField(20); 

        JLabel lblTelefono = new JLabel("Teléfono:");
        JTextField txtTelefono = new JTextField(20);
        txtTelefono.setText(movil);
        txtTelefono.setEditable(false);

        gbc.gridx = 0;
        gbc.gridy = 0;
        panelCampos.add(lblNombre, gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0; 
        panelCampos.add(txtNombre, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        panelCampos.add(lblTelefono, gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        panelCampos.add(txtTelefono, gbc);

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 0));
        panelBotones.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));

        JButton btnAceptar = new JButton("Aceptar");
        JButton btnCancelar = new JButton("Cancelar");

        Dimension botonDim = new Dimension(120, 25);
        btnAceptar.setPreferredSize(botonDim);
        btnCancelar.setPreferredSize(botonDim);

        btnAceptar.addActionListener(e -> {
            AppChat.INSTANCE.agregarContacto(txtNombre.getText(), txtTelefono.getText());
            refrescarPanelIzquierdo(); // ← Esto actualiza visualmente el panel izquierdo
            dialog.dispose();
        });

        
        btnCancelar.addActionListener(e -> dialog.dispose());

        panelBotones.add(btnAceptar);
        panelBotones.add(btnCancelar);

        dialog.add(panelCampos, BorderLayout.CENTER);
        dialog.add(panelBotones, BorderLayout.SOUTH);

        dialog.setVisible(true);
    }
    
    private void refrescarPanelIzquierdo() {
        frame.remove(scrollPanelIzquierdo); // Quitar el viejo
        scrollPanelIzquierdo = crearPanelIzquierdo(); // Crear uno nuevo con datos actualizados
        frame.add(scrollPanelIzquierdo, BorderLayout.WEST); // Agregar el nuevo
        frame.revalidate(); // Actualiza el layout
        frame.repaint(); // Redibuja
    }

}
