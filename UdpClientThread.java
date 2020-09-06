package com.example.udptest;
import android.util.Log;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class UdpClientThread extends Thread  {
    //debug
    private int packageSeqNum=0;
    private int packageNum=0;
    //缓冲字节数组
    private byte []bytes=new byte[1024];
    //传输速度 单位kb/s
    private double speed;
    //每个包间隔时间
    private  long sleeptimeL;
    private  double sleeptimeD;
    //    //IP地址
    private String mAddress;
    //端口
    private int port;
    //发送内容
    private String filePath;
    private static String TAG="<<<<<<<<<";
    private File file;
    private DatagramSocket socket;
    private FileInputStream fis;

    public UdpClientThread(String address, int port, String filePath ,double speed) {
        this.mAddress = address;
        this.port = port;
        this.filePath = filePath;
        this.speed=speed;
    }

    @Override
    public void run() {
        super.run();
        try {
             socket= new DatagramSocket();
        } catch (SocketException e) {
            e.printStackTrace();
        }
        try {
            file = new File(filePath);
            fis = new FileInputStream(file);
            sleeptimeD=1/speed*1000;
            sleeptimeL=Math.round(sleeptimeD);
            packageNum=Math.round(file.length()/1024);
            Log.i(TAG,"总的包数"+packageNum);
            Log.i(TAG, "sleepTime"+sleeptimeD+"  "+sleeptimeL);
            Log.i(TAG,"文件大小为"+file.length());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Log.i(TAG, "开始发送。。。。。。。。");
        while(true) {
            try {
                if ((fis.read(bytes)==-1)) {
                    Log.i(TAG, "发送结束");
                    Log.i(TAG,"包数"+packageSeqNum);
                    socket.close();
                    break;}
                else {
                    //Thread.sleep(sleeptimeL);
//                    由于Thread.sleep()里面参数最小只能为1
//                    if(sleeptimeD>1) {
//                        Thread.sleep(sleeptimeL);
//                    }
//                    else{
//                        if(packageSeqNum<packageNum*sleeptimeD){
//                            Thread.sleep(1);
//                        }
//                    }
//                    packageSeqNum++;
                    sendSocket();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
//            catch (InterruptedException e) {
//                e.printStackTrace();
//            }
        }
    }
    private void sendSocket(){
            try {
                Log.i(TAG, "sendSocket: 发送发送");
                /*******************发送数据***********************/
                InetAddress address = InetAddress.getByName(mAddress);
                DatagramPacket packet = new DatagramPacket(bytes, bytes.length, address, port);
                socket.send(packet);
            } catch (UnknownHostException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
    }
}
