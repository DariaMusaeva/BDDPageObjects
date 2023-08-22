package page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import data.DataHelper;
import static com.codeborne.selenide.Selenide.$$;

public class DashboardPage {

    private ElementsCollection cards = $$(".list__item div");
    private final String balanceStart = "баланс: ";
    private final String balanceFinish = " р.";

    public DashboardPage() {
    }

    public int getCardBalance(DataHelper.CardInfo cardInfo) {
        var text = cards.findBy(Condition.text(cardInfo.getCardNumber().substring(15))).getText();
        return extractBalance(text);
    }

    public TransferPage selectCard(DataHelper.CardInfo cardInfo) {
        cards.findBy(Condition.attribute("data-test-id", cardInfo.getTestId()))
                .$("button").click();
        return new TransferPage();
    }

    private int extractBalance(String text) {
        var start = text.indexOf(balanceStart);
        var finish = text.indexOf(balanceFinish);
        var value = text.substring(start + balanceStart.length(), finish);
        return Integer.parseInt(value);
    }
}
