package search;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Query {

    String query = null;
    String[] massQuery;


    public void talkWithUser() throws IOException {


        Scanner scn = new Scanner(System.in);

        System.out.println("Input query:");

        while (true) {

            if (scn.hasNextLine()) {

                query = scn.nextLine().toLowerCase();
                massQuery = query.split(" ");

                IndexSearch ind = new IndexSearch();
                ArrayList<SearchResult> results = ind.fullIndexSearch(massQuery);
                System.out.println(Serp.create(query, results));

                results = ind.freqIndexSearch(massQuery);
                System.out.println(Serp.create(query, results));

            } else break;

        }

    }


    public static void main(String args[]) throws IOException {
        Query q = new Query();
        q.talkWithUser();


    }
}
