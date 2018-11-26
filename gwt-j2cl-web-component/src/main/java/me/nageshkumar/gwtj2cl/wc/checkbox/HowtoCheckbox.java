package me.nageshkumar.gwtj2cl.wc.checkbox;

import elemental2.dom.Event;

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
import elemental2.dom.ShadowRoot;
import jsinterop.annotations.JsProperty;
import jsinterop.annotations.JsType;
import jsinterop.base.Js;
import jsinterop.base.JsPropertyMap;

@JsType
public class HowtoCheckbox extends HTMLElement {

	@JsProperty
	public String[] getObservedAttributes() {
		return new String[] { "checked", "disabled" };
	}

	public HowtoCheckbox() {
		HTMLElement.AttachShadowOptionsType options = new AttachShadowOptionsType() {

			private String mode;

			@Override
			public void setMode(String mode) {
				this.mode = mode;

			}

			@Override
			public String getMode() {
				return this.mode;
			}
		};
		options.setMode("open");
		ShadowRoot shadowRoot = attachShadow(options);
		shadowRoot.innerHTML = "<style> :host { display: inline-block; background: url('images/unchecked-checkbox.svg') no-repeat; background-size: contain; width: 24px; height: 24px; } :host([hidden]) { display: none; } :host([checked]) { background: url('images/checked-checkbox.svg') no-repeat; background-size: contain; } :host([disabled]) { background: url('../images/unchecked-checkbox-disabled.svg') no-repeat; background-size: contain; } :host([checked][disabled]) { background: url('../images/checked-checkbox-disabled.svg') no-repeat; background-size: contain; } </style>";
	}

	public void connectedCallback() {
		if (!this.hasAttribute("role"))
			this.setAttribute("role", "checkbox");
		if (!this.hasAttribute("tabindex"))
			this.setAttribute("tabindex", 0);

		this.upgradeProperty("checked");
		this.upgradeProperty("disabled");

		// this.addEventListener('keyup', this._onKeyUp);
		this.addEventListener("click", this::onClick);
	}

	private void upgradeProperty(String propertyName) {
		JsPropertyMap<Boolean> jsPropertyMap = Js.cast(this);
		Boolean jsObject = jsPropertyMap.get(propertyName);
		if (null != jsObject) {
			jsPropertyMap.delete(propertyName);
			jsPropertyMap.set(propertyName, jsObject);
		}
	}

	public void disconnectedCallback() {
		// this.removeEventListener('keyup', this._onKeyUp);
		this.removeEventListener("click", this::onClick);
	}

	@JsProperty
	public Boolean getChecked() {
		return this.hasAttribute("checked");
	}

	@JsProperty
	public void setChecked(Boolean isChecked) {
		if (isChecked)
			this.setAttribute("checked", "");
		else
			this.removeAttribute("checked");
	}

	@JsProperty
	public Boolean getDisabled() {
		return this.hasAttribute("disabled");
	}

	@JsProperty
	public void setDisabled(Boolean isDisabled) {
		if (isDisabled)
			this.setAttribute("disabled", "");
		else
			this.removeAttribute("disabled");
	}

	@Override
	public Object attributeChangedCallback(String attributeName, String oldValue, String newValue, String namespace) {
		Boolean hasValue = newValue != null;
		switch (attributeName) {
		case "checked":
			this.setAttribute("aria-checked", hasValue);
			break;
		case "disabled":
			this.setAttribute("aria-disabled", hasValue);
			if (hasValue) {
				this.removeAttribute("tabindex");
				this.blur();
			} else {
				this.setAttribute("tabindex", "0");
			}
			break;
		}
		return super.attributeChangedCallback(attributeName, oldValue, newValue, namespace);
	}

	private void onClick(Event event) {
		this.toggleChecked();
	}

	private void toggleChecked() {
		if (this.getDisabled())
			return;
		this.setChecked(!this.getChecked());
//		this.dispatchEvent(new CustomEvent("change", {
//	        detail: {
//	          checked: this.checked,
//	        },
//	        bubbles: true,
//	    }));
	}

}