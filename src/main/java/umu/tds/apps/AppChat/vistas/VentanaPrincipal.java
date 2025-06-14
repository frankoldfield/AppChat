package umu.tds.apps.AppChat.vistas;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.util.List;
import java.util.stream.Collectors;

import tds.BubbleText;
import umu.tds.apps.AppChat.controlador.AppChat;
import umu.tds.apps.AppChat.dominio.Contacto;
import umu.tds.apps.AppChat.dominio.ContactoIndividual;
import umu.tds.apps.AppChat.dominio.Grupo;
import umu.tds.apps.AppChat.dominio.Mensaje;
import umu.tds.apps.AppChat.dominio.TipoMensaje;
import umu.tds.apps.AppChat.dominio.Usuario;
import umu.tds.apps.AppChat.persistencia.RepositorioMensajes;
import umu.tds.apps.AppChat.utils.StyleUtils;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class VentanaPrincipal {
    private JFrame frame;
    private JPanel panelChats;
    private JPanel chat;
    private JScrollPane scrollPanelIzquierdo;
    private JPanel panelChatSeleccionado;
    int filtroContactos = 0;
    private Contacto contactoChat;
    private JLabel iconoUsuario;
    private JPanel panelDerecho;
    
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
        panelDerecho = crearPanelDerecho();
        frame.add(panelDerecho, BorderLayout.CENTER);
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
        
        JLabel nombre1 = new JLabel(nombre);
        
        
        
        
        JLabel iconoUsuario = new JLabel();
        iconoUsuario.setPreferredSize(new Dimension(60, 40));
        iconoUsuario.setBorder(BorderFactory.createLineBorder(StyleUtils.ACCENT_COLOR));
        
        String path = AppChat.INSTANCE.usuarioActual.getImagen();
        
        ImageIcon iconoImagen = new ImageIcon(getClass().getResource(path));
        Image imagenEscalada = iconoImagen.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        iconoUsuario.setIcon(new ImageIcon(imagenEscalada));
        iconoUsuario.setText("");
        
        iconoUsuario.addMouseListener(new MouseAdapter() {
        	@Override
            public void mouseClicked(MouseEvent e) {
        		//Codigo de la clase dada en el AV
                PanelArrastraImagen panelArrastre = new PanelArrastraImagen(frame);
                panelArrastre.setForeground(StyleUtils.BACKGROUND_DARK);
                List<File> imagenes = panelArrastre.showDialog();

                if (imagenes != null && !imagenes.isEmpty()) {
                    File archivoImagen = imagenes.get(0);
                    String rutaAbsoluta = archivoImagen.getAbsolutePath();
                    //Cargar la imagen directamente desde la ruta absoluta
                    ImageIcon iconoImagen = new ImageIcon(rutaAbsoluta);
                    Image imagenEscalada = iconoImagen.getImage()
                            .getScaledInstance(100, 100, Image.SCALE_SMOOTH);

                    iconoUsuario.setIcon(new ImageIcon(imagenEscalada));
                    iconoUsuario.setText("");
                    iconoUsuario.revalidate(); // Actualiza la jerarquía del componente
                    iconoUsuario.repaint();    // Fuerza repintado
                    AppChat.INSTANCE.cambiarImagen(rutaAbsoluta);
                }
        	}
        });
        
        
        
        
        
        
        
        
        btnContactos.addActionListener(e ->{ 
        	entrarGrupos();
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
        panelSuperior.add(nombre1);
        panelSuperior.add(iconoUsuario);

        return panelSuperior;
    }
    
    private void entrarGrupos() {
    	JDialog dialog = new JDialog(frame, "Crear/Modificar grupo", true);
        dialog.setSize(500, 250);
        dialog.setLocationRelativeTo(frame);
        dialog.setLayout(new BorderLayout(10, 10));

        JPanel panelCentral = new JPanel(new BorderLayout(10, 10));
        panelCentral.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));

        JList<String> lista = new JList<>(new String[]{
	        "Irene master", "Diego Sevilla", "Javier Candel", "Jose Hoyos"
	    });
	    lista.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

	    JScrollPane scroll = new JScrollPane(lista);
	    scroll.setPreferredSize(new Dimension(200, 0)); // Aumentamos el ancho
	    scroll.setBorder(BorderFactory.createTitledBorder("Grupos"));

	    // 👇 Lo envolvemos en un panel con margen
	    JPanel contenedor = new JPanel(new BorderLayout());
	    contenedor.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 5)); // margen izquierdo
	    contenedor.add(scroll, BorderLayout.CENTER);

        // Añadimos el JScrollPane al panelCentral en BorderLayout.WEST
        panelCentral.add(scroll, BorderLayout.WEST);

        // Añadir scrollPane al panelCentral en WEST
        panelCentral.add(scroll, BorderLayout.WEST);

        // Panel derecho: TextField + "+" más abajo
        JPanel panelDerecho = new JPanel();
        panelDerecho.setLayout(new BoxLayout(panelDerecho, BoxLayout.Y_AXIS));
        panelDerecho.add(Box.createVerticalStrut(30)); // ↓ Desplaza elementos hacia abajo

        JTextField textField = new JTextField();
        textField.setPreferredSize(new Dimension(250, 30));
        textField.setMaximumSize(new Dimension(250, 30));

        JButton btnAdd = new JButton("+");
        btnAdd.setAlignmentX(Component.CENTER_ALIGNMENT);

        panelDerecho.add(textField);
        panelDerecho.add(Box.createVerticalStrut(10));
        panelDerecho.add(btnAdd);

        panelCentral.add(panelDerecho, BorderLayout.CENTER);

        dialog.add(panelCentral, BorderLayout.CENTER);

        // --- Panel inferior: botones centrados
        JPanel panelInferior = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        JButton btnModificar = new JButton("Modificar");
        JButton btnCancelar = new JButton("Cancelar");

        btnCancelar.addActionListener(e -> dialog.dispose());
        btnModificar.addActionListener(e -> {
            VentanaGrupos ventana = new VentanaGrupos();
            ventana.mostrarVentana();
        });
        btnAdd.addActionListener(e ->{
        	List<Grupo> grupos = AppChat.INSTANCE.getListaGrupos();
        	boolean nombreCogido = false;
        	for (Grupo grupo : grupos) {
				if (grupo.getNombre().equals(textField.getText())) {
					nombreCogido = true;
				}
			}
        	if(nombreCogido) {
        		JOptionPane.showMessageDialog(dialog, "Nombre de grupo usado", "Error", JOptionPane.ERROR_MESSAGE);
                return;
        	}
        });

        panelInferior.add(btnModificar);
        panelInferior.add(btnCancelar);

        dialog.add(panelInferior, BorderLayout.SOUTH);   
        dialog.setVisible(true);
    }


