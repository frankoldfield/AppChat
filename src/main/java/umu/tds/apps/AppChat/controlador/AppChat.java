package umu.tds.apps.AppChat.controlador;

import java.time.LocalDate;

import umu.tds.apps.AppChat.dominio.ContactoIndividual;
import umu.tds.apps.AppChat.dominio.Grupo;
import umu.tds.apps.AppChat.dominio.TipoMensaje;
import umu.tds.apps.AppChat.dominio.Usuario;
import umu.tds.apps.AppChat.persistencia.RepositorioUsuarios;

public class AppChat {

	public Usuario usuarioActual;
	public RepositorioUsuarios repositorio;
	public static AppChat INSTANCE = new AppChat();
	
	public int enviarMensaje(Usuario usuario_emisor, Usuario usuario_receptor) {
		//Llama a usuario_emisor.registrarMensaje(usuario_receptor)
		//Llama a usuario_receptor.registrarMensaje(usuario_emisor)
		return 0;
	}
	
	public int enviarMensaje(Usuario usuario_emisor, Usuario[] grupo_receptor) {
		//Llama a usuario_emisor.registrarMensaje(grupo_receptor)
		//for usuario_receptor in grupo_receptor:
		//	Llama a usuario_receptor.registrarMensaje(usuario_emisor)
		return 0;
	}
	
	public Usuario getUsuario(String numero_telefono) { //Desde aquí se llama a getUsuario del repositorio
		
		return repositorio.buscarUsuarioPorMovil(numero_telefono);
	}
	
	public ContactoIndividual agregarContacto(String numeroTelefono, String username) { //Desde aquí se le dice al usuario que registre un nuevo contacto
		//usuarioActual.registrarContacto(numeroTelefono, username)
		return null;
	}
	
	public ContactoIndividual agregarContacto(int numeroTelefono) { //Desde aquí se le dice al usuario que registre un nuevo contacto
		//usuarioActual.registrarContacto(numeroTelefono)
		return null;
	}
	
	public Grupo registrarGrupo(String[] contactos_grupo) { //Desde aquí se le dice al usuario que registre un nuevo grupo
		//usuarioActual.registrarContacto(nuevo_contacto)
		return null;
	}
	
	public int getPremium() {
		//usuarioActual.getPremium();
		return 0;
	}

	
	public int removePremium() {
		//usuarioActual.removePremium();
		return 0;
	}

	public void registrarUsuario(String string, String string2, String string3, LocalDate of, String string4,
			String string5) {
		// TODO Auto-generated method stub
		
	}

	public void login(String string, String string2) {
		// TODO Auto-generated method stub
		
	}

	public void enviarMensajeContacto(ContactoIndividual c1, String string, int i, TipoMensaje enviado) {
		// TODO Auto-generated method stub
		
	}
}
