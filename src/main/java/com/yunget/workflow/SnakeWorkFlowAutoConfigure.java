/*
 *  Copyright 2018 www.yunmel.com.
 *  *
 *  * Licensed under the Apache License, Version 2.0 (the "License");
 *  * you may not use this file except in compliance with the License.
 *  * You may obtain a copy of the License at
 *  *
 *  *     http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  * Unless required by applicable law or agreed to in writing, software
 *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  * See the License for the specific language governing permissions and
 *  * limitations under the License.
 *
 */
package com.yunget.workflow;

import com.yunget.workflow.orm.MybatisAccess;
import com.yunget.workflow.spring.SpringSnakerEngine;
import org.apache.ibatis.session.SqlSessionFactory;
import org.snaker.engine.IProcessService;
import org.snaker.engine.cache.memory.MemoryCacheManager;
import org.snaker.engine.core.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SnakeWorkFlowAutoConfigure {
    @Autowired
    private  SqlSessionFactory sqlSessionFactory;

    @Bean
    public MybatisAccess mybatisAccess() {
        MybatisAccess mybatisAccess = new MybatisAccess();
        mybatisAccess.setSqlSessionFactory(sqlSessionFactory);
        return mybatisAccess;
    }

    @Bean
    public IProcessService processService() {
        ProcessService processService = new ProcessService();
        processService.setAccess(mybatisAccess());
        processService.setCacheManager(new MemoryCacheManager());
        return processService;
    }

    @Bean
    public OrderService orderService() {
        OrderService orderService = new OrderService();
        orderService.setAccess(mybatisAccess());
        return orderService;
    }

    @Bean
    public TaskService taskService() {
        TaskService taskService = new TaskService();
        taskService.setAccess(mybatisAccess());
        return taskService;
    }

    @Bean
    public ManagerService managerService() {
        ManagerService managerService = new ManagerService();
        managerService.setAccess(mybatisAccess());
        return managerService;
    }

    @Bean
    public QueryService queryService() {
        QueryService queryService = new QueryService();
        queryService.setAccess(mybatisAccess());
        return queryService;
    }

    @Bean
    public SpringSnakerEngine springSnakerEngine() {
        SpringSnakerEngine springSnakerEngine = new SpringSnakerEngine();
        springSnakerEngine.setManagerService(managerService());
        springSnakerEngine.setOrderService(orderService());
        springSnakerEngine.setProcessService(processService());
        springSnakerEngine.setQueryService(queryService());
        springSnakerEngine.setTaskService(taskService());
        return springSnakerEngine;
    }

}