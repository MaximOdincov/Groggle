package com.example.todolist.data.mapper;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class CurrencyInfoService {
    private static final Map<String, String> CODE_TO_NAME;
    private static final Map<String, String> CODE_TO_SYMBOL;
    static {
        Map<String, String> names = new HashMap<>();
        Map<String, String> symbols = new HashMap<>();
        names.put("RUB", "Russian Rouble");
        symbols.put("RUB", "₽");
        names.put("USD", "US Dollar");
        symbols.put("USD", "$");
        names.put("EUR", "Euro");
        symbols.put("EUR", "€");
        names.put("GEL", "Georgian Lari");
        symbols.put("GEL", "₾");
        CODE_TO_NAME = Collections.unmodifiableMap(names);
        CODE_TO_SYMBOL = Collections.unmodifiableMap(symbols);
    }

    public static Collection<String> getAllCodes() {
        return CODE_TO_NAME.keySet();
    }

    public static String getCurrencyName(String code) {
        String name = CODE_TO_NAME.get(code);
        return name != null ? name : code;
    }

    public static String getCurrencySymbol(String code) {
        String symbol = CODE_TO_SYMBOL.get(code);
        return symbol != null ? symbol : code;
    }

    public static String getDisplayName(String code) {
        return getCurrencyName(code) + " (" + code + ")";
    }
}
