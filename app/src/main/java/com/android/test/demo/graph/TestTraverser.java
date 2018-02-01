package com.android.test.demo.graph;

import android.util.Log;

import com.google.common.graph.ElementOrder;
import com.google.common.graph.Graph;
import com.google.common.graph.GraphBuilder;
import com.google.common.graph.MutableGraph;
import com.google.common.graph.MutableValueGraph;
import com.google.common.graph.Traverser;

import java.util.HashMap;
import java.util.Map;

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

    private static final String V1 = "V1";
    private static final String V2 = "V2";
    private static final String V3 = "V3";
    private static final String V4 = "V4";
    private static final String V5 = "V5";
    private static final String V6 = "V6";
    private static final String V7 = "V7";
    private static final String V8 = "V8";
    private static final String V9 = "V9";


    public static void test() {

        testGraph(buildGraph1(), A);

        testTree(buildGraph2(), A);

        testGraph(buildGraph3(), V1);

        testGraph(buildGraph4(), V1);

        Map<String, Integer> testMap = new HashMap<>();
        testMap.put(V1, 0);
        testMap.put(V2, 0);
        testMap.put(V3, 0);
        Log.i(TAG, "test Map KeySet() order: " + format(testMap.keySet()));
    }

    private static void testGraph(Graph<String> graph, String starNode) {

        Log.i(TAG,"init graph: " + graph);

        Log.i(TAG, starNode + " successor: " + format(graph.successors(starNode)));

        Iterable<String> bfs = Traverser.forGraph(graph).breadthFirst(starNode);
        Log.i(TAG,"bfs graph: " + format(bfs));

        Iterable<String> dfsPre = Traverser.forGraph(graph).depthFirstPreOrder(starNode);
        Log.i(TAG,"dfsPre graph: " + format(dfsPre));

        Iterable<String> dfsPost = Traverser.forGraph(graph).depthFirstPostOrder(starNode);
        Log.i(TAG,"dfsPost graph: " + format(dfsPost));
    }

    /**
     * 这里必须是有向五环的图
     */
    private static void testTree(Graph<String> graph, String starNode) {

        Iterable<String> bfs = Traverser.forTree(graph).breadthFirst(starNode);
        Log.i(TAG,"bfs tree: " + format(bfs));

        Iterable<String> dfsPre = Traverser.forTree(graph).depthFirstPreOrder(starNode);
        Log.i(TAG,"dfsPre tree: " + format(dfsPre));

        Iterable<String> dfsPost = Traverser.forTree(graph).depthFirstPostOrder(starNode);
        Log.i(TAG,"dfsPost tree: " + format(dfsPost));
    }

    private static Graph<String> buildGraph1() {
        MutableGraph<String> graph = GraphBuilder.undirected()
            .nodeOrder(ElementOrder.<String>natural()).build();
        graph.putEdge(A, B);
        graph.putEdge(A, C);
        graph.putEdge(A, D);
        graph.putEdge(B, E);
        graph.putEdge(C, E);
        graph.putEdge(C, F);
        return graph;
    }

    private static Graph<String> buildGraph2() {
        MutableGraph<String> graph = GraphBuilder.directed()
            .nodeOrder(ElementOrder.<String>natural()).build();
        graph.putEdge(A, D);
        graph.putEdge(A, E);
        graph.putEdge(B, E);
        graph.putEdge(B, F);
        graph.putEdge(E, H);
        graph.putEdge(C, G);
        return graph;
    }

    private static Graph<String> buildGraph3() {
        MutableGraph<String> graph = GraphBuilder.undirected()
            .nodeOrder(ElementOrder.<String>natural()).build();

        graph.putEdge(V1, V2);
        graph.putEdge(V1, V3);
        graph.putEdge(V2, V4);
        graph.putEdge(V2, V5);
        graph.putEdge(V3, V6);
        graph.putEdge(V3, V7);
        graph.putEdge(V4, V8);
        graph.putEdge(V5, V8);
        graph.putEdge(V6, V7);

        return graph;
    }

    private static Graph<String> buildGraph4() {
        MutableGraph<String> graph = GraphBuilder.undirected()
            .nodeOrder(ElementOrder.<String>natural()).build();
        graph.putEdge(V1, V2);
        graph.putEdge(V1, V3);
        graph.putEdge(V2, V4);
        graph.putEdge(V2, V5);
        graph.putEdge(V3, V6);
        graph.putEdge(V3, V7);
        graph.putEdge(V4, V8);
        graph.putEdge(V5, V8);
        graph.putEdge(V6, V8);
        graph.putEdge(V7, V8);

        return graph;
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
