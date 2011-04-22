/**
 * 
 */
package net.frontlinesms.plugin.credit.test;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.Iterator;

import net.frontlinesms.CommUtils;
import net.frontlinesms.payment.safaricom.RealCService;
import net.frontlinesms.payment.safaricom.SafaricomPaymentService;

import org.mockito.Mockito;
import org.smslib.CService;

import serial.SerialClassFactory;
import serial.mock.CommPortIdentifier;
import serial.mock.MockSerial;
import serial.mock.SerialPortHandler;

/**
 * @author aga
 */
@SuppressWarnings("unchecked")
public class CreditSimulationLauncher {
	public static void main(String[] args) throws Exception {
		setupMockModem();
		
		SafaricomPaymentService safaricom = new SafaricomPaymentService();
		safaricom.setCService(new RealCService());
		
		// Launch FrontlineSMS
		startFrontlineSms(args);
		
		System.out.println("Serial lib: " + SerialClassFactory.getInstance().getSerialPackageName());
	}

	private static void startFrontlineSms(String... args) {
		net.frontlinesms.DesktopLauncher.main(args);
	}

	private static void setupMockModem() throws Exception {
		// Set up modem simulation
		MockSerial.init();
		MockSerial.setMultipleOwnershipAllowed(true);
		SerialPortHandler portHandler = new SafaricomWavecomPortHandler();
		CommPortIdentifier cpi = new CommPortIdentifier("COM1", portHandler);
		MockSerial.setIdentifier("COM1", cpi);
		Mockito.when(MockSerial.getMock().values()).thenReturn(Arrays.asList(new CommPortIdentifier[]{
				cpi
		}));
		
		Field setPackageField = CommUtils.class.getDeclaredField("setPackage");
		setPackageField.setAccessible(true);
		setPackageField.set(null, Boolean.TRUE);
		
		System.out.println("CPIs");
		for(Object s : new IterableEnumeration(MockSerial.getPortIdentifiers())) {
			System.out.println(s);
		}
		System.out.println("---");
	}
}

class IterableEnumeration<E> implements Iterable<E> {
    private final Enumeration<E> e;
    public IterableEnumeration(Enumeration<E> e) {
        this.e = e;
    }

    public Iterator<E> iterator() {
        return new Iterator<E>() {
            public boolean hasNext() {
                return e.hasMoreElements();
            }
            public E next() {
                return e.nextElement();
            }
            public void remove() {
                throw new IllegalStateException();
            }
        };
    }
}
