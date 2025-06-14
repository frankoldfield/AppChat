package umu.tds.apps.AppChat.persistencia;

import java.util.ArrayList;
import java.util.List;

import umu.tds.apps.AppChat.dominio.Usuario;

public class RepositorioUsuarios {
	
	public List<Usuario> usuarios = new ArrayList<Usuario>();
	public static RepositorioUsuarios INSTANCE = new RepositorioUsuarios();

	public Usuario buscarUsuarioPorMovil(String numero_telefono) {
		for(Usuario usuario: usuarios) {
			if(usuario.getMovil().equals(numero_telefono)) {
				return usuario;
			}
		}
		return null;
	}
	
	
}
