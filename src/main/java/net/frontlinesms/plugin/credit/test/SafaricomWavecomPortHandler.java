package net.frontlinesms.plugin.credit.test;

import net.frontlinesms.test.serial.HayesState;
import net.frontlinesms.test.serial.StatefulHayesPortHandler;

public class SafaricomWavecomPortHandler extends StatefulHayesPortHandler {
	public SafaricomWavecomPortHandler() {
		super(SafaricomWavecomStates.INITIAL_STATE);
	}
}

class SafaricomWavecomStates {
	static final HayesState INITIAL_STATE = HayesState.createState("ERROR: 0",
			"AT", "OK",
			"AT+CMEE=1", "OK",
			"AT+STSF=1", "OK",
			"AT+CPIN?", "+CPIN: READY",
			"AT+CGMI", "WAVECOM MODEM\rOK",
			"AT+CGMM", "900P\rOK",	
			"AT+CNUM", "+CNUM :\"Phone\", \"0712345678\",129\rOK",
			"AT+CGSN", "123456789099998\rOK",
			"AT+CIMI", "254123456789012\rOK",
			//"AT+CBC"
			"AT+COPS=0", "OK",
			"AT+CLIP=1", "OK",
			"ATE0",	"OK",
			"AT+CREG?", "+CREG: 1,1\rOK",
			"AT+CPMS?", "+CPMS: \"SM\",3, 10,\"SM\",3,10\rOK",
			"AT+CMGF=0", "OK",	
			"+++", "", // switch 2 command mode
			"AT+CPMS?", "+CPMS:\r\"ME\",1,15,\"SM\",0,100\rOK", // get storage locations
			"AT+CPMS=\"ME\"", "OK");
	
	private final HayesState MAIN_STK_MENU_STATE = HayesState.createState("ERROR: main_menu",
			"AT", "OK",
			"AT+STGI=0", "+STGI: \"Safaricom\"\r"+
						"+STGI: 1,2,\"Safaricom\",0,0\r"+
						"+STGI: 129,2,\"M-PESA\",0,21\r\rOK");
	
	private final HayesState GET_MAIN_MPESA_MENU_STATE = HayesState.createState("ERROR: get_mpesa_main_menu",
			"AT", "OK",
			"AT+STGR=0,1,129", "OK\r+STIN: 6"
			);
	
	private final HayesState MPESA_MAIN_MENU_STATE = HayesState.createState("ERROR: mpesa_main_menu",
			"AT", "OK",
			"AT+STGI=6", "+STGI: 0,\"M-PESA\"\r"+
					"+STGI: 1,7,\"Send money\",0\r"+
					"+STGI: 2,7,\"Withdraw cash\",0\r"+
					"+STGI: 3,7,\"Buy airtime\",0\r"+
					"+STGI: 4,7,\"Pay Bill\",0\r"+
					"+STGI: 4,7,\"Pay Bill\",0\r"+
					"+STGI: 6,7,\"ATM Withdrawal\",0\r"+
					"+STGI: 7,7,\"My account\",0\r"+
					"\rOK");
	
	private final HayesState MPESA_GET_SEND_MONEY_STATE = HayesState.createState("ERROR: mpesa_get_send_money",
			"AT", "OK",
			"AT+STGR=6,1,1", "OK\r\r+STIN: 3");
	
	private final HayesState ENTER_PHONE_NUMBER_REQUEST_STATE = HayesState.createState("ERROR: phone_number_request",
			"AT", "OK",
			"AT+STGI=3", "+STGI: 0,1,0,20,0,\"Enter phone no.\"\rOK");
	
	private final HayesState ENTER_PHONE_NUMBER_STATE = HayesState.createState("ERROR: enter_phone_number",
			"AT", "OK",
			"AT+STGR=3,1", ">",
			"0712345678\032", "OK\r+STIN: 3");
	
	private final HayesState ENTER_AMOUNT_REQUEST_STATE = HayesState.createState("ERROR: enter_amount_request",
			"AT", "OK",
			"AT+STGI=3", "+STGI: 0,1,0,8,0,\"Enter amount\"\rOK");
	
	private final HayesState ENTER_AMOUNT_STATE = HayesState.createState("ERROR: enter_amount",
			"AT", "OK",
			"AT+STGR=3,1", ">",
			"1000\032", "OK\r+STIN: 3");
	
	private final HayesState ENTER_PIN_REQUEST_STATE = HayesState.createState("ERROR: enter_pin_request",
			"AT", "OK",
			"AT+STGI=3", "+STGI: 0,0,4,4,0,\"Enter PIN\"\r\rOK");
	
	private final HayesState ENTER_PIN_STATE = HayesState.createState("ERROR: enter_pin",
			"AT", "OK",
			"AT+STGR=3,1", ">",
			"1234\032", "OK\r+STIN: 1");
	
	private final HayesState CONFIRM_SEND_MONEY_STATE = HayesState.createState("ERROR: confirm_send_money",
			"AT+STGI=1", "+STGI: 1,\"Send money to 0712345678\r"+                                              
            "Ksh1000\",1\rOK");
	
	private final HayesState SEND_MONEY_STATE = HayesState.createState("ERROR: send_money",
			"AT+STGR=1,1", "OK\r"+                                                                              
			//+STIN: 9                                                                        
			"+STGI: \"Sending...\"");
}
