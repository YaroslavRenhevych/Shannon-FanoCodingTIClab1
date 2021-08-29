package com.company.ticlab1;

import java.util.HashMap;
import java.util.Map;

public class BlocksSearcher {

    public Map<String, Integer> createNumMap(MessageSource source, int blockSize) {
        String text = source.getText();

        Map<String, Integer> map = new HashMap<>();
        for (int i = 0; i < text.length(); i += blockSize) {
            String block = text.substring(i, i + blockSize);
            if (!map.containsKey(block)) {
                map.put(block, 1);
            } else {
                map.replace(block, map.get(block) + 1);
            }
        }
        return map;
    }

    public Map<String, Double> createProbMap(MessageSource source, int blockSize) {
        Map<String, Integer> numMap = createNumMap(source, blockSize);

        HashMap<String, Double> prMap = new HashMap<>();
        for (Map.Entry<String, Integer> block : numMap.entrySet()) {
            prMap.put(block.getKey(), ((double) block.getValue() / ((double) source.getText().length() / blockSize)));
        }
        return prMap;
    }

    public Map<String, Double> createAmountOfInfoMap(MessageSource source, int blockSize) {
        Map<String, Integer> numMap = createNumMap(source, blockSize);
        Map<String, Double> probMap = createProbMap(source, blockSize);

        Map<String, Double> aofMap = new HashMap<>();
        for (Map.Entry<String, Integer> block : numMap.entrySet()) {
            aofMap.put(block.getKey(), -1.0 * block.getValue() * probMap.get(block.getKey()) * (Math.log(probMap.get(block.getKey())) * Math.log(2)));
        }
        return aofMap;
    }
}
