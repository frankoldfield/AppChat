package umu.tds.apps.AppChat.vistas;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.util.ArrayList;
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
import umu.tds.apps.AppChat.utils.StyleUtils;

import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.time.format.DateTimeFormatter;

public class VentanaPrincipal {
    private JFrame frame;
    private JPanel panelChats;
    private JPanel chat;
    private JScrollPane scrollPanelIzquierdo;
    private JPanel panelChatSeleccionado;
    int filtroContactos = 0;
    private Contacto contactoChat;
    private JPanel panelDerecho;
    private AppChat appChat;
    private String descuentoSeleccionado;
    private JLabel precioFinallbl;
    private double chatSize;
    private JPanel chatConMargen;
    private JScrollPane scrollPane;
    
    public VentanaPrincipal() {
        initialize();
    }

    public void mostrarVentana() {
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        
    }

    private void initialize() {
    	precioFinallbl = new JLabel("Precio final: ");
    	appChat = AppChat.getInstance();
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
        JButton btnGrupos = new JButton("Grupos");
        
        JPanel contenedorPremium = new JPanel(new CardLayout());
        JButton btnPremium = new JButton("$ Premium");
        
        JComboBox<String> comboPremium = new JComboBox<>(new String[]{"Exportar a PDF", "Cancelar Premium"});

        contenedorPremium.add(btnPremium, "BOTON");
        contenedorPremium.add(comboPremium, "COMBO");

        CardLayout layout = (CardLayout) contenedorPremium.getLayout();
        if (appChat.usuarioActual.isPremium()) {
            layout.show(contenedorPremium, "COMBO");
        } else {
            layout.show(contenedorPremium, "BOTON");
        }

        btnPremium.addActionListener(e -> {
        	mostrarVentajasPremium(contenedorPremium);           
        });

        comboPremium.addActionListener(e -> {
        	String seleccion = (String) comboPremium.getSelectedItem();
            if ("Cancelar Premium".equals(seleccion)) {
            	appChat.removePremium();
                layout.show(contenedorPremium, "BOTON");
            }
            if ("Exportar a PDF".equals(seleccion)) {
            	appChat.exportPDF(contactoChat);
            }
        });
        String nombre = appChat.usuarioActual.getNombre();
        
        JLabel nombre1 = new JLabel(nombre);
        
        JLabel iconoUsuario = new JLabel();
        iconoUsuario.setPreferredSize(new Dimension(60, 40));
        iconoUsuario.setBorder(BorderFactory.createLineBorder(StyleUtils.ACCENT_COLOR));
        
        String path = appChat.usuarioActual.getImagen();

        ImageIcon iconoImagen = null;

        try {
            if (new File(path).isAbsolute()) {
                // Ruta absoluta del sistema
                iconoImagen = new ImageIcon(new File(path).toURI().toURL());
            } else {
                // Ruta relativa dentro del classpath (como en resources/)
                URL url = getClass().getResource(path);
                if (url != null) {
                    iconoImagen = new ImageIcon(url);
                } else {
                    throw new FileNotFoundException("No se encontró el recurso relativo: " + path);
                }
            }

            Image imagenEscalada = iconoImagen.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
            iconoUsuario.setIcon(new ImageIcon(imagenEscalada));
            iconoUsuario.setText("");

        } catch (Exception ex) {
            ex.printStackTrace();
            iconoUsuario.setText("Sin imagen");
            iconoUsuario.setIcon(null);
        }
        
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
                    iconoUsuario.revalidate(); 
                    iconoUsuario.repaint();    
                    appChat.cambiarImagen(rutaAbsoluta);
                }
        	}
        });
        
        btnContactos.addActionListener(e ->{ 
        	entrarContactos();

        });
        
        btnGrupos.addActionListener(e ->{ 
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
        panelSuperior.add(btnGrupos);
        panelSuperior.add(contenedorPremium);
        panelSuperior.add(nombre1);
        panelSuperior.add(iconoUsuario);
        

        return panelSuperior;
    }
    
    private void entrarContactos() {
        JDialog dialog = new JDialog(frame, "Lista de Contactos", true);
        dialog.setSize(500, 400);
        dialog.setLocationRelativeTo(frame);

        JPanel panelCentral = new JPanel();
        panelCentral.setLayout(new BoxLayout(panelCentral, BoxLayout.Y_AXIS));
        panelCentral.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));

        // Obtener la lista de contactos
        List<ContactoIndividual> contactos = appChat.getListaContactosIndividuales();

        // Crear los datos para la tabla
        String[] columnas = {"Nombre", "Teléfono", "Saludo"};
        String[][] datos = new String[contactos.size()][3];

        for (int i = 0; i < contactos.size(); i++) {
            ContactoIndividual c = contactos.get(i);
            datos[i][0] = c.getNombre();               // Asegúrate de que ContactoIndividual tiene este método
            datos[i][1] = String.valueOf(c.getMovil()); // Asegúrate de que devuelve un número
            datos[i][2] = appChat.getSaludo(c.getMovil());               // Asegúrate de que ContactoIndividual tiene este método
        }

        @SuppressWarnings("serial")
		DefaultTableModel modeloTabla = new DefaultTableModel(datos, columnas) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        JTable tablaContactos = new JTable(modeloTabla);
        JScrollPane scrollTabla = new JScrollPane(tablaContactos);
        scrollTabla.setPreferredSize(new Dimension(450, 200));
        scrollTabla.setAlignmentX(Component.LEFT_ALIGNMENT);

        panelCentral.add(scrollTabla);
        panelCentral.add(Box.createVerticalStrut(10));

        dialog.add(panelCentral, BorderLayout.CENTER);

        // Panel inferior con botones
        JPanel panelInferior = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        JButton btnAddContacto = new JButton("Nuevo contacto");
        JButton btnCancelar = new JButton("Cancelar");

        btnCancelar.addActionListener(e -> dialog.dispose());
        btnAddContacto.addActionListener(e -> {
            mostrarDialogoNuevoContacto();
            dialog.dispose();
        });

        panelInferior.add(btnAddContacto);
        panelInferior.add(btnCancelar);

        dialog.add(panelInferior, BorderLayout.SOUTH);
        dialog.setVisible(true);
    }


	private void mostrarVentajasPremium(JPanel contenedorPremium) {
        CardLayout layout = (CardLayout) contenedorPremium.getLayout();
        JDialog dialogo = new JDialog(frame, "Ventajas Premium", true);
        dialogo.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialogo.setSize(500, 400);
        dialogo.setLocationRelativeTo(frame);

        JPanel contenido = new JPanel(new BorderLayout(10, 10));
        contenido.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        List<JPanel> panelesSeleccionados = new ArrayList<>();
        Dimension tamañoPanel = new Dimension(220, 70);
        JPanel panelVentajas = new JPanel();
        panelVentajas.setLayout(new BoxLayout(panelVentajas, BoxLayout.Y_AXIS));

        JButton btnAceptar = new JButton("Aceptar");
        btnAceptar.setEnabled(false);

        // Panel fijo: precio original
        JPanel panel3 = crearPanel("Precio original", "Obtén Premium por el precio original", tamañoPanel, panelesSeleccionados, btnAceptar);
        panelVentajas.add(panel3);
        panelVentajas.add(Box.createVerticalStrut(10));

        // Panel 1: descuento por mensajes
        JPanel panel1 = crearPanel("Descuento por mensajes", "Recibe un 1% de descuento por cada mensaje enviado o recibido (hasta 50%)", tamañoPanel, panelesSeleccionados, btnAceptar);
        panelVentajas.add(panel1);
        panelVentajas.add(Box.createVerticalStrut(10));

        // Panel 2: descuento por fecha
        JPanel panel2 = crearPanel("Descuento por fecha", "Recibe un 1% de descuento por cada día que lleves registrado (hasta 50%)", tamañoPanel, panelesSeleccionados, btnAceptar);
        panelVentajas.add(panel2);

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnCancelar = new JButton("Cancelar");
        
        panelVentajas.add(precioFinallbl);
        btnAceptar.addActionListener(e -> {
            layout.show(contenedorPremium, "COMBO");
            dialogo.dispose();
            appChat.buyPremium();
        });

        btnCancelar.addActionListener(e -> dialogo.dispose());

        panelBotones.add(btnAceptar);
        panelBotones.add(btnCancelar);

        contenido.add(panelVentajas, BorderLayout.CENTER);
        contenido.add(panelBotones, BorderLayout.SOUTH);
        dialogo.setContentPane(contenido);
        dialogo.setVisible(true);
    }
    
    private JPanel crearPanel(String titulo, String texto, Dimension tamañoPanel, List<JPanel> paneles, JButton btnAceptar) {
        JPanel panel = new JPanel();
        panel.setPreferredSize(tamañoPanel);
        panel.setBorder(BorderFactory.createTitledBorder(titulo));
        panel.add(new JLabel(texto));

        panel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                for (JPanel p : paneles) {
                    p.setBackground(null);
                    p.putClientProperty("seleccionado", false);
                }

                panel.setBackground(StyleUtils.BACKGROUND_DARKER);
                panel.putClientProperty("seleccionado", true);
                descuentoSeleccionado = titulo;
                double precioFinal = appChat.getPrecioPremium(descuentoSeleccionado.toLowerCase().replace(" ", "_"));
                precioFinallbl.setText("Precio final: "+precioFinal);
                btnAceptar.setEnabled(true);
            }
        });

        panel.putClientProperty("seleccionado", false);
        paneles.add(panel);
        return panel;
    }


    private void entrarGrupos() {
        JDialog dialog = new JDialog(frame, "Crear/Modificar grupo", true);
        dialog.setSize(500, 400);
        dialog.setLocationRelativeTo(frame);

        JPanel panelCentral = new JPanel();
        panelCentral.setLayout(new BoxLayout(panelCentral, BoxLayout.Y_AXIS));
        panelCentral.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20)); // margen uniforme

        List<Grupo> grupos = appChat.getListaGrupos();
        List<String> nombres = grupos.stream()
            .map(Grupo::getNombre)
            .collect(Collectors.toList());
        String[] arrayNombres = nombres.toArray(new String[0]);

        JList<String> listaGrupos = new JList<>(arrayNombres);
        listaGrupos.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        JScrollPane scroll = new JScrollPane(listaGrupos);
        scroll.setPreferredSize(new Dimension(450, 150));
        scroll.setBorder(BorderFactory.createTitledBorder("Grupos"));
        scroll.setAlignmentX(Component.LEFT_ALIGNMENT); // alineación izquierda

        JLabel labelNuevoGrupo = new JLabel("Crear grupo nuevo");
        labelNuevoGrupo.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel panelCrearGrupo = new JPanel();
        panelCrearGrupo.setLayout(new BoxLayout(panelCrearGrupo, BoxLayout.X_AXIS));
        panelCrearGrupo.setAlignmentX(Component.LEFT_ALIGNMENT); // alineación izquierda
        panelCrearGrupo.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));

        JTextField textField = new JTextField();
        textField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));

        JButton btnAdd = new JButton("+");
        btnAdd.setPreferredSize(new Dimension(50, 30));

        panelCrearGrupo.add(textField);
        panelCrearGrupo.add(Box.createHorizontalStrut(10));
        panelCrearGrupo.add(btnAdd);

        panelCentral.add(scroll);
        panelCentral.add(Box.createVerticalStrut(10));
        panelCentral.add(labelNuevoGrupo);
        panelCentral.add(Box.createVerticalStrut(5));
        panelCentral.add(panelCrearGrupo);

        dialog.add(panelCentral, BorderLayout.CENTER);

        JPanel panelInferior = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        JButton btnModificar = new JButton("Modificar");
        JButton btnCancelar = new JButton("Cancelar");

        btnCancelar.addActionListener(e -> dialog.dispose());

        btnModificar.addActionListener(e -> {
        	String grupoSeleccionado = listaGrupos.getSelectedValue();
        	if(!(grupoSeleccionado == null || grupoSeleccionado.isEmpty())) {
        		VentanaGrupos ventana = new VentanaGrupos(this, listaGrupos.getSelectedValue());
                dialog.dispose();
                ventana.mostrarVentana();
        	}
        });

        btnAdd.addActionListener(e -> {
            boolean nombreCogido = false;
            for (Grupo grupo : grupos) {
                if (grupo.getNombre().equals(textField.getText())) {
                    nombreCogido = true;
                    break;
                }
            }
            if (nombreCogido) {
                JOptionPane.showMessageDialog(dialog, "Nombre de grupo usado", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                VentanaGrupos ventana = new VentanaGrupos(this, textField.getText());
                dialog.dispose();
                ventana.mostrarVentana();
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

        List<Contacto> contactos = appChat.getListaContactos();
        switch(filtroContactos) {
        case 0:
        	break;
        case 1:
        	contactos = contactos.stream().filter(c -> !c.getNombre().isEmpty()).collect(Collectors.toList());
        	break;
        case 2:
        	contactos = contactos.stream().filter(c -> c.getNombre().isEmpty()).collect(Collectors.toList());
        	break;
        default:
        	break;
        }
        
        class ContactoConUltimoMensaje {
            Contacto contacto;
            Mensaje ultimoMensaje;

            ContactoConUltimoMensaje(Contacto contacto, Mensaje ultimoMensaje) {
                this.contacto = contacto;
                this.ultimoMensaje = ultimoMensaje;
            }
        }

        List<ContactoConUltimoMensaje> listaOrdenada = new ArrayList<>();

        for (Contacto contacto : contactos) {
            List<Mensaje> conversacion;
            if (contacto instanceof ContactoIndividual) {
                String otroNumero = ((ContactoIndividual) contacto).getMovil();
                conversacion = appChat.getConversacionIndividual(otroNumero);
            } else {
                conversacion = appChat.getConversacionGrupo(contacto.getNombre());
            }

            Mensaje ultimo = null;
            // Buscar el último mensaje no vacío
            for (int i = conversacion.size() - 1; i >= 0; i--) {
                Mensaje mensaje = conversacion.get(i);
                if (!mensaje.getTexto().isEmpty()) {
                    ultimo = mensaje;
                    break;
                }
            }

            listaOrdenada.add(new ContactoConUltimoMensaje(contacto, ultimo));
        }

        listaOrdenada.sort((a, b) -> {
            if (a.ultimoMensaje == null && b.ultimoMensaje == null) return 0;
            if (a.ultimoMensaje == null) return 1;
            if (b.ultimoMensaje == null) return -1;
            return b.ultimoMensaje.getHora().compareTo(a.ultimoMensaje.getHora()); // más reciente primero
        });

        for (ContactoConUltimoMensaje par : listaOrdenada) {
            Contacto contacto = par.contacto;
            Mensaje ultimo = par.ultimoMensaje;

            JPanel elemento;

            DateTimeFormatter formatterHora = DateTimeFormatter.ofPattern("HH:mm"); // 24h, o "hh:mm a" para 12h con AM/PM

            String textoMensaje = (ultimo == null) ? "" : "(" + ultimo.getHora().format(formatterHora)+") "+ultimo.getTexto();

            if (contacto instanceof ContactoIndividual) {
                elemento = crearElementoChat(contacto.toString(), textoMensaje, ((ContactoIndividual) contacto).getMovil());
            } else {
                elemento = crearElementoChat(contacto.getNombre(), textoMensaje, "");
            }

            elemento.setBorder(StyleUtils.createPanelBorder());
            panelChats.add(elemento);
            panelChats.add(Box.createRigidArea(new Dimension(0, 10)));
        }



        JScrollPane scroll = new JScrollPane(panelChats);
        scroll.setPreferredSize(new Dimension(250, 0));
        scroll.getVerticalScrollBar().setUnitIncrement(15);
        return scroll;
    }

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
                if(movil.isEmpty()) {
                	contactoChat = appChat.usuarioActual.getGrupo(nombre);
                }
                else {
                	contactoChat = appChat.usuarioActual.getContactoIndividual(movil);
                }
                
                mostrarConversacion(contactoChat);
            }
        });
        
        
        JLabel icono = new JLabel();
        icono.setPreferredSize(new Dimension(60, 40));
        icono.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        
        
        String path = appChat.getImagenContacto(movil);
        
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
            	mostrarDialogoRegistraContacto(movil);
            });
            panel.add(btnAñadir, BorderLayout.EAST);
        } else {
        	texto.add(new JLabel(nombre));
        }
        texto.add(new JLabel(ultimoMensaje));
        
        return panel;
    }
    
    private void mostrarDialogoRegistraContacto(String movil) {
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
        	appChat.actualizarContacto(txtNombre.getText(), txtTelefono.getText());
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
    
    private void mostrarDialogoNuevoContacto() {
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
        	if(txtNombre.getText().isEmpty() || txtTelefono.getText().isEmpty()) {
        		JOptionPane.showMessageDialog(dialog, "Rellena todos los campos", "Error", JOptionPane.ERROR_MESSAGE);
                return;
        	}
        	if(appChat.existeUsuario(txtTelefono.getText()) && !appChat.contactoYaGuardado(txtTelefono.getText())) {
        		appChat.agregarContacto(txtNombre.getText(), txtTelefono.getText());
                refrescarPanelIzquierdo();
                refrescarPanelDerecho();
                dialog.dispose();
        	}
        	else if(!appChat.existeUsuario(txtTelefono.getText())){
        		JOptionPane.showMessageDialog(dialog, "El número de teléfono no corresponde a ningún usuario registrado", "Error", JOptionPane.ERROR_MESSAGE);
                return;
        	}
        	else {
        		JOptionPane.showMessageDialog(dialog, "El número de teléfono ya está registrado como otro contacto", "Error", JOptionPane.ERROR_MESSAGE);
                return;
        	}
            
        });

        
        btnCancelar.addActionListener(e -> dialog.dispose());

        panelBotones.add(btnAceptar);
        panelBotones.add(btnCancelar);

        dialog.add(panelCampos, BorderLayout.CENTER);
        dialog.add(panelBotones, BorderLayout.SOUTH);

        dialog.setVisible(true);
    }
    
    
    public void refrescarPanelIzquierdo() {
        frame.remove(scrollPanelIzquierdo); 
        scrollPanelIzquierdo = crearPanelIzquierdo();
        frame.add(scrollPanelIzquierdo, BorderLayout.WEST); 
        frame.revalidate();
        frame.repaint();
    }
    
    public void refrescarPanelDerecho() {
        frame.remove(panelDerecho); 
        panelDerecho = crearPanelDerecho();
        frame.add(panelDerecho, BorderLayout.CENTER); 
        frame.revalidate();
        frame.repaint();
    }

