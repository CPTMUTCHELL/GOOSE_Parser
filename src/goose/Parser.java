package goose;

import java.sql.Timestamp;
import java.util.Arrays;

public class Parser {
    private Storage s;

    public Parser(Storage s) {
        this.s = s;
    }

    void show(){
        System.out.println("APPID "+ Arrays.toString(s.getAPPID()));
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
    }

    private String Hex_to_ASCII(int[]arr){
        StringBuilder stringArray= new StringBuilder();
        StringBuilder output = new StringBuilder("");

        for (int i = 0; i <arr.length ; i++) {
            stringArray.append(Integer.toHexString(arr[i]));
        }
        for (int i = 0; i < stringArray.length(); i += 2) {
            String str = stringArray.substring(i, i + 2);
            output.append((char) Integer.parseInt(str, 16));
        }
        return output.toString();
    }
    private int Hex_to_INT(int[]arr){
        StringBuilder stringArray= new StringBuilder();
        for (int i = 0; i <arr.length ; i++) {
            stringArray.append(Integer.toHexString(arr[i]));
        }
        int integer=Integer.parseInt(String.valueOf(stringArray),16);
        return integer;
    }
    private Timestamp Hex_to_time(int[]arr){
        StringBuilder stringArray= new StringBuilder();

        for (int i = 0; i <arr.length ; i++) {
            stringArray.append(Integer.toHexString(arr[i]));
        }
        java.util.Date date=new java.util.Date(Long.parseLong(String.valueOf(stringArray), 16));
        Timestamp ts=new Timestamp(date.getTime());

        return ts;
    }
    private boolean Hex_to_bool(int[]arr){
        StringBuilder stringArray= new StringBuilder();
        boolean bool;
        for (int i = 0; i <arr.length ; i++) {
            stringArray.append(Integer.toHexString(arr[i]));
        }
        if (arr[0]==0) bool=false;
        else bool=true;
        return bool;
    }
}
