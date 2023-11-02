package uz.ejavlon.currencycourse.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import java.time.LocalDateTime;
import java.time.ZoneId;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "_course")
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Course{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long uniqueId;

	@JsonProperty("id")
	private int id;

	@JsonProperty("CcyNm_EN")
	@Column(length = 25)
	private String ccyNmEN;

	@JsonProperty("CcyNm_UZC")
	@Column(length = 25)
	private String ccyNmUZC;

	@JsonProperty("Diff")
	private Double diff;

	@JsonProperty("Rate")
	private Double rate;

	@JsonProperty("Ccy")
	@Column(length = 3)
	private String ccy;

	@JsonProperty("CcyNm_RU")
	@Column(length = 25)
	private String ccyNmRU;

	@JsonProperty("CcyNm_UZ")
	@Column(length = 25)
	private String ccyNmUZ;

	@JsonProperty("Code")
	private Integer code;

	@JsonProperty("Nominal")
	private Integer nominal;

	@JsonProperty("Date")
	@Column(length = 10)
	private String date;

	LocalDateTime updatedDateTime = LocalDateTime.now(ZoneId.of("Asia/Tashkent"));
}