import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.en.EnglishAnalyzer;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

public class IndexFile {

    public static String indexDirectory = "index";
    public static String cranDirectory = "cranfield";
    public static String queryDirectory = "query";

    public static int VSM = 0;
    public static int BM_25 = 1;

    private static Directory index_directory;
    private static Directory query_directory;
    private static Analyzer query_analyzer;
    private static QueryParser query_parser;

    public static void main(String[] args) throws IOException, ParseException {
        ArrayList<String> querystrings = new ArrayList<String>();
        query_analyzer = new EnglishAnalyzer();

        index_directory = FSDirectory.open(Paths.get(indexDirectory));
        query_directory = FSDirectory.open(Paths.get(queryDirectory));

        ProcessFile parse_doc = new ProcessFile(indexDirectory);
        parse_doc.processFile(cranDirectory + "/cran.all.1400", indexDirectory, index_directory);
        query_parser = new QueryParser(query_analyzer);
        querystrings = query_parser.readFile(cranDirectory + "/cran.qry");

        new scoringResults(VSM, BM_25, index_directory, query_parser);
        // Comment one while using another Score:-
        // scoringResults.scoringDocs(BM_25, querystrings);
        scoringResults.scoringDocs(VSM, querystrings);

        shutdown();
    }

    private static void shutdown() throws IOException {
        index_directory.close();
        query_directory.close();
    }

}
