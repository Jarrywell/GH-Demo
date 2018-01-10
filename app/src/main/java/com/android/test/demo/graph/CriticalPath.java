package com.android.test.demo.graph;

import android.util.ArrayMap;
import android.util.Log;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.graph.ElementOrder;
import com.google.common.graph.EndpointPair;
import com.google.common.graph.MutableValueGraph;
import com.google.common.graph.Traverser;
import com.google.common.graph.ValueGraph;
import com.google.common.graph.ValueGraphBuilder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by tech on 18-1-6.
 */

public class CriticalPath {

    private static final String TAG = "CriticalPath";

    //graph1
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


    //graph2
    private static final String V1 = "v1";
    private static final String V2 = "v2";
    private static final String V3 = "v3";
    private static final String V4 = "v4";
    private static final String V5 = "v5";
    private static final String V6 = "v6";
    private static final String V7 = "v7";
    private static final String V8 = "v8";
    private static final String V9 = "v9";

    public static void test() {
        MutableValueGraph<String, Integer> graph = buildGraph1();
        Log.i(TAG, "graph: " + graph);
        testCirticalPath(graph, A);

        MutableValueGraph<String, Integer> graph2 = buildGraph2();
        Log.i(TAG, "graph: " + graph2);
        testCirticalPath(graph2, V1);
    }

    public static void testCirticalPath(MutableValueGraph<String, Integer> graph, String startNode) {
        /**
         * 将graph进行拓扑排序topologically，此处返回的逆拓扑排序
         */
        Iterable<String> topologicallys = Traverser.forGraph(graph).depthFirstPostOrder(startNode);
        Log.i(TAG, "topologically: " + format(topologicallys));

        /**
         * 求关键活动就是求最早开始时间和最迟开始时间相同的活动：e(i) == l(i),为求的这个关系，
         * 首先应求得事件的最早发生时间ve(j)和最迟发生时间vl(j)
         * 活动a(i)由弧<j,k>表示，其持续时间记为dut(<j,k>)，则有如下关系：
         * e(i) = ve(j)                 ----ve(j) = Max{ve(i) + dut(<i,j>) }; <i,j>属于T，j=1,2...,n-1
         * l(i) = vl(k) - dut(<j,k>)    ----vl(i) = Min{vl(j) - dut(<i,j>}; <i,j>属于S，i=n-2,...,0
         * 下面两步求取每个节点的ve(i)和vl(i)值
         */
        //获取ve(i)
        Map<String, Integer> ves = getVeValues(graph, topologicallys);
        Log.i(TAG, "ves: " + format(ves));

        //获取vl(i)
        Map<String, Integer> vls = getVlValues(graph, topologicallys, ves);
        Log.i(TAG, "vls: " + format(vls));

        /**
         * 判断条件：ve(j) == vl(k) - dut(<j,k>)
         */
        List<EndpointPair<String>> criticalActives = new ArrayList<>();
        Set<EndpointPair<String>> edgs = graph.edges(); //返回图中所以的活动(边)
        for (EndpointPair<String> endpoint : edgs) {
            final int dut = graph.edgeValueOrDefault(endpoint.nodeU(), endpoint.nodeV(), 0);
            if (vls.get(endpoint.nodeV()) - dut == ves.get(endpoint.nodeU())) { //ve(j) == vl(k) - dut<j, k>
                criticalActives.add(endpoint);
            }
        }
        Log.i(TAG, "critical actives: " + format(criticalActives));
    }

    private static MutableValueGraph<String, Integer> buildGraph1() {
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

        return graph;
    }

    private static MutableValueGraph<String, Integer> buildGraph2() {
        MutableValueGraph<String, Integer> graph = ValueGraphBuilder.directed()
            .nodeOrder(ElementOrder.insertion())
            .expectedNodeCount(10)
            .build();

        graph.putEdgeValue(V1, V2, 6);
        graph.putEdgeValue(V1, V3, 4);
        graph.putEdgeValue(V1, V4, 5);
        graph.putEdgeValue(V2, V5, 1);
        graph.putEdgeValue(V3, V5, 1);
        graph.putEdgeValue(V4, V6, 2);
        graph.putEdgeValue(V5, V7, 9);
        graph.putEdgeValue(V5, V8, 7);
        graph.putEdgeValue(V6, V8, 4);
        graph.putEdgeValue(V7, V9, 2);
        graph.putEdgeValue(V8, V9, 4);

        return graph;
    }


    /**
     * ve(j) = Max{ve(i) + dut(<i,j>) }; <i,j>属于T，j=1,2...,n-1
     * @param graph
     * @param topologicallys
     * @return
     */
    private static Map<String, Integer> getVeValues(ValueGraph<String, Integer> graph, Iterable<String> topologicallys) {
        List<String> reverses = Lists.newArrayList(topologicallys.iterator());
        Collections.reverse(reverses);
        Map<String, Integer> ves = new ArrayMap<>();
        for (String node : reverses) {
            ves.put(node, 0);
            Set<String> predecessors = graph.predecessors(node);
            int maxValue = 0;
            for (String predecessor : predecessors) {
                maxValue = Math.max(ves.get(predecessor) +
                        graph.edgeValueOrDefault(predecessor, node, 0), maxValue);
            }
            ves.put(node, maxValue);
        }
        return ves;
    }

    /**
     * vl(i) = Min{vl(j) - dut(<i,j>}; <i,j>属于S，i=n-2,...,0
     * @param graph
     * @param topologicallys
     * @param vels
     * @return
     */
    private static Map<String, Integer> getVlValues(ValueGraph<String, Integer> graph,
        Iterable<String> topologicallys, Map<String, Integer> vels) {
        Map<String, Integer> vls = new ArrayMap<>();
        for (String node : topologicallys) {
            Set<String> successors = graph.successors(node);
            int initValue = Integer.MAX_VALUE;
            if (successors.size() <= 0) {
                initValue = vels.get(node);
            }
            vls.put(node, initValue);
            int minValue = initValue;
            for (String successor : successors) {
                minValue = Math.min(vls.get(successor) -
                        graph.edgeValueOrDefault(node, successor, 0), minValue);
            }
            vls.put(node, minValue);
        }
        return vls;
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
