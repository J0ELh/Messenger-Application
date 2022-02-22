import java.util.ArrayList;

/**
 * contains information about users and the messages that are sent
 * @author Joel Hempel
 * @since October 19th 2021
 *
 */
public class Messenger {
	private ArrayList<String> usernames = null;
	private ArrayList<Message> messages = null;
	
	/**
	 * constructor
	 */
	public Messenger() {
		this.usernames = new ArrayList<String>(0);
		this.messages = new ArrayList<Message>(0);//right method?
	}
	
	/**
	 * adds ONLY new usernames to an array of users
	 * @param username the name of the user
	 */
	public void addUser(String username) {
		for (int i = 0; i < this.usernames.size(); i++) {
			if (usernames.get(i) == username) {
				return;
			}
		}
		this.usernames.add(username);
	}
	
	/**
	 * creates a new message and adds it to the array of messages
	 * @param sender the user who sent the message
	 * @param receiver the user who receives the message
	 * @param text the message to be sent
	 */
	void sendMessage(String sender, String receiver, String text) {
		boolean sfound = false;
		boolean rfound = false;
		
		for (int i = 0; i < this.usernames.size(); i++) {
			if (usernames.get(i) == sender) {
				sfound = true;
			}
			if (usernames.get(i) == receiver) {
				rfound = true;
			}
		}
		if (sfound == false || rfound == false) {
			throw new IllegalArgumentException("Sender/Receiver not registered");
			//throw exception if sending message to nonexistent user
		}
		
		messages.add(new Message(text, sender, receiver));
		//add message to list of messages
	}
	
	/**
	 * returns the copy of an arraylist of messages given a username
	 * @param username the String name used by a user (the name of the recipient)
	 * @return ArrayList<Message> thearraylist containing username as a recipient
	 */
	public ArrayList<Message> getReceivedMessages(String username){
		int counter = 0;
		for (int i = 0; i < this.messages.size(); i++) {
			if (messages.get(i).getRecipient() == username) {
				counter++;
				//gets number of messages addressed to username to initialize arraylist
			}
		}
		ArrayList<Message> userMessages = new ArrayList<Message>(counter);
		for (int i = 0; i < this.messages.size(); i++) {
			if (messages.get(i).getRecipient() == username) {
				userMessages.add(messages.get(i));
				//adds message to array if the recipient is the username
			}
		}
		for (int i = 0; i < this.messages.size(); i++) {
			if (messages.get(i).getStatus() == Message.Status.Unread && messages.get(i).getRecipient() == username) {
				messages.get(i).setStatus(Message.Status.Read);
				//change the status for the user once unread messages are read
			}
			
		}
		ArrayList<Message> userMessagesCopy = new ArrayList<Message>(userMessages);
		
		return userMessagesCopy;
	}
	
	/**
	 * returns a copy of an arraylist of messages that have the Status "Unread" given a username
	 * @param username the String name used by a user (the name of the recipient)
	 * @param received the Status of the message
	 * @return ArrayList<Message> thearraylist containing user name as a recipient
	 */
	public ArrayList<Message> getReceivedMessages(String username, Message.Status received){
		int counter = 0;
		for (int i = 0; i < this.messages.size(); i++) {
			if (messages.get(i).getRecipient().equals(username) && messages.get(i).getStatus() == received) {
				counter++;
				//get count of messages with specified recipient and status
			}
		}
		ArrayList<Message> userMessages = new ArrayList<Message>(counter);
		for (int i = 0; i < this.messages.size(); i++) {
			if (messages.get(i).getRecipient().equals(username) && messages.get(i).getStatus() == received) {
				userMessages.add(messages.get(i));
				//add messages with specified user and status to arraylist
			}
		}
		
		for (int i = 0; i < this.messages.size(); i++) {
			if (messages.get(i).getStatus() == Message.Status.Unread && received == Message.Status.Unread && messages.get(i).getRecipient() == username) {
				messages.get(i).setStatus(Message.Status.Read);
				//set messages with specified recipient and status to arraylist
			}
		}
		
	ArrayList<Message> userMessagesCopy = new ArrayList<Message>(userMessages);
		return userMessagesCopy;
	}

	/**
	 * adds a smile message to the messeges arraylist
	 * @param from the sender
	 * @param to the recipient
	 */
	void sendSmile(String from, String to) {
		messages.add(new SmileMessage(from, to));
	}
	
	/**
	 * returns a copy of the array of users
	 * @return ArrayList<String> the list of users
	 */
	public ArrayList<String> getUserList() {
		ArrayList<String> userListCopy = new ArrayList<String>(usernames);
		return userListCopy;
	}
	
	
}
