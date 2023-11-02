package uz.ejavlon.currencycourse.service;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import java.util.ArrayList;
import java.util.List;

@Configuration
@RequiredArgsConstructor
public class TelegramComponentsService {
    private final List<List<InlineKeyboardButton>> navigationButtonList;
    private final List<List<InlineKeyboardButton>> courseButtonList;

    @Bean
    @SneakyThrows
    public InlineKeyboardMarkup botLink(){
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        List<InlineKeyboardButton> row = new ArrayList<>();
        InlineKeyboardButton button = new InlineKeyboardButton();
        button.setUrl("https://t.me/share/url?url=https://t.me/ccuz_bot");
        button.setText("Ботни улашиш");
        row.add(button);
        rowList.add(row);
        markup.setKeyboard(rowList);
        return markup;
    }

    @SneakyThrows
    public InlineKeyboardMarkup getCourseButtons(int page){
        int range = page == 8 ? 75 : 10*page;
        int start = page == 1 ? 0 : 10*(page-1);
        List<List<InlineKeyboardButton>> buttons = new ArrayList<>(10);
        for (int i = start; i < range; i++) {
            buttons.add(courseButtonList.get(i));
        }
        buttons.add(navigationButtonList.get(page-1));
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        markup.setKeyboard(buttons);
        return markup;
    }
}
