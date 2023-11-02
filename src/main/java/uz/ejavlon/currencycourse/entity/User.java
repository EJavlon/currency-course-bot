package uz.ejavlon.currencycourse.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Builder
@Table(name = "_users")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "telegram_id", nullable = false,unique = true)
    Long telegramId;

    @Column(name = "first_name",length = 50)
    String firstName;

    @Column(name = "last_name",length = 50)
    String lastName;

    @Column(name = "username",length = 50,unique = true)
    String username;

    @Column(name = "chat_id")
    Long chatId;
}
