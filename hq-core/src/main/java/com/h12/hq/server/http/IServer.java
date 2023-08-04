package com.h12.hq.server.http;

import com.h12.hq.IResource;

public interface IServer extends IResource {
    String getHost();
    Integer getPort();
}
