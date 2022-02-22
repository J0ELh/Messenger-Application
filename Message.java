/**
 * Simulates a message sent between two people
 * @author Joel Hempel
 * @since October 19th 2021
 *
 */
public class Message {
	public enum Status{Unread, Read, Starred};
	private String text, sender, recipient;
	private Status received;
	private static int messageCount = 0;
	private static int lengthOfMessages = 0;
	
	/**
	 * creates a new text message
	 * @param text the String to send to the recipient
	 * @param username the sender of the message
	 * @param recipient the recipient of the message
	 * @param received the status of the message
	 */
	public Message(String text, String username, String recipient, Status received) {
		if (text == null || username == null || recipient == null) {
			throw new NullPointerException("text, username and recipient must not be null");
		}
		this.text = text;
		this.sender = username;
		this.recipient = recipient;
		this.received = received;
		messageCount++;
		lengthOfMessages += text.length();
	}
	
	/**
	 * sends message given message, sender and recipient
	 * @param text the text to be sent
	 * @param username the sender of the text
	 * @param recipient the recipient of the text
	 */
	public Message(String text, String username, String recipient) {
		this(text, username, recipient, Message.Status.Unread);
	}
	
	/**
	 * returns the message text
	 * @return the message text
	 */
	public String getText() {
		return this.text;
	}
	
	/**
	 * returns the sender
	 * @return String the sender of the message
	 */
	public String getSender() {
		return this.sender;
	}
	
	/**
	 * return the recipient
	 * @return String the recipient of the message
	 */
	public String getRecipient() {
		return this.recipient;
	}
	
	/**
	 * return the Status of the message
	 * @return Status the status of the message
	 */
	public Status getStatus() {
		return this.received;
	}
	
	/**
	 * return the amount of messages sent
	 * @return int amount of messages sent
	 */
	public static int getMessageCount() {
		return messageCount;
	}
	
	/**
	 * return the total length of characters sent including new line characters
	 * @return int total number of characters sent
	 */
	public static int getTotalTextLength() {
		return lengthOfMessages;
	}
	
	/**
	 * set messages to starred or read
	 * @param received the status the message should be set to
	 */
	public void setStatus(Status received) {
		if (received == null) {
			throw new NullPointerException();
		}
		if (received != Message.Status.Unread) {
			this.received = received;
			//leave unread as unread
			//prevent going back from read or starred to unread;
		}
	}
	
	/**
	 * set the toString output
	 * @return String the summary of the Message class
	 */
	public String toString() {
		String output = "";
		output += "\nUsername Sender: " + this.sender + "\nUsername Recipient: " + this.recipient +
				"\nStatus: " + this.received  +"\nText: " + this.text + "\n\n"; 
		return output;
	}
}
