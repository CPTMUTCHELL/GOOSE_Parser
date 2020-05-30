package goose;

import java.util.Arrays;


public  class Parser {
    private String destination, source;

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    private Storage s;
    private String gocbRef,datSet,goID;
    private int appid;
    private int length;
    private int timeAllowedtoLive;
    private int sqNum,stNum;
    private int confRef;
    private int numdatSet;
    private int reserved1;
    private int reserved2;
    private boolean test, ndsCom;
    private int allData_items;
    private boolean[] bools;
    private int[] strings;

    public String getDatSet() {
        return datSet;
    }

    public String getGoID() {
        return goID;
    }

    public int getLength() {
        return length;
    }

    public int getSqNum() {
        return sqNum;
    }

    public int getStNum() {
        return stNum;
    }

    public int getConfRef() {
        return confRef;
    }

    public int getNumdatSet() {
        return numdatSet;
    }

    public int getReserved1() {
        return reserved1;
    }

    public int getReserved2() {
        return reserved2;
    }

    public boolean isTest() {
        return test;
    }

    public int getAllData_items() {
        return allData_items;
    }

    public boolean isNdsCom() {
        return ndsCom;
    }

    public String getGocbRef() {
        return gocbRef;
    }

      public  int getTimeAllowedtoLive() {
        return timeAllowedtoLive;
    }

    public int getAppid() {
        return appid;
    }

    public Parser(Storage s) {
        this.s = s;
    }

    public boolean[] getBools() {
        return bools;
    }

    public int[] getStrings() {
        return strings;
    }

    public void show(){
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
    private double Hex_to_time(int[]arr){
        StringBuilder stringArray= new StringBuilder();

        for (int i = 0; i <arr.length ; i++) {
            stringArray.append(Integer.toHexString(arr[i]));
        }
        System.out.println(stringArray);
        java.util.Date date=new java.util.Date(Long.parseLong(String.valueOf("386ebbf34217280a"),16));
//        ByteBuffer byteBuffer = ByteBuffer.allocate(s.getTIME_DATA().length * 4);
//        IntBuffer intBuffer = byteBuffer.asIntBuffer();
//        intBuffer.put(s.getTIME_DATA());
//
//        byte[] array = byteBuffer.array();
//        byte[] en = Base64.getEncoder().encode(array);
//        System.out.println(Arrays.toString(array));

        goose.Timestamp ts=new goose.Timestamp(date.getTime());

        return ts.getTimestamp();
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
