package app.youse.com.reddit.utils;

/**
 * Created by heitornascimento on 11/5/16.
 */

public class UrlUtil {

    public static String formatString(String url) {
        String formatString = url.replace("amp;", "");
        return formatString;
    }
}
