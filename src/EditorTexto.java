import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class EditorTexto implements ActionListener {

	public JFrame frame;

	// Ruta fichero
	private JLabel lblRutaFichero;

	// Area de texto
	private MiJTextArea areaTexto;

	/** Lanzamos la aplicación */
	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			EditorTexto window = new EditorTexto(new MiJTextArea());
			window.frame.setVisible(true);
		});
	}

	/** Constructor */
	public EditorTexto(MiJTextArea textArea) {
		areaTexto = textArea;
		initialize();
	}

	/** Inicializa todos los componentes del editor */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));
		frame.setTitle("SimpleEditor");

		JPanel menuSuperior = new JPanel(new FlowLayout(FlowLayout.LEFT));
		frame.getContentPane().add(menuSuperior, BorderLayout.NORTH);

		JMenuBar menuBar = new JMenuBar();
		menuBar.setAlignmentX(Component.LEFT_ALIGNMENT);
		menuSuperior.add(menuBar);

		JMenu mnFichero = new JMenu("Fichero");
		menuBar.add(mnFichero);

		JMenuItem mntmAbrir = new JMenuItem("Abrir");
		mnFichero.add(mntmAbrir);
		mntmAbrir.addActionListener(this);

		JMenuItem mntmGuardar = new JMenuItem("Guardar");
		mnFichero.add(mntmGuardar);
		mntmGuardar.addActionListener(this);

		JMenuItem mntmNuevo = new JMenuItem("Nuevo");
		mnFichero.add(mntmNuevo);
		mntmNuevo.addActionListener(this);

		JMenuItem mntmImprimir = new JMenuItem("Imprimir");
		mnFichero.add(mntmImprimir);
		mntmImprimir.addActionListener(this);

		JMenuItem mntmCerrar = new JMenuItem("Cerrar");
		mnFichero.add(mntmCerrar);
		mntmCerrar.addActionListener(this);

		JMenu mnEdicion = new JMenu("Edicion");
		menuBar.add(mnEdicion);

		JMenuItem mntmCopiar = new JMenuItem("Copiar");
		mnEdicion.add(mntmCopiar);
		mntmCopiar.addActionListener(this);

		JMenuItem mntmCortar = new JMenuItem("Cortar");
		mnEdicion.add(mntmCortar);
		mntmCortar.addActionListener(this);

		JMenuItem mntmPegar = new JMenuItem("Pegar");
		mnEdicion.add(mntmPegar);
		mntmPegar.addActionListener(this);

		JMenu mnTamanoLetra = new JMenu("Tamano Letra");
		mnEdicion.add(mnTamanoLetra);

		JMenuItem mntmAumentarLetra = new JMenuItem("Aumentar");
		mnTamanoLetra.add(mntmAumentarLetra);
		mntmAumentarLetra.addActionListener(this);

		JMenuItem mntmDisminuirLetra = new JMenuItem("Disminuir");
		mnTamanoLetra.add(mntmDisminuirLetra);

		JMenu mnColorLetra = new JMenu("Color Letra");
		mnEdicion.add(mnColorLetra);
		mntmDisminuirLetra.addActionListener(this);

		JMenuItem mntmColorNegro = new JMenuItem("TextoNegro");
		mnColorLetra.add(mntmColorNegro);
		mntmColorNegro.addActionListener(this);

		JMenuItem mntmColorRojo = new JMenuItem("TextoRojo");
		mnColorLetra.add(mntmColorRojo);
		mntmColorRojo.addActionListener(this);

		JMenuItem mntmColorAzul = new JMenuItem("TextoAzul");
		mnColorLetra.add(mntmColorAzul);
		mntmColorAzul.addActionListener(this);

		JMenu mnAyuda = new JMenu("Ayuda");
		menuBar.add(mnAyuda);

		JMenuItem mntmAcercaDe = new JMenuItem("Acerca de");
		mnAyuda.add(mntmAcercaDe);
		mntmAcercaDe.addActionListener(this);

		JMenuItem mntmAyuda = new JMenuItem("Ayuda");
		mnAyuda.add(mntmAyuda);
		mntmAyuda.addActionListener(this);

		JPanel menuInferior = new JPanel();
		menuInferior.setPreferredSize(new Dimension(10, 25));
		frame.getContentPane().add(menuInferior, BorderLayout.SOUTH);

		lblRutaFichero = new JLabel("");
		menuInferior.add(lblRutaFichero);

		frame.getContentPane().add(areaTexto, BorderLayout.CENTER);

		JScrollPane scrollPane = new JScrollPane(areaTexto);
		frame.getContentPane().add(scrollPane, BorderLayout.CENTER);

		// Cuando se pulse la X (Cerrar) en nuestro frame
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				int opcion = cerrarAplicacion();
				switch (opcion) {
				case 0:
					System.exit(0);
					break;
				case 1:
					break;
				case 2:
					comprobarGuardado();
					System.exit(0);
				}
			}
		});
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String comando = e.getActionCommand();
		System.out.println(comando);

		if (comando.equals("Cortar")) {
			areaTexto.cut();
		} else if (comando.equals("TextoAzul")) {
			areaTexto.setForeground(Color.BLUE);
		} else if (comando.equals("TextoRojo")) {
			areaTexto.setForeground(Color.RED);
		} else if (comando.equals("TextoNegro")) {
			areaTexto.setForeground(Color.BLACK);
		} else if (comando.equals("Aumentar")) {
			Font f = areaTexto.getFont();
			Font f2 = new Font(f.getFontName(), f.getStyle(), f.getSize() + 1);
			areaTexto.setFont(f2);
		} else if (comando.equals("Disminuir")) {
			Font f = areaTexto.getFont();
			Font f2 = new Font(f.getFontName(), f.getStyle(), f.getSize() - 1);
			areaTexto.setFont(f2);
		} else if (comando.equals("Copiar")) {
			areaTexto.copy();
		} else if (comando.equals("Pegar")) {
			areaTexto.paste();
		} else if (comando.equals("Ayuda")) {
			JOptionPane.showMessageDialog(frame, "Un simple editor de texto");
		} else if (comando.equals("Imprimir")) {
			try {
				areaTexto.print();
			} catch (Exception evt) {
				JOptionPane.showMessageDialog(frame, evt.getMessage());
			}
		} else if (comando.equals("Nuevo")) {
			// Borramos la ruta del fichero
			lblRutaFichero.setText("");

			// Reseteamos text área
			areaTexto.setText("");
		} else if (comando.equals("Acerca de")) {
			JOptionPane.showMessageDialog(frame, "Version 1L\nSimpleEditor by Antonio Gundermann");
		} else if (comando.equals("Cerrar")) {
			int opcion = cerrarAplicacion();
			switch (opcion) {
			case 0:
				System.exit(0);
				break;
			case 1:
				break;
			case 2:
				comprobarGuardado();
				System.exit(0);
			}
		} else if (comando.equals("Guardar")) {
			comprobarGuardado();
		} else if (comando.equals("Abrir")) {

			JFileChooser j = new JFileChooser("f:");

			int r = j.showOpenDialog(null);

			if (r == JFileChooser.APPROVE_OPTION) {
				File fi = new File(j.getSelectedFile().getAbsolutePath());

				try {
					String s1 = "", sl = "";
					FileReader fr = new FileReader(fi);
					BufferedReader br = new BufferedReader(fr);

					sl = br.readLine();

					while ((s1 = br.readLine()) != null) {
						sl = sl + "\n" + s1;
					}

					areaTexto.setText(sl);

					// Cambiamos la ruta del fichero
					lblRutaFichero.setText("" + fi.getAbsolutePath());

					br.close();
				} catch (Exception evt) {
					JOptionPane.showMessageDialog(frame, evt.getMessage());
				}
			}
		}
	}

	/*
	 * Comprueba como hay que cerrar la aplicación o que botón ha seleccionado el
	 * usuario
	 * 
	 * @return 0 Exit, 1 Cancelar la salida, 2 Guardar y Exit
	 */
	private int cerrarAplicacion() {
		if (lblRutaFichero.getText().equals("")) {
			if (areaTexto.getText().length() > 0) {
				Object[] opciones = { "Si", "Cancelar", "Guardar" };
				int opcion = JOptionPane.showOptionDialog(frame, "Hay cambios sin guardar, ¿Desea salir?",
						"Cambios no guardados", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null,
						opciones, opciones[2]);
				return opcion;
			}
		} else {
			String texto = "";
			try {
				crearTemp();
				texto = leerFichero(new File(lblRutaFichero.getText()));
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			}
			if (areaTexto.getText().hashCode() != texto.hashCode()) {
				Object[] opciones = { "Si", "Cancelar", "Guardar" };
				int opcion = JOptionPane.showOptionDialog(frame, "Hay cambios sin guardar, ¿Desea salir?",
						"Cambios no guardados", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null,
						opciones, opciones[2]);
				return opcion;
			}
		}

		// Si no hay fichero seleccionado
		return 0;
	}

	/*
	 * Comprueba el tipo de guardado que hay que hacer y lo realiza (Fichero nuevo o
	 * editado)
	 */
	private void comprobarGuardado() {
		// Si estamos editando un fichero no hace falta pedir al usuario la ruta
		if (lblRutaFichero.getText().length() > 0) {
			File fi = new File(lblRutaFichero.getText());

			try {
				FileWriter wr = new FileWriter(fi, false);
				BufferedWriter w = new BufferedWriter(wr);

				w.write(areaTexto.getText());

				w.flush();
				w.close();
			} catch (Exception evt) {
				JOptionPane.showMessageDialog(frame, evt.getMessage());
			}
		} else {
			JFileChooser j = new JFileChooser("f:");

			int r = j.showSaveDialog(null);

			if (r == JFileChooser.APPROVE_OPTION) {

				File fi = new File(j.getSelectedFile().getAbsolutePath());

				// Escribimos la ruta del fichero
				lblRutaFichero.setText(j.getSelectedFile().getAbsolutePath());

				try {
					FileWriter wr = new FileWriter(fi, false);
					BufferedWriter w = new BufferedWriter(wr);

					w.write(areaTexto.getText());

					w.flush();
					w.close();
				} catch (Exception evt) {
					JOptionPane.showMessageDialog(frame, evt.getMessage());
				}
			}
		}
	}

	/*
	 * Lee un fichero de texto pasado por parámetro
	 * 
	 * @param Fichero de texto a leer
	 * 
	 * @return Contenido del fichero de texto
	 */
	private String leerFichero(File file) throws FileNotFoundException {
		Scanner s = new Scanner(file);
		String resultado = "";

		while (s.hasNextLine()) {
			resultado += s.nextLine();
			if (s.hasNextLine()) {
				resultado += "\n";
			}
		}

		s.close();
		return resultado;
	}

	/*
	 * Crea un archivo temporal del fichero que se está editando actualmente
	 */
	private void crearTemp() {
		File temp;
		BufferedWriter bw = null;
		try {
			temp = File.createTempFile("." + lblRutaFichero.getText(), ".tmp");
			bw = new BufferedWriter(new FileWriter(temp));
			bw.write(areaTexto.getText());
			bw.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

}
