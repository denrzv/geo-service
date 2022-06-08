package ru.netology.geo;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import ru.netology.entity.Location;


public class TestGeoService {

    @Test
    @DisplayName("Выброс ошибки при отсутствующей реализации метода")
    public void test_GeoService_ByCoordinates_ThrowException () {
        GeoServiceImpl geo = new GeoServiceImpl();
        Assertions.assertThrows(RuntimeException.class, () -> geo.byCoordinates(0, 0));
    }

    @ParameterizedTest
    @DisplayName("Получение страны по ip")
    @CsvSource({"172.0.32.11, RUSSIA", "96.0.32.11, USA"})
    public void test_GeoService_ByIp (String ip, String country) {
        GeoServiceImpl geo = new GeoServiceImpl();
        Location location = geo.byIp(ip);
        Assertions.assertEquals(location.getCountry().name(), country);
        Assertions.assertThrows(NullPointerException.class, () -> geo.byIp(null));
    }
}
