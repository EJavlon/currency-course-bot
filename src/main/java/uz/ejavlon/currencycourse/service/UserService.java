package uz.ejavlon.currencycourse.service;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.User;
import uz.ejavlon.currencycourse.repository.UserRepository;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    public void findUser(User user,Long chatId) {
        Optional<uz.ejavlon.currencycourse.entity.User> optionalUser = userRepository.findByTelegramId(user.getId());
        if (optionalUser.isEmpty()) {
            uz.ejavlon.currencycourse.entity.User newUser = uz.ejavlon.currencycourse.entity.User.builder()
                    .telegramId(user.getId())
                    .firstName(user.getFirstName())
                    .lastName(user.getLastName())
                    .username(user.getUserName())
                    .chatId(chatId)
                    .build();
            userRepository.save(newUser);
        }
    }

    public List<uz.ejavlon.currencycourse.entity.User> getAllUsers() {
        return userRepository.findAll();
    }

    @Cacheable(value = "user",key = "#id")
    public uz.ejavlon.currencycourse.entity.User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }
}

