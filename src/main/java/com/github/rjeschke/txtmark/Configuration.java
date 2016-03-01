/*
 * Copyright (C) 2011 René Jeschke <rene_jeschke@yahoo.de>
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
package com.github.rjeschke.txtmark;

import java.util.ArrayList;
import java.util.List;

import org.markdown4j.Plugin;

/**
 * Txtmark configuration.
 * 
 * @author René Jeschke <rene_jeschke@yahoo.de>
 * @since 0.7
 */
public class Configuration
{
    final boolean safeMode;

    final String encoding;

    final Decorator decorator;

    final BlockEmitter codeBlockEmitter;

    final boolean forceExtendedProfile;

    final boolean convertNewline2Br;

    final SpanEmitter specialLinkEmitter;

    final List<Plugin> plugins;

    /**
     * <p>
     * This is the default configuration for txtmark's <code>process</code>
     * methods
     * </p>
     * 
     * <ul>
     * <li><code>safeMode = false</code></li>
     * <li><code>encoding = UTF-8</code></li>
     * <li><code>decorator = DefaultDecorator</code></li>
     * <li><code>codeBlockEmitter = null</code></li>
     * </ul>
     */
    public final static Configuration DEFAULT = Configuration.builder()
                                                             .build();

    /**
     * <p>
     * Default safe configuration
     * </p>
     * 
     * <ul>
     * <li><code>safeMode = true</code></li>
     * <li><code>encoding = UTF-8</code></li>
     * <li><code>decorator = DefaultDecorator</code></li>
     * <li><code>codeBlockEmitter = null</code></li>
     * </ul>
     */
    public final static Configuration DEFAULT_SAFE = Configuration.builder()
                                                                  .enableSafeMode()
                                                                  .build();

    /**
     * Constructor.
     * 
     * @param safeMode
     * @param encoding
     * @param decorator
     */
    Configuration( final boolean safeMode, final String encoding, final Decorator decorator, final BlockEmitter codeBlockEmitter,
                   final boolean forceExtendedProfile, final boolean convertNewline2Br, final SpanEmitter specialLinkEmitter,
                   final List<Plugin> plugins )
    {
        this.safeMode = safeMode;
        this.encoding = encoding;
        this.decorator = decorator;
        this.codeBlockEmitter = codeBlockEmitter;
        this.convertNewline2Br = convertNewline2Br;
        this.forceExtendedProfile = forceExtendedProfile;
        this.specialLinkEmitter = specialLinkEmitter;
        this.plugins = plugins;
    }

    /**
     * Creates a new Builder instance.
     * 
     * @return A new Builder instance.
     */
    public static Builder builder()
    {
        return new Builder();
    }

    /**
     * Configuration builder.
     * 
     * @author René Jeschke <rene_jeschke@yahoo.de>
     * @since 0.7
     */
    public static class Builder
    {
        private boolean safeMode = false;

        private boolean forceExtendedProfile = true;

        private boolean convertNewline2Br = false;

        private String encoding = "UTF-8";

        private Decorator decorator = new DefaultDecorator();

        private BlockEmitter codeBlockEmitter = null;

        private SpanEmitter specialLinkEmitter = null;

        private final List<Plugin> plugins = new ArrayList<>();

        /**
         * Constructor.
         * 
         */
        Builder()
        {
            // empty
        }

        /**
         * Enables HTML safe mode.
         * 
         * Default: <code>false</code>
         * 
         * @return This builder
         * @since 0.7
         */
        public Builder enableSafeMode()
        {
            this.safeMode = true;
            return this;
        }

        /**
         * Forces extened profile to be enabled by default.
         * 
         * @return This builder.
         * @since 0.7
         */
        public Builder forceExtentedProfile()
        {
            this.forceExtendedProfile = true;
            return this;
        }

        /**
         * convertNewline2Br.
         * 
         * @return This builder.
         */
        public Builder convertNewline2Br()
        {
            this.convertNewline2Br = true;
            return this;
        }

        /**
         * Sets the HTML safe mode flag.
         * 
         * Default: <code>false</code>
         * 
         * @param flag
         *            <code>true</code> to enable safe mode
         * @return This builder
         * @since 0.7
         */
        public Builder setSafeMode( final boolean flag )
        {
            this.safeMode = flag;
            return this;
        }

        /**
         * Sets the character encoding for txtmark.
         * 
         * Default: <code>&quot;UTF-8&quot;</code>
         * 
         * @param encoding
         *            The encoding
         * @return This builder
         * @since 0.7
         */
        public Builder setEncoding( final String encoding )
        {
            this.encoding = encoding;
            return this;
        }

        /**
         * Sets the decorator for txtmark.
         * 
         * Default: <code>DefaultDecorator()</code>
         * 
         * @param decorator
         *            The decorator
         * @return This builder
         * @see DefaultDecorator
         * @since 0.7
         */
        public Builder setDecorator( final Decorator decorator )
        {
            this.decorator = decorator;
            return this;
        }

        /**
         * Sets the code block emitter.
         * 
         * Default: <code>null</code>
         * 
         * @param emitter
         *            The BlockEmitter
         * @return This builder
         * @see BlockEmitter
         * @since 0.7
         */
        public Builder setCodeBlockEmitter( final BlockEmitter emitter )
        {
            this.codeBlockEmitter = emitter;
            return this;
        }

        /**
         * Sets the emitter for special link spans ([[ ... ]]).
         * 
         * @param emitter
         *            The emitter.
         * @return This builder.
         * @since 0.7
         */
        public Builder setSpecialLinkEmitter( final SpanEmitter emitter )
        {
            this.specialLinkEmitter = emitter;
            return this;
        }

        /**
         * Sets the plugins.
         * 
         * @param plugins
         *            The plugins.
         * @return This builder.
         */
        public Builder registerPlugins( final Plugin... plugins )
        {
            for ( final Plugin plugin : plugins )
            {
                this.plugins.add( plugin );
            }
            return this;
        }

        /**
         * Builds a configuration instance.
         * 
         * @return a Configuration instance
         * @since 0.7
         */
        public Configuration build()
        {
            return new Configuration( this.safeMode, this.encoding, this.decorator, this.codeBlockEmitter, this.forceExtendedProfile,
                                      this.convertNewline2Br, this.specialLinkEmitter, this.plugins );
        }

        public Decorator getDecorator()
        {
            return decorator;
        }
    }
}
