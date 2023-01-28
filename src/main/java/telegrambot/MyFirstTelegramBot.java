package telegrambot;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class MyFirstTelegramBot extends TelegramLongPollingBot{

	
	
	public void onUpdateReceived(Update update) {

		SendMessage message = new SendMessage();
		String messageText = update.getMessage().getText();

			if(messageText.contains("/myName")) {
				message.setText("Hi How Are you");
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


	public String getBotUsername() {
		return "mathanFirstTelegrambot";
	}

	public String getBotToken() {
		return "594*********pE";
	}
	
	
}

	



