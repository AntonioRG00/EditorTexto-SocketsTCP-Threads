import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

import com.google.common.net.InetAddresses;

public class SocketCliente {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		
		//Le pedimos la ip del servidor al cliente
		System.out.println("Dime la IP del servidor");
		String ipServidor = scanner.next();
		if(!InetAddresses.isInetAddress(ipServidor)) {
			System.out.println("Has introducido una IP incorrecta, saliendo!");
			System.out.println("Deben ser IPv4, Ejemplo: 192.168.1.5");
			System.exit(1);
		}
		
		//Empezamos la conexión
		try (Socket peticion = new Socket(ipServidor, 4200);
				DataOutputStream out = new DataOutputStream(peticion.getOutputStream());
				DataInputStream in = new DataInputStream(peticion.getInputStream())) {

			// Leemos nuestra idCliente
			int idCliente = in.readInt();
			System.out.println("Bienvenido, tu ID => " + idCliente);

			// Lanzamos la ventana
			MiJTextArea textArea = new MiJTextArea(out);
			EditorTexto editorTexto = new EditorTexto(textArea);
			editorTexto.frame.setTitle("SimpleEditor, ID Conexión: " + idCliente);
			editorTexto.frame.setVisible(true);

			// Recibimos los datos
			while (true) {
				textArea.setText(in.readUTF());
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			scanner.close();
		}
	}
}
