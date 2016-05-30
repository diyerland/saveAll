import com.gravity.goose.Article;
import com.gravity.goose.Configuration;
import com.gravity.goose.Goose;
import com.sun.prism.Image;
import de.l3s.boilerpipe.BoilerpipeExtractor;
import de.l3s.boilerpipe.document.TextDocument;
import de.l3s.boilerpipe.extractors.ArticleExtractor;
import de.l3s.boilerpipe.extractors.CommonExtractors;
import de.l3s.boilerpipe.sax.BoilerpipeSAXInput;
import de.l3s.boilerpipe.sax.HTMLDocument;
import de.l3s.boilerpipe.sax.HTMLFetcher;

import java.io.Console;
import java.net.URL;

/**
 * Created by peter on 16/5/30.
 */
public class Test {
    public static void main(String[] args){

        String url = "http://business.sohu.com/20160530/n452011285.shtml";
        String boilerpipeExtractContent = boilerpipeExtract(url);
        String gooseContent = gooseExtract(url);
    }

    public static String boilerpipeExtract(String url){
        try {
            final HTMLDocument htmlDoc = HTMLFetcher.fetch(new URL(url));
            final TextDocument doc = new BoilerpipeSAXInput(htmlDoc.toInputSource()).getTextDocument();
            String title = doc.getTitle();

            String content = ArticleExtractor.INSTANCE.getText(doc);
            System.out.println(content);
            return content;

        } catch (Exception e) {
            return "";
        }
    }

    /**
     * 中文乱码，text无法获取
     * @param url
     * @return
     */
    public static String gooseExtract(String url){
        Goose goose = new Goose(new Configuration());
        Article article = goose.extractContent(url);
        String content = article.cleanedArticleText();
        System.out.println(content);
        System.out.println(article.metaDescription());
        System.out.println(article.metaKeywords());
        return content;
    }
}
