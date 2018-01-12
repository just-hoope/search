package spider;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.*;
import java.util.logging.Logger;

public class Fetcher {

    private Indexer ind;
    private ExecutorService pool;
    private static final Logger LOGGER = Logger.getLogger( Fetcher.class.getName() );

    public Fetcher(Indexer ind, int threadCount) {
        this.ind = ind;
        pool = Executors.newFixedThreadPool(threadCount);
    }

    public class FetcherTask implements Runnable {

        String url;

        public FetcherTask(String url) {
            this.url = url;
        }

        public void run() {

            LOGGER.info("Fetching " + url);


            try {
                URL _url = new URL(url);
                HttpURLConnection connection = (HttpURLConnection) _url.openConnection();
                int code = connection.getResponseCode();
                //System.out.println(url + ":"+code);
                //connection.setRequestProperty("Accept-Charset", charset);
                LOGGER.info("Fetched " + url + " with code " + code);
                if (code == 200) {
                    InputStream response = connection.getInputStream();
                    Scanner scanner = new Scanner(response);
                    String responseBody = scanner.useDelimiter("\\A").next();
                    //System.out.println(responseBody);
                    Thread.sleep(1000);
                    ind.processDoc(url, responseBody);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }


        }

    }

    public void downloadAll(ArrayList<String> urls) throws InterruptedException, IOException {
        for (String url : urls) {
            FetcherTask task = new FetcherTask(url);
            pool.execute(task);
        }

        pool.shutdown();
        pool.awaitTermination(100, TimeUnit.SECONDS);
        ind.stop();


    }

}
