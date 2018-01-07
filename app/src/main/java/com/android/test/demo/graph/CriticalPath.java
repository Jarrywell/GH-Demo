package com.android.test.demo.graph;

import android.util.Log;

import com.google.common.collect.Iterables;
import com.google.common.graph.ElementOrder;
import com.google.common.graph.MutableValueGraph;
import com.google.common.graph.Traverser;
import com.google.common.graph.ValueGraph;
import com.google.common.graph.ValueGraphBuilder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by tech on 18-1-6.
 */

public class CriticalPath {

    private static final String TAG = "CriticalPath";

    private static final String A = "A";
    private static final String B = "B";
    private static final String C = "C";
    private static final String D = "D";
    private static final String E = "E";
    private static final String F = "F";
    private static final String G = "G";
    private static final String H = "H";
    private static final String I = "I";
    private static final String J = "J";

    public static void testCirticalPath() {
        MutableValueGraph<String, Integer> graph = ValueGraphBuilder.directed()
                .nodeOrder(ElementOrder.insertion())
                .expectedNodeCount(10)
                .build();

        graph.putEdgeValue(A, B, 9);
        graph.putEdgeValue(A, C, 5);
        graph.putEdgeValue(A, D, 10);
        graph.putEdgeValue(B, E, 10);
        graph.putEdgeValue(C, F, 4);
        graph.putEdgeValue(D, F, 9);
        graph.putEdgeValue(E, G, 2);
        graph.putEdgeValue(F, G, 3);
        graph.putEdgeValue(F, H, 20);
        graph.putEdgeValue(F, I, 4);
        graph.putEdgeValue(G, J, 7);
        graph.putEdgeValue(I, H, 1);
        graph.putEdgeValue(H, J, 10);
        graph.putEdgeValue(I, J, 4);

        Log.i(TAG, "graph: " + graph);

        Iterable<String> traversers = Traverser.forGraph(graph).depthFirstPostOrder(A);
        Log.i(TAG, "dfs: " + format(traversers));

        List<String> reverseOrder = transform(traversers);
        List<String> positiveOrder = new ArrayList<>(reverseOrder);
        Collections.reverse(positiveOrder);
        Log.i(TAG, "positive: " + format(positiveOrder) + ", reverse: " + format(reverseOrder));

        Map<String, Integer> earliests = getEarliest(graph, positiveOrder);
        Set<String> nodes = earliests.keySet();
        for (String node : nodes) {
            Log.i(TAG, "node: " + node + " earliest: " + earliests.get(node));
        }

        Map<String, Integer> latests = getLatest(graph, reverseOrder, earliests);
        nodes = latests.keySet();
        for (String node : nodes) {
            Log.i(TAG, "node: " + node + " latest: " + latests.get(node));
        }
    }

    private static Map<String, Integer> getEarliest(ValueGraph<String, Integer> graph, List<String> nodes) {
        Map<String, Integer> earliests = new HashMap<>();
        for (String node : nodes) {
            earliests.put(node, 0);
            Set<String> predecessors = graph.predecessors(node);
            int maxValue = 0;
            for (String predecessor : predecessors) {
                maxValue = Math.max(earliests.get(predecessor) +
                        graph.edgeValueOrDefault(predecessor, node, 0), maxValue);
            }
            earliests.put(node, maxValue);
        }
        return earliests;
    }

    private static Map<String, Integer> getLatest(ValueGraph<String, Integer> graph,
                                                  List<String> reverseNodes, Map<String, Integer> earliests) {
        Map<String, Integer> latests = new HashMap<>();

        for (String node : reverseNodes) {
            Set<String> successors = graph.successors(node);
            int initValue = Integer.MAX_VALUE;
            if (successors == null || successors.size() <= 0) {
                initValue = earliests.get(node);
            }
            latests.put(node, initValue);
            int minValue = initValue;
            for (String successor : successors) {
                minValue = Math.min(latests.get(successor) -
                        graph.edgeValueOrDefault(node, successor, 0), initValue);
            }
            latests.put(node, minValue);
        }
        return latests;
    }

    private static <T> List<T> transform(Iterable<T> iterable) {
        List<T> result = new ArrayList<>();
        for (T value : iterable) {
            result.add(value);
        }
        return result;
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
