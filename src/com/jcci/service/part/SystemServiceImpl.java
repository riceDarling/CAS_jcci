package com.jcci.service.part;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jcci.repository.SystemMapper;

import unicorn.common.datetime.DateUtils;

import java.util.Date;

@Service
public class SystemServiceImpl implements SystemService {

  @Override
  public Date readNowDate() {
    return systemS.readNow();
  }

  @Override
  public String readNow(String format) {
    Date now = readNowDate();
    String result = DateUtils.getDate(now, format);
    return result;
  }

  @Override
  public String readNow() {
    return readNow("yyyy-MM-dd hh:mm:ss");
  }

  @Autowired
  private SystemMapper systemS;
}
