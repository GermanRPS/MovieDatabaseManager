package classes;

import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class VentanaPrincipal extends JFrame{
	public VentanaPrincipal() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setTitle("Filmoteca II");
		getContentPane().setLayout(null);
		
		JButton bt_insertarDir = new JButton("Insertar Director");
		bt_insertarDir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				new VentanaInsertarDir();
				dispose();
			}
		});
		bt_insertarDir.setBounds(48, 65, 161, 23);
		getContentPane().add(bt_insertarDir);
		
		JButton bt_insertarPel = new JButton("Insertar Pelicula");
		bt_insertarPel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				new VentanaInsertarPel();
				dispose();
			}
		});
		bt_insertarPel.setBounds(241, 65, 161, 23);
		getContentPane().add(bt_insertarPel);
		
		JButton bt_eliminarDir = new JButton("Eliminar Director");
		bt_eliminarDir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				new VentanaEliminarDir();
				dispose();
			}
		});
		bt_eliminarDir.setBounds(48, 112, 161, 23);
		getContentPane().add(bt_eliminarDir);
		
		JButton bt_eliminarPel = new JButton("Eliminar Pelicula");
		bt_eliminarPel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				new VentanaEliminarPel();
				dispose();
			}
		});
		bt_eliminarPel.setBounds(241, 112, 161, 23);
		getContentPane().add(bt_eliminarPel);
		
		JButton bt_modificarDir = new JButton("Modificar Director");
		bt_modificarDir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				new VentanaModificarDir();
				dispose();
			}
		});
		bt_modificarDir.setBounds(48, 162, 161, 23);
		getContentPane().add(bt_modificarDir);
		
		JButton bt_modificarPel = new JButton("Modificar Pelicula");
		bt_modificarPel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				new VentanaModificarPel();
				dispose();
			}
		});
		bt_modificarPel.setBounds(241, 162, 161, 23);
		getContentPane().add(bt_modificarPel);
		
		JButton bt_listados = new JButton("Listados");
		bt_listados.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				new VentanaListados();
				dispose();
			}
		});
		bt_listados.setBounds(145, 217, 145, 23);
		getContentPane().add(bt_listados);
		
		setSize(450,300);
		setLocationRelativeTo(null);
		setVisible(true); 
	}
}
