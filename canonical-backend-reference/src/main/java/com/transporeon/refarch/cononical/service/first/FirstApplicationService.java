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
package com.transporeon.refarch.cononical.service.first;

import com.transporeon.refarch.cononical.adapter.jms.model.EventDto;
import com.transporeon.refarch.cononical.adapter.rest.model.RestDataDto;
import com.transporeon.refarch.cononical.adapter.rest.model.RestResultDto;
import com.transporeon.refarch.cononical.store.company.CompanyStore;
import org.springframework.transaction.annotation.Transactional;
import com.transporeon.refarch.cononical.domain.first.FirstDomainRepository;
import com.transporeon.refarch.cononical.domain.second.SecondDomainRepository;
import org.springframework.stereotype.Service;

@Service
public class FirstApplicationService {

	private final CompanyStore companyStore;
	private final FirstDomainRepository firstDomainRepository;
	private final SecondDomainRepository secondDomainRepository;

	public FirstApplicationService(CompanyStore companyStore, FirstDomainRepository firstDomainRepository, 
			SecondDomainRepository secondDomainRepository) {
		this.companyStore = companyStore;
		this.firstDomainRepository = firstDomainRepository;
		this.secondDomainRepository = secondDomainRepository;
	}

	@Transactional
	public RestResultDto performFirstUseCases(RestDataDto restDataDto) {
		return new RestResultDto(companyStore.getOne().getName() 
				+ firstDomainRepository.findById(123l) + secondDomainRepository.findById(123l));
	}
	
	@Transactional
	public void performFirstUseCases(EventDto eventDto) {
		final String result = companyStore.getOne().getName() 
				+ firstDomainRepository.findById(123l) + secondDomainRepository.findById(123l);
	}
}
