package pojo;

/**
 * Classe contendo a representação de um documento.
 * 
 * @author Ygor Santos
 *
 */
public class Documento {
	
	private int numero;
	
	private String titulo;
	
	private String texto;
	
	public Documento(){
		
	}
	
	/**
	 * Construtor.
	 * 
	 * @param numero
	 * 			Número do documento
	 * @param titulo
	 * 			Titulo do documento
	 * @param texto
	 * 			Conteúdo do documento
	 */
	public Documento(int numero, String titulo, String texto){
		this.numero = numero;
		this.titulo = titulo;
		this.texto = texto;
	}

	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}

	@Override
	public String toString() {
		return "Documento" + "\nnumero: " + numero + "\ntitulo: " + titulo
				+ "\ntexto: " + texto;
	}
}
