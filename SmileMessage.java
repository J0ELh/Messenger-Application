/**
 * sends a message containing a smiley
 * @author Joel Hempel
 * @since October 19th 2021
 */
public class SmileMessage extends Message{
	private static final String SMILEY = "\n ^   ^ \n" +
			 "   *   \n" +
			 "\\_____/\n";
	/**
	 * constructs a smile message to add to the arraylist of messages
	 * @param sender the person sending the smile message
	 * @param recipient the recipient of the smile message
	 */
	public SmileMessage(String sender, String recipient) {
		super(SMILEY, sender, recipient);
	}
	
	

}