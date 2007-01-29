/*
* Copyright 2004,2005 The Apache Software Foundation.
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*      http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/
package org.apache.synapse.statistics;

import org.apache.synapse.Constants;
import org.apache.synapse.MessageContext;
import org.apache.synapse.core.SynapseEnvironment;
import org.apache.synapse.statistics.impl.SequenceStatisticsStack;
import org.apache.synapse.statistics.impl.EndPointStatisticsStack;
import org.apache.synapse.statistics.impl.ProxyServiceStatisticsStack;

/**
 * A utils to process statistics
 *
 */

public class StatisticsUtils {

    /**
     * To process statistics related to the proxy services
     *
     * @param synCtx
     */
    public static void processProxyServiceStatistics(MessageContext synCtx) {

        StatisticsCollector statisticsCollector = getStatisticsCollector(synCtx);
        ProxyServiceStatisticsStack proxyServiceStatisticsStack = (ProxyServiceStatisticsStack) synCtx.getProperty(Constants.PROXYSERVICE_STATISTICS_STACK);
        if (proxyServiceStatisticsStack != null) {
            proxyServiceStatisticsStack.reportToStatisticsCollector(statisticsCollector);
        }
        ProxyServiceStatisticsStack synapseServiceStatisticsStack = (ProxyServiceStatisticsStack) synCtx.getProperty(Constants.SYNAPSESERVICE_STATISTICS_STACK);
        if (synapseServiceStatisticsStack != null) {
            synapseServiceStatisticsStack.reportToStatisticsCollector(statisticsCollector);
        }
    }

    /**
     * To process statistics related to the End Points
     *
     * @param synCtx
     */
    public static void processEndPointStatistics(MessageContext synCtx) {
        StatisticsCollector statisticsCollector = getStatisticsCollector(synCtx);
        EndPointStatisticsStack endPointStatisticsStack = (EndPointStatisticsStack) synCtx.getProperty(Constants.ENDPOINT_STATISTICS_STACK);
        if (endPointStatisticsStack != null) {
            endPointStatisticsStack.reportToStatisticsCollector(statisticsCollector);
        }
    }

    /**
     * To process statistics related to the sequence
     *
     * @param synCtx
     */
    public static void processSequenceStatistics(MessageContext synCtx) {
        StatisticsCollector statisticsCollector = getStatisticsCollector(synCtx);
        SequenceStatisticsStack sequenceStatisticsStack = (SequenceStatisticsStack) synCtx.getProperty(Constants.SEQUENCE_STATISTICS_STACK);
        if (sequenceStatisticsStack != null) {
            sequenceStatisticsStack.reportToStatisticsCollector(statisticsCollector);
        }
    }

    /**
     * A helper method to get StatisticsCollector from the Synapse Message Context
     *
     * @param synCtx
     * @return StatisticsCollector
     */
    private static StatisticsCollector getStatisticsCollector(MessageContext synCtx) {
        SynapseEnvironment synEnv = synCtx.getEnvironment();
        StatisticsCollector statisticsCollector = null;
        if (synEnv != null) {
            statisticsCollector = synEnv.getStatisticsCollector();
            if (statisticsCollector == null) {
                statisticsCollector = new StatisticsCollector();
                synEnv.setStatisticsCollector(statisticsCollector);
            }
        }
        return statisticsCollector;
    }
}
