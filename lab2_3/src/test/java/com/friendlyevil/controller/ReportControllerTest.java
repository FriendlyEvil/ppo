package com.friendlyevil.controller;

import com.friendlyevil.dto.AvgReport;
import com.friendlyevil.dto.DayReports;
import com.friendlyevil.service.AccountService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author friendlyevil
 */
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class ReportControllerTest {
    @Autowired
    private AccountService accountService;
    @Autowired
    private ManagerController managerController;
    @Autowired
    private EnterController enterController;
    @Autowired
    private ReportController reportController;

    private static final double EPS = 1e-12;

    @Before
    public void before() {
        accountService.clear();
        reportController.clear();
        managerController.create(1);
        managerController.prolong(1, 10);
    }

    @Test
    public void emptyTest() {
        AvgReport avgReport = reportController.getAvgReport();
        Assert.assertEquals(avgReport.getAvgEnter(), 0, EPS);
        Assert.assertEquals(avgReport.getAvgTime(), 0, EPS);

        DayReports dayReport = reportController.getDayReport();
        Assert.assertTrue(dayReport.getReports().isEmpty());
    }

    @Test
    public void simpleTest() {
        enterController.enter(1);
        enterController.leave(1);
        AvgReport avgReport = reportController.getAvgReport();
        Assert.assertEquals(avgReport.getAvgEnter(), 1, EPS);
        Assert.assertTrue(avgReport.getAvgTime() > 0);

        DayReports dayReport = reportController.getDayReport();
        Assert.assertEquals(dayReport.getReports().size(), 1);
    }

}