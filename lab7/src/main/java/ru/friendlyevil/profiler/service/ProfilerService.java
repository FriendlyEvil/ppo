package ru.friendlyevil.profiler.service;

import org.aspectj.lang.Signature;
import org.springframework.stereotype.Service;
import ru.friendlyevil.profiler.domain.Statistic;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Service
public class ProfilerService {
    Map<String, Statistic> methodCalls = new HashMap<>();

    public void registerCall(Signature method, long timeNs) {
        Statistic statistic = methodCalls.get(method.toString());
        if (statistic == null) {
            statistic = new Statistic(method.getDeclaringTypeName(), method.getName());
            methodCalls.put(method.toString(), statistic);
        }
        statistic.addCalls(timeNs);
    }

    public Collection<Statistic> getStatistic() {
        return methodCalls.values();
    }
}
