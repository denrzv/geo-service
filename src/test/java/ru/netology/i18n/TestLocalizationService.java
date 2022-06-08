package ru.netology.i18n;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import ru.netology.entity.Country;
import ru.netology.entity.Location;
import ru.netology.geo.GeoServiceImpl;


public class TestLocalizationService {

    @ParameterizedTest
    @DisplayName("Получение корректного сообщения")
    @CsvSource({"RUSSIA, Добро пожаловать", "USA, Welcome"})
    public void test_LocalizationService_Locale (String country, String expectedMessage) {
        LocalizationServiceImpl localizationService = new LocalizationServiceImpl();
        String message = localizationService.locale(Country.valueOf(country));
        Assertions.assertEquals(message, expectedMessage);
        Assertions.assertThrows(RuntimeException.class, () -> localizationService.locale(null));
    }
}