//PANEL IZQUIERDO
    
    private JScrollPane crearPanelIzquierdo() {
    	
        panelChats = new JPanel();
        panelChats.setLayout(new BoxLayout(panelChats, BoxLayout.Y_AXIS));
        panelChats.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        panelChats.setPreferredSize(null);
        panelChats.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

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
        
//        for (Contacto contacto : contactos) {
//
//            if (contacto instanceof ContactoIndividual) {
//                String otroNumero = ((ContactoIndividual) contacto).getMovil();
//                List<Mensaje> conversacion = AppChat.INSTANCE.buscarMensajes("", otroNumero, "");
//                
//                String ultimo = "";           
//                if (!conversacion.isEmpty()) {
//            		for(Mensaje mensaje: conversacion.reversed()) {
//            			System.out.println("lol "+mensaje.getTexto());
//            			if(!mensaje.getTexto().isEmpty()) {
//            				ultimo = mensaje.getTexto();
//            				break;
//            			}
//            		}
////                	ultimo = conversacion.get(conversacion.size() - 1).getTexto();
//                }
//                if(!contacto.getNombre().isEmpty()) {
//                	panelChats.add(crearElementoChat(contacto.getNombre(), ultimo, ((ContactoIndividual) contacto).getMovil()));
//                }
//                else {
//                	panelChats.add(crearElementoChat("", ultimo, ((ContactoIndividual) contacto).getMovil()));
//                }
//            } else {
//                continue; //TODO GRUPOS
//            }  
//        }
        for (Contacto contacto : contactos) {
            if (contacto instanceof ContactoIndividual) {
            	String otroNumero = ((ContactoIndividual) contacto).getMovil();
                List<Mensaje> conversacion = AppChat.INSTANCE.buscarMensajes("", otroNumero, "");
                
                String ultimo = "";           
                if (!conversacion.isEmpty()) {
            		for(Mensaje mensaje: conversacion.reversed()) {
            			System.out.println("lol "+mensaje.getTexto());
            			if(!mensaje.getTexto().isEmpty()) {
            				ultimo = mensaje.getTexto();
            				break;
            			}
            		}
//                	ultimo = conversacion.get(conversacion.size() - 1).getTexto();
                }

                JPanel elemento;
                if(!contacto.getNombre().isEmpty()) {
                    elemento = crearElementoChat(contacto.getNombre(), ultimo, ((ContactoIndividual) contacto).getMovil());
                } else {
                    elemento = crearElementoChat("", ultimo, ((ContactoIndividual) contacto).getMovil());
                }
                
                elemento.setBorder(StyleUtils.createPanelBorder());
                
                panelChats.add(elemento);
                panelChats.add(Box.createRigidArea(new Dimension(0, 10)));
            } else {
                continue; //TODO GRUPOS
            }  
        }


        JScrollPane scroll = new JScrollPane(panelChats);
        scroll.setPreferredSize(new Dimension(250, 0));
        scroll.getVerticalScrollBar().setUnitIncrement(15);
        return scroll;
    }

    @SuppressWarnings("deprecation")
	private JPanel crearElementoChat(String nombre, String ultimoMensaje, String movil) {
    	JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        panel.setBackground(StyleUtils.BACKGROUND_DARK);

        panel.setPreferredSize(new Dimension(200, 60));
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));

        //Añadimos un mouse listener para diferenciar el color del chat usado
        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
            	if (panelChatSeleccionado != null) {
                    panelChatSeleccionado.setBackground(StyleUtils.BACKGROUND_DARK);
                }

                //Color azul claro para el chat seleccionado
                panel.setBackground(new Color(220, 240, 255));
                panel.setBackground(StyleUtils.BACKGROUND_DARKER);
                panelChatSeleccionado = panel;	
                contactoChat = AppChat.getInstance().usuarioActual.getContactoIndividual(movil);
                mostrarConversacion((ContactoIndividual) contactoChat);
            }
        });
        
        
        JLabel icono = new JLabel();
        icono.setPreferredSize(new Dimension(60, 40));
        icono.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        
        
        String path = AppChat.INSTANCE.getImagenContacto(movil);
        
        ImageIcon iconoImagen = new ImageIcon(getClass().getResource(path));
        Image imagenEscalada = iconoImagen.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        icono.setIcon(new ImageIcon(imagenEscalada));
        icono.setText("");
        
        
        
        JPanel texto = new JPanel(new GridLayout(2, 1));
        texto.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
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
            refrescarPanelDerecho();
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
    
    private void refrescarPanelDerecho() {
        frame.remove(panelDerecho); 
        panelDerecho = crearPanelDerecho();
        frame.add(panelDerecho, BorderLayout.CENTER); 
        frame.revalidate();
        frame.repaint();
    }

