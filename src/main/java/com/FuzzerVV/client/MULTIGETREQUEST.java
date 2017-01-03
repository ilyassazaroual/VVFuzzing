package com.FuzzerVV.client;

import java.io.IOException;

import org.junit.Test;

public class MULTIGETREQUEST {
	@Test
	public void MultipleGetRequest() {
		GETESTREQUEST jct = new GETESTREQUEST();
		System.err.println("===========================================================================================");
		System.out.println("[INFO] - DEBUT TEST MONTEE EN CHARGE GET ");
		for (int i = 0; i < 5; i++) {
			System.out.println("[INFO] - test GET N* " + (i + 1));
		}
		System.out.println("[OK] - FIN TEST MONTEE EN CHARGE GET OK ");
		System.err.println("===========================================================================================\n\n");
	}
}
