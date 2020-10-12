package com.redhat.camel.components;

import org.apache.mina.common.ByteBuffer;
import org.apache.mina.common.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoder;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;

public class WelderEncoder implements ProtocolEncoder {

	static final int PAYLOAD_SIZE = 8;

	public void encode(IoSession ioSession, Object message, ProtocolEncoderOutput out) throws Exception {
		ByteBuffer buf = ByteBuffer.allocate(PAYLOAD_SIZE);
		String s = (String) message;
		buf.put(s.getBytes());
		buf.flip();
		out.write(buf);
	}

	public void dispose(IoSession ioSession) throws Exception {
		// do nothing
	}

}