package com.jasho.gtps.utility;

import jakarta.servlet.http.HttpServletRequest;

/**
 * @author Jashobanta Patra
 * crated on 11-11-2024
 */
public class UrlUtil {
    public static String getApplicatiionUrl(HttpServletRequest request) {
        String appUrl = request.getRequestURL().toString();
        return appUrl.replace(request.getServletPath(), "");
    }
}
