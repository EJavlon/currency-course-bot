package uz.ejavlon.currencycourse.config;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@FieldDefaults(level = AccessLevel.PRIVATE)
@Setter
@Getter
public class BotConfig {
    @Value("${telegrambot.userName}")
    String username;

    @Value("${telegrambot.webHookPath}")
    String botPath;

    @Value("${telegrambot.botToken}")
    String botToken;

    @Value("${telegrambot.courseApi}")
    String courseApi;

}
