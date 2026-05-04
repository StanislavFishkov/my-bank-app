package ru.yandex.practicum.mba.ui.front.web.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class UpdateRequestAccountDto {
    private String name;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthdate;
}