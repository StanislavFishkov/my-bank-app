package ru.yandex.practicum.mba.ui.front.web.controller;

import jakarta.annotation.Nullable;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.yandex.practicum.mba.ui.front.web.controller.form.AccountForm;
import ru.yandex.practicum.mba.ui.front.web.controller.form.CashOperationForm;
import ru.yandex.practicum.mba.ui.front.web.controller.form.TransferOperationForm;
import ru.yandex.practicum.mba.ui.front.web.dto.AccountDto;
import ru.yandex.practicum.mba.ui.front.web.dto.CashAction;
import ru.yandex.practicum.mba.ui.front.web.service.InternalGatewayService;

/**
 * Контроллер main.html.
 *
 * Используемая модель для main.html:
 *      model.addAttribute("name", name);
 *      model.addAttribute("birthdate", birthdate.format(DateTimeFormatter.ISO_DATE));
 *      model.addAttribute("sum", sum);
 *      model.addAttribute("accounts", accounts);
 *      model.addAttribute("errors", errors);
 *      model.addAttribute("info", info);
 *
 * Поля модели:
 *      name - Фамилия Имя текущего пользователя, String (обязательное)
 *      birthdate - дата рождения текущего пользователя, String в формате 'YYYY-MM-DD' (обязательное)
 *      sum - сумма на счету текущего пользователя, Integer (обязательное)
 *      accounts - список аккаунтов, которым можно перевести деньги, List<AccountDto> (обязательное)
 *      errors - список ошибок после выполнения действий, List<String> (не обязательное)
 *      info - строка успешности после выполнения действия, String (не обязательное)
 *
 * С примерами использования можно ознакомиться в тестовом классе заглушке AccountStub
 */
@Controller
@RequiredArgsConstructor
public class MainController {
    private final InternalGatewayService internalGatewayService;

    /**
     * GET /.
     * Редирект на GET /account
     */
    @GetMapping
    public String index() {
        return "redirect:/account";
    }

    /**
     * GET /account.
     * Что нужно сделать:
     * 1. Сходить в сервис accounts через Gateway API для получения данных аккаунта по REST
     * 2. Заполнить модель main.html полученными из ответа данными
     * 3. Текущего пользователя можно получить из контекста Security
     */
    @GetMapping("/account")
    public String getAccount(Model model) {
        AccountDto account = internalGatewayService.getAccount();
        AccountForm accountForm = new AccountForm(account.getName(), account.getBirthdate());

        model.addAttribute("accountForm", accountForm);

        fillPageData(model, account);
        initForms(model);

        return "main";
    }

    /**
     * POST /account.
     * Что нужно сделать:
     * 1. Сходить в сервис accounts через Gateway API для изменения данных текущего пользователя по REST
     * 2. Заполнить модель main.html полученными из ответа данными
     * 3. Текущего пользователя можно получить из контекста Security
     *
     * Изменяемые данные:
     * 1. name - Фамилия Имя
     * 2. birthdate - дата рождения в формате YYYY-DD-MM
     */
    @PostMapping("/account")
    public String editAccount(Model model,
                              @Valid @ModelAttribute AccountForm accountForm,
                              BindingResult bindingResult,
                              RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            fillPageData(model, null);
            initForms(model);
            return "main";
        }

        internalGatewayService.setNameAndBirthdate(accountForm.getName(), accountForm.getBirthdate());
        redirectAttributes.addFlashAttribute("info", "Данные пользователя успешно изменены");

        return "redirect:/account";
    }

    /**
     * POST /cash.
     * Что нужно сделать:
     * 1. Сходить в сервис cash через Gateway API для снятия/пополнения счета текущего аккаунта по REST
     * 2. Заполнить модель main.html полученными из ответа данными
     * 3. Текущего пользователя можно получить из контекста Security
     *
     * Параметры:
     * 1. value - сумма списания
     * 2. action - GET (снять), PUT (пополнить)
     */
    @PostMapping("/cash")
    public String editCash(Model model,
                           @Valid @ModelAttribute CashOperationForm cashOperationForm,
                           BindingResult bindingResult,
                           RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            fillPageData(model, null);
            initForms(model);
            return "main";
        }

        CashAction action = cashOperationForm.getAction();
        Integer amount = cashOperationForm.getValue();

        internalGatewayService.processCash(action, amount);
        redirectAttributes.addFlashAttribute("info",
                action == CashAction.GET ? "Снято %d руб".formatted(amount) : "Положено %d руб".formatted(amount));

        return "redirect:/account";
    }

    /**
     * POST /transfer.
     * Что нужно сделать:
     * 1. Сходить в сервис accounts через Gateway API для перевода со счета текущего аккаунта на счет другого аккаунта по REST
     * 2. Заполнить модель main.html полученными из ответа данными
     * 3. Текущего пользователя можно получить из контекста Security
     *
     * Параметры:
     * 1. value - сумма списания
     * 2. login - логин пользователя получателя
     */
    @PostMapping("/transfer")
    public String transfer(Model model,
                           @Valid @ModelAttribute TransferOperationForm transferOperationForm,
                           BindingResult bindingResult,
                           RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            fillPageData(model, null);
            initForms(model);
            return "main";
        }

        String login = transferOperationForm.getLogin();
        Integer amount = transferOperationForm.getValue();

        internalGatewayService.transferMoney(login, amount);
        redirectAttributes.addFlashAttribute("info",
                "Успешно переведено %d руб клиенту %s".formatted(amount, login));

        return "redirect:/account";
    }

    private void fillPageData(Model model, @Nullable AccountDto account) {
        if (account == null) {
            account = internalGatewayService.getAccount();
        }
        model.addAttribute("sum", account.getBalance());

        model.addAttribute("accounts", internalGatewayService.getTransferRecipients());
    }

    private void initForms(Model model) {

        if (!model.containsAttribute("accountForm")) {
            model.addAttribute("accountForm", new AccountForm());
        }

        if (!model.containsAttribute("cashOperationForm")) {
            model.addAttribute("cashOperationForm", new CashOperationForm());
        }

        if (!model.containsAttribute("transferOperationForm")) {
            model.addAttribute("transferOperationForm", new TransferOperationForm());
        }
    }
}
