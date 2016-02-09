package com.pyler.nodevicecheck;

import android.content.Context;
import android.util.Log;

import org.json.JSONObject;

import java.io.File;
import java.util.Map;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam;

public class NoDeviceCheck implements IXposedHookLoadPackage {
    public String[] keyWords = {"/su", "XposedBridge", "xposed", "xposed.installer", "app_process32_", "app_process64_", "supolicy", "sukernel", "libsupol.so", "SuperSUDaemon", "daemonsu", "Superuser", "chatter.pie", "libxposed"};
    public static final String CLASS_DROIDGUARD = "com.google.ccc.abuse.droidguard.DroidGuard";


    public boolean checkFileBool(File file, String packagename, String checktype) {
        for (int i = 0; i < keyWords.length; i++) {
            if (file.toString().contains(keyWords[i])) {
                if ((!file.toString().contains("sum")) && (!file.toString().contains("sub")) && (!file.toString().contains("surface"))) {
                    Log.d("NoDeviceCheck", "Found matching string - " + file + ". Caller: " + packagename + ". Checktype: " + checktype);
                    XposedBridge.log("NoDeviceCheck: Found matching string - " + file + ". Caller: " + packagename + ". Checktype: " + checktype);
                    return true;
                }
            }
        }
        return false;

    }




    public boolean checkFileStringArray(File[] file, String packagename, String checktype) {
        for (File files : file)
            for (int i = 0; i < keyWords.length; i++) {
                if (file.toString().contains(keyWords[i])) {
                    Log.d("NoDeviceCheck", "Found matching string - " + file + ". Caller: " + packagename + ". Checktype: " + checktype);
                    return true;
                }
            }
        return false;

    }

    public static void logHookAfter(XC_MethodHook.MethodHookParam param, File file) {
        Log.d("NoDeviceCheck", "Method hook executed (" + param.method.getName() + ") for file " + file + ", post-result=" + param.getResult());
    }

