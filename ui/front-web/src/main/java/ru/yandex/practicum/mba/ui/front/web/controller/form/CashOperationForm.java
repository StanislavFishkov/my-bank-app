package ru.yandex.practicum.mba.ui.front.web.controller.form;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.yandex.practicum.mba.ui.front.web.dto.CashAction;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class CashOperationForm {
    @NotNull
    @Positive
    private Integer value;

    @NotNull
    private CashAction action;
}
