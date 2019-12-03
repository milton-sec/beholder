package br.ufpe.cin.beholder.messages;


import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.HashMap;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.UpdateListener;

public class LandListener implements UpdateListener{


		public void update(EventBean[] newEvents, EventBean[] oldEvents) {

			System.out.println(	"*************************Potential Land Attack Detected ******************************");
			if (newEvents != null && newEvents.length > 0) {
				try {

					for (int i = 0; i < newEvents.length; i++) {
						EventBean event = newEvents[i];
						String eventName = event.getEventType().getName();
						if (eventName.equalsIgnoreCase("evilHost")) {
							Object obj = event.getUnderlying();
							System.out.println(obj.getClass().getName());
							HashMap map = (HashMap) event.getUnderlying();
							String value = map.get("srcAddr").toString();
							BufferedWriter fileGuardian = new BufferedWriter(new FileWriter("/var/log/beholder/alert", true));
							if (value != null && !value.isEmpty()) {
								value = value.substring(1);
								fileGuardian.write(value);
								fileGuardian.newLine();
								fileGuardian.flush();
								
								// Runtime run = Runtime.getRuntime();
								// run.exec("/sbin/iptables -I INPUT -s " + value + " -d 0.0.0.0 -j DROP");
								// run.exec("/sbin/iptables -I FORWARD -s " + value + " -d 0.0.0.0.0 -j DROP");
								// System.out.println("DROP " + value + "See Alert File:
								// /var/log/beholder/alert");
							}
							fileGuardian.close();
						}
					}
				} catch (Exception e) {

				}
			}
		}
}