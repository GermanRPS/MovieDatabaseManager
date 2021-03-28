package classes;

import javax.swing.JFrame;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import java.awt.Color;
import javax.swing.SwingConstants;

public class VentanaModificarDir extends JFrame {
	
	private ArrayList<String> listaDirectores;
	private JTextField tf_nombre;
	private JTextField tf_apellido;
	private JComboBox cb_director;
	private JButton bt_modificar;
	private JLabel lb_mensaje;
	
	public VentanaModificarDir() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				dispose();
				new VentanaPrincipal();
			}
		});		
		setTitle("Modificar Director");
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
		
		cb_director = new JComboBox(new DefaultComboBoxModel(directores));
		cb_director.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String datosDirector = (String) cb_director.getSelectedItem();
				String id_director = Main.baseDatos.obtenerCodigo(datosDirector);
				Director director = null;
				
				try {
					director = Main.baseDatos.obtenerDirector(Integer.parseInt(id_director));
				} catch (NumberFormatException e1) {
					e1.printStackTrace();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				
				tf_nombre.setText(director.getNombre());
				tf_apellido.setText(director.getApellido());
			}
		});
		cb_director.setBounds(41, 11, 268, 22);
		getContentPane().add(cb_director);
		
		JLabel lb_nombre = new JLabel("Nombre:");
		lb_nombre.setBounds(41, 44, 67, 17);
		getContentPane().add(lb_nombre);
		
		JLabel lb_apellido = new JLabel("Apellido:");
		lb_apellido.setBounds(41, 72, 67, 14);
		getContentPane().add(lb_apellido);
		
		tf_nombre = new JTextField();
		tf_nombre.setBounds(103, 42, 206, 20);
		getContentPane().add(tf_nombre);
		tf_nombre.setColumns(10);
		
		tf_apellido = new JTextField();
		tf_apellido.setBounds(103, 69, 206, 20);
		getContentPane().add(tf_apellido);
		tf_apellido.setColumns(10);
		
		bt_modificar = new JButton("Modificar");
		bt_modificar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String datosDirector = (String) cb_director.getSelectedItem();
				String id_director = Main.baseDatos.obtenerCodigo(datosDirector);
				String nombre = tf_nombre.getText();
				String apellido = tf_apellido.getText();
				
				try {
					Main.baseDatos.modificarDirector(id_director, nombre, apellido);
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				
				dispose();
				new VentanaPrincipal();
			}
		});
		bt_modificar.setBounds(131, 122, 109, 23);
		getContentPane().add(bt_modificar);
		
		lb_mensaje = new JLabel("Aun no has insertado directores");
		lb_mensaje.setHorizontalAlignment(SwingConstants.CENTER);
		lb_mensaje.setForeground(Color.RED);
		lb_mensaje.setBounds(51, 97, 268, 14);
		getContentPane().add(lb_mensaje);
		lb_mensaje.setVisible(false);
		
		if(cb_director.getItemCount() == 0) {
			lb_mensaje.setVisible(true);
			bt_modificar.setEnabled(false);
		}
		
		setSize(365,191);
		setLocationRelativeTo(null);
		setVisible(true);
	}
}
