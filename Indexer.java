import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 *
 * @author Aditya
 */
public class Indexer implements Serializable {
         TreeMap<String, TreeMap<String, Integer>> LemmaMap = new TreeMap<String, TreeMap<String, Integer>>();
         TreeMap<String, Integer> lemma_freq = new TreeMap<String, Integer>();
         TreeMap<Integer, Doc_Summary> Document_summary = new TreeMap<Integer, Doc_Summary>();
         TreeMap<String, Integer> Freq_Map = new TreeMap<String, Integer>(String.CASE_INSENSITIVE_ORDER);
         TreeMap<Integer, TreeMap<String, Integer>> Doc_Map = new TreeMap<Integer, TreeMap<String, Integer>>();
         TreeMap<Integer,String> paras=new TreeMap<Integer,String>();
static String[] token_processer(String oString) {
        String s = new String(".,\'\"<>:;?&*-!$^%()/\\[]{}_|#@~`=+"), s1;
        String[] finalString = new String[2];
        String[] tempArray = new String[4];
        oString = oString.toLowerCase();
        try {
            //Trimming special characters at the ends
            while (s.indexOf(oString.charAt(0)) >= 0) {
                oString = oString.substring(1);
            }
            while (s.indexOf(oString.charAt(oString.length() - 1)) >= 0) {
                oString = oString.substring(0, oString.length() - 1);
            }

            //Handling words ending with 's
            if (oString.endsWith("\'s")) {
                oString = oString.substring(0, oString.length() - 2);
            }
            oString = oString.replace(",", "");
            oString = oString.replaceAll("�", "");
            s1 = oString.replace(".", "");
            //If a word contains '.', then remove it unless it's number (i.e decimal)
            if (!s1.matches("[0-9]+")) {
                oString = s1;
            }
            /*If a word contains '--' in the middle, then the strings at both ends
             considered as two different string*/
            finalString = oString.split("--");
            for (int i = 0; i < finalString.length; i++) {
                tempArray = finalString[i].split("-");
                finalString[i] = "";
                for (int k = 0; k < tempArray.length; k++) {
                    finalString[i] = finalString[i].concat(tempArray[k]);
                }
            }
        } catch (StringIndexOutOfBoundsException e) {
            finalString[0] = " ";
        }

        return finalString;
    }

    
 public void para_indexer(HashMap<String, Integer> pnum) {
        StanfordLemmatizer slm = new StanfordLemmatizer();
        ArrayList<Integer> v_list = new ArrayList<Integer>();
        Iterator<Map.Entry<Integer, Double>> itr1;
        Iterator<Map.Entry<Integer, Double>> itr2;
        int file_count = 0, total_doclen = 0, max_tfreq, ln = 0, l, doclen, lc = 0, tf, df, maxtf,pno,line_no;
        double w1, w2, avg_doclen;
        String[] arr = new String[100];
        FileReader f;
        BufferedReader br;
        String lemma, finalToken, s, s1, doc,para;
        String[] tokenarray = new String[5];

        ArrayList<String> stopwords = new ArrayList<String>(Arrays.asList("a", "all", "an", "and", "any", "are", "as", "be", "been", "but", "by", "few",
                "for", "have", "he", "her", "here", "him", "his", "how", "i", "in", "is", "it", "its", "many", "me", "my",
                "none", "of", "on", "or", "our", "she", "some", "the", "their", "them", "there", "they", "that", "this", "us", "was",
                "what", "when", "where", "which", "who", "why", "will", "with", "you", "your"));

        Collection<Integer> freq_set;
        TreeMap<String, Integer> Temp_Map = new TreeMap<String, Integer>(String.CASE_INSENSITIVE_ORDER);
        Doc_Summary ds;
        String path="Group4";
            try {
                /* Reading the files from the specified folder */
               
                File folder = new File(path);
               for(File subfolder : folder.listFiles()){
             if(!subfolder.isDirectory())
                 continue;

                for (File textfolder : subfolder.listFiles()) {
                    if(!textfolder.isDirectory())
                        continue;
                    for (File fileEntry : textfolder.listFiles()) {
                    max_tfreq = 0;
                    doclen = 0;
                    para = " ";
                    file_count++;
                    doc =fileEntry.getName();
                    doc=doc.replace(".txt", "");
                    if(pnum.containsKey(doc))
                        pno=pnum.get(doc);
                    else
                        continue;
                    
                        
                    f = new FileReader(textfolder + "/" + fileEntry.getName());
                    br = new BufferedReader(f);
                    line_no=0;
                    while ((s = br.readLine()) != null) {
                        para=para+"\n "+s;
                        arr = s.split(" ");
                        line_no++;
                        l = arr.length;
                        for (ln = 0; ln < l; ln++) {
                            tokenarray = token_processer(arr[ln]);
                            for (int jj = 0; jj < tokenarray.length; jj++) {
                                finalToken = tokenarray[jj];
                                if (finalToken == null || stopwords.contains(finalToken) || finalToken.length() <= 1) {
                                    continue;
                                } else {

                                    doclen++;
                                    lemma = slm.lemmatize(finalToken);
                                    if (lemma_freq.containsKey(lemma)) {
                                        lemma_freq.put(lemma, lemma_freq.get(lemma) + 1);
                                    } else {
                                        lemma_freq.put(lemma, 1);
                                    }
                                    if (Temp_Map.containsKey(lemma)) {
                                        Temp_Map.put(lemma, Temp_Map.get(lemma) + 1);
                                    } else {
                                        Temp_Map.put(lemma, 1);
                                    }
                                    if (LemmaMap.containsKey(lemma)) {
                                        Freq_Map = LemmaMap.get(lemma);
                                        if (Freq_Map.containsKey(doc)) {
                                            Freq_Map.put(doc, Freq_Map.get(doc) + 1);
                                        } else {
                                            Freq_Map.put(doc, 1);
                                        }
                                        LemmaMap.put(lemma, Freq_Map);
                                    } else {
                                        TreeMap<String, Integer> Freq_Map2 = new TreeMap<String, Integer>(String.CASE_INSENSITIVE_ORDER);
                                        Freq_Map2.put(doc, 1);
                                        LemmaMap.put(lemma, Freq_Map2);

                                    }
                                }

                            }

                        }
                    }
                    if(line_no==0)
                        continue;
                    
                    paras.put(pno, para);
                    // Doc section
                    TreeMap<String, Integer> map = new TreeMap<String, Integer>();
                    map.putAll(Temp_Map);
                    freq_set = Temp_Map.values();
                    Doc_Map.put(pno, map);
                    v_list.addAll(freq_set);
                    max_tfreq = Collections.max(v_list);
                    Document_summary.put(pno, new Doc_Summary(max_tfreq, doclen));
                    Temp_Map.clear();
                    v_list.clear();
                    total_doclen = total_doclen + doclen;
                }
               }
               }
               // System.out.println(doc_title);
                
                
            } catch (IOException exp) {
                exp.printStackTrace();
            } catch (NullPointerException exp) {
                exp.printStackTrace();
            }
        
    }

    
       
}
class Doc_Summary implements Serializable{

    public int max_tf;
    public int total_lemmas;

    Doc_Summary(int m_f, int total) {
        max_tf = m_f;
        total_lemmas = total;
    }

    public String toString() {
        return " max_tf = " + this.max_tf + ", total_stems = " + this.total_lemmas;
    }
}


