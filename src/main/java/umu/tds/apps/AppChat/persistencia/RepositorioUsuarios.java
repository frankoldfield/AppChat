package umu.tds.apps.AppChat.persistencia;

import java.util.ArrayList;
import java.util.List;

import umu.tds.apps.AppChat.dominio.Mensaje;
import umu.tds.apps.AppChat.dominio.Usuario;
import umu.tds.apps.AppChat.persistencia.imp.TDSFactoriaDAO;
import umu.tds.apps.AppChat.persistencia.imp.TDSMensajeDAO;
import umu.tds.apps.AppChat.persistencia.imp.TDSUsuarioDAO;

public class RepositorioUsuarios {
	
	public List<Usuario> usuarios = new ArrayList<Usuario>();
	public static RepositorioUsuarios unicaInstancia = new RepositorioUsuarios();
	public TDSUsuarioDAO usuarioDAO;
	
	public static RepositorioUsuarios getInstance() {
		if (unicaInstancia == null) {
			return new RepositorioUsuarios();
		} else
			return unicaInstancia;
	}
	
	public RepositorioUsuarios() {
		TDSFactoriaDAO factoriaDAO = TDSFactoriaDAO.getInstance();
		usuarioDAO = factoriaDAO.getUsuarioDAO();
	}
	
	public void addMensaje(Usuario usuario) {
		usuarioDAO.create(usuario);
	}

	public Usuario getUsuario(int id) {
		return usuarioDAO.get(id);
	}
	
	public Usuario buscarUsuarioPorMovil(String numero_telefono) {
		for(Usuario usuario: usuarioDAO.getAll()) {
			if(usuario.getMovil().equals(numero_telefono)) {
				return usuario;
			}
		}
		return null;
	}
	
	
}
