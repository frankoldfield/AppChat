package umu.tds.apps.AppChat.persistencia.imp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import beans.Entidad;
import beans.Propiedad;
import tds.driver.FactoriaServicioPersistencia;
import tds.driver.ServicioPersistencia;
import umu.tds.apps.AppChat.dominio.Contacto;
import umu.tds.apps.AppChat.dominio.ContactoIndividual;
import umu.tds.apps.AppChat.dominio.Usuario;
import umu.tds.apps.AppChat.persistencia.abstracta.FactoriaDAO;
import umu.tds.apps.AppChat.persistencia.abstracta.UsuarioDAO;

public class TDSUsuarioDAO implements UsuarioDAO{
	
	private static final String USUARIO = "usuario";
	private static final String NOMBRE = "nombre";
	private static final String APELLIDOS = "apellidos";
	private static final String PASSWORD = "password";
	private static final String MOVIL = "movil";
	private static final String FECHA_NACIMIENTO = "fechaNacimiento";
	private static final String IMAGEN = "imagen";
	private static final String SALUDO = "saludo";
	private static final String FECHA_CREACION = "fechaCreacion";
	private static final String PREMIUM = "premium";
	private static final String CONTACTO_PROPIO = "contactoPropio";
	private static final String CONTACTOS = "contactos";
	
	private static ServicioPersistencia servPersistencia;
	private static TDSUsuarioDAO unicaInstancia = null;
	

	/*
	 * Patron singleton
	 */
	public static TDSUsuarioDAO getInstance() {
		if (unicaInstancia == null) {
			unicaInstancia = new TDSUsuarioDAO();
		}
		return unicaInstancia;
	}

	private TDSUsuarioDAO() {
		servPersistencia = FactoriaServicioPersistencia.getInstance().getServicioPersistencia();
	}
	
	//PASAR DE ARRAY A STRING
	TDSContactoIndividualDAO daoContactoIndividual = TDSContactoIndividualDAO.getInstance();
    TDSGrupoDAO daoGrupo = TDSGrupoDAO.getInstance();
	private String obtenerIdsContactos(ArrayList<Contacto> contactos) {
		StringBuilder sb = new StringBuilder();
		for (Contacto c : contactos) {
			sb.append(c.getId()).append(" ");		
		}
		return sb.toString().trim();
	}
	
	//PASAR DE STRING A ARRAYS
	private List<Contacto> obtenerContactosDesdeIds(String contactosStr) {
		List<Contacto> contactos = new ArrayList<>();
		if (contactosStr == null || contactosStr.isEmpty()) {
			return contactos;
		}
		String[] ids = contactosStr.split(" ");
		for (String idStr : ids) {
			int id = Integer.parseInt(idStr);
			Entidad eContacto = servPersistencia.recuperarEntidad(id);
			if (eContacto == null) continue;

			String tipo = eContacto.getNombre();
			Contacto c = null;

			if ("contactoIndividual".equalsIgnoreCase(tipo)) {
				c = daoContactoIndividual.get(id);
			} else if ("grupo".equalsIgnoreCase(tipo)) {
				c = daoGrupo.get(id);
			}

			if (c != null) {
				contactos.add(c);
			}
		}
		return contactos;
	}
	
	
	@Override
	public Usuario create(Usuario usuario) {
		Entidad eUsuario = null;
		boolean existe = true;
		eUsuario = servPersistencia.recuperarEntidad(usuario.getId());
		if (eUsuario == null) {
			existe = false;
		}
		// si ya existe no se crea de nuevo
		if (existe) {
			return null;
		}
		eUsuario = new Entidad();
		eUsuario.setNombre(USUARIO);
		eUsuario.setPropiedades(new ArrayList<>(Arrays.asList(
				new Propiedad(NOMBRE, usuario.getNombre()),
				new Propiedad(APELLIDOS, usuario.getApellidos()),
				new Propiedad(PASSWORD, usuario.getPassword()),
				new Propiedad(MOVIL, usuario.getMovil()),
				new Propiedad(FECHA_NACIMIENTO, usuario.getFechaNacimiento().toString()),
				new Propiedad(IMAGEN, usuario.getImagen()),
				new Propiedad(SALUDO, usuario.getSaludo()),
				new Propiedad(FECHA_CREACION, usuario.getFechaCreacion().toString()),
				new Propiedad(PREMIUM, String.valueOf(usuario.isPremium())),
				new Propiedad(CONTACTO_PROPIO, String.valueOf(usuario.getContactoPropio())),
				new Propiedad(CONTACTOS, obtenerIdsContactos(usuario.getContactos()))
		)));

		eUsuario = servPersistencia.registrarEntidad(eUsuario);
		usuario.setId(eUsuario.getId());
		return usuario;
	}

