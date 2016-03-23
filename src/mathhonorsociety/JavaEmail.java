package mathhonorsociety;

/**
 * Responsible for transfer of emails in the program
 * Sending email code is written by Joseph Kulandai found at: http://javapapers.com/core-java/java-email/
 * Imports are found at: http://www.tutorialspoint.com/java/java_sending_email.htm
 * @author Ryan McCaffrey
 */

import java.util.Properties; 
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.JOptionPane;
 
public class JavaEmail {
 
  private int memberPointsNeeded;
  private String recipientEmail; 
  private String memberFirstName;
  private String memberLastName;
  private int memberPointsHave;
  Properties emailProperties;
  Session mailSession;
  MimeMessage emailMessage;
 
 /*
 * Initializes JavaEmail object
 * @param pointNeeded Number of points required as entered by user.
 * @param receiveEmail Email address that is to receive email.
 * @param firstName First name of member that is to receive email.
 * @param lastName Last name of member that is to receive email.
 * @param pointsHave Number of points member currently has.
 */
  public JavaEmail(int pointsNeeded, String receiveEmail, String firstName, String lastName, int pointsHave)
  {
      recipientEmail = receiveEmail;
      memberPointsNeeded = pointsNeeded;
      memberFirstName = firstName;
      memberLastName = lastName;
      memberPointsHave = pointsHave;
  }
  
 /*
 * Method to set up, create, and send email to member who lacks points.
 * @param pointNeeded1 Number of points required as entered by user.
 * @param toEmail Email address that is to receive email.
 * @param firstName1 First name of member that is to receive email.
 * @param lastName1 Last name of member that is to receive email.
 * @param pointsHave1 Number of points member currently has.
 * @exception AddressException For incorrect email address.
 * @exception MessagingException For when email cannot be sent.
 */
  public static void send(int pointsNeeded1, String toEmail, String firstName1, String lastName1, int pointsHave1) throws AddressException, MessagingException 
  {
    
    JavaEmail javaEmail = new JavaEmail(pointsNeeded1, toEmail, firstName1, lastName1, pointsHave1);
 
    javaEmail.setMailServerProperties();
    javaEmail.createEmailMessage();
    javaEmail.sendEmail();
  }
 
 /*
 * Sets properties of email for email to send through Gmail server.
 */
  public void setMailServerProperties() {
 
    String emailPort = "587";//gmail's smtp port
 
    emailProperties = System.getProperties();
    emailProperties.put("mail.smtp.port", emailPort);
    emailProperties.put("mail.smtp.auth", "true");
    emailProperties.put("mail.smtp.starttls.enable", "true");
 
  }
 
 /*
 * Writes the header and body of the email for a member who lacks points. Adds recipient to email.
 * @exception AddressException For incorrect email address.
 * @exception MessagingException For when email cannot be sent.
 */
  public void createEmailMessage() throws AddressException, MessagingException 
  {
    String[] toEmails = { recipientEmail };
    String emailSubject = "Math Honor Society Membership Status";
    String subject = "Dear " + memberFirstName + " " + memberLastName + ",\n\n";
    String body = "You currently have " + memberPointsHave + " points in Math Honor Society, which is below the recommended "
            + memberPointsNeeded + " points you should have at this time. You can earn more points by attending meetings and"
            + " tutoring at extra helps before and after school. If you do not accumulate enough points, you will lose membership"
            + " in the Math Honor Society.";
    String ending = "\n\n\nSincerely,\nCommack Math Honor Society";
    String emailBody = subject + body + ending;
 
    mailSession = Session.getDefaultInstance(emailProperties, null);
    emailMessage = new MimeMessage(mailSession);
 
    for (int i = 0; i < toEmails.length; i++) {
      emailMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(toEmails[i]));
    }
 
    emailMessage.setSubject(emailSubject);
    //emailMessage.setContent(emailBody, "text/html");//for a html email
    emailMessage.setText(emailBody);// for a text email
 
  }
  
 /*
 * Writes the header and body of the email for a forgot password email. Adds recipient to email.
 * @exception AddressException For incorrect email address.
 * @exception MessagingException For when email cannot be sent.
 */
  public void createForgotPasswordEmailMessage() throws AddressException, MessagingException 
  {
    String[] toEmails = { recipientEmail };
    String emailSubject = "Password Retrieval";
    String emailBody = "You have requested to retrieve the password for the Math Honor Society Point Manager. \nPassword: ";
    String pass = "";
    for (int i = 0; i < MainOperations.getPassword().length; i++)
        {
            pass = pass + MainOperations.getPassword()[i];
        }
    emailBody = emailBody + pass;
 
    mailSession = Session.getDefaultInstance(emailProperties, null);
    emailMessage = new MimeMessage(mailSession);
 
    for (int i = 0; i < toEmails.length; i++) {
      emailMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(toEmails[i]));
    }
 
    emailMessage.setSubject(emailSubject);
    //emailMessage.setContent(emailBody, "text/html");//for a html email
    emailMessage.setText(emailBody);// for a text email
 
  }
 
 /*
 * Sends email to the desired email address through commackmathhonorsociety@gmail.com
 * @exception AddressException For incorrect email address.
 * @exception MessagingException For when email cannot be sent.
 */
  public void sendEmail() throws AddressException, MessagingException {
 
    String emailHost = "smtp.gmail.com";
    String fromUser = "commackmathhonorsociety";//just the id alone without @gmail.com
    String fromUserEmailPassword = "MathIsC00l";
 
    Transport transport = mailSession.getTransport("smtp");
 
    transport.connect(emailHost, fromUser, fromUserEmailPassword);
    transport.sendMessage(emailMessage, emailMessage.getAllRecipients());
    transport.close();
  }
  
 /*
 * Method to set up, create, and send email to user who forgot password.
 * @param toEmail Email address that is to receive email.
 * @exception AddressException For incorrect email address.
 * @exception MessagingException For when email cannot be sent.
 */
  public static void forgotEmail(String emailAddress) throws AddressException, MessagingException
  {
    JavaEmail javaEmail = new JavaEmail(0, emailAddress, "", "", 0);
 
    javaEmail.setMailServerProperties();
    javaEmail.createForgotPasswordEmailMessage();
    javaEmail.sendEmail();
    JOptionPane.showMessageDialog(null, "Email has been sent.");
  }
  
 /*
 * Main method for testing purposes
 * @param args Unused.
 * @exception AddressException For incorrect email address.
 * @exception MessagingException For when email cannot be sent.
 */
  public static void main(String args[]) throws AddressException, MessagingException
  {
    
    JavaEmail javaEmail = new JavaEmail(0, "ry543@aol.com", "", "", 0);
 
    javaEmail.setMailServerProperties();
    javaEmail.createEmailMessage();
    javaEmail.sendEmail();
      
  }
 
}


