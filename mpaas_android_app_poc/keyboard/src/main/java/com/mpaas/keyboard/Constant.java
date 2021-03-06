package com.mpaas.keyboard;

public class Constant {
    public static final String TAG = "mpaas_keyboard_encrypt";
    public static final String SM2_PUBLIC_KEY_TEST =
            "-----BEGIN PUBLIC KEY-----\n" +
                    "MFkwEwYHKoZIzj0CAQYIKoEcz1UBgi0DQgAE/ZL6nhqKF3kHfdA6YzC2J6Y+BEi8\n" +
                    "mcm0Sr5pDJ53bs7ufGXe/QWV/0TbazcOIgBbRT5lU/tjS6lVbytarx3WTA==\n" +
                    "-----END PUBLIC KEY-----\n";

    public static final String SM2_PRIVATE_KEY_TEST = "-----BEGIN EC PRIVATE KEY-----\n" +
            "MHcCAQEEIH1KdUEAnOPYVm8aJvrBwlPY9G6gObDaepieI9WjhCyZoAoGCCqBHM9V\n" +
            "AYItoUQDQgAE/ZL6nhqKF3kHfdA6YzC2J6Y+BEi8mcm0Sr5pDJ53bs7ufGXe/QWV\n" +
            "/0TbazcOIgBbRT5lU/tjS6lVbytarx3WTA==\n" +
            "-----END EC PRIVATE KEY-----";

    public static final String RSA_PUBLIC_KEY_TEST_2048 = "" +
            "-----BEGIN PUBLIC KEY-----\n" +
            "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAn5k5OcsiZDGJj7e5+DAC\n" +
            "c8Pa+dsCHT4gZGPE5iV7hrX24vhW0vGmcBYhCFABcUouRSPYDxaBtHJFpGIpmdTP\n" +
            "5yfHLUSW5rHjktAU8w1ZgdC8Oyfv+I//CLXOqr+Fc1VKN/mt7hihygvcx45uoREW\n" +
            "ZG9hMnmk+NW6h/ID3LXCDg3fyuHNRTHPiq7twaWmJugJpJm30gLWmprXRoMgH0j1\n" +
            "JDTdBNxih4uJq+SpxSlniRtrro0ajhcq5lJ+BfuDmXTj4XMu4OIC74N50cw0VBaX\n" +
            "P5UWIfqnwKwXK5sXQ67VVkuPcKFPmpVWIg+Hzl70nSTZO8OIftdByJNyKcQlhFeK\n" +
            "cwIDAQAB\n" +
            "-----END PUBLIC KEY-----";

    public static final String RSA_PRIVATE_KEY_TEST_2048 = "-----BEGIN PRIVATE KEY-----\n" +
            "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCfmTk5yyJkMYmP\n" +
            "t7n4MAJzw9r52wIdPiBkY8TmJXuGtfbi+FbS8aZwFiEIUAFxSi5FI9gPFoG0ckWk\n" +
            "YimZ1M/nJ8ctRJbmseOS0BTzDVmB0Lw7J+/4j/8Itc6qv4VzVUo3+a3uGKHKC9zH\n" +
            "jm6hERZkb2EyeaT41bqH8gPctcIODd/K4c1FMc+Kru3BpaYm6AmkmbfSAtaamtdG\n" +
            "gyAfSPUkNN0E3GKHi4mr5KnFKWeJG2uujRqOFyrmUn4F+4OZdOPhcy7g4gLvg3nR\n" +
            "zDRUFpc/lRYh+qfArBcrmxdDrtVWS49woU+alVYiD4fOXvSdJNk7w4h+10HIk3Ip\n" +
            "xCWEV4pzAgMBAAECggEAdofUsXCFRUZpNPytuH7ng1TFb+VQ56j5Y7WZSZgy6rR/\n" +
            "/MeVrCregsZcmBDbUWlXffAGCgacJAjuYgypet9L3RB049DaBdG+A45Y0L5saHY/\n" +
            "JeweVEuVjMGCENwW37ZLXdEodPmER0uSF/9XeHX5XHRACr2/YTNSyp5acfPngIlv\n" +
            "ETt9vQL/+ar6yj792Gl5N+t8oYBir1AOZzeq1mGzeOjMFL+NEzaFVvQu/r2mipNo\n" +
            "tEvK8Q7qMObUbdWM0V/yNEHQReCrmESpNua8T68K44eXHWtbZbRNErwnlAeadeJO\n" +
            "sDfOJhBjuwdzO9wIkX7rItMq+QkzB1FIxfO8cbm/EQKBgQDNWhaAiueWYxLyt3em\n" +
            "jH4XcQsJyTupHtkG/lCJ8pZUEv1ugHS4otNAwOWoE4h86eVFJQ1UrAg8M7Yl2Jfc\n" +
            "y+LCM/CMdtPTT2cj6Vwsn7TkYIK7GB5blXhNVwU9AWeo2lULNpVLqOXyIXOcAc7w\n" +
            "7bljUcUNa77Uu1map/RjnpJmKQKBgQDG9kThaz1bvlgXusmOOwjnQmbFvUQaOsrA\n" +
            "SpsrApzrGRgO7TLtOlp2HHGr04Ho4+xN7tfxiHz/n7/t2JAOCzxJaFtr4RSJgcLy\n" +
            "Qb7m2zTApKqs2fQmlrMKQpwJRKTVfkn2T0sHU6GyHD9MX8Ax+4y9bhZJZuabWAaz\n" +
            "zcHhanPnOwKBgAzEvD9z46CDOotjAwqVE8YLXl9WZuJGK8J2j7V0IwrJP37ed+N/\n" +
            "JFmTZPRFeqghtwLMof6Qz9ZGdN33kMHG36uppRxmyxcJieU+vl978rh8k8RkZIbX\n" +
            "TWqprYa0jqh+IumWlEPnFZ0vnUunBSIP9gYJ8JG8EFqT56dpKBYFpx65AoGAd8fF\n" +
            "eixUuUnjwQ170HvsBUXtl3g1atEgRRnlRsSA8eyelkXpFfa+hULkkDx6hlnJZrW3\n" +
            "Hbs8/awQIUHyn/HVrp/8Aft0oMAVPEGmi1dG7/9ynTNuEFlDmpa1V8MStVEdeAMO\n" +
            "uW949kCZPwKaM70sYtLG6hPgG3+oMVFbOrka5AcCgYEArGgAN1b9EtjIyRmmr1n+\n" +
            "vc6wyXP4K5e9vOh67NbclVB1b0/udvrOLj0hmFLlnci7GHrcC7vDPZoMPP57FJFx\n" +
            "6CDDIdZTbqNpZnjdj5iK9bSJ2n52COAkq/h2oBMu3usBWahIg7xV4oqY5yhEi8iJ\n" +
            "rfUdznw7Rc/A8RfZESJeQwM=\n" +
            "-----END PRIVATE KEY-----\n";
}
