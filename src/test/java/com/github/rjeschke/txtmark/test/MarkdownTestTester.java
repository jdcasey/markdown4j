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

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import com.github.rjeschke.txtmark.Processor;

@RunWith( value = Parameterized.class )
public class MarkdownTestTester
{

    private static final String MARKDOWN_TEST_DIR = "/MarkdownTest";

    String test;

    String dir;

    @Parameters
    public static Collection<Object[]> markdownTests()
    {
        final List list = new ArrayList<Object[]>();
        final URL fileUrl = MarkdownTestTester.class.getResource( MARKDOWN_TEST_DIR );
        final File dir = new File( fileUrl.getFile() );
        final File[] dirEntries = dir.listFiles();

        for ( final File dirEntry : dirEntries )
        {
            final String fileName = dirEntry.getName();
            if ( fileName.endsWith( ".text" ) )
            {
                final String testName = fileName.substring( 0, fileName.lastIndexOf( '.' ) );
                list.add( new Object[] { MARKDOWN_TEST_DIR, testName } );
            }
        }

        return list;
    }

    public MarkdownTestTester( final String dir, final String test )
    {
        this.test = test;
        this.dir = dir;
    }

    @Test
    public void runTest()
        throws IOException
    {
        final String testText = slurp( dir + File.separator + test + ".text" );
        final String htmlText = slurp( dir + File.separator + test + ".html" );
        if ( testText.startsWith( "IGNORED:" ) )
        {
            System.out.println( "Ignoring: " + test );
            return;
        }

        final String markdownText = Processor.process( testText );
        assertEquals( test, htmlText.trim(), markdownText.trim() );
    }

    private String slurp( final String fileName )
        throws IOException
    {
        final URL fileUrl = this.getClass()
                                .getResource( fileName );
        final File file = new File( URLDecoder.decode( fileUrl.getFile(), "UTF-8" ) );
        final FileReader in = new FileReader( file );
        final StringBuffer sb = new StringBuffer();
        int ch;
        while ( ( ch = in.read() ) != -1 )
        {
            sb.append( (char) ch );
        }
        return sb.toString();
    }
}
