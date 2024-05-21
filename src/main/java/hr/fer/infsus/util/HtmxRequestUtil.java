package hr.fer.infsus.util;

import java.util.Optional;

import jakarta.servlet.http.HttpServletRequest;

public class HtmxRequestUtil {

    public static boolean isHtmxRequest(HttpServletRequest request) {
        return request.getHeader("HX-Request") != null
                && !Optional.ofNullable(request.getHeader("HX-Boosted")).map(Boolean::parseBoolean).orElse(false);
    }
}
