package ticlab1;
import java.io.File;
import java.util.HashMap;
public class MainClass {

    public static void main(String[] args){
        BlocksSearcher bsearch = new BlocksSearcher();
        Coder coder = new Coder();
        File file = new File("text.txt");
        MesSource src = new MesSource(file);
        System.out.println("Послідовність повідомлень джерела:\n"+src.getText());        
        System.out.println("Number of messages: "+src.getText().length());
        System.out.println("Number of unique messages: "+bsearch.createNumMap(src, 1).size());
        
        
        HashMap<String,Integer> msgNumberMap = bsearch.createNumMap(src, 1);
        HashMap<String,Double> msgProbabilityMap = bsearch.createProbMap(src, 1);
        HashMap<String,Double> msgAmounOfInfoMap = bsearch.createAmounOfInfoMap(src, 1);
        HashMap<String,String> msgCodeMap = coder.ShannonFanoCoding(msgProbabilityMap);
        String encodingText = coder.textCoding(src.getText(), msgCodeMap);
        for(String msg:msgNumberMap.keySet())
        {
            System.out.printf("%s   %2d   %.3f   %.3f   %s\n",msg,msgNumberMap.get(msg),msgProbabilityMap.get(msg),msgAmounOfInfoMap.get(msg),msgCodeMap.get(msg));
        }
        System.out.println("Encoding text: "+encodingText);
        System.out.println("Number of symbols: "+encodingText.length());
        System.out.println("Source entropy: "+searchEntropy(msgProbabilityMap));
        double msgCodingEff = searchEfficiency(msgCodeMap,msgProbabilityMap);
        System.out.println("Coding Efficiency: "+msgCodingEff);
        double msgCodingLB = searchLowerBoundForBinaryCoding(searchEntropy(msgProbabilityMap));
        System.out.println("Lower Bound: "+msgCodingLB);
        System.out.println("-------------Block coding-----------");
        
        HashMap<String,Integer> blockNumberMap = bsearch.createNumMap(src, 2);
        HashMap<String,Double> blockProbabilityMap = bsearch.createProbMap(src, 2);
        HashMap<String,Double> blockAmounOfInfoMap = bsearch.createAmounOfInfoMap(src, 2);
        HashMap<String,String> blockCodeMap = coder.ShannonFanoCoding(blockProbabilityMap);
        encodingText = coder.textCoding(src.getText(), blockCodeMap);
        for(String msg:blockNumberMap.keySet())
        {
            System.out.printf("%s   %2d   %.3f   %.3f   %s\n",msg,blockNumberMap.get(msg),blockProbabilityMap.get(msg),blockAmounOfInfoMap.get(msg),blockCodeMap.get(msg));
        }
        System.out.println("Encoding text: "+encodingText);
        System.out.println("Number of symbols: "+encodingText.length());
        double blockCodingEff = searchEfficiency(blockCodeMap,blockProbabilityMap);
        System.out.println("Block coding Efficiency: "+blockCodingEff);
        double blockCodingLB = searchLowerBoundForBinaryCoding(searchEntropy(blockProbabilityMap));
        System.out.println("Lower Bound for block coding: "+blockCodingLB);
        System.out.println("-------------------------------------");
        
        System.out.println("Difference between messege coding and block coding efficiencys: "+(msgCodingEff-blockCodingEff));
        System.out.println("Difference between messege coding and block coding lower bounds: "+(msgCodingLB-blockCodingLB));
    }
    static public double searchEntropy(HashMap<String,Double> probMap)
    {
        double entrp = 0; //entropy
        double p = 0; //probability
        for(String block: probMap.keySet())
        {
            p = probMap.get(block);
            entrp += p * Math.log(p)/Math.log(2);
        }
        return -1*entrp;
    }
    static public double searchEfficiency(HashMap<String,String> codes,HashMap<String,Double> probs)
    {   
        double average = 0; // =sum(p(i)*lenght(i))
        for(String block:codes.keySet())
        {
            average+=(double)codes.get(block).length()*probs.get(block);
        }
        return average;
    }
    static public double searchLowerBoundForBinaryCoding(double entropy)
    {   // LB = H(A)*log(2)
        return entropy*(Math.log(2)/Math.log(2));
    }
    
}
