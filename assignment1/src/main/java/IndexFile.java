import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.en.EnglishAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

public class IndexFile {

    // Directory to save the search index
    public static String indexDirectory = "index";
    public static String cranDirectory = "cranfield";
    public static String queryDirectory = "query";

    // two methods taken for scoring:- <types>
    public static int VSM = 0;
    public static int BM_25 = 1;

    private static Directory index_directory;
    private static Directory query_directory;
    private static Analyzer query_analyzer;
    private static QueryParser query_parser;

    public static void main(String[] args) throws IOException, ParseException {
        ArrayList<String> querystrings = new ArrayList<String>();
        query_analyzer = new StandardAnalyzer(EnglishAnalyzer.getDefaultStopSet());

        index_directory = FSDirectory.open(Paths.get(indexDirectory));
        query_directory = FSDirectory.open(Paths.get(queryDirectory));
        // create index for Cranfield corpus
        ProcessFile parse_doc = new ProcessFile(indexDirectory);
        parse_doc.processFile(cranDirectory + "/cran.all.1400", indexDirectory, index_directory);
        // create index for the Cranfield Queries
        query_parser = new QueryParser(query_analyzer);
        querystrings = query_parser.readFile(cranDirectory + "/cran.qry");

        new scoringResults(VSM, BM_25, index_directory, query_parser);
        // Comment one while using another Score:-
        scoringResults.scoringDocs(BM_25, querystrings);
        // scoringResults.scoringDocs(VSM, querystrings);

        shutdown();
    }

    private static void shutdown() throws IOException {
        index_directory.close();
        query_directory.close();
    }

}
