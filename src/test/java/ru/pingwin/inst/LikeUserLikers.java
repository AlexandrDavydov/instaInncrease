package ru.pingwin.inst;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.ElementsCollection;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;

import static com.codeborne.selenide.Selenide.*;
import static ru.pingwin.inst.Actions.gotoMyInstagramHome;
import static ru.pingwin.inst.Constants.NUMBER_OF_CHECKED_PUBLICATIONS;

public class LikeUserLikers {
    private ActionCounter actionCounter = new ActionCounter();
    private ArrayList<String> accounts = new ArrayList<>();
    private String accountName = "wakephoria";
    private LikeTimer likeTimer = new LikeTimer();

    private void initAccountsArray(){
        accounts.add("wakephoria");
        accounts.add("wakestylevera");

        accountName = accounts.get(1);
    }
    @Test
    public void startLiking() throws InterruptedException {
        initAccountsArray();
        Configuration.browser="Chrome";
        gotoMyInstagramHome();
        $(By.className("XTCLo")).setValue("@"+accountName);
        $(By.xpath(".//a[@href='/"+accountName+"/']")).click();

        ElementsCollection elements = $$(By.className("_9AhH0"));
        for(int i=0; i<elements.size(); i++){
            elements.get(i).click();

            if(isPublicationVideo()){
                Logger.inLog("Видео публикация", 3);
                executeJavaScript("window.history.go(-1)");
            }else{
                likeUsersPublications();
            }


        }
    }

    private Boolean isPublicationVideo(){
        return $$(By.partialLinkText("Просмотры: ")).size() == 1;
    }

    private void likeUsersPublications() throws InterruptedException {
        for(int i=0; i<2000; i++) {
            $(By.className("zV_Nj")).click();
            //$(By.partialLinkText(" отметок \"Нравится\"")).click();
            ElementsCollection myFollowers = $$(By.className("NroHT"));
            WebElement ele = $$(By.className("NroHT")).get(i);
            String nick = myFollowers.get(i).findElement(By.className("FPmhX ")).getText();
            Logger.inLog(i+") ---------------nick = "+nick, 0);
            executeJavaScript("arguments[0].scrollIntoView();", ele);
            myFollowers.get(i).findElement(By.className("FPmhX ")).click();
            sleep(1000);
            likePublicationsOfOneUser();
            executeJavaScript("window.history.go(-1)");
            Logger.inLog("ACTIONS like: "+actionCounter.getNumberOfLikes(), 0);
        }
    }

    private void likePublicationsOfOneUser() throws InterruptedException {
        int size = $$(By.className("_9AhH0")).size();
        if(size > 0){
            if(size > NUMBER_OF_CHECKED_PUBLICATIONS){
                size = NUMBER_OF_CHECKED_PUBLICATIONS;
            }

            for(int i=0; i<size; i++){
                $$(By.className("_9AhH0")).get(i).click();
                sleep(1000);
                //ElementsCollection likes = $$(By.xpath(".//span[@aria-label='Нравится']"));
                ElementsCollection likes = $$(By.className("glyphsSpriteHeart__outline__24__grey_9"));
                if(likes.size() > 0){
                    likeTimer.sleepBeforeLikeIfNeed();
                    likes.get(0).click();
                    actionCounter.Liked();
                    Logger.inLog("click Like publication", 2);
                }else{
                    Logger.inLog("Publication already liked", 2);
                }
                executeJavaScript("window.history.go(-1)");
            }
        }else{
            sleep(1000);
            if($$(By.className("rkEop")).size() == 1){
                Logger.inLog("This Account is Private",1);
            }else{
                Logger.inLog("!!!!!!!!!!!!!!", 1);
            }
        }

    }


}
