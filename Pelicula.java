package classes;

public class Pelicula {//Clase que contiene los datos de una pelicula
	
	private int id_pelicula;
	private String titulo;
	private int director;
	private String pais;
	private int duracion;
	private String genero;
	
	public Pelicula() {
		
	}

	public Pelicula(int id_pelicula, String titulo, int director, String pais, int duracion, String genero) {
		this.id_pelicula = id_pelicula;
		this.titulo = titulo;
		this.director = director;
		this.pais = pais;
		this.duracion = duracion;
		this.genero = genero;
	}

	public int getId_pelicula() {
		return id_pelicula;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public int getDirector() {
		return director;
	}

	public void setDirector(int director) {
		this.director = director;
	}

	public String getPais() {
		return pais;
	}

	public void setPais(String pais) {
		this.pais = pais;
	}

	public int getDuracion() {
		return duracion;
	}

	public void setDuracion(int duracion) {
		this.duracion = duracion;
	}

	public String getGenero() {
		return genero;
	}

	public void setGenero(String genero) {
		this.genero = genero;
	}
}
