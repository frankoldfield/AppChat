package umu.tds.apps.AppChat.persistencia.imp;

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
import umu.tds.apps.AppChat.dominio.Grupo;
import umu.tds.apps.AppChat.dominio.Mensaje;
import umu.tds.apps.AppChat.dominio.TipoMensaje;
import umu.tds.apps.AppChat.persistencia.abstracta.GrupoDAO;

public class TDSGrupoDAO implements GrupoDAO{

	private static final String GRUPO = "grupo";
	private static final String NOMBRE = "nombre";
	private static final String CONTACTOS = "contactos";
	
	private static ServicioPersistencia servPersistencia;
	private static TDSGrupoDAO unicaInstancia = null;

	/*
	 * Patron singleton
	 */
	public static TDSGrupoDAO getInstance() {
		if (unicaInstancia == null) {
			unicaInstancia = new TDSGrupoDAO();
		}
			return unicaInstancia;
	}

	private TDSGrupoDAO() {
		servPersistencia = FactoriaServicioPersistencia.getInstance().getServicioPersistencia();
	}

	@Override
	public void create(Grupo grupo) {
		Entidad eGrupo = null;
		boolean existe = true;
		eGrupo = servPersistencia.recuperarEntidad(grupo.getId());
		if (eGrupo == null) {
			existe = false;
		}
		// si ya existe no se crea de nuevo
		if (existe) {
			return;
		}
		
		eGrupo = new Entidad();
		eGrupo.setNombre(GRUPO);
		eGrupo.setPropiedades(new ArrayList<Propiedad>(Arrays.asList(
            new Propiedad(NOMBRE, grupo.getNombre()),
            new Propiedad(CONTACTOS, obtenerIdsContactos(grupo.getContactos()))
        )));

		eGrupo = servPersistencia.registrarEntidad(eGrupo);
		grupo.setId(eGrupo.getId());
	}

    @Override
    public void delete(Grupo grupo) {
        Entidad eGrupo = servPersistencia.recuperarEntidad(grupo.getId());
        servPersistencia.borrarEntidad(eGrupo);
    }

	@Override
	public void update(Grupo grupo) {

		Entidad eContacto = servPersistencia.recuperarEntidad(grupo.getId());

		for (Propiedad prop : eContacto.getPropiedades()) {
	        if (prop.getNombre().equals(NOMBRE)) {
	            prop.setValor(grupo.getNombre());
	        } else if (prop.getNombre().equals(CONTACTOS)) {
	            prop.setValor(obtenerIdsContactos(grupo.getContactos()));
	        }
	        servPersistencia.modificarPropiedad(prop);
	    }
	}
	
    @Override
    public Grupo get(int id) {
        Entidad eGrupo = servPersistencia.recuperarEntidad(id);
        String nombre = servPersistencia.recuperarPropiedadEntidad(eGrupo, NOMBRE);
        String contactos = servPersistencia.recuperarPropiedadEntidad(eGrupo, CONTACTOS);

        Grupo grupo = new Grupo(nombre, obtenerContactosDesdeIds(contactos));
        grupo.setId(id);
        return grupo;
    }

    @Override
    public List<Grupo> getAll() {
        List<Entidad> entidades = servPersistencia.recuperarEntidades(GRUPO);
        List<Grupo> grupos = new LinkedList<>();
        for (Entidad e : entidades) {
        	grupos.add(get(e.getId()));
        }
        return grupos;
    }
    
  //PASAR DE ARRAY A STRING
    private String obtenerIdsContactos(List<ContactoIndividual> contactos) {
        StringBuilder sb = new StringBuilder();
        for (Contacto c : contactos) {
            sb.append(c.getId()).append(" ");
        }
        return sb.toString().trim();
    }
    
    private List<ContactoIndividual> obtenerContactosDesdeIds(String contactosStr) {
        List<ContactoIndividual> contactos = new ArrayList<>();
        TDSContactoIndividualDAO daoContactoIndividual = TDSContactoIndividualDAO.getInstance();
        if (contactosStr == null || contactosStr.isEmpty()) {
            return contactos;
        }
        String[] ids = contactosStr.split(" ");
        for (String idStr : ids) {
            int id = Integer.parseInt(idStr);
            Entidad eContacto = servPersistencia.recuperarEntidad(id);
            if (eContacto == null) continue;
            
            ContactoIndividual c = daoContactoIndividual.get(id);

            if (c != null) {
                contactos.add(c);
            }
        }
        
        return contactos;
    }
}
