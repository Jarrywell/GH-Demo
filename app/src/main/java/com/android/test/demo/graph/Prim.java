package com.android.test.demo.graph;

import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.Log;

import com.google.common.graph.ElementOrder;
import com.google.common.graph.MutableValueGraph;
import com.google.common.graph.ValueGraph;
import com.google.common.graph.ValueGraphBuilder;

import java.util.Map;

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
    private static final String V7 = "v7";
    private static final String V8 = "v8";
    private static final String V9 = "v9";

    public static void test() {
        MutableValueGraph<String, Integer> graph1 = buildGraph1();
        Log.i(TAG, "graph1: " + graph1);
        ValueGraph<String, Integer> result = testPrim(graph1, V1);
        Log.i(TAG, "prim result1: " + result);


        MutableValueGraph<String, Integer> graph2 = buildGraph2();
        Log.i(TAG, "graph: " + graph2);
        ValueGraph<String, Integer> result2 = testPrim(graph2, V1);
        Log.i(TAG, "prim result2: " + result2);
    }

    private static ValueGraph<String, Integer> testPrim(MutableValueGraph<String, Integer> graph,
                                                               String startNode) {
        //最小生成树的结果graph
        MutableValueGraph<String, Integer> result = ValueGraphBuilder.from(graph).build();

        //节点的附加信息，用于保存计算的中间结果
        Map<String, CloseEdge> closeEdges = new ArrayMap<>();

        /**
         * 初始化开始节点与节点的权重信息
         */
        for (String node : graph.nodes()) {
            CloseEdge extra = new CloseEdge();
            extra.preNode = startNode; //前一个节点为startNode节点
            //有边连接时，lowsestCost为其边上的权值；没有边连接时，则为无穷大
            extra.lowsestCost = graph.edgeValueOrDefault(startNode, node, Integer.MAX_VALUE);
            closeEdges.put(node, extra);
        }

        closeEdges.get(startNode).lowsestCost = 0; //初始化时，先将startNode并入U集合（lowsestCost = 0）
        for (int i = 0; i < closeEdges.size() - 1; i++) { //循环n -1次
            /**
             * 找到一条{U, V - U}中权值最小的边，minCostNode是V - U集合中的顶点
             */
            String minCostNode = "";
            int min = Integer.MAX_VALUE;
            for (String node : closeEdges.keySet()) {
                CloseEdge value = closeEdges.get(node);

                /**
                 * lowsestCost == 0时，表示该边已经并入了U集合，不在查找范围
                 */
                if (value.lowsestCost > 0 && value.lowsestCost < min) {
                    min = value.lowsestCost;
                    minCostNode = node;
                }
            }

            if (!TextUtils.isEmpty(minCostNode)) {
                CloseEdge minEdge = closeEdges.get(minCostNode);
                /**
                 * 将最小的权重边放入结果Graph中
                 */
                result.putEdgeValue(minEdge.preNode, minCostNode, minEdge.lowsestCost);
                //Log.i(TAG, minEdge.preNode + " -> " + minCostNode);
                minEdge.lowsestCost = 0; //将找到的最小的边并入U集合中

                /**
                 * 并入minCostNode后，更新minCostNode到其他节点的lowsestCost信息，作为下次循环查找的条件
                 */
                for (String node : graph.adjacentNodes(minCostNode)) { //优化：只遍历邻接点即可
                    final int cost = graph.edgeValueOrDefault(minCostNode, node, Integer.MAX_VALUE);
                    CloseEdge current = closeEdges.get(node);
                    if (current.lowsestCost > 0 && cost < current.lowsestCost) {
                        current.lowsestCost = cost;
                        current.preNode = minCostNode;
                    }
                }
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
