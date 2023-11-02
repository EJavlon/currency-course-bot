package uz.ejavlon.currencycourse.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import uz.ejavlon.currencycourse.CurrencyCourseBot;
import uz.ejavlon.currencycourse.service.CommandService;

@RestController
@RequestMapping("/api/v1/webhook")
@RequiredArgsConstructor
public class WebHookController {
    private final CurrencyCourseBot currencyCourseBot;
    private final CommandService commandService;

    @PostMapping
    public BotApiMethod<?> onUpdateReceived(@RequestBody Update update) {

        if (update.hasMessage() && "/start".equals(update.getMessage().getText())) {
            currencyCourseBot.sendMessage(commandService.startCommand(update));
        } else if (update.hasMessage() && "/course".equals(update.getMessage().getText())) {
            currencyCourseBot.sendMessage(commandService.courseCommand(update));
        } else if (update.hasCallbackQuery() && !update.getCallbackQuery().getData().startsWith("status")) {
            commandService.callbackQuery(update);
        }
        return currencyCourseBot.onWebhookUpdateReceived(update);
    }
}