	@Override
	public void delete(Usuario usuario) {
		Entidad eUsuario = servPersistencia.recuperarEntidad(usuario.getId());
		servPersistencia.borrarEntidad(eUsuario);
	}

	@Override
	public void update(Usuario usuario) {
		Entidad eUsuario = servPersistencia.recuperarEntidad(usuario.getId());

		for (Propiedad prop : eUsuario.getPropiedades()) {
			if (prop.getNombre().equals(NOMBRE)) {
				prop.setValor(usuario.getNombre());
			} else if (prop.getNombre().equals(APELLIDOS)) {
				prop.setValor(usuario.getApellidos());
			} else if (prop.getNombre().equals(PASSWORD)) {
				prop.setValor(usuario.getPassword());
			} else if (prop.getNombre().equals(MOVIL)) {
				prop.setValor(usuario.getMovil());
			} else if (prop.getNombre().equals(FECHA_NACIMIENTO)) {
				prop.setValor(usuario.getFechaNacimiento().toString());
			} else if (prop.getNombre().equals(IMAGEN)) {
				prop.setValor(usuario.getImagen());
			} else if (prop.getNombre().equals(SALUDO)) {
				prop.setValor(usuario.getSaludo());
			} else if (prop.getNombre().equals(FECHA_CREACION)) {
				prop.setValor(usuario.getFechaCreacion().toString());
			} else if (prop.getNombre().equals(PREMIUM)) {
				prop.setValor(String.valueOf(usuario.isPremium()));
			} else if (prop.getNombre().equals(CONTACTO_PROPIO)) {
				prop.setValor(usuario.getContactoPropio().toString());
			} else if (prop.getNombre().equals(CONTACTOS)) {
				prop.setValor(obtenerIdsContactos(usuario.getContactos()));
			}
			servPersistencia.modificarPropiedad(prop);
		}
	}
	
	@Override
	public Usuario get(int id) {
		Entidad eUsuario = servPersistencia.recuperarEntidad(id);

		String nombre = servPersistencia.recuperarPropiedadEntidad(eUsuario, NOMBRE);
		String apellidos = servPersistencia.recuperarPropiedadEntidad(eUsuario, APELLIDOS);
		String password = servPersistencia.recuperarPropiedadEntidad(eUsuario, PASSWORD);
		String movil = servPersistencia.recuperarPropiedadEntidad(eUsuario, MOVIL);
		LocalDate fechaNac = LocalDate.parse(servPersistencia.recuperarPropiedadEntidad(eUsuario, FECHA_NACIMIENTO));
		String imagen = servPersistencia.recuperarPropiedadEntidad(eUsuario, IMAGEN);
		String saludo = servPersistencia.recuperarPropiedadEntidad(eUsuario, SALUDO);
		LocalDateTime fechaCreacion = LocalDateTime.parse(servPersistencia.recuperarPropiedadEntidad(eUsuario, FECHA_CREACION));
		boolean premium = Boolean.parseBoolean(servPersistencia.recuperarPropiedadEntidad(eUsuario, PREMIUM));
		String contactosStr = servPersistencia.recuperarPropiedadEntidad(eUsuario, CONTACTOS);

		Usuario usuario = new Usuario(nombre, apellidos, password, movil, fechaNac, imagen, saludo);
		usuario.setPremium(premium);
		usuario.setFechaCreacion(fechaCreacion);
		usuario.getContactos().addAll(obtenerContactosDesdeIds(contactosStr));
		usuario.setId(id);

		return usuario;
	}

	@Override
	public List<Usuario> getAll() {
		List<Usuario> usuarios = new LinkedList<>();
		List<Entidad> entidades = servPersistencia.recuperarEntidades(USUARIO);

		for (Entidad e : entidades) {
			usuarios.add(get(e.getId()));
		}

		return usuarios;
	}
}
