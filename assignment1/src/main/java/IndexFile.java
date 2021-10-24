import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.CharArraySet;
import org.apache.lucene.analysis.core.SimpleAnalyzer;
import org.apache.lucene.analysis.core.StopAnalyzer;
import org.apache.lucene.analysis.core.WhitespaceAnalyzer;
import org.apache.lucene.analysis.en.EnglishAnalyzer;
import org.apache.lucene.analysis.standard.ClassicAnalyzer;
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
    private static Analyzer textAnalyzer;
    private static QueryParser query_parser;

    public static int x;
    public static CharArraySet stopWords;

    public static void main(String[] args) throws IOException, ParseException {
        ArrayList<String> querystrings = new ArrayList<String>();

        for (int i = 0; i < 4; i++) {
            if (i == 0) {
                textAnalyzer = new StandardAnalyzer(EnglishAnalyzer.getDefaultStopSet());
                x = 0;
            }
            if (i == 1) {
                textAnalyzer = new WhitespaceAnalyzer();
                x = 1;
            }
            if (i == 2) {
                textAnalyzer = new SimpleAnalyzer();
                x = 2;
            }
            if (i == 3) {
                textAnalyzer = new ClassicAnalyzer();
                x = 3;
            }
            System.out.println(textAnalyzer);
            index_directory = FSDirectory.open(Paths.get(indexDirectory));
            query_directory = FSDirectory.open(Paths.get(queryDirectory));

            // create index for Cranfield corpus
            ProcessFile parse_doc = new ProcessFile(indexDirectory, textAnalyzer);
            parse_doc.processFile(cranDirectory + "/cran.all.1400", indexDirectory, index_directory);

            // create index for the Cranfield Queries
            query_parser = new QueryParser(textAnalyzer);
            querystrings = query_parser.readFile(cranDirectory + "/cran.qry");

            new scoringResults(VSM, BM_25, index_directory, query_parser,x);
            // Comment one while using another Score:-
            scoringResults.scoringDocs(BM_25, querystrings);
            scoringResults.scoringDocs(VSM, querystrings);

        }

        shutdown();
    }

    private static void shutdown() throws IOException {
        index_directory.close();
        query_directory.close();
    }

}
