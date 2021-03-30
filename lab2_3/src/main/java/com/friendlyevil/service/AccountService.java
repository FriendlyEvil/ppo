package com.friendlyevil.service;

import java.util.List;

import com.friendlyevil.entity.Action;
import com.friendlyevil.entity.ActionType;
import com.friendlyevil.repository.ActionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author friendlyevil
 */
@Service
public class AccountService {
    private final ActionRepository actionRepository;

    @Autowired
    public AccountService(ActionRepository actionRepository) {
        this.actionRepository = actionRepository;
    }

    public long createAccount(long id) {
        return actionRepository.save(new Action(id, ActionType.REGISTER)).getId();
    }

    public void prolong(long id, int count) {
        actionRepository.save(new Action(id, ActionType.PROLONG, count));
    }

    public void enter(long id) {
        actionRepository.save(new Action(id, ActionType.ENTER));
    }

    public void leave(long id) {
        actionRepository.save(new Action(id, ActionType.LEAVE));
    }

    public boolean isExist(long id) {
        return actionRepository.existsActionByAccountIdAndActionType(id, ActionType.REGISTER);
    }

    public int countEnter(long id) {
        return actionRepository.countActionByAccountIdAndActionType(id, ActionType.ENTER);
    }

    public int countLeave(long id) {
        return actionRepository.countActionByAccountIdAndActionType(id, ActionType.LEAVE);
    }

    public boolean isEnter(long id) {
        return countEnter(id) != countLeave(id);
    }

    public List<Action> findAfter(long time) {
        return actionRepository.findByTimeAfter(time);
    }

    public int getVisitLeft(long id) {
        int visitCount = actionRepository.findByAccountIdAndActionType(id, ActionType.PROLONG)
                .stream().mapToInt(Action::getData).sum();

        return visitCount - countEnter(id);
    }

    public void clear() {
        actionRepository.deleteAll();
    }
}