    @Override
    public void handleLoadPackage(final LoadPackageParam lpparam) throws Throwable {
        XposedBridge.log("NoDeviceCheck: We got droidguard apk");
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

        if (("android".equals(lpparam.packageName)) || ("droidguard".contains(lpparam.packageName)) || ("google.android.gms".contains(lpparam.packageName)) || ("walletnfcrel".contains(lpparam.packageName))) {

//            Begin various boolean checks on file class

            XposedHelpers.findAndHookMethod(File.class, "exists",
                    new XC_MethodHook() {


                        @Override
                        protected void beforeHookedMethod(MethodHookParam param)
                                throws Throwable {
                            File file = (File) param.thisObject;

                            // Disable check for enforced SELinux state
                            if (new File("/sys/fs/selinux/enforce").equals(file)) {
                                param.setResult(true);
                                logHookAfter(param, file);
                                return;
                            }

                            if (checkFileBool(file, lpparam.packageName, "exists")) {
                                param.setResult(false);
                                logHookAfter(param, file);


                            }
                        }




                    });

            XposedHelpers.findAndHookMethod(File.class, "canExecute",
                    new XC_MethodHook() {
                        @Override
                        protected void beforeHookedMethod(MethodHookParam param)
                                throws Throwable {
                            File file = (File) param.thisObject;
                            if (checkFileBool(file, lpparam.packageName, "canExecute")) {
                                param.setResult(false);
                                logHookAfter(param, file);

                            }
                        }


                    });

            XposedHelpers.findAndHookMethod(File.class, "canRead",
                    new XC_MethodHook() {
                        @Override
                        protected void beforeHookedMethod(MethodHookParam param)
                                throws Throwable {
                            File file = (File) param.thisObject;
                            if (checkFileBool(file, lpparam.packageName, "canRead")) {
                                param.setResult(false);
                                logHookAfter(param, file);

                            }
                        }
                    });

            XposedHelpers.findAndHookMethod(File.class, "canWrite",
                    new XC_MethodHook() {
                        @Override
                        protected void beforeHookedMethod(MethodHookParam param)
                                throws Throwable {
                            File file = (File) param.thisObject;
                            if (checkFileBool(file, lpparam.packageName, "canWrite")) {
                                param.setResult(false);
                                logHookAfter(param, file);

                            }
                        }
                    });

            XposedHelpers.findAndHookMethod(File.class, "createNewFile",
                    new XC_MethodHook() {
                        @Override
                        protected void beforeHookedMethod(MethodHookParam param)
                                throws Throwable {
                            File file = (File) param.thisObject;
                            if (checkFileBool(file, lpparam.packageName, "createNewFile")) {
                                param.setResult(false);
                                logHookAfter(param, file);

                            }
                        }
                    });

            XposedHelpers.findAndHookMethod(File.class, "isDirectory",
                    new XC_MethodHook() {
                        @Override
                        protected void beforeHookedMethod(MethodHookParam param)
                                throws Throwable {
                            File file = (File) param.thisObject;
                            if (checkFileBool(file, lpparam.packageName, "isDirectory")) {
                                param.setResult(false);
                                logHookAfter(param, file);

                            }
                        }
                    });

            XposedHelpers.findAndHookMethod(File.class, "isFile",
                    new XC_MethodHook() {
                        @Override
                        protected void beforeHookedMethod(MethodHookParam param)
                                throws Throwable {
                            File file = (File) param.thisObject;
                            if (checkFileBool(file, lpparam.packageName, "isFile")) {
                                param.setResult(false);
                                logHookAfter(param, file);

                            }
                        }
                    });

// #################################### End Boolean Check

// #################################### Begin String Checks


            XposedHelpers.findAndHookMethod(File.class, "getAbsolutePath",
                    new XC_MethodHook() {
                        @Override
                        protected void beforeHookedMethod(MethodHookParam param)
                                throws Throwable {
                            File file = (File) param.thisObject;
                            if (checkFileBool(file, lpparam.packageName, "getAbsolutePath")) {
                                param.setResult("");
                                logHookAfter(param, file);

                            }
                        }
                    });


            XposedHelpers.findAndHookMethod(File.class, "getName",
                    new XC_MethodHook() {
                        @Override
                        protected void beforeHookedMethod(MethodHookParam param)
                                throws Throwable {
                            File file = (File) param.thisObject;
                            if (checkFileBool(file, lpparam.packageName, "getName")) {
                                param.setResult("");
                                logHookAfter(param, file);
                            }
                        }
                    });

            XposedHelpers.findAndHookMethod(File.class, "getParent",
                    new XC_MethodHook() {
                        @Override
                        protected void beforeHookedMethod(MethodHookParam param)
                                throws Throwable {
                            File file = (File) param.thisObject;
                            if (checkFileBool(file, lpparam.packageName, "getParent")) {
                                param.setResult("");
                                logHookAfter(param, file);

                            }
                        }
                    });

            XposedHelpers.findAndHookMethod(File.class, "getPath",
                    new XC_MethodHook() {
                        @Override
                        protected void beforeHookedMethod(MethodHookParam param)
                                throws Throwable {
                            File file = (File) param.thisObject;
                            if (checkFileBool(file, lpparam.packageName, "getPath")) {
                                param.setResult("");
                                logHookAfter(param, file);

                            }
                        }
                    });


            XposedHelpers.findAndHookMethod(File.class, "listFiles",
                    new XC_MethodHook() {
                        @Override
                        protected void beforeHookedMethod(MethodHookParam param)
                                throws Throwable {
                            File file = (File) param.thisObject;
                            Log.v("NoDeviceCheck:", "File checked (listFiles): " + file.toString() + " and result is " + param.getResult() + " Caller: " + lpparam.packageName);


                        }
                    });

            XposedHelpers.findAndHookMethod(File.class, "list",
                    new XC_MethodHook() {
                        @Override
                        protected void beforeHookedMethod(MethodHookParam param)
                                throws Throwable {
                            File file = (File) param.thisObject;
                            Log.v("NoDeviceCheck:", "File checked (list): " + file.toString() + " and result is " + param.getResult() + " Caller: " + lpparam.packageName);


                        }
                    });

            XposedHelpers.findAndHookMethod(File.class, "listRoots",
                    new XC_MethodHook() {
                        @Override
                        protected void beforeHookedMethod(MethodHookParam param)
                                throws Throwable {
                            File file = (File) param.thisObject;
                            Log.v("NoDeviceCheck:", "File checked (listRoots): " + file.toString() + " and result is " + param.getResult() + " Caller: " + lpparam.packageName);


                        }
                    });


        }

        XposedHelpers.findAndHookMethod(JSONObject.class, "getBoolean",
                String.class, new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param)
                            throws Throwable {
                        String name = (String) param.args[0];
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
                        XposedBridge.log("NoDeviceCheck: String checked is " + name + " and calling package is " + lpparam.packageName);
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
