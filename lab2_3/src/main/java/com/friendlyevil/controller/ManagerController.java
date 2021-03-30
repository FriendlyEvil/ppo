package com.friendlyevil.controller;

import com.friendlyevil.dto.ManagerStatistic;
import com.friendlyevil.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author friendlyevil
 */
@RestController
@RequestMapping("/manager")
public class ManagerController {
    private final AccountService accountService;

    @Autowired
    public ManagerController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/create")
    public void create(@RequestParam long id) {
        accountService.createAccount(id);
    }

    @PostMapping("/prolong/{id}")
    public void prolong(@PathVariable long id, @RequestParam int count) {
        accountService.prolong(id, count);
    }

    @GetMapping("/statistic/{id}")
    public ManagerStatistic statistic(@PathVariable long id) {
        if (!accountService.isExist(id)) {
            return new ManagerStatistic(false, 0, 0, 0);
        }

        return new ManagerStatistic(true, accountService.countEnter(id),
                accountService.countLeave(id), accountService.getVisitLeft(id));
    }
}