//PANEL DERECHO
    
    private JPanel crearPanelDerecho() {
        panelDerecho = new JPanel(new BorderLayout());
        
        chat = new JPanel();      
        chat.setLayout(new BoxLayout(chat, BoxLayout.Y_AXIS));
//        chat.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));  // top, left, bottom, right
        chatConMargen = new JPanel(new BorderLayout());
        chatConMargen.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        chatConMargen.add(chat, BorderLayout.CENTER);

        scrollPane = new JScrollPane(chatConMargen);

//        JScrollPane scrollPane = new JScrollPane(chat);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getVerticalScrollBar().setUnitIncrement(10);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        scrollPane.setViewportView(chatConMargen);
        scrollPane.getViewport().addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
            	chatConMargen.setPreferredSize(new Dimension(scrollPane.getViewport().getWidth(), (int)chatSize+10));
            }
        });
        JPanel panelEnvio = new JPanel(new BorderLayout(5, 5));

        JTextField enviarMensaje = new JTextField();
        JButton btnEnviar = new JButton("->");

        // Botón de emojis
        JButton btnEmojiToggle = new JButton("😊"); // El botón para mostrar emojis
        JPopupMenu menuEmojis = new JPopupMenu();
        JPanel emojiPanel = new JPanel(new GridLayout(6, 4, 5, 5)); // 6 filas x 4 columnas

        for (int i = 0; i < BubbleText.MAXICONO; i++) {
            final int emojiIndex = i;
            ImageIcon iconoIter = BubbleText.getEmoji(emojiIndex);

            JButton btnEmoji = new JButton(iconoIter);
            btnEmoji.setPreferredSize(new Dimension(40, 40)); // tamaño opcional
            btnEmoji.setFocusPainted(false);
            btnEmoji.setBorderPainted(false);
            btnEmoji.setContentAreaFilled(false);

            btnEmoji.addActionListener(e -> {
            	if(contactoChat==null) {
            		return;
            	}
                // Enviar emoji
                if (contactoChat instanceof ContactoIndividual) {
                	appChat.enviarMensajeContacto((ContactoIndividual) contactoChat, "", emojiIndex, TipoMensaje.ENVIADO);
                } else {
                	appChat.enviarMensajeGrupo((Grupo) contactoChat, "", emojiIndex, TipoMensaje.ENVIADO);
                }

                BubbleText burbuja = new BubbleText(chat, emojiIndex, StyleUtils.BACKGROUND_DARKER,
                		appChat.usuarioActual.getNombre(), BubbleText.SENT, 12);
                chat.add(burbuja);
                chat.revalidate();
                chat.repaint();

                menuEmojis.setVisible(false);
                refrescarPanelIzquierdo();
            });

            emojiPanel.add(btnEmoji);
        }

        // OBLIGATORIO: establecer tamaño preferido del panel
        emojiPanel.setPreferredSize(new Dimension(4 * 45, 6 * 45)); // 4 columnas × 45px, 6 filas × 45px
        menuEmojis.add(emojiPanel);

        // MOSTRAR HACIA ARRIBA
        btnEmojiToggle.addActionListener(e -> {
            // Asegúrate de que el menú esté empaquetado correctamente
            menuEmojis.pack();
            Dimension menuSize = menuEmojis.getPreferredSize();

            // Mostrarlo sobre el botón
            menuEmojis.show(btnEmojiToggle, 0, -menuSize.height);
        });


        // Añadir a la interfaz
        panelEnvio.add(btnEmojiToggle, BorderLayout.WEST);
        panelEnvio.add(enviarMensaje, BorderLayout.CENTER);
        panelEnvio.add(btnEnviar, BorderLayout.EAST);
        panelEnvio.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        
        panelDerecho.add(scrollPane, BorderLayout.CENTER);
        panelDerecho.add(panelEnvio, BorderLayout.SOUTH);
       
        btnEnviar.addActionListener(e -> {
        	if(contactoChat==null) {
        		return;
        	}
        	String texto = enviarMensaje.getText();
        	if (!texto.isEmpty()) {
        		if(contactoChat instanceof ContactoIndividual) {
        			appChat.enviarMensajeContacto((ContactoIndividual)contactoChat, texto, -1, TipoMensaje.ENVIADO);
        		}
        		else {
        			appChat.enviarMensajeGrupo((Grupo)contactoChat, texto, -1, TipoMensaje.ENVIADO);
        		}
        		
        		BubbleText burbuja = new BubbleText(chat, texto, StyleUtils.BACKGROUND_DARKER,
        				appChat.usuarioActual.getNombre(), BubbleText.SENT);
            	chat.add(burbuja);
            	enviarMensaje.setText("");
                refrescarPanelIzquierdo();
        	}
        });
	        
      
        return panelDerecho;
    }
    
    private void mostrarConversacion(Contacto contacto) {
    	chatSize=0;
        chat.removeAll();
        Usuario usuarioActual = appChat.usuarioActual;
        String miNumero = usuarioActual.getMovil();
        List<Mensaje> mensajes;
        if(contacto instanceof ContactoIndividual) {
        	mensajes = appChat.getConversacionIndividual(((ContactoIndividual) contacto).getMovil());
        }
        else {
        	mensajes = appChat.getConversacionGrupo(contacto.getNombre());
        }
        BubbleText burbuja;
        String otraPersonaHeader = contacto.getNombre();
        if(contacto instanceof ContactoIndividual && otraPersonaHeader.isEmpty()) {
        	otraPersonaHeader = ((ContactoIndividual) contacto).getMovil();
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
            chatSize+=burbuja.getPreferredSize().getHeight();
            chat.add(burbuja);
        }
        
        chat.revalidate();
        chat.repaint();
        chatConMargen.setPreferredSize(new Dimension(scrollPane.getViewport().getWidth(), (int)chatSize+10));
    }
    
    
}
