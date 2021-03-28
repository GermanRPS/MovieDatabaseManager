package classes;

import javax.swing.JFrame;
import javax.swing.JComboBox;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Color;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class VentanaEliminarDir extends JFrame{//Ventana en la que se eliminan directores
	
	ArrayList<String> listaDirectores;
	private JComboBox cb_director;
	private JLabel lb_mensaje;
	
	public VentanaEliminarDir() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				dispose();
				new VentanaPrincipal();
			}
		});		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setTitle("Eliminar Director");
		getContentPane().setLayout(null);
		
		lb_mensaje = new JLabel("Director asociado a pelicula. No se puede eliminar.");
		lb_mensaje.setForeground(Color.RED);
		lb_mensaje.setHorizontalAlignment(SwingConstants.CENTER);
		lb_mensaje.setBounds(32, 121, 372, 14);
		getContentPane().add(lb_mensaje);
		lb_mensaje.setVisible(false);
		
		try {
			listaDirectores = Main.baseDatos.listarDirectores();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		String[] directores = new String[listaDirectores.size()];
		for(int i=0;i<directores.length;i++) {
			directores[i] = listaDirectores.get(i);
		}		
		
		cb_director = new JComboBox(new DefaultComboBoxModel(directores));//Directores mostrados en un ComboBox
		cb_director.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lb_mensaje.setVisible(false);
			}
		});
		cb_director.setBounds(128, 75, 180, 22);
		getContentPane().add(cb_director);
		
		JButton bt_eliminar = new JButton("Eliminar");
		bt_eliminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String datosDirector = (String)cb_director.getSelectedItem();
				String id_director = Main.baseDatos.obtenerCodigo(datosDirector);
				
				try {
					Main.baseDatos.eliminarDirector(id_director);
					dispose();
					new VentanaPrincipal();
				}catch(SQLIntegrityConstraintViolationException e1) {//Controla que no se pueda eliminar si esta asociado a una pelicula
					lb_mensaje.setVisible(true);
				}catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		bt_eliminar.setBounds(164, 153, 108, 23);
		getContentPane().add(bt_eliminar);
		
		if(cb_director.getItemCount()==0) {
			lb_mensaje.setVisible(true);
			lb_mensaje.setText("No has insertado directores");
			bt_eliminar.setEnabled(false);
		}
		
		setSize(450,300);
		setLocationRelativeTo(null);
		setVisible(true); 
	}

}
