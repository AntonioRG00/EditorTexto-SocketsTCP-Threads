import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.Serializable;

import javax.swing.JTextArea;

public class MiJTextArea extends JTextArea implements Serializable {

	private static final long serialVersionUID = -3394150216122338774L;

	private DataOutputStream output;

	//Constructores
	public MiJTextArea() {}
	
	/** Si el outputStream es null no tendrá KeyListener */
	public MiJTextArea(DataOutputStream output) {
		super();
		if (output != null) {
			this.output = output;
			this.addKeyListener(new KeyListener() {
				public void keyTyped(KeyEvent e) {}
				public void keyPressed(KeyEvent e) {}
				
				// Cuando la tecla sea escrita copiamos el texto y lo pasamos
				public void keyReleased(KeyEvent e) {
					try {
						MiJTextArea.this.output.writeUTF(MiJTextArea.this.getText());
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
			});
		}
	}
}
