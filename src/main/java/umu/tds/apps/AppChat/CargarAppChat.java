package umu.tds.apps.AppChat;

import java.time.LocalDate;

import umu.tds.apps.AppChat.controlador.AppChat;
import umu.tds.apps.AppChat.dominio.ContactoIndividual;
import umu.tds.apps.AppChat.vistas.*;
import umu.tds.apps.AppChat.dominio.TipoMensaje;

import com.formdev.flatlaf.intellijthemes.materialthemeuilite.FlatMaterialDarkerIJTheme;

import java.awt.Color;
import java.util.logging.Logger;

import javax.swing.UIManager;

public class CargarAppChat {
	private static final Logger LOGGER = Logger.getLogger(CargarAppChat.class.getName());
	private static final double PRECIO_PREMIUM = 100.0;
	public static void main(String[] args) {
		LOGGER.info("Iniciando aplicación...");
		FlatMaterialDarkerIJTheme.setup();
        // Personalizar algunos colores específicos 
        UIManager.put("Component.arc", 10); // Bordes redondeados
        UIManager.put("Button.arc", 10);    // Bordes redondeados para botones
        UIManager.put("ScrollBar.thumbArc", 999); // Scrollbar redondeada
        UIManager.put("ScrollBar.width", 10); // Scrollbar más delgada
        
        // Colores personalizados
        UIManager.put("TitlePane.foreground", Color.WHITE);
        UIManager.put("Button.focusedBackground", UIManager.getColor("Button.background").darker());
        UIManager.put("TabbedPane.selectedBackground", UIManager.getColor("TabbedPane.background").brighter());
        UIManager.put("Component.foreground", Color.BLACK);
        LOGGER.info("Look and feel configurado correctamente");
        
        AppChat appChat = AppChat.getInstance();
        appChat.setPrecioPremium(PRECIO_PREMIUM);
		
		cargarStubs(appChat);
		
		VentanaLogin ventana = new VentanaLogin();
		LOGGER.info("Aplicación cargada");
		ventana.mostrarVentana();
	}
	
	public static void cargarStubs(AppChat appChat) {
		LOGGER.info("Cargando stubs");
		
		appChat.registrarUsuario("jesus", "apellido", "aa", "11", "aa", LocalDate.of(1960, 10, 03),"/usuarios/fotoJGM.png", "Hola, soy jesus");
		appChat.registrarUsuario("elena", "apellido","bb", "22", "bb", LocalDate.of(1995, 12, 28), "/usuarios/fotoJGM.png", "hola, soy elena");
		appChat.registrarUsuario("rosalia", "apellido","cc", "33", "cc", LocalDate.of(2000, 5, 15), "/usuarios/fotoJGM.png", "hola, soy rosalia");
		appChat.registrarUsuario("diego", "apellido","dd", "44", "dd", LocalDate.of(1970, 5, 11), "/usuarios/abbetatkir.jpg", "hola, soy diego");
		appChat.registrarUsuario("anne", "apellido","ee", "55", "ee", LocalDate.of(1990, 3, 28), "/usuarios/abbetatkir.jpg", "hola, soy anne");
		appChat.registrarUsuario("anne", "apellido","ee", "56", "ee", LocalDate.of(1990, 3, 28), "/usuarios/abbetatkir.jpg", "hola, soy anne");
		
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
		
		appChat.login("22", "bb");
		
		c3 = appChat.agregarContacto("rosalia", "33");
		
		
		ContactoIndividual c1 = appChat.repoUsuarios.buscarUsuarioPorMovil("22").getContactoIndividual("11");
		ContactoIndividual c4 = appChat.agregarContacto("diego", "44");
		ContactoIndividual c5 = appChat.agregarContacto("anne", "55");
		appChat.agregarContacto("anne", "56");
		appChat.agregarContacto("anne", "56");
		
		appChat.enviarMensajeContacto(c1, "Vienes este finde?", -1, TipoMensaje.ENVIADO);
		appChat.enviarMensajeContacto(c1, "", 3, TipoMensaje.ENVIADO);
		appChat.enviarMensajeContacto(c1, "", 3, TipoMensaje.ENVIADO);
	    appChat.enviarMensajeContacto(c4, "Juegas esta semana?", -1, TipoMensaje.ENVIADO);
	    appChat.enviarMensajeContacto(c5, "adios", -1, TipoMensaje.ENVIADO);
	    
	    LOGGER.info("Stubs cargados correctamente");

	}

}