//PANEL DERECHO
    
    private JPanel crearPanelDerecho() {
        panelDerecho = new JPanel(new BorderLayout());
        //panelDerecho.setMaximumSize(new Dimension(400, 700));
        //panelDerecho.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 100)); // top, left, bottom, right

        chat = new JPanel();      
        chat.setLayout(new BoxLayout(chat, BoxLayout.Y_AXIS));
        
        //chat.setMaximumSize(new Dimension(400, 700));  
        
        JScrollPane scrollPane = new JScrollPane(chat);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
 

        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getVerticalScrollBar().setUnitIncrement(10);

        JPanel panelEnvio = new JPanel(new BorderLayout(5, 5));
        
        JTextField enviarMensaje = new JTextField();
        JButton btnEnviar = new JButton("->");

        panelEnvio.add(enviarMensaje, BorderLayout.CENTER);
        panelEnvio.add(btnEnviar, BorderLayout.EAST);
        panelEnvio.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        panelDerecho.add(scrollPane, BorderLayout.CENTER);
        panelDerecho.add(panelEnvio, BorderLayout.SOUTH);
       
        btnEnviar.addActionListener(e -> {
        	String texto = enviarMensaje.getText();
        	if (!texto.isEmpty()) {
        		AppChat.INSTANCE.enviarMensajeContacto((ContactoIndividual)contactoChat, texto, -1, TipoMensaje.ENVIADO);
        		BubbleText burbuja = new BubbleText(chat, texto, StyleUtils.BACKGROUND_DARKER,
            			AppChat.INSTANCE.usuarioActual.getNombre(), BubbleText.SENT);
            	chat.add(burbuja);
            	List<Mensaje> conversacion = AppChat.INSTANCE.buscarMensajes("", "", contactoChat.getNombre());
            	String ultimo = "";
            	if (!conversacion.isEmpty()) {
            		for(Mensaje mensaje: conversacion.reversed()) {
            			if(!mensaje.getTexto().isEmpty()) {
            				ultimo = mensaje.getTexto();
            				break;
            			}
            		}
                }
            	
            	
            	//Ponemos el campo de texto limpio y refrescamos el panel izquierdo para que se vea en la previsualizacion el nuevo ultimo mensaje
            	enviarMensaje.setText("");
                refrescarPanelIzquierdo();
        	}
        });
	        
      
        return panelDerecho;
    }
    
    private void mostrarConversacion(ContactoIndividual c) {
        chat.removeAll();
        Usuario usuarioActual = AppChat.getInstance().usuarioActual;
        String miNumero = usuarioActual.getMovil();
        List<Mensaje> mensajes = AppChat.INSTANCE.buscarMensajes("", c.getMovil(), "");
        BubbleText burbuja;
        String otraPersonaHeader = c.getNombre();
        if(otraPersonaHeader.isEmpty()) {
        	otraPersonaHeader = c.getMovil();
        }
        for (Mensaje mensaje : mensajes) {
            boolean enviado = mensaje.getContacto_emisor().getMovil().equals(miNumero);
            if(mensaje.getEmoji() == -1) {
            	if(enviado) {
            		burbuja = new BubbleText(chat, mensaje.getTexto(), StyleUtils.BACKGROUND_DARKER,
                			mensaje.getContacto_emisor().getNombre(), BubbleText.SENT );
            	}
            	else {
            		burbuja = new BubbleText(chat, mensaje.getTexto(),StyleUtils.ACCENT_COLOR,
            				otraPersonaHeader, BubbleText.RECEIVED);
            	}
            	
            }else{
            	
				if(enviado) {
					burbuja = new BubbleText(chat, mensaje.getEmoji(), StyleUtils.BACKGROUND_DARKER ,
	            			mensaje.getContacto_emisor().getNombre(), BubbleText.SENT, 12);    		
            	}
            	else {
            		burbuja = new BubbleText(chat, mensaje.getEmoji(),StyleUtils.ACCENT_COLOR,
            				otraPersonaHeader, BubbleText.RECEIVED, 12);
            	}
            	
            }
            chat.add(burbuja);
        }
        
        chat.revalidate();
        chat.repaint();
    }    
}
