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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class VentanaPrincipal {
    private JFrame frame;
    private JPanel panelChats;
    private JPanel chat;
    private JScrollPane scrollPanelIzquierdo;
    private JPanel panelChatSeleccionado;
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

//PANEL SUPERIOR
    
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
    


//PANEL IZQUIERDO
    
    private JScrollPane crearPanelIzquierdo() {
    	
        panelChats = new JPanel();
        panelChats.setLayout(new BoxLayout(panelChats, BoxLayout.Y_AXIS));
        panelChats.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        panelChats.setPreferredSize(null);

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

            if (contacto instanceof ContactoIndividual) {
                String otroNumero = ((ContactoIndividual) contacto).getMovil();
                List<Mensaje> conversacion = AppChat.INSTANCE.buscarMensajes("", otroNumero, "");
                
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
        scroll.getVerticalScrollBar().setUnitIncrement(15);
        return scroll;
    }

    private JPanel crearElementoChat(String nombre, String ultimoMensaje, String movil) {
    	JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        panel.setBackground(Color.WHITE);

        panel.setPreferredSize(new Dimension(200, 60));

        //Añadimos un mouse listener para diferenciar el color del chat usado
        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
            	if (panelChatSeleccionado != null) {
                    panelChatSeleccionado.setBackground(Color.WHITE);
                }

                //Color azul claro para el chat seleccionado
                panel.setBackground(new Color(220, 240, 255));
                panelChatSeleccionado = panel;	
                mostrarConversacion(movil);
            }
        });

        JLabel icono = new JLabel("🧑");
        icono.setPreferredSize(new Dimension(40, 40));
        JPanel texto = new JPanel(new GridLayout(2, 1));
        //Ponemos esto para que se pueda ver que chat esta siendo seleccionado
        texto.setOpaque(false);
        
        
        panel.add(icono, BorderLayout.WEST);
        panel.add(texto, BorderLayout.CENTER);
        
        if(nombre.isEmpty()) {
        	texto.add(new JLabel(movil));
        	JButton btnAñadir = new JButton("+");
            btnAñadir.addActionListener(e -> {
            	mostrarDialogoNuevoContacto(movil);
            });
            panel.add(btnAñadir, BorderLayout.EAST);
        } else {
        	texto.add(new JLabel(nombre));
        }
        texto.add(new JLabel(ultimoMensaje));
        
        return panel;
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
            refrescarPanelIzquierdo(); 
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
        frame.remove(scrollPanelIzquierdo); 
        scrollPanelIzquierdo = crearPanelIzquierdo();
        frame.add(scrollPanelIzquierdo, BorderLayout.WEST); 
        frame.revalidate();
        frame.repaint();
    }

//PANEL DERECHO
    
    private JScrollPane crearPanelDerecho() {
        chat = new JPanel();
        chat.setLayout(new BoxLayout(chat, BoxLayout.Y_AXIS));
        chat.setPreferredSize(new Dimension(400, 700));

        JScrollPane scrollPane = new JScrollPane(chat);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getVerticalScrollBar().setUnitIncrement(10);
        return scrollPane;
    }
    
    private void mostrarConversacion(String movil) {
        chat.removeAll();

        String miNumero = AppChat.getInstance().usuarioActual.getMovil();
        List<Mensaje> mensajes = AppChat.INSTANCE.buscarMensajes("", movil, "");

        for (Mensaje mensaje : mensajes) {
            boolean enviado = mensaje.getContacto_emisor().getMovil().equals(miNumero);
            if(mensaje.getEmoji() == -1) {
            	BubbleText burbuja = new BubbleText(chat, mensaje.getTexto(), enviado ? Color.GREEN : Color.LIGHT_GRAY,
            			mensaje.getContacto_emisor().getNombre(), enviado ? BubbleText.SENT : BubbleText.RECEIVED);
            	chat.add(burbuja);
            }else{
            	BubbleText burbuja = new BubbleText(chat, mensaje.getEmoji(), enviado ? Color.GREEN : Color.LIGHT_GRAY,
            			mensaje.getContacto_emisor().getNombre(), enviado ? BubbleText.SENT : BubbleText.RECEIVED, 12);
            	chat.add(burbuja);
            }
            
        }

        chat.revalidate();
        chat.repaint();
    }    
}
