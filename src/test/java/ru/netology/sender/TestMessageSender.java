package ru.netology.sender;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mockito;
import ru.netology.entity.Country;
import ru.netology.entity.Location;
import ru.netology.geo.GeoService;
import ru.netology.i18n.LocalizationService;

import java.util.HashMap;

public class TestMessageSender {

    @ParameterizedTest
    @DisplayName("Успешная отправка сообщения в зависимости от ip")
    @CsvSource({"172.0.32.11, Добро пожаловать", "96.0.32.11, Welcome"})
    public void test_MessageSender_Send(String ip, String message) {
        GeoService geoServiceMock = Mockito.mock(GeoService.class);
        if (ip.startsWith("172")) {
            Mockito.when(geoServiceMock.byIp(ip))
                    .thenReturn(new Location(null, Country.RUSSIA, null, 0));
        } else {
            Mockito.when(geoServiceMock.byIp(ip))
                    .thenReturn(new Location(null, Country.USA, null, 0));
        }

        LocalizationService localizationServiceMock = Mockito.mock(LocalizationService.class);
        Mockito.when(localizationServiceMock.locale(Country.RUSSIA)).thenReturn("Добро пожаловать");
        Mockito.when(localizationServiceMock.locale(Country.USA)).thenReturn("Welcome");

        MessageSenderImpl messageSender = new MessageSenderImpl(geoServiceMock, localizationServiceMock);

        HashMap<String, String> headers = new HashMap<>();
        headers.put(MessageSenderImpl.IP_ADDRESS_HEADER, ip);
        Assertions.assertEquals(messageSender.send(headers), message);
    }
}
