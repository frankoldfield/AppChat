package umu.tds.apps.AppChat.dominio;

public class Mensaje {
	
	private String id;
	private String texto;
	private String hora;
	private String emoji;
	private TipoMensaje tipo;
	private boolean emisor;
	
	public Mensaje (String texto, String hora, String emoji, TipoMensaje tipo, boolean emisor) {
		this.texto = texto;
		this.hora = hora;
		this.emoji = emoji;
		this.tipo = tipo;
		this.emisor = emisor;
	}

	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}

	public String getHora() {
		return hora;
	}

	public void setHora(String hora) {
		this.hora = hora;
	}

	public String getEmoji() {
		return emoji;
	}

	public void setEmoji(String emoji) {
		this.emoji = emoji;
	}

	public TipoMensaje getTipo() {
		return tipo;
	}

	public void setTipo(TipoMensaje tipo) {
		this.tipo = tipo;
	}

	public boolean isEmisor() {
		return emisor;
	}
}
