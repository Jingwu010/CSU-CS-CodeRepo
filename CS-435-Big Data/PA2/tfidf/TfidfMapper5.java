package tfidf;

import java.io.IOException;
import java.lang.StringBuffer;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Map;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.ArrayList;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;

/*
    Input ({DocumentID, unigram}, TF-IDFvalue}) 
    Output – (DocumentID, {eachSentence, SentenceTF-IDF})
 */
public class TfidfMapper5 extends Mapper<Object, Text, Text, Text>{

    private Map<String, Double> tokensValue = new HashMap<String, Double>();
    private final static String wordbank = "abcdefghijklmnopqrstuvwxyz1234567890";

    @Override
    protected void setup(Context context) throws IOException, InterruptedException{
        try{
            Path[] cacheFiles = context.getLocalCacheFiles();
            if(cacheFiles != null && cacheFiles.length > 0) {
                for(Path cachefile : cacheFiles) {
                    readFile(cachefile);
                }
            }
        } catch(IOException ex) {
            System.err.println("Exception in mapper setup: " + ex.getMessage());
        }
        // for (Map.Entry<String,Double> entry : tokensValue.entrySet()) 
        //     System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
    }

    private void readFile(Path filePath) {
        try{
            String fileName = filePath.toString().substring(5);
            BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName));
            String line;
            while((line = bufferedReader.readLine()) != null) {
                String[] segs = line.split("\t");
                String key = segs[0];
                double value = Double.parseDouble(segs[1]);
                tokensValue.put(key, value);
            }
        } catch(IOException ex) {
            System.err.println("Exception while reading file: " + ex.getMessage());
        }
    }

    /*
        https://stackoverflow.com/questions/109383/sort-a-mapkey-value-by-values
     */
    private <K, V extends Comparable<? super V>> Map<K, V> sortByValue(Map<K, V> map) {
        List<Map.Entry<K, V>> list = new ArrayList<>(map.entrySet());
        list.sort((o1, o2) -> o2.getValue().compareTo(o1.getValue()));

        Map<K, V> result = new LinkedHashMap<>();
        for (Map.Entry<K, V> entry : list) {
            result.put(entry.getKey(), entry.getValue());
        }

        return result;
    }

	public void map(Object key, Text value, Context context) throws IOException, InterruptedException {

        // System.out.println("******************************");
        String line = value.toString();
        if (line.isEmpty()) return;

        String[] segs = line.split("<====>");
        // Title - DocumentID - Text
        String documentID = segs[1];
        String text = segs[2];
        String[] sentences = text.split("\\.");

        for (String sentence : text.split("\\.")) {
            if (sentence.trim().isEmpty()) continue;
            double sentence_score = 0.0;
            Map<String, Double> token_value = new HashMap<String, Double>();
            for (String word : sentence.split(" ")){
                StringBuffer sb = new StringBuffer();
                double word_score = 0.0;
                for (char c : word.toCharArray()){
                    char cur = Character.toLowerCase(c);
                    if(wordbank.indexOf(cur) != -1)
                        sb.append(cur);
                }
                if (sb.toString().isEmpty()) continue;
                if (!token_value.containsKey(sb.toString())) continue;
                word_score = tokensValue.get(documentID + "," + sb.toString());
                token_value.put(sb.toString(), word_score);
            }
            Map<String, Double> sorted_token_value = this.sortByValue(token_value);
            int flag = 0;
            for (Map.Entry<String,Double> entry : sorted_token_value.entrySet()) {
                flag += 1;
                sentence_score += entry.getValue();
                if (flag >= 5) break;
            }
            context.write(new Text(documentID), new Text(sentence + "<=>" + Double.toString(sentence_score)));
        }


        // StringBuffer sentence = new StringBuffer();
        // double sentence_score = 0.0;
        // boolean flag = false;
        // Set<String> sentence_set = new HashSet<String>();

        // for (String word : text.split(" ")){
        //     StringBuffer sb = new StringBuffer();
        //     double word_score = 0.0;
        //     for (char c : word.toCharArray()){
        //         char cur = Character.toLowerCase(c);
        //         if(cur == '.'){
        //             flag = true;
        //         }
        //         else if(wordbank.indexOf(cur) != -1)
        //             sb.append(cur);
        //     }

        //     sentence.append(word + " ");
        //     if (!sb.toString().isEmpty() && !sentence_set.contains(sb.toString())){
        //         System.out.println("key = " + documentID + "," + sb.toString());
        //         word_score = tokensValue.get(documentID + "," + sb.toString());
        //         sentence_score += word_score;
        //         sentence_set.add(sb.toString());
        //     }

        //     if (flag == true) {
        //         if (sentence.length() > 0) sentence.setLength(sentence.length() - 1);
        //         context.write(new Text(documentID), new Text(sentence.toString() + "<=>" + Double.toString(sentence_score)));
        //         sentence_score = 0.0;
        //         sentence.setLength(0);
        //         sentence_set.clear();
        //         flag = false;
        //     }
        // }
    }
}