package com.redhat.camel.components;

import org.apache.mina.filter.codec.ProtocolCodecFactory;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolEncoder;

public class WelderSensorCodec implements ProtocolCodecFactory {
    public ProtocolEncoder getEncoder() throws Exception {
        return new WelderEncoder();
    }

    public ProtocolDecoder getDecoder() throws Exception {
        return new WelderDecoder();
    }
}