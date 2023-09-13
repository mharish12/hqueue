package com.h12.hq.server.http;

import com.h12.hq.IPrepare;
import com.h12.hq.IResource;

public interface IServer extends IPrepare, IResource {
    String getHost();

    Integer getPort();
}
