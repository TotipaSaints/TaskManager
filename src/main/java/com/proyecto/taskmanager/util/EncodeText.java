package com.proyecto.taskmanager.util;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class EncodeText {

    public static String encriptarText(String data) {
        return Base64.getEncoder().encodeToString(data.getBytes(StandardCharsets.UTF_8));
    }

    public static String desencriptarText(String data) {
        byte[] decodificado = Base64.getDecoder().decode(data);
        return new String(decodificado);
    }
}
