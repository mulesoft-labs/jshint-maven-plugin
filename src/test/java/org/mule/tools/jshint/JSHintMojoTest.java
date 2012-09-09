/**
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.tools.jshint;

import org.apache.maven.plugin.testing.AbstractMojoTestCase;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.MockitoAnnotations;
import org.mule.tools.rhinodo.impl.SystemOutConsole;
import org.mule.tools.rhinodo.impl.WrappingConsoleFactory;
import org.sonatype.plexus.build.incremental.BuildContext;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.util.HashMap;

@RunWith(JUnit4.class)
public class JSHintMojoTest extends AbstractMojoTestCase {

    private JSHintMojo mojo;
    private File sourceDirectory = new File("target/source");
    private String outputDirectory = "target/output";
    private File helloLessFile = new File(outputDirectory, "hello.less");
    private File helloCssFile = new File(sourceDirectory, "hello.css");
    private File outputFile;

    @MockitoAnnotations.Mock
    private BuildContext buildContext;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        mojo = new JSHintMojo();

        sourceDirectory.mkdir();

        new File(outputDirectory).mkdirs();

        setVariableValueToObject(mojo, "buildContext", buildContext);
        setVariableValueToObject(mojo, "sourceDirectory", sourceDirectory);

    }

    @Test
    public void testExecution() throws Exception {
        new JSHint(new WrappingConsoleFactory(new SystemOutConsole()), new File("target/rhinodo").getAbsoluteFile().toString()).check("hello.js", new ByteArrayInputStream(("function() {}").getBytes("UTF-8")), new HashMap());
    }

}
