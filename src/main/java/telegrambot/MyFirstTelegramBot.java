package telegrambot;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.Stack;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class MyFirstTelegramBot extends TelegramLongPollingBot{

	private static String botTokenID=null;
	private static String botUserName=null;
	private static Properties properties=null;
	private static Stack<String> stack=new Stack<>();
	private static String previousMessage="";
	String outputMessage="The Output Value is:";
	private static final List<String> operations= Arrays.asList("/add","/subtract","/multiply","/divide");
	
	static {
		try {
		 properties=getProperties();
		 botUserName=properties.getProperty("UserName");
		 botTokenID=properties.getProperty("TokenID");
		}catch(Exception e) {
			System.out.println(e);
		}
	}
	
	public void onUpdateReceived(Update update) {

		SendMessage message = new SendMessage();
		String messageText = update.getMessage().getText();
		String loggedUser=update.getMessage().getFrom().getFirstName();
		if(messageText.equalsIgnoreCase("/Start")) {
			message.setText("Hello "+loggedUser+" I'm designed to perform basic mathematical calculation");
		}else {
			if (!stack.isEmpty())
				previousMessage = stack.pop();
			try {
		        switchOperations(previousMessage, messageText, message,update);
				if (operations.contains(messageText)) {
					stack.add(messageText);
					message.setText("Enter Input Numbers");
				}
			} catch (NumberFormatException e) {
				message.setText("Only Numbers can be Inputed");
			} catch (Exception e) {
				message.setText("Unknow Exception Occured");
				System.out.println(e);
			}
		}
 		if (message.getText() != null) {
			message.setChatId(update.getMessage().getChatId());
			try {
				execute(message);
			} catch (TelegramApiException e) {
				e.printStackTrace();
			}
		}
 		
	}

	private void switchOperations(String previousMessage, String messageText, SendMessage message,Update update) {
		double resultantValue;
		double num1=0;
		double num2=0;
		String[] numbers;
		if (!operations.contains(messageText)) {
            numbers = messageText.split(" ");
            num1 = Integer.parseInt(numbers[0]);
            num2 = Integer.parseInt(numbers[1]);
        }
		switch (previousMessage) {
			case "/add":
				resultantValue = num1 + num2;
				System.out.println(resultantValue);
				message.setText(outputMessage + resultantValue);
				break;
			case "/subtract":
				resultantValue = num2 - num1;
				System.out.println(resultantValue);
				message.setText(outputMessage + resultantValue);
				break;
			case "/multiply":
				resultantValue = num2 * num1;
				System.out.println(resultantValue);
				message.setText(outputMessage + resultantValue);
				break;
			case "/divide":
				if(num2==0) {
					message.setText("Number cannot be divisible Zero");
					break;
				}
				resultantValue = num1 / num2;
				System.out.println(resultantValue);
				message.setText(outputMessage + resultantValue);
				break;
		
			default:
				message.setText("Unrecognized Command");
				break;
		}
	}

	public String getBotUsername() {
		return botUserName;
	}

	public String getBotToken() {
		return botTokenID;
	}
	
	private static Properties getProperties() {
		File configFile = null;
		FileReader reader = null;
		Properties property = null;
		try {

			configFile = new File("Configuration.properties");
			reader = new FileReader(configFile);
			property = new Properties();
			property.load(reader);

		} catch (FileNotFoundException fileNotFounException) {
			System.out.println(fileNotFounException.getMessage());
		} catch (IOException ioException) {
			System.out.println(ioException.getMessage());

		} finally {
			try {
				if (reader != null)
					reader.close();
			} catch (Exception e) {
			}
		}
		return property;
	}

}

	



