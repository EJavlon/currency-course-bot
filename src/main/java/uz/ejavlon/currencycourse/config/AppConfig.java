package uz.ejavlon.currencycourse.config;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.client.RestTemplate;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.updates.SetWebhook;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import uz.ejavlon.currencycourse.CurrencyCourseBot;
import uz.ejavlon.currencycourse.entity.Course;
import uz.ejavlon.currencycourse.repository.CourseRepository;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

@Configuration
@AllArgsConstructor
@EnableScheduling
public class AppConfig {

    private final BotConfig botConfig;
    private final CourseRepository courseRepository;

    @Bean
    public SetWebhook setWebhookInstance() {
        return SetWebhook.builder()
                .url(botConfig.getBotPath())
                .build();
    }

    @Bean
    @SneakyThrows
    public CurrencyCourseBot currencyCourseBot(SetWebhook setWebhook, Environment environment){
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.exchange(
                String.format(
                        environment.getProperty("telegrambot.setWebhookUrl"),
                        environment.getProperty("telegrambot.botToken"),
                        environment.getProperty("telegrambot.webHookPath")
                ),
                HttpMethod.GET, HttpEntity.EMPTY, Object.class
        );
        CurrencyCourseBot currencyCourseBot = new CurrencyCourseBot(setWebhook, botConfig);
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
        telegramBotsApi.registerBot(currencyCourseBot,setWebhook);
        return currencyCourseBot;
    }

    @Scheduled(initialDelay = 3600000, fixedDelay = 3600000)
    @Caching(evict = {@CacheEvict(value="course", allEntries=true)})
    public void updateCourse() {
        RestTemplate restTemplate = new RestTemplate();
        Course[] newCourses = restTemplate.getForEntity(botConfig.getCourseApi(), Course[].class).getBody();
        assert newCourses != null;
        courseRepository.saveAll(Arrays.asList(newCourses));
    }

    @Scheduled(initialDelay = 86400000, fixedDelay = 86400000)
    public void clearCourseTable(){
        courseRepository.deleteJpaQuery();
    }

    @Bean
    public List<List<InlineKeyboardButton>> courseButtonList(){
        updateCourse();
        List<List<InlineKeyboardButton>> rowList = new ArrayList<>(75);
        List<Course> courseList = courseRepository.findAllByFirst75();
        Comparator<Course> courseComparator = new Comparator<Course>() {
            @Override
            public int compare(Course o1, Course o2) {
                return (int) (o1.getUniqueId()-o2.getUniqueId());
            }
        };
        courseList.sort(courseComparator);
        List<InlineKeyboardButton> row;
        InlineKeyboardButton button;
        for (int i = 0; i < 75; i++) {
            row = new ArrayList<>(1);
            button = new InlineKeyboardButton();
            button.setText(courseList.get(i).getCcyNmUZC());
            button.setCallbackData(courseList.get(i).getCcy());
            row.add(button);
            rowList.add(row);
        }
        return rowList;
    }

    @Bean
    public List<List<InlineKeyboardButton>> navigationButtonList() {
        InlineKeyboardButton button;
        List<InlineKeyboardButton> row;
        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        for (int i = 1; i <= 8; i++) {
            button = new InlineKeyboardButton();
            row = new ArrayList<>(3);
            button.setText("❮");
            button.setCallbackData("page-"+ (i == 1 ? 8 : (i-1)));
            row.add(button);

            button = new InlineKeyboardButton();
            button.setText(i + "/" + 8);
            button.setCallbackData("status");
            row.add(button);

            button = new InlineKeyboardButton();
            button.setText("❯");
            button.setCallbackData("page-" + (i == 8 ? 1 : (i+1)));
            row.add(button);
            rowList.add(row);
        }
        return rowList;
    }
}
