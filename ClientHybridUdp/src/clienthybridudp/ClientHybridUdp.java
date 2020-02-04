/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clienthybridudp;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.Scanner;

/**
 *
 * @author User
 */
public class ClientHybridUdp {
    static int clientPort=2427;
    static int clientToServerPort=2427;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws SocketException {
        System.out.println("Udp Client Started...........");
        DatagramSocket ds=new DatagramSocket(clientPort);
        Thread mySender=new MySender(ds);
        mySender.start();
        Thread myReceiver=new MyReceiver(ds);
        myReceiver.start();
       
    }

    private static class MySender extends Thread {
        DatagramSocket ds;
        public MySender(DatagramSocket ds) {
            this.ds=ds;
        }

        @Override
        public void run() {
            try {
                    String m="52514e542031202a406761746577617934342e6d79706c6163652e636f6d204d47435020302e310d0a523a206c2f6864286e290d0a583a20320d0a0d0a";
                    
                    byte[] b1=Utility.hexStringToByteArray(m);
//                    byte[] c = new byte[b1.length];
                    
                    for(int j=0;j<b1.length;j++){
                        System.out.print(j+"-> "+b1[j]+" ");
                    }
                    System.out.println();
                    
//                    byte[] c = new byte[b.length + b1.length];
//                    System.arraycopy(b, 0, c, 0, b.length);
//                    System.arraycopy(b1, 0, c, b.length, b1.length);
                    
                    
                    InetAddress ia=InetAddress.getByName("191.96.12.12");
//                    InetAddress ia=InetAddress.getByName("localhost");
                    DatagramPacket dp=new DatagramPacket(b1, b1.length,ia,clientToServerPort);
                    ds.send(dp);
                    String message=new String(dp.getData(),0,dp.getLength());
                    
                    System.out.println("send from client---> : "+message);
                
            } catch (Exception e) {
                e.printStackTrace();
            }
            
        }
        
        
    }

    private static class MyReceiver extends Thread {
        DatagramSocket ds;
        public MyReceiver(DatagramSocket ds) {
            this.ds=ds;
        }

        @Override
        public void run() {
            
            try {
                while(true){
                    byte[] b1= new byte[2048];
                    DatagramPacket dp1=new DatagramPacket(b1, b1.length);
                    ds.receive(dp1);
                    
                    String received= new String(dp1.getData(),0,b1.length);
                    System.out.println("Received at client:-->  "+ received);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            
        }

    }
}
