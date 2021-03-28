package classes;

import javax.swing.JFrame;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;

import java.sql.SQLException;
import java.sql.SQLSyntaxErrorException;
import java.util.ArrayList;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;
import java.awt.Color;

public class VentanaInsertarPel extends JFrame {//Ventana en la que se insertan peliculas
	
	ArrayList<String> listaDirectores;
	
	private JTextField tf_pais;
	private JTextField tf_titulo;
	private JTextField tf_duracion;
	private JTextField tf_genero;
	private JComboBox cb_director;
	private JLabel lb_mensaje;
	private JButton bt_insertar;
	
	public VentanaInsertarPel(){
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				dispose();
				new VentanaPrincipal();
			}
			@Override
			public void windowOpened(WindowEvent arg0) {//Controla que existan directores introducidos previamente
				if(cb_director.getItemCount() == 0) {
					lb_mensaje.setVisible(true);
					lb_mensaje.setText("Aun no has insertado directores");
					bt_insertar.setEnabled(false);
				}		
			}
		});
		setTitle("Insertar Pelicula");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		getContentPane().setLayout(null);
		
		try {
			listaDirectores = Main.baseDatos.listarDirectores();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		String[] directores = new String[listaDirectores.size()];
		for(int i=0;i<directores.length;i++) {
			directores[i] = listaDirectores.get(i);
		}
		
		cb_director = new JComboBox();
		cb_director.setModel(new DefaultComboBoxModel(directores)); //Directores mostrados en un comboBox
		cb_director.setBounds(79, 58, 182, 22);
		getContentPane().add(cb_director);
		
		JLabel lb_titulo = new JLabel("Titulo:");
		lb_titulo.setBounds(10, 22, 49, 14);
		getContentPane().add(lb_titulo);
		
		JLabel lb_director = new JLabel("Director:");
		lb_director.setBounds(10, 62, 62, 14);
		getContentPane().add(lb_director);
		
		JLabel lb_pais = new JLabel("Pais:");
		lb_pais.setBounds(251, 22, 49, 14);
		getContentPane().add(lb_pais);
		
		tf_pais = new JTextField();
		tf_pais.setBounds(291, 19, 126, 20);
		getContentPane().add(tf_pais);
		tf_pais.setColumns(10);
		
		tf_titulo = new JTextField();
		tf_titulo.setBounds(69, 19, 172, 20);
		getContentPane().add(tf_titulo);
		tf_titulo.setColumns(10);
		
		JLabel lb_duracion = new JLabel("Duracion:");
		lb_duracion.setBounds(271, 62, 89, 14);
		getContentPane().add(lb_duracion);
		
		JLabel lb_genero = new JLabel("Genero:");
		lb_genero.setBounds(10, 105, 62, 14);
		getContentPane().add(lb_genero);
		
		tf_duracion = new JTextField();
		tf_duracion.setBounds(353, 59, 64, 20);
		getContentPane().add(tf_duracion);
		tf_duracion.setColumns(10);
		
		tf_genero = new JTextField();
		tf_genero.setBounds(79, 103, 172, 20);
		getContentPane().add(tf_genero);
		tf_genero.setColumns(10);
		
		lb_mensaje = new JLabel("New label");
		lb_mensaje.setForeground(Color.RED);
		lb_mensaje.setHorizontalAlignment(SwingConstants.CENTER);
		lb_mensaje.setBounds(43, 151, 337, 14);
		getContentPane().add(lb_mensaje);
		lb_mensaje.setVisible(false);
		
		bt_insertar = new JButton("Insertar");
		bt_insertar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
					String datosDirector = (String)cb_director.getSelectedItem();				
					String codigoDirector = Main.baseDatos.obtenerCodigo(datosDirector);				
					String titulo = tf_titulo.getText();
					String pais = tf_pais.getText();
					String duracion = tf_duracion.getText();
					String genero = tf_genero.getText();
									
					if(titulo.equals("") || pais.equals("") || genero.equals("")) {
						lb_mensaje.setVisible(true);
						lb_mensaje.setText("Falta introducir datos");
					}
					else {
						try {
							Main.baseDatos.insertarPelicula(titulo, codigoDirector, pais, duracion, genero);
							dispose();
							new VentanaPrincipal();
						}catch(SQLSyntaxErrorException e2) {
							lb_mensaje.setVisible(true);
							lb_mensaje.setText("La duracion tiene que ser un numero");//Controla que la duracion sea un int
						}
					}
				}catch (SQLException e1) {
					e1.printStackTrace();
				}				
			}
		});
		bt_insertar.setBounds(291, 101, 107, 23);
		getContentPane().add(bt_insertar);		
		
		setSize(450,227);
		setLocationRelativeTo(null);
		setVisible(true); 
	}
}
