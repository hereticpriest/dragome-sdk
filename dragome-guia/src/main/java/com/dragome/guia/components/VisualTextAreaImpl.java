/*
 * Copyright (c) 2011-2014 Fernando Petrola
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
package com.dragome.guia.components;

import com.dragome.guia.components.interfaces.VisualTextArea;
import com.dragome.model.interfaces.Renderer;
import com.dragome.model.pectin.ComponentWithValueAndRendererImpl;

public class VisualTextAreaImpl<T> extends ComponentWithValueAndRendererImpl<T> implements VisualTextArea<T>
{
	public VisualTextAreaImpl()
	{
	}

	public VisualTextAreaImpl(String aName)
	{
		this(aName, new SimpleRenderer<T>());
	}

	public VisualTextAreaImpl(String aName, T aValue)
	{
		this(aName);
		setValue(aValue);
	}

	public VisualTextAreaImpl(String aName, Renderer<T> renderer)
	{
		super(aName, renderer);
	}
}
