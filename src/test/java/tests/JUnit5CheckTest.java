package tests;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.CollectionCondition.itemWithText;
import static com.codeborne.selenide.Selenide.*;

public class JUnit5CheckTest {
    @BeforeAll
    static void settingsBeforeAll() {
        Configuration.browserSize = "1920x1080";
        Configuration.baseUrl = "https://github.com/";
        Configuration.pageLoadStrategy = "eager";
        Configuration.timeout = 5000;
        // Configuration.holdBrowserOpen = true;
    }

    @AfterAll
    static void afterAll() {
        closeWebDriver();
    }

    @Test
    void JUnit5Check() {
        open("/selenide/selenide");
        $("#wiki-tab").click();

        // Проверяем наличие пункта "Soft assertions" в списке
        $$("#wiki-body ul li").shouldHave(itemWithText("Soft assertions"));

        // Находим ссылку "Soft Assertions" и кликаем по ней
        $$("#wiki-body ul li a").findBy(text("Soft Assertions")).click();

        // Проверяем наличие текста с использованием текстового блока
        $$(".markdown-heading").findBy(text("JUnit5")).sibling(0).shouldHave(text("""
            @ExtendWith({SoftAssertsExtension.class})
            class Tests {
                @Test
                void test() {
                    Configuration.assertionMode = SOFT;
                    open("page.html");

                    $("#first").should(visible).click();
                    $("#second").should(visible).click();
                }
            }"""));
    }
}