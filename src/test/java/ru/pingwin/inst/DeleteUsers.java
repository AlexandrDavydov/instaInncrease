package ru.pingwin.inst;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.ElementsCollection;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import java.util.ArrayList;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selenide.$$;
import static com.codeborne.selenide.Selenide.sleep;
import static ru.pingwin.inst.Actions.gotoMyInstagramHome;
import static ru.pingwin.inst.Utilities.DAYS_MAX_AFTER_LAST_PUBLICATION;

public class DeleteUsers {

    private final Integer NUMBER_OF_CHECKED_PUBLICATIONS = 15;
    private ArrayList<ArrayList<String>> lastPublicationsLikers= new ArrayList();

    @Test
    public void checkFollowers(){
        Configuration.browser="Chrome";
        gotoMyInstagramHome();
        $(By.partialLinkText("Подписки:")).click();
        sleep(2000);
        //scrollToEnd(By.className("NroHT"));

        for(int i=0; i<2000; i++){
            ElementsCollection myFollowers = $$(By.className("NroHT"));
            WebElement ele= $$(By.className("NroHT")).get(i);
            String nick = myFollowers.get(i).findElement(By.className("FPmhX ")).getText();
            executeJavaScript("arguments[0].scrollIntoView();", ele);
            myFollowers.get(i).findElement(By.className("FPmhX ")).click();
            sleep(2000);
            $$(By.className("_9AhH0")).get(0).click();
            sleep(2000);

            String date = $(By.xpath(".//time[@datetime]")).getAttribute("datetime");
            if(!Utilities.isPublicationNotOld(date, DAYS_MAX_AFTER_LAST_PUBLICATION)){
                System.out.println(nick);
                //System.out.println(nick + " - old");
            }
            executeJavaScript("window.history.go(-1)");
            executeJavaScript("window.history.go(-1)");
            if(i == $$(By.className("NroHT")).size()){
                return;
            }
        }
    }

    @Test
    public void start() {
        Configuration.browser="Chrome";
        gotoMyInstagramHome();
        $(By.className("_9AhH0")).shouldBe(Condition.visible);
        sleep(2000);
        ElementsCollection myPublications = $$(By.className("_9AhH0"));

        int size = NUMBER_OF_CHECKED_PUBLICATIONS;
        if(myPublications.size() < NUMBER_OF_CHECKED_PUBLICATIONS){
            size = myPublications.size();
        }

        for(int i=0; i<size; i++){
            myPublications.get(i).click();
            lastPublicationsLikers.add(getLikeUsers());
        }
        System.out.println(lastPublicationsLikers.size());
    }

    private void clickLikesOrViews(){
        if(getElements(By.partialLinkText("likes")).size() > 0){
            $(By.className("EDfFK")).findElement(By.tagName("a")).click();
        }else{
            $(By.className("vcOH2")).click();
        }
    }

    private ArrayList<String> getLikeUsers(){
        clickLikesOrViews();
        scrollToEnd(By.className("FPmhX"));
        ArrayList<String> users = new ArrayList();
        ElementsCollection elements = $$(By.className("FPmhX"));
        for(int i=0; i<elements.size(); i++){
            users.add(elements.get(i).getText());
        }
        executeJavaScript("window.history.go(-1)");
        return users;
    }

    private void scrollToEnd(By elementLocator){
        sleep(3000);
        int size = $$(elementLocator).size();
        for(int i=0; i<300; i++){
            WebElement ele= $$(elementLocator).get(size-1);
            executeJavaScript("arguments[0].scrollIntoView();", ele);
            sleep(1000);
            if(size == $$(elementLocator).size()){
                return;
            }
            size = $$(elementLocator).size();
            System.out.println(size);
        }
    }
}
