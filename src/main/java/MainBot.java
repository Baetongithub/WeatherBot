import bot_data.BotsData;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainBot extends TelegramLongPollingBot {

    public static void main(String[] args) {
        ApiContextInitializer.init();
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
        try {
            telegramBotsApi.registerBot(new MainBot());
        } catch (TelegramApiRequestException e) {
            e.printStackTrace();
        }
    }

    public void onUpdateReceived(Update update) {
        Model model = new Model();
        Message message = update.getMessage();
        if (message != null && message.hasText()) {
            switch (message.getText()) {
                case "/start":
                    sendMessage(message, "Made by Baet. Then попробуй \uD83D\uDC49 \uD83D\uDD27 Жардам");
                    break;
                case "\uD83D\uDD27 Жардам":
                    sendMessage(message, "Эмне болду?");
                    break;
                case "\uD83D\uDD0D Ондоо":
                    sendMessage(message, "Эмне ондош керек?");
                    break;
                default:
                    try {
                        sendMessage(message, Weather.getWeather(message.getText(), model));
                    } catch (IOException e) {
                        sendMessage(message, "Мындай шаар жок");
                    }
            }
        }
    }

    private void sendMessage(Message message, String s) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(message.getChatId().toString());
        sendMessage.setReplyToMessageId(message.getMessageId());
        sendMessage.setText(s);
        try {
            setButtons(sendMessage);
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void setButtons(SendMessage sendMessage) {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);

        List<KeyboardRow> rowList = new ArrayList<>();
        KeyboardRow keyboard1stRow = new KeyboardRow();
        keyboard1stRow.add(new KeyboardButton("\uD83D\uDD27 Жардам"));
        keyboard1stRow.add(new KeyboardButton("\uD83D\uDD0D Ондоо"));

        rowList.add(keyboard1stRow);
        replyKeyboardMarkup.setKeyboard(rowList);
    }

    public String getBotUsername() {
        return "Baet";
    }

    public String getBotToken() {
        return BotsData.botToken;
    }
}