package com.zhiwei.core.spring;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import flex.messaging.FactoryInstance;
import flex.messaging.FlexFactory;
import flex.messaging.config.ConfigMap;

public class FlexFactoryImpl implements FlexFactory { 
    private Log log = LogFactory.getLog(getClass()); 

    public FactoryInstance createFactoryInstance(String id, ConfigMap properties) { 
        log.info("Create FactoryInstance."); 
        SpringFactoryInstance instance = new SpringFactoryInstance(this, id, properties); 
        instance.setSource(properties.getPropertyAsString(SOURCE, instance.getId())); 
        return instance; 
    } 

    public Object lookup(FactoryInstance instanceInfo) { 
        log.info("Lookup service object."); 
        return instanceInfo.lookup(); 
    } 

    public void initialize(String id, ConfigMap configMap) { 
    } 
}
