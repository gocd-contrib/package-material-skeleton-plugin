/*
 * Copyright 2016 ThoughtWorks, Inc.
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
 *
 */

package plugin.go.skeleton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RepositoryConfigHandler extends PluginConfigHandler {

    private ConnectionHandler connectionHandler;

    public RepositoryConfigHandler(ConnectionHandler connectionHandler) {
        this.connectionHandler = connectionHandler;
    }

    public Map handleConfiguration() {
        Map repositoryConfig = new HashMap();

        repositoryConfig.put("SKELETON_REPOSITORY_URL", createConfigurationField("Skeleton Repository Url", "0", false, true, true));
        repositoryConfig.put("SKELETON_USERNAME", createConfigurationField("Skeleton Username", "1", false, false, false));
        repositoryConfig.put("SKELETON_PASSWORD", createConfigurationField("Skeleton Password", "2", true, false, false));

        return repositoryConfig;
    }

    public List handleValidateConfiguration(Map request) {
        List validationList = new ArrayList();

        Map configMap = (Map) request.get("repository-configuration");
        Map urlMap = (Map) configMap.get("SKELETON_REPOSITORY_URL");

        if (urlMap.get("value").equals("")) {
            Map errors = new HashMap();
            errors.put("key", "SKELETON_REPOSITORY_URL");
            errors.put("message", "Skeleton Url cannot be empty");
            validationList.add(errors);
        }

        return validationList;
    }

    public Map handleCheckRepositoryConnection(Map request) {
        Map configMap = (Map) request.get("repository-configuration");

        String repoUrl = parseValueFromEmbeddedMap(configMap, "SKELETON_REPOSITORY_URL");
        String username = parseValueFromEmbeddedMap(configMap, "SKELETON_USERNAME");
        String password = parseValueFromEmbeddedMap(configMap, "SKELETON_PASSWORD");

        return connectionHandler.checkConnectionToRepo(repoUrl, username, password);
    }

    private String parseValueFromEmbeddedMap(Map configMap, String fieldName) {
        Map fieldMap = (Map) configMap.get(fieldName);
        String value = (String) fieldMap.get("value");
        return value;
    }


}
