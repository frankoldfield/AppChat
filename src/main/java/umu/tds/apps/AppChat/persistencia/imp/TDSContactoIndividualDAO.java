package umu.tds.apps.AppChat.persistencia.imp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import beans.Entidad;
import beans.Propiedad;
import tds.driver.FactoriaServicioPersistencia;
import tds.driver.ServicioPersistencia;
import umu.tds.apps.AppChat.dominio.ContactoIndividual;
import umu.tds.apps.AppChat.persistencia.abstracta.ContactoIndividualDAO;

public class TDSContactoIndividualDAO implements ContactoIndividualDAO{

	private static final String CONTACTO_INDIVIDUAL = "contacto_individual";
	private static final String NOMBRE = "nombre";
	private static final String MOVIL = "movil";
	
	private static ServicioPersistencia servPersistencia;
	private static TDSContactoIndividualDAO unicaInstancia = null;

	public static TDSContactoIndividualDAO getInstance() {
		if (unicaInstancia == null) {
			unicaInstancia =  new TDSContactoIndividualDAO();
		}
		return unicaInstancia;
	}

	private TDSContactoIndividualDAO() {
		servPersistencia = FactoriaServicioPersistencia.getInstance().getServicioPersistencia();
	}

	@Override
	public ContactoIndividual create(ContactoIndividual contacto) {
		Entidad eContacto = null;
		boolean existe = true;
		eContacto = servPersistencia.recuperarEntidad(contacto.getId());
		if (eContacto == null) {
			existe = false;
		}
		// si ya existe no se crea de nuevo
		if (existe) {
			return contacto;
		}
		
		eContacto = new Entidad();
        eContacto.setNombre(CONTACTO_INDIVIDUAL);
        eContacto.setPropiedades(new ArrayList<Propiedad>(Arrays.asList(
            new Propiedad(NOMBRE, contacto.getNombre()),
            new Propiedad(MOVIL, contacto.getMovil())
        )));

        eContacto = servPersistencia.registrarEntidad(eContacto);
        contacto.setId(eContacto.getId());
        return contacto;
	}

    @Override
    public void delete(ContactoIndividual contacto) {
        Entidad eContacto = servPersistencia.recuperarEntidad(contacto.getId());
        servPersistencia.borrarEntidad(eContacto);
    }

	@Override
	public void update(ContactoIndividual contacto) {

		Entidad eContacto = servPersistencia.recuperarEntidad(contacto.getId());

		for (Propiedad prop : eContacto.getPropiedades()) {
	        if (prop.getNombre().equals(NOMBRE)) {
	            prop.setValor(contacto.getNombre());
	        } else if (prop.getNombre().equals(MOVIL)) {
	            prop.setValor(contacto.getMovil());
	        }
	        servPersistencia.modificarPropiedad(prop);
	    }
	}
	
    @Override
    public ContactoIndividual get(int id) {
        Entidad eContacto = servPersistencia.recuperarEntidad(id);
        String nombre = servPersistencia.recuperarPropiedadEntidad(eContacto, NOMBRE);
        String movil = servPersistencia.recuperarPropiedadEntidad(eContacto, MOVIL);

        ContactoIndividual contacto = new ContactoIndividual(nombre, movil);
        contacto.setId(id);
        return contacto;
    }

    @Override
    public List<ContactoIndividual> getAll() {
        List<Entidad> entidades = servPersistencia.recuperarEntidades(CONTACTO_INDIVIDUAL);
        List<ContactoIndividual> contactos = new LinkedList<>();
        for (Entidad e : entidades) {
            contactos.add(get(e.getId()));
        }
        return contactos;
    }

}
