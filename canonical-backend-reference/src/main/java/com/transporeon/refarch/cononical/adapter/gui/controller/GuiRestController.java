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
package com.transporeon.refarch.cononical.adapter.gui.controller;

import com.transporeon.refarch.cononical.adapter.gui.model.GuiDataDto;
import com.transporeon.refarch.cononical.adapter.gui.model.GuiResultDto;
import com.transporeon.refarch.cononical.service.second.SecondApplicationService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
class GuiRestController {
	
	private final SecondApplicationService secondApplicationService;

	GuiRestController(SecondApplicationService secondApplicationService) {
		this.secondApplicationService = secondApplicationService;
	}

	@GetMapping("/gui/result")
	GuiResultDto getResult(final GuiDataDto guiDataDto) {
		return secondApplicationService.performSecondUseCases(guiDataDto);
	}
}
