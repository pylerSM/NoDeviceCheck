package com.pyler.nodevicecheck;

import org.json.JSONObject;

import java.io.File;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam;

public class NoDeviceCheck implements IXposedHookLoadPackage {
    @Override
    public void handleLoadPackage(LoadPackageParam lpparam) throws Throwable {
        if ("android".equals(lpparam.packageName)) {
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
                            if (new File("/system/framework/XposedBridge.jar").equals(file) || new File("/system/framework/XposedBridge.jar").equals(file)) {
                                param.setResult(false);
                                return;
                            }
                            if (new File("/system/framework/XposedBridge.jar").equals(file) || new File("/system/framework/XposedBridge.jar").equals(file)) {
                                param.setResult(false);
                                return;
                            }
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
    }
}
