package udpchat.app;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.SwingUtilities;
import javax.swing.ToolTipManager;
import javax.swing.UIManager;
import javax.swing.WindowConstants;

import udpchat.client.Client;
import udpchat.gui.RAFChatTree;
import udpchat.model.RAFChatModel;
import udpchat.rooms.Room;
import udpchat.rooms.User;
import udpchat.view.ClosableTabbedPane;
import udpchat.view.PrivateRoomView;
import udpchat.view.PublicRoomView;


@SuppressWarnings("serial")
public class AppCore extends JFrame implements Runnable{
	private static AppCore instance = null;
    private ArrayList<PrivateRoomView> privateRooms = new ArrayList<PrivateRoomView>();
    private RAFChatModel rafChatModel;
    private RAFChatTree rafChatTree;
    private Client client;
    private ClosableTabbedPane desktop;
    private Thread run, listen;
	private boolean running = false;
	private PublicRoomView publicRoom;
	private static int oldNumberOfUsers = 0;
    
	private AppCore() {
		client = new Client(Login.getClient());
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				String disconnect = "/d/" + client.getID() + "/e/";
				publicRoom.send(disconnect, false);
				running = false;
				client.close();
			}
		});
	}
	
	private void initialise(){
		
		initialiseRAFChatTree();
		initialiseGUI();
		initializeRooms();
			
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			SwingUtilities.updateComponentTreeUI(this);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void initialiseGUI(){
		desktop=new ClosableTabbedPane();
		JScrollPane scroll=new JScrollPane(rafChatTree);
		scroll.setMinimumSize(new Dimension(200,150));
		JSplitPane split=new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,scroll,desktop);
		add(split,BorderLayout.CENTER);
		split.setDividerLocation(250);
		setSize(800,600);
		setLocationRelativeTo(null);
		setTitle("RAF Chat");
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		//setIconImage((new ImageIcon(getClass().getResource("images/icon.png"))).getImage());
		

	}
	private void initialiseRAFChatTree(){
		rafChatTree=new RAFChatTree();
		rafChatModel = new RAFChatModel();
		rafChatTree.setModel(rafChatModel);
		ToolTipManager.sharedInstance().registerComponent(rafChatTree);
	}
	
	private void initializeRooms(){	
		
		publicRoom = new PublicRoomView();
		boolean connect = client.openConnection(client.getAddress());
		
		if (!connect) {
			System.err.println("Connection failed!");
			publicRoom.console("Connection failed!");
		}
		
		desktop.addTab("RAF Chat public", publicRoom);
		publicRoom.console("Attempting a connection to " + client.getAddress() + ":" + client.getPort() + ", user: " + client.getName());
		String connection = "/c/" + client.getName() + "/e/";
		client.send(connection.getBytes());
		running = true;
		run = new Thread(this, "Running");
		run.start();

	}
	
	
	public static AppCore getInstance(){
		if (instance==null){
			instance=new AppCore();
			instance.initialise();
		}
		return instance;
	}


	public ClosableTabbedPane getDesktop() {
		return desktop;
	}

	public RAFChatModel getRafChatModel() {
		return rafChatModel;
	}

	public RAFChatTree getRafChatTree() {
		return rafChatTree;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public ArrayList<PrivateRoomView> getPrivateRooms() {
		return privateRooms;
	}

	public void run() {
		listen();
	}

	public void listen() {
		listen = new Thread("Listen") {
			public void run() {
				while (running) {
					String message = client.receive();
					//System.out.println(message);
					if (message.startsWith("/c/")) {
						client.setID(Integer.parseInt(message.split("/c/|/e/")[1]));
						publicRoom.console("Successfully connected to server! ID: " + client.getID());
					} else if (message.startsWith("/RAF Chat/")) {
						String text = message.substring(10);
						text = text.split("/e/")[0];
						publicRoom.console(text);
					} else if (message.startsWith("/i/")) {
						String text = "/i/" + client.getID() + "/e/";
						publicRoom.send(text, false);
					}else if (message.startsWith("/u/")) {
						String text = message.substring(3);
						String text1[] = text.split("/n/");
						text1[text1.length-1] = text1[text1.length-1].split("/e/")[0];
						if(text1.length != oldNumberOfUsers){
							oldNumberOfUsers = text1.length;	
							Room root = (Room) rafChatModel.getRoot();
							root.removeAllChildren();
							for(int i = 0; i < text1.length; i++){
								String[] temptxt = text1[i].split("/");
								String name = temptxt[0];
								String number = temptxt[1];
								//System.out.println("==============");
								//System.out.println(name+" - "+number);
								User user1 = new User(name, Integer.parseInt(number));
								rafChatTree.addUser(user1);	
							}
							
							SwingUtilities.updateComponentTreeUI(rafChatTree);
							rafChatTree.expandRow(0);
						}
						
					}else if(message.startsWith("/p/")){
						String text = message.substring(3);
						String msg = text.split("/")[2];
						String text1 = text.split("/")[1];
						text = text.split("/")[0];
						System.out.println(text1 + " == > " + text);
						int id = Integer.parseInt(text1);
						if(!desktop.existsTabWithID(id)){
							Room root = (Room) rafChatModel.getRoot();
							int childCount = root.getChildCount();
							for(int i = 0; i < childCount; i++ ){
								if(((User)root.getChildAt(i)).getID() == Integer.parseInt(text1)){
									PrivateRoomView priroom = new PrivateRoomView(((User)root.getChildAt(i)));
									desktop.addTab(((User)root.getChildAt(i)).toString()+" "+((User)root.getChildAt(i)).getID(), priroom);
									for(PrivateRoomView a : privateRooms)
										if(a.getUser().getID() == id)
											break;
									privateRooms.add(priroom);
								}
							}
						}
						
						for(PrivateRoomView a : privateRooms){
							if(a.getUser().getID() == id){
								a.console(msg);
								break;
							}
						}//publicRoom.console(message);
						
					}
				}
			}
		};
		listen.start();
	}
	
	
	

}
