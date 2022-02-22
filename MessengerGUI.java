import java.util.ArrayList;
import java.util.Scanner;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

/**
 * provides a GUI for the Messenger Class to simulate a messenger program
 * (contains lines that have been commented out because they would improve the program but are different from the Assignment criteria
 * (valid usernames: User1	User2	User3)
 * @author Joel Hempel
 * @since Novemeber 25, 2021
 *
 */
public class MessengerGUI extends Application {
	private BorderPane root, secondTab, thirdTab;
	private Text topText;
	private Tab Tab1, Tab2, Tab3;
	private TabPane centerPane;
	private TextField userSelectionField, recipient;
	private Button selectUser, nextMessage, loadAll, loadUnread, send;
	private TextArea displayMessageArea, sendMessageArea;
	private RadioButton s, w;
	private Scanner input;
	private Messenger msgr;
	private boolean userSelected = false, loadNext = false, validRecipient = false;
	private String user;
	private int userNumber;
	private ArrayList<Message> messages = null;
	private int messageIndex = 0;
	
	public static void main(String[] args) {
		Application.launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) {
		
		MessengerProgram();
		//initialize messenger, add users and add default messages
		
		root = new BorderPane();
		topText = new Text("Select A User");
		topText.setFont(Font.font("Arial", 17));
		root.setPadding(new Insets(10));
		root.setTop(topText);
		BorderPane.setAlignment(topText, Pos.CENTER);
		//creates basic window layout
		
		Text enterUser = new Text("Enter UserName:");
		selectUser = new Button("Select");
		userSelectionField = new TextField();
		selectUser.setOnAction(new UserSelectionHandler());
		HBox onFirstTab = new HBox(5, enterUser, userSelectionField, selectUser);
		onFirstTab.setAlignment(Pos.CENTER);
		
		Tab1 = new Tab("HBox", onFirstTab);
		//creates the first tab
		
		
		displayMessageArea = new TextArea("No Message Displayed");
		displayMessageArea.setEditable(false);
		displayMessageArea.setFont(Font.font("monospace"));
		nextMessage = new Button("Next");
		nextMessage.setDisable(true);
		nextMessage.setOnAction(new NextMessageHandler());
		HBox areaAndButton = new HBox(10, displayMessageArea, nextMessage);
		areaAndButton.setAlignment(Pos.CENTER);
		areaAndButton.setPadding(new Insets(5));
		//creates the second tab text area and next button
		
		loadAll = new Button("Load All Messages");
		loadAll.setOnAction(new ReceiveMessageHandler());
		loadUnread = new Button("Load Unread Messages");
		loadUnread.setOnAction(new ReceiveMessageHandler());
		HBox loadMessage = new HBox(5, loadAll, loadUnread);
		loadMessage.setAlignment(Pos.CENTER);
		//creates the second tab bottom buttons

		
		secondTab = new BorderPane();
		secondTab.setCenter(areaAndButton);
		secondTab.setBottom(loadMessage);
		
		
		Tab2 = new Tab("", secondTab);
		//second tab created with all elements
		
		
		thirdTab = new BorderPane();
		recipient = new TextField();
		HBox thirdPaneTop = new HBox(5, new Text("To:"), recipient);
		thirdPaneTop.setPadding(new Insets(5));
		thirdPaneTop.setAlignment(Pos.BASELINE_LEFT);
		thirdTab.setTop(thirdPaneTop);
		//creates the top of the third tab with the text field and the string "to"
		
		sendMessageArea = new TextArea();
		sendMessageArea.setFont(Font.font("monospace"));
		thirdTab.setCenter(sendMessageArea);
		//creates the text area for sending a message
		
		ToggleGroup msgType = new ToggleGroup();
		s = new RadioButton("Smile");
		w = new RadioButton("Written");
		s.setToggleGroup(msgType);
		w.setToggleGroup(msgType);
		s.setOnAction(new MessageTypeHandler());
		w.setOnAction(new MessageTypeHandler());
		msgType.selectToggle(w);
		//creates a togglegroup holding options of sending written text or a smile
		
		HBox radioButtons = new HBox(5, new Text("Message Type") ,s, w);
		radioButtons.setAlignment(Pos.CENTER);
		send = new Button("Send");
		send.setOnAction(new sendMessageHandler());
		HBox thirdPaneBottom = new HBox(40, radioButtons, send);
		thirdPaneBottom.setAlignment(Pos.CENTER);
		thirdPaneBottom.setPadding(new Insets(5, 5, 0, 5));
		//sets the bottom of the third pane with the radio buttons and the send button
		
		
		thirdTab.setBottom(thirdPaneBottom); 
		

		Tab3 = new Tab("BorderPane", thirdTab);
		//sets up the third tab with all elements on it
		
		Tab1.setText("Choose User");
		Tab2.setText("Read Messages");
		Tab3.setText("Send Message");
		Tab1.setClosable(false);
		Tab2.setClosable(false);
		Tab3.setClosable(false);
		//sets up tabs
		


		
		centerPane = new TabPane();
		centerPane.getTabs().addAll(Tab1, Tab2, Tab3);
		root.setCenter(centerPane);		
		
		
		Scene scene = new Scene(root);
		primaryStage.setTitle("Messenger GUI");
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	public class sendMessageHandler implements EventHandler<ActionEvent>{
		
		@Override
		public void handle(ActionEvent e) {
			if (userSelected) {
				//do if there is a user selected already
				int r = -1;
				for (int i = 0; i < msgr.getUserList().size(); i++){
					if (recipient.getText().equals(msgr.getUserList().get(i))) {
						validRecipient = true;
						r = i;
					}
					//checks if the recipient string is a valid username
				}
				
				if (validRecipient && w.isSelected()) {
					msgr.sendMessage(msgr.getUserList().get(userNumber), msgr.getUserList().get(r), sendMessageArea.getText());
				}
				else if (validRecipient && s.isSelected()) {
					msgr.sendSmile(msgr.getUserList().get(userNumber), msgr.getUserList().get(r));
				}
				//sends one of the two options from the user to the "to" user
				if (!validRecipient) {
					topText.setText("Recipient Username Not Found");
				}
				else {
//					recipient.setText("");
//					if (w.isSelected()) {
//						sendMessageArea.setText("");
						//could reset text area after sending message
//					}
					validRecipient = false;
					if (w.isSelected()) {
						topText.setText("Message Successfully Sent");
					}
					else {
						topText.setText("Smile Successfully Sent");
					}
					//sets status to the appropriate message
				}
			
			}
		}
	}
	
	public class MessageTypeHandler implements EventHandler<ActionEvent>{
		
		@Override
		public void handle(ActionEvent e) {
			//sets text to smile or blank depending on the radio buttons
			if (e.getSource() == s) {
				String smile = "\n ^   ^ \n" +
						 "   *   \n" +
						 "\\_____/\n";
				sendMessageArea.setText(smile);
				sendMessageArea.setEditable(false);
			}
			if (e.getSource() == w) {
				sendMessageArea.setText("");
				sendMessageArea.setEditable(true);
			}
		}
	}
	
	public class NextMessageHandler implements EventHandler<ActionEvent>{
		
		@Override
		public void handle(ActionEvent e) {
			//shows the next message in the messages arraylist to user
			//clears arraylist and disables button once there are no more messages to show
			if (loadNext) {
				if (messageIndex < messages.size()) {
					displayMessageArea.setText(messages.get(messageIndex).toString());
					messageIndex++;
				}
			}
			if (messageIndex >= messages.size()) {
				messages.clear();
				nextMessage.setDisable(true);
			}
		}
	}
	
	public class ReceiveMessageHandler implements EventHandler<ActionEvent>{
		
		@Override
		public void handle(ActionEvent e) {
			
			if (userSelected) {
				//do this if there is a username selected
				if (e.getSource() == loadAll) {
					messages = new ArrayList<Message>(msgr.getReceivedMessages(msgr.getUserList().get(userNumber)));
					messageIndex = 0;
					//loads arraylist and sets the index to 0
					if (messages.size() > 1) {
						nextMessage.setDisable(false);
						//enables "next" button once there are enough messages
					}
					if (messages.size() > 0) {
						displayMessageArea.setText(messages.get(messageIndex).toString());
					}
					messageIndex++;
					loadNext = true;
					topText.setText(messages.size() + " message(s) loaded");
					//sets what happens after loadAll is clicked
				}
				if (e.getSource() == loadUnread) {
					messages = new ArrayList<Message>(msgr.getReceivedMessages(msgr.getUserList().get(userNumber), Message.Status.Unread));
					topText.setText(messages.size() + " message(s) loaded");
					if(messages.size() > 0) {
						messageIndex = 0;
						topText.setText(messages.size() + " message(s) loaded");
						if (messages.size() > 1) {
							nextMessage.setDisable(false);
						}
						displayMessageArea.setText(messages.get(messageIndex).toString());
						messageIndex++;
						loadNext = true;
						//sets top text and displays first message
						
					}
					else {
						displayMessageArea.setText("No Message Displayed");
					}
				}
			}
		}
	}
	
	public class UserSelectionHandler implements EventHandler<ActionEvent>{
		
		@Override
		public void handle(ActionEvent e) {
			for (int i = 0; i < msgr.getUserList().size(); i++) {

				if (userSelectionField.getText().equals(msgr.getUserList().get(i))) {
					userNumber = i;
					user = userSelectionField.getText();
					userSelected = true;
					topText.setText("Current user: " + user);
//					userSelectionField.setText("");
					messages = null;
					displayMessageArea.setText("No Nessage Displayed");
//					sendMessageArea.setText("");
					messageIndex = 0;
//					recipient.setText("");
					break;
					//if the user exists reset all required elements and set the user specific values
				}
			}
			if (userSelected == false) {
				topText.setText("Incorrect Username");
			}
			//set the status to this if the username doesn't exist
				
		}
	}
	
	public void MessengerProgram() {
		input = new Scanner(System.in);
		msgr = new Messenger();
		msgr.addUser("User1");
		msgr.addUser("User2");
		msgr.addUser("User3");
		msgr.sendMessage("User1", "User2", "Hello from user1!");
		msgr.sendMessage("User1", "User3", "Hello from user1!");
		msgr.sendMessage("User2", "User1", "Hello from user2!");
		msgr.sendMessage("User2", "User3", "Hello from user2!");
		msgr.sendMessage("User3", "User1", "Hello from user3!");
		msgr.sendMessage("User3", "User2", "Hello from user3!");
		//sends default messages for testing
	}
}
