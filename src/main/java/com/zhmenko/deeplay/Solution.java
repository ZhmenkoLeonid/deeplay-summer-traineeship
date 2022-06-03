package com.zhmenko.deeplay;

import com.zhmenko.deeplay.config.ConfigurationFileParser;
import com.zhmenko.deeplay.config.JsonConfigurationFileParser;

import java.io.File;
import java.util.*;

public class Solution {
    private final static int rows = 4;
    private final static int columns = 4;

    public static int getResult(String cells, String entityType, File propertiesFile) {
        // загружаем конфиг из файла
        ConfigurationFileParser fileParser = new JsonConfigurationFileParser();
        Map<String, Map<String, Integer>> entitiesProperties = fileParser.parseFile(propertiesFile);
        Map<String, Integer> entityProperties = Objects.requireNonNull(entitiesProperties.get(entityType),
                String.format("Не найдено существа с именем %s", entityType));
        if (cells.length() != rows * columns)
            throw new IllegalStateException(
                    String.format("Количество клеток (%d) во входных данных не совпадает с требуемым (%d)!",
                            cells.length(),
                            rows * columns)
            );

        List<Node> nodeList = buildNodes(cells, entityProperties);
        Node start = nodeList.get(0);
        Node end = nodeList.get(nodeList.size()-1);
        //стоимость перемещения по начальной клетке не считается, поэтому "зануляем" её
        //                            |
        //                            v
        return dfs(start, end, -start.getVal(), new HashSet<>(), Integer.MAX_VALUE);
    }

    private static int dfs(Node cur, Node target, int curPathCost, Set<Node> visited, int minPathCost) {
        visited.add(cur);
        if (cur == target) {
            //DEBUG
/*            System.out.print((curPathCost + target.getVal()) + ": ");
            visited.stream().forEach(a -> System.out.print(a.getVal() + " "));
            System.out.println();*/
            return curPathCost + target.getVal();
        }
        for (Node neighbor : cur.getNeighbors()) {
            if (!visited.contains(neighbor)) {
                int newPathCost = curPathCost + cur.getVal();
                // Если стоимость уже не меньше минимальной найденной, то нет смысла идти до конца
                if (newPathCost >= minPathCost) return minPathCost;
                // Смотрим, меньше ли стоимость у текущего найденного пути, и если меньше, то запоминаем его
                minPathCost = Math.min(dfs(neighbor, target, newPathCost, new HashSet<>(visited), minPathCost), minPathCost);
            }
        }
        return minPathCost;
    }

    private static List<Node> buildNodes(String cells, Map<String, Integer> entityProperties) {
        int capacity = cells.length();
        char[] cellsArr = cells.toCharArray();
        // Создаём ноды
        List<Node> nodeList = new ArrayList<>();
        for (char c : cellsArr) {
            String cellKey = String.valueOf(c);
            if (!entityProperties.containsKey(cellKey))
                throw new IllegalStateException(String.format("Не определена стоимость для клетки с именем '%s'", cellKey));
            int value = entityProperties.get(cellKey);
            nodeList.add(new Node(value));
        }
        // ищем соседей нод
        for (int i = 0; i < nodeList.size(); i++) {
            List<Node> cellNeighbors = new ArrayList<>();
            if ((i - 1) % rows != rows - 1 && i - 1 >= 0) {
                cellNeighbors.add(nodeList.get(i - 1));
            }
            if (i - rows >= 0) cellNeighbors.add(nodeList.get(i - rows));
            if ((i + 1) % rows != 0 && i + 1 < capacity) {
                cellNeighbors.add(nodeList.get(i + 1));
            }
            if (i + rows < capacity) cellNeighbors.add(nodeList.get(i + rows));
            nodeList.get(i).setNeighbors(cellNeighbors);
        }
        return nodeList;
    }
}
