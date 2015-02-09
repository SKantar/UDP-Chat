package udpchat.app;

import java.awt.EventQueue;


/**Klasa Main sadrzi main metodu i pravi instancu glavne klase GEDa
 * @author Kantar
 */


public class Main {
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login frame = new Login();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
