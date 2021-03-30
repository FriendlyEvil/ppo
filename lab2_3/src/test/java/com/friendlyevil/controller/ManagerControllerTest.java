package com.friendlyevil.controller;

import com.friendlyevil.dto.ManagerStatistic;
import com.friendlyevil.service.AccountService;
import org.junit.After;
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
public class ManagerControllerTest {
    @Autowired
    private AccountService accountService;
    @Autowired
    private ManagerController managerController;

    @Before
    public void before() {
        accountService.clear();
    }

    @Test
    public void emptyTest() {
        ManagerStatistic statistic = managerController.statistic(1);
        Assert.assertFalse(statistic.isExist());
        Assert.assertEquals(statistic.getEnterCount(), 0);
        Assert.assertEquals(statistic.getLeaveCount(), 0);
        Assert.assertEquals(statistic.getVisitLeft(), 0);
    }

    @Test
    public void addUserTest() {
        managerController.create(1);
        ManagerStatistic statistic = managerController.statistic(1);
        Assert.assertTrue(statistic.isExist());
        Assert.assertEquals(statistic.getEnterCount(), 0);
        Assert.assertEquals(statistic.getLeaveCount(), 0);
        Assert.assertEquals(statistic.getVisitLeft(), 0);
    }

    @Test
    public void addUserWithVisitTest() {
        managerController.create(1);
        managerController.prolong(1, 10);
        ManagerStatistic statistic = managerController.statistic(1);
        Assert.assertTrue(statistic.isExist());
        Assert.assertEquals(statistic.getEnterCount(), 0);
        Assert.assertEquals(statistic.getLeaveCount(), 0);
        Assert.assertEquals(statistic.getVisitLeft(), 10);
    }

}