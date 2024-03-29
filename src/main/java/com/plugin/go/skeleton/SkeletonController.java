/*
 * Copyright 2023 Thoughtworks, Inc.
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

import com.google.gson.GsonBuilder;
import com.thoughtworks.go.plugin.api.GoApplicationAccessor;
import com.thoughtworks.go.plugin.api.GoPlugin;
import com.thoughtworks.go.plugin.api.GoPluginIdentifier;
import com.thoughtworks.go.plugin.api.annotation.Extension;
import com.thoughtworks.go.plugin.api.logging.Logger;
import com.thoughtworks.go.plugin.api.request.GoPluginApiRequest;
import com.thoughtworks.go.plugin.api.response.DefaultGoPluginApiResponse;
import com.thoughtworks.go.plugin.api.response.GoPluginApiResponse;

import java.util.Arrays;
import java.util.Map;

import static utils.Constants.*;

@Extension
public class SkeletonController implements GoPlugin {
    private GoApplicationAccessor accessor;
    private static Logger logger = Logger.getLoggerFor(SkeletonController.class);
    private ConnectionHandler connectionHandler = new ConnectionHandler();
    private RepositoryConfigHandler repositoryConfigHandler = new RepositoryConfigHandler(connectionHandler);
    private PackageConfigHandler packageConfigHandler = new PackageConfigHandler();
    private PackagePoller packagePoller = new PackagePoller(connectionHandler);


    public void initializeGoApplicationAccessor(GoApplicationAccessor goApplicationAccessor) {
        this.accessor = goApplicationAccessor;
    }

    public GoPluginApiResponse handle(GoPluginApiRequest goPluginApiRequest) {

        logger.info("Request name " + goPluginApiRequest.requestName());
        logger.info("Request body " + goPluginApiRequest.requestBody());
        logger.info("Request body " + goPluginApiRequest.requestParameters());
        logger.info("Request body " + goPluginApiRequest.requestHeaders());

        Object result = null;
        String requestName = goPluginApiRequest.requestName();
        Map requestBodyMap = (Map) new GsonBuilder().create().fromJson(goPluginApiRequest.requestBody(), Object.class);

        if (requestName.equals(REPOSITORY_CONFIGURATION)) {
            result = repositoryConfigHandler.handleConfiguration();
        } else if (requestName.equals(VALIDATE_REPOSITORY_CONFIGURATION)) {
            result = repositoryConfigHandler.handleValidateConfiguration(requestBodyMap);
        } else if (requestName.equals(CHECK_REPOSITORY_CONNECTION)) {
            result = repositoryConfigHandler.handleCheckRepositoryConnection(requestBodyMap);
        } else if (requestName.equals(PACKAGE_CONFIGURATION)) {
            result = packageConfigHandler.handleConfiguration();
        } else if (requestName.equals(VALIDATE_PACKAGE_CONFIGURATION)) {
            result = packageConfigHandler.handleValidateConfiguration(requestBodyMap);
        } else if (requestName.equals(CHECK_PACKAGE_CONNECTION)) {
            result = packagePoller.handleCheckPackageConnection(requestBodyMap);
        } else if (requestName.equals(LATEST_REVISION)) {
            result = packagePoller.handleLatestRevision(requestBodyMap);
        } else if (requestName.equals(LATEST_REVISION_SINCE)) {
            result = packagePoller.handleLatestRevisionSince(requestBodyMap);
        }

        if (result != null) {
            GoPluginApiResponse response = createResponse(SUCCESS_RESPONSE_CODE, result);
            logger.info("Response body " + response.responseBody());
            return response;
        }

        return null;
    }

    public GoPluginIdentifier pluginIdentifier() {
        return new GoPluginIdentifier("package-repository", Arrays.asList("1.0"));
    }

    private static GoPluginApiResponse createResponse(int responseCode, Object body) {
        final DefaultGoPluginApiResponse response = new DefaultGoPluginApiResponse(responseCode);
        response.setResponseBody(new GsonBuilder().serializeNulls().create().toJson(body));
        return response;
    }

}
