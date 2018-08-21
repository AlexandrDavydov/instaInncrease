package ru.pingwin.inst;

import com.codeborne.selenide.Condition;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.Selenide.sleep;

public class Actions {
    static public void gotoMyInstagramHome(){
        open("https://www.instagram.com/");
        $(By.className("_5f5mN")).click();
        $(By.name("email")).setValue("ping_win@yahoo.com");
        $(By.name("pass")).setValue("Point12345");
        $(By.name("login")).click();
        sleep(3000);
        $(By.className("coreSpriteDesktopNavProfile")).shouldBe(Condition.visible);
        $(By.className("coreSpriteDesktopNavProfile")).click();
        sleep(2000);

    }
}
