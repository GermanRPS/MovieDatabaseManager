package classes;

public class Director {//Clase que contiene los datos de un director
	
	private int id_director;
	private String nombre;
	private String apellido;
	
	public Director() {
		
	}

	public Director(int id_director, String nombre, String apellido) {
		
		this.id_director = id_director;
		this.nombre = nombre;
		this.apellido = apellido;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public int getId_director() {
		return id_director;
	}
}
