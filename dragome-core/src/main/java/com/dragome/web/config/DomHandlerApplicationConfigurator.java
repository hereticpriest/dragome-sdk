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
package com.dragome.web.config;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;

import com.dragome.commons.ChainedInstrumentationDragomeConfigurator;
import com.dragome.commons.DragomeConfiguratorImplementor;
import com.dragome.helpers.Utils;
import com.dragome.web.enhancers.jsdelegate.DefaultDelegateStrategy;
import com.dragome.web.enhancers.jsdelegate.JsDelegateGenerator;
import com.dragome.web.enhancers.jsdelegate.interfaces.SubTypeFactory;
import com.dragome.web.html.dom.html5canvas.CanvasImageSource;
import com.dragome.web.html.dom.html5canvas.CanvasRenderingContext2D;
import com.dragome.web.html.dom.html5canvas.HTMLCanvasElement;

@DragomeConfiguratorImplementor
public class DomHandlerApplicationConfigurator extends ChainedInstrumentationDragomeConfigurator
{
	protected JsDelegateGenerator jsDelegateGenerator;

	public List<File> getExtraClasspath(String classpath)
	{
		List<File> result= new ArrayList<File>();
		if (jsDelegateGenerator == null)
		{
			createJsDelegateGenerator(classpath);

			Class<?>[] classes= new Class[] { Document.class, Element.class, Attr.class, NodeList.class, Node.class, //
					NamedNodeMap.class, Text.class, HTMLCanvasElement.class, CanvasRenderingContext2D.class, CanvasImageSource.class };

			for (Class<?> class1 : classes) 
			{
				byte[] bytecode= jsDelegateGenerator.generate(class1);
				addClassBytecode(bytecode, JsDelegateGenerator.createDelegateClassName(class1.getName()));
			}
		}

		result.add(jsDelegateGenerator.getBaseDir());

		return result;
	}
	private void createJsDelegateGenerator(String classpath)
	{
		jsDelegateGenerator= new JsDelegateGenerator(Utils.createTempDir("jsdelegate"), classpath.replace(";", ":"), new DefaultDelegateStrategy()
		{
			public String getSubTypeExtractorFor(Class<?> interface1, String methodName)
			{
				if (methodName.equals("item") || methodName.equals("cloneNode"))
					return "temp.nodeType";

				return null;
			}

			public Class<? extends SubTypeFactory> getSubTypeFactoryClassFor(Class<?> interface1, String methodName)
			{
				if (methodName.equals("item") || methodName.equals("cloneNode"))
					return NodeSubTypeFactory.class;

				return null;
			}
		});
	}
}
