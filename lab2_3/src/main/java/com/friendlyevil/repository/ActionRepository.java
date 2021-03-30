package com.friendlyevil.repository;

import java.util.List;

import com.friendlyevil.entity.Action;
import com.friendlyevil.entity.ActionType;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author friendlyevil
 */
public interface ActionRepository extends JpaRepository<Action, Long> {
    boolean existsActionByAccountIdAndActionType(long accountId, ActionType actionType);

    int countActionByAccountIdAndActionType(long accountId, ActionType actionType);

    List<Action> findByAccountIdAndActionType(long accountId, ActionType actionType);

    List<Action> findByTimeAfter(long time);

    void deleteAll();
}
