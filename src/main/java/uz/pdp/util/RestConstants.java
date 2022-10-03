package uz.pdp.util;

import uz.pdp.controller.AuthController;

public interface RestConstants {
    String AUTHENTICATION_HEADER = "Authorization";

    String[] OPEN_PAGES = {
            "/*",
            AuthController.AUTH_CONTROLLER_BASE_PATH + "/**"
    };

    String SERVICE_BASE_PATH = "/api/auth/v1/";
}
