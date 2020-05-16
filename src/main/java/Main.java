
import java.io.*;
//import io.pkts.Pcap;
//import io.pkts.packet.Packet;
//import org.jnetpcap.packet.JPacket;
//import org.jnetpcap.packet.JPacketHandler;
import org.pcap4j.core.*;
//import org.pcap4j.packet.Packet;
import org.pcap4j.packet.Packet;
import org.pcap4j.util.ByteArrays;

import java.io.File;
//import org.jnetpcap.Pcap;

import java.util.Arrays;
import java.util.concurrent.TimeoutException;


public class Main {

    static String folderpath = "C:\\Users\\1\\Downloads\\pcap\\";
    static private storage s = new storage();
    private static String hex;

    public static void main(String[] args) throws PcapNativeException {

//        //Позвволяем узнать о списке интерфейсов в системе
//        List<PcapIf> alldevs = new ArrayList<PcapIf>(); //Список устройств
//        StringBuilder errbuf1 = new StringBuilder(); // буфер для ошибк
//        int r = Pcap.findAllDevs(alldevs, errbuf1); //-1 или 0
//        if (r != Pcap.OK || alldevs.isEmpty()) { //Ok=0
//            System.err.printf("Can't read list of devices, error is %s", errbuf1.toString());
//            return;
//        }
//        System.out.println("Network devices found:");
//        for (int i = 0; i < alldevs.size(); i++) {
//            System.out.println("#" + i + ": " + alldevs.get(i).getName());
//        }
//        Scanner s = new Scanner(System.in);
//        int i = s.nextInt();
//        PcapIf netInterface = alldevs.get(i);//выбираем уст-во
//        String MAC=netInterface.getName();//Source adress
//        int promiscous = Pcap.MODE_PROMISCUOUS; //приём всех пакетов
//        int timeout = 1;
//        int snaplen = 64 * 1024; // количество данных  для захвата в каждом пакете 160 байт
//        //int dlt= PcapDLT.CONST_EN10MB; //DLT ethernet, т.к goose передаются через ethernet
//        Pcap pcap = Pcap.openLive(MAC, snaplen, promiscous, timeout, errbuf1);//захват
//        PcapPacketHandler<String> handler=new PcapPacketHandler<String>(){
//            @Override
//            public void nextPacket(PcapPacket packet, String s) {
//                byte[] data = packet.getByteArray(0, packet.size());
//
//            }
//        }
//        //PcapPacket pcapPacket=new PcapPacket(JMemory.POINTER); //указатель, наверно не нужен
//

        File file = new File(folderpath);
        File[] files = file.listFiles();

        for (File f : files) {

            String FILENAME = folderpath + f.getName();

            PcapHandle handle;

            try {
                handle = Pcaps.openOffline(FILENAME);
            } catch (PcapNativeException e) {
                handle = Pcaps.openOffline(FILENAME);
            }

            for (int i = 0; i < 1; i++) { //кол-во пакетов из pcap
                try {
                    Packet packet = handle.getNextPacketEx();
                    System.out.println(handle.getTimestamp());
                    System.out.println("goose " + packet.getPayload());
                    System.out.println("Ethr " + packet.getHeader());
                    byte[] head = (packet.getPayload().getRawData());
                    System.out.println(Arrays.toString(head));
                    hex = ByteArrays.toHexString(head, " ");
                    System.out.println(hex);

                    int[] bytes = new int[head.length];
                    for (int j = 0; j < head.length; j++) {
                        bytes[j] = head[j] & 0xFF;
                    }

                    System.out.println(Arrays.toString(bytes));
                    s.setAPPID(start_info(bytes, s.getLENGTH().length, 0));
                    s.setLENGTH(start_info(bytes, s.getLENGTH().length, 2));
                    s.setRESERVED1(start_info(bytes, s.getLENGTH().length, 4));
                    s.setRESERVED2(start_info(bytes, s.getLENGTH().length, 6));
                    s.setADPU_LENGTH(apdu_length_filler(bytes, s.getADPU_START_TAG(), s.getADPU_LENGTH().length));
                    s.setGOCB_REF_DATA(apdu_filler(bytes, s.getGOCB_REF_TAG(), s.getGOCB_REF_LENGTH()));
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

                } catch (NotOpenException | EOFException | TimeoutException e) {
                    e.printStackTrace();
                }
            }
            handle.close();
        }

    }

    static int[] start_info(int[] arr, int length, int j) {

        int[] temp = new int[length];
        for (int i = j; i < length + j; i++) {
            temp[i - j] = arr[i];
        }
        return temp;
    }

    static int[] apdu_filler(int[] arr, int tag, int len) {
        int[] temp = new int[len];

        for (int j = 0; j < arr.length; j++) {
            if (arr[j] == tag) {
                for (int i = 0; i < temp.length; i++) {
                    temp[i] = arr[j + 2];
                    j++;
                }
            }
        }
        return temp;
    }

    static int[] apdu_length_filler(int[] arr, int tag, int len) {
        int[] temp = new int[len];

        for (int j = 0; j < arr.length; j++) {
            if (arr[j] == tag) {
                for (int i = 0; i < temp.length; i++) {
                    temp[i] = arr[j + 1];
                    j++;
                }
            }
        }
        return temp;
    }
}