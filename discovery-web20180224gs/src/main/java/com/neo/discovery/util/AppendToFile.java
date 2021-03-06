package com.neo.discovery.util;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
public class AppendToFile {


   static String fileName = "D:/newTemp2017-1128.txt";
    /**
     * A����׷���ļ���ʹ��RandomAccessFile
     */
    public static void appendMethodA( String content) {
        try {
            // ��һ����������ļ���������д��ʽ
            RandomAccessFile randomFile = new RandomAccessFile(fileName, "rw");
            // �ļ����ȣ��ֽ���
            long fileLength = randomFile.length();
            //��д�ļ�ָ���Ƶ��ļ�β��
            randomFile.seek(fileLength);
            randomFile.writeBytes(content);
            randomFile.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * A����׷���ļ���ʹ��RandomAccessFile
     */
    public static void appendMethodA(String fileName, String content) {
        try {
            // ��һ����������ļ���������д��ʽ
            RandomAccessFile randomFile = new RandomAccessFile(fileName, "rw");
            // �ļ����ȣ��ֽ���
            long fileLength = randomFile.length();
            //��д�ļ�ָ���Ƶ��ļ�β��
            randomFile.seek(fileLength);
            randomFile.writeBytes(content);
            randomFile.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * B����׷���ļ���ʹ��FileWriter
     */
    public static void appendMethodB(String fileName, String content) {
        try {
            //��һ��д�ļ��������캯���еĵڶ�������true��ʾ��׷����ʽд�ļ�
            FileWriter writer = new FileWriter(fileName, true);
            writer.write(content);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
//        String fileName = "E:/bifen30.txt";
        String content = "new append!";
        //������A׷���ļ�
        AppendToFile.appendMethodA(fileName, content);
        AppendToFile.appendMethodA(fileName, "\rappend end.");
        //��ʾ�ļ�����
//        ReadFromFile.readFileByBytes(fileName);//.readFileByLines(fileName);
       /* //������B׷���ļ�
        AppendToFile.appendMethodB(fileName, content);
        AppendToFile.appendMethodB(fileName, "append end. \n");
        //��ʾ�ļ�����
        ReadFromFile.readFileByBytes(fileName);*/
        // ReadFromFile.readFileByLines(fileName);
    }
}