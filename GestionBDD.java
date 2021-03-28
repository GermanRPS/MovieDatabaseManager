package classes;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.mysql.cj.jdbc.DatabaseMetaData;

public class GestionBDD {//Clase encargada de la gestion de la BDD

	private static String datosConexion = "jdbc:mysql://localhost/";
	private static String baseDatos = "Peliculas";
	private static String usuario = "root";
	private static String password = "root";
	private Connection con;
	private Statement stmt = null;
	private ResultSet rs = null;
	private String query = null;
	
	public GestionBDD() {
		try {
			con = DriverManager.getConnection(datosConexion, usuario, password);
			
			try {//				
				crearBDD(); //Crea la base de datos si no existe
				crearTablaDirector(); //Crea la tabla Director si no existe
				crearTablaPeliculas(); //Crea la tabla Pelicula si no existe
				//alterTablaPeliculas();
				
				if(checkColumn()) {
					alterTablaPeliculas(); //Altera la tabla Pelicula si no ha sido alterada antes
				}
				
			}catch (Exception e) {
				e.printStackTrace();
			}			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}	

	private void crearBDD() throws SQLException {//Crea la BDD
		
		query = "create database if not exists " + baseDatos + ";";
				
		stmt = null;
		
		try {
			stmt = con.createStatement();
			stmt.executeUpdate(query);
			con = DriverManager.getConnection(datosConexion + baseDatos, usuario, password);
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			stmt.close();
		}	
	}
	
	private void alterTablaPeliculas() throws SQLException {//Altera la BDD
		
		query = "alter table Pelicula drop column director, add column id_director int, add foreign key id_director(id_director) references Director(id_director);";
		stmt = null;
		
		try {
			stmt = con.createStatement();
			stmt.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			stmt.close();
		}
	}
	
	private boolean checkColumn() {//Verifica que la columna director exista para saber si se tiene que modificar o no la tabla Pelicula
		
		boolean check = false;
		ResultSet rst = null;
		
		try {
			DatabaseMetaData md = (DatabaseMetaData) con.getMetaData();
			
			rst = md.getColumns(null, null, "Pelicula", "director");
			if (rst.next()) {
				check = true;				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return check;
	}
	
	private void crearTablaDirector() throws SQLException {//Crea la tabla director
		
		query = "create table if not exists Director("
				+ "id_director int primary key auto_increment,"
				+ "nombre VARCHAR(20),"
				+ "apellido VARCHAR(20)"
				+ ");";
		stmt = null;
		
		try {
			stmt = con.createStatement();
			stmt.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			stmt.close();
		}		
	}
	
	private void crearTablaPeliculas() throws SQLException {//Crea la tabla Pelicula
		
		query = "create table if not exists Pelicula("
				+ "id_pelicula int primary key auto_increment,"
				+ "titulo VARCHAR(30),"
			    + "id_director int,"
			    + "pais VARCHAR(20),"
			    + "duracion int,"
			    + "genero VARCHAR(20)" 
				+ ");";
		stmt = null;
		
		try {
			stmt = con.createStatement();
			stmt.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			stmt.close();
		}
		
	}
	
	public ArrayList<String> realizarBusqueda(String id_director, String genero) throws SQLException {//Funcion de la VentanaListados
																							 //Ejecuta la query y muestra la busqueda
		ArrayList<String> listaPeliculas = new ArrayList<String>();
		query = "select p.titulo, d.nombre, d.apellido, p.pais, p.duracion, p.genero from Pelicula p, Director d where p.id_director=d.id_director";
		if(id_director!="") {
			query = query + " and d.id_director=" + id_director;
		}if(genero!="") {
			query = query + " and p.genero='" + genero + "'";
		}
		
		stmt = null;
		rs = null;
		
		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery(query);
			while(rs.next()) {
				listaPeliculas.add(rs.getString(1) + " - " + rs.getString(2) + " - " + rs.getString(3) + " - " + rs.getString(4) + " - " + rs.getInt(5) + " - " + rs.getString(6));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			stmt.close();
			rs.close();
		}
		return listaPeliculas;
	}

	public ArrayList<String> listarDirectores() throws SQLException{//Lista los directores existentes
		
		ArrayList<String> listaDirectores = new ArrayList<String>();
		query = "select * from Director;";
		stmt = null;
		rs = null;
		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery(query);
			while(rs.next()) {
				listaDirectores.add(rs.getInt(1)+ " - " + rs.getString(2) + "  " + rs.getString(3));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			rs.close();
			stmt.close();
		}
		return listaDirectores;		
	}
	
	public ArrayList<String> listarPeliculas() throws SQLException{//Lista las peliculas existentes
		
		ArrayList<String> listaPeliculas = new ArrayList<String>();
		query = "select p.id_pelicula, p.titulo, d.nombre, d.apellido, p.pais, p.duracion, p.genero from Pelicula p, Director d where p.id_director = d.id_director;";
		stmt = null;
		rs = null;
		
		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery(query);
			while(rs.next()) {
				listaPeliculas.add(rs.getInt(1) + " - " + rs.getString(2) + " - " + rs.getString(3) + " " + rs.getString(4) + " - " + rs.getString(5) + " - " + rs.getInt(6) + " - " + rs.getString(7));
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}finally {
			rs.close();
			stmt.close();
		}
		return listaPeliculas;
	}
	
	public ArrayList<String> listarGeneros() throws SQLException{//Lista los generos existentes
		
		ArrayList<String> listaGeneros = new ArrayList<String>();
		query = "select distinct(genero) from Pelicula";
		stmt = null;
		rs = null;		
		
		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery(query);
			while(rs.next()) {
				listaGeneros.add(rs.getString(1));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			rs.close();
			stmt.close();
		}
		return listaGeneros;
	}
	
	public void insertarDirector(String nombre, String apellido) throws SQLException {//Inserta directores en la tabla director
		
		query = "insert into Director(nombre, apellido) values('" + nombre + "', '" + apellido + "')";
		stmt = null;
		try {
			stmt = con.createStatement();
			stmt.executeUpdate(query);
		}catch(SQLException e) {
			e.printStackTrace();
		}
		finally {
			stmt.close();
		}
	}
	
	public void insertarPelicula(String titulo, String director, String pais, String duracion, String genero) throws SQLException {
		//Inserta peliculas en la tabla pelicula
		query = "insert into Pelicula(titulo, id_director, pais, duracion, genero) "
				+ "values('" + titulo + "', '" + director + "', '" + pais + "', " + duracion + ", '" + genero + "')";
		
		stmt = null;
		
			stmt = con.createStatement();
			stmt.executeUpdate(query);
		
			stmt.close();
		
	}
	
	public void eliminarDirector(String id_director) throws SQLException {//Elimina directores de la tabla director
		
		query = "delete from Director where id_director=" + id_director + ";";
		stmt = null;
		
		stmt = con.createStatement();
		stmt.executeUpdate(query);		
		stmt.close();		
	}
	
	public void eliminarPelicula(String id_pelicula) throws SQLException {//Elimina peliculas de la tabla pelicula
		
		query = "delete from Pelicula where id_pelicula=" + id_pelicula + ";";
		stmt = null;
		
		stmt = con.createStatement();
		stmt.executeUpdate(query);
		stmt.close();
	}
	
	public void modificarDirector(String id_director, String nombre, String apellido) throws SQLException {
		//Modifica los datos de un director
		query = "update Director set nombre='" + nombre + "', apellido='" + apellido + "' where id_director=" + id_director + ";";
		stmt = null;
		
		stmt = con.createStatement();
		stmt.executeUpdate(query);
		stmt.close();
	}
	
	public void modificarPelicula(String id_pelicula, String titulo, String id_director, String pais, String duracion, String genero) throws SQLException {
		//Modifica los datos de una pelicula
		query = "update Pelicula set titulo='" + titulo + "', id_director=" + id_director + ", pais='" + pais + "', duracion=" + duracion + ", genero='" + genero + "' where id_pelicula=" + id_pelicula + ";";
		stmt = null;
		
		stmt = con.createStatement();
		stmt.executeUpdate(query);
		stmt.close();
	}
	
	public Director obtenerDirector(int id_director) throws SQLException {//Obtiene los datos de un director
		
		Director director = null;
		query = "select * from Director where id_director=" + id_director + ";";
		stmt = null;
		rs = null;		
		
		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery(query);
			while(rs.next()) {
				director = new Director(rs.getInt(1), rs.getString(2), rs.getString(3));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			rs.close();
			stmt.close();
		}
		return director;		
	}
	
	public Pelicula obtenerPelicula(int id_pelicula) throws SQLException {//Obtiene los datos de una pelicula
		
		Pelicula pelicula = null;
		query = "select * from Pelicula where id_pelicula=" + id_pelicula + ";";		
		stmt = null;
		rs = null;
				
		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery(query);
			while(rs.next()) {
				pelicula = new Pelicula(rs.getInt(1), rs.getString(2), rs.getInt(4), rs.getString(3), rs.getInt(6), rs.getString(5));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			rs.close();
			stmt.close();
		}
		return pelicula;
	}
	
	public String[] separarDatos(String datos) {//Separa los datos en funcion a un delimitador
		
		String delimiter = " - ";
		String[] temp;
		temp = datos.split(delimiter);
		return temp;
	}
	
	public String[] quitarId(String datos) {//Separa el id (primera posicion)
		
		String delimiter = " - ";
		String[] temp;
		temp = datos.split(delimiter);
		String[] temp2 = new String[temp.length-1];
		for(int i=1;i<temp.length;i++) {
			temp2[i-1] = temp[i];
		}
		return temp2;
	}
	
	public String obtenerCodigo(String datos) {//Obtiene el id (primera posicion)
		
		String delimiter = " - ";
		String[] temp;
		temp = datos.split(delimiter);
		return temp[0];
	}
}