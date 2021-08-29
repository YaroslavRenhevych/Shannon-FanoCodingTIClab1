package ticlab1;

import java.io.File;
import java.util.Map;

public class MainClass {
    private static final Coder coder = new Coder();
    private static final BlocksSearcher blocksSearcher = new BlocksSearcher();
    private static final String SOURCE_PATH = "TICLab1/text.txt";

    public static void main(String[] args) {
        File file = new File(SOURCE_PATH);
        MessageSource src = new MessageSource(file);
        System.out.println("Послідовність повідомлень джерела:\n" + src.getText());
        System.out.println("Number of messages: " + src.getText().length());

        Map<String, Integer> msgNumberMap = blocksSearcher.createNumMap(src, 1);
        Map<String, Double> msgProbabilityMap = blocksSearcher.createProbMap(src, 1);
        Map<String, Double> msgAmountOfInfoMap = blocksSearcher.createAmountOfInfoMap(src, 1);
        System.out.println("Number of unique messages: " + msgNumberMap.size());

        Map<String, String> msgCodeMap = coder.shannonFanoCoding(msgProbabilityMap);
        String encodingText = coder.textCoding(src.getText(), msgCodeMap);

        printBlocks(msgNumberMap, msgProbabilityMap, msgAmountOfInfoMap, msgCodeMap, encodingText);
        System.out.println("Source entropy: " + searchEntropy(msgProbabilityMap));

        double msgCodingEff = searchEfficiency(msgCodeMap, msgProbabilityMap);
        System.out.println("Coding Efficiency: " + msgCodingEff);
        double msgCodingLB = searchLowerBoundForBinaryCoding(searchEntropy(msgProbabilityMap));
        System.out.println("Lower Bound: " + msgCodingLB);

        System.out.println("-------------Block coding-----------");
        Map<String, Integer> blockNumberMap = blocksSearcher.createNumMap(src, 2);
        Map<String, Double> blockProbabilityMap = blocksSearcher.createProbMap(src, 2);
        Map<String, Double> blockAmountOfInfoMap = blocksSearcher.createAmountOfInfoMap(src, 2);
        Map<String, String> blockCodeMap = coder.shannonFanoCoding(blockProbabilityMap);
        encodingText = coder.textCoding(src.getText(), blockCodeMap);

        printBlocks(blockNumberMap, blockProbabilityMap, blockAmountOfInfoMap, blockCodeMap, encodingText);
        double blockCodingEff = searchEfficiency(blockCodeMap, blockProbabilityMap);
        System.out.println("Block coding Efficiency: " + blockCodingEff);
        double blockCodingLB = searchLowerBoundForBinaryCoding(searchEntropy(blockProbabilityMap));
        System.out.println("Lower Bound for block coding: " + blockCodingLB);

        System.out.println("-------------------------------------");
        System.out.println("Difference between message coding and block coding efficiencys: " + (msgCodingEff - blockCodingEff));
        System.out.println("Difference between message coding and block coding lower bounds: " + (msgCodingLB - blockCodingLB));
    }

    private static void printBlocks(Map<String, Integer> numberMap, Map<String, Double> probabilityMap, Map<String, Double> amountOfInfoMap,
                                    Map<String, String> codeMap, String encodingText) {
        for (Map.Entry<String, Integer> block : numberMap.entrySet()) {
            System.out.printf("%s   %2d   %.3f   %.3f   %s", block.getKey(),
                    numberMap.get(block.getKey()),
                    probabilityMap.get(block.getKey()),
                    amountOfInfoMap.get(block.getKey()),
                    codeMap.get(block.getKey()));
            System.out.println("|");
        }
        System.out.println("Encoding text: " + encodingText);
        System.out.println("Number of symbols: " + encodingText.length());
    }

    private static double searchEntropy(Map<String, Double> probMap) {
        double entropy = 0; //entropy
        for (Map.Entry<String, Double> block : probMap.entrySet()) {
            double p = block.getValue();
            entropy += p * Math.log(p) / Math.log(2);
        }
        return -1 * entropy;
    }

    private static double searchEfficiency(Map<String, String> codes, Map<String, Double> probabilityMap) {
        double average = 0; // =sum(p(i)*lenght(i))
        for (Map.Entry<String, String> block : codes.entrySet()) {
            average += block.getValue().length() * probabilityMap.get(block.getKey());
        }
        return average;
    }

    private static double searchLowerBoundForBinaryCoding(double entropy) {   // LB = H(A)*log(2)
        return entropy;
    }
}
