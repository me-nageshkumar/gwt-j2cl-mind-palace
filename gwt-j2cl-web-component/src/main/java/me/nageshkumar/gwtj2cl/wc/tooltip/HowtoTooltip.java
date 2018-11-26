package me.nageshkumar.gwtj2cl.wc.tooltip;

import elemental2.dom.DomGlobal;
import elemental2.dom.Element;
import elemental2.dom.Event;
import elemental2.dom.EventListener;

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
import jsinterop.annotations.JsConstructor;
import jsinterop.annotations.JsType;

@JsType
public class HowtoTooltip extends HTMLElement {

	private Element target;

	@JsConstructor
	public HowtoTooltip() {
	}

	public void connectedCallback() {
		if (!this.hasAttribute("role"))
			this.setAttribute("role", "tooltip");

		if (!this.hasAttribute("tabindex"))
			this.setAttribute("tabindex", -1);

		target = DomGlobal.document.querySelector("[aria-describedby=" + this.id + "]");
		this.hidden = true;
		if (null == target)
			return;
		target.addEventListener("focus", new EventListener() {

			@Override
			public void handleEvent(Event evt) {
				hidden = false;
			}
		});
		target.addEventListener("blur", new EventListener() {

			@Override
			public void handleEvent(Event evt) {
				hidden = true;
			}
		});
		target.addEventListener("mouseenter", new EventListener() {

			@Override
			public void handleEvent(Event evt) {
				hidden = false;
			}
		});
		target.addEventListener("mouseleave", new EventListener() {

			@Override
			public void handleEvent(Event evt) {
				hidden = true;
			}
		});
	}

	public void disconnectedCallback() {
		if (null == target)
			return;
		target.removeEventListener("focus", new EventListener() {

			@Override
			public void handleEvent(Event evt) {
				hidden = false;
			}
		});
		target.removeEventListener("blur", new EventListener() {

			@Override
			public void handleEvent(Event evt) {
				hidden = true;
			}
		});
		target.removeEventListener("mouseenter", new EventListener() {

			@Override
			public void handleEvent(Event evt) {
				hidden = false;
			}
		});
		target.removeEventListener("mouseleave", new EventListener() {

			@Override
			public void handleEvent(Event evt) {
				hidden = true;
			}
		});
		target = null;
	}
}