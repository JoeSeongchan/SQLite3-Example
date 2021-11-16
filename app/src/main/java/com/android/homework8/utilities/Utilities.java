package com.android.homework8.utilities;

import android.util.Log;

public final class Utilities {

  public static String makeFuncTag() {
    String curFuncName = Thread.currentThread().getStackTrace()[3].getMethodName();
    return " - " + curFuncName + "\n";
  }

  public static String extractUpper(String full) {
    StringBuilder builder = new StringBuilder();

    for (int i = 0; i < full.length(); i++) {
      char c = full.charAt(i);
      if ((int) c >= 65 && (int) c <= 90) {
        builder.append(c);
      }
    }

    return builder.toString();
  }

  // function to log.
  public static void log(LogType type, String msg) {
    String callerClassName = Thread.currentThread().getStackTrace()[3].getClassName();
    int lastIndexOfDot = callerClassName.lastIndexOf(".");
    int endIdx = callerClassName.length();
    final String TAG = callerClassName.substring(lastIndexOfDot, endIdx) + "_R";
    String callerFuncName = Thread.currentThread().getStackTrace()[3].getMethodName();
    final String FUNC_TAG = "FN : " + callerFuncName + "\n";
    final String MSG = "MSG : " + msg;
    switch (type) {
      case d:
      default:
        Log.d(TAG, FUNC_TAG + MSG);
        break;
      case i:
        Log.i(TAG, FUNC_TAG + MSG);
        break;
      case w:
        Log.w(TAG, FUNC_TAG + MSG);
        break;
    }
  }

  // function to make log string.
  public static String makeLog(String msg) {
    String callerClassName = Thread.currentThread().getStackTrace()[3].getClassName();
    int lastIndexOfDot = callerClassName.lastIndexOf(".");
    int endIdx = callerClassName.length();
    final String TAG = callerClassName.substring(lastIndexOfDot, endIdx) + "_R\n";
    String callerFuncName = Thread.currentThread().getStackTrace()[3].getMethodName();
    final String FUNC_TAG = "FN : " + callerFuncName + "\n";
    final String MSG = "MSG : " + msg;
    return TAG + FUNC_TAG + MSG;
  }

  public enum LogType {i, d, w}
}
