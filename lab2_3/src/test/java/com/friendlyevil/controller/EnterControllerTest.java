package com.friendlyevil.controller;

import com.friendlyevil.dto.ManagerStatistic;
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
public class EnterControllerTest {
    @Autowired
    private AccountService accountService;
    @Autowired
    private ManagerController managerController;
    @Autowired
    private EnterController enterController;

    @Before
    public void before() {
        accountService.clear();
        managerController.create(1);
    }

    @Test
    public void emptyTest() {
        Assert.assertFalse(enterController.enter(1));
        Assert.assertFalse(enterController.leave(1));
    }

    @Test
    public void simpleTest() {
        managerController.prolong(1, 1);
        Assert.assertTrue(enterController.enter(1));
        Assert.assertFalse(enterController.enter(1));
        Assert.assertTrue(enterController.leave(1));
        Assert.assertFalse(enterController.leave(1));
    }

    @Test
    public void test() {
        managerController.prolong(1, 10);
        Assert.assertTrue(enterController.enter(1));
        Assert.assertFalse(enterController.enter(1));
        Assert.assertTrue(enterController.leave(1));
        Assert.assertFalse(enterController.leave(1));

        Assert.assertTrue(enterController.enter(1));
        Assert.assertFalse(enterController.enter(1));
        Assert.assertTrue(enterController.leave(1));
        Assert.assertFalse(enterController.leave(1));

        ManagerStatistic statistic = managerController.statistic(1);
        Assert.assertTrue(statistic.isExist());
        Assert.assertEquals(statistic.getEnterCount(), 2);
        Assert.assertEquals(statistic.getLeaveCount(), 2);
        Assert.assertEquals(statistic.getVisitLeft(), 8);
    }
}