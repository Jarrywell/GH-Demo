package com.android.test.demo.graph;

import android.util.Log;

import com.google.common.graph.ElementOrder;
import com.google.common.graph.GraphBuilder;
import com.google.common.graph.MutableGraph;
import com.google.common.graph.MutableValueGraph;
import com.google.common.graph.Traverser;

/**
 * Created by tech on 18-1-30.
 */

public class TestTraverser {
    private static final String TAG = "TestTraverser";

    private static final String A = "A";
    private static final String B = "B";
    private static final String C = "C";
    private static final String D = "D";
    private static final String E = "E";
    private static final String F = "F";
    private static final String G = "G";
    private static final String H = "H";
    public static void test() {

        testGraph();

        testTree();
    }

    private static void testGraph() {
        MutableGraph<String> graph = GraphBuilder.undirected()
                .nodeOrder(ElementOrder.<String>natural()).build();
        graph.putEdge(A, B);
        graph.putEdge(A, C);
        graph.putEdge(A, D);
        graph.putEdge(B, E);
        graph.putEdge(C, E);
        graph.putEdge(C, F);

        Log.i(TAG,"init graph: " + graph);

        Log.i(TAG,"A  successor: " + format(graph.successors(A)));

        Iterable<String> bfs = Traverser.forGraph(graph).breadthFirst(A);
        Log.i(TAG,"bfs graph: " + format(bfs));

        Iterable<String> dfsPre = Traverser.forGraph(graph).depthFirstPreOrder(A);
        Log.i(TAG,"dfsPre graph: " + format(dfsPre));

        Iterable<String> dfsPost = Traverser.forGraph(graph).depthFirstPostOrder(A);
        Log.i(TAG,"dfsPost graph: " + format(dfsPost));
    }

    /**
     * 这里必须是有向五环的图
     */
    private static void testTree() {
        MutableGraph<String> graph = GraphBuilder.directed()
                .nodeOrder(ElementOrder.<String>natural()).build();
        graph.putEdge(A, D);
        graph.putEdge(A, E);
        graph.putEdge(B, E);
        graph.putEdge(B, F);
        graph.putEdge(E, H);
        graph.putEdge(C, G);

        Iterable<String> bfs = Traverser.forTree(graph).breadthFirst(A);
        Log.i(TAG,"bfs tree: " + format(bfs));

        Iterable<String> dfsPre = Traverser.forTree(graph).depthFirstPreOrder(A);
        Log.i(TAG,"dfsPre tree: " + format(dfsPre));

        Iterable<String> dfsPost = Traverser.forTree(graph).depthFirstPostOrder(A);
        Log.i(TAG,"dfsPost tree: " + format(dfsPost));
    }

    private static <T> String format(Iterable<T> iterable) {
        StringBuilder builder = new StringBuilder();
        builder.append("{");
        for (T obj : iterable) {
            builder.append(obj).append(",");
        }
        if (builder.length()  > 1) {
            builder.deleteCharAt(builder.length() - 1);
        }
        builder.append("}");
        return builder.toString();
    }
}
