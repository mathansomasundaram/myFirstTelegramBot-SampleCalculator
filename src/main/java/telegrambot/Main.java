package telegrambot;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;


public class Main {

	public static void main(String[] args) {

	        try {
	        	TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
	        	
	            telegramBotsApi.registerBot(new MyFirstTelegramBot());

	        } catch (TelegramApiException e) {
	            e.printStackTrace();
	        }
	}
	
	 public static boolean isInstanceRunning() {
		 
		 	MyFirstTelegramBot myBot=new MyFirstTelegramBot();
	 		myBot.getBotToken();
		 	
	        String url = "https://api.telegram.org/bot" + myBot.getBotToken() + "/getMe";
	        HttpClient httpClient = HttpClientBuilder.create().build();
	        HttpGet get = new HttpGet(url);
	        try {
	            HttpResponse response = httpClient.execute(get);
	            int statusCode = response.getStatusLine().getStatusCode();
	            if(statusCode == 200) {
	                return true;
	            } else {
	                return false;
	            }
	        } catch (IOException e) {
	            e.printStackTrace();
	            return false;
	        }
	 }
}
