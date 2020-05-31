package goose;


public class Storage {
    private int apdu_length;

    private int TIME_ALLOWED_TO_LIVE_LENGTH;
    private int GOCB_REF_LENGTH;

    public void setGOCB_REF_LENGTH(int GOCB_REF_LENGTH) {
        this.GOCB_REF_LENGTH = GOCB_REF_LENGTH;
    }

    public int getApdu_length() {
        return apdu_length;
    }

    public void setApdu_length(int apdu_length) {
        this.apdu_length = apdu_length;
    }

    public void setTIME_ALLOWED_TO_LIVE_LENGTH(int TIME_ALLOWED_TO_LIVE_LENGTH) {
        this.TIME_ALLOWED_TO_LIVE_LENGTH = TIME_ALLOWED_TO_LIVE_LENGTH;
    }

    private int[] APPID = new int[2];
    private int[] LENGTH = new int[2];
    private int[] RESERVED1 = new int[2];
    private int[] RESERVED2 = new int[2];

    private int ADPU_START_TAG = 0x61;
    private int[] ADPU_LENGTH = new int[apdu_length];

    private int GOCB_REF_TAG = 0x80;

    private int[] GOCB_REF_DATA = new int[GOCB_REF_LENGTH];

    private int TIME_ALLOWED_TO_LIVE_TAG = 0x81;

    private int[] TIME_ALLOWED_TO_LIVE_DATA = new int[TIME_ALLOWED_TO_LIVE_LENGTH];

    private int DAT_SET_TAG = 0x82;
    private int DAT_SET_LENGTH;

    public void setDAT_SET_LENGTH(int DAT_SET_LENGTH) {
        this.DAT_SET_LENGTH = DAT_SET_LENGTH;
    }

    private int[] DAT_SET_DATA = new int[DAT_SET_LENGTH];

    private int GO_ID_TAG = 0x83;
    private int GO_ID_LENGTH;

    public void setGO_ID_LENGTH(int GO_ID_LENGTH) {
        this.GO_ID_LENGTH = GO_ID_LENGTH;
    }

    private int[] GO_ID_DATA = new int[GO_ID_LENGTH];

    private int TIME_TAG = 0x84;
    private int TIME_LENGTH = 8;
    private int[] TIME_DATA = new int[8];

    private int ST_NUM_TAG = 0x85;
    private int ST_NUM_LENGTH = 1;
    private int[] ST_NUM_DATA = new int[1];

    private int SQ_NUM_TAG = 0x86;
    private int SQ_NUM_LENGTH = 1;
    private int[] SQ_NUM_DATA = new int[1];

    private int TEST_TAG = 0x87;
    private int TEST_LENGTH = 1;
    private int[] TEST_DATA = new int[1];

    private int CONF_REF_TAG = 0x88;
    private int CONF_REF_LENGTH = 1;
    private int[] CONF_REF_DATA = new int[1];

    private int NDS_COM_TAG = 0x89;
    private int NDS_COM_LENGTH = 1;
    private int[] NDS_COM_DATA = new int[1];

    private int NUM_DAT_SET_ENTRIES_TAG = 0x8a;
    private int NUM_DAT_SET_ENTRIES_LENGTH = 1;
    private int[] NUM_DAT_SET_ENTRIES_DATA = new int[1];

    private int ALL_DATA_TAG = 0xab;
    private int ALL_DATA_LENGTH;

    public void setALL_DATA_LENGTH(int ALL_DATA_LENGTH) {
        this.ALL_DATA_LENGTH = ALL_DATA_LENGTH;
    }

    private int[] ALL_DATA_DATA = new int[ALL_DATA_LENGTH];

    public void setTIME_ALLOWED_TO_LIVE_DATA(int[] TIME_ALLOWED_TO_LIVE_DATA) {
        this.TIME_ALLOWED_TO_LIVE_DATA = TIME_ALLOWED_TO_LIVE_DATA;
    }

    public void setDAT_SET_DATA(int[] DAT_SET_DATA) {
        this.DAT_SET_DATA = DAT_SET_DATA;
    }

    public void setGO_ID_DATA(int[] GO_ID_DATA) {
        this.GO_ID_DATA = GO_ID_DATA;
    }

    public void setTIME_DATA(int[] TIME_DATA) {
        this.TIME_DATA = TIME_DATA;
    }

    public void setST_NUM_DATA(int[] ST_NUM_DATA) {
        this.ST_NUM_DATA = ST_NUM_DATA;
    }

    public void setSQ_NUM_DATA(int[] SQ_NUM_DATA) {
        this.SQ_NUM_DATA = SQ_NUM_DATA;
    }

    public void setTEST_DATA(int[] TEST_DATA) {
        this.TEST_DATA = TEST_DATA;
    }

    public void setCONF_REF_DATA(int[] CONF_REF_DATA) {
        this.CONF_REF_DATA = CONF_REF_DATA;
    }

    public void setNDS_COM_DATA(int[] NDS_COM_DATA) {
        this.NDS_COM_DATA = NDS_COM_DATA;
    }

