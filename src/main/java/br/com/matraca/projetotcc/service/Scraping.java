package br.com.matraca.projetotcc.service;

import java.io.IOException;
import java.net.MalformedURLException;

import org.htmlunit.BrowserVersion;
import org.htmlunit.BrowserVersion.BrowserVersionBuilder;
import org.htmlunit.FailingHttpStatusCodeException;
import org.htmlunit.WebClient;
import org.htmlunit.html.HtmlElement;
import org.htmlunit.html.HtmlPage;
import org.springframework.stereotype.Component;


@Component
public class Scraping {

    public WebClient session;

    private Scraping() throws FailingHttpStatusCodeException, MalformedURLException, IOException{
        BrowserVersionBuilder browser = new BrowserVersionBuilder(BrowserVersion.CHROME);
        browser.setBrowserLanguage("pt-BR");
        this.session = new WebClient(browser.build());
        session.getOptions().setJavaScriptEnabled(true);
        session.getOptions().setCssEnabled(false);
        session.getOptions().setThrowExceptionOnScriptError(false);
        session.getOptions().setThrowExceptionOnFailingStatusCode(false);
    }

    public String getImage(String buttonName) throws IOException {
        String url = "https://beta.arasaac.org/pictograms/search/" + buttonName; 

            HtmlPage page = this.session.getPage(url);
            session.waitForBackgroundJavaScript(4000);

            if (page != null) {
                HtmlElement gallery = page.getFirstByXPath("//ul[contains(@class, 'my-gallery-class')]/li[1]/div/a/div/img[1]");
                
                if (gallery != null) {
                    String imageSrc = gallery.getAttribute("src");
                    return imageSrc;
                }
            }
            return "";
    }
}
