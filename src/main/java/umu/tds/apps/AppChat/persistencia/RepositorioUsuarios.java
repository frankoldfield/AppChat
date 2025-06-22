package umu.tds.apps.AppChat.persistencia;

import java.util.List;

import umu.tds.apps.AppChat.dominio.Usuario;
import umu.tds.apps.AppChat.persistencia.imp.TDSFactoriaDAO;
import umu.tds.apps.AppChat.persistencia.imp.TDSUsuarioDAO;

public class RepositorioUsuarios {
	
	public static RepositorioUsuarios unicaInstancia = new RepositorioUsuarios();
	public TDSUsuarioDAO usuarioDAO;
	
	public static RepositorioUsuarios getInstance() {
		if (unicaInstancia == null) {
			unicaInstancia =  new RepositorioUsuarios();
		}
		return unicaInstancia;
	}
	
	public RepositorioUsuarios() {
		TDSFactoriaDAO factoriaDAO = TDSFactoriaDAO.getInstance();
		usuarioDAO = factoriaDAO.getUsuarioDAO();
	}

	public Usuario getUsuario(int id) {
		return usuarioDAO.get(id);
	}
	
	public List<Usuario> getAll() {
		return usuarioDAO.getAll();
	}
	
	public Usuario buscarUsuarioPorMovil(String numero_telefono) {
		for(Usuario usuario: usuarioDAO.getAll()) {
			if(usuario.getMovil().equals(numero_telefono)) {
				return usuario;
			}
		}
		return null;
	}
	
	public Usuario add(Usuario usuario) {
		Usuario usuarioNuevo = usuarioDAO.create(usuario);
		return usuarioNuevo;
	}

	public void update(Usuario usuario) {
		usuarioDAO.update(usuario);
		
	}
	
	
}
