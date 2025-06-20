package umu.tds.apps.AppChat;

import java.time.LocalDate;

import umu.tds.apps.AppChat.controlador.AppChat;
import umu.tds.apps.AppChat.dominio.ContactoIndividual;
import umu.tds.apps.AppChat.dominio.Grupo;
import umu.tds.apps.AppChat.vistas.*;
import umu.tds.apps.AppChat.dominio.TipoMensaje;

import com.formdev.flatlaf.intellijthemes.materialthemeuilite.FlatMaterialDarkerIJTheme;

import java.awt.Color;
import java.util.ArrayList;
import java.util.logging.Logger;

import javax.swing.UIManager;

public class CargarAppChat {
	private static final Logger LOGGER = Logger.getLogger(CargarAppChat.class.getName());

	public static void main(String[] args) {
		
		
		
		
		
		FlatMaterialDarkerIJTheme.setup();
        LOGGER.info("Look and feel configurado correctamente");
        
        // Personalizar algunos colores específicos 
        UIManager.put("Component.arc", 10); // Bordes redondeados
        UIManager.put("Button.arc", 10);    // Bordes redondeados para botones
        UIManager.put("ScrollBar.thumbArc", 999); // Scrollbar redondeada
        UIManager.put("ScrollBar.width", 10); // Scrollbar más delgada
        
        // Colores personalizados
        UIManager.put("TitlePane.foreground", Color.WHITE);
        UIManager.put("Button.focusedBackground", UIManager.getColor("Button.background").darker());
        UIManager.put("TabbedPane.selectedBackground", UIManager.getColor("TabbedPane.background").brighter());
        UIManager.put("Component.foreground", Color.BLACK); // o cualquier otro color


        AppChat appChat = AppChat.getInstance();
        
       
        
        
        
		VentanaLogin ventana = new VentanaLogin();
		ventana.mostrarVentana();
		
		cargarStubs(appChat);
		
	}
	
	
	
