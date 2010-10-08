/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2010, Red Hat, Inc., and individual contributors
 * as indicated by the @author tags. See the copyright.txt file in the
 * distribution for a full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */

package org.jboss.as.model.base;

import javax.xml.stream.FactoryConfigurationError;
import javax.xml.stream.XMLStreamException;

import org.jboss.as.model.DomainModel;
import org.jboss.as.model.Element;
import org.jboss.as.model.JvmElement;
import org.jboss.as.model.PropertiesElement;
import org.jboss.as.model.ServerGroupElement;
import org.jboss.as.model.UpdateFailedException;
import org.jboss.as.model.base.util.ModelParsingSupport;

/**
 * Base class for unit tests of {@link PropertiesElement} as the set of
 * system properties in a server group {@link JvmElement}.
 *
 * @author Brian Stansberry
 * @author Kabir Khan
 */
public abstract class ServerGroupJvmSystemPropertiesTestBase extends DomainModelElementTestBase {

    JvmSystemPropertiesTestCommon delegate;

    /**
     * @param name
     */
    public ServerGroupJvmSystemPropertiesTestBase(String name) {
        super(name);
    }


    @Override
    protected void setUp() throws Exception {
        super.setUp();
        delegate = new JvmSystemPropertiesTestCommon(new JvmSystemPropertiesTestCommon.ContentAndPropertiesGetter(getXMLMapper(), getTargetNamespace(), getTargetNamespaceLocation()) {

            @Override
            PropertiesElement getTestProperties(String fullcontent) throws XMLStreamException, FactoryConfigurationError,
                    UpdateFailedException {
                DomainModel root = ModelParsingSupport.parseDomainModel(getXMLMapper(), fullcontent);
                ServerGroupElement sge = root.getServerGroup("test");
                assertNotNull(sge);
                JvmElement jvm = sge.getJvm();
                assertNotNull(jvm);
                PropertiesElement testee = jvm.getSystemProperties();
                assertNotNull(testee);
                return testee;
            }

            @Override
            String getFullContent(String testContent) {
                testContent = ModelParsingSupport.wrapJvm(testContent);
                testContent = ModelParsingSupport.wrapServerGroup(testContent);
                String fullcontent = ModelParsingSupport.getXmlContent(Element.DOMAIN.getLocalName(), getTargetNamespace(), getTargetNamespaceLocation(), testContent);
                return fullcontent;
            }
        });
    }

    public void testBasicProperties() throws Exception {
        delegate.testBasicProperties();
    }

    public void testNullProperties() throws Exception {
        delegate.testNullProperties();
    }

    public void testMissingName() throws Exception {
        delegate.testMissingName();
    }

    public void testBogusAttribute() throws Exception {
        delegate.testBogusAttribute();
    }

    public void testBogusChild() throws Exception {
        delegate.testBogusChild();
    }

    public void testNoChildren() throws Exception {
        delegate.testNoChildren();
    }

    /* (non-Javadoc)
     * @see org.jboss.as.model.base.DomainModelElementTestBase#testSerializationDeserialization()
     */
    @Override
    public void testSerializationDeserialization() throws Exception {
        delegate.testSerializationDeserialization();
    }
}
