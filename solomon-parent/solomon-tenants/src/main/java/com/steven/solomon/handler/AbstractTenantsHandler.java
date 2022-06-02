package com.steven.solomon.handler;

import java.util.List;
import java.util.Map;

public abstract class AbstractTenantsHandler<F,P> {

  public abstract F getFactory();

  public abstract void removeFactory();

  public abstract void setFactory(String name);

  public abstract void setProperties(P properties);

  public abstract List<P> getProperties();

  public abstract Map<String,F> getFactoryMap();

  public abstract void setFactoryMap(Map<String, F> redisFactoryMap);
}
