/*
* The Apache Software License, Version 1.1
*
*
* Copyright (c) 2003 The Apache Software Foundation.  All rights 
* reserved.
*
* Redistribution and use in source and binary forms, with or without
* modification, are permitted provided that the following conditions
* are met:
*
* 1. Redistributions of source code must retain the above copyright
*    notice, this list of conditions and the following disclaimer. 
*
* 2. Redistributions in binary form must reproduce the above copyright
*    notice, this list of conditions and the following disclaimer in
*    the documentation and/or other materials provided with the
*    distribution.
*
* 3. The end-user documentation included with the redistribution,
*    if any, must include the following acknowledgment:  
*       "This product includes software developed by the
*        Apache Software Foundation (http://www.apache.org/)."
*    Alternately, this acknowledgment may appear in the software itself,
*    if and wherever such third-party acknowledgments normally appear.
*
* 4. The names "Apache" and "Apache Software Foundation" must 
*    not be used to endorse or promote products derived from this
*    software without prior written permission. For written 
*    permission, please contact apache@apache.org.
*
* 5. Products derived from this software may not be called "Apache 
*    XMLBeans", nor may "Apache" appear in their name, without prior 
*    written permission of the Apache Software Foundation.
*
* THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED
* WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
* OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
* DISCLAIMED.  IN NO EVENT SHALL THE APACHE SOFTWARE FOUNDATION OR
* ITS CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
* SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
* LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF
* USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
* ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
* OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT
* OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
* SUCH DAMAGE.
* ====================================================================
*
* This software consists of voluntary contributions made by many
* individuals on behalf of the Apache Software Foundation and was
* originally based on software copyright (c) 2000-2003 BEA Systems 
* Inc., <http://www.bea.com/>. For more information on the Apache Software
* Foundation, please see <http://www.apache.org/>.
*/

package org.apache.xmlbeans;

import java.math.BigInteger;

import javax.xml.namespace.QName;

/**
 * Represents a summary of similar SchemaFields in a complex type.
 * <p>
 * In a schema type, every element with the same name must have the
 * same type.  Therefore, all together, elements with the same name
 * form a coherent collection of similar elements.  Similarly, attributes
 * can only be defined once, so each attribute obviously is a coherent
 * group on its own.
 * <p>
 * A SchemaProperty represents a summary of the the elements with a
 * given name or the attribute with a given name.  It represents the
 * summary cardinality of the fields, the summary default and fixed
 * values, and so on.  When inferring information about an element
 * or attribute, it is typically easier to consult then SchemaProperty
 * than to hunt for the exact SchemaField in the particle tree or
 * attribute model.
 * 
 * @see SchemaType#getProperties
 * @see SchemaType#getAttributeProperties
 * @see SchemaType#getElementProperties
 * @see SchemaType#getAttributeProperty
 * @see SchemaType#getElementProperty
 */
public interface SchemaProperty
{
    /**
     * The type within which this property appears
     */ 
    SchemaType getContainerType();
    
    /**
     * The name of this element or attribute
     */ 
    QName getName();

    /**
     * For element properties the set of names that are accepted for this property
     * if this element is the head of a substitution group. This will always
     * have at least one element, ie, the property's name.
     */
    QName[] acceptedNames();
    
    /**
     * The Java name for this property.  For example, if the method to
     * access this property is called getFirstName, then this method
     * returns the string "FirstName".  May be null if the schema type
     * has not been compiled to Java.
     */ 
    String getJavaPropertyName();
    
    /**
     * True for read-only properties.
     */ 
    boolean isReadOnly();

    /**
     * True for attributes.
     */ 
    boolean isAttribute();

    /**
     * The schema type for the property.
     */ 
    SchemaType getType();

    // note that in the inheritance hierarchy, if the array getter shows up first,
    // then the singelton getter is never allowed to show up.

    /**
     * The schema type returned from the Java getter for this property.
     * Applies only to types that have been code generated to Java; may
     * be a base type of getType().
     */ 
    public SchemaType javaBasedOnType(); // Java property type based on this base type
    
    /**
     * True if there is a Java getter that returns a singleton.
     */ 
    boolean extendsJavaSingleton(); // has singleton getter
    
    /**
     * True if there is an Java isSet method that tests for presence.
     */ 
    boolean extendsJavaOption();    // has isSet tester
    
    /**
     * True if there is a Java getter that returns an array.
     */ 
    boolean extendsJavaArray();     // has array getter (called -Array, if singleton is present)
    
    /**
     * Returns the natural Java type for this property.  Returns either
     * XML_OBJECT (for complex types) or one of the JAVA_* constants described
     * in this interface.
     */ 
    int getJavaTypeCode();
    
    /**
     * Returns the set of element names which should appear strictly after all
     * occurences of the elements described by this property. For element properties only.
     */ 
    QNameSet getJavaSetterDelimiter();

    /**
     * Returns a summarized minimum occurrance number.
     * For example, a sequence containing a nonoptional singleton element repeated twice will
     * result in a property getMinOccurs() of 2.
     */ 
    BigInteger getMinOccurs();
    
