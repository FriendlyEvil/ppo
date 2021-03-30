package com.friendlyevil.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author friendlyevil
 */
@Entity
@Data
@NoArgsConstructor
public class Action {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    long accountId;
    int data;
    ActionType actionType;
    long time = System.currentTimeMillis();

    public Action(long accountId, ActionType actionType) {
        this.accountId = accountId;
        this.actionType = actionType;
    }

    public Action(long accountId, ActionType actionType, int data) {
        this.accountId = accountId;
        this.actionType = actionType;
        this.data = data;
    }
}
