package uz.ejavlon.currencycourse.service;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.telegram.telegrambots.meta.api.methods.send.*;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import uz.ejavlon.currencycourse.CurrencyCourseBot;
import uz.ejavlon.currencycourse.dto.ResponseApi;
import uz.ejavlon.currencycourse.entity.User;
import uz.ejavlon.currencycourse.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final UserRepository userRepository;
    private final CurrencyCourseBot bot;

    public ResponseApi sendMessage(MultipartHttpServletRequest request){
        String message = request.getParameter("message");
        if (message == null || message.isEmpty())
            return ResponseApi.builder()
                    .message("Maydonlar to'ldirilmagan")
                    .success(false)
                    .build();

        for (User user : userRepository.findAll()) {
            if (user.getChatId() == null) continue;
            SendMessage sendMessage = new SendMessage();
            sendMessage.setText(message);
            sendMessage.setParseMode("HTML");
            sendMessage.setChatId(user.getChatId());
            bot.sendMessage(sendMessage);
        }
        return ResponseApi.builder()
                .message("Xabar(SendMessage) barcha foydalanuchilarga yuborildi")
                .success(true)
                .build();
    }

    public ResponseApi sendMessageToUserByChatId(MultipartHttpServletRequest request){
        String message = request.getParameter("message");
        String chatId = request.getParameter("chatId");

        if (message == null || message.isEmpty() || chatId == null || chatId.isEmpty())
            return ResponseApi.builder()
                    .message("Maydonlar to'ldirilmagan")
                    .success(false)
                    .build();

        SendMessage sendMessage = new SendMessage();
        sendMessage.setText(message);
        sendMessage.setParseMode("HTML");
        sendMessage.setChatId(chatId);
        bot.sendMessage(sendMessage);

        return ResponseApi.builder()
                .message("Xabar(SendMessage) yuborildi")
                .success(true)
                .build();
    }

    @SneakyThrows
    public ResponseApi sendPhoto(MultipartHttpServletRequest request){
        String message = request.getParameter("message");
        MultipartFile file = request.getFile("file");

        if (message == null || message.isEmpty() || file == null)
            return ResponseApi.builder()
                    .message("Maydonlar to'ldirilmagan")
                    .success(false)
                    .build();

        for (User user : userRepository.findAll()) {
            if (user.getChatId() == null) continue;
            SendPhoto sendPhoto = new SendPhoto();
            sendPhoto.setCaption(message);
            sendPhoto.setParseMode("HTML");
            sendPhoto.setPhoto(new InputFile(file.getInputStream(), file.getOriginalFilename()));
            sendPhoto.setChatId(user.getChatId());
            bot.sendPhoto(sendPhoto);
        }
        return ResponseApi.builder()
                .message("Xabar(SendPhoto) barcha foydalanuchilarga yuborildi")
                .success(true)
                .build();
    }

    @SneakyThrows
    public ResponseApi sendVideo(MultipartHttpServletRequest request){
        String message = request.getParameter("message");
        MultipartFile file = request.getFile("file");

        if (message == null || message.isEmpty() || file == null)
            return ResponseApi.builder()
                    .message("Maydonlar to'ldirilmagan")
                    .success(false)
                    .build();

        for (User user : userRepository.findAll()) {
            if (user.getChatId() == null) continue;
            SendVideo sendVideo = new SendVideo();
            sendVideo.setCaption(message);
            sendVideo.setParseMode("HTML");
            sendVideo.setVideo(new InputFile(file.getInputStream(), file.getOriginalFilename()));
            sendVideo.setChatId(user.getChatId());
            bot.sendVideo(sendVideo);
        }
        return ResponseApi.builder()
                .message("Xabar(SendVideo) barcha foydalanuchilarga yuborildi")
                .success(true)
                .build();
    }

    @SneakyThrows
    public ResponseApi sendAudio(MultipartHttpServletRequest request){
        String message = request.getParameter("message");
        MultipartFile file = request.getFile("file");

        if (message == null || message.isEmpty() ||  file == null)
            return ResponseApi.builder()
                    .message("Maydonlar to'ldirilmagan")
                    .success(false)
                    .build();


        for (User user : userRepository.findAll()) {
            if (user.getChatId() == null) continue;
            SendAudio sendAudio = new SendAudio();
            sendAudio.setCaption(message);
            sendAudio.setParseMode("HTML");
            sendAudio.setAudio(new InputFile(file.getInputStream(), file.getOriginalFilename()));
            sendAudio.setChatId(user.getChatId());
            bot.sendAudio(sendAudio);
        }
        return ResponseApi.builder()
                .message("Xabar(SendAudio) barcha foydalanuchilarga yuborildi")
                .success(true)
                .build();
    }

    @SneakyThrows
    public ResponseApi sendAnimation(MultipartHttpServletRequest request){
        String message = request.getParameter("message");
        MultipartFile file = request.getFile("file");

        if (message == null || message.isEmpty() || file == null)
            return ResponseApi.builder()
                    .message("Maydonlar to'ldirilmagan")
                    .success(false)
                    .build();

        for (User user : userRepository.findAll()) {
            if (user.getChatId() == null) continue;
            SendAnimation sendAnimation = new SendAnimation();
            sendAnimation.setCaption(message);
            sendAnimation.setParseMode("HTML");
            sendAnimation.setAnimation(new InputFile(file.getInputStream(), file.getOriginalFilename()));
            sendAnimation.setChatId(user.getChatId());
            bot.sendAnimation(sendAnimation);
        }
        return ResponseApi.builder()
                .message("Xabar(SendAnimation) barcha foydalanuchilarga yuborildi")
                .success(true)
                .build();
    }
}