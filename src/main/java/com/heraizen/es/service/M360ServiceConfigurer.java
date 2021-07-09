package com.heraizen.es.service;

import com.heraizen.es.domain.DimensionData;
import com.heraizen.es.domain.Service;
import java.util.List;

public interface M360ServiceConfigurer {

    Service getService(String svcName);
    double calculatePrice(String svcName, List<DimensionData> eventData);
}
