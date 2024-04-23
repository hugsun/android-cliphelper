package com.qaai.cliphelper;

import android.content.ClipData;
import android.os.Build;
import android.os.IInterface;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ClipboardManager {
    private final IInterface manager;
    private Method getPrimaryClipMethod;
    private Method setPrimaryClipMethod;
    private Method addPrimaryClipChangedListener;

    public ClipboardManager(IInterface manager) {
        this.manager = manager;
    }

    private Method getSetPrimaryClipMethod() throws NoSuchMethodException {
        if (setPrimaryClipMethod == null) {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
                setPrimaryClipMethod = manager.getClass().getMethod("setPrimaryClip", ClipData.class, String.class);
            } else {
                setPrimaryClipMethod = manager.getClass().getMethod("setPrimaryClip", ClipData.class, String.class, int.class);
            }
        }
        return setPrimaryClipMethod;
    }

    private static void setPrimaryClip(Method method, IInterface manager, ClipData clipData)
            throws InvocationTargetException, IllegalAccessException {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
            method.invoke(manager, clipData, ServiceManager.PACKAGE_NAME);
        } else {
            method.invoke(manager, clipData, ServiceManager.PACKAGE_NAME, ServiceManager.USER_ID);
        }
    }

    public boolean setText(CharSequence text) {
        try {
            Method method = getSetPrimaryClipMethod();
            ClipData clipData = ClipData.newPlainText(null, text);
            setPrimaryClip(method, manager, clipData);
            return true;
        } catch (InvocationTargetException | IllegalAccessException | NoSuchMethodException e) {
            System.out.println("Could not invoke method");
            return false;
        }
    }
}
