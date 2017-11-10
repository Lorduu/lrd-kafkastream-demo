package com.lrd;

import org.apache.kafka.streams.kstream.KStream;

public class Stream {
    private KStream kStream;
    private String streamName;
    private String parentName;
    private String sonName;

    public Stream(KStream kStream, String streamName) {
        this.kStream = kStream;
        this.streamName = streamName;
    }

    public KStream getkStream() {
        return kStream;
    }

    public void setkStream(KStream kStream) {
        this.kStream = kStream;
    }

    public String getStreamName() {
        return streamName;
    }

    public void setStreamName(String streamName) {
        this.streamName = streamName;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public String getSonName() {
        return sonName;
    }

    public void setSonName(String sonName) {
        this.sonName = sonName;
    }
}
