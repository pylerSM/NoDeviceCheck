package com.pyler.nodevicecheck;

import android.content.Context;

import org.json.JSONObject;

import java.io.File;
import java.util.Map;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam;

public class NoDeviceCheck implements IXposedHookLoadPackage {
    public static final String CLASS_DROIDGUARD = "com.google.ccc.abulse.droidguard.DroidGuard";
    @Override
    public void handleLoadPackage(final LoadPackageParam lpparam) throws Throwable {
        XposedBridge.log("NoDeviceCheck: We got that fucking droidguard apk");
        if (lpparam.packageName.equals("com.google.ccc.abuse.droidguard")) {

            XposedHelpers.findAndHookMethod(CLASS_DROIDGUARD, lpparam.classLoader, "close",
                    new XC_MethodHook() {
                        @Override
                        protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                            XposedBridge.log("NoDeviceCheck: DroidGuard close Hooked");
                        }
                    });

            XposedHelpers.findAndHookMethod(CLASS_DROIDGUARD, lpparam.classLoader, "init",
                    new XC_MethodHook() {
                        @Override
                        protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                            XposedBridge.log("NoDeviceCheck: DroidGuard init Hooked.");
                        }

                        @Override
                        protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                            XposedBridge.log("NoDeviceCheck: DroidGuard init Hooked, result is " + param.getResult());
                        }
                    });

            XposedHelpers.findAndHookMethod(CLASS_DROIDGUARD, lpparam.classLoader, "run",
                    new XC_MethodHook() {
                        @Override
                        protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                            XposedBridge.log("NoDeviceCheck: DroidGuard run Hooked");
                        }
                    });

            XposedHelpers.findAndHookMethod(CLASS_DROIDGUARD, lpparam.classLoader, "ss", Map.class,
                    new XC_MethodHook() {
                        @Override
                        protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                            XposedBridge.log("NoDeviceCheck: DroidGuard ss Hooked, have some data: " + param.args[0]);
                        }
                    });

            XposedHelpers.findAndHookMethod(CLASS_DROIDGUARD, lpparam.classLoader, "a", Map.class,
                    new XC_MethodHook() {
                        @Override
                        protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                            XposedBridge.log("NoDeviceCheck: DroidGuard a Hooked, have some data: " + param.args[0]);
                        }
                    });

            XposedHelpers.findAndHookMethod(CLASS_DROIDGUARD, lpparam.classLoader, "closeNative", long.class,
                    new XC_MethodHook() {
                        @Override
                        protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                            XposedBridge.log("NoDeviceCheck: DroidGuard closeNative Hooked, have some data: " + param.args[0]);
                        }
                    });

            XposedHelpers.findAndHookMethod(CLASS_DROIDGUARD, lpparam.classLoader, "entryHelperNative", long.class,
                    new XC_MethodHook() {
                        @Override
                        protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                            XposedBridge.log("NoDeviceCheck: DroidGuard entryHelperNative Hooked, have some data: " + param.args[0]);
                        }
                    });

            XposedHelpers.findAndHookMethod(CLASS_DROIDGUARD, lpparam.classLoader, "initNative", Context.class, String.class, byte[].class, Object.class,
                    new XC_MethodHook() {
                        @Override
                        protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                            XposedBridge.log("NoDeviceCheck: DroidGuard initNative Hooked, have some data: " + param.args[0] + " and " + param.args[1] + " and " + param.args[2] + " and " + param.args[3]);
                        }
                    });

            XposedHelpers.findAndHookMethod(CLASS_DROIDGUARD, lpparam.classLoader, "runNative", Context.class, String.class, byte[].class, String[].class, Object.class,
                    new XC_MethodHook() {
                        @Override
                        protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                            XposedBridge.log("NoDeviceCheck: DroidGuard initNative Hooked, have some data: " + param.args[0] + " and " + param.args[1] + " and " + param.args[2] + " and " + param.args[3] + " and " + param.args[4]);
                        }
                    });

            XposedHelpers.findAndHookMethod(CLASS_DROIDGUARD, lpparam.classLoader, "ssNative", long.class, Context.class, String.class, Object.class,
                    new XC_MethodHook() {
                        @Override
                        protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                            XposedBridge.log("NoDeviceCheck: DroidGuard ssNative Hooked, have some data: " + param.args[0] + " and " + param.args[1] + " and " + param.args[2] + " and " + param.args[3]);
                        }
                    });
        }
        if (("android".equals(lpparam.packageName)) || (lpparam.packageName.equals("com.google.android.gms")) || (lpparam.packageName.equals("com.google.android.apps.walletnfcrel")))  {
            XposedHelpers.findAndHookMethod(File.class, "exists",
                    new XC_MethodHook() {
                        @Override
                        protected void beforeHookedMethod(MethodHookParam param)
                                throws Throwable {
                            File file = (File) param.thisObject;

                            // Disable check for enforced SELinux state
                            if (new File("/sys/fs/selinux/enforce").equals(file)) {
                                param.setResult(true);
                                return;
                            }

                            // Disable check for SU binary files
                            if (new File("/system/bin/su").equals(file) || new File("/system/xbin/su").equals(file)) {
                                param.setResult(false);
                                return;
                            }
                            if (new File("/system/framework/XposedBridge.jar").equals(file)) {
                                param.setResult(false);
                                return;
                            }


                        }

                        @Override
                    protected void afterHookedMethod(MethodHookParam param)
                            throws Throwable {
                            File file = (File) param.thisObject;
                            XposedBridge.log("NoDeviceCheck: File checked is " + file.toString() + " and result is " + param.getResult());
                        }
                    });
        }

        XposedHelpers.findAndHookMethod(JSONObject.class, "getBoolean",
                String.class, new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param)
                            throws Throwable {
                        String name = (String) param.args[0];
                        XposedBridge.log("NoDeviceCheck: Boolean checked is " + name.toString() + " and calling package is " + lpparam.packageName);
                        // Modify server response to pass CTS check
                        if ("ctsProfileMatch".equals(name)
                                || "isValidSignature".equals(name)) {
                            param.setResult(true);
                            return;
                        }
                    }
                });

        XposedHelpers.findAndHookMethod(JSONObject.class, "getString",
                String.class, new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param)
                            throws Throwable {
                        String name = (String) param.args[0];
                        XposedBridge.log("NoDeviceCheck: String checked is " + name.toString() + " and calling package is " + lpparam.packageName);
                        // Modify server response to pass CTS check
                        if ("ctsProfileMatch".equals(name)
                                || "isValidSignature".equals(name)) {
                            param.setResult(true);
                            return;
                        }
                    }
                });
    }
}
