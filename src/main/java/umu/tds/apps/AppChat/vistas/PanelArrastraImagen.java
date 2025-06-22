package umu.tds.apps.AppChat.vistas;

import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.datatransfer.DataFlavor;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDropEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JEditorPane;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import umu.tds.apps.AppChat.utils.StyleUtils;

import java.awt.Color;
import javax.swing.ImageIcon;

import java.awt.SystemColor;

public class PanelArrastraImagen extends JDialog {

	private static final long serialVersionUID = 1L;
	private  JPanel contentPane = new JPanel();
	private List<File> archivosSubidos = new ArrayList<File>();
	private JLabel lblArchivoSubido;
	private JButton btnAceptar;
	private JButton btnCancelar;

	@SuppressWarnings("serial")
	public PanelArrastraImagen(JFrame owner) {
		super(owner, "Agregar fotos", true);
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		getContentPane().setBackground(StyleUtils.BACKGROUND_DARK);
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		
		contentPane = new JPanel();
		contentPane.setBackground(StyleUtils.BACKGROUND_DARK);
		getContentPane().add(contentPane, BorderLayout.CENTER);
		
		JEditorPane editorPane = new JEditorPane();
        editorPane.setEditable(false);
        contentPane.add(editorPane);
        
        JLabel imagenLabel = new JLabel();
        imagenLabel.setHorizontalAlignment(JLabel.CENTER);
        contentPane.add(imagenLabel);
      
        editorPane.setContentType("text/html");  
        editorPane.setText("<h1>Agregar Foto</h1><br> Puedes arrastrar el fichero aquí.  </p>");
		editorPane.setDropTarget(new DropTarget() {
			public synchronized void drop(DropTargetDropEvent evt) {
		        try {
		            evt.acceptDrop(DnDConstants.ACTION_COPY);
		            @SuppressWarnings("unchecked")
					List<File> droppedFiles = (List<File>) evt.getTransferable().
		            		getTransferData(DataFlavor.javaFileListFlavor);
		            
		            if (!droppedFiles.isEmpty()) {
		            	File file = droppedFiles.get(0);
		                archivosSubidos.add(file);
		            
		         // Cargar la imagen en el JLabel
                    ImageIcon icon = new ImageIcon(file.getAbsolutePath());
                    Image img = icon.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
                    imagenLabel.setIcon(new ImageIcon(img));
		          }
		            
		        } catch (Exception ex) {
		            ex.printStackTrace();
		        }
		    }
		});
		
		
		lblArchivoSubido = new JLabel();
		lblArchivoSubido.setVisible(false);
		contentPane.add(lblArchivoSubido);
			
		JButton botonElegir = new JButton("Seleccionar de tu ordenador");
		botonElegir.setForeground(Color.WHITE);
		botonElegir.setBackground(SystemColor.textHighlight);
		contentPane.add(botonElegir);
		botonElegir.addActionListener(ev -> {
		    JFileChooser fileChooser = new JFileChooser();
		    fileChooser.setDialogTitle("Seleccionar imagen");
		    fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		    fileChooser.setAcceptAllFileFilterUsed(false);

		    //Filtro solo para imágenes
		    fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter(
		        "Imágenes (JPG, PNG, GIF)", "jpg", "jpeg", "png", "gif"
		    ));

		    int resultado = fileChooser.showOpenDialog(this);
		    if (resultado == JFileChooser.APPROVE_OPTION) {
		        File archivo = fileChooser.getSelectedFile();
		        if (archivo != null && archivo.exists()) {
		            archivosSubidos.clear();
		            archivosSubidos.add(archivo);

		            //Mostrar la imagen
		            ImageIcon icon = new ImageIcon(archivo.getAbsolutePath());
		            Image img = icon.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
		            imagenLabel.setIcon(new ImageIcon(img));
		        }
		    }
		});
		
		// Panel de botones Aceptar y Cancelar
        JPanel panelBotones = new JPanel();
        btnAceptar = new JButton("Aceptar");
        btnCancelar = new JButton("Cancelar");

        // Acción del botón Aceptar
        btnAceptar.addActionListener(ev -> dispose());

        // Acción del botón Cancelar
        btnCancelar.addActionListener(ev -> {
                archivosSubidos.clear(); // Limpia la lista si se cancela
                dispose();
        });

        panelBotones.add(btnAceptar);
        panelBotones.add(btnCancelar);
        add(panelBotones, BorderLayout.SOUTH);

        setLocationRelativeTo(owner); // Centra el diálogo en la ventana principal
    }

	
	public List<File> showDialog() {
		this.setVisible(true);
		return archivosSubidos;
	}
	
}
