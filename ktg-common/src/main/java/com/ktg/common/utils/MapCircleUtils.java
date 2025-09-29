package com.ktg.common.utils;

import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author yinjinlu
 * @description 物料BOM环形依赖关系有向图检测
 * @date 2024/12/18
 */
@Service
public class MapCircleUtils {

    private Map<Long,List<Long>> adj; // 邻接表

    private MapCircleUtils() {
        this.adj = new HashMap<>();
    }

    // 静态工厂方法，用于创建实例
    public static MapCircleUtils createInstance() {
        return new MapCircleUtils();
    }

    public void clear() {
        adj.clear();
    }

    /**
     * 向图中添加一条从顶点v到顶点w的边（无向图则双向添加）
     * @param v 起点
     * @param w 终点
     */
    public void addEdge(Long v, Long w) {
        adj.putIfAbsent(v, new ArrayList<>());
        adj.putIfAbsent(w, new ArrayList<>());
        adj.get(v).add(w);
//        adj.get(w).add(v); // 对于无向图，添加反向边
    }

    /**
     * 向图中添加一个节点（如果节点已存在，则不执行任何操作）
     * @param vertex 要添加的节点
     */
    public void addVertex(Long vertex) {
        adj.putIfAbsent(vertex, new ArrayList<>());
    }


    /**
     * 返回图中的顶点数量
     * @return
     */
    public int getVertices() {
        return adj.size();
    }

    /**
     * 检测从顶点 v 出发是否存在环
     * @param v
     * @param visited
     * @param onStack
     * @return
     */
    private boolean isCircleUtil(Long v, boolean[] visited, boolean[] onStack) {
        if (visited[v.intValue()]) {
            return true; // 已经访问过，说明有环
        }

        if (onStack[v.intValue()]) {
            return false; // 已访问过且不在递归栈中
        }

        visited[v.intValue()] = true;
        onStack[v.intValue()] = true;

        for (Long w : adj.getOrDefault(v, Collections.emptyList())) {
            if (isCircleUtil(w, visited, onStack)) {
                return true;
            }
        }

        onStack[v.intValue()] = false;
        return false;
    }

    /**
     * 图中顶点索引的最大值
     * @return
     */
    private Long getMaxVertextIndex(){
        return adj.isEmpty() ? -1L : Collections.max(adj.keySet());
    }

    /**
     * 检测图中是否存在环
     * @return
     */
    public boolean hasCircle() {
        Long max = getMaxVertextIndex();
        boolean[] visited = new boolean[(int) (max + 1)];
        boolean[] onStack = new boolean[(int) (max + 1)];
        for ( Long vertext: adj.keySet()) {
            if (!visited[vertext.intValue()] && isCircleUtil(vertext, visited, onStack)) {
                return true;
            }
        }
        return false;
    }

    public boolean hasCycle() {
        Set<Long> visited = new HashSet<>();
        Set<Long> inPath = new HashSet<>();

        for (Long node : adj.keySet()) {
            if (!visited.contains(node)) {
                if (dfs(adj, node, visited, inPath)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean dfs(Map<Long, List<Long>> graph, Long node, Set<Long> visited, Set<Long> inPath) {
        visited.add(node);
        inPath.add(node);

        List<Long> neighbors = graph.get(node);
        if (neighbors != null) {
            for (Long neighbor : neighbors) {
                if (!visited.contains(neighbor)) {
                    if (dfs(graph, neighbor, visited, inPath)) {
                        return true;
                    }
                } else if (inPath.contains(neighbor)) {
                    return true;
                }
            }
        }

        inPath.remove(node); // 回溯时移除当前节点
        return false;
    }
}
