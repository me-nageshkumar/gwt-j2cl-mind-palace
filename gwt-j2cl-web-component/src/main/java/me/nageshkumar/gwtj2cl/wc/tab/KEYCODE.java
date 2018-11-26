package me.nageshkumar.gwtj2cl.wc.tab;

/*
 * #%L
 * Web Component
 * %%
 * Copyright (C) 2017 - 2018 Vertispan
 * %%
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
 * #L%
 */

public enum KEYCODE {
	DOWN(40), LEFT(37), RIGHT(39), UP(38), HOME(36), END(35);

	private Integer keyCode;

	private KEYCODE(Integer keyCode) {
		this.keyCode = keyCode;
	}

	public Integer getValue() {
		return this.keyCode;
	}
}
