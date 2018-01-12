package search;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/* Search engine result page*/

public class Serp {

    static final int MAX = 3;

    public static String create(String query, ArrayList<SearchResult> results) throws IOException {

        StringBuffer bf = new StringBuffer();
        StringBuffer html = new StringBuffer();

        html.append("<html>");
        html.append("<head>");
        html.append("<title>");
        html.append("Search result for query: " + query);
        html.append("</title>");
        html.append("</head>");
        html.append("<body>");


        if (results.isEmpty()) {
            html.append("No results for query: " + query);
            return "\tNo results for query: " + query;
        }

        Collections.sort(results, new Comparator<SearchResult>() {
            @Override
            public int compare(SearchResult left, SearchResult right) {
                return left.score > right.score ? -1 : (left.score < right.score) ? 1 : 0;
            }
        });

        List<SearchResult> res;
        int totalResults = results.size();
        if (results.size() >= MAX) {
            res = results.subList(0, MAX);
        } else {
            res = results;
        }
        html.append("<p><i>For your query found " + totalResults + "</i></p>");
        bf.append("\tFor your query found " + totalResults + ", showing " + res.size() + "\n");

        for (int i = 0; i < res.size(); i++) {

            bf.append(i + ". (" + res.get(i).score + ") " + res.get(i).url + ": " + res.get(i).header + "\n");
        }

        html.append("<ol>");
        for (int i = 0; i < results.size(); i++) {

            html.append("<li>");
            html.append(" (" + results.get(i).score + ") ");
            if(results.get(i).header != null) {
                html.append("<a href=" + results.get(i).url + ">" + results.get(i).header + "</a>");
            }else {
                html.append("<a href=" + results.get(i).url + ">" + results.get(i).url + "</a>");
            }
            html.append("</li>");
        }
        html.append("</ol>");

        html.append("</body>");
        html.append("</html>");

        File tempFile = File.createTempFile("results", ".html");
        FileWriter file = new FileWriter(tempFile);
        file.write(html.toString());
        file.close();

        bf.append("Html results: " + tempFile.getAbsolutePath() + "\n");

        return bf.toString();
    }
}

