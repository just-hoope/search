package spider;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public class Indexer {

    private String workDir;
    private String frqWorkDir;
    private ExecutorService pool;
    private int countTxt;
    private HashMap<String, String> files;
    private static final Logger LOGGER = Logger.getLogger( Indexer.class.getName() );

    public Indexer(String workDir, String frqWorkDir) {

        this.workDir = workDir;
        this.frqWorkDir = frqWorkDir;
        pool = Executors.newFixedThreadPool(1);
        countTxt = 0;
        files = new HashMap<>();
    }

    public class IndexerTask implements Runnable {

        String url;
        String content;

        public IndexerTask(String url, String content) {

            this.content = content;
            this.url = url;

        }

        public void run() {

            LOGGER.info("Indexing " + url);

            String cuttedContent = IndexerUtil.cutTags(content);
            cuttedContent = IndexerUtil.replaceSymbols(cuttedContent);
            cuttedContent = cuttedContent.toLowerCase();
            cuttedContent = IndexerUtil.removeEmptyLines(cuttedContent);

            String fileName = workDir + "/" + countTxt + ".txt";
            files.put(url, fileName);

            try {
                FileWriter file = new FileWriter(new File(fileName));
                file.write(url + "\n");
                file.write(cuttedContent);
                file.close();
                LOGGER.info("Saved " + url + " to " + fileName);
            } catch (Exception e) {
                e.printStackTrace();
            }


            List<Map.Entry<String, Integer>> frq = IndexerUtil.getKeyWords(cuttedContent);
            fileName = frqWorkDir + "/" + countTxt + ".txt";
            try {
                FileWriter file = new FileWriter(new File(fileName));
                file.write(url + "\n");
                for (int i = 0; i < frq.size(); i++) {
                    file.write(frq.get(i).getKey() + " ");
                }
                file.close();
                LOGGER.info("Saved " + url + " to " + fileName);
            } catch (Exception e) {
                e.printStackTrace();
            }
            countTxt++;
        }

    }


    public void processDoc(String url, String content) {

        IndexerTask task = new IndexerTask(url, content);
        pool.execute(task);

    }

    public void stop() throws InterruptedException, IOException {
        pool.shutdown();
        pool.awaitTermination(100, TimeUnit.SECONDS);
        Thread.sleep(1000);
    }

}
