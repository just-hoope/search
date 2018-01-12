package spider;

import java.util.*;

public class IndexerUtil {

    public static String cutTags(String content) {

        boolean insideTag = false;
        boolean insideScript = false;
        boolean insideStyle = false;


        StringBuffer bf = new StringBuffer();

        for (int i = 0; i < content.length(); i++) {

            if (content.charAt(i) == '<') {
                insideTag = true;
            }
            if ((content.length() > i + 7) && (content.substring(i, i + 7).equals("<script"))) {
                insideScript = true;
            }
            if ((content.length() > i + 6) && (content.substring(i, i + 6).equals("<style"))){
                insideStyle = true;
            }
            if ((insideTag == false) && (insideScript == false) && (insideStyle == false)) {
                bf.append(content.charAt(i));
            }
            if (content.charAt(i) == '>') {
                insideTag = false;
            }

            if ((content.length() > i + 8) && (content.substring(i, i + 8).equals("</style>"))) {
                insideStyle = false;
            }
            if ((content.length() > i + 9) && (content.substring(i, i + 9).equals("</script>"))) {
                insideScript = false;
            }
        }
        return bf.toString();
    }

    public static String removeEmptyLines(String content){

        StringBuffer bf = new StringBuffer();

        String[] splitted = content.split("\n");

        for(int i = 0; i < splitted.length; i++){

            if(!splitted[i].trim().isEmpty()){
                bf.append(splitted[i].trim() + "\n");
            }
        }

        return bf.toString();
    }

    public static String replaceSymbols(String content) {
        return content.replace("&nbsp;", " ");

    }

    public static List<Map.Entry<String, Integer>> getKeyWords(String content){

        HashMap<String, Integer> freq = new HashMap<>();

        String[] result = content.split("[^a-zA-Z0-9']+");

        Set<String> prepositions = IndexerUtil.getPrepositions();

        for(int i = 0; i < result.length; i++) {


            if(!prepositions.contains(result[i])) {

                if (!freq.containsKey(result[i])) {
                    freq.put(result[i], 1);
                } else {
                    int n = freq.get(result[i]);
                    freq.put(result[i], n + 1);
                }

            }

        }

        ArrayList<Map.Entry<String, Integer>> array = new ArrayList<Map.Entry<String,Integer>>(freq.entrySet());
        Collections.sort(array, new Comparator<Map.Entry<String, Integer>>() {
            @Override
            public int compare(Map.Entry<String, Integer> left, Map.Entry<String, Integer> right) {
                return left.getValue() > right.getValue() ? -1 : (left.getValue() < right.getValue()) ? 1 : 0;
            }
        });

        List<Map.Entry<String, Integer>> res;
        int totalResults = array.size();
        if (array.size() >= 5) {
            res = array.subList(0, 5);
        } else {
            res = array;
        }

        return res;
    }

    public static Set<String> getPrepositions(){

        Set<String> prepositions = new HashSet<>();

        prepositions.add("aboard");
        prepositions.add("about");
        prepositions.add("above");
        prepositions.add("across");
        prepositions.add("after");
        prepositions.add("against");
        prepositions.add("along");
        prepositions.add("amid");
        prepositions.add("among");
        prepositions.add("anti");
        prepositions.add("around");
        prepositions.add("as");
        prepositions.add("at");
        prepositions.add("before");
        prepositions.add("behind");
        prepositions.add("below");
        prepositions.add("beneath");
        prepositions.add("beside");
        prepositions.add("besides");
        prepositions.add("between");
        prepositions.add("beyond");
        prepositions.add("but");
        prepositions.add("by");
        prepositions.add("concerning");
        prepositions.add("considering");
        prepositions.add("despite");
        prepositions.add("down");
        prepositions.add("during");
        prepositions.add("except");
        prepositions.add("excepting");
        prepositions.add("excluding");
        prepositions.add("following");
        prepositions.add("for");
        prepositions.add("from");
        prepositions.add("in");
        prepositions.add("inside");
        prepositions.add("into");
        prepositions.add("like");
        prepositions.add("minus");
        prepositions.add("near");
        prepositions.add("of");
        prepositions.add("off");
        prepositions.add("on");
        prepositions.add("onto");
        prepositions.add("opposite");
        prepositions.add("outside");
        prepositions.add("over");
        prepositions.add("past");
        prepositions.add("per");
        prepositions.add("plus");
        prepositions.add("regarding");
        prepositions.add("round");
        prepositions.add("save");
        prepositions.add("since");
        prepositions.add("than");
        prepositions.add("through");
        prepositions.add("to");
        prepositions.add("toward");
        prepositions.add("towards");
        prepositions.add("under");
        prepositions.add("underneath");
        prepositions.add("unlike");
        prepositions.add("until");
        prepositions.add("up");
        prepositions.add("upon");
        prepositions.add("versus");
        prepositions.add("via");
        prepositions.add("with");
        prepositions.add("within");
        prepositions.add("without");
        prepositions.add("a");
        prepositions.add("the");

        return prepositions;

    }
}
