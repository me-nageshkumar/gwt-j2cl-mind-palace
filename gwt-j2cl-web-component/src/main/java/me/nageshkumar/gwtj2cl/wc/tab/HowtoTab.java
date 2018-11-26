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

import elemental2.dom.HTMLElement;
import jsinterop.annotations.JsProperty;
import jsinterop.annotations.JsType;

@JsType
public class HowtoTab extends HTMLElement {

	private static Integer howtoTabCounter = 0;

	@JsProperty
	public Boolean selected;

	public HowtoTab() {
		observedAttributes = new String[] { "selected" };
	}

	public void connectedCallback() {
		this.setAttribute("role", "tab");
		if (null == this.id)
			this.id = "howto-tab-generated-" + howtoTabCounter++;

		this.setAttribute("aria-selected", "false");
		this.setAttribute("tabindex", -1);
		upgradeProperty("selected");
	}

	private void upgradeProperty(String prop) {
//	      if (this.hasOwnProperty(prop)) {
//	          let value = this[prop];
//	          delete this[prop];
//	          this[prop] = value;
//	      }
	}

	public void attributeChangedCallback() {
		Boolean value = this.hasAttribute("selected");
		this.setAttribute("aria-selected", value);
		this.setAttribute("tabindex", value ? 0 : -1);
	}

	public void setSelected(Boolean value) {
		if (value)
			this.setAttribute("selected", "");
		else
			this.removeAttribute("selected");
	}

	public Boolean getSelected() {
		return this.hasAttribute("selected");
	}

}