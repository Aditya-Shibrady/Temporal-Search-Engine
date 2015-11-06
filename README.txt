Details:


Instruction to run the project files.

Index Creation
 Two types of indices are created one is based on relevant factors and the second is based on the paragraphs collected. For each relevant factor, a tree map is created and in each map, the value of the relevant factor along with reference value is stored. The paragraph can be retrieved using the reference value.

Extracting relevant factors from new paragraphs
The relevant factor-maps built in the above step are used to extract relevant factors from new paragraphs. Each new paragraph is parsed and tokens obtained thus are matched with values of relevant factor-maps to identify factors in them.
In the submission, there is a folder named Addtional-Paras, to which we can copy additional paragraphs for which relevance factors has to be extracted.

Incremental Index Creation:
When the index is created for the first time, it is written to object file using Object OutputStream and its content is retained for further executions, thus saving the time to create index for previously processed paragraphs.
class1 and class2 are the object stream files created to save relevant factor index and paragraph index respectively.

Program Description: Algorithm:
1. All the annotation files are preprocessed to eliminate blank records and then read into a file named complete.csv.
2. All the judgements files are processed and the values are stored in a hash map.
3. All the annotations files are processed and a separate index is created for each relevant factor.
4. All the paragraphs are processed and then indexed and finally stored in a separate hash map.
     
Token processor while indexing paragraphs:
The initial word, identified using space delimiter is sent to this function for further processing. The following actions are performed on the received word.
1. The special characters at both the ends of the word (ex:”/car//”) are removed.
2. If the word is of abbreviated form (ex: U.S.A), the dots are removed (USA).
3. If the word is of possession form (ex: University’s), the postfix ‘s is removed (University).
4. If the word contains hyphens in the middle (ex: multi-layer), hyphens are removed to form
a single word (multilayer).
5. If the word contains ‘- -‘ in the middle, then ‘- -‘ are removed.
6. If the word contains ‘,’ in the middle(“schidler,j”), then it is removed to form a single
sentence (“schindlerj”).

Lemmatizer:
Lemmatizer function from Stanford Lemmatizer jar package is invoked to get the lemma of a term.


Program structure and Instructions to execute Java files
1. The program consists of two folders
This folder is having the datasets. Addtional-Paras: This folder hosts the new paragraphs for which relevant factors has to be extracted. Just to demonstrate its functioning, two sample paragraphs are placed.
2. The program consists of three files.
	IR_PRJ.java –This is the main class
	StanfordLemmatizer.java –This is the class containing the functionality to invoke Stanford Lemmatizer.
	Indexer.java – This is the class containing the functionality to index paragraphs.
3. Please copy the folders and the files mentioned above to the directory from which the program is run.
4. Open command prompt and navigate to the directory having these files.
5. Load Stanford lemmatizer using the command: >source /usr/local/corenlp341/classpath.sh
6. Next, compile StanfordLemmatizer.java file with the command >javac StanfordLemmatizer.java
7. Next, compile indexer.java file with the command >javac Indexer.java
8. Next, compile IR_PRJ.java file with the command >javac IR_PRJ.java
9. Next, run the main class using the below command.
>java IR_PRJ