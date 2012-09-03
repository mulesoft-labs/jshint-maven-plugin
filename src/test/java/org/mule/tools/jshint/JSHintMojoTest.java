/**
 * JSHint Maven Plugin
 * Copyright 2010-2011 (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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
