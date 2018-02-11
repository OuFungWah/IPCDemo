package com.example.ofw.ipcdemo.tools;

/**
 * Created by ofw on 2018/2/11.
 */

public class TimeFormatTools {

    public static String ms2HMS(int _ms){
        String HMStime = "";
        _ms/=1000;
        int sec = _ms%60;
        int min = (_ms/60)%60;
        int hour = _ms/3600;

        String secStr = sec<10?"0"+sec:""+sec;
        String minStr = min<10?"0"+min:""+min;
        String hourStr = hour<10?"0"+hour:""+hour;

        HMStime = hourStr+":"+minStr+":"+secStr;

        return  HMStime;
    }

    public static String ms2MS(int _ms){
        String HMStime = "";
        _ms/=1000;
        int sec = _ms%60;
        int min = _ms/60;

        String secStr = sec<10?"0"+sec:""+sec;
        String minStr = min<10?"0"+min:""+min;

        HMStime = minStr+":"+secStr;

        return  HMStime;
    }

}
