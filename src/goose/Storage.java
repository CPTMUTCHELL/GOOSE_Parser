package goose;

import lombok.Data;
import lombok.Getter;

@Data
public class Storage {
    private int apdu_length;

    private int TIME_ALLOWED_TO_LIVE_LENGTH;
    private int GOCB_REF_LENGTH;
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

    private int[] DAT_SET_DATA = new int[DAT_SET_LENGTH];

    private int GO_ID_TAG = 0x83;
    private int GO_ID_LENGTH;


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

    private int[] ALL_DATA_DATA = new int[ALL_DATA_LENGTH];



}