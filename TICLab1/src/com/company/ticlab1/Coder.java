package com.company.ticlab1;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Coder {
    public Map<String, String> shannonFanoCoding(Map<String, Double> pMap) {
        LinkedList<Message> msgs = new LinkedList<>();
        for (Map.Entry<String, Double> block : pMap.entrySet()) {
            msgs.add(new Message(block.getKey(), block.getValue()));
        }
//        not correct sorting when will be equals
//        Collections.sort(msgs, new Comparator<Message>(){
//            @Override
//            public int compare(Message o1, Message o2) {
//                if(o1.pr<o2.pr)
//                    return 1;
//                else return -1;
//            }
//        });
        msgs.sort((o1, o2) -> Double.compare(o2.getPr(), o1.getPr()));

        recursCoding(msgs);
        HashMap<String, String> codeMap = new HashMap<>();
        for (Message ms : msgs) {
            codeMap.put(ms.getSymbol(), ms.getCode());
        }
        return codeMap;
    }

    private void recursCoding(List<Message> msgs) {
        double halfOfSum = 0;
        for (Message msg : msgs) {
            halfOfSum += msg.getPr() / 2;
        }
        int indexOfMinDelta = 0;
        double currentProbSum = msgs.get(0).getPr();
        double minSum = currentProbSum;
        for (int i = 1; i < msgs.size(); i++) {
            currentProbSum += msgs.get(i).getPr();
            if (Math.abs(halfOfSum - currentProbSum) < Math.abs(halfOfSum - minSum)) {
                indexOfMinDelta = i;
                minSum = currentProbSum;
            }
        }
        for (int l = 0; l <= indexOfMinDelta; l++) {
            Message message = msgs.get(l);
            message.setCode(message.getCode() + "0");
        }
        for (int r = indexOfMinDelta + 1; r < msgs.size(); r++) {
            Message message = msgs.get(r);
            message.setCode(message.getCode() + "1");
        }
        if (indexOfMinDelta > 0) {
            recursCoding(msgs.subList(0, indexOfMinDelta + 1));
        }
        if (indexOfMinDelta < msgs.size() - 2) {
            recursCoding(msgs.subList(indexOfMinDelta + 1, msgs.size()));
        }
    }

    public String textCoding(String text, Map<String, String> encoding) {
        StringBuilder code = new StringBuilder();

        int blockSize = encoding.keySet().stream()
                .findFirst()
                .orElse("")
                .length();
        for (int i = 0; i < text.length(); i += blockSize) {
            code.append(encoding.get(text.substring(i, i + blockSize)));
        }
        return code.toString();
    }
}
