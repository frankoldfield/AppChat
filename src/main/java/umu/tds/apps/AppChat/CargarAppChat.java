package umu.tds.apps.AppChat;

import java.time.LocalDate;

import umu.tds.apps.AppChat.controlador.AppChat;
import umu.tds.apps.AppChat.dominio.ContactoIndividual;
import umu.tds.apps.AppChat.dominio.Mensaje;
import umu.tds.apps.AppChat.persistencia.RepositorioUsuarios;
import umu.tds.apps.AppChat.vistas.*;
import umu.tds.apps.AppChat.dominio.TipoMensaje;
import umu.tds.apps.AppChat.dominio.Usuario;

public class CargarAppChat {
	

	public static void main(String[] args) {
		AppChat appChat = AppChat.getInstance();
		VentanaLogin ventana = new VentanaLogin();
		ventana.mostrarVentana();

		appChat.registrarUsuario("jesus", "apellido", "aa", "11", "aa", LocalDate.of(1960, 10, 03),"/usuarios/fotoJGM.png", "Hola, soy jesus");
		appChat.registrarUsuario("elena", "apellido","bb", "22", "bb", LocalDate.of(1995, 12, 28), "/src/main/resources/usuarios/712347.jpg", "hola, soy elena");
		appChat.registrarUsuario("rosalia", "apellido","cc", "33", "cc", LocalDate.of(2000, 5, 15), "/usuarios/rosalia.jpg", "hola, soy rosalia");
		appChat.registrarUsuario("diego", "apellido","dd", "44", "dd", LocalDate.of(1970, 5, 11), "/usuarios/foto-diego.png", "hola, soy diego");
		appChat.registrarUsuario("anne", "apellido","ee", "55", "ee", LocalDate.of(1990, 3, 28), "/usuarios/annetaylor.jpg", "hola, soy anne");
		appChat.registrarUsuario("anne", "apellido","ee", "56", "ee", LocalDate.of(1990, 3, 28), "/usuarios/annetaylor.jpg", "hola, soy anne");
		appChat.registrarUsuario("anne", "apellido","ee", "57", "ee", LocalDate.of(1990, 3, 28), "/usuarios/annetaylor.jpg", "hola, soy anne");
		appChat.registrarUsuario("anne", "apellido","ee", "58", "ee", LocalDate.of(1990, 3, 28), "/usuarios/annetaylor.jpg", "hola, soy anne");
		appChat.registrarUsuario("anne", "apellido","ee", "59", "ee", LocalDate.of(1990, 3, 28), "/usuarios/annetaylor.jpg", "hola, soy anne");
		appChat.registrarUsuario("anne", "apellido","ee", "50", "ee", LocalDate.of(1990, 3, 28), "/usuarios/annetaylor.jpg", "hola, soy anne");
		appChat.registrarUsuario("anne", "apellido","ee", "51", "ee", LocalDate.of(1990, 3, 28), "/usuarios/annetaylor.jpg", "hola, soy anne");
		
		appChat.login("11", "aa");
		
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
		
		//ContactoIndividual c1 =appChat.agregarContacto("jesus", "11");
		ContactoIndividual c1 = RepositorioUsuarios.INSTANCE.buscarUsuarioPorMovil("22").getContactoIndividual("11");
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
		appChat.enviarMensajeContacto(c1, "hola", 3, TipoMensaje.ENVIADO);
	    appChat.enviarMensajeContacto(c4, "Juegas esta semana?", -1, TipoMensaje.ENVIADO);
	    appChat.enviarMensajeContacto(c5, "adios", -1, TipoMensaje.ENVIADO);
	    
	    System.out.println("Fin de la carga de datos");
	}

}
