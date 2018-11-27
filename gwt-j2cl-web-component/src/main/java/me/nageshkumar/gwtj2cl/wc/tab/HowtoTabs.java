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

import java.util.ArrayList;
import java.util.List;

import elemental2.dom.DomGlobal;
import elemental2.dom.Element;
import elemental2.dom.Event;
import elemental2.dom.HTMLElement;
import elemental2.dom.NodeList;
import elemental2.dom.ShadowRoot;
import jsinterop.annotations.JsType;

@JsType
public class HowtoTabs extends HTMLElement {

	private Element tabSlot;
	private Element panelSlot;

	public HowtoTabs() {

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
		shadowRoot.innerHTML = "<style> :host { display: flex; flex-wrap: wrap; } ::slotted(howto-panel) { flex-basis: 100%; } </style> <slot name=\"tab\"></slot> <slot name=\"panel\"></slot>";

		tabSlot = this.shadowRoot.querySelector("slot[name=tab]");
		panelSlot = this.shadowRoot.querySelector("slot[name=panel]");

		tabSlot.addEventListener("slotchange", this::onSlotChange);

		panelSlot.addEventListener("slotchange", this::onSlotChange);
	}

	public void connectedCallback() {
		this.addEventListener("click", this::onClick);

		if (!this.hasAttribute("role"))
			this.setAttribute("role", "tablist");

		linkPanels();
	}

	public void disconnectedCallback() {
		this.removeEventListener("click", this::onClick);
	}

	public HowtoPanel[] allPanels() {
		NodeList<Element> nodeList = this.querySelectorAll("howto-panel");
		List<Element> elements = nodeList.asList();
		List<HowtoPanel> howtoPanels = new ArrayList<HowtoPanel>();
		for (Element element : elements) {
			HTMLElement htmlElement = (HTMLElement) element;
			HowtoPanel howtoPanel = ((HowtoPanel) htmlElement);
			howtoPanels.add(howtoPanel);
		}
		HowtoPanel[] arrHowtoPanels = new HowtoPanel[howtoPanels.size()];
		return howtoPanels.toArray(arrHowtoPanels);
	}

	private void onSlotChange(Event event) {
		this.linkPanels();
	}

	public HowtoTab[] allTabs() {
		NodeList<Element> nodeList = this.querySelectorAll("howto-tab");
		List<Element> elements = nodeList.asList();
		List<HowtoTab> howtoTabs = new ArrayList<HowtoTab>();
		for (Element element : elements) {
			HTMLElement htmlElement = (HTMLElement) element;
			HowtoTab howtoTab = ((HowtoTab) htmlElement);
			howtoTabs.add(howtoTab);
		}
		HowtoTab[] arrHowtoTabs = new HowtoTab[howtoTabs.size()];
		return howtoTabs.toArray(arrHowtoTabs);
	}

	private void linkPanels() {
		HowtoTab[] howtoTabs = this.allTabs();
		for (HowtoTab howtoTab : howtoTabs) {
			HowtoPanel howtoPanel = (HowtoPanel) howtoTab.nextElementSibling;
			if (!howtoPanel.tagName.toLowerCase().equals("howto-panel")) {
				DomGlobal.console.log("Tab #" + howtoPanel.id + " is not a sibling of <howto-panel>");
				return;
			}
			howtoTab.setAttribute("aria-controls", howtoPanel.id);
			howtoPanel.setAttribute("aria-labelledby", howtoTab.id);
		}

		HowtoTab selectedHowtoTab = null;
		for (HowtoTab howtoTab : howtoTabs) {
			if (howtoTab.getSelected() == true) {
				selectedHowtoTab = howtoTab;
				break;
			}
		}
		if (null == selectedHowtoTab) {
			selectedHowtoTab = howtoTabs[0];
		}

		this.selectTab(selectedHowtoTab);
	}

	private void reset() {
		HowtoTab[] howtoTabs = this.allTabs();
		HowtoPanel[] howtoPanels = this.allPanels();

		for (HowtoTab howtoTab : howtoTabs) {
			howtoTab.setSelected(false);
		}

		for (HowtoPanel howtoPanel : howtoPanels) {
			howtoPanel.hidden = true;
		}
	}

	private HowtoPanel panelForTab(HowtoTab howtoTab) {
		String howtoPanelId = howtoTab.getAttribute("aria-controls");
		return (HowtoPanel) this.querySelector("#" + howtoPanelId);
	}

	private void selectTab(HowtoTab newHowtoTab) {
		this.reset();

		HowtoPanel newHowtoPanel = this.panelForTab(newHowtoTab);
		if (null == newHowtoPanel)
			throw new Error("No panel with id " + newHowtoTab.id);
		newHowtoTab.setSelected(true);
		newHowtoPanel.hidden = false;
		newHowtoTab.focus();
	}

	private void onClick(Event event) {
		Element howtoTab = (Element) event.target;
		if (!howtoTab.getAttribute("role").equals("tab"))
			return;
		this.selectTab((HowtoTab) event.target);
	}

}
