package udpchat.model;

import javax.swing.tree.DefaultTreeModel;

import udpchat.rooms.Room;
import udpchat.rooms.User;

@SuppressWarnings("serial")
public class RAFChatModel extends DefaultTreeModel {
	
	public RAFChatModel() {
		super(new Room());
	}
	
	public void addUser(User user){
		((Room)getRoot()).add(user);;
	}

}
