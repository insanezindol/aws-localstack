package com.example.awslocalstack.controller;

import com.example.awslocalstack.model.Event;
import com.example.awslocalstack.service.SnsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequestMapping("/sns")
@RestController
public class SnsController {

    private final SnsService snsService;

    public SnsController(SnsService snsService) {
        this.snsService = snsService;
    }

    @PostMapping("/publish")
    public ResponseEntity publish(@RequestBody Event event) {
        log.info("/sns/publish : {}", event);
        snsService.publish(event);
        return ResponseEntity.ok("OK");
    }

}
