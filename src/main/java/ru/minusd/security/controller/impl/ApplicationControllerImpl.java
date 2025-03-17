package ru.minusd.security.controller.impl;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.minusd.security.controller.ApplicationController;
import ru.minusd.security.service.ApplicationService;

@RestController
@RequestMapping("/application")
@RequiredArgsConstructor
@CrossOrigin("*")
@Tag(name = "Заявки на пропуск")
public class ApplicationControllerImpl implements ApplicationController {
    private final ApplicationService applicationService;


}
