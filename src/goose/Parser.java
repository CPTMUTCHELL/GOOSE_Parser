package goose;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Data
@RequiredArgsConstructor
public  class Parser {
    private String destination, source;
    private final Storage s;
    @Getter
    private static String gocbRef,datSet,goID;
    @Getter
    private static int appid;
    @Getter
    private static int length;
    @Getter
    private static int timeAllowedtoLive;
    @Getter
    private static int sqNum,stNum;
    @Getter
    private static int confRef;
    @Getter
    private static int numdatSet;
    @Getter
    private static int reserved1;
    @Getter
    private static int reserved2;
    @Getter
    private static boolean test, ndsCom;
    @Getter
    private static int allData_items;
    @Getter
    private static boolean[] bools;
    @Getter
    private static int[] strings;


    public synchronized void show()  {
        appid=Hex_to_INT(s.getAPPID());
        length=Hex_to_INT(s.getLENGTH());
        reserved1=Hex_to_INT(s.getRESERVED1());
        reserved2=Hex_to_INT(s.getRESERVED2());
        gocbRef=Hex_to_ASCII(s.getGOCB_REF_DATA());
        timeAllowedtoLive=Hex_to_INT(s.getTIME_ALLOWED_TO_LIVE_DATA());
        datSet=Hex_to_ASCII(s.getDAT_SET_DATA());
        goID=Hex_to_ASCII(s.getGO_ID_DATA());
        stNum = Hex_to_INT(s.getST_NUM_DATA());
        sqNum=Hex_to_INT(s.getSQ_NUM_DATA());
        test=Hex_to_bool(s.getTEST_DATA());
        confRef=Hex_to_INT(s.getCONF_REF_DATA());
        ndsCom=Hex_to_bool(s.getNDS_COM_DATA());
        numdatSet=Hex_to_INT(s.getNUM_DAT_SET_ENTRIES_DATA());
        bools=byte_to_bool(allData(s.getALL_DATA_DATA(),131,1));
        strings=allData(s.getALL_DATA_DATA(),132,3);
        allData_items=bools.length+strings.length/3;

        System.out.println("gocbRef "+ Hex_to_ASCII(s.getGOCB_REF_DATA()));
        System.out.println("timeAllowedtoLive "+ Hex_to_INT(s.getTIME_ALLOWED_TO_LIVE_DATA()));
        System.out.println("datSet "+ Hex_to_ASCII(s.getDAT_SET_DATA()));
        System.out.println("goID "+ Hex_to_ASCII(s.getGO_ID_DATA()));
        System.out.println("t "+ Hex_to_time(s.getTIME_DATA()));
        System.out.println("stNum "+Hex_to_INT(s.getST_NUM_DATA()));
        System.out.println("sqNum "+Hex_to_INT(s.getSQ_NUM_DATA()));
        System.out.println("test "+Hex_to_bool(s.getTEST_DATA()));
        System.out.println("confRef "+Hex_to_INT(s.getCONF_REF_DATA()));
        System.out.println("ndsCom "+Hex_to_bool(s.getNDS_COM_DATA()));
        System.out.println("numdatSet "+ Hex_to_INT(s.getNUM_DAT_SET_ENTRIES_DATA()));
        System.out.println("allData "+ Arrays.toString(s.getALL_DATA_DATA()));
        System.out.println("alldatannum "+allData_items);
           System.out.println(Arrays.toString(bools));
           System.out.println(Arrays.toString(strings));

    }

    private String Hex_to_ASCII(int[]arr){
        StringBuilder hexString= new StringBuilder();
        StringBuilder output = new StringBuilder("");

        for (int i = 0; i <arr.length ; i++) {
            hexString.append(Integer.toHexString(arr[i]));
        }
        for (int i = 0; i < hexString.length(); i += 2) {
            String str = hexString.substring(i, i + 2);
            output.append((char) Integer.parseInt(str, 16));
        }
        return output.toString();
    }
    private int Hex_to_INT(int[]arr){
        StringBuilder hexString= new StringBuilder();
        for (int i = 0; i <arr.length ; i++) {
            hexString.append(Integer.toHexString(arr[i]));
        }
        int integer=Integer.parseInt(String.valueOf(hexString),16);
        return integer;
    }
    private double Hex_to_time(int[]arr) {
        //not working correctly
        StringBuilder hexString= new StringBuilder();
        for (int i = 0; i < arr.length; i++) {
            String hex = Integer.toHexString(0xFF & arr[i]);//to prevent losing leading zeroes
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        java.util.Date date=new java.util.Date(Long.parseLong(String.valueOf(hexString),16));
        goose.Timestamp ts=new goose.Timestamp(date.getTime());
        return ts.getTimestamp();
    }
    private boolean Hex_to_bool(int[]arr){
        StringBuilder hexString= new StringBuilder();
        boolean bool;
        for (int i = 0; i <arr.length ; i++) {
            hexString.append(Integer.toHexString(arr[i]));
        }
        if (arr[0]==0) bool=false;
        else bool=true;
        return bool;
    }
    private int[] allData(int[]arr, int tag, int len){
        int [] vals;
        int count=0;
        int j=0;
        for (int i = 0; i <arr.length ; i++) {
            if (arr[i]==tag) count++;
        }
        vals=new int[count*len];
        for (int i = 0; i <arr.length ; i++) {
            if (arr[i]==tag) {
                    for (int k = i; k<len+i ; k++) {
                        vals[j]= (byte) arr[k+2];
                        j++;

                    }
            }
        }
        return vals;
    }
    private boolean[] byte_to_bool(int [] arr){
        boolean [] val=new boolean[arr.length];
        for (int i = 0; i <arr.length ; i++) {
            if (arr[i]==0)val[i]=false;
            else val[i]=true;
        }
        return val;
    }
}
