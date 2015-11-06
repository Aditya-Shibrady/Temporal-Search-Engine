
package ir_prj;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeMap;

/**
 *
 * @author Aditya
 */
public class IR_PRJ implements Serializable{

    /**
     * @param args the command line arguments
     */
     HashMap<String, Integer> pnums = new HashMap<String, Integer>();
     HashMap<Integer, Integer> judgements = new HashMap<Integer, Integer>();
     HashMap<Integer, String> judgement_expl = new HashMap<Integer, String>();
    TreeMap<String, ArrayList<Integer>> REL_1 = new TreeMap<String, ArrayList<Integer>>();
    TreeMap<String, ArrayList<Integer>> REL_2 = new TreeMap<String, ArrayList<Integer>>();
    TreeMap<String, ArrayList<Integer>> REL_3 = new TreeMap<String, ArrayList<Integer>>();
    TreeMap<String, ArrayList<Integer>> REL_4 = new TreeMap<String, ArrayList<Integer>>();
    TreeMap<String, ArrayList<Integer>> REL_5 = new TreeMap<String, ArrayList<Integer>>();
    TreeMap<String, ArrayList<Integer>> REL_6 = new TreeMap<String, ArrayList<Integer>>();
    TreeMap<String, ArrayList<Integer>> REL_7 = new TreeMap<String, ArrayList<Integer>>();
    TreeMap<String, ArrayList<Integer>> REL_8 = new TreeMap<String, ArrayList<Integer>>();
    TreeMap<String, Integer> combined_factors = new TreeMap<String, Integer>();
    int pnum = 1;

     String word_processer(String oString) {
        String s = new String(".,\'\"<>:;?&*-!$^%()/\\[]{}_|#@~`=+");
        oString = oString.toLowerCase().trim();
        if (oString.length() <= 2) {
            return null;
        }

        //Trimming special characters at the ends
        while (s.indexOf(oString.charAt(0)) >= 0) {
            oString = oString.substring(1);
        }

        while (s.indexOf(oString.charAt(oString.length() - 1)) >= 0) {
            oString = oString.substring(0, oString.length() - 1);
        }

        oString = oString.trim();
        return oString;
    }

