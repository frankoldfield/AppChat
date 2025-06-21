package umu.tds.apps.AppChat.premium;

import java.util.List;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.*;

import umu.tds.apps.AppChat.dominio.ContactoIndividual;
import umu.tds.apps.AppChat.dominio.Grupo;
import umu.tds.apps.AppChat.dominio.Mensaje;
import umu.tds.apps.AppChat.dominio.TipoMensaje;

public class ExportPDF {
	
	
	public static ExportPDF INSTANCE = new ExportPDF();
	Font fuenteEmisor = new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD, BaseColor.BLUE);
	Font fuenteMensaje = new Font(Font.FontFamily.HELVETICA, 11, Font.NORMAL, BaseColor.DARK_GRAY);
	Font fuenteReceptor = new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD, BaseColor.GREEN.darker());

	public void exportarAPDF(List<Mensaje> conversacion, String nombreUsuario) throws FileNotFoundException, DocumentException{
		if (conversacion.isEmpty()) return;
		
		FileOutputStream archivo = new FileOutputStream("C:\\Users\\frank\\OneDrive - UNIVERSIDAD DE MURCIA\\Escritorio\\hola.pdf");
//		FileOutputStream archivo = new FileOutputStream("usuarios/"+nombreUsuario+".pdf");

		Document documento = new Document();
		PdfWriter.getInstance(documento, archivo);
		documento.open();
		
		Paragraph encabezado = new Paragraph();
		
		if(conversacion.getFirst().getContacto_receptor() instanceof Grupo) {
			Grupo grupo = (Grupo) conversacion.getFirst().getContacto_receptor();
			encabezado.add(new Paragraph("Conversación de "+nombreUsuario+" con el Grupo: " + grupo.getNombre(), new Font(Font.FontFamily.HELVETICA, 16, Font.BOLD)));
			encabezado.add(new Paragraph("Integrantes:", new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD)));

			for (ContactoIndividual contacto : grupo.getContactos()) {
				String nombre = (contacto.getNombre() != null && !contacto.getNombre().isEmpty()) ? contacto.getNombre() : contacto.getMovil();
				String linea = "- " + nombre + " (" + contacto.getMovil() + ")";
				encabezado.add(new Paragraph(linea, new Font(Font.FontFamily.HELVETICA, 10)));
			}
			encabezado.add(new Paragraph("Mensajes:", new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD)));
			encabezado.setSpacingAfter(20f);
			documento.add(encabezado);
			
			for (Mensaje msg : conversacion) {
				Paragraph p = creaParrafo(msg);
				p.setIndentationRight(msg.getTipo().equals(TipoMensaje.ENVIADO) ? 0f : 100f); // izquierda para receptor
            	p.setAlignment(Element.ALIGN_LEFT);
	            
	            documento.add(p);
	        }
		}
		else {
			
			String nombreContacto = conversacion.getFirst().getContacto_emisor().getNombre();
			if (nombreContacto.isEmpty())
				nombreContacto = conversacion.getFirst().getContacto_emisor().getMovil();

			encabezado.add(new Paragraph("Conversación de "+nombreUsuario+" con: " + nombreContacto, new Font(Font.FontFamily.HELVETICA, 16, Font.BOLD)));
			encabezado.setSpacingAfter(20f);
			documento.add(encabezado);
			for (Mensaje msg : conversacion) {
				Paragraph p = creaParrafo(msg);
				
	            if(msg.getTipo().equals(TipoMensaje.ENVIADO)) {
	            	p.setIndentationLeft(msg.getTipo().equals(TipoMensaje.ENVIADO) ? 100f : 0f); // derecha para emisor
	            	p.setAlignment(Element.ALIGN_RIGHT);
	            }
	            else {
	            	p.setIndentationRight(msg.getTipo().equals(TipoMensaje.RECIBIDO) ? 0f : 100f); // izquierda para receptor
	            	p.setAlignment(Element.ALIGN_LEFT);
	            }
	            
	            documento.add(p);
	        }
		}
	      documento.close();
		
   	}

	private Paragraph creaParrafo (Mensaje msg) {
		String emisor = msg.getContacto_emisor().getNombre();
		String texto;
		if(msg.getEmoji()==-1) {
			texto = msg.getTexto();
		}
		else {
			texto = "(Emoji)";
		}
        

        Paragraph p = new Paragraph();

        Chunk mensaje = new Chunk(texto, fuenteMensaje);
        
        if(emisor.isEmpty()) {
        	emisor = msg.getContacto_emisor().getMovil();
        }
        Chunk nombre;
        if(msg.getTipo().equals(TipoMensaje.ENVIADO) && msg.getContacto_receptor() instanceof ContactoIndividual) {
            nombre = new Chunk("  " + emisor);
            p.add(mensaje);
            p.add(nombre);
        }
        else {
            nombre = new Chunk(emisor + "  ");
            p.add(nombre);
            p.add(mensaje);
        }
        
        p.setSpacingAfter(8f);
        return p;
	}

}



