package ru.pingwin.inst;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.ElementsCollection;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import static com.codeborne.selenide.Selenide.*;
import static ru.pingwin.inst.Actions.gotoMyInstagramHome;
import static ru.pingwin.inst.Constants.NUMBER_OF_CHECKED_PUBLICATIONS;

public class LikeUserLikers {

    private String accountName = "wakephoria";
    @Test
    public void startLiking(){
        Configuration.browser="Chrome";
        gotoMyInstagramHome();
        $(By.xpath(".//input[@placeholder='Поиск']")).setValue("@"+accountName);
        $(By.xpath(".//a[@href='/"+accountName+"/']")).click();



        ElementsCollection elements = $$(By.className("_9AhH0"));
        for(int i=0; i<elements.size(); i++){
            elements.get(i).click();

            if(publicationIsVideo()){
                inLog("Видео публикация");
                executeJavaScript("window.history.go(-1)");
            }else{
                likeUsersPublications();
            }


        }
    }

    private Boolean publicationIsVideo(){
        return $$(By.partialLinkText("Просмотры: ")).size() == 1;
    }

    private void likeUsersPublications(){
        for(int i=0; i<2000; i++) {
            $(By.partialLinkText(" отметок \"Нравится\"")).click();
            ElementsCollection myFollowers = $$(By.className("NroHT"));
            WebElement ele = $$(By.className("NroHT")).get(i);
            String nick = myFollowers.get(i).findElement(By.className("FPmhX ")).getText();
            executeJavaScript("arguments[0].scrollIntoView();", ele);
            myFollowers.get(i).findElement(By.className("FPmhX ")).click();
            sleep(2000);
            likePublicationsOfOneUser();
            executeJavaScript("window.history.go(-1)");
        }
    }

    private void likePublicationsOfOneUser(){
        int size = $$(By.className("_9AhH0")).size();
        if(size > 0){
            if(size > NUMBER_OF_CHECKED_PUBLICATIONS){
                size = NUMBER_OF_CHECKED_PUBLICATIONS;
            }

            for(int i=0; i<size; i++){
                $$(By.className("_9AhH0")).get(i).click();
                ElementsCollection likes = $$(By.xpath(".//span[@aria-label='Нравится']"));
                if(likes.size() > 0){

                    likes.get(0).click();
                }
                executeJavaScript("window.history.go(-1)");
            }
        }

    }

    public static void inLog(String str){
        System.out.println(str);
    }
}
