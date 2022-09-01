package com.starbotapi.caldera.util;

import java.util.HashMap;

public class SymbolUtil {

    public static HashMap<String,String> globalSymbols = new HashMap<>();
    public static HashMap<String,String> javaSymbols = new HashMap<>();
    public static HashMap<String,String> bedrockSymbols = new HashMap<>();


    public static void init() {
        globalSymbols.put("%health-icon%","❤");
        javaSymbols.put("%defense-icon%","\uD83D\uDEE1");
        bedrockSymbols.put("%defense-icon%","Defense");
        globalSymbols.put("%mana-icon%","✹");
        globalSymbols.put("%stamina-icon%","≫");
    }

    public static String applySymbols(String s, String platform) {
        if(platform.equals("JAVA")) {
            for(String sym : globalSymbols.keySet()) {
                s = s.replace(sym,globalSymbols.get(sym));
            }
            for(String sym : javaSymbols.keySet()) {
                s = s.replace(sym,javaSymbols.get(sym));
            }
            return s;
        } else if(platform.equals("BEDROCK")) {
            for(String sym : globalSymbols.keySet()) {
                s = s.replace(sym,globalSymbols.get(sym));
            }
            for(String sym : bedrockSymbols.keySet()) {
                s = s.replace(sym,bedrockSymbols.get(sym));
            }
            return s;
        } else {
            for(String sym : globalSymbols.keySet()) {
                s = s.replace(sym,globalSymbols.get(sym));
            }
            return s;
        }
    }

}
