package ru.friendlyevil.profiler.service;

import org.springframework.stereotype.Service;
import ru.friendlyevil.profiler.domain.Node;
import ru.friendlyevil.profiler.domain.Statistic;

import java.io.PrintWriter;
import java.util.*;

@Service
public class VisualizeStatisticService {

    public void visualizeTree(Collection<Statistic> statistics) {
        Node root = new Node("");
        statistics.forEach(statistic -> root.addNode(statistic.getPackageName().split("\\."), statistic));
        root.getChildren().values().forEach(Node::compressPath);
        PrintWriter printWriter = new PrintWriter(System.out);
        root.getChildren().values().forEach(node -> node.print(printWriter));
        printWriter.flush();
    }

}
