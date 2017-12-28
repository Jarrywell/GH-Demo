package com.android.test.demo.graph;

import com.google.common.collect.Interner;
import com.google.common.graph.ElementOrder;
import com.google.common.graph.EndpointPair;
import com.google.common.graph.Graph;
import com.google.common.graph.GraphBuilder;
import com.google.common.graph.Graphs;
import com.google.common.graph.ImmutableGraph;
import com.google.common.graph.ImmutableNetwork;
import com.google.common.graph.ImmutableValueGraph;
import com.google.common.graph.MutableGraph;
import com.google.common.graph.MutableNetwork;
import com.google.common.graph.MutableValueGraph;
import com.google.common.graph.NetworkBuilder;
import com.google.common.graph.Traverser;
import com.google.common.graph.ValueGraphBuilder;

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
    private static final String E42 = "4-2";
    private static final int NODE_COUNT = 20;
    private static final int EDGE_COUNT = 20;

    public static void testGraphs() {
        Log.d(TAG, "==========> Graph <==========");
        testGraph();
        Log.d(TAG, "\n\n==========> ValueGraph <==========");
        testValueGraph();
        Log.d(TAG, "\n\n==========> Network <==========");
        testNetwork();
    }

    private static void testGraph() {
        MutableGraph<Integer> graph1 = GraphBuilder.directed() //有向图
            .nodeOrder(ElementOrder.<Integer>insertion()) //节点按插入顺序
            .expectedNodeCount(NODE_COUNT) //期望节点数
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
        MutableValueGraph<Integer, String> graph1 = ValueGraphBuilder.directed()
            .allowsSelfLoops(true)
            .expectedNodeCount(NODE_COUNT)
            .nodeOrder(ElementOrder.<Integer>natural())
            .build();

        Log.d(TAG, "initlized graph1: " + graph1);

        graph1.putEdgeValue(N1, N1, E11);
        graph1.putEdgeValue(N1, N2, E12);
        graph1.putEdgeValue(N2, N1, E21);
        graph1.putEdgeValue(N1, N3, E13);
        graph1.putEdgeValue(N3, N1, E31);
        graph1.putEdgeValue(N3, N4, E34);
        graph1.putEdgeValue(N4, N4, E44);

        Set<Integer> nodes = graph1.nodes(); //返回图中所有的节点(顺序依赖nodeOrder)
        Log.d(TAG, "graph1 nodes count:" + nodes.size() + ", nodes value:" + format(nodes));

        Set<EndpointPair<Integer>> edges = graph1.edges();
        Log.d(TAG, "graph1 edge count:" + edges.size() + ", edges value:" + format(edges));

        Set<Integer> predecessors = graph1.predecessors(N3);
        Log.d(TAG, "graph1 node:" + N3 + " predecessors:" + format(predecessors));

        graph1.putEdgeValue(N4, N2, E42); //增加一条边
        Set<Integer> successors = graph1.successors(N3);
        Log.d(TAG, "add edge of (" + N4 + "->" + N2 + ") after graph1 node:" + N3
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
        ImmutableValueGraph<Integer, String> immutableGraph = ImmutableValueGraph.copyOf(graph1);
        nodes = immutableGraph.nodes(); //返回图中所有的节点(顺序依赖nodeOrder)
        Log.d(TAG, "immutable graph nodes count:" + nodes.size() + ", nodes value:" + format(nodes));

        //判断是否存在环
        final boolean cycle = Graphs.hasCycle(graph1.asGraph());
        Log.d(TAG, "graph1 has cycle: " + cycle);

        //包含指定节点的生成子图
        Set<Integer> subNodes = new HashSet<>();
        subNodes.add(N1);
        subNodes.add(N2);
        MutableValueGraph<Integer, String> subgraph = Graphs.inducedSubgraph(graph1, subNodes);
        Log.d(TAG, "subgraph: " + subgraph);

        //可到达节点集
        Set<Integer> reachNodes = Graphs.reachableNodes(graph1.asGraph(), N2);
        Log.d(TAG, "graph1 node: " + N2 + ", reachNodes: " + format(reachNodes));

        //联通图（如果节点i可达节点j,则在节点i和节点j之间增加一条直连边）
        Graph<Integer> graph2 = Graphs.transitiveClosure(graph1.asGraph());
        Log.d(TAG, "transitiveClosure graph2: " + graph2);

        //构造边反向图
        Graph<Integer> graph3 = Graphs.transpose(graph1.asGraph());
        Log.d(TAG, "transpose graph3: " + graph3);

        String edge = graph1.edgeValueOrDefault(N1, N2, "@null");
        Log.d(TAG, "graph1 node " + N1 + " & " + N2 + " edge: " + edge);

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


        MutableValueGraph<Integer, String> graph4 = ValueGraphBuilder.from(graph1).build(); //build of from()仅仅复制其属性，节点和边不会赋值过来
        Set<EndpointPair<Integer>> edges4 = graph4.edges();
        Log.d(TAG, "graph4 edge count:" + edges4.size() + ", edges value:" + format(edges4));

    }

    private static void testNetwork() {
        MutableNetwork<Integer, String> network1 = NetworkBuilder.directed() //有向网
            .allowsParallelEdges(true) //允许并行边
            .allowsSelfLoops(true) //允许自环
            .nodeOrder(ElementOrder.<Integer>natural()) //节点顺序
            .edgeOrder(ElementOrder.<String>natural()) //边顺序
            .expectedNodeCount(NODE_COUNT) //期望节点数
            .expectedEdgeCount(EDGE_COUNT) //期望边数
            .build();

        Log.d(TAG, "initlized network1: " + network1);

        network1.addEdge(N1, N1, E11);
        network1.addEdge(N1, N1, E11_A);
        network1.addEdge(N1, N2, E12);
        network1.addEdge(N1, N2, E12_A);
        network1.addEdge(N1, N2, E12_B);
        network1.addEdge(N2, N1, E21);
        network1.addEdge(N1, N3, E13);
        network1.addEdge(N3, N1, E31);
        network1.addEdge(N3, N4, E34);
        network1.addEdge(N4, N4, E44);


        Set<Integer> nodes = network1.nodes(); //返回图中所有的节点(顺序依赖nodeOrder)
        Log.d(TAG, "network1 nodes count:" + nodes.size() + ", nodes value:" + format(nodes));

        Set<String> edges = network1.edges();
        Log.d(TAG, "network1 edge count:" + edges.size() + ", edges value:" + format(edges));

        Set<Integer> predecessors = network1.predecessors(N3);
        Log.d(TAG, "network1 node:" + N3 + " predecessors:" + format(predecessors));

        network1.addEdge(N4, N2, E42); //增加一条边
        Set<Integer> successors = network1.successors(N3);
        Log.d(TAG, "add edge of (" + N4 + "->" + N2 + ") after network1 node:" + N3
            + " successors:" + format(successors));

        //邻接点
        Set<Integer> adjacents = network1.adjacentNodes(N2);
        Log.d(TAG, "network1 node: " + N2 + ", adjacents: " + format(adjacents));

        //邻接边
        Set<String> adjacentEdges = network1.adjacentEdges(E12_A);
        Log.d(TAG, "network1 edge: " + E12_A + ", adjacentEdges: " + format(adjacentEdges));

        //degree
        Log.d(TAG, "graph1 node: " + N2 + ", degree: " + network1.degree(N2)
            + ", indegree: " + network1.inDegree(N2) + ", outdegree: " + network1.outDegree(N2));

        //判断顶点连通性
        final boolean connecting23 = network1.hasEdgeConnecting(N2, N3);
        final boolean connecting34 = network1.hasEdgeConnecting(N3, N4);
        Log.d(TAG, "graph1 node " + N2 + " & " + N3 + " connecting: " + connecting23
            + ", node " + N3 + " & " + N4 + " connecting: " + connecting34);

        //转换成不可变graph
        ImmutableNetwork<Integer, String> immutableNetwork = ImmutableNetwork.copyOf(network1);
        nodes = immutableNetwork.nodes(); //返回图中所有的节点(顺序依赖nodeOrder)
        Log.d(TAG, "immutable network nodes count:" + nodes.size() + ", nodes value:" + format(nodes));

        //判断是否存在环
        final boolean cycle = Graphs.hasCycle(network1.asGraph());
        Log.d(TAG, "network1 has cycle: " + cycle);

        //包含指定节点的生成子图
        Set<Integer> subNodes = new HashSet<>();
        subNodes.add(N1);
        subNodes.add(N2);
        MutableNetwork<Integer, String> subgraph = Graphs.inducedSubgraph(network1, subNodes);
        Log.d(TAG, "network1 subgraph: " + subgraph);

        //可到达节点集
        Set<Integer> reachNodes = Graphs.reachableNodes(network1.asGraph(), N2);
        Log.d(TAG, "network1 node: " + N2 + ", reachNodes: " + format(reachNodes));

        //联通图（如果节点i可达节点j,则在节点i和节点j之间增加一条直连边）
        Graph<Integer> network2 = Graphs.transitiveClosure(network1.asGraph());
        Log.d(TAG, "transitiveClosure network2: " + network2);

        //构造边反向图
        Graph<Integer> network3 = Graphs.transpose(network1.asGraph());
        Log.d(TAG, "transpose network3: " + network3);

        //连接边集
        Set<String> networkEdges = network1.edgesConnecting(N1, N2);
        Log.d(TAG, "network1 node " + N1 + " & " + N2 + " edges: " + format(networkEdges));

        //连接边
        String edge = network1.edgeConnectingOrNull(N1, N3);
        Log.d(TAG, "network1 node " + N1 + " & " + N3 + " edge: " + edge);

        //点的邻接边
        Set<String> incidentEdges = network1.incidentEdges(N1);
        Log.d(TAG, "network1 node " + N1 + " incidents: " + format(incidentEdges));

        EndpointPair<Integer> incidentNodes =  network1.incidentNodes(E12_A);
        Log.d(TAG, "network1 edge " + E12_A + " incidentNodes: " + incidentNodes);

        //深度优先遍历图(后序)
        Iterable<Integer> dfs = Traverser.forGraph(network1).depthFirstPostOrder(N1);
        Log.d(TAG, "dfs traverser: " + format(dfs));

        //深度优先遍历图(先序)
        Iterable<Integer> dfsPre =Traverser.forGraph(network1).depthFirstPreOrder(N1);
        Log.d(TAG, "dfs pre traverser: " + format(dfsPre));

        //广度优先遍历图
        Iterable<Integer> bfs =Traverser.forGraph(network1).breadthFirst(N1);
        Log.d(TAG, "bfs traverser: " + format(bfs));

        network1.removeNode(N2); //删除一个节点N2
        edges = network1.edges();
        Log.d(TAG, "graph1 remove node of (" + N2 +  ") after graph1 edge count:" + edges.size()
            + ", edges value:" + format(edges));

        MutableNetwork<Integer, String> graph4 = NetworkBuilder.from(network1).build(); //build of from()仅仅复制其属性，节点和边不会赋值过来
        Set<String> edges4 = graph4.edges();
        Log.d(TAG, "graph4 edge count:" + edges4.size() + ", edges value:" + format(edges4));

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
