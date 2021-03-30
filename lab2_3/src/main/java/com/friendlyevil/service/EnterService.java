package com.friendlyevil.service;

import org.springframework.stereotype.Service;

/**
 * @author friendlyevil
 */
@Service
public class EnterService {
    private final AccountService accountService;
    private final ReportService reportService;

    public EnterService(AccountService accountService, ReportService reportService) {
        this.accountService = accountService;
        this.reportService = reportService;
    }

    public boolean enter(long id) {
        if (!accountService.isExist(id) || accountService.isEnter(id) || accountService.getVisitLeft(id) <= 0) {
            return false;
        }

        accountService.enter(id);
        reportService.update();

        return true;
    }

    public boolean leave(long id) {
        if (!accountService.isEnter(id) || !accountService.isEnter(id)) {
            return false;
        }

        accountService.leave(id);
        reportService.update();

        return true;
    }
}
