/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.camel.example.reportincident;

import org.apache.camel.builder.RouteBuilder;

public class ReportIncidentRoutes extends RouteBuilder {

    public void configure() throws Exception {
        // webservice response for OK
        OutputReportIncident OK = new OutputReportIncident();
        OK.setCode("0");

        // endpoint to our CXF webservice
        String cxfEndpoint = "cxf://http://localhost:8080/camel-example/incident"
                + "?serviceClass=org.apache.camel.example.reportincident.ReportIncidentEndpoint"
                + "&wsdlURL=wsdl/report_incident.wsdl";

        // first part from the webservice -> file backup
        from(cxfEndpoint)
            // we need to convert the CXF payload to InputReportIncident that FilenameGenerator and velocity expects
            .convertBodyTo(InputReportIncident.class)
            // return OK as response
            .transform(constant(OK));

    }

}
