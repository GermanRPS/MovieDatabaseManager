package classes;

import javax.swing.JFrame;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.sql.SQLSyntaxErrorException;
import java.util.ArrayList;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import java.awt.Color;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class VentanaModificarPel extends JFrame {
	
	private ArrayList<String> listaPeliculas;
	private ArrayList<String> listaDirectores;
	private JComboBox cb_pelicula;
	private JComboBox cb_director;
	private JTextField tf_titulo;
	private JTextField tf_pais;
	private JTextField tf_duracion;
	private JTextField tf_genero;
	private JLabel lb_mensaje;
	private JButton bt_modificar;
	
	public VentanaModificarPel() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				dispose();
				new VentanaPrincipal();
			}
		});		
		setTitle("Modificar Pelicula");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		getContentPane().setLayout(null);
		
		try {
			listaPeliculas = Main.baseDatos.listarPeliculas();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		String[] peliculas = new String[listaPeliculas.size()];
		for(int i=0;i<peliculas.length;i++) {
			peliculas[i] = listaPeliculas.get(i);
		}
		
		cb_pelicula = new JComboBox(new DefaultComboBoxModel(peliculas));
		cb_pelicula.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String datosPelicula = (String) cb_pelicula.getSelectedItem();
				String id_pelicula = Main.baseDatos.obtenerCodigo(datosPelicula);
				Pelicula pelicula = null;
				
				try {
					pelicula = Main.baseDatos.obtenerPelicula(Integer.parseInt(id_pelicula));
				} catch (NumberFormatException | SQLException e1) {
					e1.printStackTrace();
				}
				
				tf_titulo.setText(pelicula.getTitulo());
				//cb_director.setSelectedIndex(pelicula.getDirector());
				tf_pais.setText(pelicula.getPais());
				tf_duracion.setText(String.valueOf(pelicula.getDuracion()));
				tf_genero.setText(pelicula.getGenero());
			}
		});
		cb_pelicula.setBounds(10, 26, 385, 22);
		getContentPane().add(cb_pelicula);
		
		JLabel lb_titulo = new JLabel("Titulo:");
		lb_titulo.setBounds(10, 69, 49, 14);
		getContentPane().add(lb_titulo);
		
		tf_titulo = new JTextField();
		tf_titulo.setBounds(69, 66, 326, 20);
		getContentPane().add(tf_titulo);
		tf_titulo.setColumns(10);
		
		JLabel lb_pais = new JLabel("Pais:");
		lb_pais.setBounds(267, 98, 49, 14);
		getContentPane().add(lb_pais);
		
		tf_pais = new JTextField();
		tf_pais.setBounds(299, 97, 96, 20);
		getContentPane().add(tf_pais);
		tf_pais.setColumns(10);
		
		try {
			listaDirectores = Main.baseDatos.listarDirectores();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		String[] directores = new String[listaDirectores.size()];
		for(int i=0;i<directores.length;i++) {
			directores[i] = listaDirectores.get(i);
		}
		
		cb_director = new JComboBox(new DefaultComboBoxModel(directores));
		cb_director.setBounds(79, 98, 180, 22);
		getContentPane().add(cb_director);
		
		JLabel lb_director = new JLabel("Director:");
		lb_director.setBounds(10, 100, 70, 14);
		getContentPane().add(lb_director);
		
		JLabel lb_duracion = new JLabel("Duracion:");
		lb_duracion.setBounds(10, 132, 70, 14);
		getContentPane().add(lb_duracion);
		
		tf_duracion = new JTextField();
		tf_duracion.setBounds(89, 132, 81, 20);
		getContentPane().add(tf_duracion);
		tf_duracion.setColumns(10);
		
		JLabel lb_genero = new JLabel("Genero:");
		lb_genero.setBounds(176, 132, 63, 14);
		getContentPane().add(lb_genero);
		
		tf_genero = new JTextField();
		tf_genero.setBounds(244, 129, 151, 20);
		getContentPane().add(tf_genero);
		tf_genero.setColumns(10);
		
		lb_mensaje = new JLabel("New label");
		lb_mensaje.setForeground(Color.RED);
		lb_mensaje.setHorizontalAlignment(SwingConstants.CENTER);
		lb_mensaje.setBounds(10, 170, 385, 14);
		getContentPane().add(lb_mensaje);
		lb_mensaje.setVisible(false);
		
		bt_modificar = new JButton("Modificar");
		bt_modificar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {			
				
				String datosPelicula = (String) cb_pelicula.getSelectedItem();
				String id_pelicula = Main.baseDatos.obtenerCodigo(datosPelicula);	
				String titulo = tf_titulo.getText();
				String datosDirector = (String) cb_director.getSelectedItem();
				String id_director = Main.baseDatos.obtenerCodigo(datosDirector);
				String pais = tf_pais.getText();
				String duracion = tf_duracion.getText();
				String genero = tf_genero.getText();
				
				try {
					Main.baseDatos.modificarPelicula(id_pelicula, titulo, id_director, pais, duracion, genero);
					dispose();
					new VentanaPrincipal();
					
				} catch(SQLSyntaxErrorException e2) {
					lb_mensaje.setVisible(true);
					lb_mensaje.setText("La duracion tiene que ser un numero");
				} catch (SQLException e1) {
					e1.printStackTrace();
				}				
			}
		});
		bt_modificar.setBounds(158, 199, 101, 23);
		getContentPane().add(bt_modificar);
		
		if(cb_pelicula.getItemCount() == 0) {
			lb_mensaje.setText("Aun no has insertado peliculas");
			lb_mensaje.setVisible(true);
			bt_modificar.setEnabled(false);
		}
		
		setSize(419,270);
		setLocationRelativeTo(null);
		setVisible(true);
	}

}
