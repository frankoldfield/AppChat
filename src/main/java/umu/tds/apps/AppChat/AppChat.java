package umu.tds.apps.AppChat;

public class AppChat {

	public Usuario usuarioActual;
	public RepositorioUsuarios repositorio;
	
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
	
	public Usuario getUsuario(int numero_telefono) { //Desde aquí se llama a getUsuario del repositorio
		
		return repositorio.getUsuario(numero_telefono);
	}
	
	public int registrarContacto(int numeroTelefono, String username) { //Desde aquí se le dice al usuario que registre un nuevo contacto
		//usuarioActual.registrarContacto(numeroTelefono, username)
		return 0;
	}
	
	public int registrarContacto(int numeroTelefono) { //Desde aquí se le dice al usuario que registre un nuevo contacto
		//usuarioActual.registrarContacto(numeroTelefono)
		return 0;
	}
	
	public int registrarGrupo(String[] contactos_grupo) { //Desde aquí se le dice al usuario que registre un nuevo grupo
		//usuarioActual.registrarContacto(nuevo_contacto)
		return 0;
	}
	
	public int getPremium() {
		//usuarioActual.getPremium();
		return 0;
	}

	
	public int removePremium() {
		//usuarioActual.removePremium();
		return 0;
	}
}
