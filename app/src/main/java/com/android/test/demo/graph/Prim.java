package com.android.test.demo.graph;

import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.Log;

import com.google.common.graph.ElementOrder;
import com.google.common.graph.Graph;
import com.google.common.graph.Graphs;
import com.google.common.graph.MutableValueGraph;
import com.google.common.graph.ValueGraph;
import com.google.common.graph.ValueGraphBuilder;

import java.util.Map;
import java.util.Set;

/**
 * Created by tech on 18-1-21.
 */

public class Prim {

    private final static String TAG = "Prim";

    private static final String V1 = "v1";
    private static final String V2 = "v2";
    private static final String V3 = "v3";
    private static final String V4 = "v4";
    private static final String V5 = "v5";
    private static final String V6 = "v6";

    public static void test() {
        MutableValueGraph<String, Integer> graph = buildGraph1();
        Log.i(TAG, "graph: " + graph);

        ValueGraph<String, Integer> result = testPrim(graph, V1);
        Log.i(TAG, "prim result: " + result);
    }

    private static ValueGraph<String, Integer> testPrim(MutableValueGraph<String, Integer> graph,
                                                               String startNode) {
        MutableValueGraph<String, Integer> result = Graphs.copyOf(graph);
        Map<String, CloseEdge> closeEdges = new ArrayMap<>();
        for (String node : graph.nodes()) {
            CloseEdge extra = new CloseEdge();
            extra.preNode = startNode;
            extra.lowsestCost = graph.edgeValueOrDefault(startNode, node, Integer.MAX_VALUE);
            closeEdges.put(node, extra);
        }
        closeEdges.get(startNode).lowsestCost = 0;

        String preNode = startNode;
        for (int i = 0; i < closeEdges.size() - 1; i++) {
            final String currentNode = getMinCost(closeEdges);
            Log.i(TAG, "current: " + currentNode);
            if (!TextUtils.isEmpty(currentNode)) {
                closeEdges.get(currentNode).lowsestCost = 0;
                closeEdges.get(currentNode).preNode = preNode;
                Log.i(TAG, closeEdges.get(currentNode).preNode + " -> " + currentNode);

                for (String node : graph.nodes()) {
                    final int cost = graph.edgeValueOrDefault(currentNode, node, Integer.MAX_VALUE);
                    if (cost < closeEdges.get(node).lowsestCost) {
                            closeEdges.get(node).lowsestCost = cost;
                            closeEdges.get(node).preNode = currentNode;
                            preNode = currentNode;
                    }
                }
            }
        }
        return result;
    }

    private static String getMinCost(Map<String, CloseEdge> extras) {
        String result = "";
        int min = Integer.MAX_VALUE;
        for (String node : extras.keySet()) {
            CloseEdge value = extras.get(node);
            if (value.lowsestCost > 0 && value.lowsestCost < min) {
                min = value.lowsestCost;
                result = node;
            }
        }
        return result;
    }

    private static class CloseEdge {
        public String preNode; //顶点信息
        public int lowsestCost; //最小代价
    }

    private static MutableValueGraph<String, Integer> buildGraph1() {

        MutableValueGraph<String, Integer> graph = ValueGraphBuilder.undirected()
                .nodeOrder(ElementOrder.insertion())
                .expectedNodeCount(10)
                .build();

        graph.putEdgeValue(V1, V2, 6);
        graph.putEdgeValue(V1, V3, 1);
        graph.putEdgeValue(V1, V4, 5);
        graph.putEdgeValue(V2, V3, 5);
        graph.putEdgeValue(V2, V5, 3);
        graph.putEdgeValue(V3, V4, 5);
        graph.putEdgeValue(V3, V5, 6);
        graph.putEdgeValue(V3, V6, 4);
        graph.putEdgeValue(V4, V6, 2);
        graph.putEdgeValue(V5, V6, 6);

        return graph;
    }
}
