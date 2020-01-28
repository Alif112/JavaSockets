package com.example.user.bytegeneratortcp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.io.InputStream;
import java.io.OutputStream;

import java.net.Socket;
import java.net.SocketException;

public class MainActivity extends AppCompatActivity {
    public static int offset,len;
    public static boolean check=true;

    public EditText et1,et2;
    public static TextView textView1;
    public static TextView textView2;
    public static int sendCount=0;
    public static int receivedCount=0;

    public static Socket socket;
    OutputStream os;
    InputStream is;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        et1 = (EditText) findViewById(R.id.editText1);
        et2 = (EditText) findViewById(R.id.editText2);
        textView1 = (TextView) findViewById(R.id.textView1);
        textView2 = (TextView) findViewById(R.id.textView2);

    }


    void send_data(View v) throws SocketException {
        sendCount=0;
        receivedCount=0;
        offset=Integer.parseInt(et1.getText().toString());
        len=Integer.parseInt(et2.getText().toString());

        Thread mySender=new MySender();
        mySender.start();

    }






    class MySender extends Thread {


        @Override
        public void run() {

            try {
                socket=new Socket("191.96.12.69",6000);
                os = socket.getOutputStream();
                is = socket.getInputStream();
                Thread myReceiver=new MyReceiver(socket,is);
                myReceiver.start();



                socket.setTcpNoDelay(true);
                System.out.println("----------------> Socket established");
                int i=0;
                byte[] message=new byte[2048];
                while (i<25){
                    int size=Utility.getRandomData(message,0,len);
                    byte [] data = new byte[len + 2];
                    data[0] = (byte)((len >> 8) & 0xff);
                    data[1] = (byte)((len & 0xff));

                    System.arraycopy(message, 0, data, 2, len);

                    os.write(data);
                    System.out.println("-----------> Send Data From Client<--------");
                    sendCount=sendCount+len;
                    System.out.println("Send Count---> "+sendCount);
                    Thread.sleep(300);
                    i++;
                }

            }catch (Exception e){e.printStackTrace();}

        }


    }

    class MyReceiver extends Thread {
        Socket socket;
        InputStream is;
        public MyReceiver(Socket socket,InputStream is) {
            this.socket=socket;
            this.is=is;
        }

        @Override
        public void run() {
            try{

                byte[] data=new byte[2048];
                while(true) {
                    int firstByte = is.read();
                    int secondByte = is.read();
                    System.out.println("firstbyte "+firstByte+" ---Second byte-- "+secondByte);
                    int length = (firstByte << 8) | secondByte;
                    System.out.println("----> Receiving at client data of length: " + length);
                    is.read(data, 0, length);
                    if(length<0) continue;
                    receivedCount=receivedCount+length;
                    System.out.println("Received Count---> "+receivedCount);

                    String message=Utility.bytesToHex(data,0,length);

                    System.out.println(message);
                    //is.close();
                }

            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }


}
