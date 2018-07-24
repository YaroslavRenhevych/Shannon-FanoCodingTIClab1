package ticlab1;

import java.util.HashMap;
import java.util.Map;

public class BlocksSearcher {
    HashMap<String,Integer> createNumMap(MesSource source, int block_size)
  {     
      String text = source.getText();
      HashMap<String,Integer> map = new HashMap<String,Integer>();
      String block;
      for (int i = 0; i < text.length(); i+=block_size) {
          block = text.substring(i, i+block_size);
          if(!map.containsKey(block))
          {
              map.put(block, 1);
          }else{
             map.replace(block, map.get(block)+1);
          }
      }
      return map;
  }
    
    HashMap<String,Double> createProbMap(MesSource source, int block_size)
    {
        HashMap<String,Integer> map = createNumMap(source, block_size);
        
        HashMap<String, Double> prMap = new HashMap<String, Double>();
      
      for(String block:map.keySet())
      {
          prMap.put(block, ((double)map.get(block) / ((double)source.getText().length()/(double)block_size)));
      }
      return prMap;
    }
    
    HashMap<String,Double> createAmounOfInfoMap(MesSource source, int block_size)
    {
        HashMap<String,Integer> nmap = createNumMap(source, block_size);
        HashMap<String,Double> pmap = createProbMap(source, block_size);
        
        HashMap<String,Double> aofMap= new HashMap<String, Double>();
        for(String block:nmap.keySet())
        {
            aofMap.put(block, -1.0 * nmap.get(block) * pmap.get(block) * (Math.log(pmap.get(block))*Math.log(2)) );
        }
        return aofMap;
    }
}
