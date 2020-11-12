package ru.friendlyevil.profiler.domain;

import org.apache.commons.lang3.StringUtils;

import java.io.PrintWriter;
import java.util.*;

public class Node {
    private String path;
    private Map<String, Node> children = new HashMap<>();
    private List<Statistic> methodStatistic = new ArrayList<>();

    public Node(String path) {
        this.path = path;
    }

    public void addNode(String[] path, Statistic statistic) {
        addNode(path, 0, statistic);
    }

    private void addNode(String[] path, int cur, Statistic statistic) {
        if (cur == path.length) {
            methodStatistic.add(statistic);
            return;
        }
        String tmpPath = path[cur];
        Node node = children.get(tmpPath);
        if (node == null) {
            node = new Node(tmpPath);
            children.put(tmpPath, node);
        }
        node.addNode(path, cur + 1, statistic);
    }

    public void compressPath() {
        if (methodStatistic.size() == 0 && children.size() == 1) {
            Node node = children.values().stream().findFirst().get();
            children = node.getChildren();
            methodStatistic = node.getMethodStatistic();
            path = path + '.' + node.getPath();
            compressPath();
        }
        children.values().forEach(Node::compressPath);
    }

    public void print(PrintWriter printWriter) {
        print(printWriter, "");
    }

    private void print(PrintWriter printWriter, String prefix) {
        printWriter.print(prefix);
        printWriter.println(path);
        prefix += StringUtils.repeat(" ", path.length());

        for (Statistic statistic : methodStatistic) {
            printWriter.print(prefix);
            printWriter.println(statistic);
        }
        String finalPrefix = prefix;
        children.forEach((s, node) -> node.print(printWriter, finalPrefix));
    }

    private String getPath() {
        return path;
    }

    public Map<String, Node> getChildren() {
        return children;
    }

    public List<Statistic> getMethodStatistic() {
        return methodStatistic;
    }
}