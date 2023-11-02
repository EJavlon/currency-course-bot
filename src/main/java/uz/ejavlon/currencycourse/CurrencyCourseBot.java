package uz.ejavlon.currencycourse;

import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.*;
import org.telegram.telegrambots.meta.api.methods.updates.SetWebhook;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.starter.SpringWebhookBot;
import uz.ejavlon.currencycourse.config.BotConfig;

@Getter
@Setter
public class CurrencyCourseBot extends SpringWebhookBot {
    private BotConfig botConfig;

    public CurrencyCourseBot(SetWebhook setWebhook, BotConfig botConfig) {
        super(setWebhook, botConfig.getBotToken());
        this.botConfig = botConfig;
    }
    @Override
    public BotApiMethod<?> onWebhookUpdateReceived(Update update) {
        return null;
    }

    @Override
    public String getBotPath() {
        return botConfig.getBotPath();
    }

    @Override
    public String getBotUsername() {
        return botConfig.getUsername();
    }

    @SneakyThrows
    public void sendMessage(SendMessage sendMessage){
        execute(sendMessage);
    }

    @SneakyThrows
    public void editMessage(EditMessageText editMessageText){
        execute(editMessageText);
    }

    @SneakyThrows
    public void sendPhoto(SendPhoto sendPhoto){
        execute(sendPhoto);
    }

    @SneakyThrows
    public void sendVideo(SendVideo sendVideo){
        execute(sendVideo);
    }

    @SneakyThrows
    public void sendDocument(SendDocument sendDocument){
        execute(sendDocument);
    }

    @SneakyThrows
    public void sendAudio(SendAudio sendAudio){
        execute(sendAudio);
    }

    @SneakyThrows
    public void sendAnimation(SendAnimation sendAnimation){
        execute(sendAnimation);
    }
}
