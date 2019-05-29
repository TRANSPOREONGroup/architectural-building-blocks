/*
 * Copyright 2019 Transporeon GmbH.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.transporeon.buildingblocks.annotation;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@BuildingBlock(uses = {ApplicationServiceBuildingBlock.class, HttpDtoModelBuildingBlock.class},
		allowedAnnotations = {RestController.class, Controller.class, JmsListener.class})
public @interface HttpControllerBuildingBlock {

	Class<?>[] allowedAnnotations() default {};

	boolean includeSubPackages() default true;
}
