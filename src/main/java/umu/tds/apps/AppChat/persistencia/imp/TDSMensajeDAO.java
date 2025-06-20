package umu.tds.apps.AppChat.persistencia.imp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;

import beans.Entidad;
import beans.Propiedad;
import tds.driver.FactoriaServicioPersistencia;
import tds.driver.ServicioPersistencia;
import umu.tds.apps.AppChat.dominio.Mensaje;
import umu.tds.apps.AppChat.persistencia.abstracta.MensajeDAO;

public class TDSMensajeDAO implements MensajeDAO{
	private static final String MENSAJE = "mensaje";
	private static final String TEXTO = "texto";
	private static final String HORA = "hora";
	private static final String EMOJI = "emoji";
	private static final String TIPO = "tipo";
	private static final String CONTACTO_EMISOR = "contacto_emisor";
	private static final String CONTACTO_RECEPTOR = "contacto_receptor";

	private static ServicioPersistencia servPersistencia;
	private static TDSMensajeDAO unicaInstancia = null;

	/*
	 * Patron singleton
	 */
	public static TDSMensajeDAO getUnicaInstancia() {
		if (unicaInstancia == null) {
			return new TDSMensajeDAO();
		} else
			return unicaInstancia;
	}

	private TDSMensajeDAO() {
		servPersistencia = FactoriaServicioPersistencia.getInstance().getServicioPersistencia();
	}

	@Override
	public void create(Mensaje mensaje) {
		Entidad eMensaje = null;
		boolean existe = true;
		eMensaje = servPersistencia.recuperarEntidad(mensaje.getId());
		if (eMensaje == null) {
			existe = false;
		}
		// si ya existe no se crea de nuevo
		if (existe) {
			return;
		}
		eMensaje = new Entidad();
		eMensaje.setNombre(MENSAJE);
		eMensaje.setPropiedades(new ArrayList<Propiedad>(
				Arrays.asList(new Propiedad(TEXTO, mensaje.getTexto()), new Propiedad(HORA, mensaje.getHora().toString()),
						new Propiedad(EMOJI, String.valueOf(mensaje.getEmoji())),
						new Propiedad(TIPO, mensaje.getTipo().toString()), new Propiedad(CONTACTO_EMISOR, String.valueOf(mensaje.getContacto_emisor().getId())), 
						new Propiedad(CONTACTO_RECEPTOR, String.valueOf(mensaje.getContacto_receptor().getId())))));
		// si no existe la registramos
		eMensaje = servPersistencia.registrarEntidad(eMensaje);
		mensaje.setId(eMensaje.getId());
	}

	@Override
	public void delete(Mensaje mensaje) {
		Entidad eMensaje = servPersistencia.recuperarEntidad(mensaje.getId());
		servPersistencia.borrarEntidad(eMensaje);
	}

	@Override
	public void update(Mensaje mensaje) {

		Entidad eMensaje = servPersistencia.recuperarEntidad(mensaje.getId());

		// Pasar la lista de interpretes a un solo string para meterlo en el servidor de
		// persistencia

		for (Propiedad prop : eMensaje.getPropiedades()) {

			if (prop.getNombre().equals(TEXTO)) {
				prop.setValor(mensaje.getTexto());
			} else if (prop.getNombre().equals(HORA)) {
				prop.setValor(mensaje.getHora().toString());
			} else if (prop.getNombre().equals(EMOJI)) {
				prop.setValor(String.valueOf(mensaje.getEmoji()));
			} else if (prop.getNombre().equals(TIPO)) {
				prop.setValor(mensaje.getTipo().toString());
			} else if (prop.getNombre().equals(CONTACTO_EMISOR)) {
				prop.setValor(String.valueOf(mensaje.getContacto_emisor().getId()));
			} else if (prop.getNombre().equals(CONTACTO_RECEPTOR)) {
				prop.setValor(String.valueOf(mensaje.getContacto_receptor().getId()));
			}
			servPersistencia.modificarPropiedad(prop);
		}

	}

	@Override
	public Mensaje get(int id) {
		TDSContactoIndividualDAO daoContactoIndividual = TDSContactoIndividualDAO.getUnicaInstancia();
		TDSGrupoDAO daoGrupo = TDSGrupoDAO.getUnicaInstancia();
		
		Entidad eMensaje = servPersistencia.recuperarEntidad(id);
		String texto = servPersistencia.recuperarPropiedadEntidad(eMensaje, TEXTO);
		String hora = servPersistencia.recuperarPropiedadEntidad(eMensaje, HORA);
		String emoji = servPersistencia.recuperarPropiedadEntidad(eMensaje, EMOJI);
		String tipo = servPersistencia.recuperarPropiedadEntidad(eMensaje, TIPO);
		String contacto_emisor = servPersistencia.recuperarPropiedadEntidad(eMensaje, CONTACTO_EMISOR);
		String contacto_receptor = servPersistencia.recuperarPropiedadEntidad(eMensaje, CONTACTO_RECEPTOR);
		
		Entidad eContacto_emisor = servPersistencia.recuperarEntidad(Integer.parseInt(contacto_emisor));
		
		
		Entidad eContacto_receptor = servPersistencia.recuperarEntidad(Integer.parseInt(contacto_receptor));
		
		if(eContacto_receptor.getPropiedades().get(0).getValor()=="contacto_individual") {
			
		}
		else {
			
		}
		
		
		Mensaje mensaje= new Mensaje(texto, LocalDateTime.parse(hora), tipo, , );
		cancion.setId(id);
		return cancion;
	}

	@Override
	public List<Mensaje> getAll() {
		List<Entidad> eCanciones = servPersistencia.recuperarEntidades(CANCION);
		List<Cancion> canciones = new LinkedList<Cancion>();
		for (Entidad eCancion : eCanciones) {
			canciones.add(get(eCancion.getId()));
		}
		return canciones;
	}
}
