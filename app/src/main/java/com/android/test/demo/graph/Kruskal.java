package com.android.test.demo.graph;


import android.util.ArrayMap;
import android.util.Log;

import com.google.common.graph.ElementOrder;
import com.google.common.graph.EndpointPair;
import com.google.common.graph.MutableValueGraph;
import com.google.common.graph.ValueGraph;
import com.google.common.graph.ValueGraphBuilder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

/**
 * Created by tech on 18-1-27.
 */

public class Kruskal {
    private final static String TAG = "Kruskal";

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
        final ValueGraph<String, Integer> graph1 = buildGraph1();
        Log.i(TAG, "graph1: " + graph1);

        ValueGraph<String, Integer> result1 = testKruskal(graph1);
        Log.i(TAG, "test kruskal: " + result1);

        ValueGraph<String, Integer> graph2 = buildGraph2();
        Log.i(TAG, "graph2: " + graph2);
        ValueGraph<String, Integer> result2 = testKruskal(graph2);
        Log.i(TAG, "kruskal result2: " + result2);
    }

    private static ValueGraph<String, Integer> testKruskal(ValueGraph<String, Integer> graph) {
        //最小生成树的结果graph
        MutableValueGraph<String, Integer> result = ValueGraphBuilder.from(graph).build();

        /**
         * 辅助变量，用于判断两个顶点是否在两个连通分量中
         */
        Map<String, Integer> vest = new ArrayMap<>();

        /**
         * 初始化辅助数组vest，一开始每个节点单独成一个连通分量
         */
        int index = 0;
        for (String node : graph.nodes()) {
            vest.put(node, index++);
        }

        /**
         * 将图中所有的边，按权值从小到大排序，便于后面一次循环就能从小到大遍历
         * EndpointPair数据结构用于存储边的两个顶点U和V
         */
        List<EndpointPair<String>> edges = sortEdges(graph);

        /**
         * 按增序遍历图中所有边
         */
        for (EndpointPair<String> edge : edges) {
            /*Log.i(TAG, edge.nodeU() + " ->" + edge.nodeV() + " = "
                    + graph.edgeValueOrDefault(edge.nodeU(), edge.nodeV(), 0));*/

            /**
             * 获取两个节点所代表的连通分量的sn值
             */
            final int nodeUSn = vest.get(edge.nodeU());
            final int nodeVSn = vest.get(edge.nodeV());

            /**
             * 判断一条边的两个顶点是否对应不同的连通分量。若不相同，则将该边加入最小生成树的图中
             */
            if (nodeUSn != nodeVSn) {
                final int value = graph.edgeValueOrDefault(edge.nodeU(), edge.nodeV(), 0);
                result.putEdgeValue(edge.nodeU(), edge.nodeV(), value);
                Log.i(TAG, edge.nodeU() + " ->" + edge.nodeV() + ": " + value);

                /**
                 * 更新加入最小生成树中边对应的整个连通分量的sn值（后一个连通分量并入
                 * 前一个连通分量），以此作为下一次遍历的依据
                 */
                for (String node : vest.keySet()) {
                    if (vest.get(node) == nodeVSn) {
                        vest.put(node, nodeUSn);
                    }
                }
            }
        }
        return result;
    }


    /**
     * 将图中的边按其权值大小排序
     * @param graph
     * @return
     */
    private static List<EndpointPair<String>> sortEdges(final ValueGraph<String, Integer> graph) {
        List<EndpointPair<String>> edges = new ArrayList<>();
        edges.addAll(graph.edges());
        /**
         * 使用Collections的sort函数进行排序，compare()比较的是边权值
         */
        Collections.sort(edges, new Comparator<EndpointPair<String>>() {
            @Override
            public int compare(EndpointPair<String> endPoint1,
                               EndpointPair<String> endPoint2) {
                final int edge1 = graph.edgeValueOrDefault(endPoint1.nodeU(),
                        endPoint1.nodeV(), 0);
                final int edge2 = graph.edgeValueOrDefault(endPoint2.nodeU(),
                        endPoint2.nodeV(), 0);
                return edge1 - edge2;
            }
        });
        return edges;
    }

    private static MutableValueGraph<String, Integer> buildGraph1() {
        MutableValueGraph<String, Integer> graph = ValueGraphBuilder.undirected()
                .nodeOrder(ElementOrder.<String>natural())
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


    private static MutableValueGraph<String, Integer> buildGraph2() {
        MutableValueGraph<String, Integer> graph = ValueGraphBuilder.undirected()
                .nodeOrder(ElementOrder.<String>natural())
                .expectedNodeCount(10)
                .build();

        graph.putEdgeValue(V1, V2, 5);
        graph.putEdgeValue(V1, V3, 13);
        graph.putEdgeValue(V1, V4, 12);
        graph.putEdgeValue(V1, V5, 10);
        graph.putEdgeValue(V1, V6, 8);
        graph.putEdgeValue(V1, V7, 6);
        graph.putEdgeValue(V1, V8, 2);
        graph.putEdgeValue(V1, V9, 5);

        graph.putEdgeValue(V2, V3, 3);
        graph.putEdgeValue(V3, V4, 9);
        graph.putEdgeValue(V4, V5, 11);
        graph.putEdgeValue(V5, V6, 9);
        graph.putEdgeValue(V6, V7, 6);
        graph.putEdgeValue(V7, V8, 7);
        graph.putEdgeValue(V8, V9, 4);
        graph.putEdgeValue(V9, V2, 1);

        return graph;
    }
}
