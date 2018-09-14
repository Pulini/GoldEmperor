package com.goldemperor.Utils;

import org.ksoap2.transport.HttpTransportSE;
import org.ksoap2.transport.ServiceConnection;
import org.ksoap2.transport.ServiceConnectionSE;

import java.io.IOException;


public class MyHttpTransportSE extends HttpTransportSE {

	private int timeout = 120000;

	public MyHttpTransportSE(String url) {
		super(url);
	}

	public MyHttpTransportSE(String url, int timeout) {
		super(url);
		this.timeout = timeout;
	}

	protected ServiceConnection getServiceConnection() throws IOException {
		ServiceConnectionSE serviceConnection = new ServiceConnectionSE(
				this.url, timeout);
		return serviceConnection;
	}
}