    public void setNUM_DAT_SET_ENTRIES_DATA(int[] NUM_DAT_SET_ENTRIES_DATA) {
        this.NUM_DAT_SET_ENTRIES_DATA = NUM_DAT_SET_ENTRIES_DATA;
    }

    public void setALL_DATA_DATA(int[] ALL_DATA_DATA) {
        this.ALL_DATA_DATA = ALL_DATA_DATA;
    }

    public void setGOCB_REF_DATA(int[] GOCB_REF_DATA) {
        this.GOCB_REF_DATA = GOCB_REF_DATA;
    }

    public void setAPPID(int[] APPID) {
        this.APPID = APPID;
    }

    public void setADPU_LENGTH(int[] ADPU_LENGTH) {
        this.ADPU_LENGTH = ADPU_LENGTH;
    }

    public void setLENGTH(int[] LENGTH) {
        this.LENGTH = LENGTH;
    }

    public void setRESERVED1(int[] RESERVED1) {
        this.RESERVED1 = RESERVED1;
    }

    public void setRESERVED2(int[] RESERVED2) {
        this.RESERVED2 = RESERVED2;
    }

    public int[] getAPPID() {
        return APPID;
    }

    public int[] getLENGTH() {
        return LENGTH;
    }

    public int[] getRESERVED1() {
        return RESERVED1;
    }

    public int[] getRESERVED2() {
        return RESERVED2;
    }

    public int getADPU_START_TAG() {
        return ADPU_START_TAG;
    }

    public int[] getADPU_LENGTH() {
        return ADPU_LENGTH;
    }

    public int getGOCB_REF_TAG() {
        return GOCB_REF_TAG;
    }

    public int getGOCB_REF_LENGTH() {
        return GOCB_REF_LENGTH;
    }

    public int[] getGOCB_REF_DATA() {
        return GOCB_REF_DATA;
    }

    public int getTIME_ALLOWED_TO_LIVE_TAG() {
        return TIME_ALLOWED_TO_LIVE_TAG;
    }

    public int getTIME_ALLOWED_TO_LIVE_LENGTH() {
        return TIME_ALLOWED_TO_LIVE_LENGTH;
    }

    public int[] getTIME_ALLOWED_TO_LIVE_DATA() {
        return TIME_ALLOWED_TO_LIVE_DATA;
    }

    public int getDAT_SET_TAG() {
        return DAT_SET_TAG;
    }

    public int getDAT_SET_LENGTH() {
        return DAT_SET_LENGTH;
    }

    public int[] getDAT_SET_DATA() {
        return DAT_SET_DATA;
    }

    public int getGO_ID_TAG() {
        return GO_ID_TAG;
    }

    public int getGO_ID_LENGTH() {
        return GO_ID_LENGTH;
    }

    public int[] getGO_ID_DATA() {
        return GO_ID_DATA;
    }

    public int getTIME_TAG() {
        return TIME_TAG;
    }

    public int getTIME_LENGTH() {
        return TIME_LENGTH;
    }

    public int[] getTIME_DATA() {
        return TIME_DATA;
    }

    public int getST_NUM_TAG() {
        return ST_NUM_TAG;
    }

    public int getST_NUM_LENGTH() {
        return ST_NUM_LENGTH;
    }

    public int[] getST_NUM_DATA() {
        return ST_NUM_DATA;
    }

    public int getSQ_NUM_TAG() {
        return SQ_NUM_TAG;
    }

    public int getSQ_NUM_LENGTH() {
        return SQ_NUM_LENGTH;
    }

    public int[] getSQ_NUM_DATA() {
        return SQ_NUM_DATA;
    }

    public int getTEST_TAG() {
        return TEST_TAG;
    }

    public int getTEST_LENGTH() {
        return TEST_LENGTH;
    }

    public int[] getTEST_DATA() {
        return TEST_DATA;
    }

    public int getCONF_REF_TAG() {
        return CONF_REF_TAG;
    }

    public int getCONF_REF_LENGTH() {
        return CONF_REF_LENGTH;
    }

    public int[] getCONF_REF_DATA() {
        return CONF_REF_DATA;
    }

    public int getNDS_COM_TAG() {
        return NDS_COM_TAG;
    }

    public int getNDS_COM_LENGTH() {
        return NDS_COM_LENGTH;
    }

    public int[] getNDS_COM_DATA() {
        return NDS_COM_DATA;
    }

    public int getNUM_DAT_SET_ENTRIES_TAG() {
        return NUM_DAT_SET_ENTRIES_TAG;
    }

    public int getNUM_DAT_SET_ENTRIES_LENGTH() {
        return NUM_DAT_SET_ENTRIES_LENGTH;
    }

    public int[] getNUM_DAT_SET_ENTRIES_DATA() {
        return NUM_DAT_SET_ENTRIES_DATA;
    }

    public int getALL_DATA_TAG() {
        return ALL_DATA_TAG;
    }

    public int getALL_DATA_LENGTH() {
        return ALL_DATA_LENGTH;
    }

    public int[] getALL_DATA_DATA() {
        return ALL_DATA_DATA;
    }
}