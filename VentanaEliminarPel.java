package classes;

import javax.swing.JFrame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;

import java.awt.Panel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class VentanaEliminarPel extends JFrame{//Ventana en la que se eliminan peliculas
	
	private ArrayList<String> listaPeliculas;
	private JTable table;
	private JScrollPane scrollPane;
	private JButton bt_eliminar;
	private JLabel lb_mensaje;
	
	public VentanaEliminarPel() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				dispose();
				new VentanaPrincipal();
			}
		});
		setTitle("Eliminar Pelicula");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		getContentPane().setLayout(null);
		
		lb_mensaje = new JLabel("Aun no has insertado peliculas");
		lb_mensaje.setForeground(Color.RED);
		lb_mensaje.setHorizontalAlignment(SwingConstants.CENTER);
		lb_mensaje.setBounds(10, 161, 456, 14);
		getContentPane().add(lb_mensaje);
		lb_mensaje.setVisible(false);		
				
		try {
			listaPeliculas = Main.baseDatos.listarPeliculas();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		String[] peliculas = new String[listaPeliculas.size()];
		for(int i=0;i<peliculas.length;i++) {
			peliculas[i] = listaPeliculas.get(i);
		}		
	
		String[][] datos = new String[listaPeliculas.size()][6];		
		String[] col = {"titulo","director","pais","min","genero"};
		String[][] aux = new String[listaPeliculas.size()][7];
		
		for(int i=0;i<datos.length;i++) {
			String[] temp = Main.baseDatos.quitarId(peliculas[i]);
			String[] temp2 = Main.baseDatos.separarDatos(peliculas[i]);
			datos[i] = temp;
			aux[i] = temp2;
		}		
				
		DefaultTableModel model = new DefaultTableModel(datos,col);//Peliculas mostradas en una tabla
		
		Panel panel = new Panel();
		panel.setBounds(10, 10, 456, 143);
		panel.setLayout(new BorderLayout());
		
		getContentPane().add(panel);
		table = new JTable(model);
		table.setShowGrid(false);
		table.setBounds(227, 181, 239, 87);
		resizeColumnWidth(table);
		table.setDefaultEditor(Object.class, null);
		
		scrollPane = new JScrollPane(table);
		panel.add(scrollPane, BorderLayout.CENTER);
		
		bt_eliminar = new JButton("Eliminar");
		bt_eliminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {								
				
				try {
					String index = aux[table.getSelectedRow()][0];
					Main.baseDatos.eliminarPelicula(index);
					dispose();
					new VentanaPrincipal();
				} catch (SQLException e) {
					e.printStackTrace();
				}catch (ArrayIndexOutOfBoundsException e) {
					lb_mensaje.setText("No has seleccionado nada");
					lb_mensaje.setVisible(true);
				}				
			}
		});
		bt_eliminar.setBounds(193, 186, 104, 23);
		getContentPane().add(bt_eliminar);		
		
		if(table.getRowCount() == 0) {
			bt_eliminar.setEnabled(false);
			lb_mensaje.setVisible(true);	
		}
		
		setSize(490,257);
		setLocationRelativeTo(null);
		setVisible(true); 
	}
	
	public void resizeColumnWidth(JTable table) {//Modifica el tama�o de las columnas en funcion a su contenido
	    final TableColumnModel columnModel = table.getColumnModel();
	    for (int column = 0; column < table.getColumnCount(); column++) {
	        int width = 15; // Min width
	        for (int row = 0; row < table.getRowCount(); row++) {
	            TableCellRenderer renderer = table.getCellRenderer(row, column);
	            Component comp = table.prepareRenderer(renderer, row, column);
	            width = Math.max(comp.getPreferredSize().width +1 , width);
	        }
	        if(width > 300)
	            width=300;
	        columnModel.getColumn(column).setPreferredWidth(width);
	    }
	}
}
