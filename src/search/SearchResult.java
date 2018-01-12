package search;

public class SearchResult {

    int score;
    String fileName;
    String url;
    String header;

    public SearchResult(int score, String fileName, String url, String header){

        this.fileName = fileName;
        this.score = score;
        this.url = url;
        this.header = header;

    }
}
