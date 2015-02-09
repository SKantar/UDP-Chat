package udpchat.rooms;

import javax.swing.tree.DefaultMutableTreeNode;

/**
 * Klasa Workspace modeluje workspace koji sadrzi listu projekata
 * 
 * @author Kantar
 */

@SuppressWarnings("serial")
public class Room extends DefaultMutableTreeNode {

	private String name;

	public Room() {
		super();
		name = "RAF room";
	}

	public String toString() {
		return name;
	}
}
