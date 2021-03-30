package com.friendlyevil.service;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.friendlyevil.dto.AvgReport;
import com.friendlyevil.dto.DayReports;
import com.friendlyevil.dto.Report;
import com.friendlyevil.entity.Action;
import com.friendlyevil.entity.ActionType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author friendlyevil
 */
@Service
public class ReportService {
    private static final long MILLIS_IN_DAY = Duration.ofDays(1).toMillis();

    private long lastUpdatedTime = 0;
    private final AccountService accountService;

    private Map<String, Report> dayStatistic = new HashMap<>();
    private Map<Long, Action> notLeave = new HashMap<>();
    private long sumTime = 0;
    private long countTime = 0;
    private long enterCount = 0;

    @Autowired
    public ReportService(AccountService accountService) {
        this.accountService = accountService;
    }


    public void update() {
        List<Action> deltas = accountService.findAfter(lastUpdatedTime);
        lastUpdatedTime = deltas.stream().mapToLong(Action::getTime).max().orElseGet(() -> lastUpdatedTime);

        for (Action delta : deltas) {
            if (delta.getActionType() == ActionType.ENTER) {
                notLeave.put(delta.getAccountId(), delta);
                enterCount++;
            } else if (delta.getActionType() == ActionType.LEAVE) {
                Action action = notLeave.remove(delta.getAccountId());
                long additionalTime = delta.getTime() - action.getTime();
                this.sumTime += additionalTime;
                countTime++;

                String day = toDay(delta.getTime());
                dayStatistic.put(day, dayStatistic.getOrDefault(day, new Report(0, 0))
                        .addTime(additionalTime));
            }
        }

    }

    private String toDay(long time) {
        return String.valueOf(time / MILLIS_IN_DAY);
    }

    public AvgReport getAvgReport() {
        return new AvgReport(countTime != 0 ? 1.0 * sumTime / countTime : 0,
                dayStatistic.size() != 0 ? 1.0 * enterCount / dayStatistic.size() : 0);
    }

    public DayReports getDayReports() {
        return new DayReports(dayStatistic);
    }

    public void clear() {
        lastUpdatedTime = 0;
        dayStatistic = new HashMap<>();
        notLeave = new HashMap<>();
        sumTime = 0;
        countTime = 0;
        enterCount = 0;
    }
}
