package com.android.test.demo.graph;

import android.util.ArrayMap;
import android.util.Log;

import com.google.common.graph.ElementOrder;
import com.google.common.graph.MutableValueGraph;
import com.google.common.graph.ValueGraphBuilder;

import java.util.Map;
import java.util.Set;

/**
 * Created by tech on 18-1-11.
 */

public class Dijkstra {
    private final static String TAG = "Dijkstra";

    private static final String V0 = "v0";
    private static final String V1 = "v1";
    private static final String V2 = "v2";
    private static final String V3 = "v3";
    private static final String V4 = "v4";
    private static final String V5 = "v5";


    public static void test() {
        MutableValueGraph<String, Integer> graph = ValueGraphBuilder.directed()
                .nodeOrder(ElementOrder.insertion())
                .expectedNodeCount(10)
                .build();
        graph.putEdgeValue(V0, V2, 10);
        graph.putEdgeValue(V0, V4, 30);
        graph.putEdgeValue(V0, V5, 100);
        graph.putEdgeValue(V1, V2, 5);
        graph.putEdgeValue(V2, V3, 50);
        graph.putEdgeValue(V3, V5, 10);
        graph.putEdgeValue(V4, V3, 20);
        graph.putEdgeValue(V4, V5, 60);

        Log.i(TAG, "graph: " + graph);

        testShortestPath(graph, V0);

    }

    private static void testShortestPath(MutableValueGraph<String, Integer> graph, String startNode) {
        Set<String> nodes = graph.nodes();
        Map<String, Integer> shortestPath = new ArrayMap<>(nodes.size()); //D
        Map<String, Boolean> visited = new ArrayMap<>(nodes.size()); //V & S
        for (String node : nodes) {
            shortestPath.put(node, graph.edgeValueOrDefault(startNode, node, Integer.MAX_VALUE));
            visited.put(node, false);
        }
        Log.i(TAG, "init: " + format(shortestPath));
        shortestPath.put(startNode, 0);
        visited.put(startNode, true);
        for (String node : nodes) {
            String minNode = node;
            int min = Integer.MAX_VALUE;
            for (String endNode : nodes) {
                if (!visited.get(endNode) && shortestPath.get(endNode) < min) {
                    min = shortestPath.get(endNode);
                    minNode = endNode;
                }
            }
            visited.put(minNode, true);
            
        }
    }


    private static <K, V> String format(Map<K, V> values) {
        StringBuilder builder = new StringBuilder();
        builder.append("{");
        Set<K> keys = values.keySet();
        for (K key : keys) {
            builder.append(key).append(":")
                    .append(values.get(key)).append(",");
        }
        if (builder.length()  > 1) {
            builder.deleteCharAt(builder.length() - 1);
        }
        builder.append("}");
        return builder.toString();
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
