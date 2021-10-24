import java.io.IOException;
import java.util.ArrayList;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.store.Directory;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.similarities.BM25Similarity;
import org.apache.lucene.search.similarities.ClassicSimilarity;
import org.apache.lucene.search.IndexSearcher;

public class scoringResults {
    public static int VSM;
    public static int BM_25;
    public static Directory indexDirectory;
    public static QueryParser query_parser;

    public static int x;

    public scoringResults(int vsm, int bM_25, Directory inDirectory, QueryParser qp, int x) {
        this.VSM = vsm;
        this.BM_25 = bM_25;
        this.indexDirectory = inDirectory;
        this.query_parser = qp;
        this.x = x;
    }

    public static void scoringDocs(int type, ArrayList<String> querystrings) throws IOException, ParseException {
        DirectoryReader ireader = DirectoryReader.open(indexDirectory);
        IndexSearcher indexSearch = new IndexSearcher(ireader);
        Query query;
        int queryIndex = 1;
        File resultsDir = null;
        if (type == VSM) {
            indexSearch.setSimilarity(new ClassicSimilarity());
            if (x == 0) {
                resultsDir = new File("VSM_results_StandardEnglish.txt");
            }
            if (x == 1) {
                resultsDir = new File("VSM_results_Whitespace.txt");
            }
            if (x == 2) {
                resultsDir = new File("VSM_results_Simple.txt");
            }
            if (x == 3) {
                resultsDir = new File("VSM_results_Classic.txt");
            }

        } else if (type == BM_25) {
            indexSearch.setSimilarity(new BM25Similarity());
            if (x == 0) {
                resultsDir = new File("BM25_results_StandardEnglish.txt");
            }
            if (x == 1) {
                resultsDir = new File("BM25_results_Whitespace.txt");
            }
            if (x == 2) {
                resultsDir = new File("BM25_results_Simple.txt");
            }
            if (x == 3) {
                resultsDir = new File("BM25_results_Classic.txt");
            }

        } else {
            System.out.println("Invalid scoring entry");
        }
        BufferedWriter bw = new BufferedWriter(new FileWriter(resultsDir));
        try {

            for (String querystring : querystrings) {
                query = query_parser.parseQuery(querystring);

                ScoreDoc[] hits = indexSearch.search(query, 1400).scoreDocs;

                // write output results to file
                outputFile(hits, indexSearch, queryIndex, type, bw);

                queryIndex++;
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                bw.close();
            } catch (Exception e) {

            }
        }

    }

    public static void outputFile(ScoreDoc[] hits, IndexSearcher indexSearch, int queryIndex, int type,
            BufferedWriter bw) throws IOException {

        for (int i = 0; i < hits.length; i++) {

            Document hitsPerDoc = indexSearch.doc(hits[i].doc);
            String output = (queryIndex) + " Q0 " + hitsPerDoc.get("file_number") + " " + (i + 1) + " " + hits[i].score
                    + " STANDARD" + " \n";
            bw.write(output);

        }
        if (queryIndex == 225) {
            bw.close();
        }
    }

}
