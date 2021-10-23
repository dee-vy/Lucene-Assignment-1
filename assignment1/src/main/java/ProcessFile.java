import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.en.EnglishAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.FieldType;

public class ProcessFile {
    private Analyzer analyzer;

    public ProcessFile(String index_dir) throws IOException {
        this.analyzer = new StandardAnalyzer(EnglishAnalyzer.getDefaultStopSet());
    }

    // parse cran.all.1400
    public void processFile(String input_file, String output_directory, Directory dir) throws IOException {
        BufferedReader bufferedReader = null;
        String line;
        String data = "";
        Integer counter = 1;
        Document doc = new Document();
        FieldType field_type = new FieldType(TextField.TYPE_STORED);
        IndexWriterConfig iwconfig = new IndexWriterConfig(analyzer);
        iwconfig.setOpenMode(IndexWriterConfig.OpenMode.CREATE);
        IndexWriter iwriter = new IndexWriter(dir, iwconfig);
        try {
            bufferedReader = new BufferedReader(new FileReader(new File(input_file)));
            bufferedReader.readLine();
            while ((line = bufferedReader.readLine()) != null) {
                if (line.startsWith(".I")) {
                    doc = new Document();
                    doc.add(new StringField("file_number", line.substring(3), Field.Store.YES));
                    counter++;

                } else if (line.startsWith(".T")) {
                    doc.add(new Field("title", data, field_type));
                    data = "";
                } else if (line.startsWith(".A")) {
                    doc.add(new Field("author", data, field_type));
                    data = "";
                } else if (line.startsWith(".B")) {
                    doc.add(new Field("bibliography", data, field_type));
                    data = "";
                } else if (line.startsWith(".W")) {
                    doc.add(new Field("contents", data, field_type));
                    iwriter.addDocument(doc);
                } else {
                    data += line + " ";
                }

            }
            bufferedReader.close();
            iwriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
