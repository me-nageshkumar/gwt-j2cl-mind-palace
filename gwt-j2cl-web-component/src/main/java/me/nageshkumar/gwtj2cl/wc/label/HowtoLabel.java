package me.nageshkumar.gwtj2cl.wc.label;

import java.util.ArrayList;
import java.util.List;

import elemental2.dom.Element;
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
import elemental2.dom.HTMLSlotElement;
import elemental2.dom.HTMLSlotElement.AssignedNodesOptionsType;
import elemental2.dom.Node;
import elemental2.dom.ShadowRoot;
import jsinterop.annotations.JsProperty;
import jsinterop.annotations.JsType;

@JsType
public class HowtoLabel extends HTMLElement {

	private Integer howtoLabelCounter = 0;

	private Element slot;

	@JsProperty
	public String[] getObservedAttributes() {
		return new String[] { "for" };
	}

	public HowtoLabel() {
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
		shadowRoot.innerHTML = "<style>:host{cursor:default;}</style><slot></slot>";

		slot = this.shadowRoot.querySelector("slot");
		slot.addEventListener("slotchange", this::onSlotChange);
		this.addEventListener("click", this::onClick);
	}

	@JsProperty(name="for")
	public String getRef() {
		String value = this.getAttribute("for");
		return value == null ? "" : value;
	}

	@JsProperty(name="for")
	public void setRef(String value) {
		this.setAttribute("for", value);
	}

	@Override
	public Object attributeChangedCallback(String attributeName, String oldValue, String newValue, String namespace) {
		this.updateLabel();
		return super.attributeChangedCallback(attributeName, oldValue, newValue, namespace);
	}

	private void onClick(Event event) {
		Element element = currentLabelTarget();
		if (null == element || event.target == element) {
			return;
		}
		element.focus();
		element.click();
	}

	private Element currentLabelTarget() {
		Node scope = this.getRootNode();
		return scope.querySelector("[aria-labelledby=" + this.id + "]");
	}

	private void updateLabel() {
		if (null == this.id || this.id.isEmpty()) {
			this.id = "howto-label-generated-" + howtoLabelCounter++;
		}
		Element oldTarget = this.currentLabelTarget();
		Element newTarget = this.findTarget();
		if (null == newTarget || oldTarget == newTarget) {
			return;
		}
		if (null != oldTarget) {
			oldTarget.removeAttribute("aria-labelledby");
		}
		newTarget.setAttribute("aria-labelledby", this.id);
	}

	private void onSlotChange(Event event) {
		updateLabel();
	}

	private Element findTarget() {
		if (this.getRef() != null && !this.getRef().isEmpty()) {
			Node scope = this.getRootNode();
			return scope.querySelector("#" + this.getRef());
		}

		AssignedNodesOptionsType assignedNodesOptionsType = new AssignedNodesOptionsType() {
			private boolean flatten;

			@Override
			public void setFlatten(boolean flatten) {
				this.flatten = flatten;
			}

			@Override
			public boolean isFlatten() {

				return flatten;
			}
		};
		assignedNodesOptionsType.setFlatten(true);
		Node[] slottedChildrens = ((HTMLSlotElement) slot).assignedNodes(assignedNodesOptionsType);
		List<Node> noneTextNodes = new ArrayList<>();
		for (Node slottedChildren : slottedChildrens) {
			if (slottedChildren.nodeType != Node.TEXT_NODE) {
				noneTextNodes.add(slottedChildren);
			}
		}
		Element slottedChildren = null;
		for (Node noneTextNode : noneTextNodes) {
			if (((Element) noneTextNode).hasAttribute("howto-label-target")) {
				slottedChildren = (Element) noneTextNode;
			}
		}
		if (null == slottedChildren && !noneTextNodes.isEmpty()) {
			slottedChildren = (Element) noneTextNodes.get(0);
		}
		return slottedChildren;
	}
}