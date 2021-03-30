package com.friendlyevil.controller;

import com.friendlyevil.service.EnterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author friendlyevil
 */
@RestController
@RequestMapping
public class EnterController {
    private final EnterService enterService;

    @Autowired
    public EnterController(EnterService enterService) {
        this.enterService = enterService;
    }

    @PostMapping("enter/{id}")
    public boolean enter(@PathVariable long id) {
        return enterService.enter(id);
    }

    @PostMapping("leave/{id}")
    public boolean leave(@PathVariable long id) {
        return enterService.leave(id);
    }
}
