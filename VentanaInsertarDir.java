package classes;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;
import java.awt.Color;

public class VentanaInsertarDir extends JFrame{//Ventana en la que se insertan directores
	
	private JTextField tf_nombre;
	private JTextField tf_apellido;
	private JLabel lb_mensaje;
	
	public VentanaInsertarDir() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				dispose();
				new VentanaPrincipal();
			}
		});
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setTitle("Insertar Director");
		getContentPane().setLayout(null);
		
		lb_mensaje = new JLabel("Falta introducir datos");
		lb_mensaje.setForeground(Color.RED);
		lb_mensaje.setHorizontalAlignment(SwingConstants.CENTER);
		lb_mensaje.setBounds(70, 126, 207, 14);
		getContentPane().add(lb_mensaje);
		lb_mensaje.setVisible(false);
		
		JButton bt_insertar = new JButton("Insertar");
		bt_insertar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String nombre = tf_nombre.getText();
				String apellido = tf_apellido.getText();
				
				if(nombre.equals("") || apellido.equals("")) {
					lb_mensaje.setVisible(true);
				}
				else {
					try {
						Main.baseDatos.insertarDirector(nombre, apellido);
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
					
					dispose();
					new VentanaPrincipal();
				}
			}
		});
		bt_insertar.setBounds(129, 151, 106, 23);
		getContentPane().add(bt_insertar);
		
		JLabel lb_nombre = new JLabel("Nombre:");
		lb_nombre.setBounds(51, 52, 68, 14);
		getContentPane().add(lb_nombre);
		
		JLabel lb_apellido = new JLabel("Apellido:");
		lb_apellido.setBounds(51, 91, 68, 14);
		getContentPane().add(lb_apellido);
		
		tf_nombre = new JTextField();
		tf_nombre.setBounds(129, 49, 148, 20);
		getContentPane().add(tf_nombre);
		tf_nombre.setColumns(10);
		
		tf_apellido = new JTextField();
		tf_apellido.setBounds(129, 89, 148, 20);
		getContentPane().add(tf_apellido);
		tf_apellido.setColumns(10);		
		
		setSize(365,253);
		setLocationRelativeTo(null);
		setVisible(true);
	}
}
