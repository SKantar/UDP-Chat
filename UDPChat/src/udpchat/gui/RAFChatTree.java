package udpchat.gui;

import javax.swing.JTree;

import udpchat.listeners.RAFChatTreeListener;
import udpchat.model.RAFChatModel;
import udpchat.rooms.User;

/**Klasa WorkspaceTree nasledjuje klasu JTree i predtavlja
 * stablo aplikacije
 * @author Kantar
 */

@SuppressWarnings("serial")
public class RAFChatTree extends JTree{

public RAFChatTree() {
	    //setCellEditor(new WorkspaceTreeEditor(this,new DefaultTreeCellRenderer()));
	    setCellRenderer(new RAFChatTreeCellRendered());
	    addMouseListener(new RAFChatTreeListener());
	}

	
	/**
	 * Metoda za dodavanje novog projekta u workspace 
	 * @param project
	 */
	public void addUser(User user){
		((RAFChatModel)getModel()).addUser(user);
		//SwingUtilities.updateComponentTreeUI(this);
	}
}
