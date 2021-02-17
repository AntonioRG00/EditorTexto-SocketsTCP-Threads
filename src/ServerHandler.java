import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Iterator;
import java.util.List;

public class ServerHandler extends Thread {

	private final DataInputStream input;
	private final DataOutputStream output;
	private final Socket cliente;
	private final int idCliente;
	private final List<Socket> clientesActivos;

	@Override
	public void run() {
		System.out.println("Cliente conectado => " + idCliente);
		try {
			//Enviamos al cliente su idCliente
			output.writeInt(idCliente);
			
			while(true) {
				//Recibimos las escrituras de los clientes
				String mensajeCliente = input.readUTF();
				
				//Enviamos el mensaje a los demás clientes
				for(Iterator<Socket> iterador = clientesActivos.iterator(); iterador.hasNext();) {
					Socket socketCliente = iterador.next();
					try {
						if(!socketCliente.equals(cliente)) {							
							new DataOutputStream(socketCliente.getOutputStream()).writeUTF(mensajeCliente);
						}
					} catch (IOException e) {
						//Si da un error es porque el cliente esta desconectado
						iterador.remove();
					}
				}
			}
		} catch (IOException e) {
			//Si da un error es porque el cliente esta desconectado
			System.out.println("Cliente desconectado => " + idCliente);
		}
	}

	// Constructores
	public ServerHandler(Socket cliente, int idCliente, List<Socket> clientesActivos) throws IOException {
		this.clientesActivos = clientesActivos;
		this.idCliente = idCliente;
		this.cliente = cliente;
		this.input = new DataInputStream(cliente.getInputStream());
		this.output = new DataOutputStream(cliente.getOutputStream());
	}

	// Getters y Setters
	public DataInputStream getInput() {
		return input;
	}

	public DataOutputStream getOutput() {
		return output;
	}

	public Socket getCliente() {
		return cliente;
	}
}