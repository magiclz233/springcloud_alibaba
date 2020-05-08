package com.magic.clear;

/**
 * @author magic_lz
 * @version 1.0
 * @name UrlCleaner
 * @description TODO
 * @date 2020/5/8   16:16
 **/
public class UrlCleaner {
    public static String clean(String url){
        System.out.println("enter UrlCleaner");
        if(url.matches(".*/echo/.*")){
            System.out.println("change url");
            url = url.replaceAll("/echo/.*", "/echo/{str}");
        }
        return url;
    }
}
