import graph.ListGraph;
import graph.MatrixGraph;
import main.AwtApplication;
import main.GlobalContext;
import main.JavaFxApplication;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        GlobalContext.readGraph = MatrixGraph.readGraphFromFile("src/main/resources/matrix_graph_1.txt");
//        GlobalContext.readGraph = ListGraph.readGraphFromFile("src/main/resources/list_graph_1.txt");
        new JavaFxApplication().run();
        new AwtApplication().run();
    }
}
