/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.shardingsphere.infra.yaml.config.swapper.rule;

import org.apache.shardingsphere.infra.config.rule.RuleConfiguration;
import org.apache.shardingsphere.infra.spi.type.ordered.OrderedSPILoader;
import org.apache.shardingsphere.infra.util.yaml.datanode.RepositoryTuple;
import org.apache.shardingsphere.infra.util.yaml.swapper.YamlDataNodeConfigurationSwapper;

import java.util.Collection;
import java.util.LinkedList;
import java.util.Map;

/**
 * YAML data node rule configuration swapper engine.
 */
public final class YamlDataNodeRuleConfigurationSwapperEngine {
    
    /**
     * Swap to YAML rule configurations.
     *
     * @param ruleConfigs rule configurations
     * @return YAML rule configurations
     */
    @SuppressWarnings("rawtypes")
    public Map<RuleConfiguration, YamlDataNodeConfigurationSwapper> swapToYamlRuleConfigurations(final Collection<RuleConfiguration> ruleConfigs) {
        return OrderedSPILoader.getServices(YamlDataNodeConfigurationSwapper.class, ruleConfigs);
    }
    
    /**
     * Swap from YAML rule configurations to rule configurations.
     *
     * @param repositoryTuples repository tuples
     * @return rule configurations
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    public Collection<RuleConfiguration> swapToRuleConfigurations(final Collection<RepositoryTuple> repositoryTuples) {
        Collection<RuleConfiguration> result = new LinkedList<>();
        for (YamlDataNodeConfigurationSwapper each : OrderedSPILoader.getServices(YamlDataNodeConfigurationSwapper.class)) {
            each.swapToObject(repositoryTuples).ifPresent(optional -> result.add((RuleConfiguration) optional));
        }
        return result;
    }
}
