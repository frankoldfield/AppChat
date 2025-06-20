package umu.tds.apps.AppChat.persistencia.abstracta;

import java.util.List;

import umu.tds.apps.AppChat.dominio.ContactoIndividual;

public interface ContactoIndividualDAO {

	void create(ContactoIndividual contactoindividual);
	void delete(ContactoIndividual contactoindividual);
	void update(ContactoIndividual contactoindividual);
	ContactoIndividual get(int id);
	List<ContactoIndividual> getAll();
}
