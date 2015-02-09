package udpchat.gui;

import java.awt.Component;
import java.net.URL;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeCellRenderer;

import udpchat.rooms.User;


/**Klasa WorkspaceTreeCellRendered nasledjuje DefaultTreeCellRenderer 
 * klasa renderuje prikaz JTree-ja, svaka stavka ima svoju sliku
 * @author Kantar
 */

@SuppressWarnings("serial")
public class RAFChatTreeCellRendered extends DefaultTreeCellRenderer{

public RAFChatTreeCellRendered() {
		
		// TODO Auto-generated constructor stub
	}

	  public Component getTreeCellRendererComponent(
              JTree tree,
              Object value,
              boolean sel,
              boolean expanded,
              boolean leaf,
              int row,
              boolean hasFocus) {
                  super.getTreeCellRendererComponent(tree, value, sel,expanded, leaf, row,hasFocus);
                  
             
             if (value instanceof User ) {
                 URL imageURL = getClass().getResource("images/tdiagram.png");
                 Icon icon = null;
                 if (imageURL != null)                       
                     icon = new ImageIcon(imageURL);
                 setIcon(icon);
            } else{
            	URL imageURL = getClass().getResource("images/tworkspace.png");
                Icon icon = null;
                if (imageURL != null)                       
                    icon = new ImageIcon(imageURL);
                setIcon(icon);
            	
            }
             
            return this;
}
	  }  
