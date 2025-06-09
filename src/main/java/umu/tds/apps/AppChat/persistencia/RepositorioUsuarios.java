package umu.tds.apps.AppChat.persistencia;

import umu.tds.apps.AppChat.dominio.Usuario;

public class RepositorioUsuarios {
	
	public Usuario[] usuarios;
	public static RepositorioUsuarios INSTANCE = new RepositorioUsuarios();

	public Usuario buscarUsuarioPorMovil(String numero_telefono) {
		// TODO Auto-generated method stub
		//return new Usuario("ñpñ", "lol", "aaaa", "scororo", true);
		return null;
	}
}
