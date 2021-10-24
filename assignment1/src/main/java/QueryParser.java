import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.Query;

public class QueryParser {

    // parse cran.qry
    private MultiFieldQueryParser parser;

    public QueryParser(Analyzer queryAnalyzer) {
        parser = new MultiFieldQueryParser(new String[] { "title", "author", "bibliography", "contents" },
                queryAnalyzer);
    }

    public Query parseQuery(String queryLine) throws ParseException {
        queryLine.trim();
        return parser.parse(queryLine);
    }

    public ArrayList<String> readFile(String input_file) {
        BufferedReader br = null;
        String line;
        String queryLine = "";
        ArrayList<String> strings = new ArrayList();
        int count = 1;

        try {
            br = new BufferedReader(new FileReader(new File(input_file)));
            br.readLine();
            line = br.readLine();
            while (line != null) {

                if (line.startsWith(".I")) {

                    System.out.println("line: " + count + ": " + queryLine);
                    count++;
                    strings.add(queryLine);

                } else if (line.startsWith(".W")) {

                    queryLine = "";

                } else {

                    if (line.contains("?") || line.contains("*")) {

                        line = line.replace("?", "");
                        line = line.replace("*", "");

                    }
                    queryLine += line + " ";
                }

                line = br.readLine();
                if (line == null) {

                    strings.add(queryLine); // add last query
                    System.out.println("line: " + count + ": " + queryLine);

                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null)
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
        return strings;
    }
}
