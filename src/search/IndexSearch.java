package search;

import spider.Constants;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class IndexSearch {

    int countTxt;
    String fileName;


    public ArrayList<SearchResult> fullIndexSearch(String[] massQuery) throws FileNotFoundException, IOException {

        ArrayList<SearchResult> results = new ArrayList<>();
        File folder = new File(Constants.fullIndexPath);
        File[] listOfFiles = folder.listFiles();

        for (int i = 0; i < listOfFiles.length; i++) {

            String url;
            String header;
            int countWord = 0;
            //fileName = "/home/nadezhda/IdeaProjects/search/index" + "/" + countTxt + ".txt";
            File file = (listOfFiles[i]);
            Scanner scn = new Scanner(file);
            //System.out.println(fileName);
            try {

                url = scn.nextLine();

                header = scn.nextLine();

                countWord += getLineScore(header, massQuery) * 100;

                while (scn.hasNextLine()) {

                    String line = scn.nextLine();

                    countWord += getLineScore(line, massQuery);
                }

                if (countWord > 0) {

                    //System.out.println(header + ":" + countWord);

                    SearchResult sr = new SearchResult(countWord, file.getName(), url, header);
                    results.add(sr);
                }

            } finally {
                scn.close();
                countTxt++;
            }

        }
        return results;
    }


    public ArrayList<SearchResult> freqIndexSearch(String[] massQuery) throws FileNotFoundException, IOException {

        ArrayList<SearchResult> results = new ArrayList<>();
        File folder = new File(Constants.freqIndexPath);
        File[] listOfFiles = folder.listFiles();

        for (int i = 0; i < listOfFiles.length; i++) {

            String url;
            int countWord = 0;
            //fileName = "/home/nadezhda/IdeaProjects/search/index" + "/" + countTxt + ".txt";
            File file = (listOfFiles[i]);
            Scanner scn = new Scanner(file);
            //System.out.println(fileName);
            try {

                url = scn.nextLine();

                    String line = scn.nextLine();

                    countWord += getLineScore(line, massQuery);

                if (countWord > 0) {

                    //System.out.println(header + ":" + countWord);

                    SearchResult sr = new SearchResult(countWord, file.getName(), url, null);
                    results.add(sr);
                }

            } finally {
                scn.close();
                countTxt++;
            }

        }
        return results;
    }

    public int getLineScore(String line, String[] massQuery){

        int countWord = 0;
        int score = 0;

        for (int j = 0; j < massQuery.length; j++) {
            if (line.contains(massQuery[j])) {
                // System.out.println(fileName + ":" + line);
                countWord++;
                score += countWord;
            }

        }

        //return countWord;
        return score;
    }
}
