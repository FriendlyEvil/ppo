package com.friendlyevil.dto;

import java.util.Map;

import lombok.Value;

/**
 * @author friendlyevil
 */
@Value
public class DayReports {
    Map<String, Report> reports;
}
