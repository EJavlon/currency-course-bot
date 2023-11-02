package uz.ejavlon.currencycourse.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import uz.ejavlon.currencycourse.dto.ResponseApi;
import uz.ejavlon.currencycourse.service.AdminService;
import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
public class AdminController {
    private final AdminService service;

    @PostMapping("/sendMessage/demo")
    public ResponseEntity<?> sendMessageDemo(MultipartHttpServletRequest request){
        ResponseApi responseApi = service.sendMessageToUserByChatId(request);
        return ResponseEntity.status(responseApi.getSuccess() ? OK : CONFLICT).body(responseApi);
    }

    @PostMapping("/sendMessage")
    public ResponseEntity<?> sendMessage(MultipartHttpServletRequest request){
        ResponseApi responseApi = service.sendMessage(request);
        return ResponseEntity.status(responseApi.getSuccess() ? OK : CONFLICT).body(responseApi);
    }

    @PostMapping("/sendPhoto")
    public ResponseEntity<?> sendPhoto(MultipartHttpServletRequest request){
        ResponseApi responseApi = service.sendPhoto(request);
        return ResponseEntity.status(responseApi.getSuccess() ? OK : CONFLICT).body(responseApi);
    }

    @PostMapping("/sendVideo")
    public ResponseEntity<?> sendVideo(MultipartHttpServletRequest request){
        ResponseApi responseApi = service.sendVideo(request);
        return ResponseEntity.status(responseApi.getSuccess() ? OK : CONFLICT).body(responseApi);
    }

    @PostMapping("/sendAudio")
    public ResponseEntity<?> sendAudio(MultipartHttpServletRequest request){
        ResponseApi responseApi = service.sendAudio(request);
        return ResponseEntity.status(responseApi.getSuccess() ? OK : CONFLICT).body(responseApi);
    }

    @PostMapping("/sendAnimation")
    public ResponseEntity<?> sendAnimation(MultipartHttpServletRequest request){
        ResponseApi responseApi = service.sendAnimation(request);
        return ResponseEntity.status(responseApi.getSuccess() ? OK : CONFLICT).body(responseApi);
    }

}
