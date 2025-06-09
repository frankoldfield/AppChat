package umu.tds.apps.AppChat.persistencia;

import java.util.List;

import umu.tds.apps.AppChat.dominio.ContactoIndividual;

public interface ContactoIndividualDAO {

	void create(ContactoIndividual contactoindividual);
	boolean delete(ContactoIndividual contactoindividual);
	void update(ContactoIndividual contactoindividual);
	ContactoIndividual get(int id);
	List<ContactoIndividual> getAll();
}
