/**
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.tools.jshint;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugin.logging.Log;
import org.codehaus.plexus.util.Scanner;
import org.mule.tools.rhinodo.impl.WrappingConsoleFactory;
import org.mule.tools.rhinodo.maven.MavenConsole;

import java.io.*;
import java.util.Map;

/**
 * Goal that offers JSHint support in Maven builds.
 *
 * @goal jshint
 * @phase validate
 */
public class JSHintMojo extends AbstractJavascriptMojo {
    /**
     * List of files to include. Specified as fileset patterns which are relative to the source directory. Default value is: { "**\/*.js" }
     *
     * @parameter
     */
    protected String[] includes = new String[] { "**/*.js" };

    /**
     * List of files to exclude. Specified as fileset patterns which are relative to the source directory.
     *
     * @parameter
     */
    protected String[] excludes = new String[] {};

    /**
     * The source directory containing the Javascript sources.
     *
     * @parameter expression="${recess.sourceDirectory}" default-value="${project.basedir}/src/main/js"
     */
    protected File sourceDirectory;

    /**
     * Additional configuration parameters that should be passed to JSHint
     *
     * @parameter expression="${recess.config}"
     */
    private Map config;

    private String[] getIncludedFiles() {
        Scanner scanner = buildContext.newScanner(sourceDirectory, true);
        scanner.setIncludes(includes);
        scanner.setExcludes(excludes);
        scanner.scan();
        String[] includedFiles = scanner.getIncludedFiles();
        for (int i = 0; i < includedFiles.length; i++) {
            includedFiles[i] = scanner.getBasedir() + File.separator + includedFiles[i];
        }

        String [] result = new String[includedFiles.length];
        System.arraycopy(includedFiles, 0, result, 0, includedFiles.length);
        return result;
    }

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {

        Log log = getLog();

        sourceDirectory = sourceDirectory.getAbsoluteFile();

        if ( !(sourceDirectory.exists() && sourceDirectory.isDirectory()) ) {
            log.warn(String.format("Not executing jshint as the source directory [%s] does not exist.", sourceDirectory));
            return;
        }

        InputStream inputStream;
        boolean errors = false;
        for (String file : getIncludedFiles()) {

            log.info(String.format("Checking [%s]", file));

            try {
                inputStream = new FileInputStream(file);
            } catch (FileNotFoundException e) {
                throw new MojoExecutionException(String.format("Error loading file [%s]",file), e);
            }

            if ( !new JSHint(new WrappingConsoleFactory(MavenConsole.fromMavenLog(log)),
                    getJavascriptFilesDirectory()).check(file, inputStream, config) ) {
                errors = true;
            }
        }

        if (errors) {
            throw new MojoFailureException("Errors were found while performing jshint over Javascript code");
        }

        log.info("No problems found in analyzed Javascript files.");

    }
}
