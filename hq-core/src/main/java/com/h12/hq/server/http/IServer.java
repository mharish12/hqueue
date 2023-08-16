package com.h12.hq.server.http;

import com.h12.hq.IPrepare;

public interface IServer extends IPrepare {
    String getHost();
    Integer getPort();
}
