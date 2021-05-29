package com.NativeMethod;

public class SpaceJniTest {

    static {
        // 静态方法加载本地动态连接库
        System.loadLibrary("MyNativeDl1");
    }

    public static native void callCppMethod();

    public static void main(String[] args) {
        System.out.println("DLL path:" + System.getProperty("java.library.path"));
        callCppMethod();
    }

}
