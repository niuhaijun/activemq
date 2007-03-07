/**
 *
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.activemq.transport.discovery.multicast;

import java.io.IOException;
import java.net.URI;
import java.util.Map;

import org.apache.activemq.transport.discovery.DiscoveryAgent;
import org.apache.activemq.transport.discovery.DiscoveryAgentFactory;
import org.apache.activemq.util.IOExceptionSupport;
import org.apache.activemq.util.IntrospectionSupport;
import org.apache.activemq.util.URISupport;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class MulticastDiscoveryAgentFactory extends DiscoveryAgentFactory {

    private static final Log log = LogFactory.getLog(MulticastDiscoveryAgentFactory.class);
    
    protected DiscoveryAgent doCreateDiscoveryAgent(URI uri) throws IOException {
        try {
            
            Map options = URISupport.parseParamters(uri);
            MulticastDiscoveryAgent rc = new MulticastDiscoveryAgent();
            rc.setGroup(uri.getHost());

            if ("default".equals(uri.getHost())) {
                log.info("Using default discovery uri " + uri);

            } else {
                // only set the discovery URI if a non-default multicast IP/port endpoint is being used.
                log.info("Setting discovery uri to " + uri);
                rc.setDiscoveryURI(uri);
            }
            
            IntrospectionSupport.setProperties(rc, options);
            return rc;
            
        } catch (Throwable e) {
            throw IOExceptionSupport.create("Could not create discovery agent: " + uri, e);
        }
    }
}
