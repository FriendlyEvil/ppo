package ru.friendlyevil.profiler.domain;

public class Statistic {
    private final String packageName;
    private final String methodName;
    private long numberOfCalls;
    private long totalTimeNs;
    private long averageTimeNs;

    public Statistic(String packageName, String methodName) {
        this.packageName = packageName;
        this.methodName = methodName;
    }

    public void addCalls(long timeNs) {
        numberOfCalls += 1;
        totalTimeNs += timeNs;
        averageTimeNs = totalTimeNs / numberOfCalls;
    }

    public String getPackageName() {
        return packageName;
    }

    public String getMethodName() {
        return methodName;
    }

    public long getNumberOfCalls() {
        return numberOfCalls;
    }

    public long getTotalTimeNs() {
        return totalTimeNs;
    }

    public long getAverageTimeNs() {
        return averageTimeNs;
    }

    @Override
    public String toString() {
        return methodName +
                ": calls=" + numberOfCalls +
                ", totalTimeNs=" + totalTimeNs +
                ", averageTimeNs=" + averageTimeNs;
    }
}
