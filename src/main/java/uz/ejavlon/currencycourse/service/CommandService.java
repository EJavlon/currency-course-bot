package uz.ejavlon.currencycourse.service;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import uz.ejavlon.currencycourse.CurrencyCourseBot;
import uz.ejavlon.currencycourse.entity.Course;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CommandService {
    private final UserService userService;
    private final TelegramComponentsService telegramComponentsService;
    private final CurrencyCourseService currencyCourseService;
    private final CurrencyCourseBot bot;

    /**
     * start commandasi
     * @param update
     * @return
     */
    public SendMessage startCommand(Update update){
        User user = update.getMessage().getFrom();
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(update.getMessage().getChatId());
        sendMessage.setReplyToMessageId(update.getMessage().getMessageId());
        sendMessage.setText(
                String.format("<b>Ассалому алайкум, %s !</b>\n\n" +
                                "<i>/course - курс маълумотларини олиш</i>",
                        user.getFirstName()
                ));
        sendMessage.setParseMode("HTML");
        new Thread(() -> {
            userService.findUser(user,update.getMessage().getChatId());
        }).start();
        return sendMessage;
    }

    /**
     * course commandasi
     * @param update
     * @return
     */
    public SendMessage courseCommand(Update update){
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(update.getMessage().getChatId());
        sendMessage.setText("<b>Валютани танланг</b>");
        sendMessage.setParseMode("HTML");
        sendMessage.setReplyMarkup(telegramComponentsService.getCourseButtons(1));
        return sendMessage;
    }

    /**
     * callbackQueryni aniqlash
     * @param update
     */
    public void callbackQuery(Update update){
        CallbackQuery callbackQuery = update.getCallbackQuery();
        if (callbackQuery.getData().startsWith("page")){
            editCoursePage(callbackQuery);
        }else {
            courseButtonOnClick(callbackQuery);
        }
    }

    /**
     * inline course button pageni o'zgartirish
     * @param callbackQuery
     */
    public void editCoursePage(CallbackQuery callbackQuery){
        String data = callbackQuery.getData().split("-",2)[1];
        EditMessageText editMessageText = new EditMessageText();
        editMessageText.setChatId(callbackQuery.getMessage().getChatId());
        editMessageText.setMessageId(callbackQuery.getMessage().getMessageId());
        editMessageText.setText("<b>Валютани танланг</b>");
        editMessageText.setParseMode("HTML");
        editMessageText.setReplyMarkup(telegramComponentsService.getCourseButtons(Integer.parseInt(data)));
        bot.editMessage(editMessageText);
    }

    /**
     * kurs ma'lumotlarini jo'natish
     * @param callbackQuery
     */
    @SneakyThrows
    public void courseButtonOnClick(CallbackQuery callbackQuery){
        Course course = currencyCourseService.getCourse(callbackQuery.getData());
        String format = course.getUpdatedDateTime().format(DateTimeFormatter.ofPattern("HH:mm:ss  (dd.MM.yyyy)"));
        String result = String.format(
                "<b>1 %s = %s UZS\n\n" +
                        "\uD83D\uDCB5 Валюта номи : %s\n\n" +
                        "\uD83D\uDCB9 Валюта фарқи : %s UZS (%s)\n\n" +
                        "♻\uFE0F Оxирги янгиланиш : %s\n\n" +
                        "\uD83C\uDF10\uFE0F Манба : cbu.uz</b>" +
                        "\n\n<a href='https://t.me/ccuz_bot'>\uD83E\uDD16 Валюта курслари боти</a>",
                course.getCcy(),course.getRate(),course.getCcyNmUZC(),course.getDiff(),
                course.getDiff() == 0 ? "ўзгармаган" : course.getDiff() < 0 ? "пастлаган" : "кўтарилган",
                format
        );
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(callbackQuery.getMessage().getChatId());
        sendMessage.setText(result);
        sendMessage.setParseMode("HTML");
        sendMessage.setReplyMarkup(telegramComponentsService.botLink());
        bot.sendMessage(sendMessage);
        refreshInlineCourseButtons(callbackQuery);
    }

    /**
     * inline course button pageni refresh qilish
     * @param callbackQuery
     */
    public void refreshInlineCourseButtons(CallbackQuery callbackQuery){
        List<List<InlineKeyboardButton>> rowList = callbackQuery.getMessage().getReplyMarkup().getKeyboard();
        List<InlineKeyboardButton> navigation = rowList.get(rowList.size() - 1);
        navigation.get(1).setCallbackData("status " + UUID.randomUUID());
        rowList.set(rowList.size()-1,navigation);
        callbackQuery.getMessage().getReplyMarkup().setKeyboard(rowList);

        EditMessageText editMessageText = new EditMessageText();
        editMessageText.setChatId(callbackQuery.getMessage().getChatId());
        editMessageText.setMessageId(callbackQuery.getMessage().getMessageId());
        editMessageText.setReplyMarkup(callbackQuery.getMessage().getReplyMarkup());
        editMessageText.setText("<b>Валютани танланг</b>");
        editMessageText.setParseMode("HTML");
        bot.editMessage(editMessageText);
    }
}