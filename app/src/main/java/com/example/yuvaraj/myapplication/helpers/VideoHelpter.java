package com.example.yuvaraj.myapplication.helpers;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class VideoHelpter {

    static String pattern = "(?<=watch\\?v=|/videos/|embed\\/|youtu.be\\/|\\/v\\/|watch\\?v%3D|%2Fvideos%2F|embed%\u200C\u200B2F|youtu.be%2F|%2Fv%2F)[^#\\&\\?\\n]*";

    public static String getVideoId(String url)
    {
        Pattern compiledPattern = Pattern.compile(pattern);
        Matcher matcher = compiledPattern.matcher(url);
        if(matcher.find()){
            return matcher.group();
        }
        else
        {
            return null;
        }
    }


}