    /**
     * Returns a summarized minimum occurrance number.
     * For example, a sequence containing a nonoptional singleton element repeated twice will
     * result in a property getMaxOccurs() of 2.
     */ 
    BigInteger getMaxOccurs();

    /**
     * Returns {@link #NEVER}, {@link #VARIABLE}, or {@link #CONSISTENTLY} nillable, depending on the
     * nillability of the elements in this property. 
     */
    int hasNillable();

    /**
     * Returns {@link #NEVER}, {@link #VARIABLE}, or {@link #CONSISTENTLY} defaulted, depending on the
     * defaults present in the elements in this property. 
     */
    int hasDefault();

    /**
     * Returns {@link #NEVER}, {@link #VARIABLE}, or {@link #CONSISTENTLY} fixed, depending on the
     * fixed constraints present in the elements in this property. 
     */
    int hasFixed();

    /** Applies to no elements for this property.  See {@link #hasNillable}, {@link #hasDefault}, {@link #hasFixed} */
    static final int NEVER = 0;
    /** Applies to some, but not other elements for this property.  See {@link #hasNillable}, {@link #hasDefault}, {@link #hasFixed} */
    static final int VARIABLE = 1;
    /** Applies to all elements for this property.  See {@link #hasNillable}, {@link #hasDefault}, {@link #hasFixed} */
    static final int CONSISTENTLY = 2;

    /** An XML Bean type that inherits from {@link XmlObject}. See {@link #getJavaTypeCode}. */
    static final int XML_OBJECT = 0;

    /** Java primitive type codes (for non-nullable Java types) are between JAVA_FIRST_PRIMITIVE and JAVA_LAST_PRIMITIVE, inclusive. */
    static final int JAVA_FIRST_PRIMITIVE = 1;

    /** A Java boolean. See {@link #getJavaTypeCode}. */
    static final int JAVA_BOOLEAN = 1;
    /** A Java float. See {@link #getJavaTypeCode}. */
    static final int JAVA_FLOAT = 2;
    /** A Java double. See {@link #getJavaTypeCode}. */
    static final int JAVA_DOUBLE = 3;
    /** A Java byte. See {@link #getJavaTypeCode}. */
    static final int JAVA_BYTE = 4;
    /** A Java short. See {@link #getJavaTypeCode}. */
    static final int JAVA_SHORT = 5;
    /** A Java int. See {@link #getJavaTypeCode}. */
    static final int JAVA_INT = 6;
    /** A Java long. See {@link #getJavaTypeCode}. */
    static final int JAVA_LONG = 7;

    /** Java primitive type codes (for non-nullable Java types) are between JAVA_FIRST_PRIMITIVE and JAVA_LAST_PRIMITIVE, inclusive. */
    static final int JAVA_LAST_PRIMITIVE = 7;

    /** A {@link java.math.BigDecimal}. See {@link #getJavaTypeCode}. */
    static final int JAVA_BIG_DECIMAL = 8;
    /** A {@link java.math.BigInteger}. See {@link #getJavaTypeCode}. */
    static final int JAVA_BIG_INTEGER = 9;
    /** A {@link String}. See {@link #getJavaTypeCode}. */
    static final int JAVA_STRING = 10;
    /** A byte[]. See {@link #getJavaTypeCode}. */
    static final int JAVA_BYTE_ARRAY = 11;
    /** A {@link GDate}. See {@link #getJavaTypeCode}. */
    static final int JAVA_GDATE = 12;
    /** A {@link GDuration}. See {@link #getJavaTypeCode}. */
    static final int JAVA_GDURATION = 13;
    /** A {@link java.util.Date}. See {@link #getJavaTypeCode}. */
    static final int JAVA_DATE = 14;
    /** A {@link javax.xml.namespace.QName}. See {@link #getJavaTypeCode}. */
    static final int JAVA_QNAME = 15;
    /** A {@link java.util.List}. See {@link #getJavaTypeCode}. */
    static final int JAVA_LIST = 16;
    /** A {@link java.util.Calendar}. See {@link #getJavaTypeCode}. */
    static final int JAVA_CALENDAR = 17;

    /** A generated {@link StringEnumAbstractBase} subclass. See {@link #getJavaTypeCode}. */
    static final int JAVA_ENUM = 18;
    /** A {@link java.lang.Object}, used for some simple type unions. See {@link #getJavaTypeCode}. */
    static final int JAVA_OBJECT = 19; // for some unions

    /**
     * Returns the default or fixed value,
     * if it is consistent. If it is not consistent,
     * then returns null.
     * See {@link #hasDefault} and {@link #hasFixed}.
     */
    String getDefaultText();
    
    /**
     * Returns the default or fixed value as a strongly-typed value,
     * if it is consistent. If it is not consistent,
     * then returns null.
     * See {@link #hasDefault} and {@link #hasFixed}.
     */
    XmlAnySimpleType getDefaultValue();
}
