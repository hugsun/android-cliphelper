package com.qaai.cliphelper;


public final class ClipHelper {

    private static final ServiceManager SERVICE_MANAGER = new ServiceManager();

    private ClipHelper() {
        // not instantiable
    }

    public static void main(String... args) throws Exception {
        System.out.println(args[0]);
        ClipboardManager clipboardManager = SERVICE_MANAGER.getClipboardManager();
        clipboardManager.setText(args[0]);
    }
}
