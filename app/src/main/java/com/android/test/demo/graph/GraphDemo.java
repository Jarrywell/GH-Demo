package com.android.test.demo.graph;

import com.google.common.collect.Interner;
import com.google.common.graph.ElementOrder;
import com.google.common.graph.EndpointPair;
import com.google.common.graph.Graph;
import com.google.common.graph.GraphBuilder;
import com.google.common.graph.Graphs;
import com.google.common.graph.ImmutableGraph;
import com.google.common.graph.MutableGraph;
import com.google.common.graph.Traverser;

import android.util.Log;

import java.util.HashSet;
import java.util.Set;

/**
 * des:
 * author: libingyan
 * Date: 17-12-26 19:44
 */
public class GraphDemo {
    private static final String TAG = "GraphDemo";

    private static final Integer N1 = 1;
    private static final Integer N2 = 2;
    private static final Integer N3 = 3;
    private static final Integer N4 = 4;
    private static final String E11 = "1-1";
    private static final String E11_A = "1-1a";
    private static final String E12 = "1-2";
    private static final String E12_A = "1-2a";
    private static final String E12_B = "1-2b";
    private static final String E21 = "2-1";
    private static final String E13 = "1-3";
    private static final String E31 = "3-1";
    private static final String E34 = "3-4";
    private static final String E44 = "4-4";
    private static final int NODE_COUNT = 20;
    private static final int EDGE_COUNT = 20;

    public static void testGraphs() {
        Log.d(TAG, "==========> Graph <==========");
        testGraph();
        Log.d(TAG, "==========> ValueGraph <==========");
        testValueGraph();
        Log.d(TAG, "==========> Network <==========");
        testNetwork();
    }

    private static void testGraph() {
        MutableGraph<Integer> graph1 = GraphBuilder.directed() //无向图
            .nodeOrder(ElementOrder.<Integer>insertion()) //节点按插入顺序
            .expectedNodeCount(10) //期望节点数
            .allowsSelfLoops(true) //允许自环
            .build();

        Log.d(TAG, "initlized graph1: " + graph1);

        //插入边(默认会将节点加入graph中)
        graph1.putEdge(N2, N3);
        graph1.putEdge(N1, N3);
        graph1.putEdge(N1, N2);
        graph1.putEdge(N2, N2);
        graph1.addNode(N4);

        Set<Integer> nodes = graph1.nodes(); //返回图中所有的节点(顺序依赖nodeOrder)
        Log.d(TAG, "graph1 nodes count:" + nodes.size() + ", nodes value:" + format(nodes));

        Set<EndpointPair<Integer>> edges = graph1.edges();
        Log.d(TAG, "graph1 edge count:" + edges.size() + ", edges value:" + format(edges));

        Set<Integer> predecessors = graph1.predecessors(N2);
        Log.d(TAG, "graph1 node:" + N2 + " predecessors:" + format(predecessors));

        graph1.putEdge(N2, N4); //增加一条边
        Set<Integer> successors = graph1.successors(N2);
        Log.d(TAG, "add edge of (" + N2 + "->" + N4 + ") after graph1 node:" + N2
            + " successors:" + format(successors));

        //邻接点
        Set<Integer> adjacents = graph1.adjacentNodes(N2);
        Log.d(TAG, "graph1 node: " + N2 + ", adjacents: " + format(adjacents));

        //degree
        Log.d(TAG, "graph1 node: " + N2 + ", degree: " + graph1.degree(N2)
            + ", indegree: " + graph1.inDegree(N2) + ", outdegree: " + graph1.outDegree(N2));

        //判断顶点连通性
        final boolean connecting23 = graph1.hasEdgeConnecting(N2, N3);
        final boolean connecting34 = graph1.hasEdgeConnecting(N3, N4);
        Log.d(TAG, "graph1 node " + N2 + " & " + N3 + " connecting: " + connecting23
            + ", node " + N3 + " & " + N4 + " connecting: " + connecting34);

        //转换成不可变graph
        ImmutableGraph<Integer> immutableGraph = ImmutableGraph.copyOf(graph1);
        nodes = immutableGraph.nodes(); //返回图中所有的节点(顺序依赖nodeOrder)
        Log.d(TAG, "immutable graph nodes count:" + nodes.size() + ", nodes value:" + format(nodes));

        //判断是否存在环
        final boolean cycle = Graphs.hasCycle(graph1);
        Log.d(TAG, "graph1 has cycle: " + cycle);

        //包含指定节点的生成子图
        Set<Integer> subNodes = new HashSet<>();
        subNodes.add(N1);
        subNodes.add(N2);
        MutableGraph<Integer> subgraph = Graphs.inducedSubgraph(graph1, subNodes);
        Log.d(TAG, "subgraph: " + subgraph);

        //可到达节点集
        Set<Integer> reachNodes = Graphs.reachableNodes(graph1, N2);
        Log.d(TAG, "graph1 node: " + N2 + ", reachNodes: " + format(reachNodes));

        //联通图（如果节点i可达节点j,则在节点i和节点j之间增加一条直连边）
        Graph<Integer> graph2 = Graphs.transitiveClosure(graph1);
        Log.d(TAG, "transitiveClosure graph2: " + graph2);

        //构造边反向图
        Graph<Integer> graph3 = Graphs.transpose(graph1);
        Log.d(TAG, "transpose graph3: " + graph3);

        //深度优先遍历图(后序)
        Iterable<Integer> dfs = Traverser.forGraph(graph1).depthFirstPostOrder(N1);
        Log.d(TAG, "dfs traverser: " + format(dfs));

        //深度优先遍历图(先序)
        Iterable<Integer> dfsPre =Traverser.forGraph(graph1).depthFirstPreOrder(N1);
        Log.d(TAG, "dfs pre traverser: " + format(dfsPre));


        //广度优先遍历图
        Iterable<Integer> bfs =Traverser.forGraph(graph1).breadthFirst(N1);
        Log.d(TAG, "bfs traverser: " + format(bfs));

        graph1.removeNode(N2); //删除一个节点N2
        edges = graph1.edges();
        Log.d(TAG, "graph1 remove node of (" + N2 +  ") after graph1 edge count:" + edges.size()
            + ", edges value:" + format(edges));


        MutableGraph<Integer> graph4 = GraphBuilder.from(graph1).build(); //build of from()仅仅复制其属性，节点和边不会赋值过来
        Set<EndpointPair<Integer>> edges4 = graph4.edges();
        Log.d(TAG, "graph4 edge count:" + edges4.size() + ", edges value:" + format(edges4));

    }

    private static void testValueGraph() {

    }

    private static void testNetwork() {

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
