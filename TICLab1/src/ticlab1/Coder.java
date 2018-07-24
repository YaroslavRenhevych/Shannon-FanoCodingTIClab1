package ticlab1;
import java.util.HashMap;
import java.util.Map;
import java.util.LinkedList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
public class Coder {
    public HashMap<String,String> ShannonFanoCoding(Map<String,Double> pmap)
    {
        LinkedList<Message> msgs = new LinkedList<Message>();
        for(String block: pmap.keySet())
        {
            msgs.add(new Message(block,pmap.get(block)));
        }
        Collections.sort(msgs, new Comparator<Message>(){
            @Override
            public int compare(Message o1, Message o2) {
                if(o1.pr<o2.pr)
                    return 1;
                else return -1;
            }  
        });
        
        recursCoding(msgs);
        HashMap<String,String> codeMap = new HashMap<String,String>();
        for(Message ms:msgs)
        {
            codeMap.put(ms.symbol, ms.code);
        }
        return codeMap;
    }
    private void recursCoding(List<Message> msgs)
    {
        double halfOfSum = 0;
        for (int i = 0; i < msgs.size(); i++) {
            halfOfSum+=msgs.get(i).pr/2;
        }
        int indexOfMinDelta = 0;
        double currentProbSum = msgs.get(0).pr;
        double minSum = currentProbSum;
        for (int i = 1; i < msgs.size(); i++) {
            currentProbSum+=msgs.get(i).pr;
            if(Math.abs(halfOfSum-currentProbSum) < Math.abs(halfOfSum-minSum)){
                
                indexOfMinDelta = i; 
                minSum = currentProbSum;
            }
        }
        for (int l = 0; l <= indexOfMinDelta; l++)
        {
            msgs.get(l).code+="0";    
        }
        for (int r = indexOfMinDelta+1; r < msgs.size(); r++){
            msgs.get(r).code+="1";
        }
        if(indexOfMinDelta>0)
            recursCoding(msgs.subList(0, indexOfMinDelta+1));
        if(indexOfMinDelta<msgs.size()-2)
            recursCoding(msgs.subList(indexOfMinDelta+1, msgs.size()));
    }
    public String textCoding(String text,HashMap<String,String> encoding)
    {
        String code = "";
        
        int block_size = 1;
        for(String block:encoding.keySet())
        {
            block_size = block.length();
            break;
        }
        for (int i = 0; i < text.length(); i+=block_size) {
            code+=encoding.get(text.substring(i, i+block_size));
        }
        return code;
    }
}
