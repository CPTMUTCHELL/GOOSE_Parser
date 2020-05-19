package goose;

import org.pcap4j.core.*;
import org.pcap4j.packet.Packet;
import org.pcap4j.util.ByteArrays;

import java.io.EOFException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;


public class Capturer {


    //    static String folderpath = "C:\\Users\\1\\Downloads\\pcap\\";
     private Storage s = new Storage();
     private Parser p=new Parser(s);
      String hex;
      int count;

//      public PcapNetworkInterface devs() throws PcapNativeException {
//          List<PcapNetworkInterface> devs = Pcaps.findAllDevs();
//      }
//Делаем список devs , через кнопку выбираем, передаем dev в capture

    public void capture() throws PcapNativeException, EOFException, TimeoutException, NotOpenException {

//        File file = new File(folderpath);
//        File[] files = file.listFiles();

        List<PcapNetworkInterface> devs = Pcaps.findAllDevs();
        System.out.println("Network devices found:");
        for (int i = 0; i < devs.size(); i++) {
            System.out.println("#" + i + ": " + devs.get(i).getName());

        }

        Scanner sc = new Scanner(System.in);
        int i = sc.nextInt();
        PcapNetworkInterface in = devs.get(i);
        System.out.println("device "+in);

        PcapHandle handle=in.openLive(65536, PcapNetworkInterface.PromiscuousMode.PROMISCUOUS,10);
        String filter = "ether proto 0x88b8"; //Capture only Ethernet type ether proto 0x88b8
        handle.setFilter(filter, BpfProgram.BpfCompileMode.OPTIMIZE);

        PacketListener listener = new PacketListener() {
            @Override
            public void gotPacket(Packet packet) {
                System.out.println("Packet "+ count +" received: "+ packet);
                System.out.println("Timestamp: "+handle.getTimestamp());
                byte[] head = (packet.getPayload().getRawData());
                hex = ByteArrays.toHexString(head, " ");
                System.out.println(hex );
                int[] bytes = new int[head.length];
                for (int j = 0; j < head.length; j++) {
                    bytes[j] = head[j] & 0xFF;
                }
                System.out.println("bait "+Arrays.toString(bytes));
                s.setPacket(packet);
                s.setAPPID(start_info(bytes, s.getLENGTH().length, 0));
                s.setLENGTH(start_info(bytes, s.getLENGTH().length, 2));
                s.setRESERVED1(start_info(bytes, s.getLENGTH().length, 4));
                s.setRESERVED2(start_info(bytes, s.getLENGTH().length, 6));
                s.setApdu_length(apdu_length(bytes));
                s.setADPU_LENGTH(apdu_length_filler(bytes));
                s.setGOCB_REF_DATA(apdu_filler(bytes, s.getGOCB_REF_TAG(), s.getGOCB_REF_LENGTH()));
                s.setTIME_ALLOWED_TO_LIVE_LENGTH(length(bytes,s.getTIME_ALLOWED_TO_LIVE_TAG(),s.getDAT_SET_TAG()));
                s.setTIME_ALLOWED_TO_LIVE_DATA(apdu_filler(bytes, s.getTIME_ALLOWED_TO_LIVE_TAG(), s.getTIME_ALLOWED_TO_LIVE_LENGTH()));
                s.setDAT_SET_DATA(apdu_filler(bytes,s.getDAT_SET_TAG(),s.getDAT_SET_LENGTH()));
                s.setGO_ID_DATA(apdu_filler(bytes,s.getGO_ID_TAG(),s.getGO_ID_LENGTH()));
                s.setTIME_DATA(apdu_filler(bytes,s.getTIME_TAG(),s.getTIME_LENGTH()));
                s.setST_NUM_DATA(apdu_filler(bytes,s.getST_NUM_TAG(),s.getST_NUM_LENGTH()));
                s.setSQ_NUM_DATA(apdu_filler(bytes,s.getSQ_NUM_TAG(),s.getSQ_NUM_LENGTH()));
                s.setTIME_DATA(apdu_filler(bytes,s.getTIME_TAG(),s.getTIME_LENGTH()));
                s.setCONF_REF_DATA(apdu_filler(bytes,s.getCONF_REF_TAG(),s.getCONF_REF_LENGTH()));
                s.setNDS_COM_DATA(apdu_filler(bytes,s.getNDS_COM_TAG(),s.getNDS_COM_LENGTH()));
                s.setNUM_DAT_SET_ENTRIES_DATA(apdu_filler(bytes,s.getNUM_DAT_SET_ENTRIES_TAG(),s.getNUM_DAT_SET_ENTRIES_LENGTH()));
                s.setALL_DATA_DATA(apdu_filler(bytes,s.getALL_DATA_TAG(),s.getALL_DATA_LENGTH()));
                count++;
                p.show();
                System.out.println();
            }
        };
        try {
            int maxPackets = 50;
            handle.loop(maxPackets, listener);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        handle.close();
    }



    private int[] start_info(int[] arr, int length, int j) {

        int[] temp = new int[length];
        for (int i = j; i < length + j; i++) {
            temp[i - j] = arr[i];
        }
        return temp;
    }

     int[] apdu_filler(int[] arr, int tag, int len) {
        int[] temp = new int[len];
        int i;
        for (i = 0; i <arr.length ; i++) {
            if (arr[i]==s.getGOCB_REF_TAG()) break; // start with 0x80
        }
        for (int j=i;  j < arr.length; j++) {
            if (arr[j] == tag) {
                for (int t = 0; t < temp.length; t++) {
                    temp[t] = arr[j + 2];
                    j++;
                }
                break;
            }
        }
        return temp;
    }
    private int length(int[]arr, int start, int end){
        int i=0; int a=0; int b=0;
        for (i = 0; i <arr.length ; i++) {
            if (arr[i]==s.getGOCB_REF_TAG()) break;// start with 0x80

        }
        for (a=i; a <arr.length ; a++) {
            if (arr[a]==start) break;
        }
        for (b = i; b <arr.length ; b++) {
            if (arr[b]==end) break;
        }
        return b-a-2;
    }



    private int[] apdu_length_filler(int[] arr) { //to get the length of APDU
        int[] temp = new int[s.getApdu_length()];
        for (int j = 0; j < arr.length; j++) {
            if (arr[j] == s.getADPU_START_TAG()) {
                for (int i = 0; i < temp.length; i++) {
                    temp[i] = arr[j + 1];
                    j++;
                }
                break;
            }

        }
        return temp;
    }
    private int apdu_length(int[]arr){ //to get the number of bytes for apdu length
        int i=0; int j=0;
        for (i = 0; i <arr.length ; i++) {
            if (arr[i]==s.getADPU_START_TAG()) break;
        }
        for (j = 0; j <arr.length ; j++) {
            if (arr[j]==s.getGOCB_REF_TAG()) break;
        }
        return j-i-1;
    }

}