package umu.tds.apps.AppChat.dominio;

public class ContactoIndividual extends Contacto {
	
	private String id;
	private String movil;
	
	public ContactoIndividual(String movil) {
		super("");
		this.movil = movil;
	}
	
	public ContactoIndividual(String nombre, String movil) {
		super(nombre);
		this.movil = movil;
	}
	
	public String getMovil() {
		return this.movil;
	}
	
	public void setMovil(String movil) {
		this.movil = movil;
	}

	public Usuario getUsuario() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
    public String toString() {
		if(nombre.isEmpty()) {
			return movil;
		}
        return nombre;
    }
}
