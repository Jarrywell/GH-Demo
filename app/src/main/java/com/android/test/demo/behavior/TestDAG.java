package com.android.test.demo.behavior;

import android.support.v4.util.ArrayMap;
import android.support.v4.util.SimpleArrayMap;
import android.util.Log;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class TestDAG {

    public final String TAG = "TestDAG";


    public void test1() {

        DirectedAcyclicGraph<String> DAG = new DirectedAcyclicGraph<>();
        DAG.addNode("A");
        DAG.addNode("I");
        DAG.addNode("C");
        DAG.addNode("B");
        DAG.addNode("G");
        DAG.addNode("D");
        DAG.addNode("H");
        DAG.addNode("E");
        DAG.addNode("F");

        DAG.addEdge("B", "A");
        DAG.addEdge("C", "A");
        DAG.addEdge("D", "B");
        DAG.addEdge("E", "B");
        DAG.addEdge("E", "C");
        DAG.addEdge("F", "C");
        DAG.addEdge("G", "D");
        DAG.addEdge("G", "E");
        DAG.addEdge("H", "E");
        DAG.addEdge("H", "F");
        DAG.addEdge("I", "G");
        DAG.addEdge("I", "H");

        //DAG.addEdge("A", "I"); //增加环形依赖

        final int size = DAG.size();
        Log.d(TAG, "DAG size: " + size);

        List<String> incomingEdges = DAG.getIncomingEdges("I");
        Log.d(TAG, "node I, incomming: " + formatList(incomingEdges));

        List<String> outgoingEdges = DAG.getOutgoingEdges("E");
        Log.d(TAG, "node E, outgoings: " + formatList(outgoingEdges));

        List<String> sortList = DAG.getSortedList();
        Log.d(TAG, "sortList: " + formatList(sortList));
    }

    private String formatList(List<String> list) {
        StringBuilder builder = new StringBuilder();
        for (String value : list) {
            builder.append(value);
            builder.append(",");
        }
        return builder.toString();
    }

    /**
     * 输出结果:有序
     * 12-04 13:12:22.684 D/Jarry   (10694): i: 0, node: A
     * 12-04 13:12:22.684 D/Jarry   (10694): i: 1, node: B
     * 12-04 13:12:22.684 D/Jarry   (10694): i: 2, node: C
     * 12-04 13:12:22.684 D/Jarry   (10694): i: 3, node: D
     * 12-04 13:12:22.684 D/Jarry   (10694): i: 4, node: E
     * 12-04 13:12:22.684 D/Jarry   (10694): i: 5, node: F
     * 12-04 13:12:22.684 D/Jarry   (10694): i: 6, node: G
     * 12-04 13:12:22.684 D/Jarry   (10694): i: 7, node: H
     * 12-04 13:12:22.684 D/Jarry   (10694): i: 8, node: I
     */
    public void test2() {
        SimpleArrayMap<String, String> arrayMap = new SimpleArrayMap<>();
        arrayMap.put("A", null);
        arrayMap.put("I", null);
        arrayMap.put("C", null);
        arrayMap.put("B", null);
        arrayMap.put("G", null);
        arrayMap.put("D", null);
        arrayMap.put("H", null);
        arrayMap.put("E", null);
        arrayMap.put("F", null);

        for (int i = 0, size = arrayMap.size(); i < size; i++) {
            Log.d(TAG, "i: " + i + ", node: " + arrayMap.keyAt(i));
        }
    }

    /**
     * ArrayMap -- 输出结果(有序)
     * 12-04 13:06:48.586 D/Jarry   ( 6085): node: A
     * 12-04 13:06:48.586 D/Jarry   ( 6085): node: B
     * 12-04 13:06:48.586 D/Jarry   ( 6085): node: C
     * 12-04 13:06:48.586 D/Jarry   ( 6085): node: D
     * 12-04 13:06:48.586 D/Jarry   ( 6085): node: E
     * 12-04 13:06:48.586 D/Jarry   ( 6085): node: F
     * 12-04 13:06:48.586 D/Jarry   ( 6085): node: G
     * 12-04 13:06:48.586 D/Jarry   ( 6085): node: H
     * 12-04 13:06:48.586 D/Jarry   ( 6085): node: I

     *
     * HashMap -- 输出结果(无序)
     * 12-04 13:07:58.674 D/Jarry   ( 6775): node: D
     * 12-04 13:07:58.674 D/Jarry   ( 6775): node: I
     * 12-04 13:07:58.674 D/Jarry   ( 6775): node: C
     * 12-04 13:07:58.674 D/Jarry   ( 6775): node: E
     * 12-04 13:07:58.674 D/Jarry   ( 6775): node: A
     * 12-04 13:07:58.674 D/Jarry   ( 6775): node: H
     * 12-04 13:07:58.674 D/Jarry   ( 6775): node: G
     * 12-04 13:07:58.674 D/Jarry   ( 6775): node: F
     * 12-04 13:07:58.674 D/Jarry   ( 6775): node: B
     */
    public void test3() {
        Map<String, String> arrayMap = new ArrayMap<>(); //HashMap
        arrayMap.put("A", "0");
        arrayMap.put("I", "0");
        arrayMap.put("C", "0");
        arrayMap.put("B", "0");
        arrayMap.put("G", "0");
        arrayMap.put("D", "0");
        arrayMap.put("H", "0");
        arrayMap.put("E", "0");
        arrayMap.put("F", "0");

        Set<String> set = arrayMap.keySet();
        for (String key : set) {
            Log.d(TAG, "node: " + key);
        }
    }

}
