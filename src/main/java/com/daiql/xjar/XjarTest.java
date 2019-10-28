package com.daiql.xjar;

import io.xjar.XConstants;
import io.xjar.XKit;
import io.xjar.boot.XBoot;
import io.xjar.key.XKey;

/**
 * @author daiql
 * @date 2019/10/26 13:30
 * @description jar包加密
 * jar加密后，大小会翻倍，同时swagger无法访问，接口暂时未发现问题
 */
public class XjarTest {

    public static void main(String[] args) {
        // Spring-Boot Jar包加密
        String password = "123456";
        String fromJarPath = "F:\\data-map-0.0.1-SNAPSHOT.jar";
        String toJarPath = "F:\\data-map-danger.jar";
        encryptJarDangerMode(fromJarPath, toJarPath, password);
    }

    /**
     * jar包加密，防止反编译
     * 此编译方式在运行jar包时需要输入密码
     * 运行方式一 ：
     *      // 命令行运行JAR 然后在提示输入密码的时候输入密码后按回车即可正常启动
     *      java -jar /path/to/encrypted.jar
     * 运行方式二：
     *      // 也可以通过传参的方式直接启动，不太推荐这种方式，因为泄露的可能性更大！
     *      java -jar /path/to/encrypted.jar --xjar.password=PASSWORD
     * 运行方式三：
     *      // 对于 nohup 或 javaw 这种后台启动方式，无法使用控制台来输入密码，推荐使用指定密钥文件的方式启动
     *      nohup java -jar /path/to/encrypted.jar --xjar.keyfile=/path/to/xjar.key
     *      xjar.key 文件说明：
     *      格式：
     *          password: PASSWORD
     *          algorithm: ALGORITHM
     *          keysize: KEYSIZE
     *          ivsize: IVSIZE
     *          hold: HOLD
     *      参数说明：
     *          password 	密码 	无 	密码字符串
     *          algorithm 	密钥算法 	AES 	支持JDK所有内置算法，如AES / DES ...
     *          keysize 	密钥长度 	128 	根据不同的算法选取不同的密钥长度。
     *          ivsize 	向量长度 	128 	根据不同的算法选取不同的向量长度。
     *          hold 	是否保留 	false 	读取后是否保留密钥文件。
     * @param fromJarPath 需要加密的jar
     * @param toJarPath 加密后的jar
     * @param password 加密密码
     */
    public static void encryptJar(String fromJarPath, String toJarPath, String password) {

        try {
            // Spring-Boot Jar包加密
            XKey xKey = XKit.key(password);
            XBoot.encrypt(fromJarPath, toJarPath, xKey);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * jar包解密
     * @param fromJarPath 已通过Xjar加密的jar文件路径
     * @param toJarPath 解密后的jar文件
     * @param password 密码
     */
    public static void decryptJar(String fromJarPath, String toJarPath, String password) {
        try {
            XKey xKey = XKit.key(password);
            XBoot.decrypt(fromJarPath, toJarPath, xKey);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * jar包危险加密模式
     * 即不需要输入密码即可启动的加密方式，这种方式META-INF/MANIFEST.MF中会保留密钥，请谨慎使用！
     * @param fromJarPath 需要加密的jar
     * @param toJarPath 加密后的jar
     * @param password 加密密码
     */
    public static void encryptJarDangerMode(String fromJarPath, String toJarPath, String password) {
        try {
            XKey xKey = XKit.key(password);
            XBoot.encrypt(fromJarPath, toJarPath, xKey, XConstants.MODE_DANGER);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
