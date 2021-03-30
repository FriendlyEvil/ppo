package com.friendlyevil.dto;

import lombok.Value;

/**
 * @author friendlyevil
 */
@Value
public class ManagerStatistic {
    boolean exist;
    int enterCount;
    int leaveCount;
    int visitLeft;
}
