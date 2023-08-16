package com.h12.hq;

import java.io.Serializable;

public interface IContext extends Serializable, IPrepare {
    IResource getResource();
}
