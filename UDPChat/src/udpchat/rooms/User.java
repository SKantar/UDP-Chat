package udpchat.rooms;

import javax.swing.tree.DefaultMutableTreeNode;

@SuppressWarnings("serial")
public class User extends DefaultMutableTreeNode{
	
	private String name;
	private int ID;
	
	public User(String name, int ID) {
		super();
		this.name = name;
		this.ID = ID;
	}
	
	public String toString() {
		return name;
	}
	
	public int getID() {
		return ID;
	}
	
	@Override
	public boolean isLeaf() {
		return true;
	}

	

}