    public  void pre_processing() {
        String s, url_pno, word;
        int i, j, ln, k, l;
        BufferedReader br;
        FileReader f;
        BufferedWriter bw;
        FileWriter f1;

        File folder;
        String[] arr;
        try {
            folder = new File("Group4");
            f1 = new FileWriter(folder + "/" + "complete.csv");
            bw = new BufferedWriter(f1);
            for (File subfolder : folder.listFiles()) {
                if (!subfolder.isDirectory()) {
                    continue;
                }
                for (File fileEntry : subfolder.listFiles()) {
                    if (!fileEntry.getName().contains("annotation")) {
                        continue;
                    }

                    ln = 0;
                    f = new FileReader(subfolder + "/" + fileEntry.getName());
                    br = new BufferedReader(f);

                    while ((s = br.readLine()) != null) {
                        arr = s.split(",");

                       // System.out.println(s);
                        if (ln == 0) {
                            ln++;
                            continue;
                        }
                        if (s.length() <= 8 || s.contains(",,")) {
                            continue;
                        }
                        if (arr[3].length() <= 2) {
                            continue;
                        }
                        if (arr.length > 4 & "2".equals(arr[2])) {
                            if ((word = word_processer(arr[3])) != null) {
                                bw.write(arr[0] + "," + arr[1] + "," + arr[2] + "," + word);
                                bw.newLine();
                            }
                            for (i = 4, arr[3] = arr[3].trim(); i < arr.length; i++) {

                                if ((word = word_processer(arr[i])) != null) {
                                    bw.write(arr[0] + "," + arr[1] + "," + arr[2] + "," + word);
                                    bw.newLine();
                                }
                            }
                        } else {
                            bw.write(arr[0] + "," + arr[1] + "," + arr[2] + "," + word_processer(arr[3]));
                            bw.newLine();
                        }
                    }
                }
            }
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void Rel_indexer() {
        BufferedReader br, br_j;
        FileReader f, f_j;
        BufferedWriter bw;
        FileWriter f1;
        String s, url_pno;
        int i, j, ln1, ln, k, l, cur_pno;

        File folder;
        String[] arr;
        pre_processing();
        try {
            folder = new File("Group4");
            for (File subfolder : folder.listFiles()) {
                if (!subfolder.isDirectory()) {
                    continue;
                }

                for (File fileEntry : subfolder.listFiles()) {
                    if (fileEntry.getName().contains("judgments")) {

                        ln = 0;
                        f_j = new FileReader(subfolder + "/" + fileEntry.getName());
                        br_j = new BufferedReader(f_j);
                        while ((s = br_j.readLine()) != null) {
                            if (ln == 0) {
                                ln++;
                                continue;
                            }
                            //System.out.println(s);
                            arr = s.split(",");
                            if (s.length() < 9 | arr.length < 5) {
                                continue;
                            }

                            url_pno = arr[1] + "." + arr[2];
                            if (!pnums.containsKey(url_pno)) {
                                pnums.put(url_pno, pnum);
                                cur_pno = pnum;
                                pnum++;
                            } else {
                                cur_pno = pnums.get(url_pno);
                            }
                            judgements.put(cur_pno, Integer.parseInt(arr[3]));
                            judgement_expl.put(cur_pno, arr[4]);
                        }
                    } 

                }
                
                        
                        ln = 0;
                        f = new FileReader(folder + "/" + "complete.csv");
                        br = new BufferedReader(f);
                        while ((s = br.readLine()) != null) {
                            arr = s.split(",");
                            url_pno = arr[0] + "." + arr[1];
                            if (ln == 0) {
                                ln++;
                                continue;
                            }
                            if (!pnums.containsKey(url_pno)) {
                                pnums.put(url_pno, pnum);
                                cur_pno = pnum;
                                pnum++;
                            } else {
                                cur_pno = pnums.get(url_pno);
                            }

                            // System.out.println(url_pno + " : " + cur_pno + "  " + arr[2]);
                            if ("1".equals(arr[2])) {
                                ArrayList<Integer> al = new ArrayList<Integer>();
                                if (REL_1.containsKey(arr[3].trim().toLowerCase())) {
                                    al = REL_1.get(arr[3].trim().toLowerCase());
                                }

                                al.add(cur_pno);
                                REL_1.put(arr[3].trim().toLowerCase(), al);
                                combined_factors.put(arr[3].trim().toLowerCase(), 1);
                            } else if ("2".equals(arr[2])) {
                                ArrayList<Integer> al = new ArrayList<Integer>();
                                if (REL_2.containsKey(arr[3].trim().toLowerCase())) {
                                    al = REL_2.get(arr[3].trim().toLowerCase());
                                }

                                al.add(cur_pno);
                                REL_2.put(arr[3].trim().toLowerCase(), al);
                                combined_factors.put(arr[3].trim().toLowerCase(), 2);
                            } else if ("3".equals(arr[2])) {
                                ArrayList<Integer> al = new ArrayList<Integer>();
                                if (REL_3.containsKey(arr[3].trim().toLowerCase())) {
                                    al = REL_3.get(arr[3].trim().toLowerCase());
                                }

                                al.add(cur_pno);
                                REL_3.put(arr[3].trim().toLowerCase(), al);
                                combined_factors.put(arr[3].trim().toLowerCase(), 3);
                            } else if ("4".equals(arr[2])) {
                                ArrayList<Integer> al = new ArrayList<Integer>();
                                if (REL_4.containsKey(arr[3].trim().toLowerCase())) {
                                    al = REL_4.get(arr[3].trim().toLowerCase());
                                }

                                al.add(cur_pno);
                                REL_4.put(arr[3].trim().toLowerCase(), al);
                                combined_factors.put(arr[3].trim().toLowerCase(), 4);
                            } else if ("5".equals(arr[2])) {
                                ArrayList<Integer> al = new ArrayList<Integer>();
                                if (REL_5.containsKey(arr[3].trim().toLowerCase())) {
                                    al = REL_5.get(arr[3].trim().toLowerCase());
                                }

                                al.add(cur_pno);
                                REL_5.put(arr[3].trim().toLowerCase(), al);
                                combined_factors.put(arr[3].trim().toLowerCase(), 5);
                            } else if ("6".equals(arr[2])) {
                                ArrayList<Integer> al = new ArrayList<Integer>();
                                if (REL_6.containsKey(arr[3].trim().toLowerCase().toLowerCase())) {
                                    al = REL_6.get(arr[3].trim().toLowerCase());
                                }

                                al.add(cur_pno);
                                REL_6.put(arr[3].trim().toLowerCase(), al);
                                combined_factors.put(arr[3].trim().toLowerCase(), 6);
                            } else if ("7".equals(arr[2])) {
                                ArrayList<Integer> al = new ArrayList<Integer>();
                                if (REL_7.containsKey(arr[3].trim().toLowerCase())) {
                                    al = REL_7.get(arr[3].trim().toLowerCase());
                                }

                                al.add(cur_pno);
                                REL_7.put(arr[3].trim().toLowerCase(), al);
                                combined_factors.put(arr[3].trim().toLowerCase(), 7);
                            } else if ("8".equals(arr[2])) {
                                ArrayList<Integer> al = new ArrayList<Integer>();
                                if (REL_8.containsKey(arr[3].trim().toLowerCase())) {
                                    al = REL_8.get(arr[3].trim().toLowerCase());
                                }

                                al.add(cur_pno);
                                REL_8.put(arr[3].trim().toLowerCase(), al);
                                combined_factors.put(arr[3].trim().toLowerCase(), 8);
                            }
                        }
                    }
            

        /*    Set<Map.Entry<String, Integer>> keys = combined_factors.entrySet();
            for (Map.Entry<String, Integer> ent : keys) {
                System.out.println(ent);
            } */

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public  void auto_factor(String path) {
        BufferedReader br;
        FileReader f;
        String[] arr, tokenarray;
        String s, finalToken, doc;
        int cur_pno,count=0;
        Indexer idx = new Indexer();
        try {
            File folder = new File(path);
            for (File fileEntry : folder.listFiles()) {
                f = new FileReader(folder + "/" + fileEntry.getName());
                br = new BufferedReader(f);
                doc = fileEntry.getName();
                doc = doc.replace(".txt", "");
                count++;
                if (pnums.containsKey(doc)) {
                    continue;
                } else {
                    cur_pno = pnum;
                    pnum++;
                 
                }
                while ((s = br.readLine()) != null) {
                    arr = s.split(" ");
                    for (int ln = 0; ln < arr.length; ln++) {
                        tokenarray = idx.token_processer(arr[ln]);
                        for (int jj = 0; jj < tokenarray.length; jj++) {
                            finalToken = tokenarray[jj];
                            if(finalToken==null)
                                continue;
                            if (REL_1.containsKey(finalToken)) {
                                ArrayList<Integer> al = new ArrayList<Integer>();
                                al = REL_1.get(finalToken);
                                al.add(cur_pno);
                                REL_1.put(finalToken, al);
                               System.out.println("1 "+finalToken);
                            } else if (REL_2.containsKey(finalToken)) {
                                ArrayList<Integer> al = new ArrayList<Integer>();
                                al = REL_2.get(finalToken);
                                al.add(cur_pno);
                                REL_2.put(finalToken, al);
                                System.out.println("2 "+finalToken);
                            } else if (REL_3.containsKey(finalToken)) {
                                ArrayList<Integer> al = new ArrayList<Integer>();
                                al = REL_3.get(finalToken);
                                al.add(cur_pno);
                                REL_3.put(finalToken, al);
                                System.out.println("3 "+finalToken);
                            } else if (REL_4.containsKey(finalToken)) {
                                ArrayList<Integer> al = new ArrayList<Integer>();
                                al = REL_4.get(finalToken);
                                al.add(cur_pno);
                                REL_4.put(finalToken, al);
                                System.out.println("4 "+finalToken);
                            } else if (REL_5.containsKey(finalToken)) {
                                ArrayList<Integer> al = new ArrayList<Integer>();
                                al = REL_5.get(finalToken);

                                al.add(cur_pno);
                                REL_5.put("5 "+finalToken, al);
                                System.out.println(finalToken);
                            } else if (REL_6.containsKey(finalToken)) {
                                ArrayList<Integer> al = new ArrayList<Integer>();
                                al = REL_6.get(finalToken);
                                al.add(cur_pno);
                                REL_6.put(finalToken, al);
                                System.out.println("6 "+finalToken);
                            } else if (REL_7.containsKey(finalToken)) {
                                ArrayList<Integer> al = new ArrayList<Integer>();
                                al = REL_7.get(finalToken);
                                al.add(cur_pno);
                                REL_7.put(finalToken, al);
                                System.out.println("7 "+finalToken);
                            } else if (REL_8.containsKey(finalToken)) {
                                ArrayList<Integer> al = new ArrayList<Integer>();
                                al = REL_8.get(finalToken);
                                al.add(cur_pno);
                                REL_8.put(finalToken, al);
                                System.out.println("8 "+finalToken);
                            }

                        }

                    }
                }
            }
            if(count==0)
                System.out.println("There are no addtional paragraphs for which relevance factors can be extracted");
            else 
                System.out.println("Relevance factors have been extracted for addtional paragraphs");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        // TODO code application logic here
        IR_PRJ ip;
        Indexer idx;
        try{
        File f=new File("class1");
        File f1=new File("class2");
        if(f.exists()){
                FileInputStream inp = new FileInputStream("class1");
		ObjectInputStream ins = new ObjectInputStream(inp); 
                ip=(IR_PRJ)ins.readObject();
                System.out.println("The existed relevance factor-index has been retained");
                f.delete();
                inp.close();
	}
        else
        ip=new IR_PRJ();
        
        if(f1.exists()){
                FileInputStream inp1 = new FileInputStream("class2");
		ObjectInputStream ins1 = new ObjectInputStream(inp1); 
                idx=(Indexer)ins1.readObject();
                System.out.println("The existed paragraph index has been retained");
                f1.delete();
	}
        else
        idx = new Indexer();
        
        ip.Rel_indexer();
        
        idx.para_indexer(ip.pnums);
        f=new File("class1");
        FileOutputStream out = new FileOutputStream(f); 
	ObjectOutputStream os = new ObjectOutputStream(out) ;
        os.writeObject(ip);
        out.close();
        f1=new File("class2");
        FileOutputStream out1 = new FileOutputStream(f1); 
	ObjectOutputStream os1 = new ObjectOutputStream(out1) ;
        os1.writeObject(idx);
        out1.close();
        ip.auto_factor("Addtional-Paras");  
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
        catch(ClassNotFoundException e)
        {
            e.printStackTrace();
        }
        
    }
}
