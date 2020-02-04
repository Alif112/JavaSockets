/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hybridudp;



/**
 *
 * @author User
 */
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.SocketException;
import sun.audio.AudioPlayer;

/**
 *
 * @author REVE
 */
public class HybridUdp {
    static int ServerPort=2427;
    static int ServerToClientPort=2427;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws SocketException {
        System.out.println("UDP Server Started Successfully");
        Thread t=new MyThread();
        t.start();

    }

    private static class MyThread extends Thread {

        public MyThread() {
        }

        @Override
        public void run() {
            try{
                DatagramSocket ds=new DatagramSocket(ServerPort, InetAddress.getByName("191.96.12.12"));
                byte[] b=new byte[2048];

                DatagramPacket dp=new DatagramPacket(b, b.length);
                DatagramPacket dp2 = new DatagramPacket(b, b.length);

                while(true){
                    ds.receive(dp);
                    String message=new String(dp.getData(),0,dp.getLength());
                    System.out.println(message);
                    System.out.println("Received at server--> "+message);
                    
                    //Backend coding
                    String m="52534950203331363536383630202a406761746577617934342e6d79706c6163652e636f6d204d47435020312e300a524d3a20726573746172740a";
                   
                   byte[] b1=Utility.hexStringToByteArray(m);

                    

                    dp2.setData(b1);
                    dp2.setAddress(dp.getAddress());
                    dp2.setPort(ServerToClientPort);
                    ds.send(dp2);

                    System.out.println("------sending from server to ip:port: "+dp.getAddress()+":"+ServerToClientPort);

                }



            }catch(Exception e){

                e.printStackTrace();
            }
        }
        
        
    }
    
}
