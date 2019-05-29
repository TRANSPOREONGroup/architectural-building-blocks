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
package com.transporeon.refarch.cononical.store.user;

import java.util.Objects;

/**
 * Immutable user class suitable for caching.
 */
public class User {

	private final String name;

	User(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(this == obj) {
			return true;
		} else if(obj instanceof User) {
			User other = (User) obj;
			return name.equals(other.name);
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return Objects.hash(name);
	}	
}
