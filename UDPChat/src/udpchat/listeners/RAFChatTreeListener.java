package udpchat.listeners;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.tree.TreePath;

import udpchat.app.AppCore;
import udpchat.rooms.User;
import udpchat.view.PrivateRoomView;

/**Klasa WorkspaceTreeListener implementira MouseListener
 * i osluskuje dogadjaje misa na stablu aplikacije
 * @author Kantar
 */

public class RAFChatTreeListener extends MouseAdapter{

	public void mouseClicked(MouseEvent arg0) {
		if(arg0.getClickCount() == 2){
			int selRow = AppCore.getInstance().getRafChatTree().getRowForLocation(arg0.getX(), arg0.getY());
			TreePath selPath = AppCore.getInstance().getRafChatTree().getPathForLocation(arg0.getX(), arg0.getY());
				
				if(selRow != -1){
					if (selPath.getLastPathComponent() instanceof User){
						 		User user = (User)selPath.getLastPathComponent();
						 		//System.out.println(user.getID());
						 		if(user.getID() == AppCore.getInstance().getClient().getID()) return;

						 		if(!AppCore.getInstance().getDesktop().existsTabWithID(user.getID())){
						 			PrivateRoomView room = new PrivateRoomView(user);
						 			AppCore.getInstance().getDesktop().add(user.toString() + " " +user.getID(), room);
						 			AppCore.getInstance().getPrivateRooms().add(room);	
						 			AppCore.getInstance().getDesktop().setSelectedComponent(room);
						 			//AppCore.getInstance().getPrivateRooms().add(room);
						 		}
						 }
					 }
				}
			}
}
