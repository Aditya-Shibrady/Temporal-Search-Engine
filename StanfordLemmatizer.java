
package ir_prj;

/**
 *
 * @author Aditya
 */
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

import edu.stanford.nlp.ling.CoreAnnotations.LemmaAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TokensAnnotation;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.CoreMap;

public class StanfordLemmatizer {

    protected StanfordCoreNLP s;

    public StanfordLemmatizer() {
        Properties props;
        props = new Properties();
        props.put("annotators", "tokenize, ssplit, pos, lemma");
        this.s = new StanfordCoreNLP(props);
    }

    public String lemmatize(String word) {
        Annotation Annotated_word = new Annotation(word);
        this.s.annotate(Annotated_word);
        List<CoreLabel> token = Annotated_word.get(TokensAnnotation.class);
        return token.get(0).get(LemmaAnnotation.class);

    }
}
