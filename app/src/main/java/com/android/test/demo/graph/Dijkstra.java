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

    private static final String A = "A";
    private static final String B = "B";
    private static final String C = "C";
    private static final String D = "D";
    private static final String E = "E";



    public static void test() {
        MutableValueGraph<String, Integer> graph = buildGraph1();
        Log.i(TAG, "graph: " + graph);

        testDijkstra(graph, V0);


        MutableValueGraph<String, Integer> graph2 = buildGraph2();
        Log.i(TAG, "graph: " + graph2);

        testDijkstra(graph2, A);
    }

    private static void testDijkstra(MutableValueGraph<String, Integer> graph, String startNode) {
        Set<String> nodes = graph.nodes();
        Map<String, NodeExtra> nodeExtras = new ArrayMap<>(nodes.size());
        /**
         * 初始化extra
         */
        for (String node : nodes) {
            NodeExtra extra = new NodeExtra();
            extra.nodeName = node;
            /*初始最短路径：存在边时，为边的权值；没有边时为无穷大*/
            final int value = graph.edgeValueOrDefault(startNode, node, Integer.MAX_VALUE);
            extra.distance = value;
            extra.visited = false;
            if (value < Integer.MAX_VALUE) {
                extra.path = startNode + " -> " + node;
                extra.preNode = startNode;
            }
            nodeExtras.put(node, extra);
        }

        /**
         * 一开始可设置开始节点的最短路径为0
         */
        NodeExtra current = nodeExtras.get(startNode);
        current.distance = 0;
        current.visited = true;
        current.path = startNode;
        current.preNode = startNode;
        /*需要循环节点数遍*/
        for (String node : nodes) {
            NodeExtra minExtra = null;
            int min = Integer.MAX_VALUE;
            /**
             * 找出起始点当前路径最短的节点
             */
            for (String notVisitedNode : nodes) {
                NodeExtra extra = nodeExtras.get(notVisitedNode);
                if (!extra.visited && extra.distance < min) {
                    min = extra.distance;
                    minExtra = extra;
                }
            }

            /**
             * 更新找到的最短路径节点的extra信息（获取的标志、路径信息）
             */
            if (minExtra != null) {
                minExtra.visited = true;
                minExtra.path = nodeExtras.get(minExtra.preNode).path + " -> " + minExtra.nodeName;
                current = minExtra;
            }

            /**
             * 并入新查找到的节点后，更新与其相关节点的最短路径中间结果
             * if (D[j] + arcs[j][k] < D[k]) D[k] = D[j] + arcs[j][k]
             */
            Set<String> successors = graph.successors(current.nodeName); //只需循环当前节点的后继列表即可
            for (String notVisitedNode : successors) {
                NodeExtra extra = nodeExtras.get(notVisitedNode);
                if (!extra.visited) {
                    final int value = current.distance + graph.edgeValueOrDefault(current.nodeName,
                            notVisitedNode, 0);
                    if (value < extra.distance) {
                        extra.distance = value;
                        extra.preNode = current.nodeName;
                    }
                }
            }
        }

        /**
         * 输出起始节点到每个节点的最短路径以及路径的途径点信息
         */
        Set<String> keys = nodeExtras.keySet();
        for (String node : keys) {
            NodeExtra extra = nodeExtras.get(node);
            if (extra.distance < Integer.MAX_VALUE) {
                Log.i(TAG, startNode + " -> " + node + ": min: " + extra.distance
                        + ", path: " + extra.path);
            }
        }
    }

    private static class NodeExtra {
        public String nodeName; //当前的节点名称
        public int distance; //开始点到当前节点的最短路径
        public boolean visited; //当前节点是否已经求的最短路径
        public String preNode; //前一个节点名称
        public String path; //路径的所有途径点
    }

    private static MutableValueGraph<String, Integer> buildGraph1() {
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

        return graph;
    }

    private static MutableValueGraph<String, Integer> buildGraph2() {
        MutableValueGraph<String, Integer> graph = ValueGraphBuilder.directed()
                .nodeOrder(ElementOrder.insertion())
                .expectedNodeCount(10)
                .build();

        graph.putEdgeValue(A, B, 10);
        graph.putEdgeValue(A, C, 3);
        graph.putEdgeValue(A, D, 20);
        graph.putEdgeValue(B, D, 5);
        graph.putEdgeValue(C, E, 15);
        graph.putEdgeValue(C, B, 2);
        graph.putEdgeValue(D, E, 11);

        return graph;
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
