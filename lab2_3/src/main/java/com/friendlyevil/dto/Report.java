package com.friendlyevil.dto;

import lombok.Value;

/**
 * @author friendlyevil
 */
@Value
public class Report {
    long time;
    long count;

    public Report addTime(long time) {
        return new Report(this.time + time, count + 1);
    }
}
