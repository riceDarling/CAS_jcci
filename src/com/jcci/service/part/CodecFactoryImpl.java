package com.jcci.service.part;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.stereotype.Service;

import unicorn.common.compression.ZLibUtils;

import java.io.IOException;

@Service
public class CodecFactoryImpl implements CodecFactory {
  @Override
  public byte[] serialize(Object obj) throws IOException {
    ObjectMapper mapper = new ObjectMapper();
    String json = "";
    try {
      json = mapper.writeValueAsString(obj);     
      return ZLibUtils.compress(json.getBytes("UTF-8"));
    } catch (JsonProcessingException ex) {
      ex.printStackTrace();
    }
    return null;

  }

  @Override
  public Object deSerialize(byte[] in) throws IOException {
    byte[] sin = ZLibUtils.decompress(in);
    return new String(sin,"UTF-8");
  }

}
