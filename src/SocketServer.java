import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class SocketServer {
	public static void main(String[] args) {
		// Contendrá todos los clientes
		List<Socket> clientesActivos = new ArrayList<>();

		// Objetos compartidos
		int numeroCliente = 0;

		// Lanzamos el server y aceptamos todas las conexiones entrantes
		try (ServerSocket server = new ServerSocket(4200)) {
			System.out.println("Iniciando servidor");
			while (true) {
				Socket cliente = server.accept();
				clientesActivos.add(cliente);
				new ServerHandler(cliente, ++numeroCliente, clientesActivos).start();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
