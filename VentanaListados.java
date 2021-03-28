package classes;

import java.util.ArrayList;

import javax.swing.JFrame;

import java.awt.Color;
import java.awt.FlowLayout;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.awt.event.ActionEvent;

public class VentanaListados extends JFrame{//Ventana que muestra la lista de peliculas introducidas
											//En funcion a unos criterios de busqueda
	private ArrayList<String> listaDirectores;
	private ArrayList<String> listaPeliculas;
	private ArrayList<String> listaGeneros;
	private int peliculaActual;
	private JTextField tf_titulo;
	private JTextField tf_director;
	private JTextField tf_pais;
	private JTextField tf_duracion;
	private JTextField tf_genero;
	private JComboBox cb_director;
	private JComboBox cb_genero;
	private JButton bt_anterior;
	private JButton bt_siguiente;
	private JButton bt_buscar;
	private JLabel lb_mensaje;
	
	public VentanaListados() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				dispose();
				new VentanaPrincipal();
			}
		});
		setTitle("Listados");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);		
		
		JLabel lb_director1 = new JLabel("Director:");
		lb_director1.setBounds(8, 11, 75, 14);
		getContentPane().add(lb_director1);
		
		try {
			listaDirectores = Main.baseDatos.listarDirectores();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		String[] directores = new String[listaDirectores.size()+1];
		directores[0]="";
		for(int i=0;i<listaDirectores.size();i++) {
			directores[i+1] = listaDirectores.get(i);
		}		
		
		cb_director = new JComboBox();
		cb_director.setModel(new DefaultComboBoxModel(directores));//Directores mostrados en un comboBox
		cb_director.setBounds(81, 7, 151, 22);
		getContentPane().add(cb_director);		
		
		JLabel lb_genero1 = new JLabel("Genero:");
		lb_genero1.setBounds(250, 11, 73, 14);
		getContentPane().add(lb_genero1);
		
		try {
			listaGeneros = Main.baseDatos.listarGeneros();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		String[] generos = new String[listaGeneros.size()+1];
		generos[0] = "";
		for(int i=0;i<listaGeneros.size();i++) {
			generos[i+1] = listaGeneros.get(i);
		}			
		
		cb_genero = new JComboBox();
		cb_genero.setBounds(317, 7, 151, 22);
		cb_genero.setModel(new DefaultComboBoxModel(generos));//Generos mostrados en un comboBox
		getContentPane().add(cb_genero);		
		
		bt_buscar = new JButton("Buscar");
		bt_buscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				lb_mensaje.setVisible(false);
				peliculaActual = 0;
				String director = (String) cb_director.getSelectedItem();
				String id_director = Main.baseDatos.obtenerCodigo(director);
				String genero = (String) cb_genero.getSelectedItem();
				
				try {//Si se encuentran coincidencias
					listaPeliculas = Main.baseDatos.realizarBusqueda(id_director, genero);
					cargarPelis();
					bt_anterior.setEnabled(false);
					lb_mensaje.setText("Se ha encontrado lo siguiente:");
					lb_mensaje.setForeground(Color.BLUE);
					lb_mensaje.setVisible(true);
					
					if(peliculaActual == listaPeliculas.size()-1) {
						bt_siguiente.setEnabled(false);
						bt_anterior.setEnabled(false);					
					} else {
						bt_siguiente.setEnabled(true);
					}
				} catch (IndexOutOfBoundsException e1) {//Si no se encuentran coincidencias
					lb_mensaje.setText("No se ha encontrado nada con esos criterios");
					lb_mensaje.setForeground(Color.RED);
					lb_mensaje.setVisible(true);
					bt_anterior.setEnabled(false);
					bt_siguiente.setEnabled(false);
					tf_titulo.setText("");
					tf_director.setText("");
					tf_pais.setText("");
					tf_duracion.setText("");
					tf_genero.setText("");
					
				} catch (SQLException e1) {
					e1.printStackTrace();
				}				
			}
		});
		bt_buscar.setBounds(194, 45, 89, 23);
		getContentPane().add(bt_buscar);
		
		lb_mensaje = new JLabel("New label");
		lb_mensaje.setHorizontalAlignment(SwingConstants.CENTER);
		lb_mensaje.setBounds(22, 88, 432, 14);
		getContentPane().add(lb_mensaje);
		
		JLabel lb_titulo = new JLabel("Titulo:");
		lb_titulo.setBounds(22, 130, 49, 14);
		getContentPane().add(lb_titulo);
		
		tf_titulo = new JTextField();
		tf_titulo.setBounds(93, 127, 361, 20);
		getContentPane().add(tf_titulo);
		tf_titulo.setColumns(10);
		
		JLabel lb_director2 = new JLabel("Director:");
		lb_director2.setBounds(22, 171, 68, 14);
		getContentPane().add(lb_director2);
		
		tf_director = new JTextField();
		tf_director.setBounds(93, 168, 134, 20);
		getContentPane().add(tf_director);
		tf_director.setColumns(10);
		
		JLabel lb_pais = new JLabel("Pais:");
		lb_pais.setBounds(249, 171, 49, 14);
		getContentPane().add(lb_pais);
		
		tf_pais = new JTextField();
		tf_pais.setBounds(320, 168, 134, 20);
		getContentPane().add(tf_pais);
		tf_pais.setColumns(10);
		
		JLabel lb_duracion = new JLabel("Duracion:");
		lb_duracion.setBounds(22, 214, 68, 14);
		getContentPane().add(lb_duracion);
		
		tf_duracion = new JTextField();
		tf_duracion.setBounds(93, 211, 134, 20);
		getContentPane().add(tf_duracion);
		tf_duracion.setColumns(10);
		
		JLabel lb_genero2 = new JLabel("Genero:");
		lb_genero2.setBounds(249, 214, 74, 14);
		getContentPane().add(lb_genero2);
		
		tf_genero = new JTextField();
		tf_genero.setBounds(320, 211, 134, 20);
		getContentPane().add(tf_genero);
		tf_genero.setColumns(10);
		
		bt_anterior = new JButton("Anterior");
		bt_anterior.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				peliculaActual--;
				cargarPelis();
				logicaBotones();
				
				bt_siguiente.setEnabled(true);				
			}
		});
		bt_anterior.setBounds(99, 259, 109, 23);
		getContentPane().add(bt_anterior);
		bt_anterior.setEnabled(false);
		
		bt_siguiente = new JButton("Siguiente");
		bt_siguiente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				peliculaActual++;
				cargarPelis();
				logicaBotones();
				
				bt_anterior.setEnabled(true);		
			}
		});
		bt_siguiente.setBounds(287, 259, 109, 23);
		getContentPane().add(bt_siguiente);
		bt_siguiente.setEnabled(false);
		
		checkInfoInicioVentana();
			
		setSize(491,342);
		setLocationRelativeTo(null);
		getContentPane().setLayout(null);
		setVisible(true);	
	}
	
	public void cargarPelis() {//Carga la lista de coincidencias en la ventana
		
		String[] datos = Main.baseDatos.separarDatos(listaPeliculas.get(peliculaActual));
		tf_titulo.setText(datos[0]);
		tf_director.setText(datos[1] + " " + datos[2]);
		tf_pais.setText(datos[3]);
		tf_duracion.setText(datos[4] + " min");
		tf_genero.setText(datos[5]);
	}
	
	public void logicaBotones() {//Activa y desactiva los botones en funcion a unos determinantes
		
		if(peliculaActual == listaPeliculas.size()-1) {
			bt_anterior.setEnabled(false);
			bt_siguiente.setEnabled(false);
		}
		
		if(peliculaActual == 0) {
			bt_anterior.setEnabled(false);
			bt_siguiente.setEnabled(true);
		}
	}
	
	public void checkInfoInicioVentana() {//Verifica que datos existen al abrirse la ventana
		
		bt_buscar.setEnabled(false);
		bt_anterior.setEnabled(false);
		bt_siguiente.setEnabled(false);
		
		if(cb_director.getItemCount() == 1) {
			lb_mensaje.setText("Aun no has insertado directores");
			lb_mensaje.setForeground(Color.RED);			
		}
		else if(cb_genero.getItemCount() == 1) {
			lb_mensaje.setText("Aun no has insertado peliculas");
			lb_mensaje.setForeground(Color.RED);		
		}
		else {
			lb_mensaje.setText("Puedes filtrar la busqueda");
			lb_mensaje.setForeground(Color.BLUE);
			bt_buscar.setEnabled(true);			
		}
	}
}
