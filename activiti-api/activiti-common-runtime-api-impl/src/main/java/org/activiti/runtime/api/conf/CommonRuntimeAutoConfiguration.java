/*
 * Copyright 2018 Alfresco, Inc. and/or its affiliates.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.activiti.runtime.api.conf;

import java.util.List;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.delegate.event.ActivitiEventType;
import org.activiti.runtime.api.event.VariableEventListener;
import org.activiti.runtime.api.event.VariableCreated;
import org.activiti.runtime.api.event.VariableDeleted;
import org.activiti.runtime.api.event.VariableUpdated;
import org.activiti.runtime.api.event.impl.ToVariableCreatedConverter;
import org.activiti.runtime.api.event.impl.ToVariableDeletedConverter;
import org.activiti.runtime.api.event.impl.ToVariableUpdatedConverter;
import org.activiti.runtime.api.event.internal.VariableCreatedListenerDelegate;
import org.activiti.runtime.api.event.internal.VariableDeletedListenerDelegate;
import org.activiti.runtime.api.event.internal.VariableUpdatedListenerDelegate;
import org.activiti.runtime.api.model.impl.APIVariableInstanceConverter;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CommonRuntimeAutoConfiguration {

    @Bean
    public APIVariableInstanceConverter apiVariableInstanceConverter() {
        return new APIVariableInstanceConverter();
    }


    @Bean
    public InitializingBean registerVariableCreatedListenerDelegate(RuntimeService runtimeService,
                                                                    @Autowired(required = false) List<VariableEventListener<VariableCreated>> listeners){
        return () -> runtimeService.addEventListener(new VariableCreatedListenerDelegate(listeners, new ToVariableCreatedConverter()), ActivitiEventType.VARIABLE_CREATED);
    }

    @Bean
    public InitializingBean registerVariableUpdatedListenerDelegate(RuntimeService runtimeService,
                                                                    @Autowired(required = false) List<VariableEventListener<VariableUpdated>> listeners){
        return () -> runtimeService.addEventListener(new VariableUpdatedListenerDelegate(listeners, new ToVariableUpdatedConverter()), ActivitiEventType.VARIABLE_UPDATED);
    }

    @Bean
    public InitializingBean registerVariableDeletedListenerDelegate(RuntimeService runtimeService,
                                                                    @Autowired(required = false) List<VariableEventListener<VariableDeleted>> listeners){
        return () -> runtimeService.addEventListener(new VariableDeletedListenerDelegate(listeners, new ToVariableDeletedConverter()), ActivitiEventType.VARIABLE_DELETED);
    }

}
