package com.example.expensetracker.util;

import android.content.Context;

public class Util {

    private static int[] ids = new int[1000];
    private static int id = 0;

    public static int generateID(){
        ids[id]=id;
        return id++;
    }

    public static float PxToDp(Context context, float px) {
        return px / context.getResources().getDisplayMetrics().density;
    }

    public static float DpToPx(Context context, float dp) {
        return dp * context.getResources().getDisplayMetrics().density;
    }

}
