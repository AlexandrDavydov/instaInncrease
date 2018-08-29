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
    private String accountName;
    private LikeTimer likeTimer = new LikeTimer();

    private void initAccountsArray(){
        accounts.add("wakephoria");//0
        accounts.add("wakestylevera");//1
        accounts.add("wakepeopleriverspot");//2
        accounts.add("wake_pool");//3
        accounts.add("ramadaxpark");//4
        accounts.add("mobius.club");//5
        accounts.add("wakehome");//6
        accounts.add("wakeparksokolniki");//7
        accounts.add("wake_team");//8
        accounts.add("wake_time");//9
        accounts.add("malibuwakeclub");//10
        accounts.add("wakesummer_wakeclub");//11
        accounts.add("tonyiacconi");//12
        accounts.add("nicrapa");//13
        accounts.add("rustymalinoski");//14
        accounts.add("wakestylespot");//15
        accounts.add("wakedivision");//16
        accounts.add("wakemount");//17
        accounts.add("wakeweekend_official");//18
        accounts.add("multiriders");//19
        accounts.add("wakeupwakestation");//20

        accountName = accounts.get(20);
    }
    @Test
    public void startLiking() throws InterruptedException {
        initAccountsArray();
        Configuration.browser="Chrome";
        gotoMyInstagramHome();
        ElementsCollection elements = getAccountHomeAndGetPublications();
        processAccountPublications(elements);
    }

    private void processAccountPublications(ElementsCollection elements) throws InterruptedException {
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

    private ElementsCollection getAccountHomeAndGetPublications(){
        Logger.inLog("============"+accountName+"=============", 0);
        $(By.className("XTCLo")).setValue("@"+accountName);
        $(By.xpath(".//a[@href='/"+accountName+"/']")).click();
        return $$(By.className("_9AhH0"));
    }

    private Boolean isPublicationVideo(){
        return $$(By.className("vcOH2")).size() == 1;
    }

    private void likeUsersPublications() throws InterruptedException {
        Logger.inLog($(By.className("zV_Nj")).getText(), 0);
        for(int i=0; i<2000; i++) {
            $(By.className("zV_Nj")).click();
            ElementsCollection myFollowers = $$(By.className("NroHT"));
            if(myFollowers.size()-1 == i){
                Logger.inLog("============publications processed==============", 0);
                executeJavaScript("window.history.go(-1)");
                return;
            }
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
//        if ($("#info").waitUntil(visible, 4000).isDisplayed()) {
//            $("#info-close").click();
//        }
        int size = $$(By.className("_9AhH0")).size();
        if(size > 0){
            if(size > NUMBER_OF_CHECKED_PUBLICATIONS){
                size = NUMBER_OF_CHECKED_PUBLICATIONS;
            }

            for(int i=0; i<size; i++){
                $$(By.className("_9AhH0")).get(i).click();
                sleep(1000);
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
                Logger.inLog("User has no publications", 1);
            }
        }

    }


}
