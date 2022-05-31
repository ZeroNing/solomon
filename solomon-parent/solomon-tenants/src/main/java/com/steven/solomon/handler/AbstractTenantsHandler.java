package com.steven.solomon.handler;

import java.util.List;

public abstract class AbstractTenantsHandler<F> {

  public abstract F getFactory();

  public abstract void removeFactory();

  public abstract void setFactory(String name);

}
