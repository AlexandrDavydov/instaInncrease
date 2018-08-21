package ru.pingwin.inst;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.ElementsCollection;
import org.junit.Test;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static ru.pingwin.inst.Actions.gotoMyInstagramHome;

public class LikeUserLikers {

    private String accountName = "wakeforia";
    @Test
    public void startLiking(){
        Configuration.browser="Chrome";
        gotoMyInstagramHome();
        $(By.xpath(".//input[@placeholder='Поиск']")).setValue("@"+accountName);
        $(By.xpath(".//a[@href='/"+accountName+"/']")).click();
        ElementsCollection elements = $$(By.tagName("_9AhH0"));
        for(int i=0; i<elements.size(); i++){

        }
    }
}
