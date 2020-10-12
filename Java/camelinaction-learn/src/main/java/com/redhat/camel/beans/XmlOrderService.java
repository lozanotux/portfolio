package com.redhat.camel.beans;

import org.apache.camel.Body;
import org.apache.camel.language.Bean;
import org.apache.camel.language.XPath;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

public class XmlOrderService {

	public Document handleIncomingOrder(@Body Document xml, @XPath("/order/@customerId") int customerId,
			@Bean(ref = "guid", method = "generate") int orderId) {

		Attr attr = xml.createAttribute("orderId");
		attr.setValue("" + orderId);

		Node node = xml.getElementsByTagName("order").item(0);
		node.getAttributes().setNamedItem(attr);

		return xml;
	}

}
