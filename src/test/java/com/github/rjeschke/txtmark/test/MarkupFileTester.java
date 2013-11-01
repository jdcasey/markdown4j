/*
Copyright (c) 2005, Pete Bevin.
<http://markdownj.petebevin.com>

All rights reserved.

Redistribution and use in source and binary forms, with or without
modification, are permitted provided that the following conditions are
met:

* Redistributions of source code must retain the above copyright notice,
  this list of conditions and the following disclaimer.

* Redistributions in binary form must reproduce the above copyright
  notice, this list of conditions and the following disclaimer in the
  documentation and/or other materials provided with the distribution.

* Neither the name "Markdown" nor the names of its contributors may
  be used to endorse or promote products derived from this software
  without specific prior written permission.

This software is provided by the copyright holders and contributors "as
is" and any express or implied warranties, including, but not limited
to, the implied warranties of merchantability and fitness for a
particular purpose are disclaimed. In no event shall the copyright owner
or contributors be liable for any direct, indirect, incidental, special,
exemplary, or consequential damages (including, but not limited to,
procurement of substitute goods or services; loss of use, data, or
profits; or business interruption) however caused and on any theory of
liability, whether in contract, strict liability, or tort (including
negligence or otherwise) arising in any way out of the use of this
software, even if advised of the possibility of such damage.

*/

package com.github.rjeschke.txtmark.test;

import static org.junit.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import com.github.rjeschke.txtmark.Processor;

@RunWith( value = Parameterized.class )
public class MarkupFileTester
{

    private final static String[] TEST_FILENAMES = new String[] { "/dingus.txt", "/paragraphs.txt", "/snippets.txt", "/lists.txt" };

    TestResultPair pair;

    @Parameters
    public static Collection<Object[]> testResultPairs()
        throws IOException
    {
        final List<TestResultPair> fullResultPairList = new ArrayList<TestResultPair>();
        for ( final String filename : TEST_FILENAMES )
        {
            fullResultPairList.addAll( newTestResultPairList( filename ) );
        }

        final Collection<Object[]> testResultPairs = new ArrayList<Object[]>();
        for ( final TestResultPair p : fullResultPairList )
        {
            testResultPairs.add( new Object[] { p } );
        }
        return testResultPairs;
    }

    public MarkupFileTester( final TestResultPair pair )
    {
        this.pair = pair;
    }

    public static List<TestResultPair> newTestResultPairList( final String filename )
        throws IOException
    {
        final List<TestResultPair> list = new ArrayList<TestResultPair>();
        final URL fileUrl = MarkupFileTester.class.getResource( filename );
        final FileReader file = new FileReader( fileUrl.getFile() );
        BufferedReader in = null;
        try
        {
            in = new BufferedReader( file );
            StringBuffer test = null;
            StringBuffer result = null;

            final Pattern pTest = Pattern.compile( "# Test (\\w+) \\((.*)\\)" );
            final Pattern pResult = Pattern.compile( "# Result (\\w+)" );
            String line;
            int lineNumber = 0;

            String testNumber = null;
            String testName = null;
            StringBuffer curbuf = null;
            boolean ignored = false;
            while ( ( line = in.readLine() ) != null )
            {
                if ( line.endsWith( "-IGNORED" ) )
                {
                    ignored = true;
                    continue;
                }

                lineNumber++;
                final Matcher mTest = pTest.matcher( line );
                final Matcher mResult = pResult.matcher( line );

                if ( mTest.matches() )
                { // # Test
                    if ( ignored && !line.endsWith( "-IGNORED" ) )
                    {
                        ignored = false;
                    }

                    testNumber = mTest.group( 1 );
                    testName = mTest.group( 2 );

                    addTestResultPair( list, test, result, testNumber, testName );

                    test = new StringBuffer();
                    result = new StringBuffer();
                    curbuf = test;
                }
                else if ( mResult.matches() )
                { // # Result
                    if ( ignored )
                    {
                        continue;
                    }

                    if ( testNumber == null )
                    {
                        throw new RuntimeException( "Test file has result without a test (line " + lineNumber + ")" );
                    }
                    final String resultNumber = mResult.group( 1 );
                    if ( !testNumber.equals( resultNumber ) )
                    {
                        throw new RuntimeException( "Result " + resultNumber + " test " + testNumber + " (line " + lineNumber + ")" );
                    }

                    curbuf = result;
                }
                else
                {
                    if ( ignored )
                    {
                        continue;
                    }

                    curbuf.append( line );
                    curbuf.append( "\n" );
                }
            }

            addTestResultPair( list, test, result, testNumber, testName );

            return list;

        }
        finally
        {
            if ( in != null )
            {
                try
                {
                    in.close();
                }
                catch ( final IOException e )
                {
                }
            }
        }
    }

    private static void addTestResultPair( final List list, final StringBuffer testbuf, final StringBuffer resultbuf, final String testNumber,
                                           final String testName )
    {
        if ( testbuf == null || resultbuf == null )
        {
            return;
        }

        final String test = chomp( testbuf.toString() );
        final String result = chomp( resultbuf.toString() );

        final String id = testNumber + "(" + testName + ")";

        list.add( new TestResultPair( id, test, result ) );
    }

    private static String chomp( final String s )
    {
        int lastPos = s.length() - 1;
        while ( s.charAt( lastPos ) == '\n' || s.charAt( lastPos ) == '\r' )
        {
            lastPos--;
        }
        return s.substring( 0, lastPos + 1 );
    }

    @Test
    public void runTest()
    {
        assertEquals( pair.toString(), pair.getResult()
                                           .trim(), Processor.process( pair.getTest() )
                                                             .trim() );
    }
}
