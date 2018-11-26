package me.nageshkumar.gwtj2cl.wc;

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

import com.google.gwt.core.client.EntryPoint;

import elemental2.dom.DomGlobal;
import me.nageshkumar.gwtj2cl.wc.checkbox.HowtoCheckbox;
import me.nageshkumar.gwtj2cl.wc.label.HowtoLabel;
import me.nageshkumar.gwtj2cl.wc.tab.HowtoPanel;
import me.nageshkumar.gwtj2cl.wc.tab.HowtoTab;
import me.nageshkumar.gwtj2cl.wc.tab.HowtoTabs;
import me.nageshkumar.gwtj2cl.wc.tooltip.HowtoTooltip;

public class App implements EntryPoint {
	public void onModuleLoad() {
		DomGlobal.customElements.define("howto-tooltip", HowtoTooltip.class);
		DomGlobal.customElements.define("howto-panel", HowtoPanel.class);
		DomGlobal.customElements.define("howto-tab", HowtoTab.class);
		DomGlobal.customElements.define("howto-tabs", HowtoTabs.class);
		DomGlobal.customElements.define("howto-label", HowtoLabel.class);
		DomGlobal.customElements.define("howto-checkbox", HowtoCheckbox.class);
	}
}