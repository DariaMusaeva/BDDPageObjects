package test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import page.DashboardPage;
import page.LoginPage;
import static com.codeborne.selenide.Selenide.open;
import static data.DataHelper.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TransferTest {

    DashboardPage dashboardPage;

    @BeforeEach
    void setup() {
        var loginPage = open("http://localhost:9999", LoginPage.class);
        var authInfo = getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = getVerificationCode();
        dashboardPage = verificationPage.validCode(verificationCode);
    }

    @Test
    void shouldTransferFromFirstToSecond() {
        var firstCardInfo = getFirstCardInfo();
        var secondCardInfo = getSecondCardInfo();
        var firstCardBalance  = dashboardPage.getCardBalance(firstCardInfo);
        var secondCardBalance = dashboardPage.getCardBalance(secondCardInfo);
        var amount = generateValidAmount(firstCardBalance);
        var expectedFirstCardBalance = firstCardBalance - amount;
        var expectedSecondCardBalance = secondCardBalance + amount;
        var transferPage = dashboardPage.selectCard(secondCardInfo);
        transferPage.makeValidTransfer(String.valueOf(amount), firstCardInfo);
        var actualFirstCardBalance = dashboardPage.getCardBalance(firstCardInfo);
        var actualSecondCardBalance = dashboardPage.getCardBalance(secondCardInfo);
        assertEquals(expectedFirstCardBalance, actualFirstCardBalance);
        assertEquals(expectedSecondCardBalance, actualSecondCardBalance);
    }

    @Test
    void shouldTransferFromSecondToFirst() {
        var firstCardInfo = getFirstCardInfo();
        var secondCardInfo = getSecondCardInfo();
        var firstCardBalance  = dashboardPage.getCardBalance(firstCardInfo);
        var secondCardBalance = dashboardPage.getCardBalance(secondCardInfo);
        var amount = generateValidAmount(firstCardBalance);
        var expectedFirstCardBalance = firstCardBalance + amount;
        var expectedSecondCardBalance = secondCardBalance - amount;
        var transferPage = dashboardPage.selectCard(firstCardInfo);
        transferPage.makeValidTransfer(String.valueOf(amount), secondCardInfo);
        var actualFirstCardBalance = dashboardPage.getCardBalance(firstCardInfo);
        var actualSecondCardBalance = dashboardPage.getCardBalance(secondCardInfo);
        assertEquals(expectedFirstCardBalance, actualFirstCardBalance);
        assertEquals(expectedSecondCardBalance, actualSecondCardBalance);
    }

    @Test
    void shouldGetErrorMassage() {
        var firstCardInfo = getFirstCardInfo();
        var secondCardInfo = getSecondCardInfo();
        var firstCardBalance  = dashboardPage.getCardBalance(firstCardInfo);
        var secondCardBalance = dashboardPage.getCardBalance(secondCardInfo);
        var amount = generateInvalidAmount(secondCardBalance);
        var transferPage = dashboardPage.selectCard(secondCardInfo);
        transferPage.transfer(String.valueOf(amount), firstCardInfo);
        transferPage.getErrorMassage("Недостаточно средств, введите другую сумму");
        var actualFirstCardBalance = dashboardPage.getCardBalance(firstCardInfo);
        var actualSecondCardBalance = dashboardPage.getCardBalance(secondCardInfo);
        assertEquals(firstCardBalance, actualFirstCardBalance);
        assertEquals(secondCardBalance, actualSecondCardBalance);
    }
}
