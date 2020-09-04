package util;

import org.jetbrains.annotations.NonNls;

public enum UrlFactory {

    BASE_URL("https://www.nesine.com"),

    POPULAR_BETS(BASE_URL, "/iddaa/populer-bahisler"),
    MY_BETS(BASE_URL, "/kuponlarim/iddaa");

    // -----

    public final @NonNls
    String pageUrl;

    UrlFactory(String pageUrl) {
        this.pageUrl = pageUrl;
    }

    UrlFactory(UrlFactory baseUrl, String pageUrl) {
        this.pageUrl = baseUrl.pageUrl + pageUrl;
    }
}
