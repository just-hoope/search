package spider;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class Robot {
    public static void main(String args[]) throws Exception {

        String urlsFileName = Constants.urlsFilePath;     //args[0];

        ArrayList<String> urls = new ArrayList<>();

        File f = new File(urlsFileName);
        Scanner scn = new Scanner(f);
        while (scn.hasNextLine()) {
            String url = scn.nextLine();
            url = url.trim();
            if (!url.isEmpty()) {
                urls.add(url);
            }
        }

        Indexer ind = new Indexer(Constants.fullIndexPath, Constants.freqIndexPath);
        Fetcher ft = new Fetcher(ind, 5);
        ft.downloadAll(urls);

    }
}
