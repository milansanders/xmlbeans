/*   Copyright 2004 The Apache Software Foundation
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *  limitations under the License.
 */


package xmlcursor.checkin;

import org.apache.xmlbeans.XmlOptions;
import junit.framework.*;
import junit.framework.Assert.*;

import java.io.*;

import org.apache.xmlbeans.XmlObject;
import org.apache.xmlbeans.XmlCursor;
import org.apache.xmlbeans.XmlBeans;
import org.apache.xmlbeans.XmlCursor.TokenType;

import javax.xml.namespace.QName;

import org.apache.xmlbeans.impl.store.Cursor;
import xmlcursor.common.*;

import java.net.URL;


/**
 *
 *
 */
public class DisposeTest extends BasicCursorTestCase {
    public DisposeTest(String sName) {
        super(sName);
    }

    public static Test suite() {
        return new TestSuite(DisposeTest.class);
    }

    public void testMultipleDispose() throws Exception {
        m_xo = XmlObject.Factory.parse(Common.XML_FOO);
        m_xc = m_xo.newCursor();
        m_xc.dispose();
        m_xc.dispose();
        assertEquals(true, true);
    }
}

