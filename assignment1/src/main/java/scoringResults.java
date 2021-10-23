import java.io.IOException;
// import java.nio.charset.Charset;
// import java.nio.file.Files;
// import java.nio.file.Paths;
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

    public scoringResults(int vsm, int bM_25, Directory inDirectory, QueryParser qp) {
        this.VSM = vsm;
        this.BM_25 = bM_25;
        this.indexDirectory = inDirectory;
        this.query_parser = qp;
    }

    public static void scoringDocs(int type, ArrayList<String> querystrings) throws IOException, ParseException {
        int maxResults = 20;
        DirectoryReader ireader = DirectoryReader.open(indexDirectory);
        IndexSearcher indexSearch = new IndexSearcher(ireader);
        Query query;
        int queryIndex = 1;
        File resultsDir = null;
        if (type == VSM) {
            indexSearch.setSimilarity(new ClassicSimilarity());
            resultsDir = new File("VSM_results.txt"); // Results/VSM/
            // if (!resultsDir.exists())
            //     resultsDir.mkdirs();
            // Files.write(Paths.get("Results/vsm_output.txt"), Charset.forName("UTF-8"));

        } else if (type == BM_25) {
            indexSearch.setSimilarity(new BM25Similarity());
            resultsDir = new File("BM25_results.txt"); // Results/BM25/
            // if (!resultsDir.exists())
            //     resultsDir.mkdirs();
        } else {
            System.out.println("Invalid scoring entry");
        }
        BufferedWriter bw = new BufferedWriter(new FileWriter(resultsDir));
        try {
            for (String querystring : querystrings) {
                query = query_parser.parseQuery(querystring);

                ScoreDoc[] hits = indexSearch.search(query, maxResults).scoreDocs;

                // write results to file for eval
                writeResultsFile(hits, indexSearch, queryIndex, type, bw);

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

    public static void writeResultsFile(ScoreDoc[] hits, IndexSearcher indexSearch, int queryIndex, int type,
            BufferedWriter bw) throws IOException {

        for (int i = 0; i < hits.length; i++) {
            Document hitDoc = indexSearch.doc(hits[i].doc);
            String line = (queryIndex) + " Q0 " + hitDoc.get("file_number") + " " + (i + 1) + " " + hits[i].score
                    + " STANDARD" + " \n";
            bw.write(line);
            // System.out.println(line);
        }

        if (queryIndex == 225) {
            bw.close();
        }
    }

}