	public static void cargarStubs(AppChat appChat) {
		appChat.registrarUsuario("jesus", "apellido", "aa", "11", "aa", LocalDate.of(1960, 10, 03),"/usuarios/fotoJGM.png", "Hola, soy jesus");
		appChat.registrarUsuario("elena", "apellido","bb", "22", "bb", LocalDate.of(1995, 12, 28), "/usuarios/fotoJGM.png", "hola, soy elena");
		appChat.registrarUsuario("rosalia", "apellido","cc", "33", "cc", LocalDate.of(2000, 5, 15), "/usuarios/fotoJGM.png", "hola, soy rosalia");
		appChat.registrarUsuario("diego", "apellido","dd", "44", "dd", LocalDate.of(1970, 5, 11), "/usuarios/abbetatkir.jpg", "hola, soy diego");
		appChat.registrarUsuario("anne", "apellido","ee", "55", "ee", LocalDate.of(1990, 3, 28), "/usuarios/abbetatkir.jpg", "hola, soy anne");
//		
//		appChat.registrarUsuario("jesus", "apellido", "aa", "11", "aa", LocalDate.of(1960, 10, 03),"/usuarios/fotoJGM.png", "Hola, soy jesus");
//		appChat.registrarUsuario("elena", "apellido","bb", "22", "bb", LocalDate.of(1995, 12, 28), "/usuarios/elena.png", "hola, soy elena");
//		appChat.registrarUsuario("rosalia", "apellido","cc", "33", "cc", LocalDate.of(2000, 5, 15), "/usuarios/rosalia.jpg", "hola, soy rosalia");
//		appChat.registrarUsuario("diego", "apellido","dd", "44", "dd", LocalDate.of(1970, 5, 11), "/usuarios/foto-diego.png", "hola, soy diego");
//		appChat.registrarUsuario("anne", "apellido","ee", "55", "ee", LocalDate.of(1990, 3, 28), "/usuarios/annetaylor.jpg", "hola, soy anne");
		appChat.registrarUsuario("anne", "apellido","ee", "56", "ee", LocalDate.of(1990, 3, 28), "/usuarios/abbetatkir.jpg", "hola, soy anne");
		appChat.registrarUsuario("anne", "apellido","ee", "57", "ee", LocalDate.of(1990, 3, 28), "/usuarios/abbetatkir.jpg", "hola, soy anne");
		appChat.registrarUsuario("anne", "apellido","ee", "58", "ee", LocalDate.of(1990, 3, 28), "/usuarios/abbetatkir.jpg", "hola, soy anne");
		appChat.registrarUsuario("anne", "apellido","ee", "59", "ee", LocalDate.of(1990, 3, 28), "/usuarios/abbetatkir.jpg", "hola, soy anne");
		appChat.registrarUsuario("anne", "apellido","ee", "50", "ee", LocalDate.of(1990, 3, 28), "/usuarios/abbetatkir.jpg", "hola, soy anne");
		appChat.registrarUsuario("anne", "apellido","ee", "51", "ee", LocalDate.of(1990, 3, 28), "/usuarios/abbetatkir.jpg", "hola, soy anne");
		
		appChat.login("11", "aa");
		appChat.buyPremium();
		
		ContactoIndividual c2 = appChat.agregarContacto("elena", "22");
		ContactoIndividual c3 = appChat.agregarContacto("rosalia", "33");
		
		appChat.enviarMensajeContacto(c2, "Hola, ¿cómo estás?", -1, TipoMensaje.ENVIADO);
		appChat.enviarMensajeContacto(c2, "", 2, TipoMensaje.ENVIADO);
		appChat.enviarMensajeContacto(c2, "hola", -1, TipoMensaje.ENVIADO);
		
		appChat.enviarMensajeContacto(c3, "Cuando cantas?", -1, TipoMensaje.ENVIADO);
		appChat.enviarMensajeContacto(c2, "", 6, TipoMensaje.ENVIADO);
		appChat.enviarMensajeContacto(c3, "holaaa", -1, TipoMensaje.ENVIADO);
		appChat.enviarMensajeContacto(c3, "hola", -1, TipoMensaje.ENVIADO);
//		ArrayList<ContactoIndividual> listaContactoGrupo = new ArrayList<ContactoIndividual>();
//		listaContactoGrupo.add(c2);
//		Grupo grupoLol = appChat.CrearOActualizarGrupo("grupoLOL", listaContactoGrupo);
//		
//		appChat.enviarMensajeGrupo(grupoLol, "Bon dia", -1, TipoMensaje.ENVIADO);
		
		appChat.login("22", "bb");
		
		c3 = appChat.agregarContacto("rosalia", "33");
		
		
		//ContactoIndividual c1 =appChat.agregarContacto("jesus", "11");
		ContactoIndividual c1 = appChat.repoUsuarios.buscarUsuarioPorMovil("22").getContactoIndividual("11");
		ContactoIndividual c4 = appChat.agregarContacto("diego", "44");
		ContactoIndividual c5 = appChat.agregarContacto("anne", "55");
		ContactoIndividual c6 = appChat.agregarContacto("anne", "55");
		ContactoIndividual c7 = appChat.agregarContacto("anne", "56");
		ContactoIndividual c8 = appChat.agregarContacto("anne", "57");
		ContactoIndividual c9 = appChat.agregarContacto("anne", "58");
		ContactoIndividual c10 = appChat.agregarContacto("anne", "59");
		ContactoIndividual c11 = appChat.agregarContacto("anne", "50");
		ContactoIndividual c12 = appChat.agregarContacto("anwwne", "51");
		ContactoIndividual c14 = appChat.agregarContacto("anwwn1212e", "51");
		
		
		appChat.enviarMensajeContacto(c1, "Vienes este finde?", -1, TipoMensaje.ENVIADO);
		appChat.enviarMensajeContacto(c1, "", 3, TipoMensaje.ENVIADO);
		appChat.enviarMensajeContacto(c1, "", 3, TipoMensaje.ENVIADO);
	    appChat.enviarMensajeContacto(c4, "Juegas esta semana?", -1, TipoMensaje.ENVIADO);
	    appChat.enviarMensajeContacto(c5, "adios", -1, TipoMensaje.ENVIADO);
	    
	    System.out.println("Fin de la carga de datos");
	}

}
